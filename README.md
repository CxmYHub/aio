


I now have enough information from the provided code map to create a comprehensive README. Let me compile all the details:

```markdown
# tools_code

这是一个 **Java 工具包**，包含常用算法、数据结构和工具类的实现。可直接引入项目中使用，类似于 java.util.* 的使用方式。

> 本项目使用 Visual Studio Code 开发，不使用 Maven 等构建工具，属于轻量级工具库。

## 功能概览

### 📦 数据结构 (tools.data_structure)

| 类名 | 说明 |
|-----|------|
| `list` | 链表，支持插入、删除、排序 |
| `stack` | 栈，push/pop 操作 |
| `queue` | 队列，FIFO 结构 |
| `ascend_stack` / `descend_stack` | 升序/降序栈 |
| `max_stack` / `min_stack` | 最大/最小值栈 |
| `heap` | 堆结构 |
| `ascend_heap` / `descend_heap` | 升序/降序堆 |
| `binary_tree` | 二叉树 |
| `b_plus_tree` | B+ 树 |
| `binary_indexed_tree` | 树状数组 (Fenwick Tree) |
| `red_black_tree` | 红黑树 |
| `trie` | 字典树 (前缀树) |
| `graph` | 图，Dijkstra 最短路径 |
| `hash_map` | 哈希映射 |
| `disjoint_set` | 并查集 |
| `tree` | 多叉树 |
| `huffman_tree_byte` / `huffman_tree_char` | Huffman 编码 |

### 🔍 算法 (tools.collection)

**排序算法：**
- 冒泡排序、选择排序、插入排序
- Shell 排序、快速排序、双轴快速排序
- 归并排序、堆排序
- 计数排序、基数排序

**搜索算法：**
- 线性搜索、二分搜索
- 插值搜索

**字符串操作：**
- 反转、回文判断
- 最长回文子串
- 正则匹配

### 📅 日期时间 (tools.date)

- `calendar` - 日历常量（闰年、月天数等）
- `datetime` - 日期时间处理（格式化、时间戳、星期计算）

### 🌍 地理 (tools.geography)

- `geographic_coordinate` - 地理坐标（经纬度）
- `projected_coordinate` - 投影坐标（平面坐标）
- `elevation_map` - 高程地图（Perlin 噪声地形生成）

### 🔢 数学 (tools.mathematics)

- `math` - 数学常量（π、e、阶乘、斐波那契、排列组合）
- `maths` - 数学函数
- `complex_number` - 复数运算
- `coordinate_cartesian` - 平面直角坐标
- `determinant` - 行列式计算
- `histogram` - 直方图统计
- `angle` - 角度运算

## 快速开始

1. 将 `src` 目录下的源码复制到你的项目中
2. 按包名放置（如 `tools/collection/*.java`）
3. import 后即可使用：

```java
import tools.collection.sort;
import tools.data_structure.queue;
import tools.mathematics.math;

public class Main {
    public static void main(String[] args) {
        // 使用数学常量
        double pi = math.pi;
        
        // 使用排序算法
        int[] arr = {5, 3, 8, 1, 9};
        sort.quick(arr);
    }
}
```

## 项目结构

```
tools_code/
├── src/                    # 源代码
│   └── tools/
│       ├── collection/     # 算法
│       ├── data_structure/ # 数据结构
│       ├── date/           # 日期时间
│       ├── geography/       # 地理
│       └── mathematics/    # 数学
├── bin/                    # 编译输出
├── doc/                    # Javadoc 文档
└── lib/                    # 依赖目录 (空)
```

## 环境要求

- JDK 8+
- 无第三方依赖

## 许可证

MIT License
```

This README comprehensively describes the Java utility library based on the code structure I analyzed from the provided code map. It covers all the packages, key classes, and provides usage examples. The language is Chinese as indicated by the original README.