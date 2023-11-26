package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RemoveController extends MainController {

	@FXML
	private TextField removePostIdTextField;

	public boolean checkIfPostExists(String postId) {

		String sql = "SELECT COUNT(*) FROM " + Database.POSTS_TABLE + " WHERE PostID = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, postId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0; // If count is greater than 0, the post exists
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void removePost(ActionEvent event) {
		String postId = removePostIdTextField.getText();

		// Check if the post exists based on PostID
		if (checkIfPostExists(postId)) {
			// Remove the post from the user's collection and database
			removePostFromCollection(postId);

			// Show a success message
			showAlert(Alert.AlertType.INFORMATION, "Post Removed", "The post has been removed from your collection.");
		} else {
			// Show an error message (Post not found)
			showAlert(Alert.AlertType.ERROR, "Post Not Found",
					"Sorry, you do not have a post with that ID in your collection.");
		}
	}

	// I HAVE LEFT THESE HERE IN HOPES OF DOING SOME UGRADING BUT RAN OUT OF TIME

//	private String getPostAuthor(String postId) {
//	    String sql = "SELECT Author FROM " + Database.POSTS_TABLE_NAME + " WHERE PostID = ?";
//
//	    try (Connection connection = DatabaseConnection.getConnection();
//	            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//	        preparedStatement.setString(1, postId);
//
//	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
//	            if (resultSet.next()) {
//	                return resultSet.getString("Author");
//	            }
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//
//	    return null;
//	}

//	private boolean checkIfPostBelongsToUser(String postId, String author) {
//	    String sql = "SELECT Author FROM " + Database.POSTS_TABLE_NAME + " WHERE PostID = ?";
//
//	    try (Connection connection = DatabaseConnection.getConnection();
//	            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//	        preparedStatement.setString(1, postId);
//
//	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
//	            if (resultSet.next()) {
//	                String postAuthor = resultSet.getString("Author");
//	                return postAuthor.equals(author); // Check if the post belongs to the user
//	            }
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//
//	    return false;
//	}
	// remove post from database
	private void removePostFromCollection(String postId) {
		Database.removePostFromCollection(postId);
	}
}
