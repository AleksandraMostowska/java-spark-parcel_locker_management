//package mostowska.aleksandra.service.impl.parcel_locker_service_impl;
//
//import mostowska.aleksandra.model.Parcel;
//import mostowska.aleksandra.model.ParcelLocker;
//import mostowska.aleksandra.model.Reservation;
//import mostowska.aleksandra.model.dto.ReservationRequestDto;
//import mostowska.aleksandra.model.utils.ParcelStatus;
//import mostowska.aleksandra.repository.ParcelLockerRepository;
//import mostowska.aleksandra.repository.ReservationRepository;
//import mostowska.aleksandra.service.impl.ParcelLockerServiceImpl;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
//public class MakeReservationTest {
//    private static final Logger logger = LoggerFactory.getLogger(MakeReservationTest.class);
//
//    @Mock
//    private ParcelLockerRepository parcelLockerRepository;
//
//    @Mock
//    private ReservationRepository reservationRepository;
//
//    @InjectMocks
//    private ParcelLockerServiceImpl parcelLockerService;
//
//    private ParcelLocker parcelLocker;
//    private Parcel parcel;
//    private Reservation reservation;
//
//    @BeforeEach
//    void setUp() {
////        MockitoAnnotations.openMocks(this);
//
//        parcelLocker = ParcelLocker.builder()
//                .id(1)
//                .locationName("Locker A")
//                .latitude(52.2296756)
//                .longitude(21.0122287)
//                .totalLockers(20L)
//                .availableLockers(5L)
//                .build();
//
//        parcel = Parcel.builder()
//                .id(1)
//                .parcelLockerId(1)
//                .lockerNumber(1L)
//                .status(ParcelStatus.AVAILABLE)
//                .width(10.0)
//                .length(20.0)
//                .height(15.0)
//                .build();
//
//        reservation = Reservation.builder()
//                .id(1)
//                .userId(1)
//                .parcelId(1)
//                .reservationTime(LocalDateTime.now())
//                .releaseTime(LocalDateTime.now().plusWeeks(1))
//                .build();
//    }
//
////    @Test
////    @DisplayName("When there is suitable parcel")
////    void testValidRequest() {
////        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
////        when(parcelLockerRepository.findAvailableParcelByLockerId(1)).thenReturn(List.of(parcel));
////        when(reservationRepository.findLast(1)).thenReturn(List.of());
////        when(reservationRepository.findByUserIdAndParcelId(1, 1)).thenReturn(List.of());
////
////        logger.info("Mocked ParcelLocker: {}", parcelLocker);
////        logger.info("Mocked Parcel: {}", parcel);
////
////        var result = parcelLockerService.makeReservation(new ReservationRequestDto(
////                1, 10.0, 20.0, 15.0, 1));
////
////        assertNotNull(result);
////        assertEquals(1, result.getId());
////        assertEquals(ParcelStatus.RESERVED, parcel.getStatus());
////        assertEquals(4, parcelLocker.getAvailableLockers());
////        verify(parcelLockerRepository, times(1)).findById(1L);
////        verify(parcelLockerRepository, times(1)).findAvailableParcelByLockerId(1);
////        verify(reservationRepository, times(1)).findLast(1);
////        verify(reservationRepository, times(1)).findByUserIdAndParcelId(1, 1);
////        verify(reservationRepository, times(1)).save(result);
////    }
//
//    @Test
//    @DisplayName("When there invalid locker id passed")
//    void testInvalidLockerId() {
//        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.empty());
//
//        var thrown = assertThrows(IllegalStateException.class, () ->
//                parcelLockerService.makeReservation(new ReservationRequestDto(
//                        1, 10.0, 20.0, 15.0, 1)));
//        assertEquals("No parcel lockers of given id", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("When there are no available lockers")
//    void testNoAvailableLockers() {
//        parcelLocker.setAvailableLockers(0L);
//        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
//
//        var thrown = assertThrows(IllegalStateException.class, () ->
//                parcelLockerService.makeReservation(new ReservationRequestDto(
//                        1, 10.0, 20.0, 15.0, 1)));
//        assertEquals("No available lockers in chosen parcel", thrown.getMessage());
//    }
//
////    @Test
////    @DisplayName("When there is no suitable parcel found")
////    void testNoSuitableParcel() {
////        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
////        when(parcelLockerRepository.findAvailableParcelByLockerId(1)).thenReturn(List.of());
////
////        var thrown = assertThrows(IllegalStateException.class, () ->
////                parcelLockerService.makeReservation(new ReservationRequestDto(
////                        1, 10.0, 20.0, 15.0, 1)));
////        assertEquals("No suitable parcel found", thrown.getMessage());
////    }
////
////    @Test
////    @DisplayName("When such reservation already exists")
////    void testReservationAlreadyExists() {
////        when(parcelLockerRepository.findById(1L)).thenReturn(Optional.of(parcelLocker));
////        when(parcelLockerRepository.findAvailableParcelByLockerId(1)).thenReturn(List.of(parcel));
////        when(reservationRepository.findLast(1)).thenReturn(List.of(reservation));
////        when(reservationRepository.findByUserIdAndParcelId(1, 1)).thenReturn(List.of(reservation));
////
////        var thrown = assertThrows(IllegalStateException.class, () ->
////                parcelLockerService.makeReservation(new ReservationRequestDto(
////                        1, 10.0, 20.0, 15.0, 1)));
////        assertEquals("Reservation already exists", thrown.getMessage());
////    }
//}
