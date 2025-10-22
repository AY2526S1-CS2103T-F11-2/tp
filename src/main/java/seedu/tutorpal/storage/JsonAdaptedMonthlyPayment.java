package seedu.tutorpal.storage;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tutorpal.commons.exceptions.IllegalValueException;
import seedu.tutorpal.model.person.MonthlyPayment;

/**
 * Jackson-friendly version of {@link MonthlyPayment}.
 */
public class JsonAdaptedMonthlyPayment {
    private final String month;
    private final boolean isPaid;
    /**
     * Constructs a {@code JsonAdaptedMonthlyPayment} with the given monthly payment details.
     */
    @JsonCreator
    public JsonAdaptedMonthlyPayment(@JsonProperty("month") String month,
                                      @JsonProperty("isPaid") boolean isPaid) {
        this.month = month;
        this.isPaid = isPaid;
    }
    /**
     * Converts a given {@code MonthlyPayment} into this class for Jackson use.
     */
    public JsonAdaptedMonthlyPayment(MonthlyPayment source) {
        this.month = source.getMonth().toString();
        this.isPaid = source.isPaid();
    }
    /**
     * Converts this Jackson-friendly adapted monthly payment object into the model's {@code MonthlyPayment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted monthly payment.
     */
    public MonthlyPayment toModelType() throws IllegalValueException {
        if (month == null) {
            throw new IllegalValueException("Month field is missing!");
        }
        YearMonth modelMonth;
        try {
            modelMonth = YearMonth.parse(month);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid month format: " + month);
        }
        return new MonthlyPayment(modelMonth, isPaid);
    }
}
