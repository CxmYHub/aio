import tools.data_structure.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * binary_search_tree 类的全面测试，包含边界条件和压力测试。
 */
public class binary_search_tree_test {

    // ANSI 颜色
    private static final String GREEN = "\u001B[32m";
    private static final String RED   = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    /**
     * 比较两个 int 数组是否相等
     */
    private static boolean arrayEquals(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * 格式化 int 数组为字符串
     */
    private static String arrStr(int[] a) {
        return Arrays.toString(a);
    }

    /**
     * 检查条件并输出结果
     */
    private static void check(boolean condition, String testName, String expected, String actual) {
        if (condition) {
            System.out.println(GREEN + "[AC] " + testName + " " + actual + RESET);
        } else {
            System.out.println(RED + "[WA] " + testName + " 期望输出 " + expected + " 实际输出 " + actual + RESET);
        }
    }

    // ---------- 测试用例 ----------

    /** 空树测试 */
    static void testEmptyTree() {
        binary_search_tree tree = new binary_search_tree();
        int[] traversal = tree.traversal();
        check(traversal.length == 0, "空树遍历", "[]", arrStr(traversal));

        String str = tree.toString();
        check("".equals(str), "空树toString", "\"\"", "\"" + str + "\"");

        int depth = tree.get_depth(10);
        check(depth == Integer.MIN_VALUE, "空树查找深度", Integer.toString(Integer.MIN_VALUE), Integer.toString(depth));

        boolean removed = tree.remove(10);
        check(!removed, "空树删除", "false", Boolean.toString(removed));
    }

    /** 单结点测试 */
    static void testSingleNode() {
        binary_search_tree tree = new binary_search_tree();
        boolean inserted = tree.input(5);
        check(inserted, "插入单个元素", "true", Boolean.toString(inserted));

        int[] trav = tree.traversal();
        check(arrayEquals(trav, new int[]{5}), "单结点遍历", "[5]", arrStr(trav));

        String str = tree.toString();
        check("5".equals(str), "单结点toString", "5", str);

        int depth = tree.get_depth(5);
        check(depth == 0, "单结点深度", "0", Integer.toString(depth));

        // 插入重复元素应失败
        boolean dupInsert = tree.input(5);
        check(!dupInsert, "插入重复元素", "false", Boolean.toString(dupInsert));
        check(arrayEquals(tree.traversal(), new int[]{5}), "插入重复后遍历不变", "[5]", arrStr(tree.traversal()));
    }

    /** 插入多个元素并检查遍历和字符串表示 */
    static void testMultiNodeTree() {
        binary_search_tree tree = new binary_search_tree(4, 2, 6, 1, 3, 5, 7);
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        int[] trav = tree.traversal();
        check(arrayEquals(trav, expected), "多结点遍历", arrStr(expected), arrStr(trav));
        String expectedStr = "4{2{1,3},6{5,7}}";
        String actualStr = tree.toString();
        check(expectedStr.equals(actualStr), "多结点toString", expectedStr, actualStr);
    }

    /** get_depth 详细测试 */
    static void testGetDepth() {
        binary_search_tree tree = new binary_search_tree(4, 2, 6, 1, 3, 5, 7);
        check(tree.get_depth(4) == 0, "根结点深度", "0", Integer.toString(tree.get_depth(4)));
        check(tree.get_depth(2) == 1, "深度测试 2", "1", Integer.toString(tree.get_depth(2)));
        check(tree.get_depth(6) == 1, "深度测试 6", "1", Integer.toString(tree.get_depth(6)));
        check(tree.get_depth(1) == 2, "深度测试 1", "2", Integer.toString(tree.get_depth(1)));
        check(tree.get_depth(3) == 2, "深度测试 3", "2", Integer.toString(tree.get_depth(3)));
        check(tree.get_depth(5) == 2, "深度测试 5", "2", Integer.toString(tree.get_depth(5)));
        check(tree.get_depth(7) == 2, "深度测试 7", "2", Integer.toString(tree.get_depth(7)));
        check(tree.get_depth(100) == Integer.MIN_VALUE, "查找不存在元素深度", Integer.toString(Integer.MIN_VALUE), Integer.toString(tree.get_depth(100)));
    }

    /** 删除叶子结点 */
    static void testRemoveLeaf() {
        binary_search_tree tree = new binary_search_tree(4, 2, 6, 1, 3, 5, 7);
        boolean removed = tree.remove(1);
        check(removed, "删除叶子结点 1", "true", Boolean.toString(removed));
        int[] expected = {2, 3, 4, 5, 6, 7};
        check(arrayEquals(tree.traversal(), expected), "删除叶子后遍历", arrStr(expected), arrStr(tree.traversal()));
    }

    /** 删除有一个子结点的结点 */
    static void testRemoveOneChild() {
        // 构造树：5,3,7,2, 其中2是3的左孩子，删除3（有一个左子2）
        binary_search_tree tree = new binary_search_tree(5, 3, 7, 2);
        boolean removed = tree.remove(3);
        check(removed, "删除单子结点 3", "true", Boolean.toString(removed));
        int[] expected = {2, 5, 7};
        check(arrayEquals(tree.traversal(), expected), "删除单子结点后遍历", arrStr(expected), arrStr(tree.traversal()));

        // 再删除7（无子结点）
        removed = tree.remove(7);
        check(removed, "删除叶子结点 7", "true", Boolean.toString(removed));
        expected = new int[]{2, 5};
        check(arrayEquals(tree.traversal(), expected), "再次删除叶子后遍历", arrStr(expected), arrStr(tree.traversal()));
    }

    /** 删除有两个子结点的结点 */
    static void testRemoveTwoChildren() {
        binary_search_tree tree = new binary_search_tree(4, 2, 6, 1, 3, 5, 7);
        boolean removed = tree.remove(4); // 根有两个孩子
        check(removed, "删除双子根结点", "true", Boolean.toString(removed));
        int[] expected = {1, 2, 3, 5, 6, 7};
        check(arrayEquals(tree.traversal(), expected), "删除根后遍历", arrStr(expected), arrStr(tree.traversal()));
    }

    /** 删除根结点的各种情况 */
    static void testRemoveRoot() {
        // 情况：根只有左子树
        binary_search_tree tree = new binary_search_tree(3, 2, 1);
        tree.remove(3);
        check(arrayEquals(tree.traversal(), new int[]{1, 2}), "删除只有左子树的根", "[1, 2]", arrStr(tree.traversal()));

        // 只有右子树
        tree = new binary_search_tree(1, 2, 3);
        tree.remove(1);
        check(arrayEquals(tree.traversal(), new int[]{2, 3}), "删除只有右子树的根", "[2, 3]", arrStr(tree.traversal()));

        // 根为叶子（单结点树）
        tree = new binary_search_tree(42);
        tree.remove(42);
        check(tree.traversal().length == 0, "删除单结点根", "[]", arrStr(tree.traversal()));
    }

    /** 删除不存在的元素 */
    static void testRemoveNonExistent() {
        binary_search_tree tree = new binary_search_tree(5, 3, 7);
        boolean removed = tree.remove(100);
        check(!removed, "删除不存在的元素", "false", Boolean.toString(removed));
        check(arrayEquals(tree.traversal(), new int[]{3, 5, 7}), "删除不存在元素后遍历不变", "[3, 5, 7]", arrStr(tree.traversal()));
    }

    /** 可变参数构造方法，包括重复元素 */
    static void testConstructorWithArgs() {
        // 含重复元素，应只保留第一次出现的
        binary_search_tree tree = new binary_search_tree(5, 3, 7, 3, 5, 8);
        int[] expected = {3, 5, 7, 8}; // 按插入顺序？代码中重复直接跳过，所以元素顺序为 5,3,7,8
        int[] trav = tree.traversal();
        check(arrayEquals(trav, expected), "构造含重复元素遍历", arrStr(expected), arrStr(trav));
    }

    /** input_more 方法测试 */
    static void testInputMore() {
        binary_search_tree tree = new binary_search_tree(); // 空树
        int duplicate = tree.input_more(10, 5, 15, 5, 10, 20);
        check(duplicate == 2, "批量插入重复计数", "2", Integer.toString(duplicate));
        int[] expected = {5, 10, 15, 20};
        check(arrayEquals(tree.traversal(), expected), "批量插入后遍历", arrStr(expected), arrStr(tree.traversal()));
    }

    /** 退化树测试（全左斜和全右斜） */
    static void testDegeneratedTree() {
        // 右斜树：插入递增序列
        binary_search_tree tree = new binary_search_tree();
        int N = 100;
        for (int i = 1; i <= N; i++) {
            tree.input(i);
        }
        int[] trav = tree.traversal();
        int[] expected = new int[N];
        for (int i = 0; i < N; i++) expected[i] = i + 1;
        check(arrayEquals(trav, expected), "右斜树遍历", "递增序列", arrStr(trav));

        // 深度：每个结点深度 = element - 1
        for (int i = 1; i <= N; i++) {
            if (tree.get_depth(i) != i - 1) {
                check(false, "右斜树深度(" + i + ")", Integer.toString(i-1), Integer.toString(tree.get_depth(i)));
                return;
            }
        }
        System.out.println(GREEN + "[AC] 右斜树全部深度正确" + RESET);

        // 左斜树：递减插入
        tree = new binary_search_tree();
        for (int i = N; i >= 1; i--) {
            tree.input(i);
        }
        trav = tree.traversal();
        check(arrayEquals(trav, expected), "左斜树遍历", "递增序列", arrStr(trav));
    }

    /** 压力测试：大量随机插入与删除交替 */
    static void stressTest() {
        binary_search_tree tree = new binary_search_tree();
        HashSet<Integer> set = new HashSet<>();
        Random rand = new Random(20230730);
        int operations = 5000;
        int insertCount = 0, removeCount = 0;

        for (int i = 0; i < operations; i++) {
            if (rand.nextBoolean() || set.size() < 50) {
                // 插入
                int val = rand.nextInt(2000);
                boolean inserted = tree.input(val);
                boolean setInserted = set.add(val);
                if (inserted != setInserted) {
                    check(false, "压力测试插入一致性 " + val, Boolean.toString(setInserted), Boolean.toString(inserted));
                    return;
                }
                insertCount++;
            } else {
                // 删除
                int idx = rand.nextInt(set.size());
                int val = (int) set.toArray()[idx];
                boolean removed = tree.remove(val);
                boolean setRemoved = set.remove(val);
                if (removed != setRemoved) {
                    check(false, "压力测试删除一致性 " + val, Boolean.toString(setRemoved), Boolean.toString(removed));
                    return;
                }
                removeCount++;
            }
        }

        // 最终验证
        int[] trav = tree.traversal();
        int[] expected = set.stream().mapToInt(Integer::intValue).sorted().toArray();
        boolean traversalOk = arrayEquals(trav, expected);
        check(traversalOk, "压力测试最终遍历", arrStr(expected), arrStr(trav));
        if (traversalOk) {
            System.out.println(GREEN + "[AC] 压力测试通过（插入" + insertCount + "次，删除" + removeCount + "次，剩余" + set.size() + "个元素）" + RESET);
        }
    }

    public static void main(String[] args) {
        System.out.println("开始 binary_search_tree 测试...");
        testEmptyTree();
        testSingleNode();
        testMultiNodeTree();
        testGetDepth();
        testRemoveLeaf();
        testRemoveOneChild();
        testRemoveTwoChildren();
        testRemoveRoot();
        testRemoveNonExistent();
        testConstructorWithArgs();
        testInputMore();
        testDegeneratedTree();
        stressTest();
        System.out.println("测试结束。");
    }
}