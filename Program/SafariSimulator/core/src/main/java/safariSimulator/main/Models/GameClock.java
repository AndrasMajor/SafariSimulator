package safariSimulator.main.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class GameClock {

    private LocalDateTime gameTime;
    private Duration incrementPerTick;
    private boolean paused;
    private ScheduledExecutorService scheduler;

    public GameClock(LocalDateTime startTime) {
        this.gameTime = startTime;
        this.incrementPerTick = Duration.ofHours(1); // Default: 1 hour per second
        this.paused = false;
        startClock();
    }
    public GameClock(String startTime, float incrementPerTickSeconds, boolean paused) {
        this.gameTime = LocalDateTime.parse(startTime);
        long fullSeconds = (long) incrementPerTickSeconds;
        long nanos = (long) ((incrementPerTickSeconds % 1) * 1_000_000_000);
        this.incrementPerTick = Duration.ofSeconds(fullSeconds, nanos);
        this.paused = paused;
        startClock();
    }

    private void startClock() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (!paused) {
                gameTime = gameTime.plus(incrementPerTick);
            }
        }, 0, 1, TimeUnit.SECONDS); // One real second per tick
    }

    public LocalDateTime getCurrentTime() {
        return gameTime;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setSpeedToHourPerSecond() {
        this.incrementPerTick = Duration.ofHours(1);
    }

    public void setSpeedToDayPerSecond() {
        this.incrementPerTick = Duration.ofDays(1);
    }

    public void setSpeedToWeekPerSecond() {
        this.incrementPerTick = Duration.ofDays(7);
    }

    public void resetSpeed() {
        setSpeedToHourPerSecond();
    }

    public String getFormattedTime() {
        return String.format("Week %d - %s", getWeekOfYear(), gameTime.toString());
    }

    private int getWeekOfYear() {
        return gameTime.get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear());
    }

    public void stop() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    public Duration getIncrementPerTick() {
        return incrementPerTick;
    }

    public float getTimeSpeedMultiplier() {
        long hours = incrementPerTick.toHours();
        if (hours == 0) return 1f; // fallback
        return (float) hours;
    }

    public float getSecondsPerTick() {
        Duration d = getIncrementPerTick();
        return d.getSeconds() + d.getNano() / 1_000_000_000f;
    }



}
