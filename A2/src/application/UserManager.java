package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

	private static User loggedInUser;

	public static User registerUser(String username, String password, String firstName, String lastName) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO User (Username, Password, Firstname, Lastname, IsVIP) VALUES (?, ?, ?, ?, ?)")) {

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);
			preparedStatement.setBoolean(5, false); // Initialize IsVIP to false

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				return new User(username, password, firstName, lastName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // Registration failed
	}
	public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }


	public User loginUser(String username, String password) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM User WHERE Username = ? AND Password = ?")) {

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					 return new User(
					            resultSet.getString("Username"),
					            resultSet.getString("Password"),
					            resultSet.getString("Firstname"),
					            resultSet.getString("Lastname"));
					    }
					    
					    return null; // Login failed
					}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // Login failed
	}
}

// Other methods for user management can be added here
