package runTrackerv3;

public class WeeklyStatistics {
	    private double totalDistance;
	    private double totalTime;
	    private double averagePace;
	    private double totalCaloriesBurned;

	    public WeeklyStatistics(double totalDistance, double totalTime, double averagePace, double totalCaloriesBurned) {
	        this.totalDistance = totalDistance;
	        this.totalTime = totalTime;
	        this.averagePace = averagePace;
	        this.totalCaloriesBurned = totalCaloriesBurned;
	    }
	    public WeeklyStatistics() {
	        this.totalDistance = 0;
	        this.totalTime = 0;
	        this.averagePace = 0;
	        this.totalCaloriesBurned = 0;
	    }

	    // Getters for the fields
	    public double getTotalDistance() {
	        return totalDistance;
	    }

	    public double getTotalTime() {
	        return totalTime;
	    }

	    public double getAveragePace() {
	        return averagePace;
	    }

	    public double getTotalCaloriesBurned() {
	        return totalCaloriesBurned;
	    }
	}
