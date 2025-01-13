package runTrackerv3;

import java.util.ArrayList;

public class Client {
	private String userID;
	private String fullName;
	private int age;
	private int weight;
	private ArrayList<RunHistory> Runs;

	public Client(String userID, String fullName, int age, int weight) {
		super();
		this.userID = userID;
		this.fullName = fullName;
		this.age = age;
		this.setRuns(new ArrayList<>());
		this.weight = weight;
	}

	public String getUser() {
		return userID;
	}

	public void setUser(String userID) {
		this.userID = userID;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getFullName() {
		return fullName;
	}

	public int getAge() {
		return age;
	}

	public int getWeight() {
		return weight;
	}

	public ArrayList<RunHistory> getRuns() {
		return new ArrayList<>(Runs);
	}

	public void setRuns(ArrayList<RunHistory> runs) {
		this.Runs = new ArrayList<>(runs);
	}

	public void addRun(RunHistory run) {
		Runs.add(run);
	}

	@Override
	public String toString() {
		return "Client{" + "userID='" + userID + '\'' + ", fullName='" + fullName + '\'' + ", age=" + age
				+ ", weight=" + weight + '}';
	}
}
