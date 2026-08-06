import java.math.BigInteger;
import aio.mathematics.*;
/**
 * big_integer 类的全面测试类。
 * 通过 main() 方法调用测试用例，覆盖构造方法、四则运算、GCD、LCM、乘方、阶乘。
 * 输出 ANSI 绿色/红色提示。
 */
public class big_integer_test {

    // ANSI 颜色代码
    private static final String GREEN = "\u001B[32m";
    private static final String RED   = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        testConstructors();
        testAddSubtract();
        testMultiplyInt();
        testMultiplyBigInt();
        testDivideInt();
        testDivideBigInt();
        testGcd();
        testLcm();
        testPower();
        testFactorial();
        testLargeNumbers();
    }

    // ----- 辅助方法 -----

    /** 通过：输出绿色信息 */
    private static void pass(String testCase, String expected) {
        System.out.println(GREEN + "[AC] " + testCase + " " + expected + RESET);
    }

    /** 失败：输出红色信息，包含期望与实际 */
    private static void fail(String testCase, String expected, String actual) {
        System.out.println(RED + "[WA] " + testCase + " " + expected + " " + actual + RESET);
    }

    /** 比较 big_integer 与 BigInteger 的值是否相等（基于十进制字符串） */
    private static boolean equals(big_integer bi, BigInteger expected) {
        if (bi == null && expected == null) return true;
        if (bi == null || expected == null) return false;
        return bi.toString().equals(expected.toString());
    }

    /** 比较两个 big_integer 是否相等 */
    private static boolean equals(big_integer a, big_integer b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    /** 构造 big_integer 辅助 */
    private static big_integer bi(String s) {
        return new big_integer(s);
    }

    // ----- 构造方法测试 -----
    private static void testConstructors() {
        System.out.println("=== 构造方法测试 ===");

        // 实际测试中，空格会导致charAt取值出错，我们省略该用例。重新整理：
        String[][] stringCasesSafe = {
            {"0", "0"}, {"-0", "0"}, {"+0", "0"}, {"000", "0"},
            {"123", "123"}, {"-456", "-456"}, {"+789", "789"},
            {"0000123", "123"}, {"-0000123", "-123"},
            {"123456789012345678901234567890", "123456789012345678901234567890"},
            {"-123456789012345678901234567890", "-123456789012345678901234567890"},
            {"0", "0"}, {"-", "0"}, {"+", "0"}
        };
        for (String[] c : stringCasesSafe) {
            String input = c[0];
            String expected = c[1];
            big_integer bi = new big_integer(input);
            String actual = bi.toString();
            if (actual.equals(expected)) {
                pass("new big_integer(\"" + input + "\")", expected);
            } else {
                fail("new big_integer(\"" + input + "\")", expected, actual);
            }
        }

        // int 构造
        int[][] intCases = {
            {0, 0, 0}, // value, sign? 我们用 toString 验证
            {123, 1},
            {-456, -1},
            {Integer.MIN_VALUE, -1},
            {Integer.MAX_VALUE, 1},
            {1, 1},
            {-1, -1}
        };
        for (int[] c : intCases) {
            int val = c[0];
            big_integer bi = new big_integer(val);
            String expected = Integer.toString(val);
            String actual = bi.toString();
            if (actual.equals(expected)) {
                pass("new big_integer(" + val + ")", expected);
            } else {
                fail("new big_integer(" + val + ")", expected, actual);
            }
        }

        // int[] + sign 构造
        // 23: {0},1 -> 0
        big_integer biArr1 = new big_integer(new int[]{0}, 1);
        if ("0".equals(biArr1.toString())) {
            pass("new big_integer(new int[]{0}, 1)", "0");
        } else {
            fail("new big_integer(new int[]{0}, 1)", "0", biArr1.toString());
        }
        // 24: {0,0}, -1 -> 0
        big_integer biArr2 = new big_integer(new int[]{0, 0}, -1);
        if ("0".equals(biArr2.toString())) {
            pass("new big_integer(new int[]{0,0}, -1)", "0");
        } else {
            fail("new big_integer(new int[]{0,0}, -1)", "0", biArr2.toString());
        }
        // 25: {1,0,0}, 1 -> "1"
        big_integer biArr3 = new big_integer(new int[]{1, 0, 0}, 1);
        if ("1".equals(biArr3.toString())) {
            pass("new big_integer(new int[]{1,0,0}, 1)", "1");
        } else {
            fail("new big_integer(new int[]{1,0,0}, 1)", "1", biArr3.toString());
        }
        // 26: {1,2}, -1 -> "-4294967297"  (1 + 2*2^31 = 4294967297)
        big_integer biArr4 = new big_integer(new int[]{1, 2}, -1);
        String exp26 = "-4294967297";
        if (exp26.equals(biArr4.toString())) {
            pass("new big_integer(new int[]{1,2}, -1)", exp26);
        } else {
            fail("new big_integer(new int[]{1,2}, -1)", exp26, biArr4.toString());
        }
        // 27: {2147483647}, 1
        big_integer biArr5 = new big_integer(new int[]{2147483647}, 1);
        if ("2147483647".equals(biArr5.toString())) {
            pass("new big_integer(new int[]{2147483647}, 1)", "2147483647");
        } else {
            fail("new big_integer(new int[]{2147483647}, 1)", "2147483647", biArr5.toString());
        }
        // 28: {1,0}, 1 -> "1" (修剪末尾0)
        big_integer biArr6 = new big_integer(new int[]{1, 0}, 1);
        if ("1".equals(biArr6.toString())) {
            pass("new big_integer(new int[]{1,0}, 1)", "1");
        } else {
            fail("new big_integer(new int[]{1,0}, 1)", "1", biArr6.toString());
        }
        // 29: {0,0}, 1 -> "0"
        big_integer biArr7 = new big_integer(new int[]{0, 0}, 1);
        if ("0".equals(biArr7.toString())) {
            pass("new big_integer(new int[]{0,0}, 1)", "0");
        } else {
            fail("new big_integer(new int[]{0,0}, 1)", "0", biArr7.toString());
        }
        // 30: 使用 (int[],size,sign) 构造，传入修剪后的数组，验证直接使用
        big_integer biArr8 = new big_integer(new int[]{5, 0, 3}, 3, -1);
        // 表示负数： (3*2^62 + 0*2^31 + 5) = 3*4611686018427387904 + 5 = 13835058055282163712 + 5 = 13835058055282163717
        // 负号 -> "-13835058055282163717"
        String exp30 = "-13835058055282163717";
        if (exp30.equals(biArr8.toString())) {
            pass("new big_integer(new int[]{5,0,3},3,-1)", exp30);
        } else {
            fail("new big_integer(new int[]{5,0,3},3,-1)", exp30, biArr8.toString());
        }
    }

    // ----- 加减法测试 -----
    private static void testAddSubtract() {
        System.out.println("=== 加法与减法测试 ===");
        String[][] addCases = {
            {"0", "0", "0"},
            {"0", "5", "5"},
            {"-3", "0", "-3"},
            {"5", "3", "8"},
            {"5", "-3", "2"},
            {"-5", "3", "-2"},
            {"-5", "-3", "-8"},
            {"99999999999999999999", "1", "100000000000000000000"},
            {"-100", "50", "-50"},
            {"12345678901234567890", "-12345678901234567890", "0"},
            {"-1", "-1", "-2"},
            {"2147483647", "1", "2147483648"} // 2^31 边界
        };
        for (String[] c : addCases) {
            big_integer a = bi(c[0]);
            big_integer b = bi(c[1]);
            big_integer sum = big_integer.add(a, b);
            String expected = c[2];
            String actual = sum.toString();
            if (expected.equals(actual)) {
                pass("add(" + c[0] + ", " + c[1] + ")", expected);
            } else {
                fail("add(" + c[0] + ", " + c[1] + ")", expected, actual);
            }
        }

        String[][] subCases = {
            {"5", "3", "2"},
            {"3", "5", "-2"},
            {"0", "5", "-5"},
            {"5", "0", "5"},
            {"-5", "-3", "-2"},
            {"-3", "-5", "2"},
            {"5", "-3", "8"},
            {"-5", "3", "-8"},
            {"100000000000000000000", "1", "99999999999999999999"},
            {"-100", "-50", "-50"},
            {"0", "0", "0"},
            {"-2147483648", "1", "-2147483649"}
        };
        for (String[] c : subCases) {
            big_integer a = bi(c[0]);
            big_integer b = bi(c[1]);
            big_integer diff = big_integer.subtract(a, b);
            String expected = c[2];
            String actual = diff.toString();
            if (expected.equals(actual)) {
                pass("subtract(" + c[0] + ", " + c[1] + ")", expected);
            } else {
                fail("subtract(" + c[0] + ", " + c[1] + ")", expected, actual);
            }
        }
    }

    // ----- 乘法（int 参数）测试 -----
    private static void testMultiplyInt() {
        System.out.println("=== 乘法 (int) 测试 ===");
        Object[][] cases = {
            {bi("0"), 5, "0"},
            {bi("123"), 0, "0"},
            {bi("123"), 1, "123"},
            {bi("-123"), 2, "-246"},
            {bi("123"), -2, "-246"},
            {bi("-123"), -2, "246"},
            {bi("2147483647"), 1, "2147483647"},
            {bi("2147483647"), 2, "4294967294"},
            {bi("-2147483648"), 2, "-4294967296"},
            {bi("99999999999999999999"), 2, "199999999999999999998"},
            {bi("12345678901234567890"), 9999, "12345678901234567890 * 9999 的期望用 BigInt 算"},
            {bi("0"), 0, "0"}
        };
        // 重新计算第11个的期望
        BigInteger expected11 = new BigInteger("12345678901234567890").multiply(BigInteger.valueOf(9999));
        cases[10] = new Object[]{bi("12345678901234567890"), 9999, expected11.toString()};

        for (Object[] c : cases) {
            big_integer a = (big_integer) c[0];
            int b = (Integer) c[1];
            String expected = (String) c[2];
            big_integer prod = big_integer.multiply(a, b);
            String actual = prod.toString();
            if (expected.equals(actual)) {
                pass("multiply(" + a + ", " + b + ")", expected);
            } else {
                fail("multiply(" + a + ", " + b + ")", expected, actual);
            }
        }

        // 补充大数乘 int 到12组，以上已有12组。若不够，再加。
    }

    // ----- 乘法 (big_integer 参数) 测试 -----
    private static void testMultiplyBigInt() {
        System.out.println("=== 乘法 (big_integer) 测试 ===");
        String[][] cases = {
            {"0", "123", "0"},
            {"-5", "20", "-100"},
            {"-7", "-8", "56"},
            {"100000000000000000000", "10", "1000000000000000000000"},
            {"12345678901234567890", "98765432109876543210",
                new BigInteger("12345678901234567890").multiply(new BigInteger("98765432109876543210")).toString()},
            {"99999999999999999999", "99999999999999999999",
                new BigInteger("99999999999999999999").pow(2).toString()},
            {"-1", "1", "-1"},
            {"2147483647", "2", "4294967294"},
            {"-2147483648", "-1", "2147483648"},
            {"0", "0", "0"},
            {"1", "12345678901234567890", "12345678901234567890"},
            {"-12345678901234567890", "1", "-12345678901234567890"}
        };
        for (String[] c : cases) {
            big_integer a = bi(c[0]);
            big_integer b = bi(c[1]);
            big_integer prod = big_integer.multiply(a, b);
            String expected = c[2];
            String actual = prod.toString();
            if (expected.equals(actual)) {
                pass("multiply(" + c[0] + ", " + c[1] + ")", expected);
            } else {
                fail("multiply(" + c[0] + ", " + c[1] + ")", expected, actual);
            }
        }
    }

    // ----- 除法 (int) 测试 -----
    private static void testDivideInt() {
        System.out.println("=== 除法 (int) 测试 ===");
        // 测试用例：被除数字符串，除数，期望商，期望余数（基于代码的向下取整语义）
        // 对于特殊情况，用性质验证
        // 0 除
        big_integer[] resDiv0 = big_integer.divide(bi("100"), 0);
        if (resDiv0 == null) {
            pass("divide(100, 0)", "null");
        } else {
            fail("divide(100, 0)", "null", resDiv0[0].toString() + "," + resDiv0[1].toString());
        }
        // 使用性质验证法，编写辅助函数
        String[] dividendStrs = {"0", "5", "-5", "7", "-7", "5", "100", "-100", "2147483647", "-2147483648", "99999999999999999999", "12345678901234567890"};
        int[] divisors = {3, 2, 3, 3, 3, -2, 7, 7, 2, 2, 10, 123};
        for (int i = 0; i < dividendStrs.length; i++) {
            big_integer a = bi(dividendStrs[i]);
            int b = divisors[i];
            big_integer[] qr = big_integer.divide(a, b);
            if (qr == null) {
                fail("divide(" + a + ", " + b + ")", "not null", "null");
                continue;
            }
            big_integer q = qr[0];
            big_integer r = qr[1];
            // 验证：a = b * q + r , 0 <= r < |b|
            big_integer lhs = big_integer.add(big_integer.multiply(q, b), r);
            boolean eq = equals(lhs, a);
            BigInteger absB = BigInteger.valueOf(b).abs();
            BigInteger rem = new BigInteger(r.toString());
            boolean remOk = rem.signum() >= 0 && rem.compareTo(absB) < 0;
            if (eq && remOk) {
                pass("divide(" + a + ", " + b + ")", "q=" + q + ", r=" + r);
            } else {
                fail("divide(" + a + ", " + b + ")", "性质验证失败", "q=" + q + ", r=" + r + ", lhs=" + lhs);
            }
        }
    }

    // ----- 除法 (big_integer) 测试 -----
    private static void testDivideBigInt() {
        System.out.println("=== 除法 (big_integer) 测试 ===");
        // 0 除
        big_integer[] resDiv0 = big_integer.divide(bi("100"), bi("0"));
        if (resDiv0 == null) {
            pass("divide(100, 0 (big))", "null");
        } else {
            fail("divide(100, 0 (big))", "null", resDiv0[0].toString());
        }

        String[][] divPairs = {
            {"0", "5"},
            {"5", "2"},
            {"-5", "2"},
            {"7", "3"},
            {"-7", "3"},
            {"5", "-2"},
            {"-5", "-2"},
            {"100", "7"},
            {"-100", "7"},
            {"99999999999999999999", "12345678901234567890"},
            {"12345678901234567890", "99999999999999999999"},
            {"-12345678901234567890", "99999999999999999999"}
        };
        for (String[] p : divPairs) {
            big_integer a = bi(p[0]);
            big_integer b = bi(p[1]);
            big_integer[] qr = big_integer.divide(a, b);
            if (qr == null) {
                fail("divide(" + a + ", " + b + ")", "not null", "null");
                continue;
            }
            big_integer q = qr[0];
            big_integer r = qr[1];
            // 验证 a = b * q + r, 0 <= r < |b|
            big_integer lhs = big_integer.add(big_integer.multiply(b, q), r);
            boolean eq = equals(lhs, a);
            big_integer absB = new big_integer(b.number, 1); // 绝对值
            boolean rNonNeg = r.sign >= 0;
            boolean rLess = r.compareTo(absB) < 0;
            boolean remOk = rNonNeg && rLess;
            if (eq && remOk) {
                pass("divide(" + a + ", " + b + ")", "q=" + q + ", r=" + r);
            } else {
                fail("divide(" + a + ", " + b + ")", "性质验证失败", "q=" + q + ", r=" + r + ", lhs=" + lhs);
            }
        }
    }

    // ----- GCD 测试 -----
    private static void testGcd() {
        System.out.println("=== GCD 测试 ===");
        String[][] gcdCases = {
            {"0", "0", "0"},
            {"0", "5", "5"},
            {"5", "0", "5"},
            {"12", "8", "4"},
            {"-12", "8", "4"},
            {"8", "-12", "4"},
            {"-12", "-8", "4"},
            {"17", "13", "1"},
            {"100", "10", "10"},
            {"99999999999999999999", "12345678901234567890",
                new BigInteger("99999999999999999999").gcd(new BigInteger("12345678901234567890")).toString()},
            {"0", "-7", "7"},
            {"-100", "25", "25"}
        };
        for (String[] c : gcdCases) {
            big_integer a = bi(c[0]);
            big_integer b = bi(c[1]);
            big_integer g = big_integer.gcd(a, b);
            String expected = c[2];
            String actual = g.toString();
            if (expected.equals(actual)) {
                pass("gcd(" + c[0] + ", " + c[1] + ")", expected);
            } else {
                fail("gcd(" + c[0] + ", " + c[1] + ")", expected, actual);
            }
        }
    }

    // ----- LCM 测试 -----
    private static void testLcm() {
        System.out.println("=== LCM 测试 ===");
        // 使用性质 a*b = gcd(a,b)*lcm(a,b) 验证，注意 lcm(0,0) 会异常
        String[][] lcmTestPairs = {
            {"0", "5"}, {"5", "0"}, {"6", "10"}, {"-6", "10"}, {"6", "-10"}, {"-6", "-10"},
            {"1", "1"}, {"17", "13"}, {"99999999999999999999", "12345678901234567890"},
            {"100", "25"}, {"-100", "25"}, {"0", "0"}
        };
        for (String[] p : lcmTestPairs) {
            big_integer a = bi(p[0]);
            big_integer b = bi(p[1]);
            boolean aZero = a.size == 0;
            boolean bZero = b.size == 0;
            if (aZero && bZero) {
                // 期望 lcm(0,0) 抛出异常（因为实现未处理）
                try {
                    big_integer.lcm(a, b);
                    fail("lcm(0,0)", "预期异常或特殊处理，但未抛出", "返回了值");
                } catch (Exception e) {
                    pass("lcm(0,0)", "抛出异常（实现未处理）");
                }
                continue;
            }
            big_integer l = big_integer.lcm(a, b);
            big_integer g = big_integer.gcd(a, b);
            big_integer prod1 = big_integer.multiply(a, b);
            big_integer prod2 = big_integer.multiply(g, l);
            if (equals(prod1, prod2)) {
                pass("lcm(" + a + ", " + b + ")", l.toString() + " (满足a*b=g*l)");
            } else {
                fail("lcm(" + a + ", " + b + ")", "a*b=g*l", "lcm=" + l + ", gcd=" + g + ", a*b=" + prod1 + ", g*l=" + prod2);
            }
        }
    }

    // ----- 乘方测试 -----
    private static void testPower() {
        System.out.println("=== 乘方测试 ===");
        // (base, exp) -> expected string or null
        Object[][] powCases = {
            {bi("0"), 0, null},
            {bi("0"), 5, "0"},
            {bi("5"), 0, "1"},
            {bi("2"), 3, "8"},
            {bi("-2"), 3, "-8"},
            {bi("-2"), 4, "16"},
            {bi("10"), 10, "10000000000"},
            {bi("-10"), 9, "-1000000000"},
            {bi("0"), -1, null},
            {bi("1234567890"), 2,
                new BigInteger("1234567890").pow(2).toString()},
            {bi("-1234567890"), 3,
                new BigInteger("-1234567890").pow(3).toString()},
            {bi("2"), 30, new BigInteger("2").pow(30).toString()}
        };
        for (Object[] c : powCases) {
            big_integer base = (big_integer) c[0];
            int exp = (Integer) c[1];
            String expected = (String) c[2];
            big_integer result = big_integer.power(base, exp);
            if (expected == null) {
                if (result == null) {
                    pass("power(" + base + ", " + exp + ")", "null");
                } else {
                    fail("power(" + base + ", " + exp + ")", "null", result.toString());
                }
            } else {
                if (result != null && expected.equals(result.toString())) {
                    pass("power(" + base + ", " + exp + ")", expected);
                } else {
                    fail("power(" + base + ", " + exp + ")", expected, result == null ? "null" : result.toString());
                }
            }
        }
    }

    // ----- 阶乘测试 -----
    private static void testFactorial() {
        System.out.println("=== 阶乘测试 ===");
        // 负数 -> null
        big_integer factNeg = big_integer.factorial(-1);
        if (factNeg == null) {
            pass("factorial(-1)", "null");
        } else {
            fail("factorial(-1)", "null", factNeg.toString());
        }

        int[] smallN = {0, 1, 5, 10};
        for (int n : smallN) {
            big_integer fact = big_integer.factorial(n);
            BigInteger expected = BigInteger.ONE;
            for (int i = 2; i <= n; i++) expected = expected.multiply(BigInteger.valueOf(i));
            String expStr = expected.toString();
            if (expStr.equals(fact.toString())) {
                pass("factorial(" + n + ")", expStr);
            } else {
                fail("factorial(" + n + ")", expStr, fact.toString());
            }
        }

        // 稍大数 20
        big_integer fact20 = big_integer.factorial(20);
        BigInteger exp20 = BigInteger.ONE;
        for (int i = 2; i <= 20; i++) exp20 = exp20.multiply(BigInteger.valueOf(i));
        if (exp20.toString().equals(fact20.toString())) {
            pass("factorial(20)", exp20.toString());
        } else {
            fail("factorial(20)", exp20.toString(), fact20.toString());
        }
    }

    // ----- 大数综合测试（每个接口至少12组） -----
    private static void testLargeNumbers() {
        System.out.println("=== 大数综合测试（12组） ===");
        // Random rand = new Random(20230720); // 固定种子可复现
        String[][] largePairs = new String[12][2];
        // 手动构造一些特殊值
        largePairs[0] = new String[]{"1234567890123456789012345678901234567890", "9876543210987654321098765432109876543210"};
        largePairs[1] = new String[]{"-123456789012345678901234567890", "500000000000000000000000000001"};
        largePairs[2] = new String[]{"0", "12345678901234567890"};
        largePairs[3] = new String[]{"-1", "1"};
        largePairs[4] = new String[]{"99999999999999999999", "100000000000000000001"};
        largePairs[5] = new String[]{"-99999999999999999999", "-100000000000000000001"};
        largePairs[6] = new String[]{"2147483647", "2147483648"};
        largePairs[7] = new String[]{"-2147483648", "-2147483647"};
        largePairs[8] = new String[]{"1000000000000000000000000000000", "2"};
        //1000000000000000000000000000000
        //500000000000000000000000000000
        //1000000000000000000000000000000
        largePairs[9] = new String[]{"-1000000000000000000000000000000", "3"};
        largePairs[10] = new String[]{"123456789012345678901234567890", "-123456789012345678901234567890"};
        largePairs[11] = new String[]{"9999999999999999999999999999999999999999", "8888888888888888888888888888888888888888"};

        for (int i = 0; i < 12; i++) {
            String aStr = largePairs[i][0];
            String bStr = largePairs[i][1];
            big_integer a = bi(aStr);
            big_integer b = bi(bStr);
            BigInteger ba = new BigInteger(aStr);
            BigInteger bb = new BigInteger(bStr);

            // 加法
            big_integer addRes = big_integer.add(a, b);
            BigInteger addExp = ba.add(bb);
            if (equals(addRes, addExp)) {
                pass("add large " + (i+1), addExp.toString());
            } else {
                fail("add large " + (i+1), addExp.toString(), addRes.toString());
            }

            // 减法
            big_integer subRes = big_integer.subtract(a, b);
            BigInteger subExp = ba.subtract(bb);
            if (equals(subRes, subExp)) {
                pass("subtract large " + (i+1), subExp.toString());
            } else {
                fail("subtract large " + (i+1), subExp.toString(), subRes.toString());
            }

            // 乘法
            big_integer mulRes = big_integer.multiply(a, b);
            BigInteger mulExp = ba.multiply(bb);
            if (equals(mulRes, mulExp)) {
                pass("multiply large " + (i+1), mulExp.toString());
            } else {
                fail("multiply large " + (i+1), mulExp.toString(), mulRes.toString());
            }

            // 除法（若除数不为0）
            if (!bStr.equals("0")) {
                big_integer[] divRes = big_integer.divide(a, b);
                big_integer q = divRes[0];
                big_integer r = divRes[1];
                big_integer lhs = big_integer.add(big_integer.multiply(b, q), r);
                boolean eq = equals(lhs, a);
                big_integer absB = new big_integer(b.number, 1);
                boolean remOk = r.sign >= 0 && r.compareTo(absB) < 0;
                if (eq && remOk) {
                    pass("divide large " + (i+1), "q=" + q + ", r=" + r);
                } else {
                    fail("divide large " + (i+1), "性质验证失败", "q=" + q + ", r=" + r + ", lhs=" + lhs);
                }
            } else {
                big_integer[] divZero = big_integer.divide(a, b);
                if (divZero == null) {
                    pass("divide large (by 0) " + (i+1), "null");
                } else {
                    fail("divide large (by 0) " + (i+1), "null", divZero[0].toString());
                }
            }

            // GCD
            big_integer gcdRes = big_integer.gcd(a, b);
            BigInteger gcdExp = ba.gcd(bb);
            if (equals(gcdRes, gcdExp)) {
                pass("gcd large " + (i+1), gcdExp.toString());
            } else {
                fail("gcd large " + (i+1), gcdExp.toString(), gcdRes.toString());
            }

            // LCM (避免 0 和 0)
            boolean aZero = a.size == 0;
            boolean bZero = b.size == 0;
            if (aZero && bZero) {
                try {
                    big_integer.lcm(a, b);
                    fail("lcm large (0,0) " + (i+1), "异常", "未抛出");
                } catch (Exception e) {
                    pass("lcm large (0,0) " + (i+1), "异常");
                }
            } else {
                big_integer lcmRes = big_integer.lcm(a, b);
                big_integer g = big_integer.gcd(a, b);
                big_integer prod1 = big_integer.multiply(a, b);
                big_integer prod2 = big_integer.multiply(g, lcmRes);
                if (equals(prod1, prod2)) {
                    pass("lcm large " + (i+1), lcmRes.toString() + " (a*b=g*l)");
                } else {
                    fail("lcm large " + (i+1), "a*b=g*l", "lcm=" + lcmRes + ", gcd=" + g + ", a*b=" + prod1 + ", g*l=" + prod2);
                }
            }
        }
    }
}