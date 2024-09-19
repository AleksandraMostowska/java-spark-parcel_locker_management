package mostowska.aleksandra.router;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.ParcelLockerService;
import mostowska.aleksandra.dto.ResponseDto;
import mostowska.aleksandra.model.dto.ReservationRequestDto;
import mostowska.aleksandra.repository.ParcelLockerRepository;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Router class for handling HTTP requests related to parcel lockers and reservations.
 * Configures the routes for parcel locker operations and reservation management.
 */
@Component
@RequiredArgsConstructor
public class ParcelLockerRouter {

    private final ParcelLockerService parcelLockerService;
    private final ParcelLockerRepository parcelLockerRepository;
    private final ResponseTransformer responseTransformer;
    private final Gson gson;

    private static final Logger logger = LoggerFactory.getLogger(ParcelLockerRouter.class);

    /**
     * Defines the routes for parcel lockers and reservations.
     */
    public void routes() {
        path("/lockers", () -> {
            /*
             * Route to get all parcel lockers.
             *
             * @return A JSON response containing a list of all parcel lockers.
             */
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(parcelLockerRepository.findAll());
                    },
                    responseTransformer
            );


            /*
             * Route to find the nearest parcel locker based on user coordinates.
             *
             * @param latitude  The latitude of the user's location.
             * @param longitude The longitude of the user's location.
             * @return A JSON response containing the nearest parcel locker to the specified coordinates.
             * @throws IllegalArgumentException if no parcel lockers are found.
             */
            get(
                    "/nearest",
                    (request, response) -> {
                        var latitude = Double.parseDouble(request.queryParams("latitude"));
                        var longitude = Double.parseDouble(request.queryParams("longitude"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(parcelLockerService.findNearestLocker(latitude, longitude));
                    },
                    responseTransformer
            );

            /*
             * Route to get a specific parcel locker by ID.
             *
             * @param id The ID of the parcel locker to retrieve.
             * @return A JSON response containing the parcel locker with the specified ID.
             * @throws IllegalArgumentException if the parcel locker with the given ID does not exist.
             */
            get(
                    "/:id",
                    (request, response) -> {
                        var id = Long.parseLong(request.params("id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(parcelLockerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Parcel locker not found")));
                    },
                    responseTransformer
            );


            /*
             * Route to create a new reservation.
             *
             * @param request The HTTP request containing reservation details in the request body.
             * @return A JSON response containing the created reservation.
             * @throws IllegalStateException if there is an issue with the reservation process (e.g., locker or parcel issues).
             */
            post(
                    "/reservations",
                    (request, response) -> {
                        var reservationRequestDto = gson.fromJson(request.body(), ReservationRequestDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(201);
                        return new ResponseDto<>(parcelLockerService.makeReservation(reservationRequestDto));
                    },
                    responseTransformer
            );
        });


        /*
          Global exception handler for RuntimeException.

          @param ex The exception that was thrown.
         * @param request The HTTP request that caused the exception.
         * @param response The HTTP response to be sent.
         */
        exception(RuntimeException.class, (ex, request, response) -> {
            var exceptionMessage = ex.getMessage();
            logger.error("Exception occurred: " + exceptionMessage); // Log the exception
            response.redirect("/error?msg=" + exceptionMessage, 301); // Redirect to error page with message
        });

        /*
          Route to handle error responses.
         */
        path("/error", () ->
                get(
                        "",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            response.status(500); // Set HTTP status to 500 (Internal Server Error)
                            var message = request.queryParams("msg");
                            return new ResponseDto<>(message); // Return error message in response
                        },
                        responseTransformer
                )
        );

        /*
          Global handler for internal server errors.

          @param request The HTTP request that caused the error.
         * @param response The HTTP response to be sent.
         */
        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(new ResponseDto<>("Internal Server Error")); // Return generic server error message
        });

        /*
          Global handler for not found errors.

          @param request The HTTP request that caused the error.
         * @param response The HTTP response to be sent.
         */
        notFound((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(new ResponseDto<>("Not found")); // Return not found message
        });
    }
}
