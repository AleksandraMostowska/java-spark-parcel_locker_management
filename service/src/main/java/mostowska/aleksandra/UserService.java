package mostowska.aleksandra;

import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.User;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 * Provides methods for retrieving user and reservation information.
 */
public interface UserService {

    /**
     * Retrieves all reservations associated with a specific user.
     *
     * @param id The ID of the user whose reservations are to be retrieved.
     * @return A list of reservations for the specified user.
     */
    List<Reservation> findAllUsersReservations(Long id);

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The User object corresponding to the specified ID.
     */
    User findUserById(Long id);
}
