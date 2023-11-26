package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AnalyzerController extends MainController {
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	// perform logic to handle login
	public void handleLogin(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();

		UserManager userManager = new UserManager();
		User user = userManager.loginUser(username, password);

		if (user != null) {
			UserManager.setLoggedInUser(user);

			try {

				URL resourceUrl = getClass().getResource("Dashboard.fxml");

				if (resourceUrl == null || !new File(resourceUrl.getFile()).exists()) {

				}

				FXMLLoader loader = new FXMLLoader(resourceUrl);
				Parent root = loader.load();

				if (loader.getController() == null) {
					User.checkVIPStatusFromDatabase(UserManager.getLoggedInUser().GetUsername());
					showVIPFunctionalities();
				}

				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				Platform.runLater(() -> checkUpgrade());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Handle the case where login fails, show an error message
			showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
		}
	}

	private void checkUpgrade() {
		User loggedInUser = UserManager.getLoggedInUser();

		if (!loggedInUser.isVIP()) {
			Alert upgradeAlert = new Alert(Alert.AlertType.CONFIRMATION);
			upgradeAlert.setTitle("VIP Upgrade");
			upgradeAlert.setHeaderText(null);
			upgradeAlert.setContentText("Upgrade to VIP for advanced functionalities?");

			Optional<ButtonType> result = upgradeAlert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				loggedInUser.setVIP(true);

			}
		}
	}

}
