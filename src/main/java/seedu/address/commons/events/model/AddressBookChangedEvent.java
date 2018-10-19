package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyLoanBook;

/** Indicates the LoanBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyLoanBook data;

    public AddressBookChangedEvent(ReadOnlyLoanBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of loans " + data.getLoanList().size();
    }
}
