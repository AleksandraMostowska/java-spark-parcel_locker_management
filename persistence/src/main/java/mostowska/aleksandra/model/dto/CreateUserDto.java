package mostowska.aleksandra.model.dto;

import mostowska.aleksandra.model.User;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * Encapsulates the data required to create a user and provides a method to convert it to a {@link User} entity.
 *
 * @param username   The username of the new user.
 * @param email      The email address of the new user.
 * @param phoneNumber The phone number of the new user.
 */
public record CreateUserDto(String username, String email, Long phoneNumber) {

    /**
     * Converts this DTO to a {@link User} entity.
     *
     * @return A new {@link User} object with the data from this DTO.
     */
    public User toUser() {
        return new User(null, username, email, phoneNumber);
    }
}
