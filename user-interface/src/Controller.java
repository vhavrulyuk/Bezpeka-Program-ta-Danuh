import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.lp.bpd.lab2.*;
import javafx.scene.control.TextArea;


public class Controller {
    @FXML private Label MD5HashValue;
    @FXML private TextArea rowString;

    @FXML protected void calculateHashFromString (ActionEvent event) {
        MD5HashValue.setText(MD5.toHexString(MD5.computeMD5(rowString.getText().getBytes())));
    }
}
