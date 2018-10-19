package seedu.address.testutil;

import seedu.address.model.LoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code LoanBook ab = new AddressBookBuilder().withBike("BiK001").withLoan("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private LoanBook loanBook;

    public AddressBookBuilder() {
        loanBook = new LoanBook();
    }

    public AddressBookBuilder(LoanBook loanBook) {
        this.loanBook = loanBook;
    }

    /**
     * Adds a new {@code Bike} to the {@code LoanBook} that we are building.
     */
    public AddressBookBuilder withBike(Bike bike) {
        loanBook.addBike(bike);
        return this;
    }

    /**
     * Adds a new {@code Loan} to the {@code LoanBook} that we are building.
     */
    public AddressBookBuilder withLoan(Loan loan) {
        loanBook.addLoan(loan);
        return this;
    }

    public LoanBook build() {
        return loanBook;
    }
}
