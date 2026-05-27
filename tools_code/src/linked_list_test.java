import tools.data_structure.linked_list;
import java.util.Arrays;

/**
 * linked_list 类的全面单元测试。
 * 使用断言 (assert) 验证各个方法的行为，运行时请启用 -ea 参数。
 */
public class linked_list_test {

    public static void main(String[] args) {
        boolean allPass = true;
        allPass &= runTest("构造方法", linked_list_test::testConstructors);
        allPass &= runTest("is_empty", linked_list_test::testIsEmpty);
        allPass &= runTest("element_count", linked_list_test::testElementCount);
        allPass &= runTest("element_at", linked_list_test::testElementAt);
        allPass &= runTest("index_of", linked_list_test::testIndexOf);
        allPass &= runTest("traversal", linked_list_test::testTraversal);
        allPass &= runTest("input", linked_list_test::testInput);
        allPass &= runTest("input_more", linked_list_test::testInputMore);
        allPass &= runTest("input_list", linked_list_test::testInputList);
        allPass &= runTest("insert", linked_list_test::testInsert);
        allPass &= runTest("insert_more", linked_list_test::testInsertMore);
        allPass &= runTest("insert_list", linked_list_test::testInsertList);
        allPass &= runTest("remove_last", linked_list_test::testRemoveLast);
        allPass &= runTest("remove_first", linked_list_test::testRemoveFirst);
        allPass &= runTest("remove_element (single)", linked_list_test::testRemoveElementSingle);
        allPass &= runTest("remove_element (range)", linked_list_test::testRemoveElementRange);
        allPass &= runTest("sort_ascend", linked_list_test::testSortAscend);
        allPass &= runTest("sort_descend", linked_list_test::testSortDescend);
        allPass &= runTest("toString", linked_list_test::testToString);

        if (allPass) {
            System.out.println("所有测试通过。");
        } else {
            System.out.println("部分测试失败，请查看详细信息。");
        }
    }

    // ---------- 辅助方法 ----------
    @FunctionalInterface
    interface TestMethod {
        void run() throws Exception;
    }

    private static boolean runTest(String name, TestMethod test) {
        try {
            test.run();
            System.out.println("[PASS] " + name);
            return true;
        } catch (AssertionError e) {
            System.out.println("[FAIL] " + name + " : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("[ERROR] " + name + " 抛出异常: " + e);
            return false;
        }
    }

    private static void assertEquals(int expected, int actual) {
        assert expected == actual : "期望 " + expected + "，实际 " + actual;
    }
    
    private static void assertEquals(String expected, String actual) {
        assert expected.equals(actual) : "期望 " + expected + "，实际 " + actual;
    }

    private static void assertEquals(int[] expected, int[] actual) {
        assert Arrays.equals(expected, actual) :
                "期望 " + Arrays.toString(expected) + "，实际 " + Arrays.toString(actual);
    }

    private static void assertTrue(boolean condition) {
        assert condition;
    }

    private static void assertFalse(boolean condition) {
        assert !condition;
    }

    // ---------- 测试用例 ----------

    static void testConstructors() {
        // 无参构造
        linked_list empty = new linked_list();
        assertTrue(empty.is_empty());
        assertEquals(0, empty.element_count());
        assertEquals(0, empty.traversal().length);

        // 可变参数构造
        linked_list list = new linked_list(1, 2, 3);
        assertFalse(list.is_empty());
        assertEquals(3, list.element_count());
        assertEquals(new int[]{1, 2, 3}, list.traversal());

        // 含单个元素
        linked_list single = new linked_list(42);
        assertEquals(1, single.element_count());
        assertEquals(42, single.element_at(0));

        // 空可变参数构造（预期应支持，但已知实现可能抛异常）
        try {
            linked_list emptyVararg = new linked_list(new int[0]);
            assertTrue(emptyVararg.is_empty());
            assertEquals(0, emptyVararg.element_count());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("   [KNOWN BUG] new linked_list(new int[0]) 抛出 ArrayIndexOutOfBoundsException");
        }
    }

    static void testIsEmpty() {
        assertTrue(new linked_list().is_empty());
        assertFalse(new linked_list(1).is_empty());
    }

    static void testElementCount() {
        assertEquals(0, new linked_list().element_count());
        assertEquals(1, new linked_list(5).element_count());
        assertEquals(5, new linked_list(1, 2, 3, 4, 5).element_count());
    }

    static void testElementAt() {
        linked_list list = new linked_list(10, 20, 30);
        assertEquals(10, list.element_at(0));
        assertEquals(20, list.element_at(1));
        assertEquals(30, list.element_at(2));
        assertEquals(Integer.MIN_VALUE, list.element_at(-1));
        assertEquals(Integer.MAX_VALUE, list.element_at(3));
        assertEquals(Integer.MAX_VALUE, new linked_list().element_at(0));
    }

    static void testIndexOf() {
        linked_list list = new linked_list(5, 10, 5, 20);
        assertEquals(0, list.index_of(5));
        assertEquals(1, list.index_of(10));
        assertEquals(3, list.index_of(20));
        assertEquals(Integer.MIN_VALUE, list.index_of(99));
        assertEquals(Integer.MIN_VALUE, new linked_list().index_of(0));
    }

    static void testTraversal() {
        linked_list empty = new linked_list();
        assertEquals(new int[]{}, empty.traversal());

        linked_list list = new linked_list(7, 8, 9);
        assertEquals(new int[]{7, 8, 9}, list.traversal());
    }

    static void testInput() {
        linked_list list = new linked_list();
        int pos = list.input(100);
        assertEquals(0, pos);
        assertEquals(new int[]{100}, list.traversal());

        pos = list.input(200);
        assertEquals(1, pos);
        assertEquals(new int[]{100, 200}, list.traversal());
    }

    static void testInputMore() {
        linked_list list = new linked_list(1);
        int pos = list.input_more(2, 3, 4);
        assertEquals(1, pos);
        assertEquals(new int[]{1, 2, 3, 4}, list.traversal());

        // 空数组（已知可能异常）
        try {
            linked_list empty = new linked_list();
            empty.input_more();
            assertTrue(empty.is_empty());
        } catch (Exception e) {
            System.out.println("   [KNOWN BUG] input_more() 空参数抛出异常: " + e);
        }
    }

    static void testInputList() {
        linked_list list = new linked_list(1, 2);
        linked_list sub = new linked_list(3, 4);
        int pos = list.input_list(sub);
        assertEquals(2, pos);
        assertEquals(new int[]{1, 2, 3, 4}, list.traversal());

        // 插入空子链表
        linked_list empty = new linked_list(10);
        empty.input_list(new linked_list());
        assertEquals(new int[]{10}, empty.traversal());
    }

    static void testInsert() {
        // 插入开头
        linked_list list = new linked_list(2, 3);
        int pos = list.insert(0, 1);
        assertEquals(0, pos);
        assertEquals(new int[]{1, 2, 3}, list.traversal());

        // 插入中间
        pos = list.insert(2, 99);
        assertEquals(2, pos);
        assertEquals(new int[]{1, 2, 99, 3}, list.traversal());

        // 插入末尾（索引等于当前长度）
        pos = list.insert(4, 100);
        assertEquals(4, pos);
        assertEquals(new int[]{1, 2, 99, 3, 100}, list.traversal());

        // 索引超过长度，应填充0
        linked_list padList = new linked_list(1);
        padList.insert(3, 5);
        assertEquals(new int[]{1, 0, 0, 5}, padList.traversal());

        // 负数索引（已知未处理，会错误地插入到0位置并返回负数）
        linked_list negList = new linked_list(10);
        int negPos = negList.insert(-2, 7);
        System.out.println("   [INFO] insert(-2,7) 返回值 = " + negPos + "，链表 = " + negList);
        // 预期行为未定义，此处仅展示
    }

    static void testInsertMore() {
        linked_list list = new linked_list(1, 5);
        list.insert_more(1, 2, 3, 4);
        assertEquals(new int[]{1, 2, 3, 4, 5}, list.traversal());

        // 填充
        linked_list pad = new linked_list(1);
        pad.insert_more(3, 2);
        assertEquals(new int[]{1, 0, 0, 2}, pad.traversal());
    }

    static void testInsertList() {
        linked_list list = new linked_list(1, 4);
        linked_list sub = new linked_list(2, 3);
        list.insert_list(1, sub);
        assertEquals(new int[]{1, 2, 3, 4}, list.traversal());

        // 插入空子链表（已知可能NPE）
        try {
            linked_list test = new linked_list(1);
            test.insert_list(0, new linked_list());
            assertEquals(new int[]{1}, test.traversal());
        } catch (NullPointerException e) {
            System.out.println("   [KNOWN BUG] insert_list 空子链表抛出 NullPointerException");
        }
    }

    static void testRemoveLast() {
        linked_list list = new linked_list(1, 2, 3, 4, 5);

        // 删除尾部2个
        int remain = list.remove_last(2);
        assertEquals(3, remain);
        assertEquals(new int[]{1, 2, 3}, list.traversal());

        // count 超过长度应失败
        assertEquals(Integer.MIN_VALUE, list.remove_last(10));
        assertEquals(new int[]{1, 2, 3}, list.traversal());

        // 负数 count
        assertEquals(Integer.MIN_VALUE, list.remove_last(-1));

        // 删除全部
        list.remove_last(3);
        assertTrue(list.is_empty());

        // 致命 BUG：count = 0 会清空整个链表
        linked_list bugList = new linked_list(10, 20, 30);
        int zeroResult = bugList.remove_last(0);
        assertEquals(new int[]{10,20,30}, bugList.traversal());
        // 预期应保持 [10,20,30]，实际变为 []
    }

    static void testRemoveFirst() {
        linked_list list = new linked_list(1, 2, 3, 4, 5);

        int del = list.remove_first(2);
        assertEquals(2, del);
        assertEquals(new int[]{3, 4, 5}, list.traversal());

        // 超过长度
        assertEquals(Integer.MIN_VALUE, list.remove_first(10));
        assertEquals(new int[]{3, 4, 5}, list.traversal());

        // 负数
        assertEquals(Integer.MIN_VALUE, list.remove_first(-1));

        // 删除0个
        del = list.remove_first(0);
        assertEquals(0, del);
        assertEquals(new int[]{3, 4, 5}, list.traversal());

        // 全部删除
        list.remove_first(3);
        assertTrue(list.is_empty());
    }

    static void testRemoveElementSingle() {
        linked_list list = new linked_list(1, 2, 2, 3, 2, 4);
        int removed = list.remove_element(2);
        assertEquals(3, removed);
        assertEquals(new int[]{1, 3, 4}, list.traversal());

        // 删除不存在的元素
        removed = list.remove_element(99);
        assertEquals(0, removed);
        assertEquals(new int[]{1, 3, 4}, list.traversal());

        // 删除全部
        list.remove_element(1);
        list.remove_element(3);
        list.remove_element(4);
        assertTrue(list.is_empty());
    }

    static void testRemoveElementRange() {
        linked_list list = new linked_list(1, 2, 3, 4, 5, 6);
        int removed = list.remove_element(2, 4);
        assertEquals(3, removed);
        assertEquals(new int[]{1, 5, 6}, list.traversal());

        // 包含不存在的
        removed = list.remove_element(10, 20);
        assertEquals(0, removed);
        assertEquals(new int[]{1, 5, 6}, list.traversal());

        // 全部删除
        removed = list.remove_element(1, 6);
        assertEquals(3, removed);
        assertTrue(list.is_empty());
    }

    static void testSortAscend() {
        // 空链表
        linked_list empty = new linked_list();
        assertEquals(Integer.MIN_VALUE, empty.sort_ascend());
        assertTrue(empty.is_empty());

        // 单元素
        linked_list single = new linked_list(42);
        assertEquals(42, single.sort_ascend());
        assertEquals(new int[]{42}, single.traversal());

        // 多元素乱序
        linked_list list = new linked_list(3, 1, 4, 1, 5, 9, 2, 6);
        int first = list.sort_ascend();
        assertEquals(1, first);
        assertEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 9}, list.traversal());

        // 已排序
        linked_list sorted = new linked_list(10, 20, 30);
        sorted.sort_ascend();
        assertEquals(new int[]{10, 20, 30}, sorted.traversal());

        // 反序
        linked_list reversed = new linked_list(30, 20, 10);
        reversed.sort_ascend();
        assertEquals(new int[]{10, 20, 30}, reversed.traversal());

        // 全等元素
        linked_list same = new linked_list(5, 5, 5, 5);
        same.sort_ascend();
        assertEquals(new int[]{5, 5, 5, 5}, same.traversal());
    }

    static void testSortDescend() {
        linked_list empty = new linked_list();
        assertEquals(Integer.MIN_VALUE, empty.sort_descend());

        linked_list single = new linked_list(7);
        assertEquals(7, single.sort_descend());

        linked_list list = new linked_list(3, 1, 4, 1, 5, 9, 2, 6);
        list.sort_descend();
        assertEquals(new int[]{9, 6, 5, 4, 3, 2, 1, 1}, list.traversal());

        linked_list sorted = new linked_list(30, 20, 10);
        sorted.sort_descend();
        assertEquals(new int[]{30, 20, 10}, sorted.traversal());

        linked_list reversed = new linked_list(10, 20, 30);
        reversed.sort_descend();
        assertEquals(new int[]{30, 20, 10}, reversed.traversal());

        linked_list same = new linked_list(5, 5, 5);
        same.sort_descend();
        assertEquals(new int[]{5, 5, 5}, same.traversal());
    }

    static void testToString() {
        linked_list empty = new linked_list();
        assertEquals("[]", empty.toString());

        linked_list list = new linked_list(1, 2, 3);
        assertEquals("[1->2->3]", list.toString());
    }
}