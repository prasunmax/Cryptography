package prasun.crypto;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * Hint: XOR the ciphertexts together, and consider what happens when a space is XORed with a character in [a-zA-Z].
 */
public class Week1ProgrammingAssignment {
	static String MSGS[] = {
			"315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba50",
			"234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb741",
			"32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de812",
			"32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee41",
			"3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de812",
			"32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d",
			"32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af513",
			"315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e941",
			"271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f404",
			"466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d",
			"32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904" };

	private static final Map<Byte, Byte> ALPHABET_XOR_MAP = new HashMap<>();
	static {
		initAlphabetXorSpaceMapWithRangeOf((byte) 'a', (byte) 'z');
		initAlphabetXorSpaceMapWithRangeOf((byte) 'A', (byte) 'Z');
	}

	private static void initAlphabetXorSpaceMapWithRangeOf(byte from, byte to) {
		for (byte b = from; b <= to; b++) {
			byte byteXorSpace = (byte) (b ^ ' ');
			ALPHABET_XOR_MAP.put(byteXorSpace, b);
		}
	}

	public static void main(String[] args) {

		// String sampleText = "I am in a vast ocean of knowledge without knowing to
		// swim, I need to learn to stay alive";
		Map<Integer, List<Byte>> xorList = new TreeMap<>();
		// Lets take character 'e' and xor them, e^e^k = k
		for (String msg1 : MSGS) {
			for (String msg2 : MSGS) {
				BigInteger ct1 = new BigInteger(msg1, 16);
				BigInteger ct2 = new BigInteger(msg2, 16);
				// now xor each characters c1^c2 = p1^p2
				byte[] msgByteArry = ct1.xor(ct2).toByteArray();
				// Now xor with e to get the key list
				try {
					for (int j = 0; j < msgByteArry.length; j++) {
						byte xorByte = msgByteArry[j];
						if (ALPHABET_XOR_MAP.containsKey(xorByte)) {
							byte originalChar = ALPHABET_XOR_MAP.get(xorByte);

							byte keypart1 = (byte) (ct1.toByteArray()[j] ^ originalChar);
							byte keypart2 = (byte) (ct2.toByteArray()[j] ^ originalChar);
							System.out.println(String.format(
									"Recovered part of key at position\t%d\tOriginal character:\t%c Key parts: %d, %d",
									j, originalChar, keypart1, keypart2));
							xorList.putIfAbsent(j, new ArrayList<>());
							xorList.get(j).add(keypart1);
							xorList.get(j).add(keypart2);
						}
					}
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		xorList.entrySet().forEach(System.out::println);
		final byte[] key = new byte[MSGS[10].length() / 2];
		for (int i = 0; i < MSGS[10].length() / 2; i++) {
			List<Byte> keyParts = xorList.get(i);
			if (null == keyParts) {
				System.out.println(i + " Not found");
				continue;
			}
			// get the most used byte
			Map<Byte, Long> keyPartsWithFrequency = keyParts.stream()
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			key[i] = keyPartsWithFrequency.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue))
					.orElseThrow(AssertionError::new).getKey();
		}
		System.out.println(Arrays.toString(key));
		for (String msg : MSGS) {
			decipherText(msg, key);
		}

//		for (int i = 1; i < MSGS.length; i++) {
//			String msg = MSGS[i];
//			//xor msg[0] and msg[i]
//			String xoredMsg = encode(hexToAscii(msg), hexToAscii(prevMsg));
//			System.out.println(i + ":" + xoredMsg);
//			//now xor each character with e and store it in map
//			byte[] xoredByteArry = xoredMsg.getBytes();
//			for(byte b:xoredByteArry) {
//				
//			}
//		}

	}

	private static void decipherText(String cipheredText, byte[] key) {
		BigInteger deciphered = new BigInteger(cipheredText, 16).xor(new BigInteger(key));
		System.out.println(new String(deciphered.toByteArray()));
	}

	static String encode(String s, String key) {
		StringBuilder sb = new StringBuilder();
		if (s.length() <= key.length())
			for (int i = 0; i < s.length(); i++)
				sb.append((char) (s.charAt(i) ^ key.charAt(i % key.length())));
		else
			for (int i = 0; i < key.length(); i++)
				sb.append((char) (key.charAt(i) ^ s.charAt(i % s.length())));
		return sb.toString();
	}

}
