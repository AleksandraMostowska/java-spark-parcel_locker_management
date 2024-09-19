package mostowska.aleksandra.impl;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.UserService;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.User;
import mostowska.aleksandra.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implementation of the `UserService` interface.
 * Provides methods for retrieving user information and reservations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves all reservations associated with a user identified by the given ID.
     *
     * @param id The ID of the user whose reservations are to be retrieved.
     * @return A list of `Reservation` objects associated with the specified user.
     * @throws IllegalArgumentException If the user with the specified ID is not found.
     */
    @Override
    public List<Reservation> findAllUsersReservations(Long id) {
        return userRepository
                .findById(id)
                .map(user -> userRepository.findUsersReservations(Math.toIntExact(id)))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The `User` object associated with the specified ID.
     * @throws IllegalArgumentException If the user with the specified ID is not found.
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
