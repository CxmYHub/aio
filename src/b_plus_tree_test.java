import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import aio.data_structure.b_plus_tree;
/**
 * b_plus_tree 类的全面测试类
 */
public class b_plus_tree_test {

    // ANSI 颜色
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String RESET = "\033[0m";

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("开始 B+树 测试套件...\n");

        testEmptyTree();
        testSingleInsert();
        testMultipleInsertWithSplit();
        testDuplicateInsert();
        testRemoveBasic();
        testRemoveWithBorrowMerge();
        testRemoveNonexistent();
        testRemoveAll();
        testRangeQueries();
        testCountMethods();
        testStressRandomOperations();

        System.out.println("\n测试总结: " + GREEN + testsPassed + " 通过" + RESET + ", " +
                (testsFailed > 0 ? RED + testsFailed + " 失败" + RESET : "0 失败"));
    }

    // ---------- 辅助方法 ----------

    /** 向有序列表 ref 中插入元素，保持升序 */
    private static void refAddSorted(List<Integer> ref, int val) {
        int pos = 0;
        while (pos < ref.size() && ref.get(pos) < val) {
            pos++;
        }
        ref.add(pos, val);
    }

    /** 从有序列表中删除第一个匹配的元素，返回是否成功删除 */
    private static boolean refRemoveFirst(List<Integer> ref, int val) {
        int pos = 0;
        while (pos < ref.size() && ref.get(pos) < val) {
            pos++;
        }
        if (pos < ref.size() && ref.get(pos) == val) {
            ref.remove(pos);
            return true;
        }
        return false;
    }

    /** 执行一次测试断言，通过遍历数组比较 B+树 与参考列表 */
    private static void assertTraversal(b_plus_tree tree, List<Integer> ref, String testName) {
        int[] expected = ref.stream().mapToInt(Integer::intValue).toArray();
        int[] actual = tree.traversal();
        if (arraysEqual(expected, actual)) {
            pass(testName + " - 遍历结果");
        } else {
            fail(testName + " - 遍历结果", arrayToString(expected), arrayToString(actual));
        }
    }

    private static void assertCount(b_plus_tree tree, int element, List<Integer> ref, String testName) {
        int expected = Collections.frequency(ref, element);
        int actual = tree.count(element);
        if (expected == actual) {
            pass(testName + " - 单一元素计数(" + element + ")");
        } else {
            fail(testName + " - 单一元素计数(" + element + ")", String.valueOf(expected), String.valueOf(actual));
        }
    }

    private static void assertCountRange(b_plus_tree tree, int min, int max, List<Integer> ref, String testName) {
        long expected = ref.stream().filter(x -> x >= min && x <= max).count();
        int actual = tree.count(min, max);
        if (expected == actual) {
            pass(testName + " - 区间计数[" + min + "," + max + "]");
        } else {
            fail(testName + " - 区间计数[" + min + "," + max + "]", String.valueOf(expected), String.valueOf(actual));
        }
    }

    private static void assertGetRange(b_plus_tree tree, int min, int max, List<Integer> ref, String testName) {
        int[] expected = ref.stream().filter(x -> x >= min && x <= max).mapToInt(Integer::intValue).toArray();
        int[] actual = tree.get(min, max);
        if (arraysEqual(expected, actual)) {
            pass(testName + " - 区间获取[" + min + "," + max + "]");
        } else {
            fail(testName + " - 区间获取[" + min + "," + max + "]", arrayToString(expected), arrayToString(actual));
        }
    }

    private static void assertTotalCount(b_plus_tree tree, List<Integer> ref, String testName) {
        int expected = ref.size();
        int actual = tree.count();
        if (expected == actual) {
            pass(testName + " - 总元素计数");
        } else {
            fail(testName + " - 总元素计数", String.valueOf(expected), String.valueOf(actual));
        }
    }

    private static void assertRemoveResult(b_plus_tree tree, int element, boolean expectedResult, boolean actualResult, String testName) {
        if (expectedResult == actualResult) {
            pass(testName + " - 删除元素 " + element + " 返回值");
        } else {
            fail(testName + " - 删除元素 " + element + " 返回值", String.valueOf(expectedResult), String.valueOf(actualResult));
        }
    }

    private static void assertTreeValid(b_plus_tree tree, String testName) {
        String error = validate(tree);
        if (error == null) {
            pass(testName + " - 树结构验证");
        } else {
            fail(testName + " - 树结构验证", "有效树", "无效: " + error);
        }
    }

    // 输出方法
    private static void pass(String description) {
        System.out.println(GREEN + "[AC] " + description + RESET);
        testsPassed++;
    }

    private static void fail(String description, String expected, String actual) {
        System.out.println(RED + "[WA] " + description + " 期望: " + expected + " 实际: " + actual + RESET);
        testsFailed++;
    }

    private static boolean arraysEqual(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    private static String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(",").append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    // ---------- B+树结构验证 ----------

    /**
     * 验证 B+树 满足所有性质，返回 null 表示有效，否则返回错误描述。
     */
    private static String validate(b_plus_tree tree) {
        if (tree.type != -1) return "头结点类型错误";
        b_plus_tree root = tree.next;
        if (root == null) return null;
        if (root.count == 0) {
            // 空树
            if (root.type != 2) return "空树根结点类型应为2";
            if (root.children != null) return "空树叶结点children应为null";
            if (root.next != null) return "空树根叶结点next应为null";
            return null;
        }

        // 非空树：收集叶结点链并检查
        b_plus_tree leaf = root;
        while (leaf.type < 2) {
            if (leaf.children == null || leaf.children[0] == null) return "内部结点缺少子结点";
            leaf = leaf.children[0];
        }
        // 遍历所有叶结点
        b_plus_tree prevLeaf = null;
        int leafCount = 0;
        int totalElements = 0;
        int[] prevLast = null;
        while (leaf != null) {
            if (leaf.type != 2) return "叶结点链表中的结点类型不是2";
            if (leaf.count < 1 && root.type == 2 ? leaf != root : leaf.count < Math.ceil(tree.order / 2.0)) {
                // 根叶结点可以少于半满
                if (root.type != 2 || leaf != root) {
                    return "叶结点元素数量低于下限";
                }
            }
            if (leaf.count > tree.order) return "叶结点元素数量超过阶数";
            // 检查叶结点内有序
            for (int i = 1; i < leaf.count; i++) {
                if (leaf.elements[i] < leaf.elements[i - 1]) return "叶结点内元素无序";
            }
            // 检查与前一个叶结点的顺序
            if (prevLeaf != null) {
                if (prevLeaf.elements[prevLeaf.count - 1] > leaf.elements[0])
                    return "相邻叶结点间元素顺序错误";
            }
            totalElements += leaf.count;
            leafCount++;
            prevLeaf = leaf;
            leaf = leaf.next;
        }

        // 检查总元素数与 count() 一致
        if (tree.count() != totalElements) return "叶结点总元素数与count()不一致";

        // 递归检查内部结点（包含根结点）
        return validateInternalNode(root, tree.order, true, root);
    }

    private static String validateInternalNode(b_plus_tree node, int order, boolean isRoot, b_plus_tree root) {
        if (node.type == 2) {
            return null; // 叶结点已检查
        }
        if (node.type != 0 && node.type != 1) return "内部结点类型错误";
        if (node.count < 2 && isRoot && root.type != 2) return "根内部结点子结点数少于2";
        if (!isRoot && node.count < Math.ceil(order / 2.0)) return "非根内部结点子结点数低于下限";
        if (node.count > order) return "内部结点子结点数超过阶数";
        if (node.children == null) return "内部结点children为null";

        // 检查关键字与子结点的最小元素一致性
        for (int i = 0; i < node.count - 1; i++) {
            if (node.children[i + 1] == null) return "内部结点子结点为null";
            b_plus_tree temp = node.children[i + 1];
            while (temp.type < 2) {
                if (temp.children == null || temp.children[0] == null) return "内部结点链断裂";
                temp = temp.children[0];
            }
            if (temp.elements.length == 0 || temp.count == 0) return "子树叶结点无元素";
            if (node.elements[i] != temp.elements[0])
                return "内部结点关键字不等于对应子结点的最小叶元素: 期望" + temp.elements[0] + " 实际" + node.elements[i];
        }
        // 确保最后一个子结点存在
        if (node.children[node.count - 1] == null) return "内部结点最后一个子结点为null";

        // 递归验证子结点
        for (int i = 0; i < node.count; i++) {
            b_plus_tree child = node.children[i];
            String err = validateInternalNode(child, order, false, root);
            if (err != null) return err;
        }

        // 验证所有叶结点深度一致（通过遍历到叶结点并计算深度）
        return null;
    }

    // ---------- 测试用例 ----------

    // 1. 空树测试
    private static void testEmptyTree() {
        System.out.println("--- 测试 1: 空树 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();

        assertTraversal(tree, ref, "空树遍历");
        assertTotalCount(tree, ref, "空树总计数");
        assertCount(tree, 5, ref, "空树单一元素计数");
        assertCountRange(tree, 1, 10, ref, "空树区间计数");
        assertGetRange(tree, 1, 10, ref, "空树区间获取");
        boolean removeResult = tree.remove(5);
        assertRemoveResult(tree, 5, false, removeResult, "空树删除");
        assertTraversal(tree, ref, "空树删除后遍历");
        assertTreeValid(tree, "空树结构");
    }

    // 2. 插入单个元素
    private static void testSingleInsert() {
        System.out.println("--- 测试 2: 插入单个元素 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();

        tree.input(5);
        refAddSorted(ref, 5);

        assertTraversal(tree, ref, "单元素插入");
        assertTotalCount(tree, ref, "单元素总计数");
        assertCount(tree, 5, ref, "单元素计数(5)");
        assertCountRange(tree, 3, 7, ref, "单元素区间计数");
        assertGetRange(tree, 3, 7, ref, "单元素区间获取");
        assertTreeValid(tree, "单元素结构");
    }

    // 3. 插入多个元素，触发分裂 (阶=4)
    private static void testMultipleInsertWithSplit() {
        System.out.println("--- 测试 3: 多元素插入与分裂 (阶=4) ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] values = {3, 7, 1, 9, 5, 2, 8, 6, 4, 0, 10, 11, 12, 13, 14, 15};

        for (int v : values) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        assertTraversal(tree, ref, "多元素插入后遍历");
        assertTotalCount(tree, ref, "多元素总计数");
        for (int v : new int[]{0, 7, 15, 3}) {
            assertCount(tree, v, ref, "多元素单一计数(" + v + ")");
        }
        assertCountRange(tree, 2, 9, ref, "多元素区间计数[2,9]");
        assertGetRange(tree, 2, 9, ref, "多元素区间获取[2,9]");
        assertTreeValid(tree, "多元素结构验证");
    }

    // 4. 重复元素
    private static void testDuplicateInsert() {
        System.out.println("--- 测试 4: 重复元素插入 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] values = {5, 5, 3, 5, 3, 3, 7, 5};

        for (int v : values) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        assertTraversal(tree, ref, "重复元素插入后遍历");
        assertCount(tree, 5, ref, "重复元素计数(5)");
        assertCount(tree, 3, ref, "重复元素计数(3)");
        assertCount(tree, 7, ref, "重复元素计数(7)");
        assertTreeValid(tree, "重复元素结构验证");
    }

    // 5. 基本删除 (简单情况，无合并)
    private static void testRemoveBasic() {
        System.out.println("--- 测试 5: 基本删除 (无下溢) ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] values = {1, 2, 3, 4, 5, 6};
        for (int v : values) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        // 删除一个存在的元素
        tree.remove(3);
        refRemoveFirst(ref, 3);
        assertTraversal(tree, ref, "删除3后遍历");
        assertTreeValid(tree, "删除3后结构");

        tree.remove(1);
        refRemoveFirst(ref, 1);
        assertTraversal(tree, ref, "删除1后遍历");
        assertTreeValid(tree, "删除1后结构");

        // 删除不存在的元素
        boolean res = tree.remove(100);
        assertRemoveResult(tree, 100, false, res, "删除不存在的100");
        assertTraversal(tree, ref, "删除100后遍历");
    }

    // 6. 删除导致借用和合并 (阶=4)
    private static void testRemoveWithBorrowMerge() {
        System.out.println("--- 测试 6: 删除导致借位与合并 (阶=4) ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int v : values) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        // 删除多个元素，触发下溢处理
        int[] toRemove = {10, 9, 8, 7, 6};
        for (int r : toRemove) {
            tree.remove(r);
            refRemoveFirst(ref, r);
            assertTraversal(tree, ref, "删除 " + r + " 后遍历");
            assertTreeValid(tree, "删除 " + r + " 后结构");
        }

        // 此时应有较少元素，继续删除直到下溢合并
        tree.remove(5);
        refRemoveFirst(ref, 5);
        assertTraversal(tree, ref, "删除5后遍历");
        assertTreeValid(tree, "删除5后结构");

        tree.remove(4);
        refRemoveFirst(ref, 4);
        assertTraversal(tree, ref, "删除4后遍历");
        assertTreeValid(tree, "删除4后结构");

        tree.remove(3);
        refRemoveFirst(ref, 3);
        assertTraversal(tree, ref, "删除3后遍历");
        assertTreeValid(tree, "删除3后结构");
    }

    // 7. 删除不存在的元素
    private static void testRemoveNonexistent() {
        System.out.println("--- 测试 7: 删除不存在的元素 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        tree.input(10);
        refAddSorted(ref, 10);

        boolean res = tree.remove(20);
        assertRemoveResult(tree, 20, false, res, "删除不存在元素20");
        assertTraversal(tree, ref, "删除20后遍历不变");
        assertTreeValid(tree, "删除20后结构");
    }

    // 8. remove_all 删除所有匹配
    private static void testRemoveAll() {
        System.out.println("--- 测试 8: remove_all 删除所有匹配 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] values = {2, 2, 2, 5, 2, 7, 2};
        for (int v : values) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        int removed = tree.remove_all(2);
        int expectedRemoved = (int) ref.stream().filter(x -> x == 2).count();
        ref.removeIf(x -> x == 2);

        if (removed == expectedRemoved) {
            pass("remove_all(2) 返回值");
        } else {
            fail("remove_all(2) 返回值", String.valueOf(expectedRemoved), String.valueOf(removed));
        }

        assertTraversal(tree, ref, "remove_all(2)后遍历");
        assertCount(tree, 2, ref, "remove_all后计数(2)");
        assertTreeValid(tree, "remove_all后结构");
    }

    // 9. 区间查询边界
    private static void testRangeQueries() {
        System.out.println("--- 测试 9: 区间查询边界 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        for (int v : new int[]{5, 10, 15, 20, 25}) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        // min 小于最小值
        assertGetRange(tree, 0, 12, ref, "区间获取[0,12]");
        assertCountRange(tree, 0, 12, ref, "区间计数[0,12]");

        // max 大于最大值
        assertGetRange(tree, 12, 30, ref, "区间获取[12,30]");
        assertCountRange(tree, 12, 30, ref, "区间计数[12,30]");

        // 区间完全在数据范围外左侧
        assertGetRange(tree, 0, 2, ref, "区间获取[0,2]");
        assertCountRange(tree, 0, 2, ref, "区间计数[0,2]");

        // 区间完全在右侧
        assertGetRange(tree, 30, 40, ref, "区间获取[30,40]");
        assertCountRange(tree, 30, 40, ref, "区间计数[30,40]");

        // min == max 且命中
        assertGetRange(tree, 10, 10, ref, "区间获取[10,10]");
        assertCountRange(tree, 10, 10, ref, "区间计数[10,10]");

        // min == max 未命中
        assertGetRange(tree, 12, 12, ref, "区间获取[12,12]");
        assertCountRange(tree, 12, 12, ref, "区间计数[12,12]");

        // min > max 非法区间
        assertGetRange(tree, 20, 10, ref, "区间获取[20,10]");
        assertCountRange(tree, 20, 10, ref, "区间计数[20,10]");
    }

    // 10. 计数方法测试（总计数、单一计数、区间计数）
    private static void testCountMethods() {
        System.out.println("--- 测试 10: 计数方法测试 ---");
        b_plus_tree tree = new b_plus_tree(4);
        List<Integer> ref = new ArrayList<>();
        int[] vals = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        for (int v : vals) {
            tree.input(v);
            refAddSorted(ref, v);
        }

        assertTotalCount(tree, ref, "总计数");
        assertCount(tree, 1, ref, "单一计数(1)");
        assertCount(tree, 5, ref, "单一计数(5)");
        assertCount(tree, 0, ref, "单一计数(0)");
        assertCountRange(tree, 2, 5, ref, "区间计数[2,5]");
        assertCountRange(tree, -10, 100, ref, "区间计数全范围");
    }

    // 11. 压力测试：随机插入删除交替，对比参考列表
    private static void testStressRandomOperations() {
        System.out.println("--- 测试 11: 随机插入删除压力测试 ---");
        b_plus_tree tree = new b_plus_tree(8); // 阶=8
        List<Integer> ref = new ArrayList<>();
        Random rand = new Random(42); // 固定种子可重复
        int operations = 2000;

        for (int i = 0; i < operations; i++) {
            double p = rand.nextDouble();
            if (p < 0.65 || ref.isEmpty()) {
                // 插入
                int val = rand.nextInt(500);
                tree.input(val);
                refAddSorted(ref, val);
            } else {
                // 删除
                int idx = rand.nextInt(ref.size());
                int val = ref.get(idx);
                tree.remove(val);
                refRemoveFirst(ref, val);
            }

            // 每100次操作进行一次一致性检查
            if (i % 100 == 0) {
                int[] trav = tree.traversal();
                int[] expArr = ref.stream().mapToInt(Integer::intValue).toArray();
                if (!arraysEqual(trav, expArr)) {
                    fail("压力测试遍历 (操作" + i + ")", arrayToString(expArr), arrayToString(trav));
                    return; // 提前终止
                }
                int treeTotal = tree.count();
                if (treeTotal != ref.size()) {
                    fail("压力测试总计数 (操作" + i + ")", String.valueOf(ref.size()), String.valueOf(treeTotal));
                    return;
                }
                String err = validate(tree);
                if (err != null) {
                    fail("压力测试结构验证 (操作" + i + ")", "有效", "无效: " + err);
                    return;
                }
            }
        }

        // 最终验证
        assertTraversal(tree, ref, "压力测试最终遍历");
        assertTotalCount(tree, ref, "压力测试最终总计数");
        assertTreeValid(tree, "压力测试最终结构");
        // 随机抽测区间查询
        for (int t = 0; t < 10; t++) {
            int min = rand.nextInt(200);
            int max = min + rand.nextInt(200);
            assertGetRange(tree, min, max, ref, "压力测试区间获取[" + min + "," + max + "]");
            assertCountRange(tree, min, max, ref, "压力测试区间计数[" + min + "," + max + "]");
        }
    }
}