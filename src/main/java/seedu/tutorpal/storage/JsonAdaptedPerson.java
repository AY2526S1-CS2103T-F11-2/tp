package seedu.tutorpal.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tutorpal.commons.exceptions.IllegalValueException;
import seedu.tutorpal.model.person.Address;
import seedu.tutorpal.model.person.Class;
import seedu.tutorpal.model.person.Email;
import seedu.tutorpal.model.person.Name;
import seedu.tutorpal.model.person.PaymentHistory;
import seedu.tutorpal.model.person.Person;
import seedu.tutorpal.model.person.Phone;
import seedu.tutorpal.model.person.Role;
import seedu.tutorpal.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String role;
    private final String address;
    private final List<JsonAdaptedClass> classes = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final JsonAdaptedPaymentHistory paymentHistory;
    private final Boolean isMarked;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("role") String role,
                             @JsonProperty("address") String address,
                             @JsonProperty("classes") List<JsonAdaptedClass> classes,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("paymentHistory") JsonAdaptedPaymentHistory paymentHistory,
                             @JsonProperty("isMarked") Boolean isMarked) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        if (classes != null) {
            this.classes.addAll(classes);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.paymentHistory = paymentHistory;
        this.isMarked = isMarked;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        role = source.getRole().value;
        address = source.getAddress().value;
        classes.addAll(source.getClasses().stream()
                .map(JsonAdaptedClass::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        paymentHistory = new JsonAdaptedPaymentHistory(source.getPaymentHistory());
        isMarked = source.isMarked();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Class> personClasses = new ArrayList<>();
        for (JsonAdaptedClass classItem : classes) {
            personClasses.add(classItem.toModelType());
        }

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Backward compatibility: if old JSON lacks paymentHistory, initialize from current date
        final PaymentHistory modelPaymentHistory = (paymentHistory == null)
                ? new PaymentHistory(java.time.LocalDate.now())
                : paymentHistory.toModelType();

        // Default to false if isMarked is null (for backward compatibility)
        final boolean modelIsMarked = (isMarked != null) ? isMarked : false;

        final Set<Class> modelClasses = new HashSet<>(personClasses);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelRole, modelAddress,
                modelClasses, modelTags, modelPaymentHistory, modelIsMarked);
    }

}

