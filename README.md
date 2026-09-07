# AIO

这是一个**Java工具包**，包含了一些常用算法和数据结构的实现。也编写了一些常用工具类。

本包中的源代码、源代码文件或整包可以直接引入代码或项目中使用，可将其复制到项目源代码目录中，import后可直接调用（类似java.util.*）。

这是一个简单Java项目，不使用如Maven的项目构建器。

## 本项目使用Visual Studio Code开发

- 建议您在Visual Studio Code中打开项目文件夹。

---

## 文件结构

- `根目录`

    - `.vscode`：此处为VSCode相关文件夹，包含基础设置文件，如`settings.json`。

    - `bin`：此文件夹默认不存在，进行首次编译后将自动创建。编译后的输出文件将默认生成在此文件夹中。

    - `doc`：此处为使用javadoc自动创建的文档。

    - `src`：此处为源代码。包含 `aio/` 工具包及测试类（如 `sort_test.java`、`elevation_test.java`、`qrc_test.java` 等）。各子包中均包含 `package-info.java` 包描述文件。

    - `.gitignore`：忽略描述文档，用于忽略编译产物（`*.class`、`*.jar` 等）、Windows 系统文件（`Thumbs.db`、`Desktop.ini` 等）及日志文件。

    - `README.md`：项目说明文档。

    - `构建源代码、字节码、文档及其Jar包并安装至本地Maven仓库.bat`：一键构建脚本，编译源代码、生成字节码和文档、打包Jar并安装至本地Maven仓库。

> 如果您想要自定义文件夹结构，请打开 `.vscode/settings.json` 并更新相关设置。（当您使用VSCode打开项目时，将提示创建此文件夹。）

## 测试与辅助类

`src/` 目录下除 `aio/` 工具包外，还包含以下测试类和辅助类：

- **App.java**：项目主入口类，默认导入所有工具包子包，方便快速测试。

- **sort_test.java**：排序算法性能测试，对比自定义排序与 `Arrays.sort` 的耗时。

- **ListMergeSortTest.java**：单向链表归并排序测试，验证 `linked_list_singly.sort_ascend()` 的正确性与性能。

- **big_integer_test.java**：高精度整数类 `big_integer` 的综合测试。

- **big_rational_test.java**：高精度有理数类 `big_rational` 的综合测试。

- **avl_tree_test.java**：AVL树 `avl_tree` 的功能测试。

- **b_plus_tree_test.java**：B+树 `b_plus_tree` 的功能测试。

- **binary_search_tree_test.java**：二叉查找树 `binary_search_tree` 的功能测试。

- **binary_tree_test.java**：二叉树 `binary_tree` 的功能测试。

- **datetime_test.java**：日期时间 `datetime` 的功能测试。

- **deque_test.java**：双端队列 `deque` 的功能测试。

- **elevation_test.java**：高程地图 `elevation_map` 的可视化测试，包含地形生成与统计面板。

- **expression_tree_test.java**：表达式树 `expression_tree` 的化简与计算测试。

- **function_test.java**：一元实函数 `function` 的遗传算法拟合测试。

- **linked_list_doubly_test.java**：双向链表 `linked_list_doubly` 的功能测试。

- **linked_list_singly_test.java**：单向链表 `linked_list_singly` 的功能测试。

- **qrc_test.java**：二维码生成 `quick_response_code` 的功能测试。

- **red_black_tree_test.java**：红黑树 `red_black_tree` 的功能测试。

- **tree_test.java**：树（孩子兄弟表示法）`tree` 的功能测试。

- **trie_test.java**：字典树 `trie` 的功能测试。

- **converting_array.java**：辅助工具类，用于将二维码数据码字数组转换为 `barcode.java` 中的常量格式，生成 `output.txt`。

## 快速开始

本文件中的任何代码（或包）可直接引入代码或项目中使用，可直接将源代码、源代码文件或整包复制到项目目录中，import后可直接使用（类似java.util.*）。不完整复制时请注意跨包依赖。

``` Java
import aio.mathematics.*;
import aio.collection.*;
import java.util.*;
class using_aio
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

# AIO说明书

本工具包提供了丰富的数据结构、算法、数学工具、日期处理、地理计算等功能，旨在帮助开发者快速实现常见任务。所有代码均为纯 Java 实现，不依赖第三方库。

## 目录
- [1. 项目概述](#1-项目概述)

- [2. 包结构](#2-包结构)

- [3. 详细说明](#3-详细说明)

    - [3.1 aio.collection（集合与算法）](#31-aiocollection集合与算法)

        - [concurrent_sort（并发排序）](#concurrent_sort并发排序)

        - [search（查找）](#search查找)

        - [sort（排序）](#sort排序)

        - [string（字符串工具）](#string字符串工具)

    - [3.2 aio.data_structure（数据结构）](#32-aiodata_structure数据结构)

        - [binary_indexed_tree（树状数组）](#binary_indexed_tree树状数组)

        - [binary_tree（二叉树）](#binary_tree二叉树)

        - [binary_search_tree（二叉查找树）](#binary_search_tree二叉查找树)

        - [avl_tree（AVL树）](#avl_treeavl树)

        - [b_plus_tree（B+树）](#b_plus_treeb树)

        - [deque（双端队列）](#deque双端队列)

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

    - [3.3 aio.date_time（日期时间）](#33-aiodate_time日期时间)

        - [datetime（日期时间）](#datetime日期时间)

        - [calendar（日期常数）](#calendar日期常数)

    - [3.4 aio.geography（地理工具）](#34-aiogeography地理工具)

        - [elevation_map（高程地图）](#elevation_map高程地图)

        - [geographic_coordinate（地理坐标）](#geographic_coordinate地理坐标)

        - [projected_coordinate（投影坐标）](#projected_coordinate投影坐标)

    - [3.5 aio.mathematics（数学工具）](#35-aiomathematics数学工具)

        - [math（数学常数）](#math数学常数)

        - [maths（数学方法）](#maths数学方法)

        - [big_integer（高精度整数）](#big_integer高精度整数)

        - [big_rational（高精度有理数）](#big_rational高精度有理数)

        - [complex（复数）](#complex复数)

        - [function / expression_tree（一元实函数 / 表达式树）](#function--expression_tree一元实函数--表达式树)

        - [point_planar（平面点）](#point_planar平面点)

        - [determinant（行列式）](#determinant行列式)

        - [histogram（直方图）](#histogram直方图)

        - [matrix（矩阵）](#matrix矩阵)

        - [polynomial_equation（多项式方程）](#polynomial_equation多项式方程)

        - [square_root（平方根）](#square_root平方根)

        - [angle（角度）](#angle角度)

    - [3.6 aio.encode_decode（编解码）](#36-aioencode_decode编解码)

        - [barcode（元数据常量）](#barcode元数据常量)

        - [quick_response_code（二维码）](#quick_response_code二维码)

- [4. 使用示例](#4-使用示例)

---

## 1. 项目概述

本工具包是一个自包含的 Java 库，提供了多种常用功能：

- **排序与查找**：包括冒泡、选择、插入、希尔、快速（单轴/双轴）、归并、计数、基数排序，以及线性/二分/插值查找。

- **并发排序**：基于双轴快速排序的多线程版本，可自动划分任务并行处理。

- **数据结构**：链表（单向/双向）、栈（普通/单调/最大/最小）、队列（循环）、堆（升序/降序）、二叉树、二叉查找树、AVL树、红黑树、B+树、字典树、霍夫曼树、图（邻接矩阵）、并查集、哈希表、树状数组等。

- **日期时间**：支持公历（含公元前），时区转换，日期间隔计算，星期计算等。

- **地理工具**：地理坐标（经纬度）、投影坐标（平面直角坐标）、高程地图（柏林噪声地形生成、统计分析等）。

- **数学工具**：复数、矩阵、行列式、直方图、多项式方程求解（1/2次）、平方根化简、数论函数（最大公因数、质数判断/分解）、组合数/排列数常数表、线性回归、一元实函数（遗传算法拟合）、平面点（直角/极坐标）等。

所有类均位于 `aio` 包下，按功能划分到子包。使用时请确保编译环境支持 Java 21 及以上。

---

## 2. 包结构

```
aio
├── collection              # 集合工具（排序、查找、字符串）
├── data_structure          # 数据结构（链表、树、堆、图、哈希表等）
├── date_time               # 日期时间处理
├── geography               # 地理坐标、高程地图
├── mathematics             # 数学算法与结构
└── encode_decode # 编解码（二维码 QR Code）
```

---

## 3. 详细说明

### 3.1 aio.collection（集合与算法）

#### concurrent_sort（并发排序）

- **类**：`concurrent_sort`

- **功能**：提供并发双轴快速排序。内部使用多线程加速，对于大数组效率提升明显。

- **方法**：

    - `public static void concurrent_quick_dual_pivot(int[] numbers)`

        - 对整型数组进行原地升序排序，使用多线程并发执行。

        - 内部通过 `concurrent_quick_dual_pivot_sort`（包级私有）实现，根据阈值（`concurrent_quick_dual_pivot_sort.threshold`，默认19683）决定是否创建新线程。

- **注意**：此类功能仍在验证中，非学习或极端性能需求建议使用 `java.util.Arrays.sort`。

#### search（查找）

- **类**：`aio.collection.search`

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

- **类**：`aio.collection.sort`

- **功能**：提供多种排序算法的静态实现，支持 `int[]` 和 `double[]`。

- **主要方法**：

    - `bubble`, `selection`, `insertion`, `shell`, `quick`, `quick_dual_pivot`, `merge`, `counting`, `radix`

    - 所有方法均**原地修改**输入数组。

    - 额外提供 `median_5`, `max_second_5`, `min_second_5` 用于从5个数中快速计算中位数、第二大、第二小（均支持 `int` 和 `double` 版本）。

- **示例**：`sort.quick_dual_pivot(arr);`

#### string（字符串工具）

- **类**：`aio.collection.string`

- **方法**：

    - `reverse(String s)` → 反转字符串。

    - `contains(String base, String pattern)` → 使用 KMP 算法判断 `base` 是否包含 `pattern`。

    - `is_palindrome(String s)` → 判断是否为回文串。

    - `longest_palindrome(String s)` → 返回最长回文子串（马拉车算法）。

    - `longest_palindrome_length(String s)` → 返回最长回文子串长度。

    - `match_regular_expression(String s, String regex)` → 支持 `.` 和 `*` 的正则匹配（类似 LeetCode 题目）。

---

### 3.2 aio.data_structure（数据结构）

以下类均位于 `aio.data_structure` 包中。

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

#### binary_search_tree（二叉查找树）

- 二叉查找树满足：左子树所有结点小于根结点，右子树所有结点大于根结点。采用头结点设计，头结点的左子结点为根结点。注意：非平衡版本，最坏情况下可能退化为链表。

- **构造器**：

    - `binary_search_tree()` → 空二叉查找树。

    - `binary_search_tree(int... elements)` → 从给定元素构建（重复元素忽略）。

- **方法**：

    - `int[] traversal()` → 中序遍历（升序）。

    - `boolean input(int element)` → 插入元素，重复返回 `false`。

    - `int input_more(int... elements)` → 批量插入，返回忽略的重复元素数量。

    - `int get_depth(int element)` → 获取元素深度（根深度为1），不存在返回 `Integer.MIN_VALUE`。

    - `boolean remove(int element)` → 删除元素。

    - `String toString()` → 返回括号表示法的字符串表示。

- **字段**：`element`（元素）、`left`（左子结点）、`right`（右子结点）、`parent`（父结点）。

#### avl_tree（AVL树）

- AVL树是一种自平衡二叉查找树，规则严格，读取操作更快，但修改操作效率略低。每个结点包含平衡因子（右子树高度-左子树高度），高度差至多为1。采用头结点设计，头结点的左子结点为根结点。

- **构造器**：

    - `avl_tree()` → 空AVL树。

    - `avl_tree(int... elements)` → 从给定元素构建AVL树（自动平衡，重复元素忽略）。

- **方法**：

    - `int[] traversal()` → 中序遍历（升序）。

    - `boolean input(int element)` → 插入元素，重复返回 `false`。

    - `static avl_tree left_rotate(avl_tree tree)` → 左旋，返回新根结点。

    - `static avl_tree right_rotate(avl_tree tree)` → 右旋，返回新根结点。

- **字段**：`element`（元素）、`balance_factor`（平衡因子）、`left`（左子结点）、`right`（右子结点）、`parent`（父结点）。

#### b_plus_tree（B+树）

- **构造器**：

    - `b_plus_tree(int order)` → 指定阶数（最小4）。

    - `b_plus_tree()` → 默认阶数256。

- **方法**：

    - `int count(int element)` → 返回 `element` 出现的次数（支持重复元素）。

    - `int count(int min, int max)` → 返回区间 `[min, max]` 内元素个数。

    - `int count()` → 总元素个数。

    - `int[] get(int min, int max)` → 返回区间 `[min, max]` 内所有元素（升序）。

    - `int[] traversal()` → 中序遍历所有叶子节点（升序）。

    - `boolean input(int element)` → 插入元素，若因插入导致根节点分裂则返回 `true`。

    - `boolean remove(int element)` → 删除一个匹配的元素，若因删除导致根节点合并则返回 `true`。

    - `int remove_all(int element)` → 删除所有匹配元素，返回删除的元素个数。

#### deque（双端队列）

- 继承自 `queue`，在队列两端都可以进行插入和删除操作。以循环数组实现，默认容量256。

- **构造器**：`deque(int capacity)` 指定容量；`deque()` 默认容量256。

- **方法**：

    - `int input_back(int element)` → 从队尾入队（同 `queue.input`）。

    - `int input_more_back(int... elements)` → 从队尾批量入队。

    - `int input_front(int element)` → 从队头入队。

    - `int input_more_front(int... elements)` → 从队头批量入队。

    - `int get_back()` → 获取队尾元素但不出队，空时返回 `Integer.MIN_VALUE`。

    - `int get_front()` → 获取队头元素但不出队（同 `queue.get`）。

    - `int output_back()` → 队尾元素出队，空时返回 `Integer.MIN_VALUE`。

    - `int output_front()` → 队头元素出队（同 `queue.output`）。

- 同时继承 `queue` 的所有方法（`is_empty()`, `element_count()`, `dilate()` 等）。

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

    - 查询方法：`is_empty()`, `element_count()`, `element_at(int index)`, `index_of(int element)`, `traversal()`。

    - 插入方法：`input_back(int)`, `input_more_back(int...)`, `input_list_back(list)`, `input_front(int)`, `input_more_front(int...)`, `input_list_front(list)`, `insert(int index, int)`, `insert_more(int index, int...)`, `insert_list(int index, list)`。

    - 删除方法：`remove_back(int count)`, `remove_front(int count)`, `remove_index(int index)`, `remove_element(int)`, `remove_element(int min, int max)`。

    - 排序方法：`sort_ascend()`, `sort_descend()`（归并排序实现）。

- **双向链表** `linked_list_doubly`：无头节点，存储 `head` 和 `tail` 引用，支持正向/反向索引。

    - 构造：`linked_list_doubly()` 空链表；`linked_list_doubly(int... numbers)` 从给定数据构建。

    - 查询方法：`is_empty()`, `element_count()`, `element_at(int index)`, `index_forward(int)`, `index_backward(int)`, `traversal_forward()`, `traversal_backward()`, `reverse_index(int)`, `min_index(int)`。

    - 插入方法：`input_back(int)`, `input_more_back(int...)`, `input_list_back(list)`, `input_front(int)`, `input_more_front(int...)`, `input_list_front(list)`, `insert(int index, int)`, `insert_more(int index, int...)`, `insert_list(int index, list)`。

    - 删除方法：`remove_back(int count)`, `remove_front(int count)`, `remove_index(int index)`, `remove_element(int)`, `remove_element(int min, int max)`。

#### queue（队列）

- 循环数组实现，默认容量256，自动扩容。

- **方法**：

    - `boolean is_empty()`, `boolean is_full()`

    - `int element_count()`, `int empty_count()` → 元素数量、剩余空间。

    - `int input(int element)`, `int input_more(int... elements)` → 入队。

    - `int get()` → 查看队头。

    - `int output()` → 出队。

    - `int dilate()`, `int dilate(int more_capacity)` → 扩容。

#### red_black_tree（红黑树）

- 使用 NIL 节点，提供插入、删除、中序遍历。

- **构造**：`red_black_tree()` 创建一个空树（头节点）。

- **方法**：

    - `boolean input(int element)` → 插入，重复返回 false。

    - `int input_more(int... elements)` → 批量插入，返回重复元素个数。

    - `boolean remove(int element)` → 删除元素。

    - `int[] traversal()` → 中序遍历升序序列。

    - `int get_depth(int element)` → 返回深度（根深度0）。

    - `static red_black_tree left_rotate(red_black_tree)` / `right_rotate(red_black_tree)` → 左旋/右旋操作。

#### stack / stack_ascend / stack_descend / stack_max / stack_min（栈）

- **stack**：普通栈，数组实现，自动扩容，默认容量16。

- **stack_ascend**（单调递增栈）：独立实现，入栈时弹出所有比新元素小的元素。`input()` 返回被弹出元素的数组 `int[]`，`input_more()` 返回 `int[][]`。

- **stack_descend**（单调递减栈）：独立实现，入栈时弹出所有比新元素大的元素。`input()` 返回被弹出元素的数组 `int[]`，`input_more()` 返回 `int[][]`。

- **stack_max**：继承自 `stack`，支持 `O(1)` 获取当前栈中最大值。

- **stack_min**：继承自 `stack`，支持 `O(1)` 获取当前栈中最小值。

- 通用方法：`is_empty()`, `is_full()`, `element_count()`, `empty_count()`, `input`, `input_more`, `output`, `get`, `dilate()`, `dilate(int more_capacity)`。

#### tree（树-孩子兄弟表示法）

- 节点包含 `element`、`child`（第一个孩子）、`next`（下一个兄弟）。

- **构造**：`tree(String treeString)` 例如 `"A{B{D,E},C{F,G,H,I}}"`。

- **方法**：`count`, `depth`, `traversal_preorder`, `traversal_postorder`, `traversal_levelorder`, `insert_to`, `remove`。

#### trie（字典树）

- 不区分大小写，每个节点包含26个子节点（仅小写字母）。

- **构造**：`trie(String... words)` 插入初始单词列表。

- **方法**：

    - `int count()` → 存储的不同单词数量。

    - `int depth()`, `int max_length()` → 树深度（最长单词长度+1）及最长单词长度。

    - `int input(String word)` → 插入单词，返回新增节点数，重复返回 `Integer.MIN_VALUE`。

    - `int input_more(String... words)` → 批量插入单词，返回新增单词数量。

    - `boolean exist(String word)` → 判断是否存在。

    - `String[] get_all_words()` → 返回所有单词（字典序）。

    - `boolean remove(String word)` → 删除单词。

    - `String toString()` → 返回所有单词的字符串表示（如 `{"abc","abd","def"}`）。

#### huffman_tree_byte / huffman_tree_char（霍夫曼树）

- 分别用于 `byte[]` 和 `String` 的压缩与解压。

- **构造**：`huffman_tree_byte(byte[] data)` 或 `huffman_tree_char(String text)`。

- **方法**：

    - `String get_code(byte b)` / `String get_code(char c)` → 获取单个字符的霍夫曼编码。

    - `String encode(byte[] data)` / `String encode(String text)` → 压缩。

    - `byte[] decode(String code)` / `String decode(String code)` → 解压。

---

### 3.3 aio.date_time（日期时间）

#### datetime（日期时间）

- 支持公历（含公元前1年表示为 `year=0`，公元前2年表示为 `year=-1`，以此类推）。

- 时区范围 -12 到 +12，默认东八区（UTC+8）。

- **构造器**：多个重载，可指定年、月、日、时、分、秒、毫秒、时区，或使用当前时间戳。

- **静态方法**：

    - `int[] now()` → 获取默认时区当前时间数组 `{年,月,日,时,分,秒,毫秒,时区}`。

    - `int[] now(int time_zone)` → 获取指定时区当前时间数组。

    - `int get_default_time_zone()` / `boolean set_default_time_zone(int time_zone)` → 获取/设置默认时区。

    - `long timestamp(...)` → 计算自公元元年1月1日0时0分0秒的毫秒数。

    - `int timestamp_day(...)` → 计算日时间戳（天数）。

    - `static boolean is_leap_year(int year)` → 判断指定年份是否为闰年。

    - `static int weekday(int year, int month, int day)` → 计算指定日期的星期。

    - `static int day_in_year(int year, int month, int day)` → 计算指定日期在当年中的第几天。

    - `static datetime add_day(int year, int month, int day, int add_day)` → 日期偏移，返回新对象。

    - `static long interval_day(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day)` → 计算日期间隔天数。

    - `static int second_in_day(int hour, int minute, int second)` → 计算指定时间在当天中的第几秒。

    - `static int interval_second_in_day(int start_hour, int start_minute, int start_second, int end_hour, int end_minute, int end_second)` → 计算同一天内时间间隔秒数。

- **实例方法**：

    - `boolean is_leap_year()` → 是否为闰年。

    - `int weekday()` → 星期（0=周日，1=周一，...，6=周六）。

    - `int day_in_year()` → 当年第几天。

    - `int second_in_day()` → 当天第几秒。

    - `datetime add_day(int days)` → 返回新对象，日期偏移。

    - `long interval_day(datetime other)` → 相差天数。

    - `int interval_second_in_day(datetime other)` → 同一天内相差秒数。

    - `int compareTo(datetime other)` → 比较时间顺序。

    - `String toString()` → 返回 `"AD 2026/01/01 12:00:00.000 UTC+8"` 格式字符串。

#### calendar（日期常数）

- 提供常用常量：平年/闰年天数、月份天数表、前缀和表、星期基准等。

---

### 3.4 aio.geography（地理工具）

#### elevation_map（高程地图）

- 存储二维 double 数组，表示海拔（米）。

- **构造**：`elevation_map(int length, int width)` 或 `elevation_map(int side)`。

- **方法**：

    - `boolean calculate_statistics()` → 更新 `min`, `max`, `average`, `median`。

    - `histogram calculate_histogram()` → 返回100区间的直方图。

    - `double elevate(double inc)` / `double sink(double dec)` → 整体抬高/降低。

    - `double normalize(double newMin, double newMax)` → 线性拉伸至指定范围。

    - `double normalize()` → 线性拉伸至 [0, 1] 范围。

    - `double linear_scale(double vertical_coefficient)` → 线性缩放（乘以系数）。

    - `double exponential_scale(double vertical_base)` → 指数缩放（底数幂次变换）。

    - `double exponential_normalize(double normalize_exponent)` → 指数归一化（先指数缩放再拉伸至 [0, 1]）。

    - `double secant_odd_normalize()` → 正割奇函数归一化（使用 secant 变换后拉伸至 [0, 1]）。

    - `double overlay_perlin_terrain(long seed, double horizontal_scale, int octaves, double persistence, double lacunarity, double vertical_scale)` → 叠加柏林噪声地形，支持种子、水平/垂直缩放、细节等级等参数。

    - `double overlay_perlin_terrain(long seed, double vertical_scale)` → 简化版，仅指定种子和垂直缩放。

    - `double overlay_perlin_terrain(double vertical_scale)` → 简化版，仅指定垂直缩放。

    - `double overlay_perlin_terrain(long seed)` → 简化版，仅指定种子。

    - `double overlay_perlin_terrain()` → 无参版，使用默认参数。

    - `String toString()` → 返回高程矩阵的文本表示。

#### geographic_coordinate（地理坐标）

- 存储经度、纬度（十进制度），并提供度分秒转换。

- **构造**：`geographic_coordinate(double lon, double lat)`。

- **方法**：`deg_to_dms`, `dms_to_deg`, `print_deg`, `print_dms`。

#### projected_coordinate（投影坐标）

- 平面直角坐标，单位米，东方向为 x 正，北方向为 y 正。

- **构造**：`projected_coordinate(double x, double y)` 或默认原点。

- **方法**：

    - `boolean move(double delta_x, double delta_y)` → 平移坐标。

    - `static projected_coordinate offset(projected_coordinate coordinate, double delta_x, double delta_y)` → 静态版平移，返回新坐标。

    - `double[] relative_position(projected_coordinate coordinate)` → 计算相对位置 `{Δx, Δy}`。

    - `double distance(projected_coordinate other)` → 计算两点距离。

    - `static double distance(double x0, double y0, double xt, double yt)` → 静态版距离计算。

    - `boolean equals_approximate(projected_coordinate coordinate, double tolerance)` → 容差近似相等判断。

    - `double azimuth_angle(projected_coordinate target)` → 方位角（0°=正北）。

    - `static double azimuth_angle(double x0, double y0, double xt, double yt)` → 静态版方位角计算。

    - `projected_coordinate destination(double azimuth, double distance)` → 已知方位角和距离求终点。

    - `projected_coordinate middle_point(projected_coordinate target)` → 求中点坐标。

    - `projected_coordinate linear_interpolation(projected_coordinate target, double ratio)` → 线性插值。

    - `double move_distance_towards(projected_coordinate target, double move_distance)` → 向目标移动指定距离。

    - `double move_ratio_towards(projected_coordinate target, double move_ratio)` → 按比例向目标移动。

    - `static double area(projected_coordinate... vertices)` → 多边形面积（按逆时针顺序给出顶点）。

    - `static double perimeter(...)` → 多边形周长。

    - `String toString()` / `boolean equals(Object)` → 字符串表示与相等判断。

---

### 3.5 aio.mathematics（数学工具）

#### math（数学常数）

- 提供 `pi`, `e`, 质数表（`prime`，前1229个质数），阶乘表 `fact`，排列数 `A`，组合数 `C`，斐波那契数列 `fibonacci`。

#### maths（数学方法）

- **整数与浮点运算**：

    - `factors(int number)` → 计算一个整数的所有因子。

    - `gcd`, `lcm` → 最大公因数、最小公倍数。

    - `is_prime`, `prime_table`, `decompose` → 质数判断、质数表生成、质因数分解。

    - `power(int, int)` / `power(long, long)` → 快速幂（整数幂运算）。
    - `power_mod1000000007(long, long)` → 快速幂取模 1000000007。
    - `power_mod(long, long, long)` → 快速幂取模（自定义模数）。

    - `lowbit` → 最低位1的权值。

    - `bit_count` → 二进制表示中1的个数。

    - `length` → 十进制位数。

    - `binary` → 返回二进制表示（布尔数组）。

    - `binary_weight` → 返回二进制表示中每个1的权值。

    - `linear_interpolation` → 线性插值。

    - `mathematical_order_number` → 区间内数字的数学顺序升序序列。

    - `dictionary_order_number_to` → 区间内数字的字典序升序序列。

    - `number_combination_count` → 由给定数字组成的无前导零的不同数字个数。

- **统计**：

    - `max`, `min`, `sum`, `average` → 基本统计量（支持 `int` 和 `double`）。

    - `max_index`, `min_index` → 首个最大值/最小值的索引。

    - `weighted_average` → 加权平均值（支持 `int`/`double` 元素和权重交叉组合）。

    - `median`, `mode` → 中位数、众数。

    - `variance`, `variance_average` → 方差、方差平均值。

    - `standard_deviation`, `standard_deviation_average` → 标准差、标准差平均值。

    - `linear_regression` → 一元线性回归。

- **数组操作**：

    - `reverse_new`, `reverse_local` → 数组反转（支持 `int[]` 和 `double[]`，支持指定区间）。

    - `shuffle_new`, `shuffle_local` → 随机打乱（支持 `int[]` 和 `double[]`，支持指定区间）。

    - `distinct_sort_new`, `distinct_sort_local` → 去重并升序排序。

- **几何**：

    - `polygon_perimeter`, `polygon_area`。

    - `matrix_multiply(int[][] factor_left, int[][] factor_right)` → 矩阵乘法。

- **表达式计算**：

    - `calculate(String expression)` → 计算四则运算表达式（支持 `+ - * / ( )` 及负号）。

- **数独求解**：

    - `sudoku_valid`, `sudoku_solve`（9×9，原地修改）。

#### big_integer（高精度整数）

- 高精度整数类，支持任意大小的整数运算。内部以整型数组 `int[]` 以 2^31 进制表示整数的绝对值，低位优先存储，符号表示整数的正负。

- **字段**：

    - `int number[]` → 整数的整型数组低位优先表示（2^31 进制）。
    - `int size` → 有效位数。
    - `int sign` → 符号（1=正，-1=负，0=零）。

- **构造器**：

    - `big_integer(String number_string)` → 通过整数字符串构造（支持负号和前导零）。
    - `big_integer(int number)` → 通过基本类型 `int` 构造。
    - `big_integer(int number_array[], int sign)` → 通过低位优先的整型数组和符号构造（会拷贝数组）。
    - `big_integer(int number_array[], int size, int sign)` → 直接使用输入的数组、位数和符号构造（不拷贝，不检查）。

- **静态方法（底层运算）**：

    - `static int compare_absolute(int[], int[])` → 比较两个整型数组表示的绝对值大小。

- **静态方法（big_integer 对象运算）**：

    - `static big_integer add(big_integer, big_integer)` → 两高精度整数加法。
    - `static big_integer subtract(big_integer, big_integer)` → 两高精度整数减法。
    - `static big_integer multiply(big_integer, int)` → 高精度整数与基本类型整数乘法。
    - `static big_integer multiply(big_integer, big_integer)` → 两高精度整数乘法。
    - `static big_integer[] divide(big_integer, int)` → 除以基本类型整数，返回 `{商, 余数}`。若除数为0则返回 `null`。
    - `static big_integer[] divide(big_integer, big_integer)` → 两高精度整数除法，返回 `{商, 余数}`。若除数为0则返回 `null`。
    - `static big_integer gcd(big_integer, big_integer)` → 两高精度整数的最大公因数。`0` 与 `0` 的 `gcd` 定义为 `0`。
    - `static big_integer lcm(big_integer, big_integer)` → 两高精度整数的最小公倍数。`0` 与 `0` 的 `lcm` 定义为 `0`。
    - `static big_integer power(big_integer, int)` → 高精度整数的正整数次幂。若指数为负数或底数与指数同时为0则返回 `null`。
    - `static big_integer factorial(int)` → 计算整数的阶乘。若整数为负数则返回 `null`。

- **实例方法**：

    - `boolean increment()` → 自增1，返回位数是否改变。
    - `boolean decrement()` → 自减1，返回位数是否改变。
    - `int compareTo(big_integer another)` → 比较当前对象与指定对象的数值大小（实现 `Comparable<big_integer>` 接口）。
    - `boolean equals(Object another)` → 判断与指定对象是否相等。
    - `int hashCode()` → 返回哈希值。
    - `String toString()` → 返回整数的十进制字符串表示。

- **注意**：此类实现了 `Comparable<big_integer>` 接口。除数为 `0` 时除法返回 `null`。底数与指数同时为 `0` 时幂运算返回 `null`。

#### big_rational（高精度有理数）

- 高精度有理数类，支持任意大小的分数运算。有理数即分数，包含整数、有限小数和无限循环小数。

- 内部以两个 `big_integer` 对象 `numerator`（分子）和 `denominator`（分母）存储。当 `denominator` 为 `null` 时表示整数。

- **字段**：

    - `big_integer numerator` → 分子。
    - `big_integer denominator` → 分母（`null` 时表示整数）。
    - `int mode` → 输出格式（`>0` 小数，`=0` 分数+小数，`<0` 分数）。

- **构造器**：多个重载，支持从各种形式构造：

    - `big_rational(String rational_string)` → 从小数字符串构造（如 `"0.5"`、`"0.(3)"`，用括号表示循环节）。
    - `big_rational(String numerator_string, String denominator_string, int mode)` → 从分子分母字符串和输出模式构造。
    - `big_rational(String numerator_string, String denominator_string)` → 从分子分母字符串构造（默认 `mode=0`）。
    - `big_rational(int numerator, int denominator, int mode)` → 从基本类型整数分子分母和输出模式构造。
    - `big_rational(int numerator, int denominator)` → 从基本类型整数分子分母构造（默认 `mode=0`）。
    - `big_rational(big_integer numerator, big_integer denominator, int mode)` → 从高精度整数分子分母和输出模式构造。
    - `big_rational(big_integer numerator, big_integer denominator)` → 从高精度整数分子分母构造（默认 `mode=0`）。
    - `big_rational(big_integer numerator, big_integer denominator, int sign, int mode)` → 从高精度整数分子分母、符号和输出模式构造。

- **静态方法（有理数运算）**：

    - `static big_integer[] common_denominator(big_rational, big_rational)` → 通分两个有理数（会修改传入对象），返回两个 `big_integer` 的数组，分别为两个有理数各自通分乘数。
    - `static big_rational add(big_rational, big_rational)` → 有理数加法，返回两数之和。
    - `static big_rational subtract(big_rational, big_rational)` → 有理数减法，返回两数之差。
    - `static big_rational multiply(big_rational, big_rational)` → 有理数乘法，返回两数之积。
    - `static big_rational divide(big_rational, big_rational)` → 有理数除法，返回两数之商；若除数为0则返回 `null`。
    - `static big_rational power(big_rational, int)` → 有理数的整数次幂运算，返回幂结果。

- **实例方法**：

    - `big_integer reduce()` → 约分当前有理数对象（会修改调用对象），返回分子分母的最大公因数。
    - `String toString()` → 按 `mode` 格式输出。

#### complex（复数）

- 表示 `a + bi`，提供加减乘除、模长运算。

#### function / expression_tree（一元实函数 / 表达式树）

- **类**：`aio.mathematics.function` 及其内部类 `expression_tree`

- **功能**：一元实函数类，使用遗传算法（Genetic Programming）通过给定坐标点拟合出函数表达式。内部使用表达式树表示数学表达式，支持加法、减法、乘法、除法、指数、对数六种运算。

- **expression_tree（表达式树）**：

    - 结点类型：`-1`=自变量x，`0`=常量，`1`=加法，`2`=减法，`3`=乘法，`4`=除法，`5`=指数，`6`=对数。

    - **构造器**：

        - `expression_tree(int type, expression_tree left, expression_tree right)` → 指定类型和子树。

        - `expression_tree(int type, double value)` → 指定类型和常量值。

        - `expression_tree(double value)` → 常量结点。

        - `expression_tree(int type)` → 指定类型（默认值0.0）。

        - `expression_tree(int max_tree_depth, boolean full, double subtree_chance, double constant_range)` → 随机生成表达式树。

    - **方法**：

        - `int simplify()` → 化简表达式树，返回删除的结点数。

        - `int count()` → 结点数。

        - `int depth()` → 树深度。

        - `expression_tree get_node_random()` → 随机获取一个结点。

        - `double calculate(double x)` → 计算指定自变量值的函数值。

        - `double fitness(double x[], double y[], double complexity_penalty)` → 计算对点集的适应度（均方误差+复杂度惩罚）。

        - `static expression_tree[] crossover(expression_tree, expression_tree)` → 交叉互换两个表达式树的结点。

        - `int mutate(int max_depth, double constant_range)` → 突变表达式树的一个结点。

        - `expression_tree clone()` → 深拷贝。

        - `String toString()` → 返回表达式字符串。

- **function（一元实函数）**：

    - **遗传算法参数**（静态字段）：`population_size`（种群规模，默认6000）、`max_generation`（最大迭代次数，默认86400000）、`best_rate`（最佳保留比例，默认0.005）、`survival_rate`（选择率，默认0.02）、`new_individual_rate`（新个体比例，默认0.2）、`crossover_rate`（交叉概率，默认0.7）、`mutation_rate`（突变概率，默认0.3）、`max_tree_depth`（最大树深度，默认7）。

    - **构造器**：

        - `function(double... coordinates_xn_yn)` → 通过x1,y1,x2,y2,...格式的坐标拟合。

        - `function(double coordinates_xn[], double coordinates_yn[])` → 通过两个坐标数组拟合。

    - **方法**：

        - `double calculate(double x)` → 计算函数值。

        - `double[] calculate(double... x)` → 批量计算函数值。

        - `String toString()` → 返回 `"y=..."` 格式的函数字符串。

    - **字段**：`function_tree`（表达式树）、`fitness`（适应度）。

#### point_planar（平面点）

- 平面点表示平面中的一个位置，同时包含直角坐标（x, y）和极坐标（ρ, θ）表示。提供象限、距离、角度、中点、插值、多边形周长/面积等方法。

- **构造**：`point_planar(double rho_x, double theta_y, boolean true_polar_false_cartesian)` 可选择极坐标或直角坐标构造；`point_planar()` 默认原点。

- **方法**：

    - `void calculate_polar_coordinate()` / `void calculate_cartesian_coordinate()` → 直角坐标与极坐标互转。

    - `static double[] cartesian_to_polar(double x, double y)` / `static double[] polar_to_cartesian(double rho, double theta)` → 静态版坐标转换。

    - `int quadrant()` / `static int quadrant(double x, double y)` → 判断象限（0=原点，1~4=象限，5=坐标轴）。

    - `void add(point_planar)` / `static point_planar add(point_planar, point_planar)` → 坐标加法。

    - `void subtract(point_planar)` / `static point_planar subtract(...)` → 坐标减法。

    - `void multiply_scalar(double)` / `static point_planar multiply_scalar(...)` → 标量乘法。

    - `double distance(point_planar)` / `double distance(double, double, double, double)` → 距离计算。

    - `double angle(point_planar target)` / `double angle(double xt, double yt)` / `static double angle(double, double, double, double)` → 角度（度，0°=正东）。

    - `point_planar middle_point(point_planar target)` → 中点。

    - `point_planar linear_interpolation(point_planar target, double ratio)` → 线性插值。

    - `double move_distance_towards(point_planar target, double move_distance)` / `double move_ratio_towards(...)` → 向目标移动。

    - `static double perimeter(point_planar... coordinates_ccw)` / `static double area(...)` → 多边形周长/面积。

    - `String toString()` → 字符串表示（同时显示直角坐标和极坐标）。

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

- **方法**：

    - `static int[] sqrt(int number)` → 静态方法，化简平方根，返回 `{系数, 根号内数}`。

    - `static String string(int number)` → 静态方法，返回格式化字符串（如 `"2√3"`）。

    - `double value()` → 小数近似值。

    - `int compareTo(square_root comparing)` → 比较大小。

    - `String toString()` → 返回化简后的字符串表示。

#### angle（角度）

- **字段**：

    - `int degree` / `int minute` / `int second` → 度、分、秒。

- **构造**：`angle(int degree, int minute, int second)` 或 `angle(int degree, int minute)` 或 `angle(int degree)` 或 `angle()`。

- **方法**：

    - `double angle_to_deg()` → 转换为十进制角度。

    - `static double angle_to_deg(int degree, int minute, int second)` → 静态版角度转换。

    - `double angle_to_rad()` → 转换为弧度。

    - `static double angle_to_rad(int degree, int minute, int second)` → 静态版弧度转换。

    - `boolean add(angle angle)` → 角度加法，修改当前对象。

    - `static int[] sum(angle... angles)` → 多个角度求和，返回 `{度, 分, 秒}`。

    - `double divide(int number)` → 角度除法（整数除），返回十进制角度商。

    - `int reform()` → 规范化角度（进位处理），返回符号。

    - `String toString()` → 返回格式化字符串。

---

### 3.6 aio.encode_decode（编解码）

#### barcode（二维码元数据常量）

- **类**：`aio.encode_decode.barcode`

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

- **类**：`aio.encode_decode.quick_response_code`

- **功能**：生成 QR 二维码（Quick Response Code），支持数字、字母数字、字节（UTF-8）、日文、ECI 五种编码模式，支持 L/M/Q/H 四种纠错等级，版本 1~40。内部实现包括数据编码、Reed-Solomon 纠错码生成、功能图案与对齐图案绘制、数据位流填充、掩膜评分与选择等完整 QR 码生成流程。

- **字段**：

    - `boolean field[][]` → 二维码的布尔矩阵（true=黑，false=白），索引为 `[y][x]`。

    - `int side` → 二维码边长，公式为 `(version-1)*4+21`。

    - `int version` → 版本号（1~40）。

    - `int error_correction_level` → 纠错等级（1=L, 2=M, 3=Q, 4=H）。

    - `int mode` → 编码模式（0=数字, 1=字母数字, 2=字节, 3=日文, 4=ECI）。

    - `int mask` → 最终选择的掩膜编号（0~7），由内部评分算法自动选出最优掩膜。

    - `String encoded_text` → 被编码的原始文本。

- **构造器**：

    - `quick_response_code(String text)` → 自动选择编码模式和最小可用版本，默认 L 级纠错。

    - `quick_response_code(String text, int error_correction_level)` → 自动选择编码模式和最小可用版本，指定纠错等级。

    - `quick_response_code(String text, int error_correction_level, int version, int mode)` → 完全手动指定所有参数。

- **静态方法（encode）**：

    - `static boolean[][] encode(String text)` → 自动选择编码模式和最小可用版本，默认 L 级纠错，返回二维码布尔矩阵。

    - `static boolean[][] encode(String text, int error_correction_level)` → 自动选择编码模式和最小可用版本，指定纠错等级，返回二维码布尔矩阵。

    - `static boolean[][] encode(String text, int error_correction_level, int version, int mode)` → 完全手动指定参数，返回二维码布尔矩阵。编码过程中会打印纠错等级、版本、编码模式等调试信息。

- **实例方法（display）**：

    - `void display()` → 弹窗显示二维码，自适应像素块大小（最大12像素），窗口标题显示版本、纠错等级和掩膜编号。

    - `void display(int scale)` → 弹窗显示二维码，指定每个像素块的像素大小，窗口标题同上。

- **静态方法（display）**：

    - `static void display(boolean[][] field)` → 弹窗显示给定的二维码布尔矩阵，自适应像素块大小（最大12像素）。

    - `static void display(boolean[][] field, int scale)` → 弹窗显示给定的二维码布尔矩阵，指定每个像素块的像素大小。

- **其他方法**：

    - `String toString()` → 返回二维码的文本表示，包含纠错等级、版本、编码模式、掩膜编号信息，以及用 Unicode 字符（██ 和空格）绘制的二维码图形。

- **注意**：`field` 矩阵索引为 `[y][x]`，即先行后列。显示时四周有4像素的白色边距。`version` 为 -1 表示构造失败（如版本号越界或文本过长超出容量）。

---

## 4. 使用示例

``` Java
import aio.collection.sort;
import aio.data_structure.heap_ascend;
import aio.date_time.datetime;
import aio.geography.elevation_map;
import aio.mathematics.maths;
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