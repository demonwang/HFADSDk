package com.hf.util;

public class ByteTool {
	public static byte[] copyOfRange(byte[] data, int start, int end) {
		if (data == null || start > end || data.length < end) {
			return null;
		}
		byte[] dest = new byte[end - start];
		for (int i = 0; i < dest.length; i++) {
			dest[i] = data[start + i];
		}
		return dest;
	}

	public static String bytes2HexString(byte[] data) {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static String Byte2StringWithSpace(byte[] data) {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
			ret += " ";
		}
		return ret;
	}
	
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  

	 
	public static int Byte2Int(byte[] bytes) {
		int s = 0;
		for (int i = 0; i < bytes.length; i++) {
			s |= ((bytes[i] & 0xff) << (bytes.length - i - 1) * 8);
		}
		return s;
	}
	
	/**
	 * only get last Two bytes
	 * 
	 * @param x
	 * @return
	 */
	public static byte[] Int2Byte(int x) {
		byte[] b = new byte[2];
		b[1] = (byte) (x & 0xff);
		b[0] = (byte) ((x & 0xff00) >> 8);
		return b;
	}
	
	
	public static void sleep(int times){
		try {
			Thread.sleep(1000*times);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
