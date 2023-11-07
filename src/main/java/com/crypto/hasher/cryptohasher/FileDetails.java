package com.crypto.hasher.cryptohasher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDetails {
    private File file;

    FileDetails(File file) {
        this.file = file;
    }

    public String getName() {
        return this.file.getName();
    }

    public String getData() throws IOException {
        return Files.readAllLines(Paths.get(file.getAbsolutePath())).get(0);
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public Long size() throws IOException {
        return Files.size(Paths.get(file.getAbsolutePath()));
    }
}
