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

    - `src`：此处为源代码。包含 `aio/` 工具包及测试类（如 `SortTest.java`、`ElevationTest.java`、`QRCTest.java` 等）。各子包中均包含 `package-info.java` 包描述文件。

    - `.gitignore`：忽略描述文档，用于忽略编译产物（`*.class`、`*.jar` 等）、Windows 系统文件（`Thumbs.db`、`Desktop.ini` 等）及日志文件。

    - `README.md`：项目说明文档。

    - `构建源代码、字节码、文档及其Jar包并安装至本地Maven仓库.bat`：一键构建脚本，编译源代码、生成字节码和文档、打包Jar并安装至本地Maven仓库。

> 如果您想要自定义文件夹结构，请打开 `.vscode/settings.json` 并更新相关设置。（当您使用VSCode打开项目时，将提示创建此文件夹。）

## 测试与辅助类

`src/` 目录下除 `aio/` 工具包外，还包含以下测试类和辅助类：

- **App.java**：项目主入口类，默认导入所有工具包子包，方便快速测试。

- **SortTest.java**：排序算法性能测试，对比自定义排序与 `Arrays.sort` 的耗时。

- **ListMergeSortTest.java**：单向链表归并排序测试，验证 `LinkedListSingly.sortAscend()` 的正确性与性能。

- **BigIntegerTest.java**：高精度整数类 `BigInteger` 的综合测试。

- **BigRationalTest.java**：高精度有理数类 `BigRational` 的综合测试。

- **AvlTreeTest.java**：AVL树 `AvlTree` 的功能测试。

- **BPlusTreeTest.java**：B+树 `BPlusTree` 的功能测试。

- **BinarySearchTreeTest.java**：二叉查找树 `BinarySearchTree` 的功能测试。

- **BinaryTreeTest.java**：二叉树 `BinaryTree` 的功能测试。

- **DatetimeTest.java**：日期时间 `Datetime` 的功能测试。

- **DequeTest.java**：双端队列 `Deque` 的功能测试。

- **ElevationTest.java**：高程地图 `ElevationMap` 的可视化测试，包含地形生成与统计面板。

- **ExpressionTreeTest.java**：表达式树 `ExpressionTree` 的化简与计算测试。

- **FunctionFittingTest.java** / **FunctionTest.java**：一元实函数 `Function` 的遗传算法拟合测试。

- **LinkedListDoublyTest.java**：双向链表 `LinkedListDoubly` 的功能测试。

- **LinkedListSinglyTest.java**：单向链表 `LinkedListSingly` 的功能测试。

- **QRCTest.java**：二维码生成 `QuickResponseCode` 的功能测试。

- **RedBlackTreeTest.java**：红黑树 `RedBlackTree` 的功能测试。

- **TreeTest.java**：树（孩子兄弟表示法）`Tree` 的功能测试。

- **TrieTest.java**：字典树 `Trie` 的功能测试。

- **ConvertingArray.java**：辅助工具类，用于将二维码数据码字数组转换为 `Barcode.java` 中的常量格式，生成 `output.txt`。

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
        //使用mathematics包中Maths类的calculate方法计算表达式，输出12.5
        System.out.println(Maths.calculate("(5+4)*3/2-1"));
        //使用collection包中Sort类的quick方法对数组num进行快速排序，输出[1,2,4,5,7,8]
        int num[]={7,1,4,2,8,5};
        Sort.quick(num);
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

        - [ConcurrentSort（并发排序）](#concurrentsort并发排序)

        - [Search（查找）](#search查找)

        - [Sort（排序）](#sort排序)

        - [Strings（字符串工具）](#strings字符串工具)

    - [3.2 aio.datastructure（数据结构）](#32-aiodatastructure数据结构)

        - [BinaryIndexedTree（树状数组）](#binaryindexedtree树状数组)

        - [BinaryTree（二叉树）](#binarytree二叉树)

        - [BinarySearchTree（二叉查找树）](#binarysearchtree二叉查找树)

        - [AvlTree（AVL树）](#avltreeavl树)

        - [BPlusTree（B+树）](#bplustreeb树)

        - [Deque（双端队列）](#deque双端队列)

        - [DisjointSet / DisjointSetElement（并查集）](#disjointset--disjointsetelement并查集)

        - [Graph（图）](#graph图)

        - [HashMap（哈希表）](#hashmap哈希表)

        - [Heap / HeapAscend / HeapDescend（堆）](#heap--heapascend--heapdescend堆)

        - [LinkedListSingly / LinkedListDoubly（链表）](#linkedlistsingly--linkedlistdoubly链表)

        - [Queue（队列）](#queue队列)

        - [RedBlackTree（红黑树）](#redblacktree红黑树)

        - [Stack / StackAscend / StackDescend / StackMax / StackMin（栈）](#stack--stackascend--stackdescend--stackmax--stackmin栈)

        - [Tree（树-孩子兄弟表示法）](#tree树-孩子兄弟表示法)

        - [Trie（字典树）](#trie字典树)

        - [HuffmanTreeByte / HuffmanTreeChar（霍夫曼树）](#huffmantreebyte--huffmantreechar霍夫曼树)

    - [3.3 aio.datetime（日期时间）](#33-aiodatetime日期时间)

        - [Datetime（日期时间）](#datetime日期时间)

        - [Calendar（日期常数）](#calendar日期常数)

    - [3.4 aio.geography（地理工具）](#34-aiogeography地理工具)

        - [ElevationMap（高程地图）](#elevationmap高程地图)

        - [GeographicCoordinate（地理坐标）](#geographiccoordinate地理坐标)

        - [ProjectedCoordinate（投影坐标）](#projectedcoordinate投影坐标)

    - [3.5 aio.mathematics（数学工具）](#35-aiomathematics数学工具)

        - [Maths（数学常数与方法）](#maths数学常数与方法)

        - [BigInteger（高精度整数）](#biginteger高精度整数)

        - [BigRational（高精度有理数）](#bigrational高精度有理数)

        - [Complex（复数）](#complex复数)

        - [Function / ExpressionTree（一元实函数 / 表达式树）](#function--expressiontree一元实函数--表达式树)

        - [PointPlanar（平面点）](#pointplanar平面点)

        - [Determinant（行列式）](#determinant行列式)

        - [Histogram（直方图）](#histogram直方图)

        - [Matrix（矩阵）](#matrix矩阵)

        - [PolynomialEquation（多项式方程）](#polynomialequation多项式方程)

        - [SquareRoot（平方根）](#squareroot平方根)

        - [Angle（角度）](#angle角度)

    - [3.6 aio.endecode（编解码）](#36-aioendecode编解码)

        - [Barcode（元数据常量）](#barcode元数据常量)

        - [QuickResponseCode（二维码）](#quickresponsecode二维码)

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
├── datastructure           # 数据结构（链表、树、堆、图、哈希表等）
├── datetime                # 日期时间处理
├── geography               # 地理坐标、高程地图
├── mathematics             # 数学算法与结构
└── endecode                # 编解码（二维码 QR Code）
```

---

## 3. 详细说明

### 3.1 aio.collection（集合与算法）

#### ConcurrentSort（并发排序）

- **类**：`ConcurrentSort`

- **功能**：提供并发双轴快速排序。内部使用多线程加速，对于大数组效率提升明显。

- **方法**：

    - `public static void concurrentQuickDualPivot(int[] numbers)`

        - 对整型数组进行原地升序排序，使用多线程并发执行。

        - 内部通过 `ConcurrentQuickDualPivotSort`（包级私有）实现，根据阈值（`ConcurrentQuickDualPivotSort.threshold`，默认19683）决定是否创建新线程。

- **注意**：此类功能仍在验证中，非学习或极端性能需求建议使用 `java.util.Arrays.sort`。

#### Search（查找）

- **类**：`aio.collection.Search`

- **方法**：

    - `linearSearch(int[] numbers, int target)`

        - 线性查找第一个匹配的索引，未找到返回 `Integer.MIN_VALUE`。

    - `binarySearch(int[] numbers, int target)` 

        - 二分查找（要求数组已升序），返回索引或 `Integer.MIN_VALUE`。

    - `binarySearchFirst(int[] numbers, int target)`

        - 返回第一个大于等于 `target` 的索引（类似 `lower_bound`）。

    - `binarySearchBetween(int[] numbers, int min, int max)`

        - 返回数值在 `[min, max]` 区间内的元素个数（数组需升序）。

    - `interpolationSearch(int[] numbers, int target)`

        - 插值查找（要求数组均匀分布且升序），返回索引或 `Integer.MIN_VALUE`。

#### Sort（排序）

- **类**：`aio.collection.Sort`

- **功能**：提供多种排序算法的静态实现，支持 `int[]` 和 `double[]`。

- **主要方法**：

    - `bubble`, `selection`, `insertion`, `shell`, `quick`, `quickDualPivot`, `merge`, `counting`, `radix`

    - 所有方法均**原地修改**输入数组。

    - 额外提供 `median5`, `maxSecond5`, `minSecond5` 用于从5个数中快速计算中位数、第二大、第二小（均支持 `int` 和 `double` 版本）。

- **示例**：`Sort.quickDualPivot(arr);`

#### Strings（字符串工具）

- **类**：`aio.collection.Strings`

- **方法**：

    - `reverse(String s)` → 反转字符串。

    - `containingIndex(String base, String pattern)` → 使用 KMP 算法查找 `pattern` 在 `base` 中首次出现的起始索引，未找到返回 `-1`。

    - `containingCount(String base, String pattern)` → 使用 KMP 算法计算 `pattern` 在 `base` 中出现的次数。

    - `isPalindrome(String s)` → 判断是否为回文串。

    - `longestPalindrome(String s)` → 返回最长回文子串（马拉车算法）。

    - `longestPalindromeLength(String s)` → 返回最长回文子串长度。

    - `matchRegularExpression(String s, String regex)` → 支持 `.` 和 `*` 的正则匹配（类似 LeetCode 题目）。

---

### 3.2 aio.datastructure（数据结构）

以下类均位于 `aio.datastructure` 包中。

#### BinaryIndexedTree（树状数组）

- **构造器**：

    - `BinaryIndexedTree(int length)` → 初始全0。

    - `BinaryIndexedTree(int[] original)` → 用原数组初始化。

- **方法**：

    - `void add(int index, int addend)` → 在 `index` 处增加 `addend`（索引从0开始）。

    - `long sumPrefix(int index)` → 前缀和 `[0..index]`。

    - `long sumInterval(int left, int right)` → 区间和 `[left, right]`。

#### BinaryTree（二叉树）

- **构造器**：

    - `BinaryTree(String treeString)` → 从括号表示法构建，例如 `"A{B{D,E},C{F,G}}"`。

    - `BinaryTree(int[] preorder, int[] inorder)` → 根据先序+中序构建。

    - `BinaryTree(int[] inorder, int[] postorder, int dummy)` → 根据中序+后序构建。

- **方法**：

    - `int count()` → 节点数。

    - `int depth()` → 深度（根深度为1）。

    - `boolean equals(Object anotherTree)` → 结构相同且元素相等。

    - `int[] traversalPreorder()/traversalInorder()/traversalPostorder()/traversalLevelorder()` → 返回遍历序列。

    - `int input(int element)` → 按层序插入元素（返回父节点值）。

    - `int remove(int element)` → 删除第一个遇到的节点（按先序）。

    - `void invert()` → 镜像翻转。

#### BinarySearchTree（二叉查找树）

- 二叉查找树满足：左子树所有结点小于根结点，右子树所有结点大于根结点。采用头结点设计，头结点的左子结点为根结点。注意：非平衡版本，最坏情况下可能退化为链表。

- **构造器**：

    - `BinarySearchTree()` → 空二叉查找树。

    - `BinarySearchTree(int... elements)` → 从给定元素构建（重复元素忽略）。

- **方法**：

    - `int[] traversal()` → 中序遍历（升序）。

    - `boolean input(int element)` → 插入元素，重复返回 `false`。

    - `int inputMore(int... elements)` → 批量插入，返回忽略的重复元素数量。

    - `int getDepth(int element)` → 获取元素深度（根深度为1），不存在返回 `Integer.MIN_VALUE`。

    - `boolean remove(int element)` → 删除元素。

    - `String toString()` → 返回括号表示法的字符串表示。

- **字段**：`element`（元素）、`left`（左子结点）、`right`（右子结点）、`parent`（父结点）。

#### AvlTree（AVL树）

- AVL树是一种自平衡二叉查找树，规则严格，读取操作更快，但修改操作效率略低。每个结点包含平衡因子（右子树高度-左子树高度），高度差至多为1。采用头结点设计，头结点的左子结点为根结点。

- **构造器**：

    - `AvlTree()` → 空AVL树。

    - `AvlTree(int... elements)` → 从给定元素构建AVL树（自动平衡，重复元素忽略）。

- **方法**：

    - `int[] traversal()` → 中序遍历（升序）。

    - `boolean input(int element)` → 插入元素，重复返回 `false`。

    - `int inputMore(int... elements)` → 批量插入，返回忽略的重复元素数量。

    - `int getDepth(int element)` → 获取元素深度（根深度为1），不存在返回 `Integer.MIN_VALUE`。

    - `boolean remove(int element)` → 删除元素。

    - `static AvlTree leftRotate(AvlTree tree)` → 左旋，返回新根结点。

    - `static AvlTree rightRotate(AvlTree tree)` → 右旋，返回新根结点。

    - `String toString()` → 返回括号表示法的字符串表示。

- **字段**：`element`（元素）、`balanceFactor`（平衡因子）、`left`（左子结点）、`right`（右子结点）、`parent`（父结点）。

#### BPlusTree（B+树）

- **构造器**：

    - `BPlusTree(int order)` → 指定阶数（最小4）。

    - `BPlusTree()` → 默认阶数256。

- **方法**：

    - `int count(int element)` → 返回 `element` 出现的次数（支持重复元素）。

    - `int count(int min, int max)` → 返回区间 `[min, max]` 内元素个数。

    - `int count()` → 总元素个数。

    - `int[] get(int min, int max)` → 返回区间 `[min, max]` 内所有元素（升序）。

    - `int[] traversal()` → 中序遍历所有叶子节点（升序）。

    - `boolean input(int element)` → 插入元素，若因插入导致根节点分裂则返回 `true`。

    - `boolean remove(int element)` → 删除一个匹配的元素，若因删除导致根节点合并则返回 `true`。

    - `int removeAll(int element)` → 删除所有匹配元素，返回删除的元素个数。

#### Deque（双端队列）

- 继承自 `Queue`，在队列两端都可以进行插入和删除操作。以循环数组实现，默认容量256。

- **构造器**：`Deque(int capacity)` 指定容量；`Deque()` 默认容量256。

- **方法**：

    - `int inputBack(int element)` → 从队尾入队（同 `Queue.input`）。

    - `int inputMoreBack(int... elements)` → 从队尾批量入队。

    - `int inputFront(int element)` → 从队头入队。

    - `int inputMoreFront(int... elements)` → 从队头批量入队。

    - `int getBack()` → 获取队尾元素但不出队，空时返回 `Integer.MIN_VALUE`。

    - `int getFront()` → 获取队头元素但不出队（同 `Queue.get`）。

    - `int outputBack()` → 队尾元素出队，空时返回 `Integer.MIN_VALUE`。

    - `int outputFront()` → 队头元素出队（同 `Queue.output`）。

- 同时继承 `Queue` 的所有方法（`isEmpty()`, `elementCount()`, `dilate()` 等）。

#### DisjointSet / DisjointSetElement（并查集）

- **DisjointSet**：基于索引的并查集。

    - 构造：`DisjointSet(int capacity)` 或 `DisjointSet()`

    - `int findRootByIndex(int index)`

    - `int unionIndex(int idx1, int idx2)`

    - `boolean isRelatedIndex(int idx1, int idx2)`

- **DisjointSetElement**：扩展支持整数元素映射。

    - `int input(int element)` → 添加元素，返回新元素数量；若已存在返回 `Integer.MIN_VALUE`。

    - `int inputMore(int... elements)` → 批量添加元素，返回新元素数量。

    - `int findRootByElement(int element)`

    - `int unionElement(int e1, int e2)`

    - `boolean isRelatedElement(int e1, int e2)`

#### Graph（图）

- 采用邻接矩阵，支持有向/无向、有权/无权。

- **构造**：`Graph(String graphString, int type)`

    - `type=1` 无向有权，`type=2` 有向无权，`type=3` 有向有权。

    - 字符串格式：`"{v1,v2,w1},{v2,v3,w2},..."`，顶点编号从1开始。

- **方法**：`int costMin(int start, int end)` → Dijkstra 算法求最短路径成本（有权图）或边数（无权图）。

#### HashMap（哈希表）

- 链地址法，负载因子 2/3，自动扩容至质数容量。

- **构造**：`HashMap()` 默认容量257；`HashMap(int capacity)` 取不小于 capacity 的质数。

- **方法**：

    - `int input(int key, int value)` → 插入或更新，返回桶索引。

    - `int get(int key)` → 返回值，不存在返回 `Integer.MIN_VALUE`。

    - `int remove(int key)` → 删除并返回原值，不存在返回 `Integer.MIN_VALUE`。

#### Heap / HeapAscend / HeapDescend（堆）

- **Heap**：普通堆（仅存储，不自动维护堆序）。提供基础数组扩容、插入、取出。

- **HeapAscend**（升序堆）和 **HeapDescend**（降序堆）继承自 `Heap`，自动维护堆序。

- **构造器**：`HeapAscend()` 默认容量255；`HeapAscend(int... elements)` 自动建堆。

- **主要方法**：

    - `int input(int element)` → 插入并调整。

    - `int output()` → 弹出堆顶。

    - `int get()` → 获取堆顶。

    - `int regularAll()` → 重建堆。

    - `int regularTop()` → 调整堆顶。

    - `int regularLast()` → 调整最后一个元素。

#### LinkedListSingly / LinkedListDoubly（链表）

- **单向链表** `LinkedListSingly`：带头节点（头节点不存储有效数据）。

    - 构造：`LinkedListSingly()` 空链表；`LinkedListSingly(int... numbers)` 从给定数据构建。

    - 查询方法：`isEmpty()`, `elementCount()`, `elementAt(int index)`, `indexOf(int element)`, `traversal()`。

    - 插入方法：`inputBack(int)`, `inputMoreBack(int...)`, `inputListBack(LinkedListSingly)`, `inputFront(int)`, `inputMoreFront(int...)`, `inputListFront(LinkedListSingly)`, `insert(int index, int)`, `insertMore(int index, int...)`, `insertList(int index, LinkedListSingly)`。

    - 删除方法：`removeBack(int count)`, `removeFront(int count)`, `removeIndex(int index)`, `removeElement(int)`, `removeElement(int min, int max)`。

    - 排序方法：`sortAscend()`, `sortDescend()`（归并排序实现）。

- **双向链表** `LinkedListDoubly`：无头节点，存储 `head` 和 `tail` 引用，支持正向/反向索引。

    - 构造：`LinkedListDoubly()` 空链表；`LinkedListDoubly(int... numbers)` 从给定数据构建。

    - 查询方法：`isEmpty()`, `elementCount()`, `elementAt(int index)`, `indexForward(int)`, `indexBackward(int)`, `traversalForward()`, `traversalBackward()`, `reverseIndex(int)`, `minIndex(int)`。

    - 插入方法：`inputBack(int)`, `inputMoreBack(int...)`, `inputListBack(LinkedListDoubly)`, `inputFront(int)`, `inputMoreFront(int...)`, `inputListFront(LinkedListDoubly)`, `insert(int index, int)`, `insertMore(int index, int...)`, `insertList(int index, LinkedListDoubly)`。

    - 删除方法：`removeBack(int count)`, `removeFront(int count)`, `removeIndex(int index)`, `removeElement(int)`, `removeElement(int min, int max)`。

#### Queue（队列）

- 循环数组实现，默认容量256，自动扩容。

- **方法**：

    - `boolean isEmpty()`, `boolean isFull()`

    - `int elementCount()`, `int emptyCount()` → 元素数量、剩余空间。

    - `int input(int element)`, `int inputMore(int... elements)` → 入队。

    - `int get()` → 查看队头。

    - `int output()` → 出队。

    - `int dilate()`, `int dilate(int moreCapacity)` → 扩容。

#### RedBlackTree（红黑树）

- 使用 NIL 节点，提供插入、删除、中序遍历。

- **构造**：`RedBlackTree()` 创建一个空树（头节点）；`RedBlackTree(int... elements)` 从给定元素构建。

- **方法**：

    - `boolean input(int element)` → 插入，重复返回 false。

    - `int inputMore(int... elements)` → 批量插入，返回重复元素个数。

    - `boolean remove(int element)` → 删除元素。

    - `int[] traversal()` → 中序遍历升序序列。

    - `int getDepth(int element)` → 返回深度（根深度1），不存在返回 `Integer.MIN_VALUE`。

    - `static RedBlackTree leftRotate(RedBlackTree)` / `rightRotate(RedBlackTree)` → 左旋/右旋操作。

    - `String toString()` → 返回括号表示法的字符串表示。

#### Stack / StackAscend / StackDescend / StackMax / StackMin（栈）

- **Stack**：普通栈，数组实现，自动扩容，默认容量16。

- **StackAscend**（单调递增栈）：独立实现，入栈时弹出所有比新元素小的元素。`input()` 返回被弹出元素的数组 `int[]`，`inputMore()` 返回 `int[][]`。

- **StackDescend**（单调递减栈）：独立实现，入栈时弹出所有比新元素大的元素。`input()` 返回被弹出元素的数组 `int[]`，`inputMore()` 返回 `int[][]`。

- **StackMax**：继承自 `Stack`，支持 `O(1)` 获取当前栈中最大值。`maxElement()` 返回当前栈中最大值。

- **StackMin**：继承自 `Stack`，支持 `O(1)` 获取当前栈中最小值。`minElement()` 返回当前栈中最小值。

- 通用方法：`isEmpty()`, `isFull()`, `elementCount()`, `emptyCount()`, `input`, `inputMore`, `output`, `get`, `dilate()`, `dilate(int moreCapacity)`。

#### Tree（树-孩子兄弟表示法）

- 节点包含 `element`、`child`（第一个孩子）、`next`（下一个兄弟）。

- **构造**：`Tree(String treeString)` 例如 `"A{B{D,E},C{F,G,H,I}}"`。

- **方法**：`count`, `depth`, `traversalPreorder`, `traversalPostorder`, `traversalLevelorder`, `insertTo`, `remove`。

#### Trie（字典树）

- 不区分大小写，每个节点包含26个子节点（仅小写字母）。

- **构造**：`Trie(String... words)` 插入初始单词列表。

- **方法**：

    - `int count()` → 存储的不同单词数量。

    - `int depth()`, `int maxLength()` → 树深度（最长单词长度+1）及最长单词长度。

    - `int input(String word)` → 插入单词，返回新增节点数，重复返回 `Integer.MIN_VALUE`。

    - `int inputMore(String... words)` → 批量插入单词，返回新增单词数量。

    - `boolean exist(String word)` → 判断是否存在。

    - `String[] getAllWords()` → 返回所有单词（字典序）。

    - `boolean remove(String word)` → 删除单词。

    - `String toString()` → 返回所有单词的字符串表示（如 `{"abc","abd","def"}`）。

#### HuffmanTreeByte / HuffmanTreeChar（霍夫曼树）

- 分别用于 `byte[]` 和 `String` 的压缩与解压。

- **构造**：`HuffmanTreeByte(byte[] data)` 或 `HuffmanTreeChar(String text)`。

- **方法**：

    - `String getCode(byte b)` / `String getCode(char c)` → 获取单个字符的霍夫曼编码。

    - `String getAllCodes()` → 获取所有字符的霍夫曼编码表。

    - `String encode(byte[] data)` / `String encode(String text)` → 压缩。

    - `byte[] decode(String code)` / `String decode(String code)` → 解压。

---

### 3.3 aio.datetime（日期时间）

#### Datetime（日期时间）

- 支持公历（含公元前1年表示为 `year=0`，公元前2年表示为 `year=-1`，以此类推）。

- 时区范围 -12 到 +12，默认东八区（UTC+8）。

- **构造器**：多个重载，可指定年、月、日、时、分、秒、毫秒、时区，或使用当前时间戳。

- **静态方法**：

    - `int[] now()` → 获取默认时区当前时间数组 `{年,月,日,时,分,秒,毫秒,时区}`。

    - `int[] now(int timeZone)` → 获取指定时区当前时间数组。

    - `int getDefaultTimeZone()` / `boolean setDefaultTimeZone(int timeZone)` → 获取/设置默认时区。

    - `long timestamp(int year, int month, int day, int hour, int minute, int second, int millisecond, int timeZone)` → 计算自公元元年1月1日0时0分0秒的毫秒数。

    - `long timestampNow()` → 获取当前时间的时间戳。

    - `long timestampUnix(int year, int month, int day, int hour, int minute, int second, int millisecond, int timeZone)` → 计算自1970年1月1日0时0分0秒（UTC）的毫秒数（Unix时间戳）。

    - `long timestampUnixNow()` → 获取当前Unix时间戳。

    - `int timestampDay(int year, int month, int day)` → 计算日时间戳（天数）。

    - `int timestampDayNow()` → 获取当前日时间戳。

    - `static boolean isLeapYear(int year)` → 判断指定年份是否为闰年。

    - `static int weekday(int year, int month, int day)` → 计算指定日期的星期。

    - `static int dayInYear(int year, int month, int day)` → 计算指定日期在当年中的第几天。

    - `static Datetime addDay(int year, int month, int day, int addDay)` → 日期偏移，返回新对象。

    - `static long intervalDay(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay)` → 计算日期间隔天数。

    - `static int secondInDay(int hour, int minute, int second)` → 计算指定时间在当天中的第几秒。

    - `static int intervalSecondInDay(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond)` → 计算同一天内时间间隔秒数。

- **实例方法**：

    - `long timestamp()` → 返回自公元元年1月1日0时0分0秒的毫秒数。

    - `long timestampUnix()` → 返回Unix时间戳。

    - `int timestampDay()` → 返回日时间戳（天数）。

    - `boolean isLeapYear()` → 是否为闰年。

    - `int weekday()` → 星期（0=周日，1=周一，...，6=周六）。

    - `int dayInYear()` → 当年第几天。

    - `int secondInDay()` → 当天第几秒。

    - `Datetime addDay(int days)` → 返回新对象，日期偏移。

    - `long intervalDay(Datetime to)` → 计算与另一个Datetime的日期间隔天数。

    - `int intervalSecondInDay(Datetime to)` → 计算同一天内与另一个Datetime的时间间隔秒数。

    - `String toString()` → 返回日期时间字符串表示。

    - `int compareTo(Datetime another)` → 时间比较，返回负数表示早于，正数表示晚于。

#### Calendar（日期常数）

- 提供常用常量：平年/闰年天数、月份天数表、前缀和表、星期基准等。

---

### 3.4 aio.geography（地理工具）

#### ElevationMap（高程地图）

- 存储二维 double 数组，表示海拔（米）。

- **构造**：`ElevationMap(int length, int width)` 或 `ElevationMap(int length)`。

- **方法**：

    - `boolean calculateStatistics()` → 更新 `min`, `max`, `average`, `median`。

    - `Histogram calculateHistogram()` → 返回100区间的直方图。

    - `double elevate(double increaseHeight)` / `double sink(double decreaseHeight)` → 整体抬高/降低。

    - `double normalize(double newMin, double newMax)` → 线性拉伸至指定范围。

    - `double normalize()` → 线性拉伸至 [0, 1] 范围。

    - `double linearScale(double verticalCoefficient)` → 线性缩放（乘以系数）。

    - `double exponentialScale(double verticalBase)` → 指数缩放（底数幂次变换）。

    - `double exponentialNormalize(double normalizeBase)` → 指数归一化（先指数缩放再拉伸至 [0, 1]）。

    - `double secantOddNormalize()` → 正割奇函数归一化（使用 secant 变换后拉伸至 [0, 1]）。

    - `double overlayPerlinTerrain(long seed, double horizontalScale, int octaves, double persistence, double lacunarity, double verticalScale)` → 叠加柏林噪声地形，支持种子、水平/垂直缩放、细节等级等参数。

    - `double overlayPerlinTerrain(long seed, double verticalScale)` → 简化版，仅指定种子和垂直缩放。

    - `double overlayPerlinTerrain(double verticalScale)` → 简化版，仅指定垂直缩放。

    - `double overlayPerlinTerrain(long seed)` → 简化版，仅指定种子。

    - `double overlayPerlinTerrain()` → 无参版，使用默认参数。

    - `String toString()` → 返回高程矩阵的文本表示。

#### GeographicCoordinate（地理坐标）

- 存储经度、纬度（十进制度），并提供度分秒转换。

- **构造**：`GeographicCoordinate(double longitudeDeg, double latitudeDeg)`。

- **方法**：

    - `static int[] degToDms(double deg)` → 十进制度转换为度分秒数组 `{度, 分, 秒}`。

    - `static double dmsToDeg(int[] dms)` → 度分秒数组转换为十进制度。

    - `void printDeg()` → 打印十进制经纬度。

    - `void printDms()` → 打印度分秒格式经纬度。

    - `String toString()` → 返回度分秒格式的字符串表示。

#### ProjectedCoordinate（投影坐标）

- 平面直角坐标，单位米，东方向为 x 正，北方向为 y 正。

- **构造**：`ProjectedCoordinate(double x, double y)` 或默认原点。

- **方法**：

    - `boolean move(double deltaX, double deltaY)` → 平移坐标。

    - `static ProjectedCoordinate offset(ProjectedCoordinate coordinate, double deltaX, double deltaY)` → 静态版平移，返回新坐标。

    - `double[] relativePosition(ProjectedCoordinate coordinate)` → 计算相对位置 `{Δx, Δy}`。

    - `double distance(ProjectedCoordinate other)` → 计算两点距离。

    - `static double distance(double x0, double y0, double xt, double yt)` → 静态版距离计算。

    - `boolean equalsApproximate(ProjectedCoordinate coordinate, double tolerance)` → 容差近似相等判断。

    - `double azimuthAngle(ProjectedCoordinate target)` → 方位角（0°=正北）。

    - `static double azimuthAngle(double x0, double y0, double xt, double yt)` → 静态版方位角计算。

    - `ProjectedCoordinate destination(double azimuth, double distance)` → 已知方位角和距离求终点。

    - `ProjectedCoordinate middlePoint(ProjectedCoordinate target)` → 求中点坐标。

    - `ProjectedCoordinate linearInterpolation(ProjectedCoordinate target, double ratio)` → 线性插值。

    - `double moveDistanceTowards(ProjectedCoordinate target, double moveDistance)` → 向目标移动指定距离。

    - `double moveRatioTowards(ProjectedCoordinate target, double moveRatio)` → 按比例向目标移动。

    - `static double area(ProjectedCoordinate... vertices)` → 多边形面积（按逆时针顺序给出顶点）。

    - `static double perimeter(...)` → 多边形周长。

    - `String toString()` / `boolean equals(Object)` → 字符串表示与相等判断。

---

### 3.5 aio.mathematics（数学工具）

#### Maths（数学常数与方法）

- **数学常数**：`PI`, `E`, 质数表（`PRIME`，前1229个质数），阶乘表 `FACT`，排列数 `A`，组合数 `C`，斐波那契数列 `FIBONACCI`。

- **整数与浮点运算**：

    - `factors(int number)` → 计算一个整数的所有因子。

    - `gcd`, `lcm` → 最大公因数、最小公倍数。

    - `isPrime`, `primeTable`, `decompose` → 质数判断、质数表生成、质因数分解。

    - `power(int, int)` / `power(long, long)` → 快速幂（整数幂运算）。
    - `powerMod1000000007(long, long)` → 快速幂取模 1000000007。
    - `powerMod(long, long, long)` → 快速幂取模（自定义模数）。

    - `lowbit` → 最低位1的权值。

    - `bitCount` → 二进制表示中1的个数。

    - `length` → 十进制位数。

    - `binary` → 返回二进制表示（布尔数组）。

    - `binaryWeight` → 返回二进制表示中每个1的权值。

    - `linearInterpolation` → 线性插值。

    - `mathematicalOrderNumber` → 区间内数字的数学顺序升序序列。

    - `dictionaryOrderNumberTo` → 区间内数字的字典序升序序列。

    - `numberCombinationCount` → 由给定数字组成的无前导零的不同数字个数。

- **统计**：

    - `max`, `min`, `sum`, `average` → 基本统计量（支持 `int` 和 `double`）。

    - `maxIndex`, `minIndex` → 首个最大值/最小值的索引。

    - `weightedAverage` → 加权平均值（支持 `int`/`double` 元素和权重交叉组合）。

    - `median`, `mode` → 中位数、众数。

    - `variance`, `varianceAverage` → 方差、方差平均值。

    - `standardDeviation`, `standardDeviationAverage` → 标准差、标准差平均值。

    - `linearRegression` → 一元线性回归。

- **数组操作**：

    - `reverseNew`, `reverseLocal` → 数组反转（支持 `int[]` 和 `double[]`，支持指定区间）。

    - `shuffleNew`, `shuffleLocal` → 随机打乱（支持 `int[]` 和 `double[]`，支持指定区间）。

    - `distinctSortNew(int[] numbers)` → 去重并升序排序，返回新数组。

    - `distinctSortLocal(int[] numbers)` → 原地去重并升序排序，返回重复元素个数。

- **几何**：

    - `polygonPerimeter(double... coordinatesXnYnCcw)` → 多边形周长（顶点按逆时针顺序，参数为交替的x,y坐标）。

    - `polygonArea(double... coordinatesXnYnCcw)` → 多边形面积（顶点按逆时针顺序，参数为交替的x,y坐标）。

    - `matrixMultiply(int[][] factorLeft, int[][] factorRight)` → 矩阵乘法。

- **表达式计算**：

    - `calculate(String expression)` → 计算四则运算表达式（支持 `+ - * / ( )` 及负号）。

- **数独求解**：

    - `sudokuValid`, `sudokuSolve`（9×9，原地修改）。

#### BigInteger（高精度整数）

- 高精度整数类，支持任意大小的整数运算。内部以整型数组 `int[]` 以 2^31 进制表示整数的绝对值，低位优先存储，符号表示整数的正负。

- **字段**：

    - `int number[]` → 整数的整型数组低位优先表示（2^31 进制）。
    - `int size` → 有效位数。
    - `int sign` → 符号（1=正，-1=负，0=零）。

- **构造器**：

    - `BigInteger(String numberString)` → 通过整数字符串构造（支持负号和前导零）。
    - `BigInteger(int number)` → 通过基本类型 `int` 构造。
    - `BigInteger(int numberArray[], int sign)` → 通过低位优先的整型数组和符号构造（会拷贝数组）。
    - `BigInteger(int numberArray[], int size, int sign)` → 直接使用输入的数组、位数和符号构造（不拷贝，不检查）。

- **静态方法（底层运算）**：

    - `static int compareAbsolute(int[], int[])` → 比较两个整型数组表示的绝对值大小。

- **静态方法（BigInteger 对象运算）**：

    - `static BigInteger add(BigInteger, BigInteger)` → 两高精度整数加法。
    - `static BigInteger subtract(BigInteger, BigInteger)` → 两高精度整数减法。
    - `static BigInteger multiply(BigInteger, int)` → 高精度整数与基本类型整数乘法。
    - `static BigInteger multiply(BigInteger, BigInteger)` → 两高精度整数乘法。
    - `static BigInteger[] divide(BigInteger, int)` → 除以基本类型整数，返回 `{商, 余数}`。若除数为0则返回 `null`。
    - `static BigInteger[] divide(BigInteger, BigInteger)` → 两高精度整数除法，返回 `{商, 余数}`。若除数为0则返回 `null`。
    - `static BigInteger gcd(BigInteger, BigInteger)` → 两高精度整数的最大公因数。`0` 与 `0` 的 `gcd` 定义为 `0`。
    - `static BigInteger lcm(BigInteger, BigInteger)` → 两高精度整数的最小公倍数。`0` 与 `0` 的 `lcm` 定义为 `0`。
    - `static BigInteger power(BigInteger, int)` → 高精度整数的正整数次幂。若指数为负数或底数与指数同时为0则返回 `null`。
    - `static BigInteger factorial(int)` → 计算整数的阶乘。若整数为负数则返回 `null`。

- **实例方法**：

    - `boolean increment()` → 自增1，返回位数是否改变。
    - `boolean decrement()` → 自减1，返回位数是否改变。
    - `int compareTo(BigInteger another)` → 比较当前对象与指定对象的数值大小（实现 `Comparable<BigInteger>` 接口）。
    - `boolean equals(Object another)` → 判断与指定对象是否相等。
    - `int hashCode()` → 返回哈希值。
    - `String toString()` → 返回整数的十进制字符串表示。

- **注意**：此类实现了 `Comparable<BigInteger>` 接口。除数为 `0` 时除法返回 `null`。底数与指数同时为 `0` 时幂运算返回 `null`。

#### BigRational（高精度有理数）

- 高精度有理数类，支持任意大小的分数运算。有理数即分数，包含整数、有限小数和无限循环小数。

- 内部以两个 `BigInteger` 对象 `numerator`（分子）和 `denominator`（分母）存储。当 `denominator` 为 `null` 时表示整数。

- **字段**：

    - `BigInteger numerator` → 分子。
    - `BigInteger denominator` → 分母（`null` 时表示整数）。
    - `int mode` → 输出格式（`>0` 小数，`=0` 分数+小数，`<0` 分数）。

- **构造器**：多个重载，支持从各种形式构造：

    - `BigRational(String rationalString)` → 从小数字符串构造（如 `"0.5"`、`"0.(3)"`，用括号表示循环节）。
    - `BigRational(String numeratorString, String denominatorString, int mode)` → 从分子分母字符串和输出模式构造。
    - `BigRational(String numeratorString, String denominatorString)` → 从分子分母字符串构造（默认 `mode=0`）。
    - `BigRational(int numerator, int denominator, int mode)` → 从基本类型整数分子分母和输出模式构造。
    - `BigRational(int numerator, int denominator)` → 从基本类型整数分子分母构造（默认 `mode=0`）。
    - `BigRational(BigInteger numerator, BigInteger denominator, int mode)` → 从高精度整数分子分母和输出模式构造。
    - `BigRational(BigInteger numerator, BigInteger denominator)` → 从高精度整数分子分母构造（默认 `mode=0`）。
    - `BigRational(BigInteger numerator, BigInteger denominator, int sign, int mode)` → 从高精度整数分子分母、符号和输出模式构造。

- **静态方法（有理数运算）**：

    - `static BigInteger[] commonDenominator(BigRational, BigRational)` → 通分两个有理数（会修改传入对象），返回两个 `BigInteger` 的数组，分别为两个有理数各自通分乘数。
    - `static BigRational add(BigRational, BigRational)` → 有理数加法，返回两数之和。
    - `static BigRational subtract(BigRational, BigRational)` → 有理数减法，返回两数之差。
    - `static BigRational multiply(BigRational, BigRational)` → 有理数乘法，返回两数之积。
    - `static BigRational divide(BigRational, BigRational)` → 有理数除法，返回两数之商；若除数为0则返回 `null`。
    - `static BigRational power(BigRational, int)` → 有理数的整数次幂运算，返回幂结果。

- **实例方法**：

    - `BigInteger reduce()` → 约分当前有理数对象（会修改调用对象），返回分子分母的最大公因数。
    - `String toString()` → 按 `mode` 格式输出。

#### Complex（复数）

- 表示 `a + bi`，提供加减乘除、模长运算。

- **构造**：`Complex(double real, double imaginary)` 或 `Complex()` 默认 `0+0i`。

- **方法**（实例/静态成对出现）：

    - `boolean add(Complex C)` / `static Complex add(Complex, Complex)` → 复数加法。

    - `boolean subtract(Complex C)` / `static Complex subtract(Complex, Complex)` → 复数减法。

    - `boolean multiply(Complex C)` / `static Complex multiply(Complex, Complex)` → 复数乘法。

    - `boolean divide(Complex C)` / `static Complex divide(Complex, Complex)` → 复数除法。

    - `double magnitude()` / `static double magnitude(double real, double imaginary)` → 模长。

    - `String toString()` → 返回 `"a+bi"` 格式的字符串表示。

#### Function / ExpressionTree（一元实函数 / 表达式树）

- **类**：`aio.mathematics.Function` 及其内部类 `ExpressionTree`

- **功能**：一元实函数类，使用遗传算法（Genetic Programming）通过给定坐标点拟合出函数表达式。内部使用表达式树表示数学表达式，支持加法、减法、乘法、除法、指数、对数六种运算。

- **ExpressionTree（表达式树）**：

    - 结点类型：`-1`=自变量x，`0`=常量，`1`=加法，`2`=减法，`3`=乘法，`4`=除法，`5`=指数，`6`=对数。

    - **构造器**：

        - `ExpressionTree(int type, ExpressionTree left, ExpressionTree right)` → 指定类型和子树。

        - `ExpressionTree(int type, double value)` → 指定类型和常量值。

        - `ExpressionTree(double value)` → 常量结点。

        - `ExpressionTree(int type)` → 指定类型（默认值0.0）。

        - `ExpressionTree(int maxTreeDepth, boolean full, double subtreeChance, double constantRange)` → 随机生成表达式树。

    - **方法**：

        - `int simplify()` → 化简表达式树，返回删除的结点数。

        - `int count()` → 结点数。

        - `int depth()` → 树深度。

        - `ExpressionTree getNodeRandom()` → 随机获取一个结点。

        - `double calculate(double x)` → 计算指定自变量值的函数值。

        - `double fitness(double x[], double y[], double complexityPenalty)` → 计算对点集的适应度（均方误差+复杂度惩罚）。

        - `static ExpressionTree[] crossover(ExpressionTree, ExpressionTree)` → 交叉互换两个表达式树的结点。

        - `int mutate(int maxDepth, double constantRange)` → 突变表达式树的一个结点。

        - `ExpressionTree clone()` → 深拷贝。

        - `String toString()` → 返回表达式字符串。

- **Function（一元实函数）**：

    - **遗传算法参数**（静态字段）：`populationSize`（种群规模，默认6000）、`maxGeneration`（最大迭代次数，默认86400000）、`bestRate`（最佳保留比例，默认0.005）、`survivalRate`（选择率，默认0.02）、`newIndividualRate`（新个体比例，默认0.2）、`crossoverRate`（交叉概率，默认0.7）、`mutationRate`（突变概率，默认0.3）、`maxTreeDepth`（最大树深度，默认7）。

    - **构造器**：

        - `Function(double... coordinatesXnYn)` → 通过x1,y1,x2,y2,...格式的坐标拟合。

        - `Function(double coordinatesXn[], double coordinatesYn[])` → 通过两个坐标数组拟合。

    - **方法**：

        - `double calculate(double x)` → 计算函数值。

        - `double[] calculate(double... x)` → 批量计算函数值。

        - `String toString()` → 返回 `"y=..."` 格式的函数字符串。

    - **字段**：`functionTree`（表达式树）、`fitness`（适应度）。

#### PointPlanar（平面点）

- 平面点表示平面中的一个位置，同时包含直角坐标（x, y）和极坐标（ρ, θ）表示。提供象限、距离、角度、中点、插值、多边形周长/面积等方法。

- **构造**：`PointPlanar(double rhoX, double thetaY, boolean truePolarFalseCartesian)` 可选择极坐标或直角坐标构造；`PointPlanar()` 默认原点。

- **方法**：

    - `void calculatePolarCoordinate()` / `void calculateCartesianCoordinate()` → 直角坐标与极坐标互转。

    - `static double[] cartesianToPolar(double x, double y)` / `static double[] polarToCartesian(double rho, double theta)` → 静态版坐标转换。

    - `int quadrant()` / `static int quadrant(double x, double y)` → 判断象限（0=原点，1~4=象限，5=坐标轴）。

    - `void add(PointPlanar)` / `static PointPlanar add(PointPlanar, PointPlanar)` → 坐标加法。

    - `void subtract(PointPlanar)` / `static PointPlanar subtract(...)` → 坐标减法。

    - `void multiplyScalar(double)` / `static PointPlanar multiplyScalar(...)` → 标量乘法。

    - `double distance(PointPlanar)` / `double distance(double, double, double, double)` → 距离计算。

    - `double angle(PointPlanar target)` / `double angle(double xt, double yt)` / `static double angle(double, double, double, double)` → 角度（度，0°=正东）。

    - `PointPlanar middlePoint(PointPlanar target)` → 中点。

    - `PointPlanar linearInterpolation(PointPlanar target, double ratio)` → 线性插值。

    - `double moveDistanceTowards(PointPlanar target, double moveDistance)` / `double moveRatioTowards(...)` → 向目标移动。

    - `static double perimeter(PointPlanar... coordinatesCcw)` / `static double area(...)` → 多边形周长/面积。

    - `String toString()` → 字符串表示（同时显示直角坐标和极坐标）。

#### Determinant（行列式）

- 支持整数元素，可计算值、余子式、代数余子式、转置等。

- **构造**：

    - `Determinant(int orderNumber, int... elementNumbers)` → 指定阶数和元素。

    - `Determinant(int orderNumber)` → 指定阶数，元素初始为0。

    - `Determinant(Determinant copingDeterminant)` → 拷贝构造。

    - `Determinant(Matrix squareMatrix)` → 从方阵构造。

- **方法**：

    - `double value()` → 计算行列式的值。

    - `Determinant cofactor(int baseRow, int baseColumn)` → 余子式。

    - `Determinant cofactorAlgebraic(int baseRow, int baseColumn)` → 代数余子式。

    - `Determinant reverse()` → 转置。

    - `int simplify()` → 化简行列式，返回化简步骤数。

    - `int compareTo(Determinant comparing)` → 比较行列式值的大小。

#### Histogram（直方图）

- 等距直方图，自动计算边界，支持下溢/上溢计数。

- **构造**：`Histogram(int groupCount, double... data)` 或指定 `(groupCount, min, max)`。

- **方法**：

    - `boolean input(double data)` → 插入单个数据，返回 `true` 若在范围内。

    - `int inputMore(double... data)` → 批量插入数据，返回超出范围的数据个数。

#### Matrix（矩阵）

- 整数矩阵，支持加、减、数乘、乘法、转置、余子式、伴随矩阵、幂运算等。

- **构造**：`Matrix(int[][] elements)`。

- **主要方法**：

    - `Matrix add(Matrix source)` / `static Matrix add(Matrix, Matrix)` → 矩阵加法。

    - `Matrix subtract(Matrix subtrahend)` / `static Matrix subtract(Matrix, Matrix)` → 矩阵减法。

    - `int multiplyScalar(int coefficient)` / `static Matrix multiplyScalar(int, Matrix)` → 标量乘法。

    - `Matrix multiply(int[][] factor)` / `static Matrix multiply(Matrix, Matrix)` → 矩阵乘法。

    - `static Matrix reverse(Matrix)` → 矩阵转置。

    - `static Matrix cofactor(Matrix, int baseRow, int baseColumn)` → 余子式。

    - `static Matrix cofactorAlgebraic(Matrix, int baseRow, int baseColumn)` → 代数余子式。

    - `static Matrix adjugate(Matrix)` → 伴随矩阵。

    - `Matrix power(int power)` / `static Matrix power(Matrix, int)` → 矩阵幂运算。

#### PolynomialEquation（多项式方程）

- 解析形如 `"2x^2+3x+1=0"` 的方程，支持次数0~4（仅1、2次给出解析解，3、4次预留接口，5次以上提示不可解）。

- **构造**：`PolynomialEquation(char unknown, String equation)`。

- **方法**：

    - `double[] solve()` → 求解实数根，并打印过程。

    - `String toString()` → 返回规范化方程字符串。

#### SquareRoot（平方根）

- 将根号内整数化简为 `a√b` 形式。

- **构造**：`SquareRoot(int n)` → 自动化简。

- **方法**：

    - `static int[] sqrt(int number)` → 静态方法，化简平方根，返回 `{系数, 根号内数}`。

    - `static String string(int number)` → 静态方法，返回格式化字符串（如 `"2√3"`）。

    - `double value()` → 小数近似值。

    - `int compareTo(SquareRoot comparing)` → 比较大小。

    - `String toString()` → 返回化简后的字符串表示。

#### Angle（角度）

- **字段**：

    - `int degree` / `int minute` / `int second` → 度、分、秒。

- **构造**：`Angle(int degree, int minute, int second)` 或 `Angle(int degree, int minute)` 或 `Angle(int degree)` 或 `Angle()`。

- **方法**：

    - `double angleToDeg()` → 转换为十进制角度。

    - `static double angleToDeg(int degree, int minute, int second)` → 静态版角度转换。

    - `double angleToRad()` → 转换为弧度。

    - `static double angleToRad(int degree, int minute, int second)` → 静态版弧度转换。

    - `boolean add(Angle angle)` → 角度加法，修改当前对象，返回是否超过360度。

    - `static int[] sum(Angle... angles)` → 多个角度求和，返回 `{度, 分, 秒}`。

    - `double divide(int number)` → 角度除法（整数除），返回十进制角度商。

    - `int reform()` → 将角度规约到 [0, 360) 范围内，返回规约后的度数。

    - `String toString()` → 返回度分秒格式的字符串表示（如 `"30°15'50"`）。

---

### 3.6 aio.endecode（编解码）

#### Barcode（二维码元数据常量）

- **类**：`aio.endecode.Barcode`

- **功能**：提供 QR 二维码生成所需的全部常量数据，包括版本边长、编码模式掩码、纠错等级掩码、有限域（GF(256)）指数/对数表、生成多项式系数、编码长度位数、字母数字表、分组信息、对齐图案位置等。

- **主要常量**：

    - `SIDE_LENGTH[]` → 各版本（1~40）的二维码边长（21~177）。

    - `MODE_MASK[]` → 编码模式掩码（数字=1, 字母数字=2, 字节=4, 日文=8）。

    - `ERROR_CORRECTION_MASK[]` → 纠错等级掩码（L=1, M=0, Q=3, H=2）。

    - `EXPONENTIAL_FINITE_FIELD_256[]` / `LOGARITHM_FINITE_FIELD_256[]` → GF(256) 有限域运算表，用于 Reed-Solomon 纠错编码。

    - `GENERATOR_POLYNOMIAL_COEFFICIENT[][]` → 各纠错码字数的生成多项式系数。

    - `ALPHANUMERIC_TABLE[]` → 字母数字模式编码表（0-9, A-Z, 空格及符号共45个字符）。

    - `BLOCK_COUNT_PER_GROUP[][][]` / `DATA_CODE_WORD_COUNT_PER_BLOCK[][][]` → 各版本、各纠错等级的分组和每块数据码字数。

    - `ALIGNMENT_PATTERN_CENTER_POSITION[][][]` → 各版本对齐图案的中心坐标。

#### QuickResponseCode（二维码）

- **类**：`aio.endecode.QuickResponseCode`

- **功能**：生成 QR 二维码（Quick Response Code），支持数字、字母数字、字节（UTF-8）、日文、ECI 五种编码模式，支持 L/M/Q/H 四种纠错等级，版本 1~40。内部实现包括数据编码、Reed-Solomon 纠错码生成、功能图案与对齐图案绘制、数据位流填充、掩膜评分与选择等完整 QR 码生成流程。

- **字段**：

    - `boolean field[][]` → 二维码的布尔矩阵（true=黑，false=白），索引为 `[y][x]`。

    - `int side` → 二维码边长，公式为 `(version-1)*4+21`。

    - `int version` → 版本号（1~40）。

    - `int errorCorrectionLevel` → 纠错等级（1=L, 2=M, 3=Q, 4=H）。

    - `int mode` → 编码模式（0=数字, 1=字母数字, 2=字节, 3=日文, 4=ECI）。

    - `int mask` → 最终选择的掩膜编号（0~7），由内部评分算法自动选出最优掩膜。

    - **构造器**：

    - `QuickResponseCode(String text)` → 自动选择编码模式和最小可用版本，默认 L 级纠错。

    - `QuickResponseCode(String text, int errorCorrectionLevel)` → 自动选择编码模式和最小可用版本，指定纠错等级。

    - `QuickResponseCode(String text, int errorCorrectionLevel, int version, int mode)` → 完全手动指定所有参数。

- **静态方法（encode）**：

    - `static boolean[][] encode(String text)` → 自动选择编码模式和最小可用版本，默认 L 级纠错，返回二维码布尔矩阵。

    - `static boolean[][] encode(String text, int errorCorrectionLevel)` → 自动选择编码模式和最小可用版本，指定纠错等级，返回二维码布尔矩阵。

    - `static boolean[][] encode(String text, int errorCorrectionLevel, int version, int mode)` → 完全手动指定参数，返回二维码布尔矩阵。编码过程中会打印纠错等级、版本、编码模式等调试信息。

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
import aio.collection.Sort;
import aio.datastructure.HeapAscend;
import aio.datetime.Datetime;
import aio.geography.ElevationMap;
import aio.mathematics.Maths;
public class demo
{
    public static void main(String args[])
    {
        //排序
        int arr[]={5,2,9,1,5,6};
        Sort.quickDualPivot(arr);
        //[1, 2, 5, 5, 6, 9]
        System.out.println(java.util.Arrays.toString(arr));
        //升序堆
        HeapAscend heap=new HeapAscend(3,1,4,1,5);
        //1
        System.out.println(heap.output());
        //日期
        Datetime now=new Datetime();
        //AD 2026/01/01 12:00:00.000 UTC+8（示例）
        System.out.println(now);
        // 高程地图 & 地形生成
        ElevationMap map=new ElevationMap(200,200);
        map.overlayPerlinTerrain(12345L,1000.0);
        map.calculateStatistics();
        System.out.println("最高海拔: "+map.max);
        //计算表达式
        double result=Maths.calculate("(3+4)*2-5/2");
        //11.5
        System.out.println(result);
    }
}
```

---

## 如有疑问或需要进一步了解某个类的详细用法，请查阅源代码中的 Javadoc 注释。