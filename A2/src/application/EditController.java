package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditController extends MainController {

	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;

	public void handleProfileUpdate(ActionEvent event) throws IOException {
		// Switch to Edit.fxml
		switchToEdit(event);
		System.out.println("handleProfileUpdate method called");

		String password = passwordField.getText();
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();

		// Perform profile update logic
		User loggedInUser = UserManager.getLoggedInUser();
		if (loggedInUser != null) {
			loggedInUser.setPassword(password);
			loggedInUser.setFirstName(firstName);
			loggedInUser.setLastName(lastName);

			// Update the user profile in the database
			boolean updateSuccessful = Database.updateUserProfile(loggedInUser.GetUsername(), firstName, lastName);

			if (updateSuccessful) {

				showConfirmationMessage("Details updated successfully!");

			} else {
				// Handle the case where update fails
				// show an error message
				showAlert(AlertType.ERROR, "Update Failed", "Failed to update details. Please try again.");
			}
		}
	}

	public void handleSave(ActionEvent event) {

		// Get the updated values
		String newFirstName = firstNameField.getText();
		String newPassword = passwordField.getText();
		String newLastName = lastNameField.getText();

		// Update the user profile in the database
		boolean updateSuccessful = Database.updateUserProfile(newFirstName, newLastName, newPassword);

		if (updateSuccessful) {
			System.out.println("Profile updated successfully!");
			// show a confirmation message to the user
			showConfirmationMessage("Details updated successfully!");
		} else {
			// Handle the case where update fails
			// show an error message
			showAlert(AlertType.ERROR, "Update Failed", "Failed to update details. Please try again.");
		}
	}
}