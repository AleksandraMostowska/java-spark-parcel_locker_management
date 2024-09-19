package mostowska.aleksandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a user in the system with basic attributes.
 * Uses Lombok annotations to generate boilerplate code such as getters, setters, constructors, and builder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String username;
    private String email;
    private Long phoneNumber;

    /**
     * Creates a new User instance with a specified ID, retaining other attributes.
     *
     * @param _id The new ID for the user.
     * @return A new User instance with the specified ID and existing username, email, and phone number.
     */
    public User withId(Integer _id) {
        return new User(_id, username, email, phoneNumber);
    }
}
