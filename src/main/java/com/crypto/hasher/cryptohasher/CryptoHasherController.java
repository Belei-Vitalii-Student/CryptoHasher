package com.crypto.hasher.cryptohasher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.bouncycastle.crypto.digests.RIPEMD320Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ResourceBundle;

public class CryptoHasherController implements Initializable {

    @FXML
    TextArea text_preview;

    @FXML
    Label file_size;

    @FXML
    Label file_path;

    @FXML
    Label bit_changed;

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

    @FXML
    TextArea hash_text_after;

    @FXML
    LineChart hash_chart;
    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    FileDetails fileDetails;

    public void updateHashText(String message) throws NoSuchAlgorithmException, IOException {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("RIPEMD320");
        byte[] hash = md.digest(messageBytes);

        String hexHash = RIPEMD320Hash.bytesToHex(hash);

        hash_text.setText(hexHash);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Security.addProvider(new BouncyCastleProvider());
        bit_spinner.setValueFactory(spinnerValueFactory);
        series.setName("bit position");
    }

    @FXML
    void fileSelector(ActionEvent event) throws IOException, NoSuchAlgorithmException {
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

        updateHashText(fileDetails.getData());

        event.consume();
    }

    @FXML
    void addBitNumber(ActionEvent event) {
        Node node = (Node) event.getSource() ;
        String data = (String) node.getUserData();
        Integer number = Integer.parseInt(data);

        spinnerValueFactory.setValue((Integer) spinnerValueFactory.getValue() + number);
        bit_slider.setValue(bit_spinner.getValue());

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
        String hashOriginal = hash_text.getText();
        String message = fileDetails.getData();
        Integer bitPosition = bit_spinner.getValue();

        byte[] newMessageBytes = RIPEMD320Hash.setBit(message.getBytes(), bitPosition);

        byte[] messageBytes = newMessageBytes;
        RIPEMD320Digest digest = new RIPEMD320Digest();
        digest.update(messageBytes, 0, messageBytes.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);

        hash_text_after.setText(RIPEMD320Hash.bytesToHex(hash));

        String hashChanged = hash_text_after.getText();
        int differentBits = RIPEMD320Hash.countDifferentBits(hashOriginal.getBytes(), hashChanged.getBytes());

        bit_changed.setText("Bit changed: " + differentBits);

        hash_chart.getData().clear();
        series.getData().add(new XYChart.Data(bitPosition.toString(), differentBits));
        hash_chart.getData().add(series);

        event.consume();
    }








}