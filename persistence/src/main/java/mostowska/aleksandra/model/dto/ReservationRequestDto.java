package mostowska.aleksandra.model.dto;

/**
 * Data Transfer Object (DTO) for requesting a reservation.
 * Contains the necessary information to request a reservation for a parcel locker.
 *
 * @param userId          The ID of the user making the reservation request.
 * @param parcelWidth     The width of the parcel to be reserved.
 * @param parcelLength    The length of the parcel to be reserved.
 * @param parcelHeight    The height of the parcel to be reserved.
 * @param parcelLockerId  The ID of the parcel locker where the reservation is to be made.
 */
public record ReservationRequestDto(int userId, double parcelWidth, double parcelLength, double parcelHeight, int parcelLockerId) {
}
