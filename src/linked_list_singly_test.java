import tools.data_structure.linked_list_singly;
/**
 * linked_list_singly 的测试类。
 * 在 main 方法中执行所有测试用例，输出 ANSI 彩色结果，并统计正确与错误数。
 */
public class linked_list_singly_test {

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

        // ================== is_empty, element_count ==================
        testIsEmptyTrue();
        testIsEmptyFalse();
        testElementCount();

        // ================== traversal ==================
        testTraversalEmpty();
        testTraversalNonEmpty();

        // ================== element_at ==================
        testElementAtNormal();
        testElementAtNegativeIndex();
        testElementAtIndexTooLarge();

        // ================== index_of ==================
        testIndexOfFound();
        testIndexOfNotFound();
        testIndexOfFirstOccurrence();

        // ================== input_back ==================
        testInputTailIntoEmpty();
        testInputTailIntoNonEmpty();
        testInputMoreTail();
        testInputMoreTailEmptyArray();
        testInputListTail();
        testInputListTailEmpty();

        // ================== input_front ==================
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

        // ================== remove_back ==================
        testRemoveTailNormal();
        testRemoveTailZeroCount();
        testRemoveTailTooMany();

        // ================== remove_front ==================
        testRemoveHeadNormal();
        testRemoveHeadZeroCount();
        testRemoveHeadTooMany();

        // ================== remove_index ==================
        testRemoveIndexValid();
        testRemoveIndexInvalid();

        // ================== remove_element (单值) ==================
        testRemoveElementFound();
        testRemoveElementNotFound();
        testRemoveElementDuplicates();

        // ================== remove_element (范围) ==================
        testRemoveElementRange();
        testRemoveElementRangeNoMatch();

        // ================== sort_ascend ==================
        testSortAscendNormal();
        testSortAscendSingle();
        testSortAscendEmpty();

        // ================== sort_descend ==================
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
        linked_list_singly list = new linked_list_singly();
        assertTrue(list.is_empty(), "无参构造 -> is_empty() 应为 true");
        assertEquals(list.element_count(), 0, "无参构造 -> element_count() 应为 0");
        assertArrayEquals(list.traversal(), new int[0], "无参构造 -> traversal() 应为空数组");
    }

    private static void testVarargsConstructor() {
        linked_list_singly list = new linked_list_singly(1, 2, 3);
        assertFalse(list.is_empty(), "有参构造(1,2,3) -> is_empty() 应为 false");
        assertEquals(list.element_count(), 3, "有参构造(1,2,3) -> element_count() 应为 3");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "有参构造(1,2,3) -> traversal() 应为 [1,2,3]");
    }

    private static void testTwoArgConstructor() {
        linked_list_singly node2 = new linked_list_singly(20, (linked_list_singly)null);
        linked_list_singly node1 = new linked_list_singly(10, node2);
        assertEquals(node1.element, 10, "双参构造(10, node(20)) -> 结点元素应为10");
        assertFalse(node1.next == null, "双参构造 -> next 不应为 null");
        assertEquals(node1.next.element, 20, "双参构造 -> next 元素应为20");
    }

    private static void testIsEmptyTrue() {
        linked_list_singly list = new linked_list_singly();
        assertTrue(list.is_empty(), "空链表 is_empty() 返回 true");
    }

    private static void testIsEmptyFalse() {
        linked_list_singly list = new linked_list_singly(5);
        assertFalse(list.is_empty(), "非空链表 is_empty() 返回 false");
    }

    private static void testElementCount() {
        linked_list_singly list = new linked_list_singly(10, 20, 30, 40);
        assertEquals(list.element_count(), 4, "element_count() 应为 4");
    }

    private static void testTraversalEmpty() {
        linked_list_singly list = new linked_list_singly();
        assertArrayEquals(list.traversal(), new int[0], "空链表 traversal() 返回空数组");
    }

    private static void testTraversalNonEmpty() {
        linked_list_singly list = new linked_list_singly(7, 8, 9);
        assertArrayEquals(list.traversal(), new int[]{7, 8, 9}, "traversal() 返回 [7,8,9]");
    }

    private static void testElementAtNormal() {
        linked_list_singly list = new linked_list_singly(100, 200, 300);
        assertEquals(list.element_at(0), 100, "element_at(0) 应为 100");
        assertEquals(list.element_at(2), 300, "element_at(2) 应为 300");
    }

    private static void testElementAtNegativeIndex() {
        linked_list_singly list = new linked_list_singly(1, 2);
        assertEquals(list.element_at(-1), Integer.MIN_VALUE, "element_at(-1) 返回 MIN_VALUE");
    }

    private static void testElementAtIndexTooLarge() {
        linked_list_singly list = new linked_list_singly(5);
        assertEquals(list.element_at(5), Integer.MAX_VALUE, "element_at(>=size) 返回 MAX_VALUE");
    }

    private static void testIndexOfFound() {
        linked_list_singly list = new linked_list_singly(3, 6, 9);
        assertEquals(list.index_of(6), 1, "index_of(6) 应为 1");
    }

    private static void testIndexOfNotFound() {
        linked_list_singly list = new linked_list_singly(1, 2, 3);
        assertEquals(list.index_of(99), Integer.MIN_VALUE, "index_of(不存在) 返回 MIN_VALUE");
    }

    private static void testIndexOfFirstOccurrence() {
        linked_list_singly list = new linked_list_singly(5, 5, 5);
        assertEquals(list.index_of(5), 0, "index_of(5) 应返回首次出现的索引 0");
    }

    private static void testInputTailIntoEmpty() {
        linked_list_singly list = new linked_list_singly();
        int pos = list.input_back(42);
        assertEquals(pos, 0, "空链表 input_back(42) 返回位置 0");
        assertArrayEquals(list.traversal(), new int[]{42}, "空链表 input_back 后元素为 [42]");
    }

    private static void testInputTailIntoNonEmpty() {
        linked_list_singly list = new linked_list_singly(1, 2);
        int pos = list.input_back(3);
        assertEquals(pos, 2, "非空链表 input_back(3) 返回位置 2");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "input_back 后元素 [1,2,3]");
    }

    private static void testInputMoreTail() {
        linked_list_singly list = new linked_list_singly(10);
        int pos = list.input_more_back(20, 30);
        assertEquals(pos, 1, "input_more_back(20,30) 返回位置 1");
        assertArrayEquals(list.traversal(), new int[]{10, 20, 30}, "input_more_back 后 [10,20,30]");
    }

    private static void testInputMoreTailEmptyArray() {
        linked_list_singly list = new linked_list_singly(1);
        int pos = list.input_more_back(); // 空参数
        assertEquals(pos, Integer.MIN_VALUE, "input_more_back() 空参数返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1}, "input_more_back() 后链表不变");
    }

    private static void testInputListTail() {
        linked_list_singly list1 = new linked_list_singly(1, 2);
        linked_list_singly list2 = new linked_list_singly(3, 4);
        int pos = list1.input_list_back(list2);
        assertEquals(pos, 2, "input_list_back 返回位置 2");
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4}, "input_list_back 后 [1,2,3,4]");
    }

    private static void testInputListTailEmpty() {
        linked_list_singly list1 = new linked_list_singly(100);
        linked_list_singly empty = new linked_list_singly();
        int pos = list1.input_list_back(empty);
        assertEquals(pos, Integer.MIN_VALUE, "input_list_back(空链表) 返回 MIN_VALUE");
    }

    private static void testInputHeadIntoEmpty() {
        linked_list_singly list = new linked_list_singly();
        int pos = list.input_front(9);
        assertEquals(pos, 0, "空链表 input_front(9) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{9}, "空链表 input_front 后 [9]");
    }

    private static void testInputHeadIntoNonEmpty() {
        linked_list_singly list = new linked_list_singly(2, 3);
        list.input_front(1);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "input_front(1) 后 [1,2,3]");
    }

    private static void testInputMoreHead() {
        linked_list_singly list = new linked_list_singly(4, 5);
        list.input_more_front(1, 2, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4, 5}, "input_more_front(1,2,3) 后 [1,2,3,4,5]");
    }

    private static void testInputMoreHeadEmptyArray() {
        linked_list_singly list = new linked_list_singly(7);
        int pos = list.input_more_front();
        assertEquals(pos, Integer.MIN_VALUE, "input_more_front() 空参数返回 MIN_VALUE");
    }

    private static void testInputListHead() {
        linked_list_singly list1 = new linked_list_singly(3, 4);
        linked_list_singly list2 = new linked_list_singly(1, 2);
        list1.input_list_front(list2);
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4}, "input_list_front 后 [1,2,3,4]");
    }

    private static void testInputListHeadEmpty() {
        linked_list_singly list1 = new linked_list_singly(10);
        linked_list_singly empty = new linked_list_singly();
        int pos = list1.input_list_front(empty);
        assertEquals(pos, Integer.MIN_VALUE, "input_list_front(空链表) 返回 MIN_VALUE");
    }

    private static void testInsertMiddle() {
        linked_list_singly list = new linked_list_singly(1, 3, 4);
        int pos = list.insert(1, 2);
        assertEquals(pos, 1, "insert(1,2) 返回位置 1");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4}, "insert 后 [1,2,3,4]");
    }

    private static void testInsertAtZero() {
        linked_list_singly list = new linked_list_singly(2, 3);
        list.insert(0, 1);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "insert(0,1) 后 [1,2,3]");
    }

    private static void testInsertAtLargeIndex() {
        linked_list_singly list = new linked_list_singly(1, 2);
        list.insert(100, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "insert(超大索引) 应插入末尾 [1,2,3]");
    }

    private static void testInsertMoreMiddle() {
        linked_list_singly list = new linked_list_singly(1, 4, 5);
        list.insert_more(1, 2, 3);
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3, 4, 5}, "insert_more(1,2,3) 后 [1,2,3,4,5]");
    }

    private static void testInsertMoreEmpty() {
        linked_list_singly list = new linked_list_singly(1);
        int pos = list.insert_more(0);
        assertEquals(pos, Integer.MIN_VALUE, "insert_more(空数组) 返回 MIN_VALUE");
    }

    private static void testInsertListMiddle() {
        linked_list_singly list1 = new linked_list_singly(1, 4, 5);
        linked_list_singly list2 = new linked_list_singly(2, 3);
        list1.insert_list(1, list2);
        assertArrayEquals(list1.traversal(), new int[]{1, 2, 3, 4, 5}, "insert_list 后 [1,2,3,4,5]");
    }

    private static void testInsertListEmpty() {
        linked_list_singly list1 = new linked_list_singly(1);
        linked_list_singly empty = new linked_list_singly();
        int pos = list1.insert_list(0, empty);
        assertEquals(pos, Integer.MIN_VALUE, "insert_list(空链表) 返回 MIN_VALUE");
    }

    private static void testRemoveTailNormal() {
        linked_list_singly list = new linked_list_singly(1, 2, 3, 4);
        int removed = list.remove_back(2);
        assertEquals(removed, 2, "remove_back(2) 返回删除数 2");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "remove_back(2) 后 [1,2]");
    }

    private static void testRemoveTailZeroCount() {
        linked_list_singly list = new linked_list_singly(1, 2);
        assertEquals(list.remove_back(0), 0, "remove_back(0) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "remove_back(0) 后链表不变");
    }

    private static void testRemoveTailTooMany() {
        linked_list_singly list = new linked_list_singly(1, 2, 3);
        assertEquals(list.remove_back(10), Integer.MIN_VALUE, "remove_back(超量) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "超量删除后链表不变");
    }

    private static void testRemoveHeadNormal() {
        linked_list_singly list = new linked_list_singly(1, 2, 3, 4);
        int removed = list.remove_front(2);
        assertEquals(removed, 2, "remove_front(2) 返回删除数 2");
        assertArrayEquals(list.traversal(), new int[]{3, 4}, "remove_front(2) 后 [3,4]");
    }

    private static void testRemoveHeadZeroCount() {
        linked_list_singly list = new linked_list_singly(1, 2);
        assertEquals(list.remove_front(0), 0, "remove_front(0) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "remove_front(0) 后链表不变");
    }

    private static void testRemoveHeadTooMany() {
        linked_list_singly list = new linked_list_singly(1, 2);
        assertEquals(list.remove_front(5), Integer.MIN_VALUE, "remove_front(超量) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{1, 2}, "超量删除后链表不变");
    }

    private static void testRemoveIndexValid() {
        linked_list_singly list = new linked_list_singly(10, 20, 30);
        int val = list.remove_index(1);
        assertEquals(val, 20, "remove_index(1) 返回被删元素 20");
        assertArrayEquals(list.traversal(), new int[]{10, 30}, "remove_index(1) 后 [10,30]");
    }

    private static void testRemoveIndexInvalid() {
        linked_list_singly list = new linked_list_singly(5);
        assertEquals(list.remove_index(5), Integer.MIN_VALUE, "remove_index(无效) 返回 MIN_VALUE");
        assertArrayEquals(list.traversal(), new int[]{5}, "无效索引删除后链表不变");
    }

    private static void testRemoveElementFound() {
        linked_list_singly list = new linked_list_singly(1, 2, 3, 2, 4);
        int count = list.remove_element(2);
        assertEquals(count, 2, "remove_element(2) 删除数量应为 2");
        assertArrayEquals(list.traversal(), new int[]{1, 3, 4}, "remove_element(2) 后 [1,3,4]");
    }

    private static void testRemoveElementNotFound() {
        linked_list_singly list = new linked_list_singly(1, 2, 3);
        int count = list.remove_element(99);
        assertEquals(count, 0, "remove_element(不存在) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{1, 2, 3}, "无匹配时链表不变");
    }

    private static void testRemoveElementDuplicates() {
        linked_list_singly list = new linked_list_singly(5, 5, 5, 6);
        int count = list.remove_element(5);
        assertEquals(count, 3, "remove_element(连续重复5) 返回 3");
        assertArrayEquals(list.traversal(), new int[]{6}, "删除所有5后剩 [6]");
    }

    private static void testRemoveElementRange() {
        linked_list_singly list = new linked_list_singly(1, 2, 3, 4, 5, 6);
        int count = list.remove_element(2, 4);
        assertEquals(count, 3, "remove_element(2,4) 删除数量应为 3");
        assertArrayEquals(list.traversal(), new int[]{1, 5, 6}, "删除范围[2,4]后 [1,5,6]");
    }

    private static void testRemoveElementRangeNoMatch() {
        linked_list_singly list = new linked_list_singly(10, 20);
        int count = list.remove_element(30, 40);
        assertEquals(count, 0, "remove_element(无交集范围) 返回 0");
        assertArrayEquals(list.traversal(), new int[]{10, 20}, "无匹配范围链表不变");
    }

    private static void testSortAscendNormal() {
        linked_list_singly list = new linked_list_singly(3, 1, 4, 1, 5, 9, 2);
        int first = list.sort_ascend();
        assertEquals(first, 1, "sort_ascend 返回首个元素 1");
        assertArrayEquals(list.traversal(), new int[]{1, 1, 2, 3, 4, 5, 9}, "升序排序结果正确");
    }

    private static void testSortAscendSingle() {
        linked_list_singly list = new linked_list_singly(42);
        int first = list.sort_ascend();
        assertEquals(first, 42, "单元素链表 sort_ascend 返回 42");
        assertArrayEquals(list.traversal(), new int[]{42}, "单元素链表排序后不变");
    }

    private static void testSortAscendEmpty() {
        linked_list_singly list = new linked_list_singly();
        int first = list.sort_ascend();
        assertEquals(first, Integer.MIN_VALUE, "空链表 sort_ascend 返回 MIN_VALUE");
    }

    private static void testSortDescendNormal() {
        linked_list_singly list = new linked_list_singly(2, 7, 1, 8, 3);
        int first = list.sort_descend();
        assertEquals(first, 8, "sort_descend 返回首个元素 8");
        assertArrayEquals(list.traversal(), new int[]{8, 7, 3, 2, 1}, "降序排序结果正确");
    }

    private static void testSortDescendSingle() {
        linked_list_singly list = new linked_list_singly(99);
        int first = list.sort_descend();
        assertEquals(first, 99, "单元素链表 sort_descend 返回 99");
        assertArrayEquals(list.traversal(), new int[]{99}, "单元素链表降序排序后不变");
    }

    private static void testSortDescendEmpty() {
        linked_list_singly list = new linked_list_singly();
        int first = list.sort_descend();
        assertEquals(first, Integer.MIN_VALUE, "空链表 sort_descend 返回 MIN_VALUE");
    }

    private static void testToStringEmpty() {
        linked_list_singly list = new linked_list_singly();
        assertEquals(list.toString(), "[]", "空链表 toString() 返回 \"[]\"");
    }

    private static void testToStringNonEmpty() {
        linked_list_singly list = new linked_list_singly(10, 20, 30);
        assertEquals(list.toString(), "[10->20->30]", "toString() 返回 \"[10->20->30]\"");
    }
}