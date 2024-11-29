package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import com.irlix.traineeship.workplacebooking.repositories.BookingRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@NoArgsConstructor(force = true)
public class OneReservationValidator extends BookingValidator {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean check(BookingEntity bookingEntity) {
        if(Objects.nonNull(bookingRepository.findByDateAndCancelled(bookingEntity.getBookingStart().toLocalDate(),
                null))) {
            throw new WrongBookParametersException("You have already booked a workplace for this day");
        }
        return checkNext(bookingEntity);
    }
}