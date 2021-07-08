package prasun.crypto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Week5ProgrammingAssignment {
	private static final BigInteger p = new BigInteger(
			"13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171");
	private static final BigInteger g = new BigInteger(
			"11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568");
	private static final BigInteger h = new BigInteger(
			"3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333");
	static double B = Math.pow(2, 20);

	public static void main(String[] args) {

		Map<BigInteger, Integer> lhs = new HashMap<BigInteger, Integer>();
		for (int j = 0; j < B; j++) {
			BigInteger result = h.multiply(g.modPow(BigInteger.valueOf(-j), p)).mod(p);
			if (!lhs.containsKey(result)) {
				lhs.put(result, j);
				// System.out.println(lhs);
			}
		}
		System.out.println("Step1 complete");
		for (int j = 0; j < B; j++) {
			BigInteger result = g.modPow(BigInteger.valueOf((long) (j * B)), p);
			if (lhs.containsKey(result)) {
				int x1 = lhs.get(result);
				System.out.println("Done");
				System.out.println("x0 = " + j);
				System.out.println("x1 = " + x1);
				System.out.println("Result:" + (long) (j * B + x1));
			}
		}
	}
}
