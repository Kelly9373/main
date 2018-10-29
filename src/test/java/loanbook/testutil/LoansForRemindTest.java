package loanbook.testutil;

import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;

/**
 * A utility class containing a list of {@code Loan} objects to be used in Remind tests.
 */
public class LoansForRemindTest {

    public static final Loan AP = new LoanBuilder().withName("Alice Pauline")
            .withNric("S0848937H")
            .withPhone("94351253")
            .withBike(VALID_NAME_BIKE1)
            .withLoanRate("1.1")
            .withEmail("alice@example.com")
            .withLoanStartTime("12:33")
            .withLoanEndTime("23:54")
            .withLoanStatus("RETURNED")
            .withTags("friends").build();
    public static final Loan BM = new LoanBuilder().withName("Benson Meier")
            .withNric("F1342714M")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("0.15")
            .withLoanStartTime("2017-10-12 06:08")
            .withLoanEndTime("2017-10-12 23:54")
            .withLoanStatus("DELETED")
            .withTags("owesMoney", "friends").build();

    private LoansForRemindTest() {} // prevents instantiation

    public static List<Loan> getLoansForRemindTest() {
        return new ArrayList<>(Arrays.asList(AP, BM));
    }
}
