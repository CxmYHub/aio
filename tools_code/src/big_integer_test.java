import tools.mathematics.big_integer;
import java.math.BigInteger;

public class big_integer_test {
    private static int passed = 0;
    private static int failed = 0;
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.println("Running big_integer tests...\n");

        testConstructors();
        testCompare();
        testAdd();
        testSubtract();
        testMultiply();
        testDivide();
        testGcd();
        testPower();

        System.out.println("\n" + (passed + failed) + " tests completed.");
        System.out.println(GREEN + passed + " passed" + RESET + (failed > 0 ? ", " + RED + failed + " failed" + RESET : ""));
    }

    // ----- utility methods -----
    private static void check(String operation, String inputDescription, String expected, String actual) {
        if (expected.equals(actual)) {
            passed++;
            System.out.println(GREEN + "[AC] " + operation + " " + inputDescription + " -> " + actual + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + operation + " " + inputDescription + " error, got " + actual + ", expected " + expected + RESET);
        }
    }

    private static void checkNull(String operation, String inputDescription, Object actual, boolean expectNull) {
        if (expectNull == (actual == null)) {
            passed++;
            System.out.println(GREEN + "[AC] " + operation + " " + inputDescription + " -> " + (actual == null ? "null" : actual) + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + operation + " " + inputDescription + " error, got " + actual + ", expected " + (expectNull ? "null" : "non-null") + RESET);
        }
    }

    private static String str(big_integer x) {
        try
        {
            return x.toString();
        }
        catch (NullPointerException e)
        {
            return "null";
        }
    }

    private static String str(byte[] arr) {
        return new big_integer(arr).toString();
    }

    // ----- test groups -----
    private static void testConstructors() {
        big_integer a;
        a = new big_integer("0");
        check("ctor", "\"0\"", "0", str(a));
        a = new big_integer("-0");
        check("ctor", "\"-0\"", "0", str(a));
        a = new big_integer("123");
        check("ctor", "\"123\"", "123", str(a));
        a = new big_integer("-456");
        check("ctor", "\"-456\"", "-456", str(a));
        a = new big_integer("0000123");
        check("ctor", "\"0000123\"", "123", str(a));
        a = new big_integer("-0000");
        check("ctor", "\"-0000\"", "0", str(a));
        String big = "1234567890123456789012345678901234567890";
        a = new big_integer(big);
        check("ctor", "big positive", big, str(a));
        a = new big_integer("-" + big);
        check("ctor", "big negative", "-" + big, str(a));
    }

    private static void testCompare() {
        big_integer a = new big_integer("123");
        big_integer b = new big_integer("123");
        check("cmp", "123 vs 123", "0", String.valueOf(a.compareTo(b)));
        check("cmp", "123 vs 124", "-1", String.valueOf(a.compareTo(new big_integer("124"))));
        check("cmp", "-123 vs -124", "1", String.valueOf(new big_integer("-123").compareTo(new big_integer("-124"))));
        check("cmp", "0 vs 0", "0", String.valueOf(new big_integer("0").compareTo(new big_integer("0"))));
        check("cmp", "0 vs -1", "1", String.valueOf(new big_integer("0").compareTo(new big_integer("-1"))));
        check("cmp", "-1 vs 0", "-1", String.valueOf(new big_integer("-1").compareTo(new big_integer("0"))));
        // static compare
        byte[] x = new big_integer("123").number;
        byte[] y = new big_integer("124").number;
        check("cmp_static", "123 vs 124", "-1", String.valueOf(big_integer.compare(x, y)));
    }

    private static void testAdd() {
        big_integer a, b, c;
        // basic
        a = new big_integer("123"); b = new big_integer("456");
        c = big_integer.add(a, b);
        check("add", "123+456", "579", str(c));
        a = new big_integer("-123"); b = new big_integer("456");
        check("add", "-123+456", "333", str(big_integer.add(a, b)));
        a = new big_integer("123"); b = new big_integer("-456");
        check("add", "123+(-456)", "-333", str(big_integer.add(a, b)));
        a = new big_integer("-123"); b = new big_integer("-456");
        check("add", "-123+(-456)", "-579", str(big_integer.add(a, b)));
        // zero
        a = new big_integer("0"); b = new big_integer("0");
        check("add", "0+0", "0", str(big_integer.add(a, b)));
        a = new big_integer("0"); b = new big_integer("123");
        check("add", "0+123", "123", str(big_integer.add(a, b)));
        // large
        String s1 = "99999999999999999999";
        String s2 = "88888888888888888888";
        BigInteger bi1 = new BigInteger(s1);
        BigInteger bi2 = new BigInteger(s2);
        String sum = bi1.add(bi2).toString();
        a = new big_integer(s1); b = new big_integer(s2);
        check("add", s1 + "+" + s2, sum, str(big_integer.add(a, b)));
        // negative large
        bi1 = new BigInteger("-" + s1);
        sum = bi1.add(bi2).toString();
        a = new big_integer("-" + s1); b = new big_integer(s2);
        check("add", "-" + s1 + "+" + s2, sum, str(big_integer.add(a, b)));
    }

    private static void testSubtract() {
        big_integer a, b;
        a = new big_integer("123"); b = new big_integer("456");
        check("sub", "123-456", "-333", str(big_integer.subtract(a, b)));
        a = new big_integer("-123"); b = new big_integer("456");
        check("sub", "-123-456", "-579", str(big_integer.subtract(a, b)));
        a = new big_integer("123"); b = new big_integer("-456");
        check("sub", "123-(-456)", "579", str(big_integer.subtract(a, b)));
        a = new big_integer("0"); b = new big_integer("0");
        check("sub", "0-0", "0", str(big_integer.subtract(a, b)));
        a = new big_integer("0"); b = new big_integer("123");
        check("sub", "0-123", "-123", str(big_integer.subtract(a, b)));
        // large
        String s1 = "100000000000000000000";
        String s2 = "1";
        a = new big_integer(s1); b = new big_integer(s2);
        check("sub", s1 + "-1", new BigInteger(s1).subtract(BigInteger.ONE).toString(), str(big_integer.subtract(a, b)));
    }

    private static void testMultiply() {
        big_integer a, b;
        a = new big_integer("12"); b = new big_integer("34");
        check("mul", "12*34", "408", str(big_integer.multiply(a, b)));
        a = new big_integer("-12"); b = new big_integer("34");
        check("mul", "-12*34", "-408", str(big_integer.multiply(a, b)));
        a = new big_integer("0"); b = new big_integer("999");
        check("mul", "0*999", "0", str(big_integer.multiply(a, b)));
        a = new big_integer("-0"); b = new big_integer("-456");
        check("mul", "0*(-456)", "0", str(big_integer.multiply(a, b)));
        // large
        String s1 = "12345678901234567890";
        String s2 = "98765432109876543210";
        BigInteger prod = new BigInteger(s1).multiply(new BigInteger(s2));
        a = new big_integer(s1); b = new big_integer(s2);
        check("mul", s1 + "*" + s2, prod.toString(), str(big_integer.multiply(a, b)));
        // static byte[] multiply
        byte[] f = new big_integer("5678").number;
        check("mul_static", "5678*7", new BigInteger("5678").multiply(BigInteger.valueOf(7)).toString(), str(big_integer.multiply(f, 7)));
    }

    private static void testDivide() {
        big_integer a, b;
        big_integer[] qr;
        // simple
        a = new big_integer("123"); b = new big_integer("10");
        qr = big_integer.divide(a, b);
        check("div", "123/10 q", "12", str(qr[0]));
        check("div", "123/10 r", "3", str(qr[1]));

        // negative divisor
        a = new big_integer("123"); b = new big_integer("-10");
        qr = big_integer.divide(a, b);
        check("div", "123/(-10) q", "-12", str(qr[0]));
        check("div", "123/(-10) r", "3", str(qr[1]));

        // negative dividend (Euclidean: -123/10 -> q=-13, r=7)
        a = new big_integer("-123"); b = new big_integer("10");
        qr = big_integer.divide(a, b);
        check("div", "-123/10 q", "-13", str(qr[0]));
        check("div", "-123/10 r", "7", str(qr[1]));

        // both negative (Euclidean: -123/-10 -> q=13, r=7)
        a = new big_integer("-123"); b = new big_integer("-10");
        qr = big_integer.divide(a, b);
        check("div", "-123/(-10) q", "13", str(qr[0]));
        check("div", "-123/(-10) r", "7", str(qr[1]));

        // zero dividend
        a = new big_integer("0"); b = new big_integer("5");
        qr = big_integer.divide(a, b);
        check("div", "0/5 q", "0", str(qr[0]));
        check("div", "0/5 r", "0", str(qr[1]));

        // division by zero
        a = new big_integer("5"); b = new big_integer("0");
        checkNull("div", "5/0", big_integer.divide(a, b), true);

        // large division (Euclidean)
        a = new big_integer("1234567890123456789012345678901234567890");
        b = new big_integer("987654321098765432109876543210");
        qr = big_integer.divide(a, b);
        BigInteger ba = new BigInteger("1234567890123456789012345678901234567890");
        BigInteger bb = new BigInteger("987654321098765432109876543210");
        BigInteger[] std = ba.divideAndRemainder(bb);
        // adjust to Euclidean remainder
        if (std[1].signum() < 0) {
            if (bb.signum() > 0) {
                std[0] = std[0].subtract(BigInteger.ONE);
                std[1] = std[1].add(bb);
            } else {
                std[0] = std[0].add(BigInteger.ONE);
                std[1] = std[1].subtract(bb);
            }
        }
        check("div", "large / q", std[0].toString(), str(qr[0]));
        check("div", "large / r", std[1].toString(), str(qr[1]));

        // one‑bit divisor
        big_integer[] qr1 = big_integer.divide(new big_integer("-123"), 10);
        check("div1", "-123/10 q", "-13", str(qr1[0]));
        check("div1", "-123/10 r", "7", str(qr1[1]));
    }

    private static void testGcd() {
        big_integer a, b;
        a = new big_integer("48"); b = new big_integer("18");
        check("gcd", "gcd(48,18)", "6", str(big_integer.gcd(a, b)));
        a = new big_integer("0"); b = new big_integer("5");
        check("gcd", "gcd(0,5)", "5", str(big_integer.gcd(a, b)));
        a = new big_integer("5"); b = new big_integer("0");
        check("gcd", "gcd(5,0)", "5", str(big_integer.gcd(a, b)));
        a = new big_integer("0"); b = new big_integer("0");
        check("gcd", "gcd(0,0)", "0", str(big_integer.gcd(a, b)));
        a = new big_integer("-48"); b = new big_integer("18");
        check("gcd", "gcd(-48,18)", "6", str(big_integer.gcd(a, b)));
        a = new big_integer("1071"); b = new big_integer("462");
        check("gcd", "gcd(1071,462)", "21", str(big_integer.gcd(a, b)));
        // large
        a = new big_integer("12345678901234567890");
        b = new big_integer("98765432109876543210");
        BigInteger g = new BigInteger("12345678901234567890").gcd(new BigInteger("98765432109876543210"));
        check("gcd", "large gcd", g.toString(), str(big_integer.gcd(a, b)));
    }

    private static void testPower() {
        big_integer base;
        // 0^0 -> null
        checkNull("pow", "0^0", big_integer.power(new big_integer("0"), 0), true);
        // 0^5 -> 0
        base = new big_integer("0");
        check("pow", "0^5", "0", str(big_integer.power(base, 5)));
        // 5^0 -> 1
        base = new big_integer("5");
        check("pow", "5^0", "1", str(big_integer.power(base, 0)));
        // 2^10
        base = new big_integer("2");
        check("pow", "2^10", "1024", str(big_integer.power(base, 10)));
        // (-3)^3 = -27
        base = new big_integer("-3");
        check("pow", "(-3)^3", "-27", str(big_integer.power(base, 3)));
        // (-3)^4 = 81
        check("pow", "(-3)^4", "81", str(big_integer.power(base, 4)));
        // large exponent
        base = new big_integer("2");
        String expected = BigInteger.valueOf(2).pow(100).toString();
        check("pow", "2^100", expected, str(big_integer.power(base, 100)));
        // negative exponent -> null
        checkNull("pow", "2^-1", big_integer.power(new big_integer("2"), -1), true);
    }
}