import aio.mathematics.*;
/**
 * 高精度有理数类 {@link BigRational} 的测试类。
 * 包含全面的功能测试、边界条件测试以及压力测试。
 * 通过 {@code main()} 调用所有测试用例，并输出 ANSI 彩色结果。
 */
public class BigRationalTest {

    // ANSI 颜色
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String RESET = "\033[0m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== 开始 BigRational 测试 ===");
        testConstructionFromDecimalString();
        testConstructionFromFractionString();
        testConstructionFromInt();
        testConstructionFromBigInteger();
        testReduce();
        testToString();
        testAddition();
        testSubtraction();
        testMultiplication();
        testDivision();
        testPower();
        testStress();
        System.out.println("=== 测试结束 ===");
        System.out.println("通过: " + passed + "  失败: " + failed);
        if (failed == 0) {
            System.out.println(GREEN + "所有测试通过！" + RESET);
        } else {
            System.out.println(RED + "存在失败用例，请检查。" + RESET);
        }
    }

    // ---------- 断言辅助 ----------
    private static void check(String testName, Object expected, Object actual) {
        boolean ok;
        if (expected == null && actual == null) {
            ok = true;
        } else if (expected == null || actual == null) {
            ok = false;
        } else {
            ok = expected.equals(actual);
        }

        if (ok) {
            passed++;
            System.out.println(GREEN + "[AC] " + testName + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + testName +
                    " 期望: " + expected + " 实际: " + actual + RESET);
        }
    }

    private static void checkTrue(String testName, boolean condition, String expectedDesc, String actualDesc) {
        if (condition) {
            passed++;
            System.out.println(GREEN + "[AC] " + testName + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + testName +
                    " 期望: " + expectedDesc + " 实际: " + actualDesc + RESET);
        }
    }

    // ---------- 测试方法 ----------
    public static void testConstructionFromDecimalString() {
        BigRational r1 = new BigRational("0.5");
        check("构造小数0.5", "1/2  0.5", r1.toString());

        BigRational r2 = new BigRational("-3.14");
        check("构造小数-3.14", "-157/50  -3.14", r2.toString());

        BigRational r3 = new BigRational("0.0001");
        check("构造小数0.0001", "1/10000  0.0001", r3.toString());

        BigRational r4 = new BigRational("123");
        check("构造整数123", "123", r4.toString());

        BigRational r5 = new BigRational("-0");
        check("构造-0应为0", "0", r5.toString());

        BigRational r6 = new BigRational("0.(3)");
        // mode=0 默认格式 "1/3  0.(3)"
        r6.mode = 1; // 仅小数
        check("构造循环小数0.(3)", "0.(3)", r6.toString());

        BigRational r7 = new BigRational("1.2(34)");
        r7.mode = 1;
        check("构造循环小数1.2(34)", "1.2(34)", r7.toString());

        BigRational r8 = new BigRational("-0.(142857)");
        r8.mode = 1;
        check("构造循环小数-0.(142857)", "-0.(142857)", r8.toString());
    }

    public static void testConstructionFromFractionString() {
        BigRational r1 = new BigRational("3", "4");
        r1.mode = -1; // 分数模式
        check("构造分数3/4", "3/4", r1.toString());

        BigRational r2 = new BigRational("-5", "7");
        r2.mode = -1;
        check("构造分数-5/7", "-5/7", r2.toString());

        BigRational r3 = new BigRational("6", "3");
        // 约分为整数2
        check("构造分数6/3应约分为2", "2", r3.toString());

        BigRational r4 = new BigRational("0", "5");
        check("构造分数0/5应为0", "0", r4.toString());

        BigRational r5 = new BigRational("-4", "-8");
        check("构造分数-4/-8应为正1/2", "1/2  0.5", r5.toString()); // mode=0 默认
    }

    public static void testConstructionFromInt() {
        BigRational r1 = new BigRational(1, 2);
        r1.mode = -1;
        check("构造int分数1/2", "1/2", r1.toString());

        BigRational r2 = new BigRational(4, 2);
        check("构造int分数4/2应约分为整数2", "2", r2.toString());

        BigRational r3 = new BigRational(0, 3);
        check("构造int分数0/3应为0", "0", r3.toString());

        BigRational r4 = new BigRational(-3, 4);
        r4.mode = -1;
        check("构造int分数-3/4", "-3/4", r4.toString());

        BigRational r5 = new BigRational(3, -4);
        r5.mode = -1;
        check("构造int分数3/-4应为-3/4", "-3/4", r5.toString());
    }

    public static void testConstructionFromBigInteger() {
        BigInteger num = new BigInteger("12345678901234567890");
        BigInteger den = new BigInteger("10000000000000000000");
        BigRational r1 = new BigRational(num, den);
        r1.mode = -1;
        // 约分后：分子分母同除以10？ 1234567890123456789/1000000000000000000
        check("构造大整数分数", "1234567890123456789/1000000000000000000", r1.toString());

        BigRational r2 = new BigRational(num, null);
        check("构造大整数（分母null）", "12345678901234567890", r2.toString());
    }

    public static void testReduce() {
        BigRational r1 = new BigRational("4", "8");
        r1.mode = -1;
        check("约分4/8", "1/2", r1.toString());

        BigRational r2 = new BigRational("15", "25");
        r2.mode = -1;
        check("约分15/25", "3/5", r2.toString());

        BigRational r3 = new BigRational("7", "1");
        check("约分7/1应为整数7", "7", r3.toString());
    }

    public static void testToString() {
        BigRational r = new BigRational(1, 3);
        r.mode = 0;
        check("mode=0 1/3", "1/3  0.(3)", r.toString());

        r.mode = 1;
        check("mode=1 1/3", "0.(3)", r.toString());

        r.mode = -1;
        check("mode=-1 1/3", "1/3", r.toString());

        BigRational r2 = new BigRational(1, 2);
        r2.mode = 0;
        check("mode=0 1/2", "1/2  0.5", r2.toString());

        r2.mode = 1;
        check("mode=1 1/2", "0.5", r2.toString());

        // 循环节大于1位
        BigRational r3 = new BigRational("1.24(56)");
        r3.mode = 1;
        check("构造并显示1.24(56)", "1.24(56)", r3.toString());
    }

    public static void testAddition() {
        BigRational a = new BigRational(1, 2);
        BigRational b = new BigRational(1, 3);
        BigRational sum = BigRational.add(a, b);
        sum.mode = -1;
        check("1/2 + 1/3", "5/6", sum.toString());

        BigRational c = new BigRational("-1", "4");
        BigRational d = new BigRational("1", "4");
        sum = BigRational.add(c, d);
        check("-1/4 + 1/4 = 0", "0", sum.toString());

        BigRational e = new BigRational("2"); // 整数
        BigRational f = new BigRational(3, 4);
        sum = BigRational.add(e, f);
        sum.mode = -1;
        check("2 + 3/4", "11/4", sum.toString());

        BigRational g = new BigRational(0, 1);
        sum = BigRational.add(g, new BigRational("9999999999999999999"));
        check("0 + 大数", "9999999999999999999", sum.toString());
    }

    public static void testSubtraction() {
        BigRational a = new BigRational(5, 6);
        BigRational b = new BigRational(1, 3);
        BigRational diff = BigRational.subtract(a, b);
        diff.mode = -1;
        check("5/6 - 1/3", "1/2", diff.toString());

        BigRational c = new BigRational(1, 4);
        BigRational d = new BigRational(3, 4);
        diff = BigRational.subtract(c, d);
        diff.mode = -1;
        check("1/4 - 3/4", "-1/2", diff.toString());

        BigRational e = new BigRational("2");
        diff = BigRational.subtract(e, new BigRational(1, 2));
        diff.mode = -1;
        check("2 - 1/2", "3/2", diff.toString());
    }

    public static void testMultiplication() {
        BigRational a = new BigRational(2, 3);
        BigRational b = new BigRational(3, 4);
        BigRational prod = BigRational.multiply(a, b);
        prod.mode = -1;
        check("2/3 * 3/4", "1/2", prod.toString());

        BigRational c = new BigRational(-1, 2);
        BigRational d = new BigRational(1, 2);
        prod = BigRational.multiply(c, d);
        prod.mode = -1;
        check("-1/2 * 1/2", "-1/4", prod.toString());

        BigRational e = new BigRational(0, 1);
        prod = BigRational.multiply(e, new BigRational("123456789"));
        check("0 * 大数 = 0", "0", prod.toString());
    }

    public static void testDivision() {
        BigRational a = new BigRational(3, 4);
        BigRational b = new BigRational(2, 3);
        BigRational quot = BigRational.divide(a, b);
        quot.mode = -1;
        check("(3/4) / (2/3)", "9/8", quot.toString());

        BigRational c = new BigRational("5");
        BigRational d = new BigRational(2, 3);
        quot = BigRational.divide(c, d);
        quot.mode = -1;
        check("5 / (2/3)", "15/2", quot.toString());

        BigRational zero = new BigRational(0, 1);
        BigRational byZero = BigRational.divide(a, zero);
        check("除以0应返回null", null, byZero);

        BigRational zeroDividend = BigRational.divide(zero, a);
        check("0 / 非零 = 0", "0", zeroDividend.toString());
    }

    public static void testPower() {
        BigRational base = new BigRational(2, 3);
        BigRational pow = BigRational.power(base, 3);
        pow.mode = -1;
        check("(2/3)^3", "8/27", pow.toString());

        BigRational pow0 = BigRational.power(base, 0);
        pow0.mode = -1;
        check("(2/3)^0", "1", pow0.toString());

        BigRational powNeg = BigRational.power(base, -2);
        powNeg.mode = -1;
        check("(2/3)^(-2) = 9/4", "9/4", powNeg.toString());

        BigRational one = new BigRational("1");
        BigRational powOneNeg = BigRational.power(one, -3);
        check("1^(-3) = 1", "1", powOneNeg.toString());

        // 0的负指数应为 null
        BigRational zero = new BigRational(0, 1);
        BigRational zeroPowNeg = null;
        try {
            zeroPowNeg = BigRational.power(zero, -1);
        } catch (Exception ignored) {
        }
        check("0^(-1) 应返回null", null, zeroPowNeg);

        BigRational zeroPowPos = BigRational.power(zero, 5);
        check("0^5 = 0", "0", zeroPowPos.toString());

        // 负数的幂
        BigRational negBase = new BigRational(-1, 2);
        BigRational powNegEven = BigRational.power(negBase, 2);
        powNegEven.mode = -1;
        check("(-1/2)^2", "1/4", powNegEven.toString());

        BigRational powNegOdd = BigRational.power(negBase, 3);
        powNegOdd.mode = -1;
        check("(-1/2)^3", "-1/8", powNegOdd.toString());
    }

    public static void testStress() {
        // 大数构造与运算
        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < 100; i++) sb.append("0"); // 10^100
        String bigStr = sb.toString();
        BigRational big = new BigRational(bigStr, "3");
        big.mode = 1;
        // 10^100 / 3 转换为小数应无异常且长度合理
        try {
            String s = big.toString();
            checkTrue("压力：构造10^100/3转小数未崩溃 "+s,
                    s.length() > 10, "长度>10", "长度=" + s.length());
        } catch (Exception e) {
            checkTrue("压力：构造10^100/3转小数未崩溃", false, "无异常", "异常: " + e);
        }

        // 连续乘法压力
        BigRational factor = new BigRational(1, 2);
        BigRational result = new BigRational("1");
        for (int i = 0; i < 50; i++) {
            result = BigRational.multiply(result, factor);
        }
        // 理论值 (1/2)^50
        BigRational expected = BigRational.power(factor, 50);
        result.mode = -1;
        expected.mode = -1;
        check("压力：连续乘(1/2)^50等于power结果 "+result.toString(),
                expected.toString(), result.toString());

        // 大数加减
        BigRational huge1 = new BigRational(
                new BigInteger("999999999999999999999999999999"),
                new BigInteger("1000000000000000000000000000000"));
        BigRational huge2 = new BigRational(
                new BigInteger("1"),
                new BigInteger("1000000000000000000000000000000"));
        BigRational sumHuge = BigRational.add(huge1, huge2);
        sumHuge.mode = -1;
        // 和为 100...0 / 100...0 = 1
        check("压力：大数分数相加约分为1", "1", sumHuge.toString());

        // 大数循环小数
        BigRational oneSeventh = new BigRational("1", "7");
        oneSeventh.mode = 1;
        check("压力：1/7循环小数 "+oneSeventh.toString(), "0.(142857)", oneSeventh.toString());
    }
}