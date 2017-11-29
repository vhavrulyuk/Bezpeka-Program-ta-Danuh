import com.lp.bpd.lab2.MD5;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    private File selectedFile;

    private enum IntegrityStatus {
        HASH_NOT_FOUND, INTEGRITY_CONFIRMED, INTEGRITY_NOT_CONFIRMED
    }

    @FXML
    private Label MD5HashValue;
    @FXML
    private TextArea rowString;
    @FXML
    private Button calculateHashForFile;
    @FXML
    private CheckBox writeToFile;
    @FXML
    private Button checkFileIntegrity;
    @FXML
    private Label integrityStatus;
    @FXML
    private Circle integrityIndicator;

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
            disableControls(false);
        } else {
            disableControls(true);
        }
    }

    private void disableControls(Boolean disable) {
        calculateHashForFile.setDisable(disable);
        writeToFile.setDisable(disable);
        checkFileIntegrity.setDisable(disable);
        integrityStatus.setText("");
        integrityIndicator.setFill(Color.GRAY);
        MD5HashValue.setText("");
    }

    public void calculateHashForSelectedFile(ActionEvent event) {
        byte[] bytesArray;
        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
            bytesArray = new byte[(int) selectedFile.length()];
            fileInputStream.read(bytesArray);
            String md5Hash = MD5.toHexString(MD5.computeMD5(bytesArray));
            MD5HashValue.setText(md5Hash);
            if (writeToFile.isSelected()) {
                writeHashIntoFile(md5Hash);
            }

        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private void writeHashIntoFile(String hash) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(selectedFile.getPath() + ".md5.txt"))) {
            bw.write(hash);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    public void checkFileIntegrity() {
        byte[] bytesArray;
        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
            bytesArray = new byte[(int) selectedFile.length()];
            fileInputStream.read(bytesArray);
            String md5Hash = MD5.toHexString(MD5.computeMD5(bytesArray));
            try (BufferedReader br = Files.newBufferedReader(Paths.get(selectedFile.getPath() + ".md5.txt"))) {
                String calculatedHash = br.readLine();
                if (calculatedHash.equals(md5Hash)) {
                    integrityStatus.setText(IntegrityStatus.INTEGRITY_CONFIRMED.toString());
                    integrityIndicator.setFill(Color.GREEN);
                } else {
                    integrityStatus.setText(IntegrityStatus.INTEGRITY_NOT_CONFIRMED.toString());
                    integrityIndicator.setFill(Color.RED);
                }
            } catch (Exception e) {
                integrityStatus.setText(IntegrityStatus.HASH_NOT_FOUND.toString());
                integrityIndicator.setFill(Color.YELLOW);
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}