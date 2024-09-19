package mostowska.aleksandra.repository;

import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.model.utils.PackageDimensions;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating ParcelLocker entities.
 * Extends the generic CrudRepository for basic CRUD operations.
 */
public interface ParcelLockerRepository extends CrudRepository<ParcelLocker, Long> {

    /**
     * Finds all parcel lockers based on the location name.
     *
     * @param locationName The name of the location to search for parcel lockers.
     * @return A List of ParcelLocker objects matching the specified location name.
     */
    List<ParcelLocker> findAllByLocationName(String locationName);

    /**
     * Finds an available parcel within a specific parcel locker based on the given dimensions.
     *
     * @param parcelLockerId The ID of the parcel locker to search within.
     * @param packageDimensions The dimensions of the package to find an appropriate parcel for.
     * @return An Optional containing the Parcel if available, otherwise empty.
     */
    Optional<Parcel> findAvailableParcelByLockerId(Integer parcelLockerId, PackageDimensions packageDimensions);
}
