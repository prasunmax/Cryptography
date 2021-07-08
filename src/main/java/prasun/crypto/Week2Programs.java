package prasun.crypto;
import java.math.BigInteger;
import java.util.Arrays;

public class Week2Programs {
	public static void main(String[] args) {
		// Question 4
		Question4();
		// Question 9
		Question9();
	}

	private static void Question9() {
		String keys[] = { "0011", "0101", "0000", "0000", "0000" };
		givenFunction(keys, "0110");// 0011
		givenFunction(keys, "0101");// 1010
		givenFunction(keys, "1110");// 0110
		// The Xor of all the outputs of function is the output of 1101
		System.out.println(outputFunction("0011", outputFunction("1010", "0110")));
	}

	private static String outputFunction(String input1, String input2) {
		BigInteger output = new BigInteger(input1, 2);
		output = new BigInteger(input2, 2).xor(output);
		return output.toString(2);
	}

	static void givenFunction(String[] keys, String input) {
		BigInteger output = new BigInteger(keys[0], 2);
		String[] strArr = input.split("");

		for (int i = 0; i < strArr.length + 1; i++) {
			if (i > 0 && strArr[i - 1].equals("1"))
				output = new BigInteger(keys[i], 2).xor(output);
		}
		System.out.println(output.toString(2));
		// System.out.println(XORTestForWeek1.asciiToHex(new
		// String(output.toByteArray())));
	}

	private static void Question4() {
		String[][][] ciphers = { { { "e86d2de2", "e1387ae9" }, { "1792d21d", "b645c008" } },
				{ { "5f67abaf", "5210722b" }, { "bbe033c0", "0bc9330e" } },
				{ { "7c2822eb", "fdc48bfb" }, { "325032a9", "c5e2364b" } },
				{ { "7b50baab", "07640c3d" }, { "ac343a22", "cea46d60" } },
				{ { "5f67abaf", "5210722b" }, { "bbe033c0", "0bc9330e" } },
				{ { "4af53267", "1351e2e1" }, { "87a40cfa", "8dd39154" } },
				{ { "2d1cfa42", "c0b1d266" }, { "eea6e3dd", "b2146dd0" } },
				{ { "9f970f4e", "932330e4" }, { "6068f0b1", "b645c008" } }, };
		Arrays.asList(ciphers).stream().forEach(cipher -> {
			for (int j = 0; j < cipher[0].length; j++) {
				decipherText(cipher[0][j], cipher[1][j]);
				// Take the one which is all 1
			}
		});
	}

	private static void decipherText(String cipheredText1, String cipheredText2) {

		BigInteger deciphered = new BigInteger(cipheredText1, 16).xor(new BigInteger(cipheredText2, 16));
		// System.out.println(new String(deciphered.toByteArray()));
		System.out.print(cipheredText1 + " xor " + cipheredText2 + "===");
		System.out.println(CipherUtils.asciiToHex(new String(deciphered.toByteArray())));
	}

	static String xorText(byte[] key) {
		StringBuilder sb = new StringBuilder();
		int length = key.length;
		for (int i = 0; i < length; i++)
			sb.append((char) (key[i]));
		return sb.toString();
	}
}
