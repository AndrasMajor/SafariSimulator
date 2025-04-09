package safariSimulator.main.Models.Entity.Human;

import safariSimulator.main.Models.Point;

public class Tourist {
    private final int impressLevel; // 0 to 3, based on ticket price
    private double rating;          // 0.0 to 5.0

    public Tourist(int impressLevel) {
        this.impressLevel = impressLevel;
        this.rating = 0.0; // not rated yet
    }

    public int getImpressLevel() {
        return impressLevel;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = Math.max(0, Math.min(5, rating)); // Clamp to 0–5
    }

    public void incrementRating(double increment) {
        this.rating = Math.max(0, Math.min(5, this.rating + increment)); // Clamp to 0–5
    }

    // Later: add logic for rating generation based on experience
}
