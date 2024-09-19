package mostowska.aleksandra.repository;

import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing and manipulating Reservation entities.
 * Extends the generic CrudRepository for basic CRUD operations.
 */
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    /**
     * Finds all reservations associated with a specific user ID.
     *
     * @param userId The ID of the user whose reservations are to be found.
     * @return A List of Reservation objects associated with the specified user ID.
     */
    List<Reservation> findByUserId(int userId);

    /**
     * Finds all reservations associated with a specific parcel ID.
     *
     * @param parcelId The ID of the parcel whose reservations are to be found.
     * @return A List of Reservation objects associated with the specified parcel ID.
     */
    List<Reservation> findByParcelId(int parcelId);

    /**
     * Finds reservations associated with a specific user ID and parcel ID.
     *
     * @param userId   The ID of the user whose reservations are to be found.
     * @param parcelId The ID of the parcel whose reservations are to be found.
     * @return A List of Reservation objects associated with the specified user ID and parcel ID.
     */
    List<Reservation> findByUserIdAndParcelId(int userId, int parcelId);

    /**
     * Retrieves the remaining time for a reservation based on its ID.
     *
     * @param id The ID of the reservation for which to retrieve the remaining time.
     * @return A Duration representing the remaining time for the specified reservation.
     */
    Duration getRemainingTimeByReservationId(int id);

    /**
     * Retrieves the ID of the last reservation in the repository.
     *
     * @return The ID of the last reservation, or null if no reservations exist.
     */
    Integer getLastReservationsId();
}
