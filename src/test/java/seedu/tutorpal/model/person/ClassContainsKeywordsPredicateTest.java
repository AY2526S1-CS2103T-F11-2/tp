package seedu.tutorpal.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tutorpal.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.tutorpal.testutil.PersonBuilder;

public class ClassContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ClassContainsKeywordsPredicate firstPredicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600"));
        ClassContainsKeywordsPredicate secondPredicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        ClassContainsKeywordsPredicate thirdPredicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4wed1400"));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nullPerson_throwsNullPointerException() {
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600"));
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_classContainsKeywords_returnsTrue() {
        // One keyword matching
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("s4mon1600").build()));

        // Multiple keywords, one matching
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon1600", "s4wed1400"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("s4mon1600").build()));

        // Partial keyword should match
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4mon"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("s4mon1600").build()));
    }

    @Test
    public void test_classDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withClasses("s4mon1600").build()));

        // Non-matching keyword
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("s4wed1400"));
        assertFalse(predicate.test(new PersonBuilder().withClasses("s4mon1600").build()));

        // Keywords match name but not class
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withClasses("s4mon1600").build()));
    }
}

