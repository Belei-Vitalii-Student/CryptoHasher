package com.crypto.hasher.cryptohasher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoHasherController {

    @FXML
    TextArea text_preview;

    @FXML
    Label file_size;

    @FXML
    Label file_path;

    @FXML
    Button choose_btn;

    @FXML
    void fileSelector(ActionEvent event) throws IOException {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(window);

        if(file == null || !file.exists()) {
            event.consume();
            System.out.println("File does not exists");
            event.consume();
            return;
        }

        FileDetails fileDetails = new FileDetails(file);


        choose_btn.setText(fileDetails.getName());
        text_preview.setText(fileDetails.getData());
        file_path.setText("📂 " + fileDetails.getName());
        file_size.setText("📥 Size: " + fileDetails.size() + " bytes");

        event.consume();
    }
}