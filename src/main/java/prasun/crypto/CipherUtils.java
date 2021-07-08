package prasun.crypto;
import java.math.BigInteger;

import com.google.common.io.BaseEncoding;

public class CipherUtils {
	public static void main(String[] args) {
		String encodedString = "09e1c5f70a65ac519458e7e53f36";
		String s = "attack at dawn";

		String decodedString = hexToAscii(encodedString);
		System.out.println(decodedString);

		String key = encode(s, decodedString);
		System.out.println(key);
		System.out.println(asciiToHex(encode(s, key)));
		s = "attack at dusk";
		System.out.println(asciiToHex(encode(s, key)));
	}

	static String encode(String s, String key) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
			sb.append((char) (s.charAt(i) ^ key.charAt(i % key.length())));
		return sb.toString();
	}

	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}

	public static String asciiToHex(String asciiStr) {
		char[] chars = asciiStr.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char ch : chars) {
			String hexVal = Integer.toHexString((int) ch);
			if (hexVal.length() == 1)
				hexVal = "0" + hexVal;
			hex.append(hexVal);
		}

		return hex.toString();
	}

	public static byte[] xorbytes(byte[] plainText, byte[] iv) {
		BigInteger text = new BigInteger(plainText);
		BigInteger ivTxt = new BigInteger(iv);

		return text.xor(ivTxt).toByteArray();

	}

	public static byte[] hex2Bytes(String hex) {
		return BaseEncoding.base16().lowerCase().decode(hex);
	}
	public static String bytes2Hex(byte[] bytes) {
		return BaseEncoding.base16().lowerCase().encode(bytes);
	}
	public static byte[] xor(byte[] a, byte[] b) {
		int len = Math.min(a.length, b.length);
		byte[] aXorB = new byte[len];
		for (int i = 0; i < len; i++) {
			aXorB[i] = (byte) (a[i] ^ b[i]);
		}
		return aXorB;
	}
}