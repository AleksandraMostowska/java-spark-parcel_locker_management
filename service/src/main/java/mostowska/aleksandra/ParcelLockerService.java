package mostowska.aleksandra;

import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.dto.ReservationRequestDto;

/**
 * Service interface for managing parcel lockers and reservations.
 * Provides methods for finding the nearest parcel locker and creating reservations.
 */
public interface ParcelLockerService {

    /**
     * Finds the nearest parcel locker to the given coordinates.
     *
     * @param userLatitude  The latitude of the user's location.
     * @param userLongitude The longitude of the user's location.
     * @return The nearest `ParcelLocker` object to the specified coordinates.
     */
    ParcelLocker findNearestLocker(double userLatitude, double userLongitude);

    /**
     * Creates a reservation based on the provided reservation request details.
     *
     * @param reservationRequestDto The reservation request details encapsulated in a `ReservationRequestDto` object.
     * @return The created `Reservation` object.
     */
    Reservation makeReservation(ReservationRequestDto reservationRequestDto);
}
