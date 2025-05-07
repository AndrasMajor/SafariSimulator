import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Mover;
import safariSimulator.main.Models.Point;

import static org.junit.jupiter.api.Assertions.*;

public class MoverTest {

    private Mover mover;
    private Point start;
    private Point end;

    @BeforeEach
    public void setup() {
        start = new Point(0, 0);
        end = new Point(10, 0);
        mover = new Mover(start, end, 2.0f); // 2 seconds duration
    }

    @Test
    public void testInitialProgressIsZero() {
        assertEquals(0.0f, mover.progress);
    }

    @Test
    public void testUpdateIncreasesProgress() {
        mover.update(1.0f); // 1 second
        assertEquals(0.5f, mover.progress, 0.01f);

        mover.update(0.5f); // 0.5 more seconds
        assertEquals(0.75f, mover.progress, 0.01f);
    }

    @Test
    public void testProgressClampsToOne() {
        mover.update(5.0f); // way over duration
        assertEquals(1.0f, mover.progress, 0.0001f);
        assertTrue(mover.isComplete());
    }

    @Test
    public void testInterpolatedX() {
        mover.update(1.0f); // 50% progress
        float interpolatedX = mover.getInterpolatedX();
        assertEquals(5.0f, interpolatedX, 0.01f);
    }

    @Test
    public void testInterpolatedY() {
        mover.update(1.0f);
        float interpolatedY = mover.getInterpolatedY();
        assertEquals(0.0f, interpolatedY, 0.01f);
    }

    @Test
    public void testAngleCalculation() {
        float angle = mover.getAngleDeg();
        assertEquals(0.0f, angle, 0.01f); // movement is horizontal
    }

    @Test
    public void testAngleDiagonal() {
        Mover diagonal = new Mover(new Point(0, 0), new Point(10, 10), 1f);
        assertEquals(45.0f, diagonal.getAngleDeg(), 0.01f);
    }
}
