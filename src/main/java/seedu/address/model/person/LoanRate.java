package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Loan's rate in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate}
 */
public class LoanRate {

    public static final String LOANRATE_VALIDATION_REGEX = "([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])";
    public static final String MESSAGE_LOANRATE_CONSTRAINTS =
            "Rate should be greater than 0 and have at most 2 decimal places. The last decimal place cannot be 0.";

    public final double rate;

    /**
     * Constructs a {@code LoanRate}.
     *
     * @param rate A valid rate.
     */
    LoanRate(double rate) {
        if (Math.round(rate) - rate == 0) {
            checkArgument(isValidRate(String.valueOf((int) rate)), MESSAGE_LOANRATE_CONSTRAINTS);
        } else {
            checkArgument(isValidRate(String.valueOf(rate)), MESSAGE_LOANRATE_CONSTRAINTS);
        }
        this.rate = rate;
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches(LOANRATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (Math.round(rate) - rate == 0) {
            return String.valueOf((int) rate);
        } else {
            return String.valueOf(rate);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRate // instanceof handles nulls
                && rate == ((LoanRate) other).rate); // state check
    }

    @Override
    public int hashCode() {
        return Double.hashCode(rate);
    }
}
