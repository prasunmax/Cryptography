package prasun.crypto;
import java.math.BigInteger;

public class Week4ProgrammingAssignment {
	public static void main(String[] args) {
		// Question1
		Question1();
	}

	private static void Question1() {
		String inputStr = "20814804c1767293b99f1d9cab3bc3e7 ac1e37bfb15599e5f40eef805488281d";
		String[] inpArr = inputStr.split(" ");
		// String cypherTextIV = XORTestForWeek1.hexToAscii(inpArr[0]);
		String cypherTextC0 = CipherUtils.hexToAscii(inpArr[1]);
		// CBC has two parts IV encrypted and the message encrypted
		// input Message
		String input = "Pay Bob 100$";
		// Changed message
		String desiredOutput = "Pay Bob 500$";
		// Check on paddings
		int lenC0 = cypherTextC0.length() - input.length();
		// Append padding to the input text
		for (int i = 0; i < lenC0; i++)
			input += lenC0;
		int lenTarget = cypherTextC0.length() - desiredOutput.length();
		for (int i = 0; i < lenTarget; i++)
			desiredOutput += lenTarget;
		// Xor the text based on paddings
		String xorredTexts = xorText(CipherUtils.asciiToHex(input), CipherUtils.asciiToHex(desiredOutput));
		lenTarget = inpArr[0].length() - xorredTexts.length();
		String newIV = xorText(inpArr[0], xorredTexts);
		System.out.println(newIV + " " + inpArr[1]);
	}

	private static String xorText(String cipheredText1, String cipheredText2) {

		BigInteger deciphered = new BigInteger(cipheredText1, 16).xor(new BigInteger(cipheredText2, 16));
		// System.out.println(new String(deciphered.toByteArray()));
		System.out.print(cipheredText1 + " xor " + cipheredText2 + "===");
		System.out.println(CipherUtils.asciiToHex(new String(deciphered.toByteArray())));
		return deciphered.toString(16);
	}

}
