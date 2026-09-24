import java.util.*;

import aio.datastructure.Deque;
/**
 * deque 类的全面测试，包含边界条件与压力测试。
 * 通过 main() 方法依次执行所有测试用例，输出带 ANSI 颜色的结果。
 */
public class DequeTest {

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
    private static Deque fillViaBack(int... elements) {
        Deque dq = new Deque();
        for (int e : elements) {
            dq.inputBack(e);
        }
        return dq;
    }

    /**
     * 用前端插入填充指定数量的元素（从队头入队）。
     */
    private static Deque fillViaFront(int... elements) {
        Deque dq = new Deque();
        for (int e : elements) {
            dq.inputFront(e);
        }
        return dq;
    }

    // ---------- 测试用例 ----------

    /** 默认构造和指定容量构造 */
    public static void testNewDequeDefaults() {
        Deque dq1 = new Deque();
        checkInt("默认容量应为256", 256, dq1.capacity);
        checkBool("初始为空", true, dq1.isEmpty());
        checkBool("初始未满", false, dq1.isFull());
        checkInt("初始元素数量为0", 0, dq1.elementCount());
        checkInt("初始空余数量为256", 256, dq1.emptyCount());

        Deque dq2 = new Deque(10);
        checkInt("指定容量10", 10, dq2.capacity);
        checkInt("指定容量后元素数量为0", 0, dq2.elementCount());
    }

    /** 空队列上的只读操作 */
    public static void testEmptyDequeOperations() {
        Deque dq = new Deque();
        checkMinValue("空队列 getFront 应返回 MIN_VALUE", dq.getFront());
        checkMinValue("空队列 getBack 应返回 MIN_VALUE", dq.getBack());
        checkMinValue("空队列 outputFront 应返回 MIN_VALUE", dq.outputFront());
        checkMinValue("空队列 outputBack 应返回 MIN_VALUE", dq.outputBack());
        checkBool("空队列 isEmpty 应为 true", true, dq.isEmpty());
        checkBool("空队列 isFull 应为 false", false, dq.isFull());
    }

    /** 后端插入一个元素 */
    public static void testInputBackSingle() {
        Deque dq = new Deque();
        int cnt = dq.inputBack(42);
        checkInt("inputBack 返回数量 1", 1, cnt);
        checkInt("elementCount 为 1", 1, dq.elementCount());
        checkBool("非空", false, dq.isEmpty());
        checkInt("getFront 为 42", 42, dq.getFront());
        checkInt("getBack 为 42", 42, dq.getBack());
    }

    /** 后端插入多个元素（验证顺序） */
    public static void testInputBackMultiple() {
        Deque dq = new Deque();
        dq.inputBack(10);
        dq.inputBack(20);
        dq.inputBack(30);
        checkInt("三次后端插入后 elementCount", 3, dq.elementCount());
        checkInt("getFront 应为第一个插入的10", 10, dq.getFront());
        checkInt("getBack 应为最后插入的30", 30, dq.getBack());
        // 出队顺序应为 10,20,30
        checkInt("outputFront 第1个应为10", 10, dq.outputFront());
        checkInt("outputFront 第2个应为20", 20, dq.outputFront());
        checkInt("outputFront 第3个应为30", 30, dq.outputFront());
        checkBool("全部出队后为空", true, dq.isEmpty());
    }

    /** 前端插入一个元素（空队列） */
    public static void testInputFrontSingle() {
        Deque dq = new Deque();
        int cnt = dq.inputFront(7);
        checkInt("前端插入空队列返回数量1", 1, cnt);
        checkInt("getFront 应为7", 7, dq.getFront());
        checkInt("getBack 应为7", 7, dq.getBack());
        // 再后端插入
        dq.inputBack(8);
        checkInt("后端插入后 getFront 仍为7", 7, dq.getFront());
        checkInt("后端插入后 getBack 为8", 8, dq.getBack());
        checkInt("elementCount 为2", 2, dq.elementCount());
        // 前端插入应当放在队头，因此 outputFront 应先得到7
        checkInt("outputFront 得到7", 7, dq.outputFront());
        checkInt("再次 outputFront 得到8", 8, dq.outputFront());
    }

    /** 前端插入多个元素 */
    public static void testInputFrontMultiple() {
        Deque dq = new Deque();
        dq.inputFront(1);
        dq.inputFront(2);
        dq.inputFront(3);
        checkInt("三次前端插入后 elementCount", 3, dq.elementCount());
        // 期望队头到队尾为 3,2,1
        checkInt("getFront 应为3", 3, dq.getFront());
        checkInt("getBack 应为1", 1, dq.getBack());
        checkInt("outputFront 第1个应为3", 3, dq.outputFront());
        checkInt("outputFront 第2个应为2", 2, dq.outputFront());
        checkInt("outputFront 第3个应为1", 1, dq.outputFront());
    }

    /** inputMoreBack 批量后端插入 */
    public static void testInputMoreBack() {
        Deque dq = new Deque();
        int cnt = dq.inputMoreBack(5, 6, 7, 8);
        checkInt("批量后端插入4个元素后数量为4", 4, cnt);
        checkInt("getFront 为5", 5, dq.getFront());
        checkInt("getBack 为8", 8, dq.getBack());
        checkInt("outputFront 顺序 5,6,7,8", 5, dq.outputFront());
        checkInt("", 6, dq.outputFront());
        checkInt("", 7, dq.outputFront());
        checkInt("", 8, dq.outputFront());
    }

    /** inputMoreFront 批量前端插入 */
    public static void testInputMoreFront() {
        Deque dq = new Deque();
        int cnt = dq.inputMoreFront(10, 20, 30);
        checkInt("批量前端插入3个元素后数量为3", 3, cnt);
        // 插入顺序：先10，然后20插到10前面，然后30插到20前面 -> 30,20,10
        checkInt("getFront 应为30", 30, dq.getFront());
        checkInt("getBack 应为10", 10, dq.getBack());
        checkInt("outputFront 顺序 30,20,10", 30, dq.outputFront());
        checkInt("", 20, dq.outputFront());
        checkInt("", 10, dq.outputFront());
    }

    /** getBack 在不同情境下的正确性 */
    public static void testGetBackBoundary() {
        // 只有一个元素
        Deque dq = new Deque();
        dq.inputBack(99);
        checkInt("单元素 getBack", 99, dq.getBack());
        dq.inputFront(100);
        checkInt("前端插入后 getBack 应仍为99", 99, dq.getBack());
        dq.outputBack();
        checkInt("删除队尾后 getBack 应为100", 100, dq.getBack());
        dq.outputFront();
        checkMinValue("全部删除后 getBack 应为 MIN_VALUE", dq.getBack());
    }

    /** outputBack 出队尾并返回 */
    public static void testOutputBack() {
        Deque dq = fillViaBack(1, 2, 3, 4);
        checkInt("outputBack 得到 4", 4, dq.outputBack());
        checkInt("剩余元素 getBack 为 3", 3, dq.getBack());
        checkInt("outputBack 得到 3", 3, dq.outputBack());
        checkInt("outputBack 得到 2", 2, dq.outputBack());
        checkInt("outputBack 得到 1", 1, dq.outputBack());
        checkMinValue("空队列 outputBack 应为 MIN_VALUE", dq.outputBack());
    }

    /** 仅用后端插入后用 outputFront 出队（正常 FIFO） */
    public static void testOutputFrontFromBackInput() {
        Deque dq = fillViaBack(100, 200, 300);
        checkInt("outputFront 1st", 100, dq.outputFront());
        checkInt("outputFront 2nd", 200, dq.outputFront());
        checkInt("outputFront 3rd", 300, dq.outputFront());
        checkMinValue("出队完毕 outputFront MIN_VALUE", dq.outputFront());
    }

    /** 混合前后插入和前后出队 */
    public static void testMixedInputFrontBackOutput() {
        Deque dq = new Deque();
        dq.inputBack(1);       // 队列: 1
        dq.inputFront(2);      // 2,1
        dq.inputBack(3);       // 2,1,3
        dq.inputFront(4);      // 4,2,1,3
        checkInt("elementCount 4", 4, dq.elementCount());
        checkInt("getFront 4", 4, dq.getFront());
        checkInt("getBack 3", 3, dq.getBack());

        // 出队头
        checkInt("outputFront -> 4", 4, dq.outputFront());
        checkInt("outputFront -> 2", 2, dq.outputFront());
        // 出队尾
        checkInt("outputBack -> 3", 3, dq.outputBack());
        checkInt("outputBack -> 1", 1, dq.outputBack());
        checkBool("全部出队后为空", true, dq.isEmpty());
    }

    /** 填满后从后端插入触发扩容 */
    public static void testFullQueueExpandBack() {
        Deque dq = new Deque(4); // 小容量便于测试
        dq.inputMoreBack(1, 2, 3, 4);
        checkBool("4容量应满", true, dq.isFull());
        // 再插入触发扩容
        dq.inputBack(5);
        checkInt("扩容后容量应为 4*2+2=10", 10, dq.capacity);
        checkInt("元素数量 5", 5, dq.elementCount());
        checkBool("非满", false, dq.isFull());
        checkInt("getFront 仍为1", 1, dq.getFront());
        checkInt("getBack 为5", 5, dq.getBack());
        // 验证顺序
        checkInt("出队顺序 1,2,3,4,5", 1, dq.outputFront());
        checkInt("", 2, dq.outputFront());
        checkInt("", 3, dq.outputFront());
        checkInt("", 4, dq.outputFront());
        checkInt("", 5, dq.outputFront());
    }

    /** 填满后从前端插入触发扩容 */
    public static void testFullQueueExpandFront() {
        Deque dq = new Deque(4);
        dq.inputMoreBack(10, 20, 30, 40); // 10,20,30,40
        checkBool("应满", true, dq.isFull());
        dq.inputFront(0); // 从前端插入触发扩容
        checkInt("扩容后容量 10", 10, dq.capacity);
        // 期望顺序: 0,10,20,30,40
        checkInt("getFront 0", 0, dq.getFront());
        checkInt("getBack 40", 40, dq.getBack());
        checkInt("outputFront -> 0", 0, dq.outputFront());
        checkInt("outputFront -> 10", 10, dq.outputFront());
        checkInt("outputFront -> 20", 20, dq.outputFront());
        checkInt("outputFront -> 30", 30, dq.outputFront());
        checkInt("outputFront -> 40", 40, dq.outputFront());
    }

    /** 制造循环环绕并验证数据完整性 */
    public static void testWrapAround() {
        Deque dq = new Deque(4);
        // 制造环绕: 插入4个，出队2个，再插入2个，使得 rear 回绕
        dq.inputMoreBack(1, 2, 3, 4);
        checkInt("出队2个", 1, dq.outputFront());
        checkInt("出队2个", 2, dq.outputFront());
        dq.inputBack(5);
        dq.inputBack(6); // 此时 rear 可能回绕
        checkInt("元素数量 4", 4, dq.elementCount());
        // 期望顺序: 3,4,5,6
        checkInt("getFront 3", 3, dq.getFront());
        checkInt("getBack 6", 6, dq.getBack());
        checkInt("出队 3", 3, dq.outputFront());
        checkInt("出队 4", 4, dq.outputFront());
        checkInt("出队 5", 5, dq.outputFront());
        checkInt("出队 6", 6, dq.outputFront());
        checkBool("为空", true, dq.isEmpty());

        // 前端环绕: 空队列前端插入 -> rear 回绕到 capacity-1
        Deque dq2 = new Deque(4);
        dq2.inputFront(100);
        checkInt("前端插入后 getFront 100", 100, dq2.getFront());
        // 应使 rear = 3, front = 0, overturn = true
        dq2.inputBack(200);
        checkInt("再后端插入后 getBack 200", 200, dq2.getBack());
        checkInt("getFront 仍 100", 100, dq2.getFront());
        checkInt("出队顺序 100,200", 100, dq2.outputFront());
        checkInt("", 200, dq2.outputFront());
    }

    /** 大量数据压力测试 */
    public static void testLargeVolumeStress() {
        int N = 100000;
        Deque dq = new Deque(8); // 初始小容量强制多次扩容
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            dq.inputBack(i);
        }
        long mid = System.currentTimeMillis();
        checkInt("压力测试：插入 N 个元素后数量", N, dq.elementCount());
        checkInt("第一个应为 0", 0, dq.getFront());
        checkInt("最后一个应为 N-1", N - 1, dq.getBack());
        // 出队一半
        for (int i = 0; i < N / 2; i++) {
            int v = dq.outputFront();
            if (v != i) {
                checkInt("压力测试：出队顺序错误 at " + i, i, v);
                return;
            }
        }
        checkInt("出队一半后数量", N - N / 2, dq.elementCount());
        // 再从前面插入
        for (int i = 0; i < N / 2; i++) {
            dq.inputFront(-i - 1);
        }
        checkInt("混合操作后数量", N, dq.elementCount());
        long end = System.currentTimeMillis();
        System.out.println(GREEN + "[AC] 压力测试通过 (插入/出队 " + N + " 元素) 耗时: " + (end - start) + "ms" + RESET);
        passed++;
    }

    /** 触发 getBack 在 rear == 0 且队列非空的情况 */
    public static void testGetBackWhenRearZero() {
        // 制造 rear == 0 且有元素: 可在小容量下通过前端插入使 rear 回绕到0并保持非空
        Deque dq = new Deque(4);
        // 先插入3个元素，然后出队一个使 front 移动，再通过前端插入使 rear=0
        dq.inputBack(1);
        dq.inputBack(2);
        dq.inputBack(3);
        dq.outputFront(); // 移除1, 剩余 2,3, front=1, rear=3, overturn=false
        // 现在 inputFront 两次: 第一次 rear=2? 不，inputFront 是 rear--，当前 rear=3，插入后 rear=2。
        // 我们需要 rear 变为 0。
        // 重新设计：填满后出队一些，再从前端插入使 rear 回绕到0
        dq = new Deque(4);
        dq.inputMoreBack(10, 20, 30, 40); // 满
        dq.outputFront(); // 出队10，front=1, rear=0? 等等：满时 inputMoreBack 结束后 rear 在哪？满时 rear==front 且 overturn=true。如果插入1,2,3,4，最初 capacity=4：
        // 插10: rear=1,front=0,overturn=false
        // 20: rear=2
        // 30: rear=3
        // 40: rear=0, overturn=true (因为 rear>=capacity 时 rear=0, overturn=true)
        // 此时 front=0,rear=0,overturn=true。满。
        // outputFront: 取 elements[0]=10, front++ =>1, front>=capacity? 4? no. overturn 不变仍 true。
        // 现在 front=1, rear=0, overturn=true, 非空。此时 getBack 期望得到队尾元素（即40），位置在 rear-1 = -1，这会导致数组越界。
        // 我们捕获异常并将测试标记为失败。
        try {
            int back = dq.getBack();
            // 如果没有越界，检查值是否应为40
            checkInt("rear=0 时 getBack 应返回40", 40, back);
        } catch (ArrayIndexOutOfBoundsException e) {
            failed++;
            System.out.println(RED + "[WA] rear=0时getBack 抛出数组越界异常 " + e.getMessage() + RESET);
        }
    }

    /** 随机混合操作，与预期双端队列行为比较 */
    public static void testRandomMixedOperations() {
        // 使用一个简单的 LinkedList 模拟正确双端队列作为参考
        java.util.LinkedList<Integer> expected = new java.util.LinkedList<>();
        Deque dq = new Deque(8);
        java.util.Random rand = new java.util.Random(42);
        boolean allOk = true;
        String failMsg = "";
        int expectedVal = 0, actualVal = 0;
        for (int step = 0; step < 2000; step++) {
            int op = rand.nextInt(10);
            try {
                // System.out.println(Arrays.toString(dq.elements)+"\t"+dq.front+","+dq.rear);
                switch (op) {
                    case 0: // inputBack
                    case 1: {
                        int val = rand.nextInt(1000);
                        expected.addLast(val);
                        int cnt = dq.inputBack(val);
                        if (cnt != expected.size()) {
                            allOk = false; failMsg = "inputBack 返回数量错误"; expectedVal = expected.size(); actualVal = cnt; break;
                        }
                        break;
                    }
                    case 2: // inputFront
                    case 3: {
                        int val = rand.nextInt(1000);
                        expected.addFirst(val);
                        int cnt = dq.inputFront(val);
                        if (cnt != expected.size()) {
                            allOk = false; failMsg = "inputFront 返回数量错误"; expectedVal = expected.size(); actualVal = cnt; break;
                        }
                        break;
                    }
                    case 4: // outputFront
                        if (expected.isEmpty()) {
                            int out = dq.outputFront();
                            if (out != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 outputFront 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = out;
                            }
                        } else {
                            int exp = expected.removeFirst();
                            int act = dq.outputFront();
                            if (exp != act) {
                                allOk = false; failMsg = "outputFront 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 5: // outputBack
                        if (expected.isEmpty()) {
                            int out = dq.outputBack();
                            if (out != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 outputBack 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = out;
                            }
                        } else {
                            int exp = expected.removeLast();
                            int act = dq.outputBack();
                            if (exp != act) {
                                allOk = false; failMsg = "outputBack 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 6: // getFront
                        if (expected.isEmpty()) {
                            int act = dq.getFront();
                            if (act != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 getFront 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = act;
                            }
                        } else {
                            int exp = expected.getFirst();
                            int act = dq.getFront();
                            if (exp != act) {
                                allOk = false; failMsg = "getFront 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 7: // getBack
                        if (expected.isEmpty()) {
                            int act = dq.getBack();
                            if (act != Integer.MIN_VALUE) {
                                allOk = false; failMsg = "空队列 getBack 非 MIN_VALUE"; expectedVal = Integer.MIN_VALUE; actualVal = act;
                            }
                        } else {
                            int exp = expected.getLast();
                            int act = dq.getBack();
                            if (exp != act) {
                                allOk = false; failMsg = "getBack 错误"; expectedVal = exp; actualVal = act;
                            }
                        }
                        break;
                    case 8: // isEmpty
                        if (dq.isEmpty() != expected.isEmpty()) {
                            allOk = false; failMsg = "isEmpty 错误"; // 不需要expectedVal
                        }
                        break;
                    case 9: // elementCount
                        if (dq.elementCount() != expected.size()) {
                            allOk = false; failMsg = "elementCount 错误"; expectedVal = expected.size(); actualVal = dq.elementCount();
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