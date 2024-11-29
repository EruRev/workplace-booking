package com.irlix.traineeship.workplacebooking.validatorbooking;

import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.exceptions.WrongBookParametersException;
import com.irlix.traineeship.workplacebooking.repositories.BookingRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(force = true)
public class FreeWorkplaceValidator extends BookingValidator {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean check(BookingEntity bookingEntity) {
        if (!bookingRepository.findByWorkplaceAndCancellated(bookingEntity.getWorkplaceId(), bookingEntity.getBookingStart(),
                bookingEntity.getBookingEnd(), null).isEmpty()) {
            throw new WrongBookParametersException("The workplace is already occupied at this time");
        }
        return checkNext(bookingEntity);
    }
}