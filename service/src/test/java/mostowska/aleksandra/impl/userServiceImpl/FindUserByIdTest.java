package mostowska.aleksandra.impl.userServiceImpl;

import mostowska.aleksandra.impl.UserServiceImpl;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class FindUserByIdTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @TestFactory
    Stream<DynamicTest> testFindUserById() {
        var user = new User(1, "TestUser", "test@example.com", 1234567890L);

        return Stream.of(
                DynamicTest.dynamicTest(
                        "Test finding existing user by ID",
                        () -> {
                            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

                            assertEquals(user, userService.findUserById(1L));
                            verify(userRepository, times(1)).findById(1L);
                        }
                ),
                DynamicTest.dynamicTest(
                        "Test finding non-existing user by ID",
                        () -> {
                            when(userRepository.findById(2L)).thenReturn(Optional.empty());

                            assertThrows(IllegalArgumentException.class, () ->
                                    userService.findUserById(2L));
                            verify(userRepository, times(1)).findById(2L);
                        }
                ),
                DynamicTest.dynamicTest(
                        "Timeout test for method findUserById",
                        () -> {
                            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

                            assertTimeout(Duration.ofMillis(1000), () ->
                                    userService.findUserById(1L));
                        }
                )
        );
    }
}
