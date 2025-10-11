package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Class;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Role("student"), new Address("Blk 30 Geylang Street 29, #06-40"),
                getClassSet("s4mon1600"), getTagSet("friends"), new Payment("unpaid"),
                new Attendance()),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Role("student"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getClassSet("s3tue1400", "s3fri1000"), getTagSet("colleagues", "friends"),
                new Payment("unpaid"), new Attendance()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Role("tutor"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getClassSet("s1mon0900", "s2wed1400"), getTagSet("neighbours"),
                new Payment("unpaid"), new Attendance()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Role("student"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getClassSet("s5thu1600"), getTagSet("family"), new Payment("unpaid"),
                new Attendance()),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Role("student"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getClassSet("s2mon1000"), getTagSet("classmates"), new Payment("unpaid"),
                new Attendance()),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Role("tutor"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getClassSet("s4mon1600", "s5wed1400"), getTagSet("colleagues"),
                new Payment("unpaid"), new Attendance())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a class set containing the list of strings given.
     */
    public static Set<Class> getClassSet(String... strings) {
        return Arrays.stream(strings)
                .map(Class::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
