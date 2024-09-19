package mostowska.aleksandra.model.dto;

import mostowska.aleksandra.model.Reservation;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for creating a new reservation.
 * Encapsulates the data required to create a reservation and provides a method to convert it to a {@link Reservation} entity.
 *
 * @param userId          The ID of the user making the reservation.
 * @param parcelId        The ID of the parcel being reserved.
 * @param reservationTime The time when the reservation is made.
 * @param releaseTime     The time when the reservation will expire or be released.
 */
public record CreateReservationDto(Integer userId, Integer parcelId, LocalDateTime reservationTime, LocalDateTime releaseTime) {

    /**
     * Converts this DTO to a {@link Reservation} entity.
     *
     * @return A new {@link Reservation} object with the data from this DTO.
     */
    public Reservation toReservation() {
        return new Reservation(null, userId, parcelId, reservationTime, releaseTime);
    }
}
