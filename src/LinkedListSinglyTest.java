import aio.datastructure.LinkedListSingly;
/**
 * linkedListSingly 的测试类。
 * 在 main 方法中执行所有测试用例，输出 ANSI 彩色结果，并统计正确与错误数。
 */
public class LinkedListSinglyTest {

    // ANSI 颜色
    private static final String GREEN = "\u001B[32m";
    private static final String RED   = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private static int total   = 0;
    private static int passed  = 0;
    private static int failed  = 0;

    public static void main(String[] args) {
        // ================== 构造方法 ==================
        testEmptyConstructor();
        testVarargsConstructor();
        testTwoArgConstructor();

        // ================== isEmpty, elementCount ==================
        testIsEmptyTrue();
        testIsEmptyFalse();
        testElementCount();

        // ================== traversal ==================
        testTraversalEmpty();
        testTraversalNonEmpty();

        // ================== elementAt ==================
        testElementAtNormal();
        testElementAtNegativeIndex();
        testElementAtIndexTooLarge();

        // ================== indexOf ==================
        testIndexOfFound();
        testIndexOfNotFound();
        testIndexOfFirstOccurrence();

        // ================== inputBack ==================
        testInputTailIntoEmpty();
        testInputTailIntoNonEmpty();
        testInputMoreTail();
        testInputMoreTailEmptyArray();
        testInputListTail();
        testInputListTailEmpty();

        // ================== inputFront ==================
        testInputHeadIntoEmpty();
        testInputHeadIntoNonEmpty();
        testInputMoreHead();
        testInputMoreHeadEmptyArray();
        testInputListHead();
        testInputListHeadEmpty();

        // ================== insert ==================
        testInsertMiddle();
        testInsertAtZero();
        testInsertAtLargeIndex();
        testInsertMoreMiddle();
        testInsertMoreEmpty();
        testInsertListMiddle();
        testInsertListEmpty();

        // ================== removeBack ==================
        testRemoveTailNormal();
        testRemoveTailZeroCount();
        testRemoveTailTooMany();

        // ================== removeFront ==================
        testRemoveHeadNormal();
        testRemoveHeadZeroCount();
        testRemoveHeadTooMany();

        // ================== removeIndex ==================
        testRemoveIndexValid();
        testRemoveIndexInvalid();

        // ================== removeElement (单值) ==================
        testRemoveElementFound();
        testRemoveElementNotFound();
        testRemoveElementDuplicates();

        // ================== removeElement (范围) ==================
        testRemoveElementRange();
        testRemoveElementRangeNoMatch();

        // ================== sortAscend ==================
        testSortAscendNormal();
        testSortAscendSingle();
        testSortAscendEmpty();

        // ================== sortDescend ==================
        testSortDescendNormal();
        testSortDescendSingle();
        testSortDescendEmpty();

        // ================== toString ==================
        testToStringEmpty();
        testToStringNonEmpty();

        // ================== 总结 ==================
        System.out.println("========================================");
        System.out.println("测试结束: 总数 " + total + " | " +
                           GREEN + "通过 " + passed + RESET + " | " +
                           RED + "错误 " + failed + RESET);
        if (failed > 0) {
            System.exit(1);
        }
    }

    // ---------- 辅助方法 ----------
    private static void assertTrue(boolean condition, String msg) {
        total++;
        if (condition) {
            passed++;
            System.out.println(GREEN + "[AC] " + msg + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] 实际：false 期望：true —— " + msg + RESET);
        }
    }

    private static void assertFalse(boolean condition, String msg) {
        total++;
        if (!condition) {
            passed++;
            System.out.println(GREEN + "[AC] " + msg + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] 实际：true 期望：false —— " + msg + RESET);
        }
    }

    private static void assertEquals(int actual, int expected, String msg) {
        total++;
        if (actual == expected) {
            passed++;
            System.out.println(GREEN + "[AC] " + msg + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] 实际：" + actual + " 期望：" + expected + " —— " + msg + RESET);
        }
    }

    private static void assertEquals(String actual, String expected, String msg) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println(GREEN + "[AC] " + msg + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] 实际：" + actual + " 期望：" + expected + " —— " + msg + RESET);
        }
    }

    private static void assertArrayEquals(int[] actual, int[] expected, String msg) {
        total++;
        if (java.util.Arrays.equals(actual, expected)) {
            passed++;
            System.out.println(GREEN + "[AC] " + msg + RESET);
        } else {
            failed++;
            System.out.println(RED + "[WA] 实际：" + java.util.Arrays.toString(actual) +
                               " 期望：" + java.util.Arrays.toString(expected) + " —— " + msg + RESET);
        }
    }

    // ================== 测试用例 ==================

    private static void testEmptyConstructor() {
        LinkedListSingly list = new LinkedListSingly();
        assertTrue(list.isEmpty(), "无参构造 -> isEmpty() 应为 true");
        assertEquals(list.elementCount(), 0, "无参构造 -> elementCount() 应为 0");
        assertArrayEquals(list.traversal(), new int[0], "无参构造 -> traversal() 应为空数组");
    }

    private static void testVarargsConstructor() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3);
        assertFalse(list.isEmpty(), "有参构造(1,2,3) -> isEmpty() 应为 false");
        assertEquals(list.elementCount(), 3, "有参构造(1,2,3) -> elementCount() 应为 3");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "有参构造(1,2,3) -> traversal() 应为 [1,2,3]");
    }

    private static void testTwoArgConstructor() {
        LinkedListSingly node2 = new LinkedListSingly(20, (LinkedListSingly)null);
        LinkedListSingly node1 = new LinkedListSingly(10, node2);
        assertEquals(node1.element, 10, "双参构造(10, node(20)) -> 结点元素应为10");
        assertFalse(node1.next == null, "双参构造 -> next 不应为 null");
        assertEquals(node1.next.element, 20, "双参构造 -> next 元素应为20");
    }

    private static void testIsEmptyTrue() {
        LinkedListSingly list = new LinkedListSingly();
        assertTrue(list.isEmpty(), "空链表 isEmpty() 返回 true");
    }

    private static void testIsEmptyFalse() {
        LinkedListSingly list = new LinkedListSingly(5);
        assertFalse(list.isEmpty(), "非空链表 isEmpty() 返回 false");
    }

    private static void testElementCount() {
        LinkedListSingly list = new LinkedListSingly(10, 20, 30, 40);
        assertEquals(list.elementCount(), 4, "elementCount() 应为 4");
    }

    private static void testTraversalEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        assertArrayEquals(list.traversal(), new int[0], "空链表 traversal() 返回空数组");
    }

    private static void testTraversalNonEmpty() {
        LinkedListSingly list = new LinkedListSingly(7, 8, 9);
        assertArrayEquals(list.traversal(), new int[]{7, 8, 9}, "traversal() 返回 [7,8,9]");
    }

    private static void testElementAtNormal() {
        LinkedListSingly list = new LinkedListSingly(100, 200, 300);
        assertEquals(list.elementAt(0), 100, "elementAt(0) 应为 100");
        assertEquals(list.elementAt(2), 300, "elementAt(2) 应为 300");
    }

    private static void testElementAtNegativeIndex() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        assertEquals(list.elementAt(-1), Integer.MIN_VALUE, "elementAt(-1) 返回 MIN_VALUE");
    }

    private static void testElementAtIndexTooLarge() {
        LinkedListSingly list = new LinkedListSingly(5);
        assertEquals(list.elementAt(5), Integer.MAX_VALUE, "elementAt(>=size) 返回 MAX_VALUE");
    }

    private static void testIndexOfFound() {
        LinkedListSingly list = new LinkedListSingly(3, 6, 9);
        assertEquals(list.indexOf(6), 1, "indexOf(6) 应为 1");
    }

    private static void testIndexOfNotFound() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3);
        assertEquals(list.indexOf(99), Integer.MIN_VALUE, "indexOf(不存在) 返回 MIN_VALUE");
    }

    private static void testIndexOfFirstOccurrence() {
        LinkedListSingly list = new LinkedListSingly(5, 5, 5);
        assertEquals(list.indexOf(5), 0, "indexOf(5) 应返回首次出现的索引 0");
    }

    private static void testInputTailIntoEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        int pos = list.inputBack(42);
        assertEquals(pos, 0, "空链表 inputBack(42) 返回位置 0");
        assertArrayEquals(list.traversal(), new int[]{42}, "空链表 inputBack 后元素为 [42]");
    }

    private static void testInputTailIntoNonEmpty() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        int pos = list.inputBack(3);
        assertEquals(pos, 2, "非空链表 inputBack(3) 返回位置 2");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "inputBack 后元素 [1,2,3]");
    }

    private static void testInputMoreTail() {
        LinkedListSingly list = new LinkedListSingly(10);
        int pos = list.inputMoreBack(20, 30);
        assertEquals(pos, 1, "inputMoreBack(20,30) 返回位置 1");
        assertArrayEquals(list.traversal(), new int[]{10, 20, 30}, "inputMoreBack 后 [10,20,30]");
    }

    private static void testInputMoreTailEmptyArray() {
        LinkedListSingly list = new LinkedListSingly(1);
        int pos = list.inputMoreBack(); // 空参数
        assertEquals(pos, Integer.MIN_VALUE, "inputMoreBack() 空参数返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1}, "inputMoreBack() 后链表不变");
    }

    private static void testInputListTail() {
        LinkedListSingly list1 = new LinkedListSingly(1, 2);
        LinkedListSingly list2 = new LinkedListSingly(3, 4);
        int pos = list1.inputListBack(list2);
        assertEquals(pos, 2, "inputListBack 返回位置 2");
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4}, "inputListBack 后 [1,2,3,4]");
    }

    private static void testInputListTailEmpty() {
        LinkedListSingly list1 = new LinkedListSingly(100);
        LinkedListSingly empty = new LinkedListSingly();
        int pos = list1.inputListBack(empty);
        assertEquals(pos, Integer.MIN_VALUE, "inputListBack(空链表) 返回 MIN_VALUE");
    }

    private static void testInputHeadIntoEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        int pos = list.inputFront(9);
        assertEquals(pos, 0, "空链表 inputFront(9) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{9}, "空链表 inputFront 后 [9]");
    }

    private static void testInputHeadIntoNonEmpty() {
        LinkedListSingly list = new LinkedListSingly(2, 3);
        list.inputFront(1);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "inputFront(1) 后 [1,2,3]");
    }

    private static void testInputMoreHead() {
        LinkedListSingly list = new LinkedListSingly(4, 5);
        list.inputMoreFront(1, 2, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4, 5}, "inputMoreFront(1,2,3) 后 [1,2,3,4,5]");
    }

    private static void testInputMoreHeadEmptyArray() {
        LinkedListSingly list = new LinkedListSingly(7);
        int pos = list.inputMoreFront();
        assertEquals(pos, Integer.MIN_VALUE, "inputMoreFront() 空参数返回 MIN_VALUE");
    }

    private static void testInputListHead() {
        LinkedListSingly list1 = new LinkedListSingly(3, 4);
        LinkedListSingly list2 = new LinkedListSingly(1, 2);
        list1.inputListFront(list2);
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4}, "inputListFront 后 [1,2,3,4]");
    }

    private static void testInputListHeadEmpty() {
        LinkedListSingly list1 = new LinkedListSingly(10);
        LinkedListSingly empty = new LinkedListSingly();
        int pos = list1.inputListFront(empty);
        assertEquals(pos, Integer.MIN_VALUE, "inputListFront(空链表) 返回 MIN_VALUE");
    }

    private static void testInsertMiddle() {
        LinkedListSingly list = new LinkedListSingly(1, 3, 4);
        int pos = list.insert(1, 2);
        assertEquals(pos, 1, "insert(1,2) 返回位置 1");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4}, "insert 后 [1,2,3,4]");
    }

    private static void testInsertAtZero() {
        LinkedListSingly list = new LinkedListSingly(2, 3);
        list.insert(0, 1);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "insert(0,1) 后 [1,2,3]");
    }

    private static void testInsertAtLargeIndex() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        list.insert(100, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "insert(超大索引) 应插入末尾 [1,2,3]");
    }

    private static void testInsertMoreMiddle() {
        LinkedListSingly list = new LinkedListSingly(1, 4, 5);
        list.insertMore(1, 2, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4, 5}, "insertMore(1,2,3) 后 [1,2,3,4,5]");
    }

    private static void testInsertMoreEmpty() {
        LinkedListSingly list = new LinkedListSingly(1);
        int pos = list.insertMore(0);
        assertEquals(pos, Integer.MIN_VALUE, "insertMore(空数组) 返回 MIN_VALUE");
    }

    private static void testInsertListMiddle() {
        LinkedListSingly list1 = new LinkedListSingly(1, 4, 5);
        LinkedListSingly list2 = new LinkedListSingly(2, 3);
        list1.insertList(1, list2);
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4, 5}, "insertList 后 [1,2,3,4,5]");
    }

    private static void testInsertListEmpty() {
        LinkedListSingly list1 = new LinkedListSingly(1);
        LinkedListSingly empty = new LinkedListSingly();
        int pos = list1.insertList(0, empty);
        assertEquals(pos, Integer.MIN_VALUE, "insertList(空链表) 返回 MIN_VALUE");
    }

    private static void testRemoveTailNormal() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3, 4);
        int removed = list.removeBack(2);
        assertEquals(removed, 2, "removeBack(2) 返回删除数 2");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "removeBack(2) 后 [1,2]");
    }

    private static void testRemoveTailZeroCount() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        assertEquals(list.removeBack(0), 0, "removeBack(0) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "removeBack(0) 后链表不变");
    }

    private static void testRemoveTailTooMany() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3);
        assertEquals(list.removeBack(10), Integer.MIN_VALUE, "removeBack(超量) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "超量删除后链表不变");
    }

    private static void testRemoveHeadNormal() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3, 4);
        int removed = list.removeFront(2);
        assertEquals(removed, 2, "removeFront(2) 返回删除数 2");
        assertArrayEquals(list.traversal(), new int[]{3, 4}, "removeFront(2) 后 [3,4]");
    }

    private static void testRemoveHeadZeroCount() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        assertEquals(list.removeFront(0), 0, "removeFront(0) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "removeFront(0) 后链表不变");
    }

    private static void testRemoveHeadTooMany() {
        LinkedListSingly list = new LinkedListSingly(1, 2);
        assertEquals(list.removeFront(5), Integer.MIN_VALUE, "removeFront(超量) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "超量删除后链表不变");
    }

    private static void testRemoveIndexValid() {
        LinkedListSingly list = new LinkedListSingly(10, 20, 30);
        int val = list.removeIndex(1);
        assertEquals(val, 20, "removeIndex(1) 返回被删元素 20");
        assertArrayEquals(list.traversal(), new int[]{10, 30}, "removeIndex(1) 后 [10,30]");
    }

    private static void testRemoveIndexInvalid() {
        LinkedListSingly list = new LinkedListSingly(5);
        assertEquals(list.removeIndex(5), Integer.MIN_VALUE, "removeIndex(无效) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{5}, "无效索引删除后链表不变");
    }

    private static void testRemoveElementFound() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3, 2, 4);
        int count = list.removeElement(2);
        assertEquals(count, 2, "removeElement(2) 删除数量应为 2");
        assertArrayEquals(list.traversal(), new int[]{1, 3, 4}, "removeElement(2) 后 [1,3,4]");
    }

    private static void testRemoveElementNotFound() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3);
        int count = list.removeElement(99);
        assertEquals(count, 0, "removeElement(不存在) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "无匹配时链表不变");
    }

    private static void testRemoveElementDuplicates() {
        LinkedListSingly list = new LinkedListSingly(5, 5, 5, 6);
        int count = list.removeElement(5);
        assertEquals(count, 3, "removeElement(连续重复5) 返回 3");
        assertArrayEquals(list.traversal(), new int[]{6}, "删除所有5后剩 [6]");
    }

    private static void testRemoveElementRange() {
        LinkedListSingly list = new LinkedListSingly(1, 2, 3, 4, 5, 6);
        int count = list.removeElement(2, 4);
        assertEquals(count, 3, "removeElement(2,4) 删除数量应为 3");
        assertArrayEquals(list.traversal(), new int[]{1, 5, 6}, "删除范围[2,4]后 [1,5,6]");
    }

    private static void testRemoveElementRangeNoMatch() {
        LinkedListSingly list = new LinkedListSingly(10, 20);
        int count = list.removeElement(30, 40);
        assertEquals(count, 0, "removeElement(无交集范围) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{10, 20}, "无匹配范围链表不变");
    }

    private static void testSortAscendNormal() {
        LinkedListSingly list = new LinkedListSingly(3, 1, 4, 1, 5, 9, 2);
        int first = list.sortAscend();
        assertEquals(first, 1, "sortAscend 返回首个元素 1");
        assertArrayEquals(list.traversal(), new int[]{1, 1, 2, 3, 4, 5, 9}, "升序排序结果正确");
    }

    private static void testSortAscendSingle() {
        LinkedListSingly list = new LinkedListSingly(42);
        int first = list.sortAscend();
        assertEquals(first, 42, "单元素链表 sortAscend 返回 42");
        assertArrayEquals(list.traversal(), new int[]{42}, "单元素链表排序后不变");
    }

    private static void testSortAscendEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        int first = list.sortAscend();
        assertEquals(first, Integer.MIN_VALUE, "空链表 sortAscend 返回 MIN_VALUE");
    }

    private static void testSortDescendNormal() {
        LinkedListSingly list = new LinkedListSingly(2, 7, 1, 8, 3);
        int first = list.sortDescend();
        assertEquals(first, 8, "sortDescend 返回首个元素 8");
        assertArrayEquals(list.traversal(), new int[]{8, 7, 3, 2, 1}, "降序排序结果正确");
    }

    private static void testSortDescendSingle() {
        LinkedListSingly list = new LinkedListSingly(99);
        int first = list.sortDescend();
        assertEquals(first, 99, "单元素链表 sortDescend 返回 99");
        assertArrayEquals(list.traversal(), new int[]{99}, "单元素链表降序排序后不变");
    }

    private static void testSortDescendEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        int first = list.sortDescend();
        assertEquals(first, Integer.MIN_VALUE, "空链表 sortDescend 返回 MIN_VALUE");
    }

    private static void testToStringEmpty() {
        LinkedListSingly list = new LinkedListSingly();
        assertEquals(list.toString(), "[]", "空链表 toString() 返回 \"[]\"");
    }

    private static void testToStringNonEmpty() {
        LinkedListSingly list = new LinkedListSingly(10, 20, 30);
        assertEquals(list.toString(), "[10->20->30]", "toString() 返回 \"[10->20->30]\"");
    }
}