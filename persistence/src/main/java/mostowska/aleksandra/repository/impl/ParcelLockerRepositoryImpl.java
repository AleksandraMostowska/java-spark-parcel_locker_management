package mostowska.aleksandra.repository.impl;

import mostowska.aleksandra.model.Parcel;
import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.model.utils.PackageDimensions;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.ParcelLockerRepository;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Implementation of the ParcelLockerRepository interface using JDBI for database operations.
 * Provides methods to perform CRUD operations and custom queries for parcel lockers and associated parcels.
 */
@Repository
public class ParcelLockerRepositoryImpl extends AbstractCrudRepository<ParcelLocker, Long> implements ParcelLockerRepository {

    /**
     * Constructs a new ParcelLockerRepositoryImpl with the given Jdbi instance.
     *
     * @param jdbi The Jdbi instance used for database operations.
     */
    public ParcelLockerRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds all parcel lockers by the specified location name.
     *
     * @param locationName The name of the location to filter parcel lockers.
     * @return A list of ParcelLocker entities that match the specified location name.
     */
    @Override
    public List<ParcelLocker> findAllByLocationName(String locationName) {
        var sql = "select * from parcel_lockers where location_name = :location_name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("location_name", locationName)
                .mapToBean(ParcelLocker.class)
                .list());
    }

    /**
     * Finds all available parcels in a parcel locker with the specified locker ID.
     * This method retrieves parcels that are available and associated with a specific parcel locker.
     *
     * @param id The ID of the parcel locker to find available parcels.
     * @return A list of Parcel entities that are available in the specified parcel locker.
     */
    @Override
    public Optional<Parcel> findAvailableParcelByLockerId(Integer id, PackageDimensions packageDimensions) {
        var sql = "select parcels.* from parcels " +
                "join parcel_lockers on parcels.parcel_locker_id = parcel_lockers.id " +
                "where parcel_lockers.id = :id " +
                "and parcels.status = :status";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id", id)
                .bind("status", ParcelStatus.AVAILABLE.name())
                .mapToBean(Parcel.class)
                .stream()
                .filter(parcel -> parcel.hasExpectedDimensions(packageDimensions))
                .findFirst());
    }
}
