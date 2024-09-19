package mostowska.aleksandra.impl.parcelLockerServiceImpl;

import mostowska.aleksandra.impl.ParcelLockerServiceImpl;
import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.dto.ReservationRequestDto;
import mostowska.aleksandra.model.utils.PackageDimensions;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.ParcelLockerRepository;
import mostowska.aleksandra.repository.ParcelRepository;
import mostowska.aleksandra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class MakeReservationTest {
    private static final Logger logger = LoggerFactory.getLogger(MakeReservationTest.class);

    @Mock
    private ParcelLockerRepository parcelLockerRepository;
    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ParcelLockerServiceImpl parcelLockerService;


    private ParcelLocker parcelLocker;
    private Parcel parcel;
    private Reservation reservation;
    private PackageDimensions packageDimensions;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);

        parcelLocker = ParcelLocker.builder()
                .id(1)
                .locationName("Locker A")
                .latitude(52.2296756)
                .longitude(21.0122287)
                .totalLockers(20L)
                .availableLockers(5L)
                .build();

        parcel = Parcel.builder()
                .id(1)
                .parcelLockerId(1)
                .lockerNumber(1L)
                .status(ParcelStatus.AVAILABLE)
                .width(10.0)
                .length(20.0)
                .height(15.0)
                .build();

        reservation = Reservation.builder()
                .id(1)
                .userId(1)
                .parcelId(1)
                .reservationTime(LocalDateTime.now())
                .releaseTime(LocalDateTime.now().plusWeeks(1))
                .build();

        packageDimensions = new PackageDimensions((double) 10, (double) 10, (double) 10);
    }

    @Test
    @DisplayName("When there is suitable parcel")
    void testValidRequest() {
        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
        when(parcelLockerRepository.findAvailableParcelByLockerId(1, packageDimensions)).thenReturn(Optional.ofNullable(parcel));
        when(reservationRepository.getLastReservationsId()).thenReturn(1);
//        when(reservationRepository.findByUserIdAndParcelId(1, 1)).thenReturn(List.of());

        logger.info("Mocked ParcelLocker: {}", parcelLocker);
        logger.info("Mocked Parcel: {}", parcel);

        var result = parcelLockerService.makeReservation(new ReservationRequestDto(
                1, 10.0, 10.0, 10.0, 1));

        logger.info("RESERVATION MADE: ", result);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(4, parcelLocker.getAvailableLockers() - 1);
        verify(parcelLockerRepository, times(1)).findById(1L);
        verify(parcelLockerRepository, times(1))
                .findAvailableParcelByLockerId(1, packageDimensions);
        verify(reservationRepository, times(1)).getLastReservationsId();
//        verify(reservationRepository, times(1)).findByUserIdAndParcelId(1, 1);
        verify(reservationRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("When invalid locker id passed")
    void testInvalidLockerId() {
        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.empty());

        var thrown = assertThrows(IllegalStateException.class, () ->
                parcelLockerService.makeReservation(new ReservationRequestDto(
                        1, 10.0, 20.0, 15.0, 1)));
        assertEquals("No parcel lockers of given id", thrown.getMessage());
    }


    @Test
    @DisplayName("When there is no suitable parcel found")
    void testNoSuitableParcel() {
        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
        when(parcelLockerRepository.findAvailableParcelByLockerId(1,
                new PackageDimensions((double) 10,(double)  25, (double) 20)))
                .thenReturn(Optional.empty());

        var thrown = assertThrows(IllegalStateException.class, () ->
                parcelLockerService.makeReservation(new ReservationRequestDto(
                        1, 10.0, 25.0, 20.0, 1)));
        assertEquals("No matching parcel found", thrown.getMessage());
    }

}
