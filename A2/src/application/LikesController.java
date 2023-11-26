package application;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LikesController extends MainController {
	@FXML
	private TextField numberOfPostsTextField;

	@FXML
	private VBox postContainer;

	public void retrieveTopPosts() {
		try {
			int N = Integer.parseInt(numberOfPostsTextField.getText());

			// Check if N is within the valid range
			if (N > 0 && N <= 5) {
				List<Post> topPosts = Database.getTopPosts(N);

				// Clear previous posts if any
				postContainer.getChildren().clear();
				double maxHeight = 100.0; // Set your preferred maximum height
				postContainer.setMaxHeight(maxHeight);
				for (Post post : topPosts) {
					Text postText = new Text(post.toString());
					postText.setWrappingWidth(400); // Set your preferred wrapping width
					postText.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;");
					postContainer.getChildren().add(postText);
				}
			} else {
				showAlert("Invalid Input", "Please enter a number between 1 and 5.");
			}
		} catch (NumberFormatException e) {
			showAlert("Invalid Input", "Please enter a valid number.");
		}
	}

	// method to show an alert to user
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
