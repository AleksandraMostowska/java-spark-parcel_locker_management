package mostowska.aleksandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mostowska.aleksandra.model.utils.PackageDimensions;
import mostowska.aleksandra.model.utils.ParcelStatus;


/**
 * Represents a parcel within a parcel locker system.
 * Uses Lombok annotations to automatically generate getters, setters, constructors, and builders.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parcel {
    private Integer id;
    private Integer parcelLockerId;
    private Long lockerNumber;
    private ParcelStatus status;
    private Double width;
    private Double length;
    private Double height;

    /**
     * Creates a new Parcel instance with a specified status.
     *
     * @param newParcelStatus The new status to set for the parcel.
     * @return A new Parcel instance with the updated status and existing attributes.
     */
    public Parcel withStatus(ParcelStatus newParcelStatus) {
        return new Parcel(id, parcelLockerId, lockerNumber, newParcelStatus, width, length, height);
    }

    /**
     * Checks if the parcel has dimensions that meet or exceed the expected dimensions.
     *
     * @param packageDimensions The dimensions to compare against.
     * @return {@code true} if the parcel's dimensions are equal to or greater than the expected dimensions; {@code false} otherwise.
     */
    public Boolean hasExpectedDimensions(PackageDimensions packageDimensions) {
        return width >= packageDimensions.packageWidth()
                && length >= packageDimensions.packageLength()
                && height >= packageDimensions.packageHeight();
    }
}
