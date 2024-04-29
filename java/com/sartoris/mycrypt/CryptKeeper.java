/**
 * Copyright (c) 2014, Sartoris Software
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sartoris.mycrypt;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Static class that implements the encryption/decryption algorithms.
 */
public class CryptKeeper {

	private static byte[] salt = "[B@6576e542".getBytes();
	private static int padding = 20;
	private static int ivSize = 16;
	private static Exception lastException = null;

	/**
	 * @return the last exception that was raised.
	 */
	public static Exception getLastException() {
		return CryptKeeper.lastException;
	}

	/**
	 * @return a 16 byte string of random characters
	 */
	public static String getRandomString() {
		return CryptKeeper.getRandomBytes(16).toString();
	}
	
	/**
	 * @return a 16 byte array of random bytes
	 */
	public static byte[] getRandomBytes(int length) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * Displays a list of security providers
	 */
	public static void listProviders() {
		for (Provider provider : Security.getProviders()) {
			System.out.println(provider.getName() + " - " + provider.getClass().getCanonicalName());
			System.out.println(provider.getInfo());
			for (Service service : provider.getServices()) {
				System.out.println("   " + service.getType() + " - " + service.getAlgorithm());
			}
			System.out.println();
		}
	}

	/**
	 * Encrypt a specified string using a specified password 
	 * @param unencrypted a byte array of the text to encrypt
	 * @param password a password used to encrypt the text
	 * @return a byte array containing the encrypted text
	 * @throws Exception 
	 */
	public static byte[] encrypt(String unencrypted, String password) throws Exception
	{
		byte[] encrypted = {};
		try {
			SecretKey secret = CryptKeeper.getSecretKey(password);
			encrypted = CryptKeeper.encrypt(unencrypted.getBytes("UTF8"), secret);
		} catch (Exception ex) {
			CryptKeeper.lastException = ex;
			throw(ex);
		}
		return encrypted;
	}

	/**
	 * Decrypt a specified byte array using a specified password 
	 * @param encrypted a byte array of the text to decrypt
	 * @param password a password that was originally used to encrypt the text
	 * @return the original text that was encrypted
	 * @throws Exception 
	 */
	public static String decrypt(byte[] encrypted, String password) throws Exception
	{
		String decrypted = "";
		if (encrypted.length > 16)
		{
			try	{
			SecretKey secret = CryptKeeper.getSecretKey(password);
			byte[] unencrypted = CryptKeeper.decrypt(encrypted, secret);
			decrypted = new String(unencrypted, StandardCharsets.UTF_8);
			}
			catch (Exception ex) {
				CryptKeeper.lastException = ex;
				throw(ex);
			}
		}
		return decrypted;
	}

	private static byte[] encrypt(byte[] ciphertext, SecretKey secret)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException {
		byte[] packed = {};
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encrypted = cipher.doFinal(ciphertext);
		packed = new byte[CryptKeeper.padding + CryptKeeper.ivSize + encrypted.length];
		System.arraycopy(CryptKeeper.getRandomBytes(CryptKeeper.padding), 0, packed, 0, CryptKeeper.padding);
		System.arraycopy(iv, 0, packed, CryptKeeper.padding, CryptKeeper.ivSize);
		System.arraycopy(encrypted, 0, packed, CryptKeeper.ivSize + CryptKeeper.padding, encrypted.length);
		return packed;
	}
	
	private static byte[] decrypt(byte[] ciphertext, SecretKey secret)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] decrypted = {};
		byte[] iv = new byte[CryptKeeper.ivSize];
		System.arraycopy(ciphertext, CryptKeeper.padding, iv, 0, CryptKeeper.ivSize);
		int encryptedLength = ciphertext.length - CryptKeeper.ivSize - CryptKeeper.padding;
		byte[] encrypted = new byte[encryptedLength];
		System.arraycopy(ciphertext, CryptKeeper.ivSize + CryptKeeper.padding, encrypted, 0, encryptedLength);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private static SecretKey getSecretKey(String password)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		SecretKey key = null;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1", "SunJCE");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), CryptKeeper.salt, 65536, 256);
		SecretKey tmp;
		tmp = factory.generateSecret(spec);
		key = new SecretKeySpec(tmp.getEncoded(), "AES");
		return key;
	}

}
