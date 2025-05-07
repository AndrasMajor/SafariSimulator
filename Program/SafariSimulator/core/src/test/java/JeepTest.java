

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Objects.Road;
import safariSimulator.main.Models.Objects.RoadDirection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JeepTest {

    private Map map;
    private Jeep jeep;

    // Dummy concrete Road class for testing
    static class TestRoad extends Road {
        public TestRoad(Point pos, int roadNumber) {
            super(pos, RoadDirection.EAST, RoadDirection.WEST);
            this.roadNumber = roadNumber;
        }
    }

    @BeforeEach
    public void setUp() {
        map = new Map();
        jeep = new Jeep(new Point(1, 1));
        map.getEntities().add(jeep);

        Road road1 = new TestRoad(new Point(1, 1), 0);
        Road road2 = new TestRoad(new Point(2, 1), 1);

        map.setObjects(List.of(road1, road2));
    }

    @Test
    public void testStartTourInitializesJeep() {
        jeep.startTour(2, 3);
        assertEquals(2, jeep.impressLevel);
        assertEquals(3, jeep.getPassengerCount());
        assertEquals(0, jeep.getRating());
    }

    @Test
    public void testEndTourReturnsCorrectRating() {
        jeep.rating = 4.2;
        double result = jeep.endTour();
        assertEquals(4.2, result, 0.01);
        assertEquals(0, jeep.getPassengerCount());
        assertEquals(0, jeep.getRating());
    }

    @Test
    public void testIncrementHappinessCapsAtFive() {
        jeep.rating = 4.9;
        jeep.incrementHappiness(3);
        assertEquals(5.0, jeep.getRating(), 0.01);
    }

    @Test
    public void testMoveWithNoAnimalsMovesToNextRoad() {
        jeep.startTour(1, 2);
        jeep.move(map);
        assertEquals(new Point(2, 1), jeep.getPos());
    }


    @Test
    public void testSearchForNextRoadReturnsCorrectTile() {
        Point next = jeep.searchForNextRoad(map);
        assertEquals(new Point(2, 1), next);
    }
}
