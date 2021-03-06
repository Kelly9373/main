package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BIKES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LOANS;
import static seedu.address.testutil.TypicalBikes.BIKE1;
import static seedu.address.testutil.TypicalBikes.BIKE2;
import static seedu.address.testutil.TypicalLoans.ALICE;
import static seedu.address.testutil.TypicalLoans.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.loan.NameContainsKeywordsPredicate;
import seedu.address.testutil.LoanBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasBike_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasBike(null);
    }

    @Test
    public void hasLoan_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasLoan(null);
    }

    @Test
    public void hasBike_bikeNotInLoanBook_returnsFalse() {
        assertFalse(modelManager.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanNotInLoanBook_returnsFalse() {
        assertFalse(modelManager.hasLoan(ALICE));
    }

    @Test
    public void hasBike_bikeInLoanBook_returnsTrue() {
        modelManager.addBike(BIKE1);
        assertTrue(modelManager.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanInLoanBook_returnsTrue() {
        modelManager.addLoan(ALICE);
        assertTrue(modelManager.hasLoan(ALICE));
    }

    @Test
    public void getFilteredBikeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredBikeList().remove(0);
    }

    @Test
    public void getFilteredLoanList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLoanList().remove(0);
    }

    @Test
    public void equals() {
        LoanBook loanBook = new LoanBookBuilder()
                .withLoan(ALICE).withLoan(BENSON)
                .withBike(BIKE1).withBike(BIKE2).build();
        LoanBook differentLoanBook = new LoanBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(loanBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(loanBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different loanBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLoanBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().value.split("\\s+");
        modelManager.updateFilteredLoanList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(loanBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBikeList(PREDICATE_SHOW_ALL_BIKES);
        modelManager.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLoanBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(loanBook, differentUserPrefs)));
    }
}
