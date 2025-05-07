import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Objects.*;

import static org.junit.jupiter.api.Assertions.*;

public class RoadTest {

    @Test
    public void testRoadDirectionMovement() {
        Point origin = new Point(5, 5);

        assertEquals(new Point(5, 6), RoadDirection.NORTH.move(origin));
        assertEquals(new Point(6, 5), RoadDirection.EAST.move(origin));
        assertEquals(new Point(5, 4), RoadDirection.SOUTH.move(origin));
        assertEquals(new Point(4, 5), RoadDirection.WEST.move(origin));
    }

    @Test
    public void testRoadBuildTypeNext() {
        assertEquals(RoadBuildType.STRAIGHT_WE, RoadBuildType.STRAIGHT_NS.next());
        assertEquals(RoadBuildType.TURN_NE, RoadBuildType.STRAIGHT_WE.next());
        assertEquals(RoadBuildType.STRAIGHT_NS, RoadBuildType.TURN_SW.next());
    }

    @Test
    public void testRoadBuildTypeDirections() {
        RoadBuildType turn = RoadBuildType.TURN_NE;
        assertEquals(RoadDirection.NORTH, turn.getFrom());
        assertEquals(RoadDirection.EAST, turn.getTo());
    }

    @Test
    public void testEntranceExitRoadConstructor() {
        Point point = new Point(0, 1);
        EntranceExitRoad entrance = new EntranceExitRoad(point, true);
        EntranceExitRoad exit = new EntranceExitRoad(new Point(49, 1), false);

        assertEquals(point, entrance.getPos());
        assertTrue(entrance.isEntrance);
        assertFalse(exit.isEntrance);
        assertFalse(entrance.sellable);
        assertEquals(0, entrance.price);
        assertArrayEquals(new RoadDirection[]{RoadDirection.EAST, RoadDirection.WEST}, entrance.direction);
    }
}
