package mostowska.aleksandra.router;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.dto.ResponseDto;
import mostowska.aleksandra.repository.ReservationRepository;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

/**
 * Router class for handling HTTP requests related to reservations.
 * Configures the routes for retrieving reservation information.
 */
@Component
@RequiredArgsConstructor
public class ReservationRouter {

    private final ReservationRepository reservationRepository;
    private final ResponseTransformer responseTransformer;

    /**
     * Defines the routes for reservation-related operations.
     */
    public void routes() {
        path("/reservations", () -> {
            /*
             * Route to get a list of all reservations.
             *
             * @return A JSON response containing a list of all reservations.
             */
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(reservationRepository.findAll());
                    },
                    responseTransformer
            );
        });
    }
}
