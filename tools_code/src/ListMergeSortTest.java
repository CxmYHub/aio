import tools.data_structure.linked_list;
import java.util.Arrays;
import java.util.Random;

public class ListMergeSortTest {
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        testWithSize(100, "小数据量（100个元素）");
        testWithSize(100_000, "大数据量（100,000个元素）");
    }

    private static void testWithSize(int size, String testName) {
        System.out.println("\n=== " + testName + " ===");
        
        // 1. 生成随机整数数组
        int[] original = new int[size];
        for (int i = 0; i < size; i++) {
            original[i] = RNG.nextInt(1000000); // 随机范围 0 ~ 999999
        }
        
        // 2. 用 Arrays.sort 得到正确排序结果
        int[] expected = original.clone();
        Arrays.sort(expected);
        
        // 3. 用自定义链表构建并排序
        linked_list myList = new linked_list(original);          // 假设 linked_list 有接收 int[] 的构造器
        long start = System.nanoTime();
        myList.sort_ascend();                        // 归并排序
        long end = System.nanoTime();
        
        // 4. 将排序后的链表转回数组进行比较
        int[] actual = listToArray(myList);
        
        // 5. 验证
        boolean passed = Arrays.equals(expected, actual);
        System.out.println("排序耗时: " + (end - start) / 1000 + " μs");
        System.out.println("结果: " + (passed ? "[AC] 排序正确" : "[WA] 排序错误"));
        if (!passed) {
            // 可选：打印前20个元素帮助调试
            System.out.println("预期前20: " + Arrays.toString(Arrays.copyOf(expected, 20)));
            System.out.println("实际前20: " + Arrays.toString(Arrays.copyOf(actual, 20)));
        }
    }
    
    // 将链表转换为 int 数组（假设 linked_list 的迭代方式是通过 next 遍历）
    private static int[] listToArray(linked_list l) {
        int size = l.element_count();   // element_count() 返回链表元素个数
        int[] arr = new int[size];
        linked_list cur = l.next;               // 跳过哑元头节点
        for (int i = 0; i < size; i++) {
            arr[i] = cur.element;
            cur = cur.next;
        }
        return arr;
    }
}