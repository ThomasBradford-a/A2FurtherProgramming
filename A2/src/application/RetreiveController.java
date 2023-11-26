package application;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RetreiveController extends MainController {

	@FXML
	private TextField retrievePostIdTextField;
	@FXML
	private Text postIDText;
	@FXML
	private Text postContentText;
	@FXML
	private Text postAuthorText;
	@FXML
	private Text postLikesText;
	@FXML
	private Text postSharesText;
	@FXML
	private Text postDateTimeText;
	@FXML
	private Text postMainPostIdText;
	@FXML
	private VBox contentVBox;
	@FXML
	private TableView<Post> postTableView;

	public void retrieveAndShowPost(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPost.fxml"));
			Parent root = loader.load();

			MainController viewController = loader.getController();

			try {
				// Try to parse input as an integer
				int postID = Integer.parseInt(retrievePostIdTextField.getText());

				// Attempt to retrieve the post
				Post retrievedPost = Database.retrievePost(postID);

				if (retrievedPost != null) {
					// Post found, update UI with post information
					viewController.getPostIDText().setText("Post ID: " + retrievedPost.getPostId());
					viewController.getPostContentText().setText("Content: " + retrievedPost.getContent());
					viewController.getPostAuthorText().setText("Author: " + retrievedPost.getAuthor());
					viewController.getPostLikesText().setText("Likes: " + retrievedPost.getLikes());
					viewController.getPostSharesText().setText("Shares: " + retrievedPost.getShares());
					viewController.getPostDateTimeText().setText("Date/Time: " + retrievedPost.getDateTime());
					viewController.getPostMainPostIdText().setText("Main Post ID: " + retrievedPost.getMainPostId());

					// Get the current stage
					Stage stage = (Stage) retrievePostIdTextField.getScene().getWindow();

					// Set the new scene
					stage.setScene(new Scene(root));
					stage.show();
				} else {
					// If Post was not found, display an error message
					viewController.showAlert(Alert.AlertType.ERROR, "Post Not Found",
							"That post is not in your collection.");
				}
			} catch (NumberFormatException e) {
				// Handle the case where the input is not a valid integer
				viewController.showAlert(Alert.AlertType.ERROR, "Invalid Input",
						"Please enter a number for the Post ID.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void retrieveReplies(ActionEvent event) {
		String postId = retrievePostIdTextField.getText();

		// Check if postId is not empty
		if (!postId.isEmpty()) {

			// Convert the String postId to int
			int postIdInt = Integer.parseInt(postId);

			List<Post> replies = Database.getReplies(postIdInt);

			// Clear existing content if there is any
			contentVBox.getChildren().clear();

			if (!replies.isEmpty()) {
				// Display replies in the VBox
				for (Post reply : replies) {
					System.out.println("Adding reply to VBox: " + reply);
					Text replyText = new Text(reply.toString());
					contentVBox.getChildren().add(replyText);
				}
			} else {
				// No replies found show message inside VBox
				Text noRepliesText = new Text("No replies found for the post.");
				contentVBox.getChildren().add(noRepliesText);
			}
		} else {
			// Handle case where postId is empty
			showAlert(Alert.AlertType.ERROR, "invalid input", "please enter a number for the post id.");
		}
	}

}
