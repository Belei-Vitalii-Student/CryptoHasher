package com.crypto.hasher.cryptohasher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class RIPEMD320Hash {
    public static void main(String[] args) {
        try {
            // Читаємо текстове повідомлення з файлу
            String fileName = "message.txt";
            String message = readFromFile(fileName);

            // Обчислюємо хеш-функцію RIPEMD-320
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("RIPEMD320");
            byte[] hash = md.digest(messageBytes);

            // Представляємо хеш-функцію в шістнадцятковому вигляді
            String hexHash = bytesToHex(hash);

            // Зберігаємо хеш-функцію у файл
            String hashFileName = "hash.txt";
            writeToFile(hashFileName, hexHash);

            System.out.println("Хеш-функція RIPEMD-320: " + hexHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для читання тексту з файлу
    private static String readFromFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    // Метод для запису тексту у файл
    private static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    // Метод для перетворення масиву байт у шістнадцятковий рядок
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
