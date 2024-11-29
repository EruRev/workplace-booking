package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AvailablePeriodValidator extends BookingValidator {
    private final int MAX_GAP = 30;

    @Override
    public boolean check(BookingEntity bookingEntity) {
        if(Duration.between(bookingEntity.getBookingStart(), bookingEntity.getBookingDate()).toDays() > MAX_GAP) {
            throw new WrongBookParametersException("It is too early to book a workplace for the selected date");
        }
        return checkNext(bookingEntity);
    }
}
