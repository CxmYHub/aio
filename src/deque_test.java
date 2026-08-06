import java.util.*;
import aio.data_structure.deque;
/**
 * deque 类的全面测试，包含边界条件与压力测试。
 * 通过 main() 方法依次执行所有测试用例，输出带 ANSI 颜色的结果。
 */
public class deque_test {

    // ANSI 颜色码
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testNewDequeDefaults();
        testEmptyDequeOperations();
        testInputBackSingle();
        testInputBackMultiple();
        testInputFrontSingle();
        testInputFrontMultiple();
        testInputMoreBack();
        testInputMoreFront();
        testGetBackBoundary();
        testOutputBack();
        testOutputFrontFromBackInput();
        testMixedInputFrontBackOutput();
        testFullQueueExpandBack();
        testFullQueueExpandFront();
        testWrapAround();
        testLargeVolumeStress();
        testGetBackWhenRearZero(); // 潜在数组越界的边界
        testRandomMixedOperations();

        System.out.println("========================================");
        System.out.println("总通过: " + passed + "  总失败: " + failed);
    }

    // ---------- 辅助方法 ----------

    private static void checkInt(String testName, int expected, int actual) {
        if (expected == actual) {
            passed++;
            System.out.println(GREEN + "[AC] " + testName + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + testName + " 期望 " + expected + " 实际 " + actual + RESET);
        }
    }

    private static void checkBool(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            passed++;
            System.out.println(GREEN + "[AC] " + testName + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + testName + " 期望 " + expected + " 实际 " + actual + RESET);
        }
    }

    private static void checkMinValue(String testName, int actual) {
        if (actual == Integer.MIN_VALUE) {
            passed++;
            System.out.println(GREEN + "[AC] " + testName + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] " + testName + " 期望 " + Integer.MIN_VALUE + " 实际 " + actual + RESET);
        }
    }

    /**
     * 用后端插入填充指定数量的元素（从队尾入队）。
     * @return 填充后的双端队列
     */
    private static deque fillViaBack(int... elements) {
        deque dq = new deque();
        for (int e : elements) {
            dq.input_back(e);
        }
        return dq;
    }

    /**
     * 用前端插入填充指定数量的元素（从队头入队）。
     */
    private static deque fillViaFront(int... elements) {
        deque dq = new deque();
        for (int e : elements) {
            dq.input_front(e);
        }
        return dq;
    }

    // ---------- 测试用例 ----------

    /** 默认构造和指定容量构造 */
    public static void testNewDequeDefaults() {
        deque dq1 = new deque();
        checkInt("默认容量应为256", 256, dq1.capacity);
        checkBool("初始为空", true, dq1.is_empty());
        checkBool("初始未满", false, dq1.is_full());
        checkInt("初始元素数量为0", 0, dq1.element_count());
        checkInt("初始空余数量为256", 256, dq1.empty_count());

        deque dq2 = new deque(10);
        checkInt("指定容量10", 10, dq2.capacity);
        checkInt("指定容量后元素数量为0", 0, dq2.element_count());
    }

    /** 空队列上的只读操作 */
    public static void testEmptyDequeOperations() {
        deque dq = new deque();
        checkMinValue("空队列 get_front 应返回 MIN_VALUE", dq.get_front());
        checkMinValue("空队列 get_back 应返回 MIN_VALUE", dq.get_back());
        checkMinValue("空队列 output_front 应返回 MIN_VALUE", dq.output_front());
        checkMinValue("空队列 output_back 应返回 MIN_VALUE", dq.output_back());
        checkBool("空队列 is_empty 应为 true", true, dq.is_empty());
        checkBool("空队列 is_full 应为 false", false, dq.is_full());
    }

    /** 后端插入一个元素 */
    public static void testInputBackSingle() {
        deque dq = new deque();
        int cnt = dq.input_back(42);
        checkInt("input_back 返回数量 1", 1, cnt);
        checkInt("element_count 为 1", 1, dq.element_count());
        checkBool("非空", false, dq.is_empty());
        checkInt("get_front 为 42", 42, dq.get_front());
        checkInt("get_back 为 42", 42, dq.get_back());
    }

    /** 后端插入多个元素（验证顺序） */
    public static void testInputBackMultiple() {
        deque dq = new deque();
        dq.input_back(10);
        dq.input_back(20);
        dq.input_back(30);
        checkInt("三次后端插入后 element_count", 3, dq.element_count());
        checkInt("get_front 应为第一个插入的10", 10, dq.get_front());
        checkInt("get_back 应为最后插入的30", 30, dq.get_back());
        // 出队顺序应为 10,20,30
        checkInt("output_front 第1个应为10", 10, dq.output_front());
        checkInt("output_front 第2个应为20", 20, dq.output_front());
        checkInt("output_front 第3个应为30", 30, dq.output_front());
        checkBool("全部出队后为空", true, dq.is_empty());
    }

    /** 前端插入一个元素（空队列） */
    public static void testInputFrontSingle() {
        deque dq = new deque();
        int cnt = dq.input_front(7);
        checkInt("前端插入空队列返回数量1", 1, cnt);
        checkInt("get_front 应为7", 7, dq.get_front());
        checkInt("get_back 应为7", 7, dq.get_back());
        // 再后端插入
        dq.input_back(8);
        checkInt("后端插入后 get_front 仍为7", 7, dq.get_front());
        checkInt("后端插入后 get_back 为8", 8, dq.get_back());
        checkInt("element_count 为2", 2, dq.element_count());
        // 前端插入应当放在队头，因此 output_front 应先得到7
        checkInt("output_front 得到7", 7, dq.output_front());
        checkInt("再次 output_front 得到8", 8, dq.output_front());
    }

    /** 前端插入多个元素 */
    public static void testInputFrontMultiple() {
        deque dq = new deque();
        dq.input_front(1);
        dq.input_front(2);
        dq.input_front(3);
        checkInt("三次前端插入后 element_count", 3, dq.element_count());
        // 期望队头到队尾为 3,2,1
        checkInt("get_front 应为3", 3, dq.get_front());
        checkInt("get_back 应为1", 1, dq.get_back());
        checkInt("output_front 第1个应为3", 3, dq.output_front());
        checkInt("output_front 第2个应为2", 2, dq.output_front());
        checkInt("output_front 第3个应为1", 1, dq.output_front());
    }

    /** input_more_back 批量后端插入 */
    public static void testInputMoreBack() {
        deque dq = new deque();
        int cnt = dq.input_more_back(5, 6, 7, 8);
        checkInt("批量后端插入4个元素后数量为4", 4, cnt);
        checkInt("get_front 为5", 5, dq.get_front());
        checkInt("get_back 为8", 8, dq.get_back());
        checkInt("output_front 顺序 5,6,7,8", 5, dq.output_front());
        checkInt("", 6, dq.output_front());
        checkInt("", 7, dq.output_front());
        checkInt("", 8, dq.output_front());
    }

    /** input_more_front 批量前端插入 */
    public static void testInputMoreFront() {
        deque dq = new deque();
        int cnt = dq.input_more_front(10, 20, 30);
        checkInt("批量前端插入3个元素后数量为3", 3, cnt);
        // 插入顺序：先10，然后20插到10前面，然后30插到20前面 -> 30,20,10
        checkInt("get_front 应为30", 30, dq.get_front());
        checkInt("get_back 应为10", 10, dq.get_back());
        checkInt("output_front 顺序 30,20,10", 30, dq.output_front());
        checkInt("", 20, dq.output_front());
        checkInt("", 10, dq.output_front());
    }

    /** get_back 在不同情境下的正确性 */
    public static void testGetBackBoundary() {
        // 只有一个元素
        deque dq = new deque();
        dq.input_back(99);
        checkInt("单元素 get_back", 99, dq.get_back());
        dq.input_front(100);
        checkInt("前端插入后 get_back 应仍为99", 99, dq.get_back());
        dq.output_back();
        checkInt("删除队尾后 get_back 应为100", 100, dq.get_back());
        dq.output_front();
        checkMinValue("全部删除后 get_back 应为 MIN_VALUE", dq.get_back());
    }

    /** output_back 出队尾并返回 */
    public static void testOutputBack() {
        deque dq = fillViaBack(1, 2, 3, 4);
        checkInt("output_back 得到 4", 4, dq.output_back());
        checkInt("剩余元素 get_back 为 3", 3, dq.get_back());
        checkInt("output_back 得到 3", 3, dq.output_back());
        checkInt("output_back 得到 2", 2, dq.output_back());
        checkInt("output_back 得到 1", 1, dq.output_back());
        checkMinValue("空队列 output_back 应为 MIN_VALUE", dq.output_back());
    }

    /** 仅用后端插入后用 output_front 出队（正常 FIFO） */
    public static void testOutputFrontFromBackInput() {
        deque dq = fillViaBack(100, 200, 300);
        checkInt("output_front 1st", 100, dq.output_front());
        checkInt("output_front 2nd", 200, dq.output_front());
        checkInt("output_front 3rd", 300, dq.output_front());
        checkMinValue("出队完毕 output_front MIN_VALUE", dq.output_front());
    }

    /** 混合前后插入和前后出队 */
    public static void testMixedInputFrontBackOutput() {
        deque dq = new deque();
        dq.input_back(1);       // 队列: 1
        dq.input_front(2);      // 2,1
        dq.input_back(3);       // 2,1,3
        dq.input_front(4);      // 4,2,1,3
        checkInt("element_count 4", 4, dq.element_count());
        checkInt("get_front 4", 4, dq.get_front());
        checkInt("get_back 3", 3, dq.get_back());

        // 出队头
        checkInt("output_front -> 4", 4, dq.output_front());
        checkInt("output_front -> 2", 2, dq.output_front());
        // 出队尾
        checkInt("output_back -> 3", 3, dq.output_back());
        checkInt("output_back -> 1", 1, dq.output_back());
        checkBool("全部出队后为空", true, dq.is_empty());
    }

    /** 填满后从后端插入触发扩容 */
    public static void testFullQueueExpandBack() {
        deque dq = new deque(4); // 小容量便于测试
        dq.input_more_back(1, 2, 3, 4);
        checkBool("4容量应满", true, dq.is_full());
        // 再插入触发扩容
        dq.input_back(5);
        checkInt("扩容后容量应为 4*2+2=10", 10, dq.capacity);
        checkInt("元素数量 5", 5, dq.element_count());
        checkBool("非满", false, dq.is_full());
        checkInt("get_front 仍为1", 1, dq.get_front());
        checkInt("get_back 为5", 5, dq.get_back());
        // 验证顺序
        checkInt("出队顺序 1,2,3,4,5", 1, dq.output_front());
        checkInt("", 2, dq.output_front());
        checkInt("", 3, dq.output_front());
        checkInt("", 4, dq.output_front());
        checkInt("", 5, dq.output_front());
    }

    /** 填满后从前端插入触发扩容 */
    public static void testFullQueueExpandFront() {
        deque dq = new deque(4);
        dq.input_more_back(10, 20, 30, 40); // 10,20,30,40
        checkBool("应满", true, dq.is_full());
        dq.input_front(0); // 从前端插入触发扩容
        checkInt("扩容后容量 10", 10, dq.capacity);
        // 期望顺序: 0,10,20,30,40
        checkInt("get_front 0", 0, dq.get_front());
        checkInt("get_back 40", 40, dq.get_back());
        checkInt("output_front -> 0", 0, dq.output_front());
        checkInt("output_front -> 10", 10, dq.output_front());
        checkInt("output_front -> 20", 20, dq.output_front());
        checkInt("output_front -> 30", 30, dq.output_front());
        checkInt("output_front -> 40", 40, dq.output_front());
    }

    /** 制造循环环绕并验证数据完整性 */
    public static void testWrapAround() {
        deque dq = new deque(4);
        // 制造环绕: 插入4个，出队2个，再插入2个，使得 rear 回绕
        dq.input_more_back(1, 2, 3, 4);
        checkInt("出队2个", 1, dq.output_front());
        checkInt("出队2个", 2, dq.output_front());
        dq.input_back(5);
        dq.input_back(6); // 此时 rear 可能回绕
        checkInt("元素数量 4", 4, dq.element_count());
        // 期望顺序: 3,4,5,6
        checkInt("get_front 3", 3, dq.get_front());
        checkInt("get_back 6", 6, dq.get_back());
        checkInt("出队 3", 3, dq.output_front());
        checkInt("出队 4", 4, dq.output_front());
        checkInt("出队 5", 5, dq.output_front());
        checkInt("出队 6", 6, dq.output_front());
        checkBool("为空", true, dq.is_empty());

        // 前端环绕: 空队列前端插入 -> rear 回绕到 capacity-1
        deque dq2 = new deque(4);
        dq2.input_front(100);
        checkInt("前端插入后 get_front 100", 100, dq2.get_front());
        // 应使 rear = 3, front = 0, overturn = true
        dq2.input_back(200);
        checkInt("再后端插入后 get_back 200", 200, dq2.get_back());
        checkInt("get_front 仍 100", 100, dq2.get_front());
        checkInt("出队顺序 100,200", 100, dq2.output_front());
        checkInt("", 200, dq2.output_front());
    }

    /** 大量数据压力测试 */
    public static void testLargeVolumeStress() {
        int N = 100_000;
        deque dq = new deque(8); // 初始小容量强制多次扩容
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            dq.input_back(i);
        }
        long mid = System.currentTimeMillis();
        checkInt("压力测试：插入 N 个元素后数量", N, dq.element_count());
        checkInt("第一个应为 0", 0, dq.get_front());
        checkInt("最后一个应为 N-1", N - 1, dq.get_back());
        // 出队一半
        for (int i = 0; i < N / 2; i++) {
            int v = dq.output_front();
            if (v != i) {
                checkInt("压力测试：出队顺序错误 at " + i, i, v);
                return;
            }
        }
        checkInt("出队一半后数量", N - N / 2, dq.element_count());
        // 再从前面插入
        for (int i = 0; i < N / 2; i++) {
            dq.input_front(-i - 1);
        }
        checkInt("混合操作后数量", N, dq.element_count());
        long end = System.currentTimeMillis();
        System.out.println(GREEN + "[AC] 压力测试通过 (插入/出队 " + N + " 元素) 耗时: " + (end - start) + "ms" + RESET);
        passed++;
    }

    /** 触发 get_back 在 rear == 0 且队列非空的情况 */
    public static void testGetBackWhenRearZero() {
        // 制造 rear == 0 且有元素: 可在小容量下通过前端插入使 rear 回绕到0并保持非空
        deque dq = new deque(4);
        // 先插入3个元素，然后出队一个使 front 移动，再通过前端插入使 rear=0
        dq.input_back(1);
        dq.input_back(2);
        dq.input_back(3);
        dq.output_front(); // 移除1, 剩余 2,3, front=1, rear=3, overturn=false
        // 现在 input_front 两次: 第一次 rear=2? 不，input_front 是 rear--，当前 rear=3，插入后 rear=2。
        // 我们需要 rear 变为 0。
        // 重新设计：填满后出队一些，再从前端插入使 rear 回绕到0
        dq = new deque(4);
        dq.input_more_back(10, 20, 30, 40); // 满
        dq.output_front(); // 出队10，front=1, rear=0? 等等：满时 input_more_back 结束后 rear 在哪？满时 rear==front 且 overturn=true。如果插入1,2,3,4，最初 capacity=4：
        // 插10: rear=1,front=0,overturn=false
        // 20: rear=2
        // 30: rear=3
        // 40: rear=0, overturn=true (因为 rear>=capacity 时 rear=0, overturn=true)
        // 此时 front=0,rear=0,overturn=true。满。
        // output_front: 取 elements[0]=10, front++ =>1, front>=capacity? 4? no. overturn 不变仍 true。
        // 现在 front=1, rear=0, overturn=true, 非空。此时 get_back 期望得到队尾元素（即40），位置在 rear-1 = -1，这会导致数组越界。
        // 我们捕获异常并将测试标记为失败。
        try {
            int back = dq.get_back();
            // 如果没有越界，检查值是否应为40
            checkInt("rear=0 时 get_back 应返回40", 40, back);
        } catch (ArrayIndexOutOfBoundsException e) {
            failed++;
            System.out.println(RED + "[WA] rear=0时get_back 抛出数组越界异常 " + e.getMessage() + RESET);
        }
    }

    /** 随机混合操作，与预期双端队列行为比较 */
    public static void testRandomMixedOperations() {
        // 使用一个简单的 LinkedList 模拟正确双端队列作为参考
        java.util.LinkedList<Integer> expected = new java.util.LinkedList<>();
        deque dq = new deque(8);
        java.util.Random rand = new java.util.Random(42);
        boolean allOk = true;
        String failMsg = "";
        int expectedVal = 0, actualVal = 0;
        for (int step = 0; step < 2000; step++) {
            int op = rand.nextInt(10);
            try {
                // System.out.println(Arrays.toString(dq.elements)+"\t"+dq.front+","+dq.rear);
                switch (op) {
                    case 0: // input_back
                    case 1: {
                        int val = rand.nextInt(1000);
                        expected.addLast(val);
                        int cnt = dq.input_back(val);
                        if (cnt != expected.size()) {
                            allOk = false; failMsg = "input_back 返回数量错误"; expectedVal = expected.size(); actualVal = cnt; break;
                        }
                        break;
                    }
                    case 2: // input_front
                    case 3: {
                        int val = rand.nextInt(1000);
                        expected.addFirst(val);
                        int cnt = dq.input_front(val);
                        if (cnt != expected.size()) {
                            allOk = false; failMsg = "input_front 返回数量错误"; expectedVal = expected.size(); actualVal = cnt; break;
                        }
                        break;
                    }
                    case 4: // output_front
                        if (expected.isEmpty()) {
                            int out = dq.output_front();
                            if (out != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 output_front 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = out;
                            }
                        } else {
                            int exp = expected.removeFirst();
                            int act = dq.output_front();
                            if (exp != act) {
                                allOk = false; failMsg = "output_front 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 5: // output_back
                        if (expected.isEmpty()) {
                            int out = dq.output_back();
                            if (out != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 output_back 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = out;
                            }
                        } else {
                            int exp = expected.removeLast();
                            int act = dq.output_back();
                            if (exp != act) {
                                allOk = false; failMsg = "output_back 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 6: // get_front
                        if (expected.isEmpty()) {
                            int act = dq.get_front();
                            if (act != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 get_front 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = act;
                            }
                        } else {
                            int exp = expected.getFirst();
                            int act = dq.get_front();
                            if (exp != act) {
                                allOk = false; failMsg = "get_front 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 7: // get_back
                        if (expected.isEmpty()) {
                            int act = dq.get_back();
                            if (act != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 get_back 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = act;
                            }
                        } else {
                            int exp = expected.getLast();
                            int act = dq.get_back();
                            if (exp != act) {
                                allOk = false; failMsg = "get_back 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 8: // is_empty
                        if (dq.is_empty() != expected.isEmpty()) {
                            allOk = false; failMsg = "is_empty 错误"; // 不需要expectedVal
                        }
                        break;
                    case 9: // element_count
                        if (dq.element_count() != expected.size()) {
                            allOk = false; failMsg = "element_count 错误"; expectedVal = expected.size(); actualVal = dq.element_count();
                        }
                        break;
                }
            } catch (Exception e) {
                allOk = false;
                failMsg = "抛出异常: " + e.getClass().getSimpleName() + " " + e.getMessage();
                break;
            }
            if (!allOk) break;
        }
        if (allOk) {
            passed++;
            System.out.println(GREEN + "[AC] 随机混合操作测试通过" + RESET);
        } else {
            failed++;
            if (failMsg.contains("数量") || failMsg.contains("错误") || failMsg.contains("MIN_VALUE")) {
                System.out.println(RED + "[WA] 随机混合操作 " + failMsg + " 期望 " + expectedVal + " 实际 " + actualVal + RESET);
            } else {
                System.out.println(RED + "[WA] 随机混合操作 " + failMsg + RESET);
            }
        }
    }
}