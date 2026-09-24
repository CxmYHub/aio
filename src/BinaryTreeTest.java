import java.util.Arrays;

import aio.datastructure.BinaryTree;
/**
 * BinaryTree 类的测试类。
 * 通过 main 方法调用每个测试用例，验证二叉树类的功能。
 */
public class BinaryTreeTest {

    // ANSI 颜色
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String RESET = "\u001B[0m";

    // ==================== 辅助断言方法 ====================

    static void checkInt(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println(GREEN + "[AC] " + testName + " 实际输出 " + actual + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 " + expected + " 实际输出 " + actual + RESET);
        }
    }

    static void checkBoolean(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            System.out.println(GREEN + "[AC] " + testName + " 实际输出 " + actual + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 " + expected + " 实际输出 " + actual + RESET);
        }
    }

    static void checkString(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println(GREEN + "[AC] " + testName + " 实际输出 " + actual + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 " + expected + " 实际输出 " + actual + RESET);
        }
    }

    static void checkArray(String testName, int[] expected, int[] actual) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(GREEN + "[AC] " + testName + " 实际输出 " + Arrays.toString(actual) + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 " + Arrays.toString(expected) + " 实际输出 " + Arrays.toString(actual) + RESET);
        }
    }

    // 压力测试专用：不输出完整数组，避免刷屏
    static void checkArrayBrief(String testName, int[] expected, int[] actual) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(GREEN + "[AC] " + testName + " 实际输出 数组长度 " + actual.length + " 内容一致" + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 数组长度 " + expected.length + " 实际输出 数组长度 " + actual.length + RESET);
        }
    }

    // ==================== 完美二叉树序列生成 ====================

    static int[] perfectLevelOrder(int h) {
        int n = (1 << h) - 1;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    static void fillPreorder(int[] arr, int[] idx, int node, int n) {
        if (node > n) return;
        arr[idx[0]++] = node;
        fillPreorder(arr, idx, node * 2, n);
        fillPreorder(arr, idx, node * 2 + 1, n);
    }

    static int[] perfectPreorder(int h) {
        int n = (1 << h) - 1;
        int[] arr = new int[n];
        int[] idx = {0};
        fillPreorder(arr, idx, 1, n);
        return arr;
    }

    static void fillInorder(int[] arr, int[] idx, int node, int n) {
        if (node > n) return;
        fillInorder(arr, idx, node * 2, n);
        arr[idx[0]++] = node;
        fillInorder(arr, idx, node * 2 + 1, n);
    }

    static int[] perfectInorder(int h) {
        int n = (1 << h) - 1;
        int[] arr = new int[n];
        int[] idx = {0};
        fillInorder(arr, idx, 1, n);
        return arr;
    }

    static void fillPostorder(int[] arr, int[] idx, int node, int n) {
        if (node > n) return;
        fillPostorder(arr, idx, node * 2, n);
        fillPostorder(arr, idx, node * 2 + 1, n);
        arr[idx[0]++] = node;
    }

    static int[] perfectPostorder(int h) {
        int n = (1 << h) - 1;
        int[] arr = new int[n];
        int[] idx = {0};
        fillPostorder(arr, idx, 1, n);
        return arr;
    }

    // ==================== 测试用例 ====================

    static void testStringSingleLetter() {
        BinaryTree tree = new BinaryTree("A");
        checkInt("字符串构造单节点字母-元素", 65, tree.element);
        checkInt("字符串构造单节点字母-结点数", 1, tree.count());
        checkInt("字符串构造单节点字母-深度", 1, tree.depth());
        checkArray("字符串构造单节点字母-先序遍历", new int[]{65}, tree.traversalPreorder());
        checkArray("字符串构造单节点字母-中序遍历", new int[]{65}, tree.traversalInorder());
        checkArray("字符串构造单节点字母-后序遍历", new int[]{65}, tree.traversalPostorder());
        checkArray("字符串构造单节点字母-层序遍历", new int[]{65}, tree.traversalLevelorder());
        checkString("字符串构造单节点字母-字符串表示", "65", tree.toString());
    }

    static void testStringSingleNumber() {
        BinaryTree tree = new BinaryTree("123");
        checkInt("字符串构造单节点数字-元素", 123, tree.element);
        checkInt("字符串构造单节点数字-结点数", 1, tree.count());
        checkInt("字符串构造单节点数字-深度", 1, tree.depth());
        checkArray("字符串构造单节点数字-先序遍历", new int[]{123}, tree.traversalPreorder());
        checkArray("字符串构造单节点数字-中序遍历", new int[]{123}, tree.traversalInorder());
        checkArray("字符串构造单节点数字-后序遍历", new int[]{123}, tree.traversalPostorder());
        checkArray("字符串构造单节点数字-层序遍历", new int[]{123}, tree.traversalLevelorder());
        checkString("字符串构造单节点数字-字符串表示", "123", tree.toString());
    }

    static void testStringFullTree() {
        BinaryTree tree = new BinaryTree("A{B{D,E},C{F,G}}");
        int[] pre = {65, 66, 68, 69, 67, 70, 71};
        int[] in = {68, 66, 69, 65, 70, 67, 71};
        int[] post = {68, 69, 66, 70, 71, 67, 65};
        int[] level = {65, 66, 67, 68, 69, 70, 71};
        checkInt("字符串构造完整树-元素", 65, tree.element);
        checkInt("字符串构造完整树-结点数", 7, tree.count());
        checkInt("字符串构造完整树-深度", 3, tree.depth());
        checkArray("字符串构造完整树-先序遍历", pre, tree.traversalPreorder());
        checkArray("字符串构造完整树-中序遍历", in, tree.traversalInorder());
        checkArray("字符串构造完整树-后序遍历", post, tree.traversalPostorder());
        checkArray("字符串构造完整树-层序遍历", level, tree.traversalLevelorder());
        checkString("字符串构造完整树-字符串表示", "65{66{68,69},67{70,71}}", tree.toString());
    }

    static void testStringLeftOnly() {
        BinaryTree tree = new BinaryTree("A{B{D,E}}");
        checkInt("字符串构造仅有左子树-元素", 65, tree.element);
        checkInt("字符串构造仅有左子树-结点数", 4, tree.count());
        checkInt("字符串构造仅有左子树-深度", 3, tree.depth());
        checkArray("字符串构造仅有左子树-先序遍历", new int[]{65, 66, 68, 69}, tree.traversalPreorder());
        checkArray("字符串构造仅有左子树-中序遍历", new int[]{68, 66, 69, 65}, tree.traversalInorder());
        checkArray("字符串构造仅有左子树-后序遍历", new int[]{68, 69, 66, 65}, tree.traversalPostorder());
        checkArray("字符串构造仅有左子树-层序遍历", new int[]{65, 66, 68, 69}, tree.traversalLevelorder());
        checkString("字符串构造仅有左子树-字符串表示", "65{66{68,69}}", tree.toString());
    }

    static void testStringRightOnly() {
        BinaryTree tree = new BinaryTree("A{,C{F,G}}");
        checkInt("字符串构造仅有右子树-元素", 65, tree.element);
        checkInt("字符串构造仅有右子树-结点数", 4, tree.count());
        checkInt("字符串构造仅有右子树-深度", 3, tree.depth());
        checkArray("字符串构造仅有右子树-先序遍历", new int[]{65, 67, 70, 71}, tree.traversalPreorder());
        checkArray("字符串构造仅有右子树-中序遍历", new int[]{65, 70, 67, 71}, tree.traversalInorder());
        checkArray("字符串构造仅有右子树-后序遍历", new int[]{70, 71, 67, 65}, tree.traversalPostorder());
        checkArray("字符串构造仅有右子树-层序遍历", new int[]{65, 67, 70, 71}, tree.traversalLevelorder());
        checkString("字符串构造仅有右子树-字符串表示", "65{,67{70,71}}", tree.toString());
    }

    static void testStringZero() {
        BinaryTree tree = new BinaryTree("0");
        checkInt("字符串构造数字0-元素", 0, tree.element);
        checkInt("字符串构造数字0-结点数", 1, tree.count());
        checkInt("字符串构造数字0-深度", 1, tree.depth());
        checkArray("字符串构造数字0-先序遍历", new int[]{0}, tree.traversalPreorder());
        checkString("字符串构造数字0-字符串表示", "0", tree.toString());
    }

    static void testPreInConstruct() {
        int[] pre = {1, 2, 4, 5, 3, 6, 7};
        int[] in = {4, 2, 5, 1, 6, 3, 7};
        int[] post = {4, 5, 2, 6, 7, 3, 1};
        int[] level = {1, 2, 3, 4, 5, 6, 7};
        BinaryTree tree = new BinaryTree(pre, in);
        checkInt("先序中序构造完整树-结点数", 7, tree.count());
        checkInt("先序中序构造完整树-深度", 3, tree.depth());
        checkArray("先序中序构造完整树-先序遍历", pre, tree.traversalPreorder());
        checkArray("先序中序构造完整树-中序遍历", in, tree.traversalInorder());
        checkArray("先序中序构造完整树-后序遍历", post, tree.traversalPostorder());
        checkArray("先序中序构造完整树-层序遍历", level, tree.traversalLevelorder());
    }

    static void testInPostConstruct() {
        int[] in = {4, 2, 5, 1, 6, 3, 7};
        int[] post = {4, 5, 2, 6, 7, 3, 1};
        int[] pre = {1, 2, 4, 5, 3, 6, 7};
        int[] level = {1, 2, 3, 4, 5, 6, 7};
        BinaryTree tree = new BinaryTree(in, post, 0);
        checkInt("中序后序构造完整树-结点数", 7, tree.count());
        checkInt("中序后序构造完整树-深度", 3, tree.depth());
        checkArray("中序后序构造完整树-先序遍历", pre, tree.traversalPreorder());
        checkArray("中序后序构造完整树-中序遍历", in, tree.traversalInorder());
        checkArray("中序后序构造完整树-后序遍历", post, tree.traversalPostorder());
        checkArray("中序后序构造完整树-层序遍历", level, tree.traversalLevelorder());
    }

    static void testPreInSkewLeft() {
        int[] pre = {1, 2, 3, 4};
        int[] in = {4, 3, 2, 1};
        int[] post = {4, 3, 2, 1};
        int[] level = {1, 2, 3, 4};
        BinaryTree tree = new BinaryTree(pre, in);
        checkInt("先序中序构造左斜树-结点数", 4, tree.count());
        checkInt("先序中序构造左斜树-深度", 4, tree.depth());
        checkArray("先序中序构造左斜树-先序遍历", pre, tree.traversalPreorder());
        checkArray("先序中序构造左斜树-中序遍历", in, tree.traversalInorder());
        checkArray("先序中序构造左斜树-后序遍历", post, tree.traversalPostorder());
        checkArray("先序中序构造左斜树-层序遍历", level, tree.traversalLevelorder());
    }

    static void testInPostSkewRight() {
        int[] in = {1, 2, 3, 4};
        int[] post = {4, 3, 2, 1};
        int[] pre = {1, 2, 3, 4};
        int[] level = {1, 2, 3, 4};
        BinaryTree tree = new BinaryTree(in, post, 0);
        checkInt("中序后序构造右斜树-结点数", 4, tree.count());
        checkInt("中序后序构造右斜树-深度", 4, tree.depth());
        checkArray("中序后序构造右斜树-先序遍历", pre, tree.traversalPreorder());
        checkArray("中序后序构造右斜树-中序遍历", in, tree.traversalInorder());
        checkArray("中序后序构造右斜树-后序遍历", post, tree.traversalPostorder());
        checkArray("中序后序构造右斜树-层序遍历", level, tree.traversalLevelorder());
    }

    static void testInput() {
        BinaryTree tree = new BinaryTree("A");
        int parent1 = tree.input(66);
        int parent2 = tree.input(67);
        int parent3 = tree.input(68);
        checkInt("input插入第一个-父结点", 65, parent1);
        checkInt("input插入第二个-父结点", 65, parent2);
        checkInt("input插入第三个-父结点", 67, parent3);
        checkInt("input插入后结点数", 4, tree.count());
        checkArray("input插入后层序遍历", new int[]{65, 66, 67, 68}, tree.traversalLevelorder());
    }

    static void testRemoveNonRoot() {
        BinaryTree tree = new BinaryTree("A{B{D,E},C{F,G}}");
        int removed = tree.remove('D');
        checkInt("remove删除叶子-返回", 68, removed);
        checkInt("remove删除叶子后结点数", 6, tree.count());
        checkArray("remove删除叶子后先序遍历", new int[]{65, 66, 69, 67, 70, 71}, tree.traversalPreorder());
    }

    static void testRemoveRoot() {
        BinaryTree tree = new BinaryTree("A{B,C}");
        int removed = tree.remove('A');
        checkInt("remove删除根-返回", 65, removed);
        checkInt("remove删除根后结点数", 1, tree.count());
        checkInt("remove删除根后元素", 0, tree.element);
        checkArray("remove删除根后先序遍历", new int[]{0}, tree.traversalPreorder());
    }

    static void testRemoveNotFound() {
        BinaryTree tree = new BinaryTree("A{B,C}");
        int removed = tree.remove(100);
        checkInt("remove不存在元素-返回", Integer.MIN_VALUE, removed);
        checkInt("remove不存在元素后结点数", 3, tree.count());
    }

    static void testInvert() {
        BinaryTree tree = new BinaryTree("A{B{D,E},C{F,G}}");
        tree.invert();
        checkArray("invert后先序遍历", new int[]{65, 67, 71, 70, 66, 69, 68}, tree.traversalPreorder());
        checkArray("invert后中序遍历", new int[]{71, 67, 70, 65, 69, 66, 68}, tree.traversalInorder());
        checkArray("invert后后序遍历", new int[]{71, 70, 67, 69, 68, 66, 65}, tree.traversalPostorder());
        checkArray("invert后层序遍历", new int[]{65, 67, 66, 71, 70, 69, 68}, tree.traversalLevelorder());

        tree.invert();
        checkArray("invert两次后先序遍历", new int[]{65, 66, 68, 69, 67, 70, 71}, tree.traversalPreorder());
    }

    static void testEquals() {
        BinaryTree tree1 = new BinaryTree("A{B{D,E},C{F,G}}");
        BinaryTree tree2 = new BinaryTree("A{B{D,E},C{F,G}}");
        BinaryTree tree3 = new BinaryTree("A{B{D,E},C{F}}");
        BinaryTree tree4 = new BinaryTree("A{B{D,E},C{F,G}}");
        tree4.element = 66;
        BinaryTree tree5 = new BinaryTree("A{B{D,E},C{F,G}}");
        tree5.invert();

        checkBoolean("equals相同树", true, tree1.equals(tree2));
        checkBoolean("equals不同结构", false, tree1.equals(tree3));
        checkBoolean("equals不同元素", false, tree1.equals(tree4));
        checkBoolean("equals镜像不同", false, tree1.equals(tree5));
        checkBoolean("equals与null", false, tree1.equals(null));
        checkBoolean("equals与非二叉树", false, tree1.equals("A"));
    }

    static void testToString() {
        checkString("toString完整树", "65{66{68,69},67{70,71}}", new BinaryTree("A{B{D,E},C{F,G}}").toString());
        checkString("toString左空右非空", "65{,67{70,71}}", new BinaryTree("A{,C{F,G}}").toString());
        checkString("toString右空左非空", "65{66{68,69}}", new BinaryTree("A{B{D,E}}").toString());
        checkString("toString单节点", "65", new BinaryTree("A").toString());
    }

    static void testPressure() {
        int h = 10;
        int n = (1 << h) - 1;
        int[] pre = perfectPreorder(h);
        int[] in = perfectInorder(h);
        int[] post = perfectPostorder(h);
        int[] level = perfectLevelOrder(h);

        BinaryTree tree = new BinaryTree(pre, in);
        checkInt("压力测试-结点数", n, tree.count());
        checkInt("压力测试-深度", h, tree.depth());
        checkArrayBrief("压力测试-先序遍历", pre, tree.traversalPreorder());
        checkArrayBrief("压力测试-中序遍历", in, tree.traversalInorder());
        checkArrayBrief("压力测试-后序遍历", post, tree.traversalPostorder());
        checkArrayBrief("压力测试-层序遍历", level, tree.traversalLevelorder());

        BinaryTree tree2 = new BinaryTree(pre, in);
        checkBoolean("压力测试-相同构造equals", true, tree.equals(tree2));

        BinaryTree tree3 = new BinaryTree(in, post, 0);
        checkBoolean("压力测试-中后构造equals", true, tree.equals(tree3));
    }

    // ==================== 主方法 ====================

    public static void main(String[] args) {
        testStringSingleLetter();
        testStringSingleNumber();
        testStringFullTree();
        testStringLeftOnly();
        testStringRightOnly();
        testStringZero();
        testPreInConstruct();
        testInPostConstruct();
        testPreInSkewLeft();
        testInPostSkewRight();
        testInput();
        testRemoveNonRoot();
        testRemoveRoot();
        testRemoveNotFound();
        testInvert();
        testEquals();
        testToString();
        testPressure();
    }
}