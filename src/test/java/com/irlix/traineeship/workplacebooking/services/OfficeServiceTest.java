package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.OfficeDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForOfficeDTO;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import com.irlix.traineeship.workplacebooking.exceptions.BlankFieldsException;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.OfficeMapper;
import com.irlix.traineeship.workplacebooking.repositories.OfficeRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfficeServiceTest {
    @InjectMocks
    OfficeService officeService;

    @Mock
    OfficeRepository officeRepository;

    @Mock
    OfficeMapper officeMapper;

    @Mock
    UserService userService;

    private static class TestOfficeData {
        UUID officeId1 = UUID.randomUUID();
        UUID officeId2 = UUID.randomUUID();
        UUID officeId3 = UUID.randomUUID();
        UUID officeId4 = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OfficeEntity officeEntity1 = new OfficeEntity(
                officeId1,
                "Ul'yanovsk, Hvalynskaya st., 2",
                "Gryffindor",
                false
        );
        OfficeEntity officeEntity2 = new OfficeEntity(
                officeId2,
                "Ul'yanovsk, Zelenaya st., 13",
                "Slytherin",
                false
        );
        OfficeEntity officeEntity3 = new OfficeEntity(
                officeId3,
                "Ul'yanovsk, Druzhby st., 10",
                "Hufflepuff",
                true
        );
        OfficeEntity officeEntity4 = new OfficeEntity(
                officeId4,
                "Ulyanovsk, Akademicheskaya st., 15",
                "Ravenclaw",
                true
        );
        OfficeForAdminDTO officeForAdminDTO1 = new OfficeForAdminDTO(
                "Ul'yanovsk, Hvalynskaya st., 2",
                "Gryffindor",
                false
        );
        OfficeForAdminDTO officeForAdminDTO2 = new OfficeForAdminDTO(
                "Ul'yanovsk, Zelenaya st., 13",
                "Slytherin",
                false
        );
        OfficeForAdminDTO officeForAdminDTO3 = new OfficeForAdminDTO(
                "Ul'yanovsk, Druzhby st., 10",
                "Hufflepuff",
                true
        );
        OfficeForAdminDTO officeForAdminDTO4 = new OfficeForAdminDTO(
                "Ulyanovsk, Akademicheskaya st., 15",
                "Ravenclaw",
                true
        );
        OfficeForOfficeDTO officeForOfficeDTO1 = new OfficeForOfficeDTO(
                "Ul'yanovsk, Hvalynskaya st., 2",
                "Gryffindor"
        );
        OfficeForOfficeDTO officeForOfficeDTO2 = new OfficeForOfficeDTO(
                "Ul'yanovsk, Zelenaya st., 13",
                "Slytherin"
        );
        OfficeForOfficeDTO officeForOfficeDTO3 = new OfficeForOfficeDTO(
                "Ul'yanovsk, Druzhby st., 10",
                "Hufflepuff"
        );
        OfficeForOfficeDTO officeForOfficeDTO4 = new OfficeForOfficeDTO(
                "Ulyanovsk, Akademicheskaya st., 15",
                "Ravenclaw"
        );
        UserDetailsImpl currentUser = new UserDetailsImpl(
                userId,
                "Full Name",
                "89009009090",
                "email@gmail.com",
                "password",
                false
        );
        List<OfficeEntity> allOffices = Arrays.asList(officeEntity1, officeEntity2, officeEntity3, officeEntity4);
        List<OfficeForAdminDTO> allOfficesForAdminDTO = Arrays.asList(officeForAdminDTO1, officeForAdminDTO2,
                officeForAdminDTO3, officeForAdminDTO4);
        List<OfficeForOfficeDTO> allOfficesForOfficeDTO = Arrays.asList(officeForOfficeDTO1, officeForOfficeDTO2,
                officeForOfficeDTO3, officeForOfficeDTO4);
        List<OfficeEntity> allOfficesWithFalseStatus = Arrays.asList(officeEntity1, officeEntity2);
        List<OfficeForAdminDTO> allOfficesForAdminWithFalseStatusDTO =
                Arrays.asList(officeForAdminDTO1, officeForAdminDTO2);
        List<OfficeForOfficeDTO> allOfficesForOfficeWithFalseStatusDTO =
                Arrays.asList(officeForOfficeDTO1, officeForOfficeDTO2);
        List<OfficeEntity> allOfficesWithTrueStatus = Arrays.asList(officeEntity3, officeEntity4);
        List<OfficeForAdminDTO> allOfficesForAdminWithTrueStatusDTO =
                Arrays.asList(officeForAdminDTO3, officeForAdminDTO4);
        List<OfficeForOfficeDTO> allOfficesForOfficeWithTrueStatusDTO =
                Arrays.asList(officeForOfficeDTO3, officeForOfficeDTO4);
    }

    @Test
    public void createOffice() {
        TestOfficeData testOfficeData = new TestOfficeData();
        OfficeForAdminDTO expectedOfficeForAdminDTO = testOfficeData.officeForAdminDTO1;

        when(officeRepository.save(any(OfficeEntity.class))).thenReturn(testOfficeData.officeEntity1);
        when(officeMapper.toOfficeForAdminDTO(any(OfficeEntity.class))).thenReturn(expectedOfficeForAdminDTO);
        when(officeMapper.toOfficeEntity(any(OfficeForAdminDTO.class))).thenReturn(testOfficeData.officeEntity1);
        OfficeForAdminDTO actualOfficeForAdminDTO = officeService.createOffice(expectedOfficeForAdminDTO);

        Assertions.assertEquals(expectedOfficeForAdminDTO, actualOfficeForAdminDTO);
        verify(officeRepository, times(1)).save(any(OfficeEntity.class));
        verify(officeMapper, times(1)).toOfficeForAdminDTO(any(OfficeEntity.class));
        verify(officeMapper, times(1)).toOfficeEntity(any(OfficeForAdminDTO.class));
    }

    @Test
    public void createOfficeWithMissingParameters() {
        OfficeForAdminDTO expectedOfficeForAdminDTO = new OfficeForAdminDTO(
                "Address",
                null,
                false
        );

        BlankFieldsException thrown = Assertions.assertThrows(BlankFieldsException.class, () -> {
            officeService.createOffice(expectedOfficeForAdminDTO);
        });

        Assertions.assertEquals("Office name and address are required", thrown.getMessage());
    }

    @Test
    public void showOfficeForAdmin() {
        TestOfficeData testOfficeData = new TestOfficeData();
        OfficeForAdminDTO expectedOfficeForAdminDTO = testOfficeData.officeForAdminDTO1;

        when(officeMapper.toOfficeForAdminDTO(any(OfficeEntity.class))).thenReturn(testOfficeData.officeForAdminDTO1);
        when(officeRepository.findById(testOfficeData.officeId1)).thenReturn(Optional.of(testOfficeData.officeEntity1));
        when(userService.checkAdminRole(any())).thenReturn(true);
        OfficeDTO actualOfficeForAdminDTO =
                officeService.showOffice(testOfficeData.currentUser, testOfficeData.officeId1);

        Assertions.assertNotNull(actualOfficeForAdminDTO);
        Assertions.assertEquals(expectedOfficeForAdminDTO, actualOfficeForAdminDTO);
    }

    @Test
    public void showOfficeForUser() {
        TestOfficeData testOfficeData = new TestOfficeData();
        OfficeForOfficeDTO expectedOfficeForOfficeDTO = testOfficeData.officeForOfficeDTO1;

        when(officeMapper.toOfficeForOfficeDTO(any(OfficeEntity.class))).thenReturn(testOfficeData.officeForOfficeDTO1);
        when(officeRepository.findById(testOfficeData.officeId1)).thenReturn(Optional.of(testOfficeData.officeEntity1));
        when(userService.checkAdminRole(any())).thenReturn(false);
        OfficeDTO actualOfficeForOfficeDTO =
                officeService.showOffice(testOfficeData.currentUser, testOfficeData.officeId1);

        Assertions.assertNotNull(actualOfficeForOfficeDTO);
        Assertions.assertEquals(expectedOfficeForOfficeDTO, actualOfficeForOfficeDTO);
    }

    @Test
    public void showOfficeNotFound() {
        TestOfficeData testOfficeData = new TestOfficeData();
        UUID officeId = UUID.randomUUID();

        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            when(officeRepository.findById(officeId)).thenReturn(Optional.empty());
            officeService.showOffice(testOfficeData.currentUser ,officeId);
        });

        Assertions.assertEquals("Office not found", thrown.getMessage());
    }

    @Test
    public void showAllOfficesForAdmin() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOffices;
        List<OfficeForAdminDTO> expectedOfficesDTO = testOfficeData.allOfficesForAdminDTO;

        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity1))
                .thenReturn(testOfficeData.officeForAdminDTO1);
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForAdminDTO2);
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity3))
                .thenReturn(testOfficeData.officeForAdminDTO3);
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity4))
                .thenReturn(testOfficeData.officeForAdminDTO4);
        when(officeRepository.findAll()).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(true);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser,null);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesForUser() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOffices;
        List<OfficeForOfficeDTO> expectedOfficesDTO = testOfficeData.allOfficesForOfficeDTO;

        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity1))
                .thenReturn(testOfficeData.officeForOfficeDTO1);
        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForOfficeDTO2);
        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity3))
                .thenReturn(testOfficeData.officeForOfficeDTO3);
        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity4))
                .thenReturn(testOfficeData.officeForOfficeDTO4);
        when(officeRepository.findAll()).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(false);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser,null);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesWithFalseStatusForAdmin() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOfficesWithFalseStatus;
        List<OfficeForAdminDTO> expectedOfficesDTO = testOfficeData.allOfficesForAdminWithFalseStatusDTO;

        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity1))
                .thenReturn(testOfficeData.officeForAdminDTO1);
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForAdminDTO2);
        when(officeRepository.findByIsDeleted(false)).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(true);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser, false);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesWithFalseStatusForUser() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOfficesWithFalseStatus;
        List<OfficeForOfficeDTO> expectedOfficesDTO = testOfficeData.allOfficesForOfficeWithFalseStatusDTO;

        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity1))
                .thenReturn(testOfficeData.officeForOfficeDTO1);
        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForOfficeDTO2);
        when(officeRepository.findByIsDeleted(false)).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(false);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser, false);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesWithTrueStatusForAdmin() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOfficesWithTrueStatus;
        List<OfficeForAdminDTO> expectedOfficesDTO = testOfficeData.allOfficesForAdminWithTrueStatusDTO;

        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity3))
                .thenReturn(testOfficeData.officeForAdminDTO3);
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity4))
                .thenReturn(testOfficeData.officeForAdminDTO4);
        when(officeRepository.findByIsDeleted(true)).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(true);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser, true);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesWithTrueStatusForUser() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = testOfficeData.allOfficesWithTrueStatus;
        List<OfficeForOfficeDTO> expectedOfficesDTO = testOfficeData.allOfficesForOfficeWithTrueStatusDTO;

        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity3))
                .thenReturn(testOfficeData.officeForOfficeDTO3);
        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity4))
                .thenReturn(testOfficeData.officeForOfficeDTO4);
        when(officeRepository.findByIsDeleted(true)).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(false);
        List<OfficeDTO> actualOffices = officeService.showAllOffices(testOfficeData.currentUser, true);

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void showAllOfficesNotFound() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = new ArrayList<>(0);
        List<OfficeForAdminDTO> expectedOfficesDTO = new ArrayList<>(0);

        when(officeRepository.findAll()).thenReturn(expectedOffices);
        List<OfficeDTO> actualOfficesDTO = officeService.showAllOffices(testOfficeData.currentUser, null);

        Assertions.assertEquals(expectedOfficesDTO, actualOfficesDTO);
    }

    @Test
    public void searchOfficeForAdmin() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = Arrays.asList(testOfficeData.officeEntity2);
        List<OfficeForAdminDTO> expectedOfficesDTO = Arrays.asList(testOfficeData.officeForAdminDTO2);

        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForAdminDTO2);
        when(officeRepository.findByName("Slytherin")).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(true);
        List<OfficeDTO> actualOffices = officeService.searchOffice(testOfficeData.currentUser,"Slytherin");

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void searchOfficeForUser() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = Arrays.asList(testOfficeData.officeEntity2);
        List<OfficeForOfficeDTO> expectedOfficesDTO = Arrays.asList(testOfficeData.officeForOfficeDTO2);

        when(officeMapper.toOfficeForOfficeDTO(testOfficeData.officeEntity2))
                .thenReturn(testOfficeData.officeForOfficeDTO2);
        when(officeRepository.findByName("Slytherin")).thenReturn(expectedOffices);
        when(userService.checkAdminRole(any())).thenReturn(false);
        List<OfficeDTO> actualOffices = officeService.searchOffice(testOfficeData.currentUser,"Slytherin");

        Assertions.assertNotNull(actualOffices);
        Assertions.assertEquals(expectedOfficesDTO, actualOffices);
    }

    @Test
    public void searchOfficeNotFound() {
        TestOfficeData testOfficeData = new TestOfficeData();
        List<OfficeEntity> expectedOffices = new ArrayList<>(0);
        List<OfficeForAdminDTO> expectedOfficesDTO = new ArrayList<>(0);

        when(officeRepository.findByName("Slytherin")).thenReturn(expectedOffices);
        List<OfficeDTO> actualOfficesDTO = officeService.searchOffice(testOfficeData.currentUser,"Slytherin");

        Assertions.assertEquals(expectedOfficesDTO, actualOfficesDTO);
    }

    @Test
    public void updateOffice() {
        TestOfficeData testOfficeData = new TestOfficeData();
        OfficeForAdminDTO expectedOfficeForAdminDTO = testOfficeData.officeForAdminDTO1;

        when(officeRepository.findById(testOfficeData.officeId1)).thenReturn(Optional.of(testOfficeData.officeEntity1));
        when(officeMapper.toOfficeForAdminDTO(testOfficeData.officeEntity1)).thenReturn(expectedOfficeForAdminDTO);
        when(officeRepository.save(testOfficeData.officeEntity1)).thenReturn(testOfficeData.officeEntity1);
        OfficeForAdminDTO actualOfficeForAdminDTO = officeService.updateOffice(testOfficeData.officeId1,
                expectedOfficeForAdminDTO);

        Assertions.assertEquals(expectedOfficeForAdminDTO, actualOfficeForAdminDTO);
    }

    @Test
    public void showOfficeNotFoundById(){
        UUID officeId = UUID.randomUUID();

        when(officeRepository.findById(officeId)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            officeService.showOfficeById(officeId);
        });

        Assertions.assertEquals("Office not found", thrown.getMessage());
    }

    @Test
    public void showOfficeNotFoundByName(){
        String officeName = "officeName";

        when(officeRepository.findByName(officeName)).thenReturn(List.of());
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            officeService.showOfficeByName(officeName);
        });

        Assertions.assertEquals("Office not found", thrown.getMessage());
    }
}