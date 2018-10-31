package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Before;
import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListBikesCommand.
 */
public class ListBikesCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListBikesCommand(), model, commandHistory,
                ListBikesCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
