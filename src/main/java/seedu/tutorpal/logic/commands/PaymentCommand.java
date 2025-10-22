package seedu.tutorpal.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.List;

import seedu.tutorpal.commons.core.index.Index;
import seedu.tutorpal.commons.util.ToStringBuilder;
import seedu.tutorpal.logic.Messages;
import seedu.tutorpal.logic.commands.exceptions.CommandException;
import seedu.tutorpal.model.Model;
import seedu.tutorpal.model.person.PaymentHistory;
import seedu.tutorpal.model.person.Person;

/**
 * Updates the payment status of a student in the address book.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific month's payment as paid for the student identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer) m/MM-yyyy\n"
            + "Example: " + COMMAND_WORD + " 1 m/01-2024";

    public static final String MESSAGE_SUCCESS = "Payment for %1$s for %2$s has been marked as paid.";
    public static final String MESSAGE_NOT_STUDENT =
            "Index belongs to a tutor. Please provide an index tied to a student instead";
    public static final String MESSAGE_MONTH_BEFORE_JOIN =
            "Cannot mark payment for month before student's join date (%1$s)";
    public static final String MESSAGE_FUTURE_MONTH =
            "Cannot mark payment for future month";

    private final Index index;
    private final YearMonth month;

    /**
     * Creates a PaymentCommand to mark the specified month's payment as paid for the specified person.
     */
    public PaymentCommand(Index index, YearMonth month) {
        requireNonNull(index);
        requireNonNull(month);
        this.index = index;
        this.month = month;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Check if the person is a student
        if (personToEdit.getRole().value.equals("tutor")) {
            throw new CommandException(MESSAGE_NOT_STUDENT);
        }

        // Validate month constraints
        YearMonth joinMonth = YearMonth.from(personToEdit.getJoinDate());
        if (month.isBefore(joinMonth)) {
            throw new CommandException(String.format(MESSAGE_MONTH_BEFORE_JOIN, joinMonth));
        }

        if (month.isAfter(YearMonth.now())) {
            throw new CommandException(MESSAGE_FUTURE_MONTH);
        }

        Person editedPerson = createPersonWithUpdatedPayment(personToEdit, month);
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                editedPerson.getName(), month));
    }

    /**
     * Creates and returns a {@code Person} with the updated payment status for the specified month.
     */
    private Person createPersonWithUpdatedPayment(Person person, YearMonth month) {
        PaymentHistory updatedPaymentHistory = person.getPaymentHistory().markMonthAsPaid(month);
        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getRole(), person.getAddress(), person.getClasses(),
                person.getTags(), updatedPaymentHistory, person.isMarked());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaymentCommand)) {
            return false;
        }

        PaymentCommand otherCommand = (PaymentCommand) other;
        return index.equals(otherCommand.index)
                && month.equals(otherCommand.month);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("month", month)
                .toString();
    }
}
