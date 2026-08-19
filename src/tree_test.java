import java.util.Arrays;
import aio.data_structure.tree;
/**
 * tree 类的测试类，包含基本功能、边界条件与压力测试。
 * 每个测试用例通过 check 方法进行断言，正确输出绿色 [AC]，错误输出红色 [WA]。
 */
public class tree_test {

    private static int passedCount = 0;
    private static int failedCount = 0;

    public static void main(String[] args) {
        testConstructorInt();
        testConstructorStringNumeric();
        testConstructorStringSingle();
        testConstructorStringAlpha();
        testConstructorEmptyString();
        testConstructorStringDeepBoundary();
        testCountAndDepth();
        testTraversalPreorder();
        testTraversalPostorder();
        testTraversalLevelorder();
        testInsertTo();
        testRemove();
        testToString();
        testPressureDeepTree();
        testPressureWideTree();
        testPressureInsertMany();
        testPressureRemoveMany();

        System.out.println("测试完成：通过 " + passedCount + " 个，失败 " + failedCount + " 个");
    }

    /**
     * 通用断言方法。
     *
     * @param testName 测试用例中文描述
     * @param actual   实际输出
     * @param expected 期望输出
     */
    private static void check(String testName, Object actual, Object expected) {
        boolean passed;
        if (actual == null && expected == null) {
            passed = true;
        } else if (actual == null || expected == null) {
            passed = false;
        } else if (actual instanceof int[] && expected instanceof int[]) {
            passed = Arrays.equals((int[]) actual, (int[]) expected);
        } else if (actual instanceof int[] || expected instanceof int[]) {
            passed = false;
        } else {
            passed = actual.equals(expected);
        }

        if (passed) {
            passedCount++;
            System.out.println("\u001B[32m[AC] " + testName + " 实际输出: " + stringify(actual) + "\u001B[0m");
        } else {
            failedCount++;
            System.out.println("\u001B[31m[WA] " + testName + " 期望输出: " + stringify(expected)
                    + " 实际输出: " + stringify(actual) + "\u001B[0m");
        }
    }

    /**
     * 将对象转为可读字符串，数组使用 Arrays.toString。
     */
    private static String stringify(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        }
        return obj.toString();
    }

    // ==================== 构造函数测试 ====================

    private static void testConstructorInt() {
        tree t = new tree(42);
        check("构造函数-int构造-元素", t.element, 42);
        check("构造函数-int构造-子结点为空", t.child, null);
        check("构造函数-int构造-兄弟为空", t.next, null);
    }

    private static void testConstructorStringNumeric() {
        tree t = new tree("12{34,56{78,90},100}");
        check("构造函数-字符串数字-结点数", t.count(), 6);
        check("构造函数-字符串数字-先序遍历", t.traversal_preorder(), new int[]{12, 34, 56, 78, 90, 100});
        check("构造函数-字符串数字-后序遍历", t.traversal_postorder(), new int[]{34, 78, 90, 56, 100, 12});
        check("构造函数-字符串数字-层序遍历", t.traversal_levelorder(), new int[]{12, 34, 56, 100, 78, 90});
        check("构造函数-字符串数字-深度", t.depth(), 3);
        check("构造函数-字符串数字-字符串表示", t.toString(), "12{34,56{78,90},100}");
    }

    private static void testConstructorStringSingle() {
        tree t = new tree("7");
        check("构造函数-字符串单节点-元素", t.element, 7);
        check("构造函数-字符串单节点-结点数", t.count(), 1);
        check("构造函数-字符串单节点-深度", t.depth(), 1);
        check("构造函数-字符串单节点-先序遍历", t.traversal_preorder(), new int[]{7});
        check("构造函数-字符串单节点-后序遍历", t.traversal_postorder(), new int[]{7});
        check("构造函数-字符串单节点-层序遍历", t.traversal_levelorder(), new int[]{7});
        check("构造函数-字符串单节点-字符串表示", t.toString(), "7");
    }

    private static void testConstructorStringAlpha() {
        tree t = new tree("A{B,C}");
        check("构造函数-字符串字母-结点数", t.count(), 3);
        check("构造函数-字符串字母-先序遍历", t.traversal_preorder(), new int[]{65, 66, 67});
        check("构造函数-字符串字母-后序遍历", t.traversal_postorder(), new int[]{66, 67, 65});
        check("构造函数-字符串字母-层序遍历", t.traversal_levelorder(), new int[]{65, 66, 67});
        check("构造函数-字符串字母-深度", t.depth(), 2);
    }

    private static void testConstructorEmptyString() {
        tree t = new tree("");
        check("构造函数-空字符串-元素", t.element, 0);
        check("构造函数-空字符串-结点数", t.count(), 1);
        check("构造函数-空字符串-深度", t.depth(), 1);
        check("构造函数-空字符串-先序遍历", t.traversal_preorder(), new int[]{0});
        check("构造函数-空字符串-后序遍历", t.traversal_postorder(), new int[]{0});
        check("构造函数-空字符串-层序遍历", t.traversal_levelorder(), new int[]{0});
        check("构造函数-空字符串-字符串表示", t.toString(), "0");
    }

    private static void testConstructorStringDeepBoundary() {
        // 构造一个嵌套 10 层的树（不触发底层数组越界）
        String s = "0{1{2{3{4{5{6{7{8{9}}}}}}}}}";
        tree t = new tree(s);
        check("构造函数-字符串深度边界-结点数", t.count(), 10);
        check("构造函数-字符串深度边界-深度", t.depth(), 10);
        check("构造函数-字符串深度边界-先序尾元素", t.traversal_preorder()[9], 9);
    }

    // ==================== 计数与深度测试 ====================

    private static void testCountAndDepth() {
        tree t = new tree("1{2,3{4,5},6}");
        check("结点计数-多层树", t.count(), 6);
        check("深度计算-多层树", t.depth(), 3);

        tree single = new tree(9);
        check("结点计数-单节点", single.count(), 1);
        check("深度计算-单节点", single.depth(), 1);
    }

    // ==================== 遍历测试 ====================

    private static void testTraversalPreorder() {
        tree t = new tree("1{2,3{4,5},6}");
        check("先序遍历-多层树", t.traversal_preorder(), new int[]{1, 2, 3, 4, 5, 6});

        tree single = new tree(5);
        check("先序遍历-单节点", single.traversal_preorder(), new int[]{5});
    }

    private static void testTraversalPostorder() {
        tree t = new tree("1{2,3{4,5},6}");
        check("后序遍历-多层树", t.traversal_postorder(), new int[]{2, 4, 5, 3, 6, 1});

        tree single = new tree(5);
        check("后序遍历-单节点", single.traversal_postorder(), new int[]{5});
    }

    private static void testTraversalLevelorder() {
        tree t = new tree("1{2,3{4,5},6}");
        check("层序遍历-多层树", t.traversal_levelorder(), new int[]{1, 2, 3, 6, 4, 5});

        tree single = new tree(5);
        check("层序遍历-单节点", single.traversal_levelorder(), new int[]{5});
    }

    // ==================== 插入测试 ====================

    private static void testInsertTo() {
        tree t = new tree(1);

        int r1 = t.insert_to(2, 1);
        check("插入-向根插入第一个子结点-返回值", r1, 2);
        check("插入-向根插入第一个子结点-先序", t.traversal_preorder(), new int[]{1, 2});

        int r2 = t.insert_to(3, 1);
        check("插入-向根插入第二个子结点-返回值", r2, 3);
        check("插入-向根插入第二个子结点-先序", t.traversal_preorder(), new int[]{1, 2, 3});

        int r3 = t.insert_to(4, 2);
        check("插入-向子结点插入-返回值", r3, 4);
        check("插入-向子结点插入-先序", t.traversal_preorder(), new int[]{1, 2, 4, 3});

        int r4 = t.insert_to(9, 99);
        check("插入-目标不存在-返回值", r4, Integer.MIN_VALUE);
        check("插入-目标不存在-结点数不变", t.count(), 4);
    }

    // ==================== 删除测试 ====================

    private static void testRemove() {
        tree t = new tree("1{2,3}");
        int r1 = t.remove(2);
        check("删除-叶子结点-返回值", r1, 2);
        check("删除-叶子结点-先序", t.traversal_preorder(), new int[]{1, 3});
        check("删除-叶子结点-结点数", t.count(), 2);

        tree t2 = new tree("1{2{3,4},5}");
        int r2 = t2.remove(2);
        check("删除-中间子树-返回值", r2, 2);
        check("删除-中间子树-先序", t2.traversal_preorder(), new int[]{1, 5});
        check("删除-中间子树-结点数", t2.count(), 2);

        tree t3 = new tree("1{2,3}");
        int r3 = t3.remove(1);
        check("删除-根结点-返回值", r3, 1);
        check("删除-根结点-元素", t3.element, 0);
        check("删除-根结点-子结点为空", t3.child, null);
        check("删除-根结点-兄弟为空", t3.next, null);
        check("删除-根结点-结点数", t3.count(), 1);

        tree t4 = new tree("1{2,3,2,4}");
        int r4 = t4.remove(2);
        check("删除-多个相同元素-返回值", r4, 2);
        check("删除-多个相同元素-先序", t4.traversal_preorder(), new int[]{1, 3, 2, 4});

        int r5 = t4.remove(99);
        check("删除-不存在的元素-返回值", r5, Integer.MIN_VALUE);
    }

    // ==================== 字符串表示测试 ====================

    private static void testToString() {
        tree t = new tree("12{34,56{78,90},100}");
        check("字符串表示-多层树", t.toString(), "12{34,56{78,90},100}");

        tree single = new tree(7);
        check("字符串表示-单节点", single.toString(), "7");
    }

    // ==================== 压力测试 ====================

    private static void testPressureDeepTree() {
        int depth = 1000;
        tree t = new tree(0);
        int current = 0;
        for (int i = 1; i < depth; i++) {
            t.insert_to(i, current);
            current = i;
        }

        check("压力测试-深度树-结点数", t.count(), depth);
        check("压力测试-深度树-深度", t.depth(), depth);
        check("压力测试-深度树-先序首元素", t.traversal_preorder()[0], 0);
        check("压力测试-深度树-先序尾元素", t.traversal_preorder()[depth - 1], depth - 1);
        check("压力测试-深度树-后序首元素", t.traversal_postorder()[0], depth - 1);
        check("压力测试-深度树-后序尾元素", t.traversal_postorder()[depth - 1], 0);
        check("压力测试-深度树-层序长度", t.traversal_levelorder().length, depth);
    }

    private static void testPressureWideTree() {
        int width = 10000;
        StringBuilder sb = new StringBuilder("0{");
        for (int i = 1; i <= width; i++) {
            if (i > 1) {
                sb.append(",");
            }
            sb.append(i);
        }
        sb.append("}");

        tree t = new tree(sb.toString());

        check("压力测试-宽树-结点数", t.count(), width + 1);
        check("压力测试-宽树-深度", t.depth(), 2);
        check("压力测试-宽树-先序首元素", t.traversal_preorder()[0], 0);
        check("压力测试-宽树-先序第二个元素", t.traversal_preorder()[1], 1);
        check("压力测试-宽树-先序尾元素", t.traversal_preorder()[width], width);
        check("压力测试-宽树-后序首元素", t.traversal_postorder()[0], 1);
        check("压力测试-宽树-后序尾元素", t.traversal_postorder()[width], 0);
        check("压力测试-宽树-层序长度", t.traversal_levelorder().length, width + 1);
    }

    private static void testPressureInsertMany() {
        int count = 2000;
        tree t = new tree(0);
        for (int i = 1; i <= count; i++) {
            t.insert_to(i, 0);
        }

        check("压力测试-插入大量子结点-结点数", t.count(), count + 1);
        check("压力测试-插入大量子结点-深度", t.depth(), 2);

        tree p = t.child;
        int last = p.element;
        while (p.next != null) {
            p = p.next;
            last = p.element;
        }
        check("压力测试-插入大量子结点-末尾元素", last, count);
    }

    private static void testPressureRemoveMany() {
        int count = 2000;
        tree t = new tree(0);
        for (int i = 1; i <= count; i++) {
            t.insert_to(i, 0);
        }
        for (int i = 1; i <= count; i++) {
            t.remove(i);
        }

        check("压力测试-删除大量结点-结点数", t.count(), 1);
        check("压力测试-删除大量结点-子结点为空", t.child, null);
    }
}