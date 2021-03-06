package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.loan.Loan;

/**
 * An Immutable LoanBook that is serializable to XML format
 */
@XmlRootElement(name = "loanbook")
public class XmlSerializableLoanBook {

    public static final String MESSAGE_DUPLICATE_LOAN = "Loans list contains duplicate loan(s).";

    @XmlElement
    private List<XmlAdaptedLoan> loans;

    /**
     * Creates an empty XmlSerializableLoanBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLoanBook() {
        loans = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableLoanBook(ReadOnlyLoanBook src) {
        this();
        loans.addAll(src.getLoanList().stream().map(XmlAdaptedLoan::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loanbook into the model's {@code LoanBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedLoan}.
     */
    public LoanBook toModelType() throws IllegalValueException {
        LoanBook loanBook = new LoanBook();
        for (XmlAdaptedLoan p : loans) {
            Loan loan = p.toModelType();
            if (loanBook.hasLoan(loan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOAN);
            }
            loanBook.addLoan(loan);
        }
        return loanBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableLoanBook)) {
            return false;
        }
        return loans.equals(((XmlSerializableLoanBook) other).loans);
    }
}
