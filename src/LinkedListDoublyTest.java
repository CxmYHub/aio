import aio.datastructure.LinkedListDoubly;
public class LinkedListDoublyTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        // 构造与基本状态测试
        testConstructorEmpty();
        testConstructorWithArgs();
        testIsEmptyAndCount();

        // elementAt 测试
        testElementAtPositiveIndex();
        testElementAtNegativeIndex();
        testElementAtInvalidIndex();

        // indexForward / indexBackward 测试
        testIndexForward();
        testIndexBackward();

        // reverseIndex / minIndex 测试
        testReverseIndex();
        testMinIndex();

        // 遍历测试
        testTraversalForward();
        testTraversalBackward();

        // 尾部插入测试
        testInputBack();
        testInputMoreBack();
        testInputListBack();

        // 头部插入测试
        testInputFront();
        testInputMoreFront();
        testInputListFront();

        // 中间插入测试
        testInsertPositive();
        testInsertNegative();
        testInsertBoundary();

        // 多个元素与链表插入
        testInsertMmore();
        testInsertList();

        // 删除测试
        testRemoveBack();
        testRemoveFront();
        testRemoveIndex();
        testRemoveElementSingle();
        testRemoveElementRange();

        // toString 格式测试
        testToString();

        // 结果汇总
        System.out.println("\n=================================");
        System.out.printf("测试结束: 通过 %d, 失败 %d\n", passed, failed);
        if (failed == 0) {
            System.out.println("\u001B[32m所有测试通过!\u001B[0m");
        } else {
            System.out.println("\u001B[31m存在失败用例!\u001B[0m");
        }
    }

    // ---------- 辅助断言方法 ----------
    private static void pass(String msg) {
        passed++;
        System.out.println("\u001B[32m[AC] " + msg + "\u001B[0m");
    }

    private static void fail(String msg, String expected, String actual) {
        failed++;
        System.out.println("\u001B[31m[WA] " + msg + " 实际：" + actual + " 期望：" + expected + "\u001B[0m");
    }

    private static void checkInt(int expected, int actual, String msg) {
        if (expected == actual) {
            pass(msg);
        } else {
            fail(msg, Integer.toString(expected), Integer.toString(actual));
        }
    }

    private static void checkBoolean(boolean expected, boolean actual, String msg) {
        if (expected == actual) {
            pass(msg);
        } else {
            fail(msg, Boolean.toString(expected), Boolean.toString(actual));
        }
    }

    private static void checkArray(int[] expected, int[] actual, String msg) {
        if (expected.length != actual.length) {
            fail(msg + " 长度不同", arrayToString(expected), arrayToString(actual));
            return;
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                fail(msg + " 索引 " + i, arrayToString(expected), arrayToString(actual));
                return;
            }
        }
        pass(msg);
    }

    private static void checkString(String expected, String actual, String msg) {
        if (expected.equals(actual)) {
            pass(msg);
        } else {
            fail(msg, expected, actual);
        }
    }

    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    // ---------- 测试用例 ----------
    static void testConstructorEmpty() {
        LinkedListDoubly list = new LinkedListDoubly();
        checkBoolean(true, list.isEmpty(), "空构造：链表应为空");
        checkInt(0, list.elementCount(), "空构造：元素个数为0");
    }

    static void testConstructorWithArgs() {
        LinkedListDoubly list = new LinkedListDoubly(10, 20, 30);
        checkBoolean(false, list.isEmpty(), "带参构造：链表非空");
        checkInt(3, list.elementCount(), "带参构造：元素个数=3");
        checkInt(10, list.elementAt(0), "带参构造：头元素");
        checkInt(30, list.elementAt(-1), "带参构造：尾元素");
    }

    static void testIsEmptyAndCount() {
        LinkedListDoubly list = new LinkedListDoubly();
        checkBoolean(true, list.isEmpty(), "空链表 isEmpty()");
        list.inputBack(5);
        checkBoolean(false, list.isEmpty(), "插入后 isEmpty()");
        checkInt(1, list.elementCount(), "插入后 elementCount()");
        list.removeBack(1);
        checkBoolean(true, list.isEmpty(), "删除全部后 isEmpty()");
    }

    static void testElementAtPositiveIndex() {
        LinkedListDoubly list = new LinkedListDoubly(5, 6, 7, 8);
        checkInt(5, list.elementAt(0), "elementAt(0)");
        checkInt(6, list.elementAt(1), "elementAt(1)");
        checkInt(8, list.elementAt(3), "elementAt(3) 末尾");
    }

    static void testElementAtNegativeIndex() {
        LinkedListDoubly list = new LinkedListDoubly(5, 6, 7, 8);
        checkInt(8, list.elementAt(-1), "elementAt(-1)");
        checkInt(7, list.elementAt(-2), "elementAt(-2)");
        checkInt(5, list.elementAt(-4), "elementAt(-4) 开头");
    }

    static void testElementAtInvalidIndex() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2);
        checkInt(Integer.MAX_VALUE, list.elementAt(2), "elementAt(2) 越界");
        checkInt(Integer.MAX_VALUE, list.elementAt(-3), "elementAt(-3) 越界");
        LinkedListDoubly empty = new LinkedListDoubly();
        checkInt(Integer.MAX_VALUE, empty.elementAt(0), "空链表 elementAt(0)");
    }

    static void testIndexForward() {
        LinkedListDoubly list = new LinkedListDoubly(10, 20, 30);
        checkInt(0, list.indexForward(10), "正向查找 10 -> 0");
        checkInt(1, list.indexForward(20), "正向查找 20 -> 1");
        checkInt(Integer.MIN_VALUE, list.indexForward(99), "正向查找不存在的 99");
    }

    static void testIndexBackward() {
        LinkedListDoubly list = new LinkedListDoubly(10, 20, 30);
        checkInt(-3, list.indexBackward(10), "反向查找 10 -> -3");
        checkInt(-1, list.indexBackward(30), "反向查找 30 -> -1");
        checkInt(Integer.MIN_VALUE, list.indexBackward(5), "反向查找不存在的 5");
    }

    static void testReverseIndex() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2, 3); // count=3
        // 正向转反向
        checkInt(-3, list.reverseIndex(0), "reverseIndex(0) -> -3");
        checkInt(-1, list.reverseIndex(2), "reverseIndex(2) -> -1");
        // 反向转正向
        checkInt(2, list.reverseIndex(-1), "reverseIndex(-1) -> 2");
        checkInt(0, list.reverseIndex(-3), "reverseIndex(-3) -> 0");
    }

    static void testMinIndex() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2, 3, 4, 5); // count=5
        checkInt(0, list.minIndex(0), "minIndex(0) 靠近头");
        checkInt(1, list.minIndex(1), "minIndex(1) 正向更小");
        checkInt(-2, list.minIndex(3), "minIndex(3) 反向-2更小");
        checkInt(-1, list.minIndex(4), "minIndex(4) -> -1");
        checkInt(1, list.minIndex(-4), "minIndex(-4) 正向1更小");
        checkInt(0, list.minIndex(-5), "minIndex(-5) 正向0");
    }

    static void testTraversalForward() {
        LinkedListDoubly list = new LinkedListDoubly(7, 8, 9);
        int[] expected = {7, 8, 9};
        checkArray(expected, list.traversalForward(), "正向遍历");
    }

    static void testTraversalBackward() {
        LinkedListDoubly list = new LinkedListDoubly(7, 8, 9);
        int[] expected = {9, 8, 7};
        checkArray(expected, list.traversalBackward(), "反向遍历");
    }

    // 尾部插入
    static void testInputBack() {
        LinkedListDoubly list = new LinkedListDoubly();
        int idx = list.inputBack(100);
        checkInt(-1, idx, "空表尾插返回反向索引-1");
        checkInt(1, list.elementCount(), "尾插后个数=1");
        checkInt(100, list.elementAt(0), "尾插元素正确");

        idx = list.inputBack(200);
        checkInt(-1, idx, "再次尾插返回-1");
        checkInt(2, list.elementCount(), "尾插后个数=2");
        checkInt(200, list.elementAt(-1), "尾插元素在末尾");
    }

    static void testInputMoreBack() {
        LinkedListDoubly list = new LinkedListDoubly();
        int idx = list.inputMoreBack(1, 2, 3);
        checkInt(-3, idx, "空表尾插多个返回-3");
        checkArray(new int[]{1, 2, 3}, list.traversalForward(), "尾插多个元素");

        // 空数组参数
        LinkedListDoubly list2 = new LinkedListDoubly(10);
        idx = list2.inputMoreBack(); // 无参相当于空数组
        checkInt(Integer.MIN_VALUE, idx, "空数组尾插返回MIN_VALUE");
        checkInt(1, list2.elementCount(), "空数组插入个数不变");
    }

    static void testInputListBack() {
        LinkedListDoubly list1 = new LinkedListDoubly(1, 2);
        LinkedListDoubly list2 = new LinkedListDoubly(3, 4);
        int idx = list1.inputListBack(list2);
        checkInt(-2, idx, "尾插链表返回-2");
        checkArray(new int[]{1, 2, 3, 4}, list1.traversalForward(), "尾插链表内容");
    }

    // 头部插入
    static void testInputFront() {
        LinkedListDoubly list = new LinkedListDoubly();
        int idx = list.inputFront(42);
        checkInt(0, idx, "空表头插返回0");
        checkInt(42, list.elementAt(0), "头插元素");

        idx = list.inputFront(99);
        checkInt(0, idx, "再头插返回0");
        checkArray(new int[]{99, 42}, list.traversalForward(), "头插顺序");
    }

    static void testInputMoreFront() {
        LinkedListDoubly list = new LinkedListDoubly();
        int idx = list.inputMoreFront(3, 4, 5);
        checkInt(0, idx, "空表多头插返回0");
        checkArray(new int[]{3, 4, 5}, list.traversalForward(), "多头插顺序");
    }

    static void testInputListFront() {
        LinkedListDoubly list1 = new LinkedListDoubly(5, 6);
        LinkedListDoubly list2 = new LinkedListDoubly(3, 4);
        int idx = list1.inputListFront(list2);
        checkInt(0, idx, "头插链表返回0");
        checkArray(new int[]{3, 4, 5, 6}, list1.traversalForward(), "头插链表内容");
    }

    // 中间插入
    static void testInsertPositive() {
        LinkedListDoubly list = new LinkedListDoubly(10, 30);
        // 在索引1插入20
        int pos = list.insert(1, 20);
        checkInt(1, pos, "insert(1) 返回正向1");
        checkArray(new int[]{10, 20, 30}, list.traversalForward(), "插入后序列");
    }

    static void testInsertNegative() {
        LinkedListDoubly list = new LinkedListDoubly(10, 20, 40);
        // 负索引 -2 插入30 (倒数第二)
        System.out.println(list);
        int pos = list.insert(-2, 30);
        System.out.println(list);
        checkInt(-2, pos, "insert(-2) 返回-2");
        checkArray(new int[]{10, 20, 30, 40}, list.traversalForward(), "负索引插入");
    }

    static void testInsertBoundary() {
        // 越界正向索引，插入末尾
        LinkedListDoubly list = new LinkedListDoubly(1, 2);
        int pos = list.insert(10, 3);
        checkInt(-1, pos, "越界正索引插入末尾返回-1");
        checkArray(new int[]{1, 2, 3}, list.traversalForward(), "末尾插入");

        // 越界负索引，插入开头
        pos = list.insert(-4, 0);
        checkInt(0, pos, "越界负索引插入开头返回0");
        checkArray(new int[]{0, 1, 2, 3}, list.traversalForward(), "开头插入");

        // 空链表 insert
        LinkedListDoubly empty = new LinkedListDoubly();
        pos = empty.insert(5, 100);
        checkInt(0, pos, "空链表插入返回0");
        checkInt(100, empty.elementAt(0), "空链表插入元素");
    }

    static void testInsertMmore() {
        LinkedListDoubly list = new LinkedListDoubly(10, 50);
        int pos = list.insertMore(1, 20, 30, 40);
        // 期望：10,20,30,40,50 插入位置的最短索引：插入后第一个新元素在索引1，正1，负-4，更短为1
        checkInt(1, pos, "insertMore 正向中间返回最短索引1");
        checkArray(new int[]{10, 20, 30, 40, 50}, list.traversalForward(), "多元素插入内容");

        // 负索引插入多个
        LinkedListDoubly list2 = new LinkedListDoubly(1, 2, 6);
        System.out.println(list2);
        pos = list2.insertMore(-2, 3, 4, 5); // 在 -2 位置插入，即原2之后、6之前
        System.out.println(list2);
        checkArray(new int[]{1, 2, 3, 4, 5, 6}, list2.traversalForward(), "负索引多元素插入");
        // 最短索引应为 2 因为 2 绝对值小于 -4, 插入后新元素块占3个位置，第一个新元素在2位置，反向-4 正向2，绝对值2<4，返回2
        checkInt(2, pos, "负索引多插入返回最短2");
    }

    static void testInsertList() {
        LinkedListDoubly list1 = new LinkedListDoubly(1, 2, 5);
        LinkedListDoubly list2 = new LinkedListDoubly(3, 4);
        int pos = list1.insertList(2, list2);
        checkArray(new int[]{1, 2, 3, 4, 5}, list1.traversalForward(), "插入链表内容");
        checkInt(2, pos, "插入链表返回正向2");
    }

    // 删除测试
    static void testRemoveBack() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2, 3);
        int cnt = list.removeBack(2);
        checkInt(2, cnt, "删除尾部2个返回2");
        checkArray(new int[]{1}, list.traversalForward(), "删除尾部后");

        // 删除剩余1个
        cnt = list.removeBack(1);
        checkInt(1, cnt, "删除最后一个返回1");
        checkBoolean(true, list.isEmpty(), "链表变空");

        // 空链表删除
        cnt = list.removeBack(1);
        checkInt(Integer.MIN_VALUE, cnt, "空链表尾删返回MIN_VALUE");

        // 删除0个
        LinkedListDoubly list2 = new LinkedListDoubly(10);
        cnt = list2.removeBack(0);
        checkInt(0, cnt, "非空链表删除0个返回0");
        checkInt(1, list2.elementCount(), "删除0个元素个数不变");
    }

    static void testRemoveFront() {
        LinkedListDoubly list = new LinkedListDoubly(5, 6, 7);
        int cnt = list.removeFront(2);
        checkInt(2, cnt, "删除头部2个返回2");
        checkArray(new int[]{7}, list.traversalForward(), "删除头部后");

        // 删除剩余1个
        cnt = list.removeFront(1);
        checkInt(1, cnt, "删除最后头返回1");
        checkBoolean(true, list.isEmpty(), "链表变空");
    }

    static void testRemoveIndex() {
        LinkedListDoubly list = new LinkedListDoubly(100, 200, 300);
        // 删除中间元素 索引1
        int val = list.removeIndex(1);
        checkInt(200, val, "删除索引1元素值200");
        checkArray(new int[]{100, 300}, list.traversalForward(), "删除后序列");

        // 删除尾元素 负索引-1
        val = list.removeIndex(-1);
        checkInt(300, val, "删除负索引-1值300");
        checkArray(new int[]{100}, list.traversalForward(), "剩一个元素");

        // 删除唯一元素
        val = list.removeIndex(0);
        checkInt(100, val, "删除唯一元素值100");
        checkBoolean(true, list.isEmpty(), "删除后变空");

        // 无效索引
        val = list.removeIndex(0);
        checkInt(Integer.MIN_VALUE, val, "空链表 removeIndex 返回 MIN_VALUE");
    }

    static void testRemoveElementSingle() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2, 2, 3, 2, 4);
        int cnt = list.removeElement(2);
        checkInt(3, cnt, "删除所有2 返回3");
        checkArray(new int[]{1, 3, 4}, list.traversalForward(), "删除2后序列");

        // 全删
        LinkedListDoubly list2 = new LinkedListDoubly(5, 5, 5);
        cnt = list2.removeElement(5);
        checkInt(3, cnt, "全删5 返回3");
        checkBoolean(true, list2.isEmpty(), "全删后链表空");
    }

    static void testRemoveElementRange() {
        LinkedListDoubly list = new LinkedListDoubly(10, 20, 30, 40, 50);
        int cnt = list.removeElement(20, 40); // 删除20~40
        checkInt(3, cnt, "范围删除返回3");
        checkArray(new int[]{10, 50}, list.traversalForward(), "范围删除后");

        // 删除全部范围
        LinkedListDoubly list2 = new LinkedListDoubly(3, 4, 5);
        cnt = list2.removeElement(1, 9);
        checkInt(3, cnt, "全部在范围内删除返回3");
        checkBoolean(true, list2.isEmpty(), "全删后为空");
    }

    static void testToString() {
        LinkedListDoubly list = new LinkedListDoubly(1, 2, 3);
        String expected = "[1<=>2<=>3]";
        checkString(expected, list.toString(), "toString 格式");
    }
}