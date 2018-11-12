package loanbook.model.loan;

/**
 * Represents a Loan's rate in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate}
 */
public class LoanRate extends DataField<Double> {

    public static final String MESSAGE_LOANRATE_CONSTRAINTS =
            "Rate should be a valid real number that is not less than 0 and have at most 2 decimal places."
                    + " e.g. 3.5 is a valid loan rate, but 03.5 is not.";

    /**
     * Constructs a {@code LoanRate}.
     *
     * @param rate A valid rate.
     */
    public LoanRate(String rate) {
        super(MESSAGE_LOANRATE_CONSTRAINTS, LoanRate::isValidRate, Double::parseDouble, rate);
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches("^(([1-9]\\d*|0)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$");
    }

    public double calculateCost (long minutes) {
        return value * minutes / 60;
    }
}
