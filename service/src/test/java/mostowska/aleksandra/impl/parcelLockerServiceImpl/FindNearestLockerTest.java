package mostowska.aleksandra.impl.parcelLockerServiceImpl;

import mostowska.aleksandra.impl.ParcelLockerServiceImpl;
import mostowska.aleksandra.model.ParcelLocker;
import mostowska.aleksandra.repository.ParcelLockerRepository;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class FindNearestLockerTest {
    @Mock
    private ParcelLockerRepository parcelLockerRepository;

    @InjectMocks
    private ParcelLockerServiceImpl parcelLockerService;


    @TestFactory
    Stream<DynamicTest> testFindNearestLocker() {
        var locker1 = new ParcelLocker(1, "Locker A", 52.2296756, 21.0122287, 20L, 5L);
        var locker2 = new ParcelLocker(2, "Locker B", 52.406374, 16.9251681, 30L, 10L);


        return Stream.of(
                DynamicTest.dynamicTest(
                        "Test when finding the nearest locker",
                        () -> {
                            when(parcelLockerRepository.findAll()).thenReturn(List.of(locker1, locker2));
                            assertEquals(locker2, parcelLockerService.findNearestLocker(52.406374, 16.9251681));
                            verify(parcelLockerRepository, times(1)).findAll();
                        }
                ),
                DynamicTest.dynamicTest(
                        "Test when no lockers are available",
                        () -> {
                            when(parcelLockerRepository.findAll()).thenReturn(List.of());
                            assertThrows(IllegalArgumentException.class, () ->
                                    parcelLockerService.findNearestLocker(52.406374, 16.9251681));
                        }
                ),
                DynamicTest.dynamicTest(
                        "Timeout test for method findNearestLocker",
                        () -> {
                            when(parcelLockerRepository.findAll()).thenReturn(List.of(locker1, locker2));
                            assertTimeout(Duration.ofMillis(1000), () ->
                                    parcelLockerService.findNearestLocker(52.406374, 16.9251681));
                        }
                )
        );
    }
}
