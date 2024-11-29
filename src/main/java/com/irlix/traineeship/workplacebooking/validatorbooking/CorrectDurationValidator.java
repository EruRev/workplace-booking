package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CorrectDurationValidator extends BookingValidator {
    private final int MIN_DURATION = 10;
    @Override
    public boolean check(BookingEntity bookingEntity) {
        int bookingMinutes = Math.toIntExact(Duration.between(bookingEntity.getBookingStart(),
                bookingEntity.getBookingEnd()).toMinutes());
        if(bookingMinutes < MIN_DURATION) {
            throw new WrongBookParametersException("The booking time should not be less than 10 minutes");
        }
        return  checkNext(bookingEntity);
    }
}
