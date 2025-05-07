import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.GameClock;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class GameClockTest {

    private GameClock gameClock;
    private LocalDateTime baseTime;

    @BeforeEach
    public void setUp() {
        baseTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        gameClock = new GameClock(baseTime);
    }

    @AfterEach
    public void tearDown() {
        gameClock.stop();
    }

    @Test
    public void testPauseResume() {
        gameClock.pause();
        assertTrue(gameClock.isPaused());

        gameClock.resume();
        assertFalse(gameClock.isPaused());
    }

    @Test
    public void testSetSpeedToHourPerSecond() {
        gameClock.setSpeedToHourPerSecond();
        assertEquals(Duration.ofHours(1), gameClock.getIncrementPerTick());
    }

    @Test
    public void testSetSpeedToDayPerSecond() {
        gameClock.setSpeedToDayPerSecond();
        assertEquals(Duration.ofDays(1), gameClock.getIncrementPerTick());
    }

    @Test
    public void testSetSpeedToWeekPerSecond() {
        gameClock.setSpeedToWeekPerSecond();
        assertEquals(Duration.ofDays(7), gameClock.getIncrementPerTick());
    }

    @Test
    public void testResetSpeed() {
        gameClock.setSpeedToDayPerSecond();
        gameClock.resetSpeed();
        assertEquals(Duration.ofHours(1), gameClock.getIncrementPerTick());
    }

    @Test
    public void testGetFormattedTime() {
        String formatted = gameClock.getFormattedTime();
        assertTrue(formatted.startsWith("Week "));
    }

    @Test
    public void testTimeSpeedMultiplier() {
        gameClock.setSpeedToHourPerSecond();
        assertEquals(1f, gameClock.getTimeSpeedMultiplier());

        gameClock.setSpeedToDayPerSecond();
        assertEquals(24f, gameClock.getTimeSpeedMultiplier());

        gameClock.setSpeedToWeekPerSecond();
        assertEquals(168f, gameClock.getTimeSpeedMultiplier());
    }

    @Test
    public void testSecondsPerTick() {
        gameClock.setSpeedToHourPerSecond();
        assertEquals(3600f, gameClock.getSecondsPerTick());

        gameClock.setSpeedToDayPerSecond();
        assertEquals(86400f, gameClock.getSecondsPerTick());
    }

    @Test
    public void testConstructorFromString() {
        GameClock clock = new GameClock("2024-01-01T00:00:00", 2.5f, false);
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), clock.getCurrentTime());
        assertEquals(2.5f, clock.getSecondsPerTick(), 0.001);
        clock.stop();
    }
}
