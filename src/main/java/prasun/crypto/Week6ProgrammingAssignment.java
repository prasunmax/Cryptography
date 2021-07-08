package prasun.crypto;

import java.math.BigInteger;
import java.util.Arrays;

public class Week6ProgrammingAssignment {
	static double B = Math.pow(2, 20);

	public static void main(String[] args) {
		factChallenge1();
		factChallenge2();
		factChallenge3();
		factChallenge4();
	}

	private static void factChallenge1() {
		BigInteger N = new BigInteger("17976931348623159077293051907890247336179769789423065727343008115"
				+ "7732675805505620686985379449212982959585501387537164015710139858647833778606925583497"
				+ "5410851965916151280575759407526350074759352887108236499499407718956170543611494748650"
				+ "46711015101563940680527540071584560878577663743040086340742855278549092581");
		BigInteger A = N.sqrt().add(BigInteger.ONE); // This is to ensure that the square root is not less than the
														// number
		BigInteger x = A.pow(2).subtract(N).sqrt();
		BigInteger p = A.subtract(x);
		BigInteger q = A.add(x);
		if (N.equals(p.multiply(q))) {
			System.out.println(p);
			// System.out.println(q);
		}
		// System.out.println("print:" + A.subtract(x));
	}

	private static void factChallenge2() {
		BigInteger N = new BigInteger("6484558428080716696628242653467722787263437207069762630604390703787"
				+ "9730861808111646271401527606141756919558732184025452065542490671989"
				+ "2428844841839353281972988531310511738648965962582821502504990264452"
				+ "1008852816733037111422964210278402893076574586452336833570778346897"
				+ "15838646088239640236866252211790085787877");
		BigInteger A = N.sqrt().add(BigInteger.ONE); // This is to ensure that the square root is not less than the
														// number
		while (true) {
			A = A.add(BigInteger.ONE);
			BigInteger x = A.pow(2).subtract(N).sqrt();
			BigInteger p = A.subtract(x);
			BigInteger q = A.add(x);
			if (N.equals(p.multiply(q))) {
				System.out.println(p);
				break;
			}
		}
	}

	private static void factChallenge3() {
		BigInteger N = new BigInteger("72006226374735042527956443552558373833808445147399984182665305798191"
				+ "63556901883377904234086641876639384851752649940178970835240791356868"
				+ "77441155132015188279331812309091996246361896836573643119174094961348"
				+ "52463970788523879939683923036467667022162701835329944324119217381272"
				+ "9276147530748597302192751375739387929");
		BigInteger A = N.multiply(BigInteger.valueOf(6)).sqrt();

		// p = (A+x-1)/3 /////as 3p+2q is odd
		// q = (A-x)/2
		// N = pq
		// N= ((A+x-1)(A-x))/6
		// 6N = A^2 -x^2 - A +x
		// x^2-x = A^2 - A - 6N
		// c = A^2 - A - 6N Note: A^2 != 6N
		// a*x^2 -b*x -c = 0
		// N.multiply(BigInteger.valueOf(6)).sqrt();
		BigInteger a = BigInteger.ONE;
		BigInteger c = A.pow(2).add(A).subtract(N.multiply(BigInteger.valueOf(6)));

		BigInteger x = BigInteger.valueOf(4).multiply(c).add(a).sqrt().add(a).divide(BigInteger.valueOf(2));
		checkresults(x, A, N, a);
		x = BigInteger.valueOf(4).multiply(c).add(a).sqrt().subtract(a).divide(BigInteger.valueOf(2));
		checkresults(x, A, N, a);

	}

	static void checkresults(BigInteger x, BigInteger A, Object N, BigInteger a) {
		BigInteger p = A.add(x).add(a).divide(BigInteger.valueOf(3));
		BigInteger q = A.subtract(x).divide(BigInteger.valueOf(2));
		if (N.equals(p.multiply(q))) {
			System.out.println(p);
			return;
		}

		p = A.subtract(x).add(a).divide(BigInteger.valueOf(3));
		q = A.add(x).divide(BigInteger.valueOf(2));
		if (N.equals(p.multiply(q))) {
			System.out.println(p);
			return;
		}

	}

	private static void factChallenge4() {
		BigInteger e = BigInteger.valueOf(65537);
		BigInteger input = new BigInteger("2209645186741038177630656113488341801741006978789283107173183914367"
				+ "6135600120538004282329650473509424343946219751512256465839967942889460764542040581564748988"
				+ "0137348641204523252293201764879166664029975091887299716905260832220677716000193292608700095"
				+ "79993724077458967773697817571267229951148662959627934791540");
		BigInteger p = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764"
				+ "030073662768891111614362326998675040546094339320838419523375986027530441562135724301");
		BigInteger q = new BigInteger("1340780792994259709957402499820584612747936582059239337772356144372176"
				+ "4030073778560980348930557750569660049234002192590823085163940025485114449475265364281");
		BigInteger N = new BigInteger("17976931348623159077293051907890247336179769789423065727343008115"
				+ "7732675805505620686985379449212982959585501387537164015710139858647833778606925583497"
				+ "5410851965916151280575759407526350074759352887108236499499407718956170543611494748650"
				+ "46711015101563940680527540071584560878577663743040086340742855278549092581");

		BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		// Please refer to
		// https://math.stackexchange.com/questions/586263/rsa-encryption-decryption-scheme
		// as quick recap
		BigInteger decrypComponent = e.modInverse(phiN);

		BigInteger pkcsText = input.modPow(decrypComponent, N);
		byte[] bytearr = pkcsText.toByteArray();
		if (bytearr[0] == 2) {
			int j = 0;
			while (bytearr[j++] != 0 && j < bytearr.length)
				;
			String finalText = "";
			if (j < bytearr.length) {
//				while (j < bytearr.length) {
//					finalText += (char) bytearr[j];
//					j++;
//				}
				finalText = new String(Arrays.copyOfRange(bytearr, j, bytearr.length));
				System.out.println(finalText);
			}
		}
	}
}
