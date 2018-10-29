package loanbook.testutil;

import loanbook.model.LoanBook;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanIdManager;

/**
 * A utility class containing a factory for a {@code LoanBook} object to be used only in remind tests.
 */
public class LoanBookForRemindTest {
    /**
     * Returns an {@code LoanBook} with all the bikes and loans only for the remind tests.
     */
    public static LoanBook getLoanBookForRemindTest() {
        LoanBook lb = new LoanBook();
        for (Bike bike : TypicalBikes.getTypicalBikes()) {
            lb.addBike(bike);
        }
        for (Loan loan : LoansForRemindTest.getLoansForRemindTest()) {
            lb.addLoan(loan);
        }

        int lastUsedId = lb.getLoanList().size() + LoanId.MINIMUM_ID - 1;
        LoanId lastUsedLoanId = LoanId.isValidLoanId(lastUsedId) ? LoanId.fromInt(lastUsedId) : null;
        lb.setLoanIdManager(new LoanIdManager(lastUsedLoanId));

        return lb;
    }
}
