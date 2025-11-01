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
import seedu.tutorpal.model.person.JoinDate;
import seedu.tutorpal.model.person.Name;
import seedu.tutorpal.model.person.PaymentHistory;
import seedu.tutorpal.model.person.Person;
import seedu.tutorpal.model.person.Phone;
import seedu.tutorpal.model.person.Role;
import seedu.tutorpal.model.person.Student;
import seedu.tutorpal.model.person.Tutor;

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
    private final JsonAdaptedPaymentHistory paymentHistory;
    private final String joinDate;
    private final JsonAdaptedAttendanceHistory attendanceHistory;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("role") String role,
            @JsonProperty("address") String address,
            @JsonProperty("classes") List<JsonAdaptedClass> classes,
            @JsonProperty("paymentHistory") JsonAdaptedPaymentHistory paymentHistory,
            @JsonProperty("joinDate") String joinDate,
            @JsonProperty("attendanceHistory") JsonAdaptedAttendanceHistory attendanceHistory) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        if (classes != null) {
            this.classes.addAll(classes);
        }
        this.paymentHistory = paymentHistory;
        this.joinDate = joinDate;
        this.attendanceHistory = attendanceHistory;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        role = source.getRole().toString().toLowerCase();
        address = source.getAddress().value;
        classes.addAll(source.getClasses().stream()
                .map(JsonAdaptedClass::new)
                .collect(Collectors.toList()));
        paymentHistory = new JsonAdaptedPaymentHistory(source.getPaymentHistory());
        joinDate = source.getJoinDate().toString();
        attendanceHistory = source.hasAttendanceHistory()
                ? new JsonAdaptedAttendanceHistory(source.getAttendanceHistory())
                : null;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Class> personClasses = new ArrayList<>();
        for (JsonAdaptedClass classItem : classes) {
            personClasses.add(classItem.toModelType());
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
        final Role modelRole = Role.fromString(role);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // JoinDate is mandatory
        if (joinDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JoinDate.class.getSimpleName()));
        }
        if (!JoinDate.isValidJoinDate(joinDate)) {
            throw new IllegalValueException(JoinDate.MESSAGE_CONSTRAINTS);
        }
        final JoinDate modelJoinDate = new JoinDate(joinDate);

        // Backward compatibility: if old JSON lacks paymentHistory, initialize from
        // current date
        final PaymentHistory modelPaymentHistory = (paymentHistory == null)
                ? new PaymentHistory(modelJoinDate.toLocalDate())
                : paymentHistory.toModelType();

        // Validate JoinDate sync between Person and PaymentHistory
        if (!modelPaymentHistory.getJoinDate().equals(modelJoinDate.toLocalDate())) {
            throw new IllegalValueException("Person's joinDate must match PaymentHistory's joinDate.");
        }

        final Set<Class> modelClasses = new HashSet<>(personClasses);

        // Create Student or Tutor based on role
        if (modelRole == Role.STUDENT) {
            // Validate Student must have exactly one class
            if (modelClasses.size() != 1) {
                throw new IllegalValueException(
                        String.format("Student must have exactly one class. Found %d class(es).",
                        modelClasses.size()));
            }

            // Preserve attendance history if present in JSON; otherwise initialize empty history
            final seedu.tutorpal.model.person.AttendanceHistory modelAttendanceHistory = (attendanceHistory == null)
                    ? null
                    : attendanceHistory.toModelType();

            // Validate JoinDate sync between Person and AttendanceHistory
            if (modelAttendanceHistory != null
                    && !modelAttendanceHistory.getJoinDate().equals(modelJoinDate)) {
                throw new IllegalValueException("Person's joinDate must match AttendanceHistory's joinDate.");
            }

            // Use constructor that accepts attendance history to avoid wiping existing records
            return new Student(modelName, modelPhone, modelEmail, modelAddress,
                    modelClasses, modelJoinDate, modelAttendanceHistory, modelPaymentHistory);
        } else {
            // Validate Tutor must have at least one class
            if (modelClasses.isEmpty()) {
                throw new IllegalValueException("Tutor must have at least one class.");
            }

            return new Tutor(modelName, modelPhone, modelEmail, modelAddress,
                    modelClasses, modelJoinDate, modelPaymentHistory);
        }
    }

}
