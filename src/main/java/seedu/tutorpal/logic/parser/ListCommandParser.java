package seedu.tutorpal.logic.parser;

import static seedu.tutorpal.logic.Messages.MESSAGE_CONFLICTING_FILTERS;
import static seedu.tutorpal.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutorpal.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.tutorpal.logic.parser.CliSyntax.PREFIX_TUTOR;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.tutorpal.logic.commands.ListCommand;
import seedu.tutorpal.logic.parser.exceptions.ParseException;
import seedu.tutorpal.model.person.ClassContainsKeywordsPredicate;
import seedu.tutorpal.model.person.StudentBelongsToTutorPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    private static Logger logger = Logger.getLogger(ListCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing ListCommand with args: \"" + args + "\"");
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS, PREFIX_TUTOR);

        // If no arguments provided, return command to list all persons
        if (args.trim().isEmpty()) {
            return new ListCommand();
        }

        // Check for conflicting filters
        boolean hasClassFilter = argMultimap.getValue(PREFIX_CLASS).isPresent();
        boolean hasTutorFilter = argMultimap.getValue(PREFIX_TUTOR).isPresent();
        if (hasClassFilter && hasTutorFilter) {
            logger.log(Level.WARNING, "Conflicting filters: both c/ and tu/ provided");
            throw new ParseException(MESSAGE_CONFLICTING_FILTERS);
        }

        // Check if class prefix is provided
        if (hasClassFilter) {
            String classKeyword = argMultimap.getValue(PREFIX_CLASS).get().trim();
            logger.log(Level.FINE, "List filter selected: class=" + classKeyword);
            List<String> keywords = new ArrayList<>();
            keywords.add(classKeyword);
            return new ListCommand(new ClassContainsKeywordsPredicate(keywords));
        } else if (hasTutorFilter) {
            String tutorName = argMultimap.getValue(PREFIX_TUTOR).get().trim();
            if (tutorName.isEmpty()) {
                logger.log(Level.WARNING, "Empty tutor name after trim");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            logger.log(Level.FINE, "List filter selected: tutor=" + tutorName);
            List<String> tutorNames = new ArrayList<>();
            tutorNames.add(tutorName);
            return new ListCommand(new StudentBelongsToTutorPredicate(tutorNames));
        }

        // If arguments are provided but no valid prefix, throw exception
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}

