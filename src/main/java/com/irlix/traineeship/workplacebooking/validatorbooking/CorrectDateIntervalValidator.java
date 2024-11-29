package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import org.springframework.stereotype.Component;

@Component
public class CorrectDateIntervalValidator extends BookingValidator {
    @Override
    public boolean check(BookingEntity bookingEntity) {
        if(bookingEntity.getBookingStart().isAfter(bookingEntity.getBookingEnd())) {
            throw new WrongBookParametersException("The end of the booking must not be earlier than the beginning");
        }
        return checkNext(bookingEntity);
    }
}
