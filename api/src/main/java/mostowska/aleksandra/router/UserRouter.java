package mostowska.aleksandra.router;


import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.UserService;
import mostowska.aleksandra.dto.ResponseDto;
import mostowska.aleksandra.repository.UserRepository;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.get;
import static spark.Spark.path;

/**
 * Router class for handling HTTP requests related to user operations.
 * Configures the routes for retrieving user information and their reservations.
 */
@Component
@RequiredArgsConstructor
public class UserRouter {

    private final UserRepository userRepository;  // Repository for user data access
    private final UserService userService;        // Service for user-related operations
    private final ResponseTransformer responseTransformer; // Transformer to format responses

    /**
     * Defines the routes for user-related operations.
     */
    public void routes() {
        path("/users", () -> {

            /*
             * Route to get all users.
             *
             * @return A JSON response containing a list of all users.
             */
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(userRepository.findAll()); // Return all users in response
                    },
                    responseTransformer
            );

            path("/:id", () -> {

                /*
                 * Route to get a specific user by ID.
                 *
                 * @param id The ID of the user to retrieve.
                 * @return A JSON response containing the user with the specified ID.
                 * @throws IllegalArgumentException if the user with the given ID does not exist.
                 */
                get("",
                        (request, response) -> {
                            var id = Long.parseLong(request.params("id")); // Parse user ID from request
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(userService.findUserById(id)); // Return user data in response
                        },
                        responseTransformer
                );

                /*
                 * Route to get all reservations for a specific user.
                 *
                 * @param id The ID of the user whose reservations are to be retrieved.
                 * @return A JSON response containing a list of reservations for the specified user.
                 * @throws IllegalArgumentException if the user with the given ID does not exist.
                 */
                get(
                        "/reservations",
                        (request, response) -> {
                            long id = Long.parseLong(request.params("id")); // Parse user ID from request
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(userService.findAllUsersReservations(id)); // Return user's reservations in response
                        },
                        responseTransformer
                );
            });
        });
    }
}
