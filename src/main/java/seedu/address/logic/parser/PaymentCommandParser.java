package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PaymentCommand object.
 */
public class PaymentCommandParser implements Parser<PaymentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaymentCommand
     * and returns a PaymentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaymentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PAYMENT_STATUS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_PAYMENT_STATUS).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
        }

        String paymentStatus = argMultimap.getValue(PREFIX_PAYMENT_STATUS).get().toLowerCase();

        if (!isValidPaymentStatus(paymentStatus)) {
            throw new ParseException("Invalid payment status. Please use: paid, unpaid, or overdue");
        }

        return new PaymentCommand(index, paymentStatus);
    }

    /**
     * Returns true if the given string is a valid payment status.
     */
    private boolean isValidPaymentStatus(String status) {
        return status.equals("paid") || status.equals("unpaid") || status.equals("overdue");
    }
}
