package com.crypto.hasher.cryptohasher;

import org.bouncycastle.crypto.digests.RIPEMD320Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;

public class RIPEMD320Hash {
    public static void main(String[] args) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            String message = "This is message";

            // Обчислюємо хеш-функцію RIPEMD-320
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("RIPEMD320");
            byte[] hash = md.digest(messageBytes);

            // Представляємо хеш-функцію в шістнадцятковому вигляді
            String hexHash = bytesToHex(hash);


            System.out.println("Хеш-функція RIPEMD-320: " + hexHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для читання тексту з файлу
    public static String readFromFile(String fileName) throws IOException {
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
    public static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    // Метод для перетворення масиву байт у шістнадцятковий рядок
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

//
//    public static void main(String[] args) throws IOException {
//
//        Security.addProvider(new BouncyCastleProvider());
//
//        // Зчитування повідомлення з файлу
//        String message = readMessageFromFile("input.txt");
//
//        int bitPosition = 2;
//        int newBitValue = 1;
//
//        byte[] newMessageBytes = message.getBytes();
//        newMessageBytes = setBit(newMessageBytes, bitPosition, newBitValue);
//
//        // Обчислення хешу
////        byte[] messageBytes = message.getBytes();
//        byte[] messageBytes = newMessageBytes;
//        RIPEMD320Digest digest = new RIPEMD320Digest();
//        digest.update(messageBytes, 0, messageBytes.length);
//        byte[] hash = new byte[digest.getDigestSize()];
//        digest.doFinal(hash, 0);
//
//        // Збереження хешу в файл у шістнадцятковому вигляді
//        writeHashToFile("hash.txt", hash);
//
//        String hash1 = "22d65d5661536cdc75c1fdf5c6de7b41b9f27325ebc61e8557177d705a0ec880151c3a32a00899b8";
//        String hash2 = "00b40d5f18beb399040a495b8153ee6981622b276370a91d73c0dc4832651f84cbb1be28e009c38d";
//
//        System.out.println(countDifferentBits(hash1.getBytes(), hash2.getBytes()));
//    }
//
//    public static int countDifferentBits(byte[] hash1, byte[] hash2) {
//        int bitDifferenceCount = 0;
//
//        for (int i = 0; i < hash1.length; i++) {
//            // Використовуємо XOR для порівняння бітів
//            byte xorResult = (byte) (hash1[i] ^ hash2[i]);
//
//            // Підраховуємо кількість встановлених бітів (1) в xorResult
//            while (xorResult != 0) {
//                bitDifferenceCount += xorResult & 1;
//                xorResult >>= 1;
//            }
//        }
//
//        return bitDifferenceCount;
//    }
//
//    public static byte[] setBit(byte[] bytes, int bitPosition, int newBitValue) {
//        int byteIndex = bitPosition / 8;
//        int bitOffset = bitPosition % 8;
//
//        if (byteIndex < bytes.length) {
//            int mask = 1 << bitOffset;
//
//            if (newBitValue == 0) {
//                // Змінити біт на 0
//                bytes[byteIndex] &= ~mask;
//            } else if (newBitValue == 1) {
//                // Змінити біт на 1
//                bytes[byteIndex] |= mask;
//            }
//        }
//
//        return bytes;
//    }