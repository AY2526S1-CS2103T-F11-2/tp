package seedu.tutorpal.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tutorpal.logic.Messages.MESSAGE_CONFLICTING_FILTERS;
import static seedu.tutorpal.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutorpal.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tutorpal.logic.commands.ListCommand;
import seedu.tutorpal.logic.parser.exceptions.ParseException;
import seedu.tutorpal.model.person.ClassContainsKeywordsPredicate;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_returnsListCommand() throws ParseException {
        ListCommand command = parser.parse("");
        assertEquals(new ListCommand(), command);
    }

    @Test
    public void parse_validClassFilter_returnsListCommandWithPredicate() throws ParseException {
        ListCommand command = parser.parse(" c/s4mon1600");
        assertEquals(new ListCommand(new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600"))), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), () -> parser.parse(" invalid"));
    }

    @Test
    public void parse_emptyClassPrefix_returnsAllStudents() throws ParseException {
        ListCommand command = parser.parse(" c/");
        assertEquals(new ListCommand(new ClassContainsKeywordsPredicate(Arrays.asList(""))), command);
    }

    @Test
    public void parse_conflictingFilters_throwsParseException() {
        assertThrows(ParseException.class,
            MESSAGE_CONFLICTING_FILTERS, () -> parser.parse(" c/s4mon1600 tu/Siang Jun"));
    }

    @Test
    public void parse_conflictingFiltersReverseOrder_throwsParseException() {
        assertThrows(ParseException.class,
            MESSAGE_CONFLICTING_FILTERS, () -> parser.parse(" tu/Siang Jun c/s4mon1600"));
    }
}

