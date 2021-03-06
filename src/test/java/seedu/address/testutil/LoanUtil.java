package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditLoanDescriptor;
import seedu.address.model.loan.Loan;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Loan.
 */
public class LoanUtil {

    /**
     * Returns an add command string for adding the {@code loan}.
     */
    public static String getAddCommand(Loan loan) {
        return AddCommand.COMMAND_WORD + " " + getLoanDetails(loan);
    }

    /**
     * Returns the part of command string for the given {@code loan}'s details.
     */
    public static String getLoanDetails(Loan loan) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + loan.getName().value + " ");
        sb.append(PREFIX_PHONE + loan.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + loan.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + loan.getAddress().value + " ");
        loan.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.value + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditLoanDescriptor}'s details.
     */
    public static String getEditLoanDescriptorDetails(EditLoanDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.value).append(" "));
            }
        }
        return sb.toString();
    }
}
