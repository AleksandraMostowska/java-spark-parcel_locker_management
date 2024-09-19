package mostowska.aleksandra.repository;

import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.User;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating User entities.
 * Extends the generic CrudRepository for basic CRUD operations.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to find.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByPhoneNumber(Long phoneNumber);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds all reservations associated with a specific user ID.
     *
     * @param id The ID of the user whose reservations are to be found.
     * @return A List of Reservation objects associated with the specified user ID.
     */
    List<Reservation> findUsersReservations(Integer id);
}
