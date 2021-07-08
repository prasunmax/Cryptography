package prasun.crypto;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.primitives.Bytes;

public class Week3ProgrammingAssignment {
	public static void main(String[] args) {
		// In this project we will be using SHA256 as the hash function.
		computeSHA256ofFile("6.2.birthday.mp4_download");
		computeSHA256ofFile("6.1.intro.mp4_download");
	}

	private static void computeSHA256ofFile(String fileName) {
		try {
			byte[] fileContent = Files.toByteArray(new File(fileName));
			int len = fileContent.length;
			byte[] sha256hex = null;
			HashCode sha256hexHas = null;
			String sha256hexcc = "";
			int blocksize = 1024;
			byte[] hashString, hashStringhas, hashStringhcc;
			while (len > 0) {
				if (len == fileContent.length) {
					if (len % 1024 != 0)
						blocksize = len % 1024;
				}
				hashString = Arrays.copyOfRange(fileContent, len - blocksize, len);
				hashStringhas = Arrays.copyOfRange(fileContent, len - blocksize, len);
				hashStringhcc = Arrays.copyOfRange(fileContent, len - blocksize, len);
				if (null != sha256hex) {
					hashString = Bytes.concat(hashString, sha256hex);
					hashStringhas = Bytes.concat(hashStringhas, sha256hexHas.asBytes());
					hashStringhcc = Bytes.concat(hashStringhcc, sha256hexcc.getBytes());
				}
				sha256hexHas = Hashing.sha256().hashString(hashString.toString(), StandardCharsets.US_ASCII);
				sha256hexcc = DigestUtils.sha256Hex(hashStringhcc);
				sha256hex = MessageDigest.getInstance("SHA-256").digest(hashString);
				len -= blocksize;
				blocksize = 1024;
			}
			System.out.println(fileName + "-----------------");
			System.out.println("Java security MessageDigest:" + CipherUtils.bytes2Hex(sha256hex));
			System.out.println("Hash using Guava Libraries:" + sha256hexHas);
			System.out.println("Apache Common DigestUtils:" + sha256hexcc);
			System.out.println("-----------------");
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
