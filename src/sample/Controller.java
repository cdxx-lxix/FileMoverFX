package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;


public class Controller {
    @FXML
    private TextField fromField, toField, filterField;
    @FXML
    Stage primaryStage;
    @FXML
    private Label filesAmountLabel, filesMovedLabel;
    @FXML
    public int movedFilesCounter;


    public void folderSelect(ActionEvent e) {
        // Sets textfield to a path provided by directoryPicker. Understands what textfield gotta be set. Also updates files amount stat.
        String directory = directoryPicker();
        String clicked = ((Control) e.getTarget()).getId();
        switch (clicked) {
            case "chooseBtn1":
                fromField.setText(directory);
                filesAmount();
                break;
            case "chooseBtn2":
                toField.setText(directory);
                break;
        }
    }

    public String directoryPicker() {
        // Responsible for opening a dialog and returning an abs path
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        return selectedDirectory.getAbsolutePath();
    }

    public void filesAmount() {
        // Scans selected folder for amount of present files. Just for the stats.
        try (Stream<Path> files = Files.list(Paths.get(fromField.getText()))) {
            // It has to be long
            long amount = files.count();
            filesAmountLabel.setText(String.valueOf(amount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File[] getFiles() {
        File folder = new File(fromField.getText());
        return folder.listFiles();
    }

    public void moveFiles() {
        // Resets the counter
        movedFilesCounter = 0;

        File[] filesToMove = getFiles();
        String fileNamesString = Arrays.toString(filesToMove).replace(fromField.getText(), "");
        String[] fileNames = fileNamesString.split(",");
        if (fromField.getText().isEmpty() || toField.getText().isEmpty() || filterField.getText().isEmpty()) {
            Alert badHumanNoEmpty = new Alert(Alert.AlertType.INFORMATION);
            badHumanNoEmpty.setTitle("Bad human! No empty fields!");
            badHumanNoEmpty.setHeaderText("You MUST have 'From' & 'To' folders AND a filter");
            badHumanNoEmpty.showAndWait();
        } else {
            for (int i = 0; i < fileNames.length; i++ ) {
                fileNames[i] = fileNames[i].trim();
                fileNames[i] = fileNames[i].replace("[", "");
                fileNames[i] = fileNames[i].replace("]", "");
                if (fileNames[i].toLowerCase().contains(filterField.getText().toLowerCase())) {
                    try {
                        Files.move(Paths.get(fromField.getText() + fileNames[i]), Paths.get(toField.getText() + fileNames[i]));
                        //Setting counter for +1 only when an item was moved
                        movedFilesCounter++;
                    } catch (IOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setContentText(String.valueOf(e));
                        errorAlert.showAndWait();
                    }
                }
            }
            // Setting the counter
            filesMovedLabel.setText(String.valueOf(movedFilesCounter));
        }
    }
}
