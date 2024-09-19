package mostowska.aleksandra.repository.impl;

import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.repository.ReservationRepository;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Implementation of the ReservationRepository interface using JDBI for database operations.
 * Provides methods to perform CRUD operations and custom queries for reservations.
 */
@Repository
public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation, Long> implements ReservationRepository {

    /**
     * Constructs a new ReservationRepositoryImpl with the given Jdbi instance.
     *
     * @param jdbi The Jdbi instance used for database operations.
     */
    public ReservationRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds reservations by the user ID.
     *
     * @param userId The ID of the user whose reservations are to be found.
     * @return A list of reservations associated with the given user ID.
     */
    @Override
    public List<Reservation> findByUserId(int userId) {
        var sql = "select * from reservations where user_id = :user_id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("user_id", userId)
                        .mapToBean(Reservation.class)
                        .list());
    }

    /**
     * Finds reservations by the parcel ID.
     *
     * @param parcelId The ID of the parcel whose reservations are to be found.
     * @return A list of reservations associated with the given parcel ID.
     */
    @Override
    public List<Reservation> findByParcelId(int parcelId) {
        var sql = "select * from reservations where parcel_id = :parcel_id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("parcel_id", parcelId)
                        .mapToBean(Reservation.class)
                        .list());
    }

    /**
     * Finds reservations by both user ID and parcel ID.
     *
     * @param userId The ID of the user.
     * @param parcelId The ID of the parcel.
     * @return A list of reservations associated with both the given user ID and parcel ID.
     */
    @Override
    public List<Reservation> findByUserIdAndParcelId(int userId, int parcelId) {
        var sql = "select * from reservations where user_id = :user_id and parcel_id = :parcel_id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("user_id", userId)
                        .bind("parcel_id", parcelId)
                        .mapToBean(Reservation.class)
                        .list());
    }

    /**
     * Calculates the remaining time for a reservation based on its ID.
     *
     * @param id The ID of the reservation.
     * @return The remaining duration until the reservation's release time.
     *         Returns null if the reservation does not exist or the remaining time is non-positive.
     */
    @Override
    public Duration getRemainingTimeByReservationId(int id) {
        var sql = "select * from reservations where id = :id";
        var reservation = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Reservation.class)
                        .findFirst());

        if (reservation.isEmpty()) {
            return null;
        }

        var duration = Duration.between(LocalDateTime.now(), reservation.get().getReleaseTime());
        return (duration.isNegative() || duration.isZero()) ? null : duration;
    }

    /**
     * Retrieves the ID of the last reservation in the repository.
     *
     * @return The ID of the last reservation, or 1 if no reservations exist.
     */
    @Override
    public Integer getLastReservationsId() {
        var lastReservation = findLast(1);
        return lastReservation.isEmpty() ? 1 : lastReservation.getFirst().getId() + 1;
    }
}
