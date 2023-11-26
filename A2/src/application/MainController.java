package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
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
	@FXML
	private TextField retrievePostIdTextField;
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
	private Text postIDText;

	@FXML
	private Text contentText;

	@FXML
	private Text authorText;

	@FXML
	private Text likesText;

	@FXML
	private Text sharesText;

	@FXML
	private Text dateTimeText;

	@FXML
	private Text mainPostIDText;
	@FXML
	private TextField removePostIdTextField;
	@FXML
	private CheckBox editModeCheckbox;
	@FXML
	private Text welcomeText;
	@FXML
	private Button logoutButton;
	@FXML
	private Button upgradeToVIPButton;
	@FXML
	private Button pieChartButton;
	@FXML
	private Button importButton;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	public void switchToRegistration(ActionEvent event) {
		// logic to sitch scenes
		try {
			Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource("Signup.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading registration scene", e);
		}
	}

	public void switchToEdit(ActionEvent event) {
		// logic to sitch scenes

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
			Parent root = loader.load();

			MainController mainController = loader.getController();

			Stage editStage = new Stage();
			mainController.setStage(editStage);

			Scene scene = new Scene(root);
			editStage.setScene(scene);
			editStage.show();
		} catch (IOException e) {
			handleException("Error loading edit scene", e);
		}
	}

	public void switchToAnalyzer(ActionEvent event) {
		// logic to sitch scenes

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Analyzer.fxml"));
			root = loader.load();
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading analyzer scene", e);
		}
	}

	public void switchToAddPost(ActionEvent event) throws IOException {
		// logic to sitch scenes

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPost.fxml"));
			Parent root = loader.load();

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading AddPost scene", e);
		}

	}

	public void switchToDashboard(ActionEvent event) throws IOException {
		// logic to sitch scenes
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
		Parent root = loader.load();

		// get the loggedInUser
		User loggedInUser = UserManager.getLoggedInUser();
		if (loggedInUser != null) {
			// Access the Text element directly and set the welcome text NOT WORKING!
			Text welcomeText = (Text) root.lookup("#welcomeText");
			if (welcomeText != null) {
				welcomeText.setText("Welcome, " + loggedInUser.GetFirstName() + " " + loggedInUser.GetLastName() + "!");
			} else {

			}
		}

		// Use the node to get the current stage if available
		Node sourceNode = (event != null) ? (Node) event.getSource() : null;

		Stage stage;
		if (sourceNode != null && sourceNode.getScene() != null && sourceNode.getScene().getWindow() instanceof Stage) {
			stage = (Stage) sourceNode.getScene().getWindow();
		} else {
			stage = new Stage();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToRetrieve(ActionEvent event) throws IOException {
		// logic to sitch scenes

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Retreive.fxml"));
			root = loader.load();
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading Retreive scene", e);
		}
	}

	public void showAlert(Alert.AlertType alertType, String title, String content) {
		// show alert method to display errors ect to user
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void switchToRemove(ActionEvent event) throws IOException {
		// logic to sitch scenes
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Remove.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading Remove scene", e);
		}
	}

	public void switchToProfileUpdate(ActionEvent event) throws IOException {
		// logic to switch scenes
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
			Parent root = loader.load();

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading Edit scene", e);
		}
	}

	public void showConfirmationMessage(String message) {
		// logic for comfirmation message to user
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void switchToLikes() throws Exception {
		// logic to switch scenes

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Likes.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading Likes scene", e);
		}
	}

	public void switchToExport(ActionEvent event) throws IOException {
		// logic to switch scenes
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Export.fxml"));
			Parent root = loader.load();

			// Use the node to get the current stage if available
			Node sourceNode = (event != null) ? (Node) event.getSource() : null;

			Stage stage;
			if (sourceNode != null && sourceNode.getScene() != null
					&& sourceNode.getScene().getWindow() instanceof Stage) {
				stage = (Stage) sourceNode.getScene().getWindow();
			} else {
				stage = new Stage();
			}

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error loading Export scene", e);
		}
	}

	@FXML
	private void logout(ActionEvent event) {
		// logic for loggin user out
		// Display confirmation dialog
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to log out?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Switch to the login scene (Analyzer.fxml)
			switchToLoginScene();
		}
	}

	private void switchToLoginScene() {
		// logic to take user who logged out back to the login screen
		// Display confirmation alert
		Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
		confirmationAlert.setTitle("Logout Confirmation");
		confirmationAlert.setHeaderText(null);
		confirmationAlert.setContentText("See you next time!");

		confirmationAlert.showAndWait();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Analyzer.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			handleException("Error logging out", e);
		}
	}

	@FXML
	private void upgradeToVIP(ActionEvent event) {
		System.out.println("Upgrading to VIP...");

		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("VIP Upgrade");
		confirmationAlert.setHeaderText(null);
		confirmationAlert
				.setContentText("Do you want to upgrade to VIP?\n\nVIP users have access to advanced functionalities.");

		Optional<ButtonType> result = confirmationAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Upgrade the user to VIP
			UserManager.getLoggedInUser().setVIP(true);
			UserManager.getLoggedInUser().saveVIPStatusToDatabase(); // Save VIP status to the database
			logout(event); // Log out the user for the changes to take effect
		}
	}

	public void showVIPFunctionalities() {
		User loggedInUser = UserManager.getLoggedInUser();

		// Check VIP status
		if (loggedInUser.getVIP()) {
			// User is a VIP, show VIP functionalities
			enableVIPFeatures();
		} else {
			// User is not a VIP, hide VIP functionalities
			disableVIPFeatures();
		}
	}

	private void enableVIPFeatures() {
		// Implement logic to enable VIP features
		pieChartButton.setVisible(true);
		importButton.setVisible(true);
	}

	private void disableVIPFeatures() {
		// Implement logic to disable VIP features
		pieChartButton.setVisible(false);
		importButton.setVisible(false);
	}

	private void handleException(String message, Exception e) {
		e.printStackTrace(); // Log the exception (you might want to use a proper logging framework)
		showAlert(Alert.AlertType.ERROR, "Error", message + ": " + e.getMessage());
	}

	public void setWelcomeText(String firstName, String lastName) {
		welcomeText.setText("Welcome " + firstName + " " + lastName);
	}

	public Text getPostIDText() {
		return postIDText;
	}

	public void setPostIDText(Text postIDText) {
		this.postIDText = postIDText;
	}

	public Text getPostContentText() {
		return postContentText;
	}

	public void setPostContentText(Text postContentText) {
		this.postContentText = postContentText;
	}

	public Text getPostAuthorText() {
		return postAuthorText;
	}

	public void setPostAuthorText(Text postAuthorText) {
		this.postAuthorText = postAuthorText;
	}

	public Text getPostLikesText() {
		return postLikesText;
	}

	public void setPostLikesText(Text postLikesText) {
		this.postLikesText = postLikesText;
	}

	public Text getPostSharesText() {
		return postSharesText;
	}

	public void setPostSharesText(Text postSharesText) {
		this.postSharesText = postSharesText;
	}

	public Text getPostDateTimeText() {
		return postDateTimeText;
	}

	public void setPostDateTimeText(Text postDateTimeText) {
		this.postDateTimeText = postDateTimeText;
	}

	public Text getPostMainPostIdText() {
		return postMainPostIdText;
	}

	public void setPostMainPostIdText(Text postMainPostIdText) {
		this.postMainPostIdText = postMainPostIdText;
	}

}
