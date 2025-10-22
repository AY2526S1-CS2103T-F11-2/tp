package seedu.tutorpal.model.person;

import static seedu.tutorpal.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.tutorpal.commons.util.ToStringBuilder;
import seedu.tutorpal.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Role role;
    private final Address address;
    private final Set<Class> classes = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final PaymentHistory paymentHistory;
    private final boolean isMarked;

    /**
     * Constructor for Person.
     * @param name name of the person.
     * @param phone phone number of the person.
     * @param email email of the person.
     * @param role role of the person, either student or tutor.
     * @param address address of the person.
     * @param classes class the student belong to, or the tutor is teaching.
     * @param tags any additional remarks.
     * @param isMarked whether attendance is marked.
     */
    public Person(Name name, Phone phone, Email email, Role role, Address address,
                  Set<Class> classes, Set<Tag> tags, boolean isMarked) {
        requireAllNonNull(name, phone, email, role, address, classes, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.classes.addAll(classes);
        this.tags.addAll(tags);
        this.paymentHistory = new PaymentHistory(LocalDate.now());
        this.isMarked = isMarked;
    }

    /**
     * Constructor with PaymentHistory (for editing existing persons).
     */
    public Person(Name name, Phone phone, Email email, Role role, Address address,
                  Set<Class> classes, Set<Tag> tags, PaymentHistory paymentHistory, boolean isMarked) {
        requireAllNonNull(name, phone, email, role, address, classes, tags, paymentHistory);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.classes.addAll(classes);
        this.tags.addAll(tags);
        this.paymentHistory = paymentHistory;
        this.isMarked = isMarked;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable class set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Class> getClasses() {
        return Collections.unmodifiableSet(classes);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the payment status of this person.
     */
    public Payment getPaymentStatus() {
        return new Payment(paymentHistory);
    }

    /**
     * Returns the payment history of this person.
     */
    public PaymentHistory getPaymentHistory() {
        return paymentHistory;
    }

    /**
     * Returns the join date of this person.
     */
    public LocalDate getJoinDate() {
        return paymentHistory.getJoinDate();
    }

    /**
     * Returns whether this person is marked for attendance.
     */
    public boolean isMarked() {
        return isMarked;
    }

    /**
     * Returns true if both persons have the same name and phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && role.equals(otherPerson.role)
                && address.equals(otherPerson.address)
                && classes.equals(otherPerson.classes)
                && tags.equals(otherPerson.tags)
                && paymentHistory.equals(otherPerson.paymentHistory)
                && isMarked == otherPerson.isMarked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, role, address, classes, tags, paymentHistory, isMarked);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("address", address)
                .add("classes", classes)
                .add("tags", tags)
                .add("paymentHistory", paymentHistory)
                .add("isMarked", isMarked)
                .toString();
    }
}
