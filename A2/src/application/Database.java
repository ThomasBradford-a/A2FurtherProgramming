package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	// constrants for table names
	public static final String USER_TABLE = "User";
	public static final String POSTS_TABLE = "Posts";

	// List to store post objects
	private static List<Post> posts = new ArrayList<>();

	public static void main(String[] args) {
		try (Connection con = DatabaseConnection.getConnection(); Statement stmt = con.createStatement()) {
			 // Create User table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (Username VARCHAR(255) NOT NULL,"
					+ " Password VARCHAR(255) NOT NULL," + " Firstname VARCHAR(255) NOT NULL,"
					+ " Lastname VARCHAR(255) NOT NULL," + " IsVIP BOOLEAN NOT NULL," + " PRIMARY KEY (Username))");
			 // Create Posts table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + POSTS_TABLE + " (PostID INTEGER PRIMARY KEY,"
					+ " Content TEXT NOT NULL," + " Author VARCHAR(255) NOT NULL," + " Likes INTEGER NOT NULL,"
					+ " Shares INTEGER NOT NULL," + " Date_Time TEXT NOT NULL," + " Main_Post_ID INTEGER NOT NULL,"
					+ " FOREIGN KEY (Author) REFERENCES " + USER_TABLE + "(Username))");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Post retrievePost(int postID) {
		// query to retrieve a post based on PostID
		String sql = "SELECT * FROM " + POSTS_TABLE + " WHERE PostID = ?";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, postID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// if the Post is found, create a Post object and return it
				int postIdResult = resultSet.getInt("PostID");
				String content = resultSet.getString("Content");
				String author = resultSet.getString("Author");
				int likes = resultSet.getInt("Likes");
				int shares = resultSet.getInt("Shares");
				String dateTime = resultSet.getString("Date_Time");
				int mainPostId = resultSet.getInt("Main_Post_ID");

				return new Post(postIdResult, content, author, likes, shares, dateTime, mainPostId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void removePostFromCollection(String postId) {
		// query to delete a post from the Posts table
		String sql = "DELETE FROM Posts WHERE PostID = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, postId);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean updateUserProfile(String firstName, String lastName, String password) {
		String sql = "UPDATE User SET FirstName = ?, LastName = ?, Password = ? WHERE Username = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, UserManager.getLoggedInUser().GetUsername()); // Add this line

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Post> getTopPosts(int N) {
		List<Post> topPosts = new ArrayList<>();

		String sql = "SELECT * FROM Posts ORDER BY Likes DESC LIMIT ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, N);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int postId = resultSet.getInt("PostID");
					String content = resultSet.getString("Content");
					String author = resultSet.getString("Author");
					int likes = resultSet.getInt("Likes");
					int shares = resultSet.getInt("Shares");
					String dateTime = resultSet.getString("Date_Time");
					int mainPostId = resultSet.getInt("Main_Post_ID");

					Post post = new Post(postId, content, author, likes, shares, dateTime, mainPostId);
					topPosts.add(post);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return topPosts;
	}

	public static List<Post> getReplies(int postId) {
		List<Post> replies = new ArrayList<>();

		String sql = "SELECT * FROM " + POSTS_TABLE + " WHERE Main_Post_ID = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, postId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int replyId = resultSet.getInt("PostID");
					String content = resultSet.getString("Content");
					String author = resultSet.getString("Author");
					int likes = resultSet.getInt("Likes");
					int shares = resultSet.getInt("Shares");
					String dateTime = resultSet.getString("Date_Time");
					int mainPostId = resultSet.getInt("Main_Post_ID");

					Post reply = new Post(replyId, content, author, likes, shares, dateTime, mainPostId);
					replies.add(reply);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return replies;
	}

	public static Map<String, Long> getShareDistribution() {
		Map<String, Long> shareDistribution = new HashMap<>();

		// Retrieve all posts from the database
		List<Post> allPosts = getAllPosts();

		// Categorize posts by the number of shares
		for (Post post : allPosts) {
			int shares = post.getShares();

			// Determine the category based on the number of shares
			String category;
			if (shares >= 1000) {
				category = "1000+";
			} else if (shares >= 100) {
				category = "100-999";
			} else {
				category = "0-99";
			}

			// Update the count in the map
			shareDistribution.put(category, shareDistribution.getOrDefault(category, 0L) + 1);
		}

		return shareDistribution;
	}

	private static List<Post> getAllPosts() {
		List<Post> allPosts = new ArrayList<>();

		String sql = "SELECT * FROM " + Database.POSTS_TABLE;

		try (Connection connection = DatabaseConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				int postId = resultSet.getInt("PostID");
				String content = resultSet.getString("Content");
				String author = resultSet.getString("Author");
				int likes = resultSet.getInt("Likes");
				int shares = resultSet.getInt("Shares");
				String dateTime = resultSet.getString("Date_Time");
				int mainPostId = resultSet.getInt("Main_Post_ID");

				Post post = new Post(postId, content, author, likes, shares, dateTime, mainPostId);
				allPosts.add(post);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return allPosts;
	}

}
