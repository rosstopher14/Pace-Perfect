package runTrackerv3;

import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

public class PacePerfect {
	Scanner reader;
	String userID;
	Hashtable<String, String> hashtable;
	ArrayList<Client> clients;

	// Database objects
	Connection connection;
	Statement statement;
	ResultSet resultSet;

	public PacePerfect() { // default constructor
		clients = new ArrayList<Client>();
		reader = new Scanner(System.in, "utf-8");
		hashtable = new Hashtable<>();
		userID = "";

		connection = null;
		statement = null;
		resultSet = null;
	}

	void connectDB() throws ClassNotFoundException, SQLException {

		// Step 1: Loading or registering JDBC driver class
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

		// Step 2: Opening database connection
		String msAccDB = "runtracker.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAccDB;

		// Step 3: Create and get connection using DriverManager class
		connection = DriverManager.getConnection(dbURL);

		// Step 4: Creating JDBC Statement - scrollable (use next() and last()) &
		// updatable (enter records)
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		System.out.println("Database Connected!");
	}

	void loadData() throws SQLException {
		loadHashtable();
		loadClients();
		loadRuns();

	}

	/*******************************
	 * START LOAD USER INFO TO HASH
	 **************************************/

	void loadHashtable() throws SQLException {
		String userID = "", password = "";
		int totalrows = 0;

		// Get the total rows in the table to loop through the result set
		resultSet = statement.executeQuery("SELECT * FROM HASH");

		while (resultSet.next()) {
			totalrows = resultSet.getRow();

			userID = resultSet.getString(1);
			password = resultSet.getString(2);

			// Add the product aisles to the hashtable
			hashtable.put(userID, password);
		} // fin. retrieving from table...
		System.out.println("Hashtable loaded!");

	}

	/*******************************
	 * END LOAD USER INFO TO HASH
	 **************************************/

	/*******************************
	 * START LOGIN
	 * 
	 * @throws SQLException
	 **************************************/

	void login() throws IOException, SQLException {

		String user = "", pass = "", enteredpass = "";
		int numattempts = 0;
		boolean loginValid = false;

		System.out.println("Welcome to Pace Perfect");

		do {
			System.out.print("Enter your userID: ");
			user = reader.nextLine();

			if (!hashtable.containsKey(user)) {
				System.out.println("Username is Invalid!");
			} else {
				System.out.print("Enter your password: ");
				enteredpass = reader.nextLine();
				pass = hashtable.get(user);
				if (!enteredpass.equals(pass)) {
					System.out.println("Password is Invalid!");
				} else {
					loginValid = true;
					userID = user;
				}
			}
			numattempts++;
		} while ((numattempts < 3) && (!loginValid));
		if (!loginValid) {
			System.out.println("Error: Number of attempts exceeded, program terminated!");
			System.exit(100);
		}
		userID = user;
		System.out.println("Login success!");
		displayMenu();

	}
	void login(String user, String enteredpass) throws IOException, SQLException {

		String pass = "";
		int numattempts = 0;
		boolean loginValid = false;

		System.out.println("Welcome to Pace Perfect");

		do {

			if (!hashtable.containsKey(user)) {
				System.out.println("Username is Invalid!");
			} else {
				pass = hashtable.get(user);
				if (!enteredpass.equals(pass)) {
					System.out.println("Password is Invalid!");
				} else {
					loginValid = true;
					userID = user;
				}
			}
			numattempts++;
		} while ((numattempts < 3) && (!loginValid));
		if (!loginValid) {
			System.out.println("Error: Number of attempts exceeded, program terminated!");
			System.exit(100);
		}
		userID = user;
		System.out.println("Login success!");
		displayMenu();
	}
	
	

	/*******************************
	 * END LOGIN
	 **************************************/

	/*******************************
	 * START LOAD CLIENT
	 **************************************/

	void loadClients() throws SQLException {

		String fullname = "", userID = "";
		int weight, age; // create variables for each field
		int totalrows = 0;

		resultSet = statement.executeQuery("SELECT * FROM RUNNER");

		while (resultSet.next()) {
			totalrows = resultSet.getRow();

			userID = resultSet.getString(1);
			fullname = resultSet.getString(2);
			age = resultSet.getInt(3);
			weight = resultSet.getInt(4);

			clients.add(new Client(userID, fullname, age, weight));
		}
		System.out.println("Clients loaded!");
	}

	/*******************************
	 * END LOAD CLIENT
	 **************************************/

	/*******************************
	 * START LOAD RUNS
	 **************************************/
	void loadRuns() throws SQLException {

		// create the variables for each field in the file
		String runID;
		String user;
		String tempDistance;
		String tempTime;
		float distance;
		float time;
		String runDate;
		String tempPace;
		float pace;
		RunHistory run;
		int totalrows;

		resultSet = statement.executeQuery("SELECT * FROM runHistory");

		while (resultSet.next()) {
			totalrows = resultSet.getRow();

			runID = resultSet.getString(1);
			user = resultSet.getString(2);
			tempDistance = resultSet.getString(3);
			tempTime = resultSet.getString(4);
			runDate = resultSet.getString(5);
			tempPace = resultSet.getString(6);

			distance = Float.parseFloat(tempDistance);
			time = Float.parseFloat(tempTime);
			pace = Float.parseFloat(tempPace);

			run = new RunHistory(runID, user, distance, time, runDate, pace);

			for (int i = 0; i < clients.size(); i++) {
				if (clients.get(i).getUser().equals(run.getUser())) {
					clients.get(i).addRun(run);
				}
			}

		}

		System.out.println("Runs Loaded!");
	}

	/*******************************
	 * END LOAD RUNS
	 **************************************/

	/*******************************
	 * START DISPLAY MENU
	 * 
	 * @throws SQLException
	 **************************************/

	void displayMenu() throws IOException, SQLException {
		int menuChoice = 0;
		System.out.println("******************MEMBER MENU*********************");

		System.out.println("1. Add a Run!");
		System.out.println("2. See your progress!");
		System.out.println("3. Check your running history!");
		System.out.println("4. Test weekly recommendations");
		System.out.println("5. Change Weight");
		System.out.println("6. Exit");
		System.out.print("Enter Choice: ");
		menuChoice = reader.nextInt();

		switch (menuChoice) {
		case 1: {
			try {
				Entry();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 2: {
			try {
				CaloriesBurnt();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 3: {
			ViewRuns();
			break;
		}
		case 4: {
			generateWeeklyRecommendations(); // Add this option for recommendations
			break;
		}
		case 5: {
			changeWeight();
			break;
		}
		case 6: {
			System.out.println("Thank you for using Pace Perfect, program terminated!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}

		}

	}
	
	void displayMenu(String user, JTextArea textArea) throws IOException, SQLException {
		int menuChoice = 0;
		textArea.append("******************MEMBER MENU*********************\n");
	    textArea.append("1. Add a Run!\n");
	    textArea.append("2. See your progress!\n");
	    textArea.append("3. Check your running history!\n");
	    textArea.append("4. Test weekly recommendations\n");
	    textArea.append("5. Change Weight\n");
	    textArea.append("6. Exit\n");
	    textArea.append("Enter Choice: ");
		menuChoice = reader.nextInt();

		switch (menuChoice) {
		case 1: {
			try {
				Entry();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 2: {
			try {
				CaloriesBurnt();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 3: {
			ViewRuns();
			break;
		}
		case 4: {
			generateWeeklyRecommendations(); // Add this option for recommendations
			break;
		}
		case 5: {
			changeWeight();
			break;
		}
		case 6: {
			System.out.println("Thank you for using Pace Perfect, program terminated!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}

		}

	}

	/*******************************
	 * END DISPLAY MENU
	 * 
	 * @throws SQLException
	 **************************************/

	private void ViewRuns() throws IOException, SQLException {
		int menuChoice;
		String user = "";
		System.out.println("Your run history: ");
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getUser().equals(userID)) {
				String runs = clients.get(i).getRuns().toString().replace(", ", "");
				runs = runs.substring(1, runs.length() - 1); // remove the square brackets
				System.out.println(runs);
			}
		}
		System.out.println("Would you like to go back to the display menu?\n1.Yes 2.No");
		menuChoice = reader.nextInt();
		switch (menuChoice) {
		case 1: {
			try {
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 2: {
			System.out.println("Thank you for using Pace Perfect!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}
		}
	}

	private void changeWeight() throws SQLException {
		int menuChoice;
		int weight;

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getUser().equals(userID)) {
				System.out.println("Please enter your current weight:");
				weight = reader.nextInt();
				if (clients.get(i).getWeight() > weight) {
					System.out.println("Great Job! You lost " + (clients.get(i).getWeight() - weight) + " pounds!");
				} else {
					System.out.println("You gained " + (weight - clients.get(i).getWeight()) + " pounds.");
				}
				System.out.println("Would you like to save your new weight?\n1.Yes 2.No");
				menuChoice = reader.nextInt();
				switch (menuChoice) {
				case 1: {
					clients.get(i).setWeight(weight);
					System.out.println("Weight Saved!\nWould you like to go back to the display menu?\n1.Yes 2.No");
					menuChoice = reader.nextInt();
					switch (menuChoice) {
					case 1: {
						try {
							displayMenu();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case 2: {
						System.out.println("Thank you for using Pace Perfect!");
						System.exit(0);
						break;
					}
					default: {
						System.out.println("Error, invalid choice!");
						break;
					}
					}
					break;
				}
				case 2: {
					System.out.println("Weight not saved.\nWould you like to go back to the display menu?\n1.Yes 2.No");
					menuChoice = reader.nextInt();
					switch (menuChoice) {
					case 1: {
						try {
							displayMenu();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case 2: {
						System.out.println("Thank you for using Pace Perfect!");
						System.exit(0);
						break;
					}
					default: {
						System.out.println("Error, invalid choice!");
						break;
					}
					}
					break;
				}
				default: {
					System.out.println("Error, invalid choice!");
					break;
				}
				}

			}

		}

	}

	private void Entry() throws IOException, SQLException {
		int menuChoice;
		float distance;
		float time;
		String runDate;
		float pace;

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getUser().equals(userID)) {
				System.out.println("Please enter the distance you ran in miles: ");
				distance = reader.nextFloat();
				System.out.println("Please enter the amount of time it took you to run that distance: ");
				time = reader.nextFloat();

				// Consume the newline character
				reader.nextLine();

				System.out.println("Enter the date of this run");
				runDate = reader.nextLine();
				pace = time / distance;

				System.out.println("Generated run ID: " + newRunID()); // Debugging line
				RunHistory newRun = new RunHistory(newRunID(), userID, distance, time, runDate, pace);
				System.out.println("New run: " + newRun.toString()); // Debugging line
				clients.get(i).addRun(newRun);
				System.out.println("Adding run to database..."); // Debugging line
				writeRunToDatabase(newRun);
			}

		}

		System.out.println("Would you like to go back to the display menu?\n1.Yes 2.No");
		menuChoice = reader.nextInt();
		switch (menuChoice) {
		case 1: {
			displayMenu();
			break;
		}
		case 2: {
			System.out.println("Thank you for using Pace Perfect!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}
		}
	}

	private void CaloriesBurnt() throws IOException, SQLException {
		int menuChoice;
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getUser().equals(userID)) {
				List<RunHistory> runHistory = clients.get(i).getRuns();

				// Group runs by week
				Map<Integer, List<RunHistory>> runsByWeek = new HashMap<>();
				for (RunHistory run : runHistory) {
					int weekNumber = getWeekNumber(run.getRunDate());
					runsByWeek.computeIfAbsent(weekNumber, k -> new ArrayList<>()).add(run);
				}

				// Calculate calories burnt for each week
				for (Map.Entry<Integer, List<RunHistory>> entry : runsByWeek.entrySet()) {
					int weekNumber = entry.getKey();
					List<RunHistory> runsInWeek = entry.getValue();

					double totalCaloriesBurned = 0.0;
					for (RunHistory run : runsInWeek) {
						double mets = run.getMets();
						double weightInKg = clients.get(i).getWeight() / 2.20462;
						double time = run.getTime();

						double caloriesBurned = mets * weightInKg * time;
						totalCaloriesBurned += caloriesBurned;
					}

					System.out.printf("Week %d - Total Calories Burned: %.2f%n", weekNumber, totalCaloriesBurned);
				}
			}
		}
		System.out.println("Would you like to go back to the display menu?\n1.Yes 2.No");
		menuChoice = reader.nextInt();
		switch (menuChoice) {
		case 1: {
			displayMenu();
			break;
		}
		case 2: {
			System.out.println("Thank you for using Pace Perfect!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}
		}
	}

	private int getWeekNumber(String runDate) {
		LocalDate date = LocalDate.parse(runDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());
	}

	private void generateWeeklyRecommendations() throws IOException, SQLException {
		int menuChoice;
		for (Client client : clients) {
			if (client.getUser().equals(userID)) {
				WeeklyStatistics weeklyStats = calculateWeeklyStatistics(client);
				String recommendation = generateRecommendation(weeklyStats);
				System.out.println(recommendation);
			}
		}
		System.out.println("Would you like to go back to the display menu?\n1.Yes 2.No");
		menuChoice = reader.nextInt();
		switch (menuChoice) {
		case 1: {
			displayMenu();
			break;
		}
		case 2: {
			System.out.println("Thank you for using Pace Perfect!");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Error, invalid choice!");
			break;
		}
		}
	}

	private WeeklyStatistics calculateWeeklyStatistics(Client client) {
		List<RunHistory> clientRuns = client.getRuns();

		LocalDate currentDate = LocalDate.now();
		LocalDate weekStart = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);

		double totalDistance = 0.0;
		double totalTime = 0.0;
		double totalCaloriesBurned = 0.0;

		for (RunHistory run : clientRuns) {
			LocalDate runDate = LocalDate.parse(run.getRunDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			if (runDate.isAfter(weekStart) || runDate.equals(weekStart)) {
				totalDistance += run.getDistance();
				totalTime += run.getTime();
				totalCaloriesBurned += calculateCaloriesBurned(run.getMets(), client.getWeight(), run.getTime());
			}
		}

		double averagePace = totalDistance / totalTime; // Adjust this calculation based on data

		return new WeeklyStatistics(totalDistance, totalTime, averagePace, totalCaloriesBurned);
	}

	private double calculateCaloriesBurned(double mets, double weightInPounds, double timeInMinutes) {
		double weightInKg = weightInPounds / 2.20462;
		double timeInHours = timeInMinutes / 60.0;
		return mets * weightInKg * timeInHours;
	}

	private String generateRecommendation(WeeklyStatistics weeklyStats) {
		String recommendation = "";

		// Check average pace
		if (weeklyStats.getAveragePace() > 9.0) {
			recommendation += "Consider running at a faster pace for better progress. ";
		}

		// Check total distance
		if (weeklyStats.getTotalDistance() < 15.0) {
			recommendation += "Try running longer distances to improve endurance. ";
		}

		// Check total calories burned
		if (weeklyStats.getTotalCaloriesBurned() < 500.0) {
			recommendation += "Increase your running intensity or duration to burn more calories. ";
		}

		// If no specific recommendation is provided, suggest maintaining the current
		// routine
		if (recommendation.isEmpty()) {
			recommendation = "Keep up the good work and maintain your running routine!";
		}

		return recommendation;
	}

	private String newRunID() throws SQLException {
		String latestRunID = "";

		// Establish a connection to the database
		try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://runtracker.accdb")) {
			String selectQuery = "SELECT MAX(RUNID) FROM RUNHISTORY";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					latestRunID = resultSet.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		int nextRunID = Integer.parseInt(latestRunID) + 1;
		return String.format("%04d", nextRunID); // Format as 4-digit string with leading zeros
	}

	private void writeRunToDatabase(RunHistory run) throws SQLException {
		String url = "jdbc:ucanaccess://runtracker.accdb";
		try (Connection connection = DriverManager.getConnection(url)) {
			String insertQuery = "INSERT INTO runHistory (runID, userID, distance, time, runDate, pace) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setString(1, run.getRunID());
				preparedStatement.setString(2, run.getUser());
				preparedStatement.setDouble(3, run.getDistance());
				preparedStatement.setDouble(4, run.getTime());
				preparedStatement.setString(5, run.getRunDate());
				preparedStatement.setDouble(6, run.getPace());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void writeClientsToDatabase(ArrayList<Client> clients) throws SQLException {
		String insertQuery = "INSERT INTO CLIENT (fullname, userID, password, startDate) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://runtracker.accdb");
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

			for (Client client : clients) {
				preparedStatement.setString(1, client.getUser());
				preparedStatement.setString(2, client.getFullName());
				preparedStatement.setInt(3, client.getAge());
				preparedStatement.setInt(4, client.getWeight());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}

