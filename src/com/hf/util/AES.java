package com.hf.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	public static String DEF_KEY = "1234567890abcdef";

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(byte[] content, String password)
			throws HFModuleException {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(getbyte(content));
			return result; // 加密
		} catch (Exception e) {
			throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
		}
	}

	public static byte[] getbyte(byte[] asda) {
		int x = asda.length / 16;
		int y = asda.length % 16;
		if (y != 0) {
			x++;
		}
		ByteBuffer bf = ByteBuffer.allocate(x * 16);
		bf.put(asda);
		return bf.array();
	}

	public static byte[] decrypt(byte[] content, String password) throws HFModuleException {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			byte[] byteContent = content;
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Exception e) {
			throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
		} 
	}
}
