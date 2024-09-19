package mostowska.aleksandra.impl.userServiceImpl;

import mostowska.aleksandra.impl.UserServiceImpl;
import mostowska.aleksandra.model.Reservation;
import mostowska.aleksandra.model.User;
import mostowska.aleksandra.repository.UserRepository;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class FindAllUsersReservationsTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @TestFactory
    Stream<DynamicTest> testFindAllUsersReservations() {
        var user = new User(1, "TestUser", "test@example.com", 1234567890L);
        var testReservations = List.of(
                new Reservation(1, 1, 100, LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)),
                new Reservation(2, 1, 101, LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1)));

        return Stream.of(
                DynamicTest.dynamicTest(
                        "Test finding all reservations for existing user",
                        () -> {
                            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                            when(userRepository.findUsersReservations(1)).thenReturn(testReservations);

                            assertEquals(testReservations, userService.findAllUsersReservations(1L));
                            verify(userRepository, times(1)).findById(1L);
                            verify(userRepository, times(1)).findUsersReservations(1);
                        }
                ),
                DynamicTest.dynamicTest(
                        "Test finding reservations for non-existing user",
                        () -> {
                            when(userRepository.findById(2L)).thenReturn(Optional.empty());

                            assertThrows(IllegalArgumentException.class, () ->
                                    userService.findAllUsersReservations(2L));
                            verify(userRepository, times(1)).findById(2L);
                        }
                ),
                DynamicTest.dynamicTest(
                        "Timeout test for method findAllUsersReservations",
                        () -> {
                            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                            when(userRepository.findUsersReservations(1)).thenReturn(testReservations);

                            assertTimeout(Duration.ofMillis(1000), () ->
                                    userService.findAllUsersReservations(1L));
                        }
                )
        );
    }
}
