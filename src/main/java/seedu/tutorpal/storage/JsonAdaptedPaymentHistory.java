package seedu.tutorpal.storage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tutorpal.commons.exceptions.IllegalValueException;
import seedu.tutorpal.model.person.MonthlyPayment;
import seedu.tutorpal.model.person.PaymentHistory;

/**
 * Jackson-friendly version of {@link PaymentHistory}.
 */
class JsonAdaptedPaymentHistory {

    private final String joinDate;
    private final List<JsonAdaptedMonthlyPayment> monthlyPayments;

    /**
     * Constructs a {@code JsonAdaptedPaymentHistory} with the given payment history details.
     */
    @JsonCreator
    public JsonAdaptedPaymentHistory(@JsonProperty("joinDate") String joinDate,
                                     @JsonProperty("monthlyPayments") List<JsonAdaptedMonthlyPayment> monthlyPayments) {
        this.joinDate = joinDate;
        this.monthlyPayments = monthlyPayments;
    }

    /**
     * Converts a given {@code PaymentHistory} into this class for Jackson use.
     */
    public JsonAdaptedPaymentHistory(PaymentHistory source) {
        this.joinDate = source.getJoinDate().toString();
        this.monthlyPayments = source.getMonthlyPayments().entrySet().stream()
                .map(entry -> new JsonAdaptedMonthlyPayment(entry.getKey().toString(), entry.getValue()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted payment history object into the model's {@code PaymentHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted payment history.
     */
    public PaymentHistory toModelType() throws IllegalValueException {
        if (joinDate == null) {
            throw new IllegalValueException("Join date field is missing!");
        }

        LocalDate modelJoinDate;
        try {
            modelJoinDate = LocalDate.parse(joinDate);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid join date format");
        }

        Map<YearMonth, Boolean> modelMonthlyPayments = new HashMap<>();
        if (monthlyPayments != null) {
            for (JsonAdaptedMonthlyPayment adaptedPayment : monthlyPayments) {
                try {
                    MonthlyPayment monthlyPayment = adaptedPayment.toModelType();
                    modelMonthlyPayments.put(monthlyPayment.getMonth(), monthlyPayment.isPaid());
                } catch (Exception e) {
                    throw new IllegalValueException("Invalid monthly payment: " + e.getMessage());
                }
            }
        }

        return new PaymentHistory(modelJoinDate, modelMonthlyPayments);
    }
}
