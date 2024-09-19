package mostowska.aleksandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mostowska.aleksandra.DistanceCalculator;


/**
 * Represents a parcel locker in the system.
 * Uses Lombok annotations to generate boilerplate code such as getters, setters, constructors, and builder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcelLocker {
    private Integer id;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private Long totalLockers;
    private Long availableLockers;

    /**
     * Creates a new ParcelLocker instance with a specified number of available lockers.
     *
     * @param newAvailableLockersCount The new count of available lockers.
     * @return A new ParcelLocker instance with updated available lockers count and existing attributes.
     */
    public ParcelLocker withAvailableLockers(Long newAvailableLockersCount) {
        return new ParcelLocker(id, locationName, latitude, longitude, totalLockers, newAvailableLockersCount);
    }

    /**
     * Calculates the distance from the parcel locker to a given location.
     *
     * @param userLatitude  The latitude of the user's location.
     * @param userLongitude The longitude of the user's location.
     * @return The distance between the parcel locker and the user's location.
     */
    public double getLockersDistance(double userLatitude, double userLongitude) {
        return DistanceCalculator.getDistance(latitude, longitude, userLatitude, userLongitude);
    }
}
