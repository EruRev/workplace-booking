package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.BookingForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.BookingForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import com.irlix.traineeship.workplacebooking.entities.WorkspaceEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.StatusEnum;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T10:56:05+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingForUserDTO toBookingForUserDTO(BookingEntity bookingEntity, OfficeEntity officeEntity, WorkspaceEntity workspaceEntity, WorkplaceEntity workplaceEntity) {
        if ( bookingEntity == null && officeEntity == null && workspaceEntity == null && workplaceEntity == null ) {
            return null;
        }

        LocalDateTime bookingStart = null;
        LocalDateTime bookingEnd = null;
        if ( bookingEntity != null ) {
            bookingStart = bookingEntity.getBookingStart();
            bookingEnd = bookingEntity.getBookingEnd();
        }
        String officeName = null;
        if ( officeEntity != null ) {
            officeName = officeEntity.getName();
        }
        String workspaceName = null;
        short floorNumber = 0;
        if ( workspaceEntity != null ) {
            workspaceName = workspaceEntity.getName();
            floorNumber = workspaceEntity.getFloorNumber();
        }
        int workplaceNumber = 0;
        String equipment = null;
        if ( workplaceEntity != null ) {
            workplaceNumber = workplaceEntity.getNumber();
            equipment = workplaceEntity.getDescription();
        }

        BookingForUserDTO bookingForUserDTO = new BookingForUserDTO( officeName, workspaceName, floorNumber, workplaceNumber, bookingStart, bookingEnd, equipment );

        return bookingForUserDTO;
    }

    @Override
    public BookingEntity toBookingEntity(BookingForUserDTO bookingForUserDTO) {
        if ( bookingForUserDTO == null ) {
            return null;
        }

        BookingEntity bookingEntity = new BookingEntity();

        bookingEntity.setBookingStart( bookingForUserDTO.bookingStart() );
        bookingEntity.setBookingEnd( bookingForUserDTO.bookingEnd() );

        return bookingEntity;
    }

    @Override
    public BookingForAdminDTO toBookingForAdminDTO(BookingEntity bookingEntity, OfficeEntity officeEntity, WorkspaceEntity workspaceEntity, WorkplaceEntity workplaceEntity, UserEntity userEntity) {
        if ( bookingEntity == null && officeEntity == null && workspaceEntity == null && workplaceEntity == null && userEntity == null ) {
            return null;
        }

        LocalDateTime bookingStart = null;
        LocalDateTime bookingEnd = null;
        if ( bookingEntity != null ) {
            bookingStart = bookingEntity.getBookingStart();
            bookingEnd = bookingEntity.getBookingEnd();
        }
        String officeName = null;
        if ( officeEntity != null ) {
            officeName = officeEntity.getName();
        }
        String workspaceName = null;
        short floorNumber = 0;
        if ( workspaceEntity != null ) {
            workspaceName = workspaceEntity.getName();
            floorNumber = workspaceEntity.getFloorNumber();
        }
        int workplaceNumber = 0;
        String equipment = null;
        if ( workplaceEntity != null ) {
            workplaceNumber = workplaceEntity.getNumber();
            equipment = workplaceEntity.getDescription();
        }
        String user = null;
        if ( userEntity != null ) {
            user = userEntity.getFullName();
        }

        StatusEnum status = getStatus(bookingEntity);

        BookingForAdminDTO bookingForAdminDTO = new BookingForAdminDTO( officeName, workspaceName, floorNumber, workplaceNumber, bookingStart, bookingEnd, equipment, status, user );

        return bookingForAdminDTO;
    }

    @Override
    public BookingEntity toBookingEntity(BookingForAdminDTO bookingForAdminDTO) {
        if ( bookingForAdminDTO == null ) {
            return null;
        }

        BookingEntity bookingEntity = new BookingEntity();

        bookingEntity.setBookingStart( bookingForAdminDTO.bookingStart() );
        bookingEntity.setBookingEnd( bookingForAdminDTO.bookingEnd() );

        return bookingEntity;
    }
}
