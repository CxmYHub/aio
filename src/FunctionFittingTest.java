import aio.mathematics.Function;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * function 类函数拟合精度测试。
 * 仅测试 function(boolean, double[], double[]) 构造器，并传入 true 以启用多线程拟合。
 * 包含 100 个初等函数测试用例（不含三角函数与反三角函数）。
 */
public class FunctionFittingTest {
    private static final int TEST_COUNT = 100;
    private static final int SAMPLE_COUNT = 30;
    private static final double NOISE_RATE = 0.0;
    private static final Random RNG = new Random(20240517L);

    @FunctionalInterface
    private interface RealFunction {
        double apply(double x);
    }

    private static final class TestCase {
        final String name;
        final RealFunction f;
        final double xmin;
        final double xmax;

        TestCase(String name, RealFunction f, double xmin, double xmax) {
            this.name = name;
            this.f = f;
            this.xmin = xmin;
            this.xmax = xmax;
        }
    }

    public static void main(String[] args) {
        // 限制 function 内部并发线程数，避免默认 20 线程在测试时资源占用过高；仍然使用多线程。
        Function.concurrentCount = Math.max(2, Math.min(16, Runtime.getRuntime().availableProcessors()));
        Function.populationSize=2000;
        Function.maxTreeDepth=5;

        List<TestCase> cases = buildTestCases();

        int n = 1;
        for (TestCase tc : cases) {
            double[] x = new double[SAMPLE_COUNT];
            double[] y = new double[SAMPLE_COUNT];
            generateData(tc, x, y);

            long start = System.nanoTime();

            Function fit;
            // PrintStream originalOut = System.out;
            // ByteArrayOutputStream silentBuffer = new ByteArrayOutputStream();
            // System.setOut(new PrintStream(silentBuffer));
            // try
            // {
                // 仅测试 function(boolean, double[], double[]) 构造器，并传入 true 以启用多线程。
                fit = new Function(true, x, y);
            // }
            // finally
            // {
            //     System.setOut(originalOut);
            // }

            long timeMs = (System.nanoTime() - start) / 1000000L;
            double rss = residualSumOfSquares(fit, x, y);

            System.out.printf(
                "测试用例%d/%d\t\t残差平方和：%.6g\t\t耗时：%dms\t\t结果：\n%s%n",
                n,
                TEST_COUNT,
                rss,
                timeMs,
                fit.toString()
            );

            n++;
        }
    }

    private static List<TestCase> buildTestCases() {
        List<TestCase> list = new ArrayList<>(TEST_COUNT);

        for (int i = 0; i < TEST_COUNT; i++) {
            int type = i % 10;

            switch (type) {
                case 0 -> {
                    double a = rand(-3.0, 3.0);
                    double b = rand(-5.0, 5.0);
                    list.add(new TestCase("线性函数", x -> a * x + b, -5.0, 5.0));
                }
                case 1 -> {
                    double a = rand(-2.0, 2.0);
                    double b = rand(-4.0, 4.0);
                    double c = rand(-5.0, 5.0);
                    list.add(new TestCase("二次函数", x -> a * x * x + b * x + c, -4.0, 4.0));
                }
                case 2 -> {
                    double a = rand(-1.0, 1.0);
                    double b = rand(-2.0, 2.0);
                    double c = rand(-4.0, 4.0);
                    double d = rand(-5.0, 5.0);
                    list.add(new TestCase("三次函数", x -> a * x * x * x + b * x * x + c * x + d, -3.0, 3.0));
                }
                case 3 -> {
                    double a = rand(-0.5, 0.5);
                    double b = rand(-1.0, 1.0);
                    double c = rand(-2.0, 2.0);
                    double d = rand(-4.0, 4.0);
                    double e = rand(-5.0, 5.0);
                    list.add(new TestCase(
                        "四次函数",
                        x -> a * x * x * x * x + b * x * x * x + c * x * x + d * x + e,
                        -2.5,
                        2.5
                    ));
                }
                case 4 -> {
                    double a = rand(0.3, 3.0);
                    double p = rand(0.5, 4.0);
                    double b = rand(-3.0, 3.0);
                    list.add(new TestCase("幂函数", x -> a * Math.pow(x, p) + b, 0.1, 5.0));
                }
                case 5 -> {
                    double a = rand(0.3, 3.0);
                    double b = rand(0.1, 1.5);
                    double c = rand(-3.0, 3.0);
                    double bb = RNG.nextBoolean() ? -b : b;
                    list.add(new TestCase("指数函数", x -> a * Math.exp(bb * x) + c, -2.0, 2.0));
                }
                case 6 -> {
                    double a = rand(0.3, 3.0);
                    double c = rand(-3.0, 3.0);
                    double base = new double[]{2.0, Math.E, 10.0, 0.5}[RNG.nextInt(4)];
                    double logBase = Math.log(base);
                    list.add(new TestCase("对数函数", x -> a * Math.log(x) / logBase + c, 0.1, 10.0));
                }
                case 7 -> {
                    double a = rand(-3.0, 3.0);
                    double b = rand(-4.0, 4.0);
                    double c = rand(0.5, 4.0);
                    double d = rand(-3.0, 3.0);
                    list.add(new TestCase("有理函数", x -> (a * x + b) / (x * x + c) + d, -4.0, 4.0));
                }
                case 8 -> {
                    double a = rand(0.3, 2.5);
                    double b = rand(0.5, 3.0);
                    double c = rand(-2.0, 2.0);
                    double d = rand(-2.0, 2.0);
                    list.add(new TestCase(
                        "复合函数",
                        x -> a * Math.pow(x, b) + c * Math.log(x) + d,
                        0.1,
                        5.0
                    ));
                }
                case 9 -> {
                    double a = rand(0.3, 2.0);
                    double b = rand(0.1, 1.2);
                    double c = rand(0.3, 2.0);
                    double d = rand(0.5, 2.0);
                    double e = rand(-2.0, 2.0);
                    list.add(new TestCase(
                        "混合初等函数",
                        x -> a * Math.exp(b * x) + c * Math.log(x + d) + e,
                        0.1,
                        3.0
                    ));
                }
            }
        }

        return list;
    }

    private static void generateData(TestCase tc, double[] x, double[] y) {
        int n = x.length;

        for (int i = 0; i < n; i++) {
            double t = n == 1 ? 0.0 : (double) i / (n - 1);
            x[i] = tc.xmin + t * (tc.xmax - tc.xmin);
        }

        double[] clean = new double[n];
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < n; i++) {
            double v = tc.f.apply(x[i]);
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                v = 0.0;
            }
            clean[i] = v;
            if (v < minY) {
                minY = v;
            }
            if (v > maxY) {
                maxY = v;
            }
        }

        double range = maxY - minY;
        if (range <= 1e-12) {
            range = 1.0;
        }

        double noise = NOISE_RATE * range;

        for (int i = 0; i < n; i++) {
            y[i] = clean[i] + RNG.nextGaussian() * noise;
        }
    }

    private static double residualSumOfSquares(Function fit, double[] x, double[] y) {
        double rss = 0.0;

        for (int i = 0; i < x.length; i++) {
            double pred = fit.calculate(x[i]);
            double diff = pred - y[i];

            if (Double.isNaN(diff) || Double.isInfinite(diff)) {
                return Double.POSITIVE_INFINITY;
            }

            rss += diff * diff;
        }

        return rss;
    }

    private static double rand(double min, double max) {
        return min + RNG.nextDouble() * (max - min);
    }
}