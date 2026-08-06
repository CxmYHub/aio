import aio.data_structure.linked_list_doubly;
public class linked_list_doubly_test {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        // 构造与基本状态测试
        test_constructor_empty();
        test_constructor_with_args();
        test_is_empty_and_count();

        // element_at 测试
        test_element_at_positive_index();
        test_element_at_negative_index();
        test_element_at_invalid_index();

        // index_forward / index_backward 测试
        test_index_forward();
        test_index_backward();

        // reverse_index / min_index 测试
        test_reverse_index();
        test_min_index();

        // 遍历测试
        test_traversal_forward();
        test_traversal_backward();

        // 尾部插入测试
        test_input_back();
        test_input_more_back();
        test_input_list_back();

        // 头部插入测试
        test_input_front();
        test_input_more_front();
        test_input_list_front();

        // 中间插入测试
        test_insert_positive();
        test_insert_negative();
        test_insert_boundary();

        // 多个元素与链表插入
        test_insert_more();
        test_insert_list();

        // 删除测试
        test_remove_back();
        test_remove_front();
        test_remove_index();
        test_remove_element_single();
        test_remove_element_range();

        // toString 格式测试
        test_toString();

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
    static void test_constructor_empty() {
        linked_list_doubly list = new linked_list_doubly();
        checkBoolean(true, list.is_empty(), "空构造：链表应为空");
        checkInt(0, list.element_count(), "空构造：元素个数为0");
    }

    static void test_constructor_with_args() {
        linked_list_doubly list = new linked_list_doubly(10, 20, 30);
        checkBoolean(false, list.is_empty(), "带参构造：链表非空");
        checkInt(3, list.element_count(), "带参构造：元素个数=3");
        checkInt(10, list.element_at(0), "带参构造：头元素");
        checkInt(30, list.element_at(-1), "带参构造：尾元素");
    }

    static void test_is_empty_and_count() {
        linked_list_doubly list = new linked_list_doubly();
        checkBoolean(true, list.is_empty(), "空链表 is_empty()");
        list.input_back(5);
        checkBoolean(false, list.is_empty(), "插入后 is_empty()");
        checkInt(1, list.element_count(), "插入后 element_count()");
        list.remove_back(1);
        checkBoolean(true, list.is_empty(), "删除全部后 is_empty()");
    }

    static void test_element_at_positive_index() {
        linked_list_doubly list = new linked_list_doubly(5, 6, 7, 8);
        checkInt(5, list.element_at(0), "element_at(0)");
        checkInt(6, list.element_at(1), "element_at(1)");
        checkInt(8, list.element_at(3), "element_at(3) 末尾");
    }

    static void test_element_at_negative_index() {
        linked_list_doubly list = new linked_list_doubly(5, 6, 7, 8);
        checkInt(8, list.element_at(-1), "element_at(-1)");
        checkInt(7, list.element_at(-2), "element_at(-2)");
        checkInt(5, list.element_at(-4), "element_at(-4) 开头");
    }

    static void test_element_at_invalid_index() {
        linked_list_doubly list = new linked_list_doubly(1, 2);
        checkInt(Integer.MAX_VALUE, list.element_at(2), "element_at(2) 越界");
        checkInt(Integer.MAX_VALUE, list.element_at(-3), "element_at(-3) 越界");
        linked_list_doubly empty = new linked_list_doubly();
        checkInt(Integer.MAX_VALUE, empty.element_at(0), "空链表 element_at(0)");
    }

    static void test_index_forward() {
        linked_list_doubly list = new linked_list_doubly(10, 20, 30);
        checkInt(0, list.index_forward(10), "正向查找 10 -> 0");
        checkInt(1, list.index_forward(20), "正向查找 20 -> 1");
        checkInt(Integer.MIN_VALUE, list.index_forward(99), "正向查找不存在的 99");
    }

    static void test_index_backward() {
        linked_list_doubly list = new linked_list_doubly(10, 20, 30);
        checkInt(-3, list.index_backward(10), "反向查找 10 -> -3");
        checkInt(-1, list.index_backward(30), "反向查找 30 -> -1");
        checkInt(Integer.MIN_VALUE, list.index_backward(5), "反向查找不存在的 5");
    }

    static void test_reverse_index() {
        linked_list_doubly list = new linked_list_doubly(1, 2, 3); // count=3
        // 正向转反向
        checkInt(-3, list.reverse_index(0), "reverse_index(0) -> -3");
        checkInt(-1, list.reverse_index(2), "reverse_index(2) -> -1");
        // 反向转正向
        checkInt(2, list.reverse_index(-1), "reverse_index(-1) -> 2");
        checkInt(0, list.reverse_index(-3), "reverse_index(-3) -> 0");
    }

    static void test_min_index() {
        linked_list_doubly list = new linked_list_doubly(1, 2, 3, 4, 5); // count=5
        checkInt(0, list.min_index(0), "min_index(0) 靠近头");
        checkInt(1, list.min_index(1), "min_index(1) 正向更小");
        checkInt(-2, list.min_index(3), "min_index(3) 反向-2更小");
        checkInt(-1, list.min_index(4), "min_index(4) -> -1");
        checkInt(1, list.min_index(-4), "min_index(-4) 正向1更小");
        checkInt(0, list.min_index(-5), "min_index(-5) 正向0");
    }

    static void test_traversal_forward() {
        linked_list_doubly list = new linked_list_doubly(7, 8, 9);
        int[] expected = {7, 8, 9};
        checkArray(expected, list.traversal_forward(), "正向遍历");
    }

    static void test_traversal_backward() {
        linked_list_doubly list = new linked_list_doubly(7, 8, 9);
        int[] expected = {9, 8, 7};
        checkArray(expected, list.traversal_backward(), "反向遍历");
    }

    // 尾部插入
    static void test_input_back() {
        linked_list_doubly list = new linked_list_doubly();
        int idx = list.input_back(100);
        checkInt(-1, idx, "空表尾插返回反向索引-1");
        checkInt(1, list.element_count(), "尾插后个数=1");
        checkInt(100, list.element_at(0), "尾插元素正确");

        idx = list.input_back(200);
        checkInt(-1, idx, "再次尾插返回-1");
        checkInt(2, list.element_count(), "尾插后个数=2");
        checkInt(200, list.element_at(-1), "尾插元素在末尾");
    }

    static void test_input_more_back() {
        linked_list_doubly list = new linked_list_doubly();
        int idx = list.input_more_back(1, 2, 3);
        checkInt(-3, idx, "空表尾插多个返回-3");
        checkArray(new int[]{1, 2, 3}, list.traversal_forward(), "尾插多个元素");

        // 空数组参数
        linked_list_doubly list2 = new linked_list_doubly(10);
        idx = list2.input_more_back(); // 无参相当于空数组
        checkInt(Integer.MIN_VALUE, idx, "空数组尾插返回MIN_VALUE");
        checkInt(1, list2.element_count(), "空数组插入个数不变");
    }

    static void test_input_list_back() {
        linked_list_doubly list1 = new linked_list_doubly(1, 2);
        linked_list_doubly list2 = new linked_list_doubly(3, 4);
        int idx = list1.input_list_back(list2);
        checkInt(-2, idx, "尾插链表返回-2");
        checkArray(new int[]{1, 2, 3, 4}, list1.traversal_forward(), "尾插链表内容");
    }

    // 头部插入
    static void test_input_front() {
        linked_list_doubly list = new linked_list_doubly();
        int idx = list.input_front(42);
        checkInt(0, idx, "空表头插返回0");
        checkInt(42, list.element_at(0), "头插元素");

        idx = list.input_front(99);
        checkInt(0, idx, "再头插返回0");
        checkArray(new int[]{99, 42}, list.traversal_forward(), "头插顺序");
    }

    static void test_input_more_front() {
        linked_list_doubly list = new linked_list_doubly();
        int idx = list.input_more_front(3, 4, 5);
        checkInt(0, idx, "空表多头插返回0");
        checkArray(new int[]{3, 4, 5}, list.traversal_forward(), "多头插顺序");
    }

    static void test_input_list_front() {
        linked_list_doubly list1 = new linked_list_doubly(5, 6);
        linked_list_doubly list2 = new linked_list_doubly(3, 4);
        int idx = list1.input_list_front(list2);
        checkInt(0, idx, "头插链表返回0");
        checkArray(new int[]{3, 4, 5, 6}, list1.traversal_forward(), "头插链表内容");
    }

    // 中间插入
    static void test_insert_positive() {
        linked_list_doubly list = new linked_list_doubly(10, 30);
        // 在索引1插入20
        int pos = list.insert(1, 20);
        checkInt(1, pos, "insert(1) 返回正向1");
        checkArray(new int[]{10, 20, 30}, list.traversal_forward(), "插入后序列");
    }

    static void test_insert_negative() {
        linked_list_doubly list = new linked_list_doubly(10, 20, 40);
        // 负索引 -2 插入30 (倒数第二)
        System.out.println(list);
        int pos = list.insert(-2, 30);
        System.out.println(list);
        checkInt(-2, pos, "insert(-2) 返回-2");
        checkArray(new int[]{10, 20, 30, 40}, list.traversal_forward(), "负索引插入");
    }

    static void test_insert_boundary() {
        // 越界正向索引，插入末尾
        linked_list_doubly list = new linked_list_doubly(1, 2);
        int pos = list.insert(10, 3);
        checkInt(-1, pos, "越界正索引插入末尾返回-1");
        checkArray(new int[]{1, 2, 3}, list.traversal_forward(), "末尾插入");

        // 越界负索引，插入开头
        pos = list.insert(-4, 0);
        checkInt(0, pos, "越界负索引插入开头返回0");
        checkArray(new int[]{0, 1, 2, 3}, list.traversal_forward(), "开头插入");

        // 空链表 insert
        linked_list_doubly empty = new linked_list_doubly();
        pos = empty.insert(5, 100);
        checkInt(0, pos, "空链表插入返回0");
        checkInt(100, empty.element_at(0), "空链表插入元素");
    }

    static void test_insert_more() {
        linked_list_doubly list = new linked_list_doubly(10, 50);
        int pos = list.insert_more(1, 20, 30, 40);
        // 期望：10,20,30,40,50 插入位置的最短索引：插入后第一个新元素在索引1，正1，负-4，更短为1
        checkInt(1, pos, "insert_more 正向中间返回最短索引1");
        checkArray(new int[]{10, 20, 30, 40, 50}, list.traversal_forward(), "多元素插入内容");

        // 负索引插入多个
        linked_list_doubly list2 = new linked_list_doubly(1, 2, 6);
        System.out.println(list2);
        pos = list2.insert_more(-2, 3, 4, 5); // 在 -2 位置插入，即原2之后、6之前
        System.out.println(list2);
        checkArray(new int[]{1, 2, 3, 4, 5, 6}, list2.traversal_forward(), "负索引多元素插入");
        // 最短索引应为 2 因为 2 绝对值小于 -4, 插入后新元素块占3个位置，第一个新元素在2位置，反向-4 正向2，绝对值2<4，返回2
        checkInt(2, pos, "负索引多插入返回最短2");
    }

    static void test_insert_list() {
        linked_list_doubly list1 = new linked_list_doubly(1, 2, 5);
        linked_list_doubly list2 = new linked_list_doubly(3, 4);
        int pos = list1.insert_list(2, list2);
        checkArray(new int[]{1, 2, 3, 4, 5}, list1.traversal_forward(), "插入链表内容");
        checkInt(2, pos, "插入链表返回正向2");
    }

    // 删除测试
    static void test_remove_back() {
        linked_list_doubly list = new linked_list_doubly(1, 2, 3);
        int cnt = list.remove_back(2);
        checkInt(2, cnt, "删除尾部2个返回2");
        checkArray(new int[]{1}, list.traversal_forward(), "删除尾部后");

        // 删除剩余1个
        cnt = list.remove_back(1);
        checkInt(1, cnt, "删除最后一个返回1");
        checkBoolean(true, list.is_empty(), "链表变空");

        // 空链表删除
        cnt = list.remove_back(1);
        checkInt(Integer.MIN_VALUE, cnt, "空链表尾删返回MIN_VALUE");

        // 删除0个
        linked_list_doubly list2 = new linked_list_doubly(10);
        cnt = list2.remove_back(0);
        checkInt(0, cnt, "非空链表删除0个返回0");
        checkInt(1, list2.element_count(), "删除0个元素个数不变");
    }

    static void test_remove_front() {
        linked_list_doubly list = new linked_list_doubly(5, 6, 7);
        int cnt = list.remove_front(2);
        checkInt(2, cnt, "删除头部2个返回2");
        checkArray(new int[]{7}, list.traversal_forward(), "删除头部后");

        // 删除剩余1个
        cnt = list.remove_front(1);
        checkInt(1, cnt, "删除最后头返回1");
        checkBoolean(true, list.is_empty(), "链表变空");
    }

    static void test_remove_index() {
        linked_list_doubly list = new linked_list_doubly(100, 200, 300);
        // 删除中间元素 索引1
        int val = list.remove_index(1);
        checkInt(200, val, "删除索引1元素值200");
        checkArray(new int[]{100, 300}, list.traversal_forward(), "删除后序列");

        // 删除尾元素 负索引-1
        val = list.remove_index(-1);
        checkInt(300, val, "删除负索引-1值300");
        checkArray(new int[]{100}, list.traversal_forward(), "剩一个元素");

        // 删除唯一元素
        val = list.remove_index(0);
        checkInt(100, val, "删除唯一元素值100");
        checkBoolean(true, list.is_empty(), "删除后变空");

        // 无效索引
        val = list.remove_index(0);
        checkInt(Integer.MIN_VALUE, val, "空链表 remove_index 返回 MIN_VALUE");
    }

    static void test_remove_element_single() {
        linked_list_doubly list = new linked_list_doubly(1, 2, 2, 3, 2, 4);
        int cnt = list.remove_element(2);
        checkInt(3, cnt, "删除所有2 返回3");
        checkArray(new int[]{1, 3, 4}, list.traversal_forward(), "删除2后序列");

        // 全删
        linked_list_doubly list2 = new linked_list_doubly(5, 5, 5);
        cnt = list2.remove_element(5);
        checkInt(3, cnt, "全删5 返回3");
        checkBoolean(true, list2.is_empty(), "全删后链表空");
    }

    static void test_remove_element_range() {
        linked_list_doubly list = new linked_list_doubly(10, 20, 30, 40, 50);
        int cnt = list.remove_element(20, 40); // 删除20~40
        checkInt(3, cnt, "范围删除返回3");
        checkArray(new int[]{10, 50}, list.traversal_forward(), "范围删除后");

        // 删除全部范围
        linked_list_doubly list2 = new linked_list_doubly(3, 4, 5);
        cnt = list2.remove_element(1, 9);
        checkInt(3, cnt, "全部在范围内删除返回3");
        checkBoolean(true, list2.is_empty(), "全删后为空");
    }

    static void test_toString() {
        linked_list_doubly list = new linked_list_doubly(1, 2, 3);
        String expected = "[1<=>2<=>3]";
        checkString(expected, list.toString(), "toString 格式");
    }
}