package runTrackerv3;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RunHistory {

	private String runID;
	private String userID;
	private float distance;
	private float time;
	private String runDate;
	private float pace;

	public RunHistory(String runID, String userID, float distance, float time, String runDate, float pace) {
		this.runID = runID;
		this.userID = userID;
		this.distance = distance;
		this.time = time;
		this.runDate = runDate;
		this.pace = pace;
	}

	public RunHistory() {
		this.runID = "";
		this.userID = "";
		this.distance = 0;
		this.time = 0;
		this.runDate = null;
		this.pace = 0;
	}

	public String getRunID() {
		return runID;
	}

	public void setRunID(String runID) {
		this.runID = runID;
	}

	public String getUser() {
		return userID;
	}

	public void setUser(String userID) {
		this.userID = userID;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}

	public float getPace() {
		return pace;
	}

	public void setPace(float pace) {
		this.pace = pace;
	}

	// Rest of the methods, including getMets(), toString(), etc.

	public double getMets() {
		// Create a mapping of pace values (in minutes per mile) to METs
		Map<Double, Double> paceToMets = new HashMap<>();
		paceToMets.put(13.0, 6.0); // 13 minutes per mile pace -> 6 METs
		paceToMets.put(12.0, 8.3); // 12 minutes per mile pace -> 8.3 METs
		paceToMets.put(11.5, 9.0); // 11.5 minutes per mile pace -> 9.0 METs
		paceToMets.put(10.0, 9.8); // 10 minutes per mile pace -> 9.8 METs
		paceToMets.put(9.0, 10.5); // 9 minutes per mile pace -> 10.5 METs
		paceToMets.put(8.5, 11.0); // 8.5 minutes per mile pace -> 11.0 METs
		paceToMets.put(8.0, 11.5); // 8 minutes per mile pace -> 11.5 METs
		paceToMets.put(7.5, 11.8); // 7.5 minutes per mile pace -> 11.8 METs
		paceToMets.put(7.0, 12.3); // 7 minutes per mile pace -> 12.3 METs
		paceToMets.put(6.5, 12.8); // 6.5 minutes per mile pace -> 12.8 METs

		// Find the corresponding MET value for the pace
		double metValue = paceToMets.getOrDefault(pace, 0.0);

		// If the pace is not an exact match, find the lower bound MET value
		if (metValue == 0.0) {
			Double lowerBoundMets = null;
			for (Map.Entry<Double, Double> entry : paceToMets.entrySet()) {
				double paceValue = entry.getKey();
				if (paceValue <= pace) {
					lowerBoundMets = entry.getValue();
				} else {
					break; // Stop searching when the current pace is greater than the target pace
				}
			}

			if (lowerBoundMets != null) {
				metValue = lowerBoundMets;
			}
		}

		return metValue;
	}

	@Override
	public String toString() {
		return "RunID: " + runID + " UserID: " + userID + " Distance traveled: " + String.format("%.2f", distance)
				+ " Time spent running: " + time + " Pace: " + pace + " Date: " + runDate + "\n";
	}
}
