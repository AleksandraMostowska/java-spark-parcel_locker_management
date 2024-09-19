package mostowska.aleksandra.repository;

import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating 'Parcel' entities.
 * Extends the generic 'CrudRepository' for basic CRUD operations.
 */
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

    /**
     * Finds all parcels associated with a specific user ID.
     *
     * @param userId The ID of the user whose parcels are to be found.
     * @return A list of 'Parcel' objects associated with the specified user ID.
     */
    List<Parcel> findByUserId(int userId);

    /**
     * Finds all parcels in a specific parcel locker with a given status.
     *
     * @param parcelLockerId The ID of the parcel locker where parcels are stored.
     * @param status         The status of the parcels to be found.
     * @return A list of 'Parcel' objects that match the specified parcel locker ID and status.
     */
    List<Parcel> findByParcelLockerIdAndStatus(int parcelLockerId, ParcelStatus status);

}
