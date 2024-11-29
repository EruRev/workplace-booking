package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import org.springframework.stereotype.Component;

@Component
public class CorrectDateStartValidator extends BookingValidator {
    @Override
    public boolean check(BookingEntity bookingEntity) {
        if(bookingEntity.getBookingStart().isBefore(bookingEntity.getBookingDate())) {
            throw new WrongBookParametersException("The booking start should not be earlier than the current one");
        }
        return checkNext(bookingEntity);
    }
}
