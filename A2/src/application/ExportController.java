package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class ExportController extends MainController {
	@FXML
	private TextField postIdTextField;

	@FXML
	private TextField fileNameTextField;

	@FXML
	private TextField fileLocationTextField;

	@FXML
	private Button handleBrowse;

	@FXML
	private Button handleExport;

	@FXML
	private Button switchToDashboard;

	@FXML
	public void handleBrowse(ActionEvent event) {
		// method for the browse button so the user can choos where they want their file
		// exported to
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Export Location");
		File selectedDirectory = directoryChooser.showDialog(null);

		if (selectedDirectory != null) {
			fileLocationTextField.setText(selectedDirectory.getAbsolutePath());
		}
	}

	@FXML
	public void handleExport(ActionEvent event) {
		// method to export a post
		String postIdStr = postIdTextField.getText().trim();
		String fileName = fileNameTextField.getText().trim();
		String fileLocation = fileLocationTextField.getText().trim();

		if (postIdStr.isEmpty() || fileName.isEmpty() || fileLocation.isEmpty()) {
			showAlert("Error", "All fields must be filled out.");
			return;
		}

		try {
			int postId = Integer.parseInt(postIdStr);
			Post post = Database.retrievePost(postId);

			if (post != null) {
				String contentToExport = post.toString();

				File file = new File(fileLocation, fileName + ".csv"); // Change the file extension to .csv
				try (FileWriter writer = new FileWriter(file)) {
					writer.write(contentToExport);
					showAlert("Success", "Post exported successfully!");
				} catch (IOException e) {
					e.printStackTrace();
					showAlert("Error", "An error occurred while exporting the post.");
				}
			} else {
				showAlert("Error", "Post with ID " + postId + " not found.");
			}
		} catch (NumberFormatException e) {
			showAlert("Error", "Invalid Post ID. Please enter a valid number.");
		}
	}

	private void showAlert(String title, String content) {
		// method to show alert to user
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
