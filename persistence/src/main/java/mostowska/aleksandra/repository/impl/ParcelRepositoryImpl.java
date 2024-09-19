package mostowska.aleksandra.repository.impl;

import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.ParcelRepository;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Implementation of the ParcelRepository interface using JDBI for database operations.
 * Provides methods to perform CRUD operations and custom queries for parcels.
 */
@Repository
public class ParcelRepositoryImpl extends AbstractCrudRepository<Parcel, Long> implements ParcelRepository {

    /**
     * Constructs a new ParcelRepositoryImpl with the given Jdbi instance.
     *
     * @param jdbi The Jdbi instance used for database operations.
     */
    public ParcelRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds a list of parcels associated with a specific user ID.
     *
     * @param userId The ID of the user whose parcels are to be found.
     * @return A list of Parcel objects associated with the given user ID.
     */
    @Override
    public List<Parcel> findByUserId(int userId) {
        var sql = "select * from parcels where user_id = :user_id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("user_id", userId)
                        .mapToBean(Parcel.class)
                        .list());
    }

    /**
     * Finds a list of parcels associated with a specific parcel locker ID and status.
     *
     * @param parcelLockerId The ID of the parcel locker where parcels are to be found.
     * @param status The status of the parcels to be retrieved (e.g., RESERVED, DELIVERED).
     * @return A list of Parcel objects matching the given parcel locker ID and status.
     */
    @Override
    public List<Parcel> findByParcelLockerIdAndStatus(int parcelLockerId, ParcelStatus status) {
        var sql = "select * from parcels where parcel_locker_id = :parcel_locker_id and status = :status";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("parcel_locker_id", parcelLockerId)
                        .bind("status", status.name()) // Binds the status enum value as a string
                        .mapToBean(Parcel.class)
                        .list());
    }
}
