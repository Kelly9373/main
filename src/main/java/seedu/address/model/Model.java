package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Bike> PREDICATE_SHOW_ALL_BIKES = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Loan> PREDICATE_SHOW_ALL_LOANS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLoanBook newData);

    /** Returns the LoanBook */
    ReadOnlyLoanBook getLoanBook();

    /**
     * Returns true if a bike with the same identity as {@code bike} exists in the loan book.
     */
    boolean hasBike(Bike bike);

    /**
     * Adds the given bike.
     * {@code bike} must not already exist in the loan book.
     */
    void addBike(Bike bike);

    /**
     * Deletes the given bike.
     * The bike must exist in the loan book.
     */
    void deleteBike(Bike target);

    /**
     * Replaces the given bike {@code target} with {@code editedBike}.
     * {@code target} must exist in the loan book.
     * The bike identity of {@code editedBike} must not be the same as another existing bike in the loan book.
     */
    void updateBike(Bike target, Bike editedBike);

    /** Returns an unmodifiable view of the filtered bike list */
    ObservableList<Bike> getFilteredBikeList();

    /**
     * Updates the filter of the filtered bike list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBikeList(Predicate<Bike> predicate);

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the loan book.
     */
    boolean hasLoan(Loan loan);

    /**
     * Adds the given loan.
     * {@code loan} must not already exist in the loan book.
     */
    void addLoan(Loan loan);

    /**
     * Deletes the given loan.
     * The loan must exist in the loan book.
     */
    void deleteLoan(Loan target);

    /**
     * Replaces the given loan {@code target} with {@code editedLoan}.
     * {@code target} must exist in the loan book.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the loan book.
     */
    void updateLoan(Loan target, Loan editedLoan);

    /** Returns an unmodifiable view of the filtered loan list */
    ObservableList<Loan> getFilteredLoanList();

    /**
     * Updates the filter of the filtered loan list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLoanList(Predicate<Loan> predicate);

    /**
     * Returns true if the model has previous loan book states to restore.
     */
    boolean canUndoLoanBook();

    /**
     * Returns true if the model has undone loan book states to restore.
     */
    boolean canRedoLoanBook();

    /**
     * Restores the model's loan book to its previous state.
     */
    void undoLoanBook();

    /**
     * Restores the model's loan book to its previously undone state.
     */
    void redoLoanBook();

    /**
     * Saves the current loan book state for undo/redo.
     */
    void commitLoanBook();
}
