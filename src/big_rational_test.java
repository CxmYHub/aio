import aio.mathematics.*;
/**
 * 高精度有理数类 {@link big_rational} 的测试类。
 * 包含全面的功能测试、边界条件测试以及压力测试。
 * 通过 {@code main()} 调用所有测试用例，并输出 ANSI 彩色结果。
 */
public class big_rational_test {

    // ANSI 颜色
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String RESET = "\033[0m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== 开始 big_rational 测试 ===");
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
        big_rational r1 = new big_rational("0.5");
        check("构造小数0.5", "1/2  0.5", r1.toString());

        big_rational r2 = new big_rational("-3.14");
        check("构造小数-3.14", "-157/50  -3.14", r2.toString());

        big_rational r3 = new big_rational("0.0001");
        check("构造小数0.0001", "1/10000  0.0001", r3.toString());

        big_rational r4 = new big_rational("123");
        check("构造整数123", "123", r4.toString());

        big_rational r5 = new big_rational("-0");
        check("构造-0应为0", "0", r5.toString());

        big_rational r6 = new big_rational("0.(3)");
        // mode=0 默认格式 "1/3  0.(3)"
        r6.mode = 1; // 仅小数
        check("构造循环小数0.(3)", "0.(3)", r6.toString());

        big_rational r7 = new big_rational("1.2(34)");
        r7.mode = 1;
        check("构造循环小数1.2(34)", "1.2(34)", r7.toString());

        big_rational r8 = new big_rational("-0.(142857)");
        r8.mode = 1;
        check("构造循环小数-0.(142857)", "-0.(142857)", r8.toString());
    }

    public static void testConstructionFromFractionString() {
        big_rational r1 = new big_rational("3", "4");
        r1.mode = -1; // 分数模式
        check("构造分数3/4", "3/4", r1.toString());

        big_rational r2 = new big_rational("-5", "7");
        r2.mode = -1;
        check("构造分数-5/7", "-5/7", r2.toString());

        big_rational r3 = new big_rational("6", "3");
        // 约分为整数2
        check("构造分数6/3应约分为2", "2", r3.toString());

        big_rational r4 = new big_rational("0", "5");
        check("构造分数0/5应为0", "0", r4.toString());

        big_rational r5 = new big_rational("-4", "-8");
        check("构造分数-4/-8应为正1/2", "1/2  0.5", r5.toString()); // mode=0 默认
    }

    public static void testConstructionFromInt() {
        big_rational r1 = new big_rational(1, 2);
        r1.mode = -1;
        check("构造int分数1/2", "1/2", r1.toString());

        big_rational r2 = new big_rational(4, 2);
        check("构造int分数4/2应约分为整数2", "2", r2.toString());

        big_rational r3 = new big_rational(0, 3);
        check("构造int分数0/3应为0", "0", r3.toString());

        big_rational r4 = new big_rational(-3, 4);
        r4.mode = -1;
        check("构造int分数-3/4", "-3/4", r4.toString());

        big_rational r5 = new big_rational(3, -4);
        r5.mode = -1;
        check("构造int分数3/-4应为-3/4", "-3/4", r5.toString());
    }

    public static void testConstructionFromBigInteger() {
        big_integer num = new big_integer("12345678901234567890");
        big_integer den = new big_integer("10000000000000000000");
        big_rational r1 = new big_rational(num, den);
        r1.mode = -1;
        // 约分后：分子分母同除以10？ 1234567890123456789/1000000000000000000
        check("构造大整数分数", "1234567890123456789/1000000000000000000", r1.toString());

        big_rational r2 = new big_rational(num, null);
        check("构造大整数（分母null）", "12345678901234567890", r2.toString());
    }

    public static void testReduce() {
        big_rational r1 = new big_rational("4", "8");
        r1.mode = -1;
        check("约分4/8", "1/2", r1.toString());

        big_rational r2 = new big_rational("15", "25");
        r2.mode = -1;
        check("约分15/25", "3/5", r2.toString());

        big_rational r3 = new big_rational("7", "1");
        check("约分7/1应为整数7", "7", r3.toString());
    }

    public static void testToString() {
        big_rational r = new big_rational(1, 3);
        r.mode = 0;
        check("mode=0 1/3", "1/3  0.(3)", r.toString());

        r.mode = 1;
        check("mode=1 1/3", "0.(3)", r.toString());

        r.mode = -1;
        check("mode=-1 1/3", "1/3", r.toString());

        big_rational r2 = new big_rational(1, 2);
        r2.mode = 0;
        check("mode=0 1/2", "1/2  0.5", r2.toString());

        r2.mode = 1;
        check("mode=1 1/2", "0.5", r2.toString());

        // 循环节大于1位
        big_rational r3 = new big_rational("1.24(56)");
        r3.mode = 1;
        check("构造并显示1.24(56)", "1.24(56)", r3.toString());
    }

    public static void testAddition() {
        big_rational a = new big_rational(1, 2);
        big_rational b = new big_rational(1, 3);
        big_rational sum = big_rational.add(a, b);
        sum.mode = -1;
        check("1/2 + 1/3", "5/6", sum.toString());

        big_rational c = new big_rational("-1", "4");
        big_rational d = new big_rational("1", "4");
        sum = big_rational.add(c, d);
        check("-1/4 + 1/4 = 0", "0", sum.toString());

        big_rational e = new big_rational("2"); // 整数
        big_rational f = new big_rational(3, 4);
        sum = big_rational.add(e, f);
        sum.mode = -1;
        check("2 + 3/4", "11/4", sum.toString());

        big_rational g = new big_rational(0, 1);
        sum = big_rational.add(g, new big_rational("9999999999999999999"));
        check("0 + 大数", "9999999999999999999", sum.toString());
    }

    public static void testSubtraction() {
        big_rational a = new big_rational(5, 6);
        big_rational b = new big_rational(1, 3);
        big_rational diff = big_rational.subtract(a, b);
        diff.mode = -1;
        check("5/6 - 1/3", "1/2", diff.toString());

        big_rational c = new big_rational(1, 4);
        big_rational d = new big_rational(3, 4);
        diff = big_rational.subtract(c, d);
        diff.mode = -1;
        check("1/4 - 3/4", "-1/2", diff.toString());

        big_rational e = new big_rational("2");
        diff = big_rational.subtract(e, new big_rational(1, 2));
        diff.mode = -1;
        check("2 - 1/2", "3/2", diff.toString());
    }

    public static void testMultiplication() {
        big_rational a = new big_rational(2, 3);
        big_rational b = new big_rational(3, 4);
        big_rational prod = big_rational.multiply(a, b);
        prod.mode = -1;
        check("2/3 * 3/4", "1/2", prod.toString());

        big_rational c = new big_rational(-1, 2);
        big_rational d = new big_rational(1, 2);
        prod = big_rational.multiply(c, d);
        prod.mode = -1;
        check("-1/2 * 1/2", "-1/4", prod.toString());

        big_rational e = new big_rational(0, 1);
        prod = big_rational.multiply(e, new big_rational("123456789"));
        check("0 * 大数 = 0", "0", prod.toString());
    }

    public static void testDivision() {
        big_rational a = new big_rational(3, 4);
        big_rational b = new big_rational(2, 3);
        big_rational quot = big_rational.divide(a, b);
        quot.mode = -1;
        check("(3/4) / (2/3)", "9/8", quot.toString());

        big_rational c = new big_rational("5");
        big_rational d = new big_rational(2, 3);
        quot = big_rational.divide(c, d);
        quot.mode = -1;
        check("5 / (2/3)", "15/2", quot.toString());

        big_rational zero = new big_rational(0, 1);
        big_rational byZero = big_rational.divide(a, zero);
        check("除以0应返回null", null, byZero);

        big_rational zeroDividend = big_rational.divide(zero, a);
        check("0 / 非零 = 0", "0", zeroDividend.toString());
    }

    public static void testPower() {
        big_rational base = new big_rational(2, 3);
        big_rational pow = big_rational.power(base, 3);
        pow.mode = -1;
        check("(2/3)^3", "8/27", pow.toString());

        big_rational pow0 = big_rational.power(base, 0);
        pow0.mode = -1;
        check("(2/3)^0", "1", pow0.toString());

        big_rational powNeg = big_rational.power(base, -2);
        powNeg.mode = -1;
        check("(2/3)^(-2) = 9/4", "9/4", powNeg.toString());

        big_rational one = new big_rational("1");
        big_rational powOneNeg = big_rational.power(one, -3);
        check("1^(-3) = 1", "1", powOneNeg.toString());

        // 0的负指数应为 null
        big_rational zero = new big_rational(0, 1);
        big_rational zeroPowNeg = null;
        try {
            zeroPowNeg = big_rational.power(zero, -1);
        } catch (Exception ignored) {
        }
        check("0^(-1) 应返回null", null, zeroPowNeg);

        big_rational zeroPowPos = big_rational.power(zero, 5);
        check("0^5 = 0", "0", zeroPowPos.toString());

        // 负数的幂
        big_rational negBase = new big_rational(-1, 2);
        big_rational powNegEven = big_rational.power(negBase, 2);
        powNegEven.mode = -1;
        check("(-1/2)^2", "1/4", powNegEven.toString());

        big_rational powNegOdd = big_rational.power(negBase, 3);
        powNegOdd.mode = -1;
        check("(-1/2)^3", "-1/8", powNegOdd.toString());
    }

    public static void testStress() {
        // 大数构造与运算
        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < 100; i++) sb.append("0"); // 10^100
        String bigStr = sb.toString();
        big_rational big = new big_rational(bigStr, "3");
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
        big_rational factor = new big_rational(1, 2);
        big_rational result = new big_rational("1");
        for (int i = 0; i < 50; i++) {
            result = big_rational.multiply(result, factor);
        }
        // 理论值 (1/2)^50
        big_rational expected = big_rational.power(factor, 50);
        result.mode = -1;
        expected.mode = -1;
        check("压力：连续乘(1/2)^50等于power结果 "+result.toString(),
                expected.toString(), result.toString());

        // 大数加减
        big_rational huge1 = new big_rational(
                new big_integer("999999999999999999999999999999"),
                new big_integer("1000000000000000000000000000000"));
        big_rational huge2 = new big_rational(
                new big_integer("1"),
                new big_integer("1000000000000000000000000000000"));
        big_rational sumHuge = big_rational.add(huge1, huge2);
        sumHuge.mode = -1;
        // 和为 100...0 / 100...0 = 1
        check("压力：大数分数相加约分为1", "1", sumHuge.toString());

        // 大数循环小数
        big_rational oneSeventh = new big_rational("1", "7");
        oneSeventh.mode = 1;
        check("压力：1/7循环小数 "+oneSeventh.toString(), "0.(142857)", oneSeventh.toString());
    }
}