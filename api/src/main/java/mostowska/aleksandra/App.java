package mostowska.aleksandra;

import mostowska.aleksandra.config.AppConfig;
import mostowska.aleksandra.db_management.DbManagement;
import mostowska.aleksandra.db_management.model.RowData;
import mostowska.aleksandra.model.utils.ParcelStatus;
import mostowska.aleksandra.repository.impl.ParcelLockerRepositoryImpl;
import mostowska.aleksandra.repository.impl.ReservationRepositoryImpl;
import mostowska.aleksandra.router.ParcelLockerRouter;
import mostowska.aleksandra.router.ReservationRouter;
import mostowska.aleksandra.router.UserRouter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.*;

import java.util.List;


/**
 * Entry point for the application, responsible for initializing the Spring context,
 * setting up routes, and performing initial database operations.
 * The main method configures the web server and Spring application context,
 * manages database setup, and initializes routers for handling HTTP requests.
 */
public class App {
    public static void main(String[] args) {

        initExceptionHandler(err -> System.out.println(err.getMessage()));
        port(8080);

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        List<RowData<?>> parcelLockerData = List.of(
                new RowData<>("location_name", "Warehouse 3"),
                new RowData<>("latitude", 32.2296756),
                new RowData<>("longitude", 16.0122287),
                new RowData<>("total_lockers", 90L),
                new RowData<>("available_lockers", 78L)
        );

        List<RowData<?>> parcelData = List.of(
                new RowData<>("id", 4),
                new RowData<>("parcel_locker_id", 1),
                new RowData<>("locker_number", 99L),
                new RowData<>("status", ParcelStatus.AVAILABLE),
                new RowData<>("width", 45.0),
                new RowData<>("length", 20.0),
                new RowData<>("height", 30.0)
        );

        List<RowData<?>> userData = List.of(
                new RowData<>("id", 4),
                new RowData<>("username", "sophie_jackson_123"),
                new RowData<>("email", "jackson_s00@example.com"),
                new RowData<>("phone_number", 000000000L)
        );


        var dbManagement = context.getBean("dbManagement", DbManagement.class);
//        dbManagement.insert("parcels", parcelData);


        var parcelLockerServiceImpl = context.getBean("parcelLockerServiceImpl", ParcelLockerService.class);

        // Retrieve and configure routers
        var parcelLockerRouter = context.getBean("parcelLockerRouter", ParcelLockerRouter.class);
        parcelLockerRouter.routes();
        var reservationRouter = context.getBean("reservationRouter", ReservationRouter.class);
        reservationRouter.routes();
        var userRouter = context.getBean("userRouter", UserRouter.class);
        userRouter.routes();

        var reservationRepositoryImpl = context.getBean("reservationRepositoryImpl", ReservationRepositoryImpl.class);
        var parcelLockerRepositoryImpl = context.getBean("parcelLockerRepositoryImpl", ParcelLockerRepositoryImpl.class);

    }
}
