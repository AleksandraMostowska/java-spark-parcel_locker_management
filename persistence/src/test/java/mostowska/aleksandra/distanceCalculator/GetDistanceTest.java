package mostowska.aleksandra.distanceCalculator;

import mostowska.aleksandra.DistanceCalculator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetDistanceTest {

    @TestFactory
    public Stream<DynamicTest> testGetDistance() {
        return Stream.of(
                DynamicTest.dynamicTest("testGetDistanceBetweenTwoPoints",
                        () -> assertEquals(1315.51, DistanceCalculator.getDistance(
                                52.2296756, 21.0122287, 41.8919300, 12.5113300), 0.01)),

                DynamicTest.dynamicTest("testGetDistanceSamePoint",
                        () -> assertEquals(0.0, DistanceCalculator.getDistance(
                                52.2296756, 21.0122287, 52.2296756, 21.0122287), 0.01)),

                DynamicTest.dynamicTest("testGetDistanceEquator",
                        () -> assertEquals(1111.95, DistanceCalculator.getDistance(
                                0.0, 0.0, 0.0, 10.0), 0.01)),

                DynamicTest.dynamicTest("testGetDistanceAntiMeridian",
                        () -> assertEquals(222.38, DistanceCalculator.getDistance(
                                0.0, 179.0, 0.0, -179.0), 0.01))
        );
    }
}

