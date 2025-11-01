package seedu.tutorpal.model.person;

import static seedu.tutorpal.model.person.Role.TUTOR;

import java.time.Clock;
import java.util.Set;

import seedu.tutorpal.commons.util.ToStringBuilder;

/**
 * Subtype of Person, representing a Tutor
 */
public class Tutor extends Person {
    public static final String PERSON_WORD = TUTOR.toString();

    /**
     * For Add Command and Edit Command
     * Public constructor with explicit joinDate (uses system clock for defaults).
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Set<Class> classes,
                 JoinDate joinDate) {
        this(name, phone, email, address, classes, joinDate, null, Clock.systemDefaultZone(),
                new PaymentHistory(joinDate.toLocalDate()));
    }

    /**
     * Public constructor with explicit joinDate and paymentHistory.
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Set<Class> classes,
                 JoinDate joinDate, PaymentHistory paymentHistory) {
        this(name, phone, email, address, classes, joinDate, null, Clock.systemDefaultZone(),
                paymentHistory);
    }

    /**
     * TODO Added for Payment extension. Please remove and simplify constructors if not needed.
     * Testing version of public constructor. Allows injecting of clock to control "now".
     */
    protected Tutor(Name name, Phone phone, Email email, Address address, Set<Class> classes,
                    JoinDate joinDate, Clock nowClock) {
        this(name, phone, email, address, classes, joinDate, null, nowClock,
                new PaymentHistory(joinDate.toLocalDate()));
    }

    /**
     * Private constructor: inject a Clock to control "now".
     */
    private Tutor(Name name, Phone phone, Email email, Address address, Set<Class> classes,
                  JoinDate joinDate, AttendanceHistory attendanceHistory, Clock nowClock,
                  PaymentHistory paymentHistory) {
        super(name, phone, email, address, classes, joinDate, paymentHistory, nowClock);
        validateAttendanceHistoryRules(attendanceHistory);
    }

    @Override
    public Role getRole() {
        return Role.TUTOR;
    }

    @Override
    public boolean hasAttendanceHistory() {
        return false;
    }

    @Override
    public AttendanceHistory getAttendanceHistory() {
        throw new IllegalStateException(String.format(Person.MESSAGE_INVALID_ATTENDANCE_RETRIEVAL, Tutor.PERSON_WORD));
    }

    private static void validateAttendanceHistoryRules(AttendanceHistory attendanceHistory) {
        // Tutors must not have attendance history
        if (attendanceHistory != null) {
            throw new IllegalArgumentException(String.format(Person.MESSAGE_NO_ATTENDANCE_HISTORY, Tutor.PERSON_WORD));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tutor)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        Tutor o = (Tutor) other;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", super.toString())
                .toString();
    }
}
