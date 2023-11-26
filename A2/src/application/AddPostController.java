package application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
public class AddPostController extends MainController{
	 @FXML
	    private TextField postIdTextField;
	    @FXML
	    private TextField contentTextField;
	    @FXML
	    private TextField authorTextField;
	    @FXML
	    private TextField likesTextField;
	    @FXML
	    private TextField sharesTextField;
	    @FXML
	    private TextField dateTimeTextField;
	    @FXML
	    private TextField mainPostIdTextField;
	    
	    public void handleAddPost(ActionEvent event) throws IOException {
			String postId = postIdTextField.getText();
			String content = contentTextField.getText();
			String author = authorTextField.getText();

			// Validate likes and shares as integers
			if (!isInteger(likesTextField.getText())) {
				showAlert(AlertType.ERROR, "Invalid Input", "Likes must be an integer.");
				return;
			}

			if (!isInteger(sharesTextField.getText())) {
				showAlert(AlertType.ERROR, "Invalid Input", "Shares must be an integer.");
				return;
			}

			int likes = Integer.parseInt(likesTextField.getText());
			int shares = Integer.parseInt(sharesTextField.getText());

			String dateTime = dateTimeTextField.getText();

			// Validate date time format
			if (!isValidDateTime(dateTime)) {
				showAlert(AlertType.ERROR, "Invalid Date/Time",
						"Please enter a valid date/time format (e.g., 12/12/2021 10:30).");
				return;
			}

			String mainPostId = mainPostIdTextField.getText();

			// Validate post ID as an integer
			if (!isInteger(postId)) {
				showAlert(AlertType.ERROR, "Invalid Input", "Post ID must be an integer.");
				return;
			}

			// add the post to the database
			addPostToDatabase(postId, content, author, likes, shares, dateTime, mainPostId);
		}

		private boolean isInteger(String input) {
			try {
				Integer.parseInt(input);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		private boolean isValidDateTime(String dateTime) {
			// Define your desired date/time format
			String regex = "\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}";

			return dateTime.matches(regex);
		}

		public void addPostToDatabase(String postId, String content, String author, int likes, int shares, String dateTime,
				String mainPostId) throws IOException {
			String sql = "INSERT INTO Posts (PostID, Content, Author, Likes, Shares, Date_Time, Main_Post_ID) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (Connection connection = DatabaseConnection.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, postId);
				preparedStatement.setString(2, content);
				preparedStatement.setString(3, author);
				preparedStatement.setInt(4, likes);
				preparedStatement.setInt(5, shares);
				preparedStatement.setString(6, dateTime);
				preparedStatement.setString(7, mainPostId);

				preparedStatement.executeUpdate();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Post Added");
				alert.setHeaderText(null);
				alert.setContentText("The post has been added!");
				alert.showAndWait();

				// Navigate back to Dashboard.fxml
				
				switchToDashboard(null);
			} catch (SQLException e) {
				e.printStackTrace(); // Handle the exception appropriately
				System.err.println("SQLException: " + e.getMessage());
				System.err.println("SQLState: " + e.getSQLState());
				System.err.println("VendorError: " + e.getErrorCode());
			}
		}
		public void showAlert(Alert.AlertType alertType, String title, String content) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(content);
			alert.showAndWait();
		}
}
