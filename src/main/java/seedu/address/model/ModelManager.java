package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * Represents the in-memory model of the loan book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Bike> filteredBikes;
    private final FilteredList<Loan> filteredLoans;

    /**
     * Initializes a ModelManager with the given loanBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with loan book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredBikes = new FilteredList<>(versionedAddressBook.getBikeList());
        filteredLoans = new FilteredList<>(versionedAddressBook.getLoanList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    //=========== Bike List Mutators =============================================================

    @Override
    public boolean hasBike(Bike bike) {
        requireNonNull(bike);
        return versionedAddressBook.hasBike(bike);
    }

    @Override
    public void addBike(Bike bike) {
        versionedAddressBook.addBike(bike);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteBike(Bike target) {
        versionedAddressBook.removeBike(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateBike(Bike target, Bike editedBike) {
        requireAllNonNull(target, editedBike);

        versionedAddressBook.updateBike(target, editedBike);
        indicateAddressBookChanged();
    }

    //=========== Filtered Bike List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bike} backed by the internal list of
     * {@code versionedLoanBook}
     */
    @Override
    public ObservableList<Bike> getFilteredBikeList() {
        return FXCollections.unmodifiableObservableList(filteredBikes);
    }

    @Override
    public void updateFilteredBikeList(Predicate<Bike> predicate) {
        requireNonNull(predicate);
        filteredBikes.setPredicate(predicate);
    }

    //=========== Loan List Mutators =============================================================

    @Override
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return versionedAddressBook.hasLoan(loan);
    }

    @Override
    public void addLoan(Loan loan) {
        versionedAddressBook.addLoan(loan);
        updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteLoan(Loan target) {
        versionedAddressBook.removeLoan(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        versionedAddressBook.updateLoan(target, editedLoan);
        indicateAddressBookChanged();
    }

    //=========== Filtered Loan List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Loan} backed by the internal list of
     * {@code versionedLoanBook}
     */
    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        return FXCollections.unmodifiableObservableList(filteredLoans);
    }

    @Override
    public void updateFilteredLoanList(Predicate<Loan> predicate) {
        requireNonNull(predicate);
        filteredLoans.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredBikes.equals(other.filteredBikes)
                && filteredLoans.equals(other.filteredLoans);
    }

}
