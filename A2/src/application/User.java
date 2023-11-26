package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// define user attributes
public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private boolean isVIP;
	private List<Post> posts;

	// contructor to initilaise user attributes
	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isVIP = false; // Initially, the user is not a VIP
		this.posts = new ArrayList<>();
	}

	// getters and setters
	public String GetUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String GetPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String GetFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String GetLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public boolean getVIP() {
		return isVIP;
	}

	public void setVIP(boolean VIP) {
		this.isVIP = VIP;
	}

	// Method to save VIP status to the database
	public void saveVIPStatusToDatabase() {
		System.out.println("Saving VIP status to database. Username: " + username + ", VIP Status: " + isVIP);

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("UPDATE users SET isVIP = ? WHERE id = ?")) {
			statement.setBoolean(1, isVIP);
			statement.setString(2, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to check VIP status from the database
	public static boolean checkVIPStatusFromDatabase(String username) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT isVIP FROM users WHERE username = ?")) {
			preparedStatement.setString(1, username);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("isVIP") == 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}