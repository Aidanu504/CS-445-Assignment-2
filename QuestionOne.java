import java.math.BigInteger;
import java.security.SecureRandom;

public class QuestionOne {
    private BigInteger n, e, d, z;

    public QuestionOne(int bitLength) {
        SecureRandom random = new SecureRandom();

        // two large primes (Utilized probablePrime so I would not have to implement a primality test)
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);

        // n = p * q
        n = p.multiply(q);
        
        // Z = (p-1) * (q-1)
        z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // e that gcd(e, Z) = 1
        e = BigInteger.valueOf(3);
        while (z.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.TWO); // ensure prime by incremnting by 2
        }

        // d that ed % z = 1
        d = e.modInverse(z);
    }

    // C = M^e mod n
    public BigInteger encrypt(BigInteger M) {
        return M.modPow(e, n);  
    }

    // M = C^d mod n
    public BigInteger decrypt(BigInteger C) {
        return C.modPow(d, n);  
    }

    // Signature = M^d mod n
    public BigInteger sign(BigInteger M) {
        return M.modPow(d, n);  
    }

    // VerifiedMessage = C^e mod n
    public boolean verify(BigInteger M, BigInteger C) {
        BigInteger verifiedMessage = C.modPow(e, n);  
        return verifiedMessage.equals(M); 
    }

    public static void main(String[] args) {
        QuestionOne rsa = new QuestionOne(512);

        BigInteger M = new BigInteger("12345");

        // Encrypt and print C
        BigInteger C = rsa.encrypt(M);
        System.out.println("Encrypted Message (C): " + C);

        // Decrypt and print M
        BigInteger decryptedM = rsa.decrypt(C);
        System.out.println("Decrypted Message (M): " + decryptedM);

        // Sign and print the signarture 
        BigInteger signature = rsa.sign(M);
        System.out.println("Signature: " + signature);
        
        // Verify the signiture and print boolean
        boolean isValid = rsa.verify(M, signature);
        System.out.println("Signature verified: " + isValid);
    }
}