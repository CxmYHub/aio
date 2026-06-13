# tools_code

这是一个**Java工具包**，包含了一些常用算法和数据结构的实现。也编写了一些常用工具类。

本包中的源代码、源代码文件或整包可以直接引入代码或项目中使用，可将其复制到项目源代码目录中，import后可直接调用（类似java.util.*）。

这是一个简单Java项目，不使用如Maven的项目构建器。

## 本项目使用Visual Studio Code开发

- 建议您在Visual Studio Code中打开项目文件夹。

---

## 文件结构

- `根目录`

    - `tools_code`：工具包源代码目录。可使用VSCode打开文件夹。其中包含工具包子包和一些测试类。

        - `.vscode`：此处为VSCode相关文件夹，包含基础设置文件，如`settings.json`。

        - `bin`：此文件夹默认不存在，进行首次编译后将自动创建。编译后的输出文件将默认生成在此文件夹中。

        - `doc`：此处为使用javadoc自动创建的文档。

        - `src`：此处为源代码。

    - `.gitignore`：忽略描述文档，用于忽略一些文件。

    - `README.md`：项目说明文档。

> 如果您想要自定义文件夹结构，请打开 `.vscode/settings.json` 并更新相关设置。（当您使用VSCode打开项目时，将提示创建此文件夹。）

## 快速开始

本文件中的任何代码（或包）可直接引入代码或项目中使用，可直接将源代码、源代码文件或整包复制到项目目录中，import后可直接使用（类似java.util.*）。不完整复制时请注意跨包依赖。

``` Java
import tools.mathematics.*;
import tools.collection.*;
import java.util.*;
class using_tools
{
    public static void main(String args[])
    {
        //使用mathematics包中maths类的calculate方法计算表达式，输出12.5
        System.out.println(maths.calculate("(5+4)*3/2-1"));
        //使用collection包中sort类的quick方法对数组num进行快速排序，输出[1,2,4,5,7,8]
        int num[]={7,1,4,2,8,5};
        sort.quick(num);
        System.out.println(Arrays.toString(num));
    }
}
```

---

## 依赖管理

本项目属于工具包，基于Java标准库，不应包含第三方依赖。

---

# tools_code说明书

本工具包提供了丰富的数据结构、算法、数学工具、日期处理、地理计算等功能，旨在帮助开发者快速实现常见任务。所有代码均为纯 Java 实现，不依赖第三方库。

## 目录
- [1. 项目概述](#1-项目概述)

- [2. 包结构](#2-包结构)

- [3. 详细说明](#3-详细说明)

    - [3.1 tools.collection（集合与算法）](#31-toolscollection集合与算法)

        - [concurrent_sort（并发排序）](#concurrent_sort并发排序)

        - [search（查找）](#search查找)

        - [sort（排序）](#sort排序)

        - [string（字符串工具）](#string字符串工具)

    - [3.2 tools.data_structure（数据结构）](#32-toolsdata_structure数据结构)

        - [binary_indexed_tree（树状数组）](#binary_indexed_tree树状数组)

        - [binary_tree（二叉树）](#binary_tree二叉树)

        - [b_plus_tree（B+树）](#b_plus_treeb树)

        - [disjoint_set / disjoint_set_element（并查集）](#disjoint_set--disjoint_set_element并查集)

        - [graph（图）](#graph图)

        - [hash_map（哈希表）](#hash_map哈希表)

        - [heap / heap_ascend / heap_descend（堆）](#heap--heap_ascend--heap_descend堆)

        - [linked_list_singly / linked_list_doubly（链表）](#linked_list_singly--linked_list_doubly链表)

        - [queue（队列）](#queue队列)

        - [red_black_tree（红黑树）](#red_black_tree红黑树)

        - [stack / stack_ascend / stack_descend / stack_max / stack_min（栈）](#stack--stack_ascend--stack_descend--stack_max--stack_min栈)

        - [tree（树-孩子兄弟表示法）](#tree树-孩子兄弟表示法)

        - [trie（字典树）](#trie字典树)

        - [huffman_tree_byte / huffman_tree_char（霍夫曼树）](#huffman_tree_byte--huffman_tree_char霍夫曼树)

    - [3.3 tools.date_time（日期时间）](#33-toolsdate_time日期时间)

        - [datetime（日期时间）](#datetime日期时间)

        - [calendar（日期常数）](#calendar日期常数)

    - [3.4 tools.geography（地理工具）](#34-toolsgeography地理工具)

        - [elevation_map（高程地图）](#elevation_map高程地图)

        - [geographic_coordinate（地理坐标）](#geographic_coordinate地理坐标)

        - [projected_coordinate（投影坐标）](#projected_coordinate投影坐标)

    - [3.5 tools.mathematics（数学工具）](#35-toolsmathematics数学工具)

        - [math（数学常数）](#math数学常数)

    - [3.6 tools.two_dimensional_barcode（二维码）](#36-toolstwo_dimensional_barcode二维码)

        - [meta（元数据常量）](#meta元数据常量)

        - [quick_response_code（二维码）](#quick_response_code二维码)

        - [maths（数学方法）](#maths数学方法)

        - [complex_number（复数）](#complex_number复数)

        - [coordinate_cartesian（直角坐标）](#coordinate_cartesian直角坐标)

        - [determinant（行列式）](#determinant行列式)

        - [histogram（直方图）](#histogram直方图)

        - [matrix（矩阵）](#matrix矩阵)

        - [polynomial_equation（多项式方程）](#polynomial_equation多项式方程)

        - [square_root（平方根）](#square_root平方根)
        
        - [angle（角度）](#angle角度)

- [4. 使用示例](#4-使用示例)

---

## 1. 项目概述

本工具包是一个自包含的 Java 库，提供了多种常用功能：

- **排序与查找**：包括冒泡、选择、插入、希尔、快速（单轴/双轴）、归并、计数、基数排序，以及线性/二分/插值查找。

- **并发排序**：基于双轴快速排序的多线程版本，可自动划分任务并行处理。

- **数据结构**：链表（单向/双向）、栈（普通/单调/最大/最小）、队列（循环）、堆（升序/降序）、二叉树、红黑树、B+树、字典树、霍夫曼树、图（邻接矩阵）、并查集、哈希表、树状数组等。

- **日期时间**：支持公历（含公元前），时区转换，日期间隔计算，星期计算等。

- **地理工具**：地理坐标（经纬度）、投影坐标（平面直角坐标）、高程地图（柏林噪声地形生成、统计分析等）。

- **数学工具**：复数、矩阵、行列式、直方图、多项式方程求解（1/2次）、平方根化简、数论函数（最大公因数、质数判断/分解）、组合数/排列数常数表、线性回归等。

所有类均位于 `tools` 包下，按功能划分到子包。使用时请确保编译环境支持 Java 8 及以上。

---

## 2. 包结构

```
tools
├── collection          # 集合工具（排序、查找、字符串）
├── data_structure      # 数据结构（链表、树、堆、图、哈希表等）
├── date                # 日期时间处理
├── geography           # 地理坐标、高程地图
└── mathematics         # 数学算法与结构
```

---

## 3. 详细说明

### 3.1 tools.collection（集合与算法）

#### concurrent_sort（并发排序）

- **类**：`concurrent_sort`

- **功能**：提供并发双轴快速排序。内部使用多线程加速，对于大数组效率提升明显。

- **方法**：

    - `public static void concurrent_quick_dual_pivot(int[] numbers)`

        - 对整型数组进行原地升序排序。

        - 内部根据阈值（`concurrent_quick_dual_pivot_sort.threshold`，默认19683）决定是否创建新线程。

- **注意**：此类功能仍在验证中，非学习或极端性能需求建议使用 `java.util.Arrays.sort`。

#### search（查找）

- **类**：`tools.collection.search`

- **方法**：

    - `linear_search(int[] numbers, int target)`

        - 线性查找第一个匹配的索引，未找到返回 `Integer.MIN_VALUE`。

    - `binary_search(int[] numbers, int target)` 

        - 二分查找（要求数组已升序），返回索引或 `Integer.MIN_VALUE`。

    - `binary_search_first(int[] numbers, int target)`

        - 返回第一个大于等于 `target` 的索引（类似 `lower_bound`）。

    - `binary_search_between(int[] numbers, int min, int max)`

        - 返回数值在 `[min, max]` 区间内的元素个数（数组需升序）。

    - `interpolation_search(int[] numbers, int target)`

        - 插值查找（要求数组均匀分布且升序），返回索引或 `Integer.MIN_VALUE`。

#### sort（排序）

- **类**：`tools.collection.sort`

- **功能**：提供多种排序算法的静态实现，支持 `int[]` 和 `double[]`。

- **主要方法**：

    - `bubble`, `selection`, `insertion`, `shell`, `quick`, `quick_dual_pivot`, `merge`, `counting`, `radix`

    - 所有方法均**原地修改**输入数组。

    - 额外提供 `median_5`, `max_second_5`, `min_second_5` 用于从5个数中快速计算中位数、第二大、第二小。

- **示例**：`sort.quick_dual_pivot(arr);`

#### string（字符串工具）

- **类**：`tools.collection.string`

- **方法**：

    - `reverse(String s)` → 反转字符串。

    - `contains(String base, String pattern)` → 使用 KMP 算法判断 `base` 是否包含 `pattern`。

    - `is_palindrome(String s)` → 判断是否为回文串。

    - `longest_palindrome(String s)` → 返回最长回文子串（马拉车算法）。

    - `longest_palindrome_length(String s)` → 返回最长回文子串长度。

    - `match_regular_expression(String s, String regex)` → 支持 `.` 和 `*` 的正则匹配（类似 LeetCode 题目）。

---

### 3.2 tools.data_structure（数据结构）

以下类均位于 `tools.data_structure` 包中。

#### binary_indexed_tree（树状数组）

- **构造器**：

    - `binary_indexed_tree(int length)` → 初始全0。

    - `binary_indexed_tree(int[] original)` → 用原数组初始化。

- **方法**：

    - `void add(int index, int delta)` → 在 `index` 处增加 `delta`（索引从0开始）。

    - `long sum_prefix(int index)` → 前缀和 `[0..index]`。

    - `long sum_interval(int left, int right)` → 区间和 `[left, right]`。

#### binary_tree（二叉树）

- **构造器**：

    - `binary_tree(String treeString)` → 从括号表示法构建，例如 `"A{B{D,E},C{F,G}}"`。

    - `binary_tree(int[] preorder, int[] inorder)` → 根据先序+中序构建。

    - `binary_tree(int[] inorder, int[] postorder, int dummy)` → 根据中序+后序构建。

- **方法**：

    - `int count()` → 节点数。

    - `int depth()` → 深度（根深度为1）。

    - `boolean is_same(binary_tree other)` → 结构相同且元素相等。

    - `int[] traversal_preorder/inorder/postorder/levelorder()` → 返回遍历序列。

    - `int input(int element)` → 按层序插入元素（返回父节点值）。

    - `int remove(int element)` → 删除第一个遇到的节点（按先序）。

    - `void invert()` → 镜像翻转。

#### b_plus_tree（B+树）

- **构造器**：

    - `b_plus_tree(int order)` → 指定阶数（最小4）。

    - `b_plus_tree()` → 默认阶数256。

- **方法**：

    - `int get(int key)` → 返回 `key` 出现的次数（支持重复元素）。

    - `int get(int min, int max)` → 返回区间 `[min, max]` 内元素个数。

    - `int count()` → 总元素个数。

    - `int[] traversal()` → 中序遍历所有叶子节点（升序）。

    - `b_plus_tree input(int key)` → 插入元素，返回可能变化的新根。

    - `b_plus_tree remove(int key)` → 删除一个匹配的元素，返回新根。

    - `b_plus_tree remove_all(int key)` → 删除所有匹配元素，返回新根。

#### disjoint_set / disjoint_set_element（并查集）

- **disjoint_set**：基于索引的并查集。

    - 构造：`disjoint_set(int capacity)` 或 `disjoint_set()`

    - `int find_root_by_index(int index)`

    - `int union_index(int idx1, int idx2)`

    - `boolean is_related_index(int idx1, int idx2)`

- **disjoint_set_element**：扩展支持整数元素映射。

    - `int input(int element)` → 添加元素（若已存在返回 `Integer.MIN_VALUE`）。

    - `int input_more(int... elements)`

    - `int find_root_by_element(int element)`

    - `int union_element(int e1, int e2)`

    - `boolean is_related_element(int e1, int e2)`

#### graph（图）

- 采用邻接矩阵，支持有向/无向、有权/无权。

- **构造**：`graph(String graphString, int type)`

    - `type=1` 无向无权，`type=2` 无向有权，`type=3` 有向有权。

    - 字符串格式：`"{(v1,v2,w1),(v2,v3,w2),...}"`，顶点编号从1开始。

- **方法**：`int cost_min(int start, int end)` → Dijkstra 算法求最短路径成本（有权图）或边数（无权图）。

#### hash_map（哈希表）

- 链地址法，负载因子 2/3，自动扩容至质数容量。

- **构造**：`hash_map()` 默认容量257；`hash_map(int capacity)` 取不小于 capacity 的质数。

- **方法**：

    - `int input(int key, int value)` → 插入或更新，返回桶索引。

    - `int get(int key)` → 返回值，不存在返回 `Integer.MIN_VALUE`。

    - `int remove(int key)` → 删除并返回原值，不存在返回 `Integer.MIN_VALUE`。

#### heap / heap_ascend / heap_descend（堆）

- **heap**：普通堆（仅存储，不自动维护堆序）。提供基础数组扩容、插入、取出。

- **heap_ascend**（升序堆）和 **heap_descend**（降序堆）继承自 `heap`，自动维护堆序。

- **构造器**：`heap_ascend()` 默认容量255；`heap_ascend(int... elements)` 自动建堆。

- **主要方法**：

    - `int input(int element)` → 插入并调整。

    - `int output()` → 弹出堆顶。

    - `int get()` → 获取堆顶。

    - `int regular_all()` → 重建堆。

#### linked_list_singly / linked_list_doubly（链表）

- **单向链表** `linked_list_singly`：带头节点（头节点不存储有效数据）。

    - 构造：`linked_list_singly()` 空链表；`linked_list_singly(int... numbers)` 从给定数据构建。

    - 方法：`input`, `insert`, `remove_tail`, `remove_head`, `remove_element`, `sort_ascend`, `sort_descend` 等。

- **双向链表** `linked_list_doubly`：无头节点，存储 `head` 和 `tail` 引用，支持正向/反向索引。

    - 方法：`input_tail`, `input_head`, `element_at`, `index_forward`, `index_backward`, `traversal_forward/backward` 等。

#### queue（队列）

- 循环数组实现，默认容量256，自动扩容。

- **方法**：

    - `boolean is_empty()`, `boolean is_full()`

    - `int element_count()`

    - `int input(int element)`, `int input_more(int... elements)`

    - `int get()` → 查看队头。

    - `int output()` → 出队。

#### red_black_tree（红黑树）

- 使用 NIL 节点，提供插入、删除、中序遍历。

- **构造**：`red_black_tree()` 创建一个空树（头节点）。

- **方法**：

    - `boolean input(int element)` → 插入，重复返回 false。

    - `int input_more(int... elements)` → 批量插入，返回重复元素个数。

    - `boolean remove(int element)` → 删除元素。

    - `int[] traversal()` → 中序遍历升序序列。

    - `int get_depth(int element)` → 返回深度（根深度0）。

#### stack / stack_ascend / stack_descend / stack_max / stack_min（栈）

- **stack**：普通栈，数组实现，自动扩容。

- **stack_ascend**（单调递增栈）：入栈时弹出所有比新元素小的元素。

- **stack_descend**（单调递减栈）：入栈时弹出所有比新元素大的元素。

- **stack_max**：支持 `O(1)` 获取当前栈中最大值。

- **stack_min**：支持 `O(1)` 获取当前栈中最小值。

- 通用方法：`input`, `input_more`, `output`, `get`。

#### tree（树-孩子兄弟表示法）

- 节点包含 `element`、`child`（第一个孩子）、`next`（下一个兄弟）。

- **构造**：`tree(String treeString)` 例如 `"A{B{D,E},C{F,G,H,I}}"`。

- **方法**：`count`, `depth`, `traversal_preorder`, `traversal_postorder`, `traversal_levelorder`, `insert_to`, `remove`。

#### trie（字典树）

- 不区分大小写，每个节点包含26个子节点（仅小写字母）。

- **构造**：`trie(String... words)` 插入初始单词列表。

- **方法**：

    - `int count()` → 存储的不同单词数量。

    - `int depth()` → 树深度（即最长单词长度+1）。

    - `int input(String word)` → 插入单词，返回新增节点数，重复返回 `Integer.MIN_VALUE`。

    - `boolean exist(String word)` → 判断是否存在。

    - `String[] get_all_words()` → 返回所有单词（字典序）。

    - `boolean remove(String word)` → 删除单词。

#### huffman_tree_byte / huffman_tree_char（霍夫曼树）

- 分别用于 `byte[]` 和 `String` 的压缩与解压。

- **构造**：`huffman_tree_byte(byte[] data)` 或 `huffman_tree_char(String text)`。

- **方法**：

    - `String get_code(byte b)` / `String get_code(char c)` → 获取单个字符的霍夫曼编码。

    - `String encode(byte[] data)` / `String encode(String text)` → 压缩。

    - `byte[] decode(String code)` / `String decode(String code)` → 解压。

---

### 3.3 tools.date_time（日期时间）

#### datetime（日期时间）

- 支持公历（含公元前1年表示为 `year=0`，公元前2年表示为 `year=-1`，以此类推）。

- 时区范围 -12 到 +12，默认东八区（UTC+8）。

- **构造器**：多个重载，可指定年、月、日、时、分、秒、毫秒、时区，或使用当前时间戳。

- **静态方法**：

    - `int[] now()` → 获取默认时区当前时间数组 `{年,月,日,时,分,秒,毫秒,时区}`。

    - `long timestamp(...)` → 计算自公元元年1月1日0时0分0秒的毫秒数。

    - `int timestamp_day(...)` → 计算日时间戳（天数）。

- **实例方法**：

    - `boolean is_leap_year()` → 是否为闰年。

    - `int weekday()` → 星期（0=周日，1=周一，...，6=周六）。

    - `int day_in_year()` → 当年第几天。

    - `datetime add_day(int days)` → 返回新对象，日期偏移。

    - `long interval_day(datetime other)` → 相差天数。

    - `int compareTo(datetime other)` → 比较时间顺序。

#### calendar（日期常数）

- 提供常用常量：平年/闰年天数、月份天数表、前缀和表、星期基准等。

---

### 3.4 tools.geography（地理工具）

#### elevation_map（高程地图）

- 存储二维 double 数组，表示海拔（米）。

- **构造**：`elevation_map(int length, int width)` 或 `elevation_map(int side)`。

- **方法**：

    - `boolean calculate_statistics()` → 更新 `min`, `max`, `average`, `median`。

    - `histogram calculate_histogram()` → 返回100区间的直方图。

    - `double elevate(double inc)` / `double sink(double dec)` → 整体抬高/降低。

    - `double normalize(double newMin, double newMax)` → 线性拉伸至指定范围。

    - `double overlay_perlin_terrain(...)` → 叠加柏林噪声地形，支持种子、水平/垂直缩放、细节等级等参数。

#### geographic_coordinate（地理坐标）

- 存储经度、纬度（十进制度），并提供度分秒转换。

- **构造**：`geographic_coordinate(double lon, double lat)`。

- **方法**：`deg_to_dms`, `dms_to_deg`, `print_deg`, `print_dms`。

#### projected_coordinate（投影坐标）

- 平面直角坐标，单位米，东方向为 x 正，北方向为 y 正。

- **构造**：`projected_coordinate(double x, double y)` 或默认原点。

- **方法**：

    - `double distance(projected_coordinate other)`

    - `double azimuth_angle(projected_coordinate target)` → 方位角（0°=正北）。

    - `projected_coordinate destination(double azimuth, double distance)` → 已知方位角和距离求终点。

    - `static double area(projected_coordinate... vertices)` → 多边形面积（按逆时针顺序给出顶点）。

    - `static double perimeter(...)` → 多边形周长。

---

### 3.5 tools.mathematics（数学工具）

#### math（数学常数）

- 提供 `pi`, `e`, 质数表（`prime`，前1229个质数），阶乘表 `fact`，排列数 `A`，组合数 `C`，斐波那契数列 `fibonacci`。

#### maths（数学方法）

- **整数与浮点运算**：

    - `gcd`, `lcm`, `is_prime`, `prime_table`, `decompose`。

    - `quick_power`, `quick_power_mod`。

    - `lowbit`, `bit_count`, `length`（十进制位数）。

- **统计**：

    - `max`, `min`, `sum`, `average`, `median`, `mode`, `variance`, `standard_deviation`, `linear_regression`。

- **数组操作**：

    - `reverse_new`, `reverse_local`, `shuffle_new`, `shuffle_local`。

- **几何**：

    - `polygon_perimeter`, `polygon_area`。

- **表达式计算**：

    - `calculate(String expression)` → 计算四则运算表达式（支持 `+ - * / ( )` 及负号）。

- **数独求解**：

    - `sudoku_valid`, `sudoku_solve`（9×9，原地修改）。

#### complex_number（复数）

- 表示 `a + bi`，提供加减乘除、模长运算。

#### coordinate_cartesian（直角坐标）

- 类似 `projected_coordinate`，无地理语义，提供象限、距离、角度、中点、插值等方法。

#### determinant（行列式）

- 支持整数元素，可计算值、余子式、代数余子式、转置等。

#### histogram（直方图）

- 等距直方图，自动计算边界，支持下溢/上溢计数。

- **构造**：`histogram(int groups, double... data)` 或指定 `(groups, min, max)`。

- **方法**：`input`, `input_more` 插入数据。

#### matrix（矩阵）

- 整数矩阵，支持加、减、数乘、乘法、转置、余子式、伴随矩阵、幂运算等。

#### polynomial_equation（多项式方程）

- 解析形如 `"2x^2+3x+1=0"` 的方程，支持次数0~4（仅1、2次给出解析解，3、4次预留接口，5次以上提示不可解）。

- **构造**：`polynomial_equation(char unknown, String equation)`。

- **方法**：

    - `double[] solve()` → 求解实数根，并打印过程。

    - `String toString()` → 返回规范化方程字符串。

#### square_root（平方根）

- 将根号内整数化简为 `a√b` 形式。

- **构造**：`square_root(int n)` → 自动化简。

- **方法**：`double value()` → 小数近似值；`compareTo` 比较大小。

#### angle（角度）

- 存储度、分、秒，支持加法、除法（整数除）、格式化输出。

---

### 3.6 tools.two_dimensional_barcode（二维码）

#### meta（元数据常量）

- **类**：`tools.two_dimensional_barcode.meta`

- **功能**：提供 QR 二维码生成所需的全部常量数据，包括版本边长、编码模式掩码、纠错等级掩码、有限域（GF(256)）指数/对数表、生成多项式系数、编码长度位数、字母数字表、分组信息、对齐图案位置等。

- **主要常量**：

    - `side_length[]` → 各版本（1~40）的二维码边长（21~177）。

    - `mode_mask[]` → 编码模式掩码（数字=1, 字母数字=2, 字节=4, 日文=8）。

    - `error_correction_mask[]` → 纠错等级掩码（L=1, M=0, Q=3, H=2）。

    - `exponential_finite_field_256[]` / `logarithm_finite_field_256[]` → GF(256) 有限域运算表，用于 Reed-Solomon 纠错编码。

    - `generator_polynomial_coefficient[][]` → 各纠错码字数的生成多项式系数。

    - `alphanumeric_table[]` → 字母数字模式编码表（0-9, A-Z, 空格及符号共45个字符）。

    - `block_count_per_group[][][]` / `data_code_word_count_per_block[][][]` → 各版本、各纠错等级的分组和每块数据码字数。

    - `alignment_pattern_center_position[][][]` → 各版本对齐图案的中心坐标。

#### quick_response_code（二维码）

- **类**：`tools.two_dimensional_barcode.quick_response_code`

- **功能**：生成 QR 二维码（Quick Response Code），支持数字、字母数字、字节（UTF-8）、日文、ECI 五种编码模式，支持 L/M/Q/H 四种纠错等级，版本 1~40。

- **字段**：

    - `boolean field[][]` → 二维码的布尔矩阵（true=黑，false=白）。

    - `int side` → 二维码边长。

    - `int version` → 版本号（1~40）。

    - `int error_correction_level` → 纠错等级（1=L, 2=M, 3=Q, 4=H）。

    - `int mode` → 编码模式（0=数字, 1=字母数字, 2=字节, 3=日文, 4=ECI）。

    - `String encoded_text` → 被编码的原始文本。

- **构造器**：

    - `quick_response_code(String text)` → 自动选择编码模式和最小可用版本，默认 L 级纠错。

    - `quick_response_code(String text, int error_correction_level)` → 自动选择编码模式和最小可用版本，指定纠错等级。

    - `quick_response_code(String text, int mode, int version, int error_correction_level)` → 完全手动指定所有参数。

- **方法**：

    - `void display(int scale)` → 弹窗显示二维码图像，`scale` 为每个像素块的像素大小。

    - `String toString()` → 返回二维码的文本表示（Unicode 字符 ██ 和空格），包含版本和纠错等级信息。

- **注意**：`field` 矩阵索引为 `[y][x]`，即先行后列。显示时四周有4像素的白色边距。

---

## 4. 使用示例

``` Java
import tools.collection.sort;
import tools.data_structure.heap_ascend;
import tools.date.datetime;
import tools.geography.elevation_map;
import tools.mathematics.maths;
public class demo
{
    public static void main(String args[])
    {
        //排序
        int arr[]={5,2,9,1,5,6};
        sort.quick_dual_pivot(arr);
        //[1, 2, 5, 5, 6, 9]
        System.out.println(java.util.Arrays.toString(arr));
        //升序堆
        heap_ascend heap=new heap_ascend(3,1,4,1,5);
        //1
        System.out.println(heap.output());
        //日期
        datetime now=new datetime();
        //AD 2026/01/01 12:00:00.000 UTC+8（示例）
        System.out.println(now);
        // 高程地图 & 地形生成
        elevation_map map=new elevation_map(200,200);
        map.overlay_perlin_terrain(12345L,1000.0);
        map.calculate_statistics();
        System.out.println("最高海拔: "+map.max);
        //计算表达式
        double result=maths.calculate("(3+4)*2-5/2");
        //11.5
        System.out.println(result);
    }
}
```

---

## 如有疑问或需要进一步了解某个类的详细用法，请查阅源代码中的 Javadoc 注释。