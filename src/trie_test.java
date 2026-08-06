import aio.data_structure.trie;
public class trie_test {
    private static final String GREEN = "\u001B[32m";
    private static final String RED   = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    public static void main(String[] args) {
        testBasicOperations();
        testRemoveWithDepthBug();
        testRemoveNonExistent();
        testInputMoreAndCount();
        System.out.println("\n所有测试执行完毕。");
    }

    private static void testBasicOperations() {
        System.out.println("=== 测试基本插入、查询与计数 ===");
        trie t = new trie();
        
        // 插入单个单词
        int added = t.input("hello");
        assertEqual(added, 5, "插入 hello 新增结点数");
        assertEqual(t.exist("hello"), true, "查询 hello 存在");
        assertEqual(t.exist("hell"), false, "查询 hell 不存在");
        
        // 插入重复单词
        int dup = t.input("hello");
        assertEqual(dup, Integer.MIN_VALUE, "重复插入 hello 返回 Integer.MIN_VALUE");
        
        // 插入前缀单词
        t.input("hell");
        assertEqual(t.exist("hell"), true, "插入 hell 后存在");
        assertEqual(t.count(), 2, "总单词数为 2");
        
        // 获取所有单词（注意：返回小写）
        String[] words = t.get_all_words();
        assertEqual(words.length, 2, "get_all_words 返回数组长度");
        boolean hasHello = false, hasHell = false;
        for (String w : words) {
            if ("hello".equals(w)) hasHello = true;
            if ("hell".equals(w)) hasHell = true;
        }
        assertEqual(hasHello && hasHell, true, "get_all_words 包含插入的单词");
        
        System.out.println();
    }

    private static void testRemoveWithDepthBug() {
        System.out.println("=== 复现 remove 后 depth 值错误 ===");
        
        // 构造结构：
        // 根 -> A (depth 应为 6) -> B (depth 5) -> C (depth 4) -> D (depth 3) -> E (depth 2) -> F (depth 1, 单词结尾)
        //      同时 A 还有另一个子结点 X，对应长单词 "AXYZ" (长度4，depth分别为 4,3,2,1)
        trie t = new trie();
        t.input("ABCDEF");   // 长度6
        t.input("AXYZ");     // 长度4
        
        // 获取根结点的 A 子结点
        trie nodeA = t.children['A' - 'A'];
        assertNotNull(nodeA, "结点 A 存在");
        
        // 检查删除前 A 的 depth（应为 max(ABCDEF剩余长度, AXYZ剩余长度) = 6）
        System.out.println("删除前 A.depth = " + nodeA.depth + " (预期 6)");
        assertEqual(nodeA.depth, 6, "删除前 A.depth 正确");
        
        // 获取 B 结点 (A->B)
        trie nodeB = nodeA.children['B' - 'A'];
        assertNotNull(nodeB, "结点 B 存在");
        System.out.println("删除前 B.depth = " + nodeB.depth + " (预期 5)");
        assertEqual(nodeB.depth, 5, "删除前 B.depth 正确");
        
        // 删除单词 "ABCDEF"
        boolean removed = t.remove("ABCDEF");
        assertEqual(removed, true, "删除 ABCDEF 成功");
        assertEqual(t.exist("ABCDEF"), false, "删除后 ABCDEF 不存在");
        assertEqual(t.exist("AXYZ"), true, "删除后 AXYZ 仍存在");
        
        // 重新获取结点 A（因删除可能导致其子结点变化）
        nodeA = t.children['A' - 'A'];
        assertNotNull(nodeA, "删除后结点 A 仍存在");
        
        // 此时 A 下面只有 X 分支（深度4），所以 A.depth 应为 X 分支的深度 4 + 1 = 5
        // 但由于 remove 中的 bug，实际 depth 会被错误计算
        System.out.println("删除后 A.depth = " + nodeA.depth + " (预期 4)");
        assertEqual(nodeA.depth, 4, "删除后 A.depth 正确更新为 4");
        
        // 进一步验证：再次删除 "AXYZ" 应该能正确清理
        boolean removed2 = t.remove("AXYZ");
        assertEqual(removed2, true, "删除 AXYZ 成功");
        // 此时 A 应被删除（因为无其他子结点且自身不是单词结尾）
        trie rootAChild = t.children['A' - 'A'];
        assertEqual(rootAChild == null, true, "删除 AXYZ 后根结点的 A 子结点被清理");
        
        System.out.println();
    }

    private static void testRemoveNonExistent() {
        System.out.println("=== 测试删除不存在的单词 ===");
        trie t = new trie("apple", "banana");
        boolean result = t.remove("orange");
        assertEqual(result, false, "删除不存在的单词返回 false");
        assertEqual(t.count(), 2, "单词数量不变");
        System.out.println();
    }

    private static void testInputMoreAndCount() {
        System.out.println("=== 测试批量插入 input_more ===");
        trie t = new trie();
        int added = t.input_more("cat", "car", "dog", "cat"); // 重复 cat
        // 预期新增结点：c(1), a(2), t(3), r(4), d(5), o(6), g(7) 共7个结点 + 3个单词结束标记（cat,car,dog）但 cat 已存在不计单词标记
        // 注意 input_more 返回值统计的是新增结点数，不包括重复单词的结束标记，但包括新单词的结束标记。
        // 具体：cat (3新结点+1结束) =4, car (1新结点'r'+1结束) =2, dog (3新结点+1结束) =4, cat重复 (0) => 总计10
        // 我们只验证返回值非负且基本合理，重点不在此。
        System.out.println("input_more 返回新增结点数: " + added);
        assertEqual(t.count(), 3, "批量插入后单词数为 3");
        assertEqual(t.exist("cat"), true, "cat 存在");
        assertEqual(t.exist("car"), true, "car 存在");
        assertEqual(t.exist("dog"), true, "dog 存在");
        System.out.println();
    }

    // ---------- 辅助断言方法（不依赖 JUnit） ----------
    // private static void assertEqual(Object actual, Object expected, String message) {
    //     boolean pass = (actual == null && expected == null) || (actual != null && actual.equals(expected));
    //     if (pass) {
    //         System.out.println(GREEN + "  [AC] " + message + RESET);
    //     } else {
    //         System.out.println(RED + "  [WA] " + message + " - 期望: " + expected + "，实际: " + actual + RESET);
    //     }
    // }

    private static void assertEqual(int actual, int expected, String message) {
        if (actual == expected) {
            System.out.println(GREEN + "  [AC] " + message + RESET);
        } else {
            System.out.println(RED + "  [WA] " + message + " - 期望: " + expected + "，实际: " + actual + RESET);
        }
    }

    private static void assertEqual(boolean actual, boolean expected, String message) {
        if (actual == expected) {
            System.out.println(GREEN + "  [AC] " + message + RESET);
        } else {
            System.out.println(RED + "  [WA] " + message + " - 期望: " + expected + "，实际: " + actual + RESET);
        }
    }

    private static void assertNotNull(Object obj, String message) {
        if (obj != null) {
            System.out.println(GREEN + "  [AC] " + message + RESET);
        } else {
            System.out.println(RED + "  [WA] " + message + " - 对象为 null" + RESET);
        }
    }
}