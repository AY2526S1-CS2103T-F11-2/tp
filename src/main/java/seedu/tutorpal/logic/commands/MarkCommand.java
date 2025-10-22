package seedu.tutorpal.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tutorpal.commons.core.index.Index;
import seedu.tutorpal.commons.util.ToStringBuilder;
import seedu.tutorpal.logic.Messages;
import seedu.tutorpal.logic.commands.exceptions.CommandException;
import seedu.tutorpal.model.Model;
import seedu.tutorpal.model.person.Person;

/**
 * Marks the attendance of a student in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Marked attendance for: %1$s";
    public static final String MESSAGE_ALREADY_MARKED = "%1$s is already marked.";

    private final Index index;

    /**
     * Creates a MarkCommand to mark attendance of the specified person.
     */
    public MarkCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(index.getZeroBased());

        if (personToMark.isMarked()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_MARKED, personToMark.getName()));
        }

        Person markedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getEmail(),
                personToMark.getRole(),
                personToMark.getAddress(),
                personToMark.getClasses(),
                personToMark.getTags(),
                personToMark.getPaymentHistory(),
                true
        );

        model.setPerson(personToMark, markedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, markedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherCommand = (MarkCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
