package mostowska.aleksandra.impl;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.ParcelLockerService;
import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.dto.CreateReservationDto;
import mostowska.aleksandra.model.dto.ReservationRequestDto;
import mostowska.aleksandra.model.utils.PackageDimensions;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.ParcelLockerRepository;
import mostowska.aleksandra.repository.ParcelRepository;
import mostowska.aleksandra.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;


/**
 * Implementation of the 'ParcelLockerService' interface.
 * Provides methods for finding the nearest parcel locker and creating reservations.
 */
@Service
@RequiredArgsConstructor
public class ParcelLockerServiceImpl implements ParcelLockerService {

    private final ParcelLockerRepository parcelLockerRepository;
    private final ParcelRepository parcelRepository;
    private final ReservationRepository reservationRepository;
    private static final Logger logger = LoggerFactory.getLogger(ParcelLockerServiceImpl.class);

    /**
     * Finds the nearest parcel locker to the specified user location.
     *
     * @param userLatitude  The latitude of the user's location.
     * @param userLongitude The longitude of the user's location.
     * @return The nearest {@link ParcelLocker} object.
     * @throws IllegalArgumentException If no parcel lockers are available.
     */
    @Override
    public ParcelLocker findNearestLocker(double userLatitude, double userLongitude) {
        var lockers = parcelLockerRepository.findAll();
        return lockers
                .stream()
                .min(Comparator.comparingDouble(l -> l.getLockersDistance(userLatitude, userLongitude)))
                .orElseThrow(() -> new IllegalArgumentException("No nearest locker found"));
    }

    /**
     * Creates a reservation for a parcel locker based on the provided reservation details.
     *
     * @param reservationRequestDto The DTO containing reservation details.
     * @return The created {@link Reservation} object.
     * @throws IllegalStateException If the parcel locker or suitable parcel is not found,
     *                                or if the parcel locker has no available lockers.
     */
    @Override
    public Reservation makeReservation(ReservationRequestDto reservationRequestDto) {
        int userId = reservationRequestDto.userId();
        int parcelLockerId = reservationRequestDto.parcelLockerId();

        var packageDimensions = new PackageDimensions(
                reservationRequestDto.parcelWidth(),
                reservationRequestDto.parcelLength(),
                reservationRequestDto.parcelHeight()
        );

        var parcelLocker = parcelLockerRepository
                .findById((long) parcelLockerId)
                .orElseThrow(() -> new IllegalStateException("No parcel lockers of given id"));

        var suitableParcel = parcelLockerRepository
                .findAvailableParcelByLockerId(parcelLockerId, packageDimensions)
                .orElseThrow(() -> new IllegalStateException("No matching parcel found"));

        var reserveSuitableParcel = suitableParcel.withStatus(ParcelStatus.RESERVED);
        parcelRepository.update((long) suitableParcel.getId(), reserveSuitableParcel);

        var reserveInSuitableParcelLocker = parcelLocker.withAvailableLockers(parcelLocker.getAvailableLockers() - 1);
        parcelLockerRepository.update((long) parcelLockerId, reserveInSuitableParcelLocker);

        var reservation = new CreateReservationDto(userId, reserveSuitableParcel.getId(),
                LocalDateTime.now(), LocalDateTime.now().plusWeeks(1))
                .toReservation()
                .withId(reservationRepository.getLastReservationsId());


        reservationRepository.save(reservation);
        return reservation;
    }
}
