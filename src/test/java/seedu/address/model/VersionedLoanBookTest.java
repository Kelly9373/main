package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalLoans.AMY;
import static seedu.address.testutil.TypicalLoans.BOB;
import static seedu.address.testutil.TypicalLoans.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedLoanBookTest {

    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withLoan(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withLoan(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withLoan(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(emptyAddressBook);

        versionedLoanBook.commit();
        assertAddressBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedLoanBook.commit();
        assertAddressBookListStatus(versionedLoanBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        versionedLoanBook.commit();
        assertAddressBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        assertTrue(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertFalse(versionedLoanBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        assertTrue(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertTrue(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedLoanBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedLoanBook.undo();
        assertAddressBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        versionedLoanBook.undo();
        assertAddressBookListStatus(versionedLoanBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedLoanBook.NoUndoableStateException.class, versionedLoanBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertThrows(VersionedLoanBook.NoUndoableStateException.class, versionedLoanBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        versionedLoanBook.redo();
        assertAddressBookListStatus(versionedLoanBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        versionedLoanBook.redo();
        assertAddressBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedLoanBook.NoRedoableStateException.class, versionedLoanBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedLoanBook.NoRedoableStateException.class, versionedLoanBook::redo);
    }

    @Test
    public void equals() {
        VersionedLoanBook versionedLoanBook = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedLoanBook copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedLoanBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedLoanBook.equals(versionedLoanBook));

        // null -> returns false
        assertFalse(versionedLoanBook.equals(null));

        // different types -> returns false
        assertFalse(versionedLoanBook.equals(1));

        // different state list -> returns false
        VersionedLoanBook differentAddressBookList = prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedLoanBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedLoanBook differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);
        assertFalse(versionedLoanBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedLoanBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedLoanBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedLoanBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedLoanBook versionedLoanBook,
                                             List<ReadOnlyAddressBook> expectedStatesBeforePointer,
                                             ReadOnlyAddressBook expectedCurrentState,
                                             List<ReadOnlyAddressBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new LoanBook(versionedLoanBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedLoanBook.canUndo()) {
            versionedLoanBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new LoanBook(versionedLoanBook));
            versionedLoanBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedLoanBook.redo();
            assertEquals(expectedAddressBook, new LoanBook(versionedLoanBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedLoanBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedLoanBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedLoanBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedLoanBook#currentStatePointer} at the end of list.
     */
    private VersionedLoanBook prepareAddressBookList(ReadOnlyAddressBook... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedLoanBook versionedLoanBook = new VersionedLoanBook(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedLoanBook.resetData(addressBookStates[i]);
            versionedLoanBook.commit();
        }

        return versionedLoanBook;
    }

    /**
     * Shifts the {@code versionedLoanBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedLoanBook versionedLoanBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedLoanBook.undo();
        }
    }
}
