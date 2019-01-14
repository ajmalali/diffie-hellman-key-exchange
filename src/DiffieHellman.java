import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class DiffieHellman {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        BigInteger modulo = null, base = null;

        while (true) {
            try {
                System.out.println("Enter the prime number (modulo): ");
                modulo = new BigInteger(scan.next());
                if (!isPrime(modulo)) {
                    System.out.println(modulo + " is not a prime number\nplease try again!");
                    continue;
                }

                System.out.println("Enter the primitive root (base): ");
                base = new BigInteger(scan.next());
                if (!isPrimitiveRoot(base, modulo)) {
                    System.out.println(base + " is not a primitive root of " + modulo + "\nplease try again!");
                    continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input\nplease try again!");
                continue;
            }

            break;
        }

        Random rand = new Random();
        BigInteger privateA = new BigInteger(modulo.bitLength(), rand);
        BigInteger privateB = new BigInteger(modulo.bitLength(), rand);
        BigInteger A = base.modPow(privateA, modulo);
        BigInteger B = base.modPow(privateB, modulo);
        boolean ran = false;

        while (true) {
            System.out.println("\nEnter you choice:\n1-Generate shared key\n2-Find shared key\n3-Exit");
            String choice = scan.next();

            long start = System.currentTimeMillis();
            switch (choice) {
                case "1":
                    ran = true;
                    System.out.println("\nGenerated private number of A: " + privateA);
                    System.out.println("Generated private number of B: " + privateB);

                    System.out.println("\nA sends " + A + " to be B" + "\nB sends " + B + " to be A");

                    BigInteger sharedSecretA = B.modPow(privateA, modulo);
                    BigInteger sharedSecretB = A.modPow(privateB, modulo);

                    System.out.println("\nA has the shared key: " + sharedSecretA + "\nB has the shared key: " + sharedSecretB);
                    System.out.println("\nExecution time: " + (System.currentTimeMillis() - start) / 1000.0 + "s");
                    break;

                case "2":
                    if (!ran)
                        System.out.println("\nPlease run option 1 first before running this option");
                    else {
                        BigInteger sharedKey = B.modPow(getPow(modulo, base, A), modulo);
                        System.out.println("\nShared secret: " + sharedKey);
                        System.out.println("Execution time: " + (System.currentTimeMillis() - start) / 1000.0 + "s");
                    }
                    break;

                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice try again");
            }
        }
    }

    private static boolean isPrime(BigInteger number) {
        return number.isProbablePrime(20);
    }

    private static boolean isPrimitiveRoot(BigInteger base, BigInteger modulo) {
        if (!modulo.gcd(base).equals(BigInteger.ONE))
            return false;

        BigInteger s = modulo.subtract(BigInteger.ONE);
        Set<BigInteger> factors = getPrimeFactors(s);
        for (BigInteger factor : factors) {
            if (base.modPow(s.divide(factor), modulo).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    private static Set<BigInteger> getPrimeFactors(BigInteger number) {
        Set<BigInteger> factors = new HashSet<>();

        BigInteger two = new BigInteger("2");
        for (BigInteger i = two; i.compareTo(number) < 1; i = i.add(BigInteger.ONE)) {
            if (number.mod(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                while (number.mod(i).equals(BigInteger.ZERO)) {
                    number = number.divide(i);
                    if (number.isProbablePrime(20)) {
                        factors.add(number);
                        return factors;
                    }
                }
            }
        }
        return factors;
    }

    private static BigInteger getPow(BigInteger modulo, BigInteger base, BigInteger A) {
        BigInteger falsePow = BigInteger.valueOf(-1);
        BigInteger i;
        for (i = BigInteger.ONE; i.compareTo(modulo) < 1; i = i.add(BigInteger.ONE)) {
            if (base.modPow(i, modulo).equals(A)) return i;
        }
        return falsePow;
    }
}