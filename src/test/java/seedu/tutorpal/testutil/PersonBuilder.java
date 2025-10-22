package seedu.tutorpal.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.tutorpal.model.person.Address;
import seedu.tutorpal.model.person.Class;
import seedu.tutorpal.model.person.Email;
import seedu.tutorpal.model.person.Name;
import seedu.tutorpal.model.person.Person;
import seedu.tutorpal.model.person.Phone;
import seedu.tutorpal.model.person.Role;
import seedu.tutorpal.model.tag.Tag;
import seedu.tutorpal.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROLE = "student";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CLASS = "s4mon1600";
    public static final String DEFAULT_PAYMENT_STATUS = "unpaid"; // kept for backward compatibility; unused now

    private Name name;
    private Phone phone;
    private Email email;
    private Role role;
    private Address address;
    private Set<Class> classes;
    private Set<Tag> tags;
    // Payment is now derived from PaymentHistory; no explicit field needed

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        role = new Role(DEFAULT_ROLE);
        address = new Address(DEFAULT_ADDRESS);
        classes = new HashSet<>();
        classes.add(new Class(DEFAULT_CLASS));
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        role = personToCopy.getRole();
        address = personToCopy.getAddress();
        classes = new HashSet<>(personToCopy.getClasses());
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Parses the {@code classes} into a {@code Set<Class>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withClasses(String ... classes) {
        this.classes = SampleDataUtil.getClassSet(classes);
        return this;
    }

    // Backward-compatibility no-op: tests that call withPayment() can remain unchanged
    public PersonBuilder withPayment(String paymentStatus) {
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, role, address, classes, tags, false);
    }

}
