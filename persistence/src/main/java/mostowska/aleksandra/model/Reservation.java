package mostowska.aleksandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * Represents a reservation in the system.
 * Uses Lombok annotations to generate boilerplate code such as getters, setters, constructors, and builder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private Integer id;
    private Integer userId;
    private Integer parcelId;
    private LocalDateTime reservationTime;
    private LocalDateTime releaseTime;

    /**
     * Creates a new Reservation instance with a specified ID, retaining other attributes.
     *
     * @param _id The new ID for the reservation.
     * @return A new Reservation instance with the specified ID and existing user ID, parcel ID, reservation time, and release time.
     */
    public Reservation withId(Integer _id) {
        return new Reservation(_id, userId, parcelId, reservationTime, releaseTime);
    }
}
