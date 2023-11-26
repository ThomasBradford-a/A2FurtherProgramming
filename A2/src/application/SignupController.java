package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SignupController extends MainController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;

	public void handleRegistration(ActionEvent event) throws IOException {
		// Retrieve data from input fields for user registration
		String username = usernameField.getText();
		String password = passwordField.getText();
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();

		// Perform registration logic
		User newUser = UserManager.registerUser(username, password, firstName, lastName);

		if (newUser != null) {
			System.out.println("User registration successful!");
			System.out.println("Username: " + newUser.GetUsername());
			System.out.println("First Name: " + newUser.GetFirstName());
			System.out.println("Last Name: " + newUser.GetLastName());
			System.out.println("VIP Status: " + newUser.isVIP());
			// if Registration was successful, switch back to the Analyzer.fxml scene
			switchToAnalyzer(event);
		} else {
			// if registration fails, show alert
			showAlert(AlertType.ERROR, "Registration Failed", "Sorry, that username is already taken.");
		}
	}
}
