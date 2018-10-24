package seedu.address.model.loan;

import java.util.function.Function;

/**
 * Represents a Loan's phone number in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends DataField<String> implements Censor {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        super(MESSAGE_PHONE_CONSTRAINTS, Phone::isValidPhone, Function.identity(), phone);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String objString) {
        return objString.matches("\\d{3,}");
    }

    @Override
    public String getCensored() {
        String output = this.value;
        output = output.charAt(0) + "xxxxx" + output.substring(output.length() - 2);
        return output;
    }

}
