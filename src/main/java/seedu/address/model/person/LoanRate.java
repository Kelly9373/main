package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Loan's rate in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate}
 */
public class LoanRate {

    public static final String LOANRATE_VALIDATION_REGEX = "([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])";
    public static final String MESSAGE_LOANRATE_CONSTRAINTS =
            "Rate should only contain numbers. It should be greater than 0 and it can have at most 2 decimal places";

    public final String rate;

    /**
     * Constructs a {@code LoanRate}.
     *
     * @param rate A valid rate.
     */
    LoanRate(String rate) {
        requireNonNull(rate);
        checkArgument(isValidRate(rate), MESSAGE_LOANRATE_CONSTRAINTS);
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
        return rate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRate // instanceof handles nulls
                && rate.equals(((LoanRate) other).rate)); // state check
    }

    @Override
    public int hashCode() {
        return rate.hashCode();
    }
}
