package seedu.address.logic.commands;

import seedu.address.logic.commands.Command;
import seedu.address.model.Model;

/**
 * Mark something.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_MARK_ACKNOWLEDGEMENT = "Mark as paid successfully.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(_MARK_ACKNOWLEDGEMENT));
    }

}
