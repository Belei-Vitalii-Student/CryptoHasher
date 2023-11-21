module com.crypto.hasher.cryptohasher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.bouncycastle.provider;

    opens com.crypto.hasher.cryptohasher to javafx.fxml;
    exports com.crypto.hasher.cryptohasher;
}