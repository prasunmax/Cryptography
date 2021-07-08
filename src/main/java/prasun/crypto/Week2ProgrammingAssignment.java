package prasun.crypto;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Week2ProgrammingAssignment {
	static String singleAlgorithm = "AES/ECB/NoPadding";

	public static void main(String[] args) {
		String key = "140b41b22a29beb4061bda66b6747e14";
		String cipher = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";
		System.out.println(DecyptCBC(key, cipher));
		key = "140b41b22a29beb4061bda66b6747e14";
		cipher = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253";
		System.out.println(DecyptCBC(key, cipher));
		key = "36f18357be4dbd77f050515c73fcf9f2";
		cipher = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
		System.out.println(DecyptCntr(key, cipher));
		key = "36f18357be4dbd77f050515c73fcf9f2";
		cipher = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
		System.out.println(DecyptCntr(key, cipher));

	}

	private static String DecyptCntr(String key, String cipher) {
		// https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Counter_(CTR)
		StringBuilder plainText = new StringBuilder();

		try {
			// Actual Method to be implemented in projects
			Cipher cipherProcessor = Cipher.getInstance("AES/ECB/NoPadding");
			// IvParameterSpec ivSpec = new
			// IvParameterSpec(XORTestForWeek1.hex2Bytes(cipher.substring(0, 32)));
			Key secretKey = new SecretKeySpec(CipherUtils.hex2Bytes(key), "AES");
			cipherProcessor.init(Cipher.ENCRYPT_MODE, secretKey);
			String hexIV = cipher.substring(0, 32);

			String cipherText = cipher.substring(32);
			int len = cipherText.length() / key.length();
			String cipherBloc = "";
			if (cipherText.length() > len * key.length())
				len++;
			for (int i = 0; i < len; i++) {
				try {
					cipherBloc = cipherText.substring(i * key.length(), (i + 1) * key.length());
				} catch (Exception e) {
					cipherBloc = cipherText.substring(i * key.length());
				}
				byte[] iv = CipherUtils.hex2Bytes(hexIV);
				byte[] encryptedBloc = cipherProcessor.doFinal(iv);
				byte[] decrypted = CipherUtils.xor(encryptedBloc, CipherUtils.hex2Bytes(cipherBloc));
				plainText.append(new String(decrypted));
				BigInteger integer = new BigInteger(hexIV, 16);
				integer = integer.add(BigInteger.ONE);
				hexIV = integer.toString(16);
			}
			// System.out.println(new
			// String(cipherProcessor.doFinal(XORTestForWeek1.hex2Bytes(cipher.substring(32)))));
			// System.out.println(plainText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
		}

		return plainText.toString();
	}

	private static String DecyptCBC(String cbcKey, String cipher) {
		// https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Cipher_Block_Chaining_(CBC)
		// number of elements in array
		int len = cipher.length() / cbcKey.length();
		String[] cipherArr = new String[len];

		/// IV is the first element of the cipherArr
		// String plainText = AESUtil.decrypt(algorithm, cipherText, key,
		// ivParameterSpec);
		Cipher cipherProcessor;
		StringBuilder plainText1 = new StringBuilder();
		Key secretKey = new SecretKeySpec(CipherUtils.hex2Bytes(cbcKey), "AES");
		try {
			cipherProcessor = Cipher.getInstance(singleAlgorithm);

			// BigInteger keyBytes = new BigInteger(cbcKey, 16);
			// Key secretKey = new SecretKeySpec(keyBytes.toByteArray(), "AES");

			byte[] iv = null;

			plainText1 = new StringBuilder();

			cipherProcessor.init(Cipher.DECRYPT_MODE, secretKey);
			for (int i = 0; i < len; i++) {
				cipherArr[i] = cipher.substring(i * cbcKey.length(), (i + 1) * cbcKey.length());
				if (i != 0) {
					byte[] plainText = cipherProcessor.doFinal(CipherUtils.hex2Bytes(cipherArr[i]));
					byte[] decrypted = CipherUtils.xor(plainText, iv);
					plainText1.append(new String(decrypted));
					if (i == len - 1) {
						byte paddingBytesCount = decrypted[15];
						removePKCS5Padding(plainText1, paddingBytesCount);
					}
				}
				iv = CipherUtils.hex2Bytes(cipherArr[i]);
			}

			// Actual Method to be implemented in projects
			cipherProcessor = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			IvParameterSpec ivSpec = new IvParameterSpec(CipherUtils.hex2Bytes(cipherArr[0]));
			cipherProcessor.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
			System.out.println(new String(cipherProcessor.doFinal(CipherUtils.hex2Bytes(cipher.substring(32)))));

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return plainText1.toString();
	}

	private static void removePKCS5Padding(StringBuilder plainText, byte paddingBytesCount) {
		plainText.delete(plainText.length() - paddingBytesCount, plainText.length());

	}
}
