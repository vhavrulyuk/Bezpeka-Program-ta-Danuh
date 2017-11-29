import com.lp.bpd.lab2.MD5;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class Controller {
    private File selectedFile;
    @FXML
    private Label MD5HashValue;
    @FXML
    private TextArea rowString;
    @FXML
    private Button calculateHashForFile;

    @FXML
    protected void calculateHashFromString(ActionEvent event) {
        MD5HashValue.setText(MD5.toHexString(MD5.computeMD5(rowString.getText().getBytes())));
    }

    public void selectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        if ((selectedFile = fileChooser.showOpenDialog(new Stage())) != null) {
            calculateHashForFile.setDisable(false);
        } else {
            calculateHashForFile.setDisable(true);
        }
    }

    public void calculateHashForSelectedFile(ActionEvent event) {
        byte[] bytesArray;
        try (FileInputStream fileInputStream =  new FileInputStream(selectedFile)){
            bytesArray = new byte[(int)selectedFile.length()];
            fileInputStream.read(bytesArray);
            MD5HashValue.setText(MD5.toHexString(MD5.computeMD5(bytesArray)));
        } catch (Exception e) {
           System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}