package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.LoanCardHandle;
import guitests.guihandles.LoanListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.loan.Loan;
import seedu.address.ui.LoanCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(LoanCardHandle expectedCard, LoanCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard
                .getTags()
                .forEach(tag ->
                        assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLoan}.
     */
    public static void assertCardDisplaysLoan(Loan expectedLoan, LoanCardHandle actualCard) {
        assertEquals(expectedLoan.getName().value, actualCard.getName());
        assertEquals(expectedLoan.getPhone().value, actualCard.getPhone());
        assertEquals(expectedLoan.getEmail().value, actualCard.getEmail());
        assertEquals(expectedLoan.getAddress().value, actualCard.getAddress());
        assertTagsEqual(expectedLoan, actualCard);
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code LoanCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see LoanCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "teal";
        case "colleagues":
        case "neighbours":
            return "yellow";
        case "family":
        case "friend":
            return "orange";
        case "friends":
            return "brown";
        case "husband":
            return "grey";
        default:
            throw new AssertionError(tagName + " does not have a color assigned.");
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Loan expectedLoan, LoanCardHandle actualCard) {
        List<String> expectedTags = expectedLoan.getTags().stream()
                .map(tag -> tag.value).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag -> assertEquals(
                Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                actualCard.getTagStyleClasses(tag)
        ));
    }

    /**
     * Asserts that the list in {@code loanListPanelHandle} displays the details of {@code loans} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LoanListPanelHandle loanListPanelHandle, Loan... loans) {
        for (int i = 0; i < loans.length; i++) {
            loanListPanelHandle.navigateToCard(i);
            assertCardDisplaysLoan(loans[i], loanListPanelHandle.getLoanCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code loanListPanelHandle} displays the details of {@code loans} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LoanListPanelHandle loanListPanelHandle, List<Loan> loans) {
        assertListMatching(loanListPanelHandle, loans.toArray(new Loan[0]));
    }

    /**
     * Asserts the size of the list in {@code loanListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(LoanListPanelHandle loanListPanelHandle, int size) {
        int numberOfLoans = loanListPanelHandle.getListSize();
        assertEquals(size, numberOfLoans);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
