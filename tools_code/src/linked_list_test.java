import tools.data_structure.*;
public class linked_list_test {
    public static void main(String[] args) {
        testConstructorAndEmpty();
        testElementCountAndIsEmpty();
        testElementAt();
        testIndexOf();
        testInputSingle();
        testInputMultiple();
        testInputAnotherList();
        testRemoveLast();
        testRemoveFirst();
        testRemoveElementByValue();
        testRemoveElementByRange();
        testSortAscend();
        testSortDescend();
        testToString();
        System.out.println("\n所有测试执行完毕。");
    }

    // ---------- 基础断言辅助 ----------
    private static void assertEqual(Object actual, Object expected, String message) {
        boolean pass = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if (pass) {
            System.out.println("  [AC] " + message);
        } else {
            System.out.println("  [WA] " + message + " - 期望: " + expected + "，实际: " + actual);
        }
    }

    private static void assertEqual(int actual, int expected, String message) {
        if (actual == expected) {
            System.out.println("  [AC] " + message);
        } else {
            System.out.println("  [WA] " + message + " - 期望: " + expected + "，实际: " + actual);
        }
    }

    private static void assertEqual(boolean actual, boolean expected, String message) {
        if (actual == expected) {
            System.out.println("  [AC] " + message);
        } else {
            System.out.println("  [WA] " + message + " - 期望: " + expected + "，实际: " + actual);
        }
    }

    private static void assertNotNull(Object obj, String message) {
        if (obj != null) {
            System.out.println("  [AC] " + message);
        } else {
            System.out.println("  [WA] " + message + " - 对象为 null");
        }
    }

    // ========== 测试方法 ==========
    private static void testConstructorAndEmpty() {
        System.out.println("=== 测试构造方法与空判断 ===");
        linked_list list = new linked_list();
        assertEqual(list.is_empty(), true, "空链表 isEmpty = true");
        assertEqual(list.element_count(), 0, "空链表元素个数为0");

        linked_list list2 = new linked_list(1, 2, 3);
        assertEqual(list2.is_empty(), false, "非空链表 isEmpty = false");
        assertEqual(list2.element_count(), 3, "非空链表元素个数为3");
        System.out.println();
    }

    private static void testElementCountAndIsEmpty() {
        System.out.println("=== 测试 element_count 与 is_empty 动态变化 ===");
        linked_list list = new linked_list();
        list.input(10);
        assertEqual(list.element_count(), 1, "插入一个元素后 count=1");
        assertEqual(list.is_empty(), false, "非空");
        list.remove_last(1);
        assertEqual(list.element_count(), 0, "删除后 count=0");
        assertEqual(list.is_empty(), true, "空");
        System.out.println();
    }

    private static void testElementAt() {
        System.out.println("=== 测试 elementAt ===");
        linked_list list = new linked_list(5, 10, 15);
        assertEqual(list.element_at(0), 5, "index=0 -> 5");
        assertEqual(list.element_at(1), 10, "index=1 -> 10");
        assertEqual(list.element_at(2), 15, "index=2 -> 15");
        assertEqual(list.element_at(-1), Integer.MIN_VALUE, "负索引返回 MIN_VALUE");
        assertEqual(list.element_at(3), Integer.MAX_VALUE, "索引超出返回 MAX_VALUE");
        System.out.println();
    }

    private static void testIndexOf() {
        System.out.println("=== 测试 indexOf ===");
        linked_list list = new linked_list(5, 10, 5, 20);
        assertEqual(list.index_of(5), 0, "第一次出现5的索引为0");
        assertEqual(list.index_of(10), 1, "10的索引为1");
        assertEqual(list.index_of(20), 3, "20的索引为3");
        assertEqual(list.index_of(99), Integer.MIN_VALUE, "不存在的元素返回 MIN_VALUE");
        System.out.println();
    }

    private static void testInputSingle() {
        System.out.println("=== 测试 input (单个元素) ===");
        linked_list list = new linked_list();
        list.input(100);
        assertEqual(list.element_count(), 1, "插入后 count=1");
        assertEqual(list.element_at(0), 100, "元素值为100");

        list.input(200);
        assertEqual(list.element_count(), 2, "再插入后 count=2");
        assertEqual(list.element_at(1), 200, "第二个元素值为200");
        System.out.println();
    }

    private static void testInputMultiple() {
        System.out.println("=== 测试 input_more (多个元素) ===");
        linked_list list = new linked_list();
        int added = list.input_more(10, 20, 30);
        assertEqual(added, 3, "input_more 返回插入数量 (3)");
        assertEqual(list.element_count(), 3, "链表元素个数=3");
        assertEqual(list.element_at(0), 10, "第一个10");
        assertEqual(list.element_at(1), 20, "第二个20");
        assertEqual(list.element_at(2), 30, "第三个30");

        // 测试向非空链表尾部追加多个
        list.input_more(40, 50);
        assertEqual(list.element_count(), 5, "追加后共5个元素");
        assertEqual(list.element_at(4), 50, "最后一个是50");
        System.out.println();
    }

    private static void testInputAnotherList() {
        System.out.println("=== 测试 input(linked_list) 插入另一个链表 ===");
        linked_list list1 = new linked_list(1, 2);
        linked_list list2 = new linked_list(3, 4);
        list1.input(list2);
        assertEqual(list1.element_count(), 4, "插入后总元素个数=4");
        assertEqual(list1.element_at(2), 3, "索引2为3");
        assertEqual(list1.element_at(3), 4, "索引3为4");

        // 边界：空链表插入另一个非空链表
        linked_list empty = new linked_list();
        linked_list list3 = new linked_list(5, 6);
        // 注意：当前代码中 empty.input(list3) 会因为 empty.head==null 导致空指针异常
        // 这是一个已知缺陷，测试会失败
        try {
            empty.input(list3);
            System.out.println("  [INFO] 空链表插入子链表成功（如果代码已修复）");
            assertEqual(empty.element_count(), 2, "空链表插入后元素个数=2");
            assertEqual(empty.element_at(0), 5, "第一个元素为5");
        } catch (NullPointerException e) {
            System.out.println("  [WA] 空链表调用 input(linked_list) 抛出 NullPointerException - 代码缺陷：未处理 head==null 情况");
        }
        System.out.println();
    }

    private static void testRemoveLast() {
        System.out.println("=== 测试 remove_last ===");
        linked_list list = new linked_list(1, 2, 3, 4, 5);
        int remaining = list.remove_last(2);
        assertEqual(remaining, 3, "删除末尾2个元素后剩余3个");
        assertEqual(list.element_count(), 3, "实际元素个数=3");
        assertEqual(list.element_at(2), 3, "新的最后一个元素是3");
        assertEqual(list.toString(), "[1->2->3]", "链表内容正确");

        // 删除所有元素
        list.remove_last(3);
        assertEqual(list.element_count(), 0, "删除所有元素后为空");
        assertEqual(list.is_empty(), true, "链表为空");

        // 非法删除
        linked_list list2 = new linked_list(10, 20);
        int ret = list2.remove_last(5);
        assertEqual(ret, Integer.MIN_VALUE, "删除数量超过长度返回 MIN_VALUE");
        assertEqual(list2.element_count(), 2, "原链表未被修改");
        System.out.println();
    }

    private static void testRemoveFirst() {
        System.out.println("=== 测试 remove_first ===");
        linked_list list = new linked_list(10, 20, 30, 40);
        int remaining = list.remove_first(2);
        assertEqual(remaining, 2, "删除开头2个元素后剩余2个");
        assertEqual(list.element_count(), 2, "实际元素个数=2");
        assertEqual(list.element_at(0), 30, "新第一个元素为30");
        assertEqual(list.element_at(1), 40, "新第二个元素为40");

        // 删除所有元素
        list.remove_first(2);
        assertEqual(list.element_count(), 0, "删除所有元素后为空");
        assertEqual(list.is_empty(), true, "空链表");

        // 非法删除
        linked_list list2 = new linked_list(5);
        int ret = list2.remove_first(2);
        assertEqual(ret, Integer.MIN_VALUE, "删除数量超过长度返回 MIN_VALUE");
        assertEqual(list2.element_count(), 1, "原链表未被修改");
        System.out.println();
    }

    private static void testRemoveElementByValue() {
        System.out.println("=== 测试 remove_element(int element) 删除所有指定值 ===");
        linked_list list = new linked_list(2, 1, 2, 3, 2);
        int removed = list.remove_element(2);
        assertEqual(removed, 3, "删除了3个值为2的节点");
        assertEqual(list.element_count(), 2, "剩余2个元素");
        assertEqual(list.element_at(0), 1, "剩余元素1");
        assertEqual(list.element_at(1), 3, "剩余元素3");

        // 删除头节点连续重复
        linked_list list2 = new linked_list(2, 2, 2, 5);
        int removed2 = list2.remove_element(2);
        assertEqual(removed2, 3, "删除3个头部2");
        assertEqual(list2.element_count(), 1, "剩下一个5");
        assertEqual(list2.element_at(0), 5, "剩下5");

        // 删除不存在的元素
        linked_list list3 = new linked_list(1, 2, 3);
        int removed3 = list3.remove_element(99);
        assertEqual(removed3, 0, "未删除任何元素");
        assertEqual(list3.element_count(), 3, "链表不变");
        System.out.println();
    }

    private static void testRemoveElementByRange() {
        System.out.println("=== 测试 remove_element(min, max) 删除范围内所有元素 ===");
        linked_list list = new linked_list(5, 1, 8, 3, 9, 2);
        int removed = list.remove_element(2, 5);
        assertEqual(removed, 3, "删除元素 5,3,2 共3个");
        assertEqual(list.element_count(), 3, "剩余 1,8,9");
        assertEqual(list.element_at(0), 1, "第一个1");
        assertEqual(list.element_at(1), 8, "第二个8");
        assertEqual(list.element_at(2), 9, "第三个9");

        // 头节点在范围内被删除
        linked_list list2 = new linked_list(3, 6, 7, 1);
        int removed2 = list2.remove_element(3, 6);
        assertEqual(removed2, 2, "删除3和6");
        assertEqual(list2.element_count(), 2, "剩下7和1");
        assertEqual(list2.element_at(0), 7, "头变为7");
        assertEqual(list2.element_at(1), 1, "尾为1");

        // 范围不含任何元素
        linked_list list3 = new linked_list(10, 20);
        int removed3 = list3.remove_element(30, 40);
        assertEqual(removed3, 0, "未删除");
        assertEqual(list3.element_count(), 2, "链表不变");
        System.out.println();
    }

    private static void testSortAscend() {
        System.out.println("=== 测试 sort_ascend 升序排序 ===");
        linked_list list = new linked_list(5, 2, 8, 1, 9);
        int first = list.sort_ascend();
        assertEqual(first, 1, "排序后第一个元素为1");
        assertEqual(list.toString(), "[1->2->5->8->9]", "升序结果正确");

        // 空链表排序
        linked_list empty = new linked_list();
        // 空链表调用会出错（head==null，sort_ascend中直接访问head.element导致NPE）
        // 这是代码缺陷，测试会暴露
        try {
            empty.sort_ascend();
            System.out.println("  [INFO] 空链表排序未抛异常（可能已修复）");
        } catch (NullPointerException e) {
            System.out.println("  [WA] 空链表调用 sort_ascend 抛出 NullPointerException - 代码缺陷：未处理空链表情况");
        }

        // 单元素链表
        linked_list single = new linked_list(42);
        int firstSingle = single.sort_ascend();
        assertEqual(firstSingle, 42, "单元素排序后仍是42");
        assertEqual(single.toString(), "[42]", "内容不变");
        System.out.println();
    }

    private static void testSortDescend() {
        System.out.println("=== 测试 sort_descend 降序排序 ===");
        linked_list list = new linked_list(5, 2, 8, 1, 9);
        int first = list.sort_descend();
        assertEqual(first, 9, "排序后第一个元素为9");
        assertEqual(list.toString(), "[9->8->5->2->1]", "降序结果正确");

        // 单元素链表
        linked_list single = new linked_list(7);
        single.sort_descend();
        assertEqual(single.toString(), "[7]", "单元素不变");
        System.out.println();
    }

    private static void testToString() {
        System.out.println("=== 测试 toString ===");
        linked_list list = new linked_list(1, 2, 3);
        assertEqual(list.toString(), "[1->2->3]", "正确格式");
        linked_list empty = new linked_list();
        assertEqual(empty.toString(), "[]", "空链表返回[]");
        linked_list single = new linked_list(9);
        assertEqual(single.toString(), "[9]", "单元素");
        System.out.println();
    }
}