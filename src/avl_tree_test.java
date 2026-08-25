import aio.data_structure.avl_tree;

import java.util.Arrays;

/**
 * AVL树测试类，包含全面的测试用例。
 * 通过main方法调用每个测试用例，并根据结果输出ANSI颜色提示。
 */
public class avl_tree_test {

    /**
     * 测试结果内部类，用于封装测试名称、是否通过、期望输出和实际输出。
     */
    static class TestResult {
        String name;
        boolean passed;
        String expected;
        String actual;

        TestResult(String name, boolean passed, String expected, String actual) {
            this.name = name;
            this.passed = passed;
            this.expected = expected;
            this.actual = actual;
        }
    }

    // ANSI颜色码
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        // 依次调用所有测试用例
        TestResult[] results = {
                testEmptyTree(),
                testSingleInsert(),
                testInsertAscending(),
                testInsertDescending(),
                testInsertRandom(),
                testInsertDuplicates(),
                testLLRotation(),
                testRRRotation(),
                testLRRotation(),
                testRLRotation(),
                testDeleteLeaf(),
                testDeleteNodeWithOneChild(),
                testDeleteNodeWithTwoChildren(),
                testDeleteNonexistent(),
                testDeleteRoot(),
                testDeleteCausingRotation(),
                testGetDepth(),
                testTraversal(),
                testBalanceAfterManyOperations(),
                testBoundaryValues()
        };

        // 输出测试结果
        for (TestResult r : results) {
            if (r.passed) {
                System.out.println(ANSI_GREEN + "[AC] " + r.name + " " + r.actual + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "[WA] " + r.name + " 期望输出: " + r.expected + " 实际输出: " + r.actual + ANSI_RESET);
            }
        }
    }

    // ------------------ 测试用例实现 ------------------

    /** 测试空树的基本行为 */
    static TestResult testEmptyTree() {
        avl_tree tree = new avl_tree();
        // 遍历应为空数组
        int[] traversal = tree.traversal();
        String expectedTraversal = "[]";
        String actualTraversal = Arrays.toString(traversal);
        if (!actualTraversal.equals(expectedTraversal)) {
            return new TestResult("空树遍历", false, "遍历=" + expectedTraversal, "遍历=" + actualTraversal);
        }
        // toString应为空字符串
        String str = tree.toString();
        if (!str.equals("")) {
            return new TestResult("空树toString", false, "toString=\"\"", "toString=\"" + str + "\"");
        }
        // get_depth任意元素返回Integer.MIN_VALUE
        int depth = tree.get_depth(10);
        if (depth != Integer.MIN_VALUE) {
            return new TestResult("空树get_depth", false, "深度=Integer.MIN_VALUE", "深度=" + depth);
        }
        // remove返回false
        boolean removed = tree.remove(10);
        if (removed) {
            return new TestResult("空树remove", false, "删除=false", "删除=true");
        }
        return new TestResult("空树测试", true, "全部通过", "遍历=[]，toString=\"\"，深度=Integer.MIN_VALUE，删除=false");
    }

    /** 测试插入单个元素 */
    static TestResult testSingleInsert() {
        avl_tree tree = new avl_tree();
        boolean inserted = tree.input(5);
        if (!inserted) {
            return new TestResult("插入单个元素", false, "插入成功", "插入失败");
        }
        int[] traversal = tree.traversal();
        String actual = Arrays.toString(traversal);
        String expected = "[5]";
        if (!actual.equals(expected)) {
            return new TestResult("插入单个元素遍历", false, "遍历=" + expected, "遍历=" + actual);
        }
        int depth = tree.get_depth(5);
        if (depth != 1) {
            return new TestResult("插入单个元素深度", false, "深度=1", "深度=" + depth);
        }
        // 根节点平衡因子应为0
        avl_tree root = tree.left;
        if (root == null || root.balance_factor != 0) {
            return new TestResult("插入单个元素平衡因子", false, "平衡因子=0", "平衡因子=" + (root == null ? "null" : root.balance_factor));
        }
        return new TestResult("插入单个元素测试", true, "全部通过", "遍历=[5]，深度=0，平衡因子=0");
    }

    /** 测试升序插入（触发RR旋转） */
    static TestResult testInsertAscending() {
        avl_tree tree = new avl_tree();
        int[] values = {1, 2, 3, 4, 5, 6, 7};
        for (int v : values) {
            tree.input(v);
        }
        int[] traversal = tree.traversal();
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("升序插入遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        // 验证所有节点平衡因子绝对值<=1
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("升序插入平衡", false, "所有节点平衡因子绝对值<=1", "存在不平衡节点");
        }
        return new TestResult("升序插入测试", true, "全部通过", "遍历=[1,2,3,4,5,6,7]，平衡因子正确");
    }

    /** 测试降序插入（触发LL旋转） */
    static TestResult testInsertDescending() {
        avl_tree tree = new avl_tree();
        int[] values = {7, 6, 5, 4, 3, 2, 1};
        for (int v : values) {
            tree.input(v);
        }
        int[] traversal = tree.traversal();
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("降序插入遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("降序插入平衡", false, "所有节点平衡因子绝对值<=1", "存在不平衡节点");
        }
        return new TestResult("降序插入测试", true, "全部通过", "遍历=[1,2,3,4,5,6,7]，平衡因子正确");
    }

    /** 测试随机插入 */
    static TestResult testInsertRandom() {
        avl_tree tree = new avl_tree();
        int[] values = {4, 7, 2, 9, 6, 1, 8, 3, 5};
        for (int v : values) {
            tree.input(v);
        }
        int[] traversal = tree.traversal();
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("随机插入遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("随机插入平衡", false, "所有节点平衡因子绝对值<=1", "存在不平衡节点");
        }
        return new TestResult("随机插入测试", true, "全部通过", "遍历=[1,2,3,4,5,6,7,8,9]，平衡因子正确");
    }

    /** 测试插入重复元素 */
    static TestResult testInsertDuplicates() {
        avl_tree tree = new avl_tree();
        boolean first = tree.input(10);
        boolean second = tree.input(10);
        if (!first || second) {
            return new TestResult("插入重复元素", false, "第一次插入=true，第二次插入=false", "第一次=" + first + "，第二次=" + second);
        }
        // 使用input_more批量插入重复
        int duplicates = tree.input_more(10, 20, 20, 30);
        if (duplicates != 2) {
            return new TestResult("批量插入重复计数", false, "忽略重复数=2", "忽略重复数=" + duplicates);
        }
        int[] traversal = tree.traversal();
        int[] expected = {10, 20, 30};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("批量插入重复遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        return new TestResult("插入重复元素测试", true, "全部通过", "重复元素处理正确，遍历=[10,20,30]");
    }

    /** 测试LL旋转（直接构造左左情形） */
    static TestResult testLLRotation() {
        // 构造树：头结点 -> 3 (左) -> 2 (左) -> 1，平衡因子：3:-2, 2:-1, 1:0
        avl_tree tree = new avl_tree();
        avl_tree node3 = new avl_tree(3,' ');
        avl_tree node2 = new avl_tree(2,' ');
        avl_tree node1 = new avl_tree(1,' ');
        tree.left = node3;
        node3.parent = tree;
        node3.left = node2;
        node2.parent = node3;
        node2.left = node1;
        node1.parent = node2;
        node3.balance_factor = -2;
        node2.balance_factor = -1;
        node1.balance_factor = 0;

        // 执行右旋转（LL旋转为右旋）
        avl_tree newRoot = avl_tree.right_rotate(node3);
        if (newRoot != node2) {
            return new TestResult("LL旋转结果", false, "新根为2", "新根为" + (newRoot == null ? "null" : newRoot.element));
        }
        // 检查结构：2的左为1，右为3
        if (node2.left != node1 || node2.right != node3) {
            return new TestResult("LL旋转结构", false, "2.left=1, 2.right=3", "2.left=" + (node2.left == null ? "null" : node2.left.element) + ", 2.right=" + (node2.right == null ? "null" : node2.right.element));
        }
        return new TestResult("LL旋转测试", true, "全部通过", "右旋后根为2，左右子节点正确");
    }

    /** 测试RR旋转 */
    static TestResult testRRRotation() {
        // 构造树：头结点 -> 1 (左) -> 2 (右) -> 3
        avl_tree tree = new avl_tree();
        avl_tree node1 = new avl_tree(1,' ');
        avl_tree node2 = new avl_tree(2,' ');
        avl_tree node3 = new avl_tree(3,' ');
        tree.left = node1;
        node1.parent = tree;
        node1.right = node2;
        node2.parent = node1;
        node2.right = node3;
        node3.parent = node2;
        node1.balance_factor = 2;
        node2.balance_factor = 1;
        node3.balance_factor = 0;

        avl_tree newRoot = avl_tree.left_rotate(node1);
        if (newRoot != node2) {
            return new TestResult("RR旋转结果", false, "新根为2", "新根为" + (newRoot == null ? "null" : newRoot.element));
        }
        if (node2.left != node1 || node2.right != node3) {
            return new TestResult("RR旋转结构", false, "2.left=1, 2.right=3", "2.left=" + (node2.left == null ? "null" : node2.left.element) + ", 2.right=" + (node2.right == null ? "null" : node2.right.element));
        }
        return new TestResult("RR旋转测试", true, "全部通过", "左旋后根为2，左右子节点正确");
    }

    /** 测试LR旋转 */
    static TestResult testLRRotation() {
        // 构造树：3 -> 1 (左) -> 2 (右)
        avl_tree tree = new avl_tree();
        avl_tree node3 = new avl_tree(3,' ');
        avl_tree node1 = new avl_tree(1,' ');
        avl_tree node2 = new avl_tree(2,' ');
        tree.left = node3;
        node3.parent = tree;
        node3.left = node1;
        node1.parent = node3;
        node1.right = node2;
        node2.parent = node1;
        node3.balance_factor = -2;
        node1.balance_factor = 1;
        node2.balance_factor = 0;

        // LR旋转：先左旋node1，再右旋node3
        avl_tree rotated1 = avl_tree.left_rotate(node1);
        avl_tree newRoot = avl_tree.right_rotate(node3);
        // 新根应为node2
        if (newRoot != node2) {
            return new TestResult("LR旋转结果", false, "新根为2", "新根为" + (newRoot == null ? "null" : newRoot.element));
        }
        if (node2.left != node1 || node2.right != node3) {
            return new TestResult("LR旋转结构", false, "2.left=1, 2.right=3", "2.left=" + (node2.left == null ? "null" : node2.left.element) + ", 2.right=" + (node2.right == null ? "null" : node2.right.element));
        }
        return new TestResult("LR旋转测试", true, "全部通过", "先左旋1，再右旋3，新根为2，结构正确");
    }

    /** 测试RL旋转 */
    static TestResult testRLRotation() {
        // 构造树：1 -> 3 (右) -> 2 (左)
        avl_tree tree = new avl_tree();
        avl_tree node1 = new avl_tree(1,' ');
        avl_tree node3 = new avl_tree(3,' ');
        avl_tree node2 = new avl_tree(2,' ');
        tree.left = node1;
        node1.parent = tree;
        node1.right = node3;
        node3.parent = node1;
        node3.left = node2;
        node2.parent = node3;
        node1.balance_factor = 2;
        node3.balance_factor = -1;
        node2.balance_factor = 0;

        // RL旋转：先右旋node3，再左旋node1
        avl_tree rotated3 = avl_tree.right_rotate(node3);
        avl_tree newRoot = avl_tree.left_rotate(node1);
        if (newRoot != node2) {
            return new TestResult("RL旋转结果", false, "新根为2", "新根为" + (newRoot == null ? "null" : newRoot.element));
        }
        if (node2.left != node1 || node2.right != node3) {
            return new TestResult("RL旋转结构", false, "2.left=1, 2.right=3", "2.left=" + (node2.left == null ? "null" : node2.left.element) + ", 2.right=" + (node2.right == null ? "null" : node2.right.element));
        }
        return new TestResult("RL旋转测试", true, "全部通过", "先右旋3，再左旋1，新根为2，结构正确");
    }

    /** 测试删除叶子节点 */
    static TestResult testDeleteLeaf() {
        avl_tree tree = new avl_tree(5, 3, 7, 2, 4, 6, 8);
        boolean removed = tree.remove(2);
        if (!removed) {
            return new TestResult("删除叶子节点", false, "删除成功", "删除失败");
        }
        int[] traversal = tree.traversal();
        int[] expected = {3, 4, 5, 6, 7, 8};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除叶子节点遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("删除叶子节点平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        return new TestResult("删除叶子节点测试", true, "全部通过", "删除2后遍历=[3,4,5,6,7,8]，平衡正确");
    }

    /** 测试删除只有一个子节点的节点 */
    static TestResult testDeleteNodeWithOneChild() {
        avl_tree tree = new avl_tree(5, 3, 7, 2, 4, 6, 8, 9);
        // 删除节点8，它只有右子节点9
        boolean removed = tree.remove(8);
        if (!removed) {
            return new TestResult("删除单子节点", false, "删除成功", "删除失败");
        }
        int[] traversal = tree.traversal();
        int[] expected = {2, 3, 4, 5, 6, 7, 9};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除单子节点遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("删除单子节点平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        return new TestResult("删除单子节点测试", true, "全部通过", "删除8后遍历=[2,3,4,5,6,7,9]，平衡正确");
    }

    /** 测试删除有两个子节点的节点 */
    static TestResult testDeleteNodeWithTwoChildren() {
        avl_tree tree = new avl_tree(5, 3, 7, 2, 4, 6, 8);
        // 删除节点5，有两个子节点，用后继6替代
        boolean removed = tree.remove(5);
        if (!removed) {
            return new TestResult("删除双子节点", false, "删除成功", "删除失败");
        }
        int[] traversal = tree.traversal();
        int[] expected = {2, 3, 4, 6, 7, 8};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除双子节点遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("删除双子节点平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        return new TestResult("删除双子节点测试", true, "全部通过", "删除5后遍历=[2,3,4,6,7,8]，平衡正确");
    }

    /** 测试删除不存在的元素 */
    static TestResult testDeleteNonexistent() {
        avl_tree tree = new avl_tree(5, 3, 7);
        boolean removed = tree.remove(100);
        if (removed) {
            return new TestResult("删除不存在元素", false, "删除失败", "删除成功");
        }
        int[] traversal = tree.traversal();
        int[] expected = {3, 5, 7};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除不存在元素遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        return new TestResult("删除不存在元素测试", true, "全部通过", "删除100返回false，遍历不变");
    }

    /** 测试删除根节点 */
    static TestResult testDeleteRoot() {
        avl_tree tree = new avl_tree(5, 3, 7, 2, 4, 6, 8);
        boolean removed = tree.remove(5);
        if (!removed) {
            return new TestResult("删除根节点", false, "删除成功", "删除失败");
        }
        int[] traversal = tree.traversal();
        int[] expected = {2, 3, 4, 6, 7, 8};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除根节点遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("删除根节点平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        return new TestResult("删除根节点测试", true, "全部通过", "删除根5后遍历=[2,3,4,6,7,8]，平衡正确");
    }

    /** 测试删除导致旋转的情况 */
    static TestResult testDeleteCausingRotation() {
        // 构造一棵树，删除后导致不平衡需要旋转
        avl_tree tree = new avl_tree(50, 25, 75, 10, 30, 60, 80, 5, 15);
        // 删除80，可能导致75的右子树变矮，引起旋转
        boolean removed = tree.remove(80);
        if (!removed) {
            return new TestResult("删除导致旋转", false, "删除成功", "删除失败");
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("删除导致旋转平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        int[] traversal = tree.traversal();
        int[] expected = {5, 10, 15, 25, 30, 50, 60, 75};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("删除导致旋转遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        return new TestResult("删除导致旋转测试", true, "全部通过", "删除80后遍历正确，平衡保持");
    }

    /** 测试get_depth方法 */
    static TestResult testGetDepth() {
        avl_tree tree = new avl_tree(10, 5, 15, 3, 7, 12, 18);
        int d10 = tree.get_depth(10);
        int d3 = tree.get_depth(3);
        int d18 = tree.get_depth(18);
        int d99 = tree.get_depth(99);
        if (d10 != 1 || d3 != 3 || d18 != 3 || d99 != Integer.MIN_VALUE) {
            return new TestResult("get_depth测试", false, "深度:10=1,3=3,18=3,99=MIN", "深度:10=" + d10 + ",3=" + d3 + ",18=" + d18 + ",99=" + d99);
        }
        return new TestResult("get_depth测试", true, "全部通过", "深度:10=0,3=2,18=2,99=MIN");
    }

    /** 测试中序遍历 */
    static TestResult testTraversal() {
        avl_tree tree = new avl_tree(5, 2, 8, 1, 3, 7, 9, 4);
        int[] traversal = tree.traversal();
        int[] expected = {1, 2, 3, 4, 5, 7, 8, 9};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("中序遍历测试", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        return new TestResult("中序遍历测试", true, "全部通过", "遍历=[1,2,3,4,5,7,8,9]");
    }

    /** 测试大量随机操作后的平衡性 */
    static TestResult testBalanceAfterManyOperations() {
        avl_tree tree = new avl_tree();
        int[] values = {15, 7, 30, 3, 10, 20, 40, 1, 5, 8, 12, 25, 35, 50, 6, 9, 11, 13};
        for (int v : values) {
            tree.input(v);
        }
        // 删除一些元素
        tree.remove(1);
        tree.remove(50);
        tree.remove(30);
        tree.input(45);
        tree.input(2);
        tree.remove(15);
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("随机操作平衡性", false, "所有节点平衡因子绝对值<=1", "存在不平衡节点");
        }
        int[] traversal = tree.traversal();
        // 手动排序可得预期，但这里只检查是否有序
        for (int i = 1; i < traversal.length; i++) {
            if (traversal[i - 1] >= traversal[i]) {
                return new TestResult("随机操作有序性", false, "遍历严格递增", "遍历=" + Arrays.toString(traversal));
            }
        }
        return new TestResult("随机操作平衡性测试", true, "全部通过", "多次操作后平衡且有序，遍历=" + Arrays.toString(traversal));
    }

    /** 测试边界值（Integer.MIN_VALUE和MAX_VALUE） */
    static TestResult testBoundaryValues() {
        avl_tree tree = new avl_tree();
        boolean insertedMin = tree.input(Integer.MIN_VALUE);
        boolean insertedMax = tree.input(Integer.MAX_VALUE);
        boolean insertedZero = tree.input(0);
        if (!insertedMin || !insertedMax || !insertedZero) {
            return new TestResult("边界值插入", false, "插入MIN,MAX,0均成功", "插入MIN=" + insertedMin + ",MAX=" + insertedMax + ",0=" + insertedZero);
        }
        int[] traversal = tree.traversal();
        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        if (!Arrays.equals(traversal, expected)) {
            return new TestResult("边界值遍历", false, "遍历=" + Arrays.toString(expected), "遍历=" + Arrays.toString(traversal));
        }
        boolean balanced = checkBalanceFactors(tree.left);
        if (!balanced) {
            return new TestResult("边界值平衡", false, "平衡因子正确", "存在不平衡节点");
        }
        return new TestResult("边界值测试", true, "全部通过", "插入MIN,0,MAX成功，遍历正确，平衡");
    }

    // ------------------ 辅助方法 ------------------

    /** 递归检查AVL树所有节点的平衡因子绝对值是否<=1 */
    static boolean checkBalanceFactors(avl_tree node) {
        if (node == null) return true;
        if (Math.abs(node.balance_factor) > 1) return false;
        return checkBalanceFactors(node.left) && checkBalanceFactors(node.right);
    }
}