package mostowska.aleksandra.repository.impl;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.User;
import mostowska.aleksandra.repository.UserRepository;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Implementation of the UserRepository interface using JDBI for database operations.
 * Provides methods to query users based on different attributes.
 */
@Repository
public class UserRepositoryImpl extends AbstractCrudRepository<User, Long> implements UserRepository {

    /**
     * Constructs a new UserRepositoryImpl with the given Jdbi instance.
     *
     * @param jdbi The Jdbi instance used for database operations.
     */
    public UserRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        var sql = "select * from users where username = :username";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("username", username)
                .mapToBean(User.class)
                .findFirst());
    }

    /**
     * Finds a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    @Override
    public Optional<User> findByPhoneNumber(Long phoneNumber) {
        var sql = "select * from users where phone_number = :phone_number";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("phone_number", phoneNumber)
                .mapToBean(User.class)
                .findFirst());
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        var sql = "select * from users where email = :email";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("email", email) // Fixed binding to use parameter name
                .mapToBean(User.class)
                .findFirst());
    }

    /**
     * Finds all reservations associated with a specific user ID.
     *
     * @param id The ID of the user whose reservations are to be retrieved.
     * @return A list of reservations for the specified user. If no reservations are found, an empty list is returned.
     */
    @Override
    public List<Reservation> findUsersReservations(Integer id) {
        var sql = "select reservations.* from reservations " +
                "join users on reservations.user_id = users.id " +
                "where users.id = :id";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id", id)
                .mapToBean(Reservation.class)
                .list());
    }
}
