package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemarkCommand(null, new Remark("test")));
    }

    @Test
    public void constructor_nullRemark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemarkCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void equals() {
        RemarkCommand remarkCommand = new RemarkCommand(Index.fromOneBased(1), new Remark("test"));
        RemarkCommand remarkCommandCopy = new RemarkCommand(Index.fromOneBased(1), new Remark("test"));
        RemarkCommand differentRemarkCommand = new RemarkCommand(Index.fromOneBased(2), new Remark("test"));
        RemarkCommand differentRemarkCommand2 = new RemarkCommand(Index.fromOneBased(1), new Remark("different"));

        // same object -> returns true
        assertTrue(remarkCommand.equals(remarkCommand));

        // same values -> returns true
        assertTrue(remarkCommand.equals(remarkCommandCopy));

        // different types -> returns false
        assertFalse(remarkCommand.equals(1));

        // null -> returns false
        assertFalse(remarkCommand.equals(null));

        // different index -> returns false
        assertFalse(remarkCommand.equals(differentRemarkCommand));

        // different remark -> returns false
        assertFalse(remarkCommand.equals(differentRemarkCommand2));
    }

    @Test
    public void testHashCode() {
        RemarkCommand remarkCommand = new RemarkCommand(Index.fromOneBased(1), new Remark("test"));
        RemarkCommand remarkCommandCopy = new RemarkCommand(Index.fromOneBased(1), new Remark("test"));
        RemarkCommand differentRemarkCommand = new RemarkCommand(Index.fromOneBased(2), new Remark("test"));

        // same values -> same hash code
        assertEquals(remarkCommand.hashCode(), remarkCommandCopy.hashCode());

        // different values -> different hash code
        assertFalse(remarkCommand.hashCode() == differentRemarkCommand.hashCode());
    }
}
