package seedu.tutorpal.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tutorpal.commons.util.AppUtil.checkArgument;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Person's join date in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidJoinDate(String)}
 */
public final class JoinDate {

    public static final String MESSAGE_CONSTRAINTS = "Join dates should be in the format dd-MM-yyyy, "
            + "must be a valid date from year 2000 onwards, and cannot be in the future!";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private final LocalDate value; //immutable

    /**
     * Constructs a {@code JoinDate}.
     *
     * @param joinDate A valid LocalDate object.
     */
    public JoinDate(LocalDate joinDate) {
        requireNonNull(joinDate);
        this.value = joinDate;
    }

    /**
     * Constructs a {@code JoinDate} from a string. For editing.
     *
     * @param joinDate A valid join date string in dd-MM-yyyy format.
     */
    public JoinDate(String joinDate) {
        requireNonNull(joinDate);
        checkArgument(isValidJoinDate(joinDate), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(joinDate, DATE_FORMATTER);
    }

    /**
     * Gives current date as JoinDate
     */
    public static JoinDate now() {
        return new JoinDate(LocalDate.now());
    }

    /**
     * Testable version of now
     */
    public static JoinDate now(Clock nowClock) {
        return new JoinDate(LocalDate.now(nowClock));
    }

    /**
     * Returns true if a given string is a valid join date.
     * Join date must be from year 2000 onwards and not in the future.
     */
    public static boolean isValidJoinDate(String test) {
        requireNonNull(test);
        try {
            LocalDate date = LocalDate.parse(test, DATE_FORMATTER);
            // Must be year 2000 or later, and not in the future
            return date.getYear() >= 2000 && !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts joinDate to join week.
     * i.e. week containing join date.
     * @return WeeklyAttendance containing joinDate
     */
    public WeeklyAttendance getJoinWeek() {
        return WeeklyAttendance.at(this.value);
    }

    /**
     * Checks if join date is after given LocalDate date.
     * @return Boolean representing if this is true
     */
    public boolean isAfter(LocalDate date) {
        return this.value.isAfter(date);
    }

    /**
     * Converts this join date to a YearMonth.
     * @return YearMonth representation of this join date
     */
    public YearMonth toYearMonth() {
        return YearMonth.from(this.value);
    }

    /**
     * Returns the underlying LocalDate value.
     */
    public LocalDate toLocalDate() {
        return this.value;
    }

    @Override
    public String toString() {
        // Validating invariant. value should not be mutable, and can never be null.
        assert value != null : "JoinDate value should not be null";
        return value.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        // Validating invariant. value should not be mutable.
        assert value != null : "JoinDate value should not be null";
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JoinDate)) {
            return false;
        }

        JoinDate otherJoinDate = (JoinDate) other;
        return value.equals(otherJoinDate.value);
    }

    @Override
    public int hashCode() {
        // Validating invariant. value should not be mutable.
        assert value != null : "JoinDate value should not be null";
        return value.hashCode();
    }
}
