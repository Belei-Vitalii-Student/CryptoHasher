package com.crypto.hasher.cryptohasher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.function.DoubleToIntFunction;

public class CryptoHasherController implements Initializable {

    @FXML
    TextArea text_preview;

    @FXML
    Label file_size;

    @FXML
    Label file_path;

    @FXML
    Button choose_btn;

    @FXML
    Spinner<Integer> bit_spinner;
    SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

    @FXML
    Button bit_plus_one;

    @FXML
    Button bit_plus_ten;

    @FXML
    Button bit_plus_fifty;

    @FXML
    Slider bit_slider;

    @FXML
    Button bit_change_btn;

    @FXML
    TextArea hash_text;

    FileDetails fileDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bit_spinner.setValueFactory(spinnerValueFactory);
    }

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

        fileDetails = new FileDetails(file);


        choose_btn.setText(fileDetails.getName());
        text_preview.setText(fileDetails.getData());
        file_path.setText("📂 " + fileDetails.getName());
        file_size.setText("📥 Size: " + fileDetails.size() + " bytes");

        event.consume();
    }

    @FXML
    void addBitNumber(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource() ;
        String data = (String) node.getUserData();
        Integer number = Integer.parseInt(data);

        spinnerValueFactory.setValue((Integer) spinnerValueFactory.getValue() + number);
        bit_slider.setValue(bit_spinner.getValue());

        System.out.println(bit_spinner.getValue());

        event.consume();
    }

    @FXML
    void updateBitSpinner() {
        bit_slider.setValue(bit_spinner.getValue());
    }

    @FXML
    void onSliderChange() {
        Double sliderValue = Double.valueOf(bit_slider.getValue());
        spinnerValueFactory.setValue(sliderValue.intValue());
    }

    @FXML
    void changeBit(ActionEvent event) throws NoSuchAlgorithmException, IOException {
            String message = fileDetails.getData();

            // Обчислюємо хеш-функцію RIPEMD-320
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("RIPEMD320");
            byte[] hash = md.digest(messageBytes);

            // Представляємо хеш-функцію в шістнадцятковому вигляді
            String hexHash = RIPEMD320Hash.bytesToHex(hash);

            // Зберігаємо хеш-функцію у файл
//            String hashFileName = "hash.txt";
//            writeToFile(hashFileName, hexHash);

            hash_text.setText(hexHash);
            System.out.println("Хеш-функція RIPEMD-320: " + hexHash);

            event.consume();
    }


}