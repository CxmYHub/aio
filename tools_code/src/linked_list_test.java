import tools.data_structure.linked_list_singly;

public class linked_list_test {
    // ANSI 颜色代码
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String RESET = "\033[0m";

    private static int passed = 0, failed = 0;

    // 断言辅助方法
    private static void assertEqual(String testName, Object actual, Object expected) {
        if (actual == null && expected == null) {
            pass(testName, actual);
        } else if (actual != null && actual.equals(expected)) {
            pass(testName, actual);
        } else {
            fail(testName, actual, expected);
        }
    }

    private static void assertTrue(String testName, boolean condition, Object actualOutput) {
        if (condition) {
            pass(testName, actualOutput);
        } else {
            fail(testName, actualOutput, "true");
        }
    }

    private static void pass(String testName, Object output) {
        passed++;
        System.out.println(GREEN + "[AC] " + testName + " -> " + output + RESET);
    }

    private static void fail(String testName, Object output, Object expected) {
        failed++;
        System.out.println(RED + "[WA] " + testName + " -> " + output + " (expected: " + expected + ")" + RESET);
    }

    public static void main(String[] args) {
        // ---------- 构造函数 & isEmpty & element_count ----------
        linked_list_singly empty = new linked_list_singly();
        assertTrue("empty list is_empty()", empty.is_empty(), empty.is_empty());
        assertEqual("empty list element_count()", empty.element_count(), 0);

        linked_list_singly list1 = new linked_list_singly(10, 20, 30);
        assertTrue("varargs list not is_empty()", !list1.is_empty(), !list1.is_empty());
        assertEqual("varargs list element_count()", list1.element_count(), 3);

        // ---------- element_at ----------
        assertEqual("list1.element_at(0)", list1.element_at(0), 10);
        assertEqual("list1.element_at(2)", list1.element_at(2), 30);
        assertEqual("list1.element_at(-1) negative index", list1.element_at(-1), Integer.MIN_VALUE);
        assertEqual("list1.element_at(3) out of bounds", list1.element_at(3), Integer.MAX_VALUE);
        assertEqual("empty.element_at(0)", empty.element_at(0), Integer.MAX_VALUE);

        // ---------- index_of ----------
        assertEqual("list1.index_of(20)", list1.index_of(20), 1);
        assertEqual("list1.index_of(99) not found", list1.index_of(99), Integer.MIN_VALUE);
        assertEqual("empty.index_of(0)", empty.index_of(0), Integer.MIN_VALUE);

        // ---------- traversal ----------
        int[] arr = list1.traversal();
        assertTrue("list1.traversal() length == 3", arr.length == 3, arr.length);
        if (arr.length >= 1) assertEqual("list1.traversal()[0]", arr[0], 10);
        if (arr.length >= 3) assertEqual("list1.traversal()[2]", arr[2], 30);
        int[] emptyArr = empty.traversal();
        assertEqual("empty.traversal() length", emptyArr.length, 0);

        // ---------- input ----------
        linked_list_singly list2 = new linked_list_singly();
        int pos = list2.input(5);
        assertEqual("input into empty returns index", pos, 0);
        assertEqual("after input, element_count", list2.element_count(), 1);
        assertEqual("after input, element_at(0)", list2.element_at(0), 5);

        pos = list2.input(7);
        assertEqual("input append returns index", pos, 1);
        assertEqual("after append, element_count", list2.element_count(), 2);
        assertEqual("after append, element_at(1)", list2.element_at(1), 7);

        // ---------- input_more ----------
        linked_list_singly list3 = new linked_list_singly(1);
        pos = list3.input_more(2, 3);
        assertEqual("input_more returns index", pos, 1);
        assertEqual("after input_more, element_count", list3.element_count(), 3);
        assertEqual("input_more element_at(2)", list3.element_at(2), 3);

        pos = list3.input_more(); // 空数组
        assertEqual("input_more empty args returns index (should be -2147483648)", pos, Integer.MIN_VALUE);
        assertEqual("after empty args, element_count unchanged", list3.element_count(), 3);

        // ---------- input_list ----------
        linked_list_singly sub = new linked_list_singly(100, 200);
        linked_list_singly list4 = new linked_list_singly(1, 2);
        pos = list4.input_list(sub);
        assertEqual("input_list returns index", pos, 2);
        assertEqual("after input_list, element_count", list4.element_count(), 4);
        assertEqual("element_at(2)", list4.element_at(2), 100);
        assertEqual("element_at(3)", list4.element_at(3), 200);

        // input_list(null) 预期不抛出异常并返回 MIN_VALUE? 原测试认为不崩溃即正确，这里改为期待返回值
        linked_list_singly listBug = new linked_list_singly(1);
        int nullPos = listBug.input_list(null);
        // 根据原实现，可能返回 Integer.MIN_VALUE 或抛异常，这里假设设计上应当不崩溃返回 MIN_VALUE
        // 如果实际抛异常，测试会失败并显示异常信息
        assertEqual("input_list(null) returns MIN_VALUE (no crash)", nullPos, Integer.MIN_VALUE);

        // ---------- insert ----------
        linked_list_singly list5 = new linked_list_singly(10, 30);
        pos = list5.insert(1, 20);
        assertEqual("insert middle returns index", pos, 1);
        assertEqual("after insert, element_at(1)", list5.element_at(1), 20);
        assertEqual("element_count after insert", list5.element_count(), 3);

        pos = list5.insert(0, 5);
        assertEqual("insert head returns index", pos, 0);
        assertEqual("after head insert, element_at(0)", list5.element_at(0), 5);
        assertEqual("element_count after head insert", list5.element_count(), 4);

        pos = list5.insert(10, 99); // 超界，填充0
        assertEqual("insert over range returns index", pos, 10);
        assertEqual("element_count after padding", list5.element_count(), 11);
        assertEqual("element_at(9) after padding", list5.element_at(9), 0);
        assertEqual("element_at(10) after padding", list5.element_at(10), 99);

        pos = list5.insert(-3, 77); // 负索引 -> 末尾
        int lastIdx = list5.element_count() - 1;
        assertEqual("insert negative index works, last element is 77", list5.element_at(lastIdx), 77);

        // ---------- insert_more ----------
        linked_list_singly list6 = new linked_list_singly(1, 6);
        pos = list6.insert_more(1, 2, 3, 4, 5);
        assertEqual("insert_more returns index", pos, 1);
        assertEqual("element_count after insert_more", list6.element_count(), 6);
        assertEqual("element_at(1)", list6.element_at(1), 2);
        assertEqual("element_at(5)", list6.element_at(5), 6);

        pos = list6.insert_more(20); // 空插入，根据原测试应该返回 MIN_VALUE
        assertEqual("insert_more empty args returns MIN_VALUE", pos, Integer.MIN_VALUE);

        // ---------- insert_list ----------
        linked_list_singly list7 = new linked_list_singly(1, 5);
        linked_list_singly sub2 = new linked_list_singly(2, 3, 4);
        pos = list7.insert_list(1, sub2);
        assertEqual("insert_list returns index", pos, 1);
        assertEqual("element_count after insert_list", list7.element_count(), 5);
        assertEqual("element_at(1)", list7.element_at(1), 2);
        assertEqual("element_at(4)", list7.element_at(4), 5);

        pos = list7.insert_list(3, new linked_list_singly()); // 空子链表
        assertEqual("insert_list empty sublist returns MIN_VALUE", pos, Integer.MIN_VALUE);
        assertEqual("element_count unchanged", list7.element_count(), 5);

        // ---------- remove_tail ----------
        linked_list_singly list8 = new linked_list_singly(1, 2, 3, 4, 5);
        int remaining = list8.remove_tail(2);
        assertEqual("remove_tail(2) returns remaining count", remaining, 3);
        assertEqual("after remove_tail, element_count", list8.element_count(), 3);
        assertEqual("last element now 3", list8.element_at(2), 3);

        remaining = list8.remove_tail(0);
        assertEqual("remove_tail(0) returns same count", remaining, 3);
        assertEqual("element_count unchanged", list8.element_count(), 3);

        int failRemove = list8.remove_tail(10);
        assertEqual("remove_tail(exceed) returns MIN_VALUE", failRemove, Integer.MIN_VALUE);
        assertEqual("element_count unchanged", list8.element_count(), 3);

        failRemove = empty.remove_tail(-1);
        assertEqual("remove_tail(negative) returns MIN_VALUE", failRemove, Integer.MIN_VALUE);

        // ---------- remove_head ----------
        linked_list_singly list9 = new linked_list_singly(1, 2, 3, 4, 5);
        int deleted = list9.remove_head(2);
        assertEqual("remove_head(2) returns deleted count", deleted, 2);
        assertEqual("after remove_head, element_count", list9.element_count(), 3);
        assertEqual("new head is 3", list9.element_at(0), 3);

        deleted = list9.remove_head(0);
        assertEqual("remove_head(0) returns 0", deleted, 0);
        assertEqual("element_count unchanged", list9.element_count(), 3);

        deleted = list9.remove_head(10);
        assertEqual("remove_head(exceed) returns MIN_VALUE", deleted, Integer.MIN_VALUE);
        assertEqual("element_count unchanged", list9.element_count(), 3);

        // ---------- remove_element (single) ----------
        linked_list_singly list10 = new linked_list_singly(1, 2, 3, 2, 4);
        int removed = list10.remove_element(2);
        assertEqual("remove_element(2) returns removed count (first only)", removed, 2);
        assertEqual("after removal, element_count", list10.element_count(), 3);
        assertEqual("2 should be gone", list10.index_of(2), Integer.MIN_VALUE);

        removed = list10.remove_element(99);
        assertEqual("remove_element(not found) returns 0", removed, 0);

        // ---------- remove_element (range) ----------
        linked_list_singly list11 = new linked_list_singly(1, 2, 3, 4, 5, 6);
        removed = list11.remove_element(2, 4);
        assertEqual("remove_element(2,4) returns removed count", removed, 3);
        assertEqual("after range removal, element_count", list11.element_count(), 3);
        assertEqual("first element remains 1", list11.element_at(0), 1);
        assertEqual("second element becomes 5", list11.element_at(1), 5);

        // ---------- sort_ascend ----------
        linked_list_singly list12 = new linked_list_singly(5, 3, 1, 4, 2);
        int first = list12.sort_ascend();
        assertEqual("sort_ascend returns first element", first, 1);
        assertEqual("after sort, first element 1", list12.element_at(0), 1);
        assertEqual("after sort, last element 5", list12.element_at(4), 5);

        first = new linked_list_singly().sort_ascend();
        assertEqual("sort_ascend empty returns MIN_VALUE", first, Integer.MIN_VALUE);

        first = new linked_list_singly(42).sort_ascend();
        assertEqual("sort_ascend single returns that element", first, 42);

        // ---------- sort_descend ----------
        linked_list_singly list13 = new linked_list_singly(2, 4, 1, 5, 3);
        first = list13.sort_descend();
        assertEqual("sort_descend returns first element", first, 5);
        assertEqual("after sort, first element 5", list13.element_at(0), 5);
        assertEqual("after sort, last element 1", list13.element_at(4), 1);

        first = new linked_list_singly().sort_descend();
        assertEqual("sort_descend empty returns MIN_VALUE", first, Integer.MIN_VALUE);

        // ---------- toString ----------
        linked_list_singly list14 = new linked_list_singly(7, 8, 9);
        String str = list14.toString();
        assertEqual("toString() format", str, "[7->8->9]");

        // 最终统计
        System.out.println("\n===== Test Summary =====");
        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }
}