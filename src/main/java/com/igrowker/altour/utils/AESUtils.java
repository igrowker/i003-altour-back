package com.igrowker.altour.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESUtils {
	 private final SecretKeySpec secretKey;

	    public AESUtils(@Value("${aes.encryption.key}") String key) {
	        // Asegúrate de que la longitud de la clave sea de 16 bytes para AES-128
	        this.secretKey = new SecretKeySpec(key.getBytes(), "AES");
	    }

	    public String encrypt(String data) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
	            return Base64.getEncoder().encodeToString(encryptedBytes);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al cifrar la preferencia", e);
	        }
	    }

	    public String decrypt(String encryptedData) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	            return new String(decryptedBytes);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al descifrar la preferencia", e);
	        }
	    }
}
