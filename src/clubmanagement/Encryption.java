/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubmanagement;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	Cipher ecipher;
	Cipher dcipher;
	byte[] buf = new byte[1024];
	static final String HEXES = "0123456789ABCDEF";

	/***
	 * Constructor 1
	 */
	public Encryption() {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);

			this.setupEncryption(kgen.generateKey());
		} catch (Exception e) {
		}
	}

	/***
	 * Constructor 2
	 * 
	 * @param key
	 */
	public Encryption(String key) {
		SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
		this.setupEncryption(skey);
	}

	/***
	 * Setup
	 * 
	 * @param key
	 */
	private void setupEncryption(SecretKey key) {
		byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
				0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | InvalidAlgorithmParameterException ex) {
		}
	}

	/***
	 * Encrypt
	 * 
	 * @param in
	 * @param out
	 */
	public void encrypt(InputStream in, OutputStream out) {
		try {
			out = new CipherOutputStream(out, ecipher);

			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		} catch (java.io.IOException e) {
		}
	}

	/***
	 * Encrypt
	 * 
	 * @param plaintext
	 * @return
	 */
	public String encrypt(String plaintext) {
		try {
			byte[] ciphertext = ecipher.doFinal(plaintext.getBytes("UTF-8"));
			return Encryption.byteToHex(ciphertext);
		} catch (UnsupportedEncodingException | IllegalBlockSizeException
				| BadPaddingException ex) {
			return null;
		}

	}

	/***
	 * Decrypt
	 * 
	 * @param in
	 * @param out
	 */
	public void decrypt(InputStream in, OutputStream out) {
		try {
			in = new CipherInputStream(in, dcipher);

			int numRead;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		} catch (java.io.IOException e) {
		}
	}

	/***
	 * Decrypt
	 * 
	 * @param hexCipherText
	 * @return
	 */
	public String decrypt(String hexCipherText) {
		try {
			String plaintext = new String(dcipher.doFinal(Encryption
					.hexToByte(hexCipherText)), "UTF-8");
			return plaintext;
		} catch (IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException ex) {
			return null;
		}
	}

	/***
	 * Decrypt
	 * 
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(byte[] ciphertext) {
		try {
			String plaintext = new String(dcipher.doFinal(ciphertext), "UTF-8");
			return plaintext;
		} catch (IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException ex) {
			return null;
		}
	}

	/***
	 * Get MD5
	 * 
	 * @param input
	 * @return
	 */
	private static byte[] getMD5(String input) {
		try {
			byte[] bytesOfMessage = input.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(bytesOfMessage);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
			return null;
		}
	}

	/***
	 * Byte To Hex
	 * 
	 * @param raw
	 * @return
	 */
	public static String byteToHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	/***
	 * Hex to Byte
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexToByte(String hexString) {
		int len = hexString.length();
		byte[] ba = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			ba[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
					.digit(hexString.charAt(i + 1), 16));
		}
		return ba;
	}
}
