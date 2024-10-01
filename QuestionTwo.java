import java.math.BigInteger;
import java.security.SecureRandom;

public class QuestionTwo {
    private SecureRandom random;

    public QuestionTwo() {
        this.random = new SecureRandom();
    }

    // Fermat's Little Theorem implementation 
    public boolean fermatTest(BigInteger n, int k) {
        // Check n < 2
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        // If n = 2 or 3 then prime
        if (n.compareTo(BigInteger.valueOf(3)) < 0) return true;
        // If n even then composite 
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        // test k times
        for (int i = 0; i < k; i++) {
            // random integer a in range [2, n-2]
            BigInteger a = BigInteger.TWO.add(new BigInteger(n.bitLength() - 2, random).mod(n.subtract(BigInteger.TWO)));
            // a^(n-1) mod n
            BigInteger result = a.modPow(n.subtract(BigInteger.ONE), n);
            // If result != 1 then n composite
            if (!result.equals(BigInteger.ONE)) {
                return false;
            }
        }

        // If tests pass n is probably prime
        return true;
    }

    public static void main(String[] args) {
        QuestionTwo primalityTest = new QuestionTwo();
        int[] confidenceParams = {7, 10, 20}; 

        SecureRandom random = new SecureRandom();

        // Generate a single random number between 2 and Integer.MAX_VALUE
        BigInteger number = BigInteger.valueOf(random.nextInt(Integer.MAX_VALUE - 1) + 2);
        System.out.println("Testing number: " + number);

        // Test the number for each confidence parameter
        for (int k : confidenceParams) {
            boolean result = primalityTest.fermatTest(number, k);
            if (result) {
                System.out.println("Confidence parameter " + k + ": " + number + " is prime.");
            } else {
                System.out.println("Confidence parameter " + k + ": " + number + " is composite.");
            }
        }
    }
}
