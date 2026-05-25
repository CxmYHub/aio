


# tools_code

[![Gitee stars](https://gitee.com/cxmyee/tools_code/badge/star.svg?theme=yellow)](https://gitee.com/cxmyee/tools_code)

**Java 工具包**——一个包含常用算法、数据结构和工具类的开源项目。

## 项目简介

`tools_code` 是一个纯 Java 实现的工具包，提供了丰富的算法实现、数据结构和常用工具类。项目源代码可直接复制到您的项目中使用，类似 `java.util.*` 的使用方式，无需复杂的依赖配置。

## 功能特性

### 📦 核心包结构

| 包 | 说明 |
|---|---|
| `tools.mathematics` | 数学工具：表达式求值、复数、行列式、坐标计算、直线方程、角度运算、组合数、排列组合、直方图等 |
| `tools.collection` | 集合操作：多种排序算法（二叉快排、双轴快排、归并排序、希尔排序等）、搜索算法（线性、二分、插值）、字符串处理 |
| `tools.data_structure` | 数据结构：链表、栈、队列、树、二叉树、堆、B+树、红黑树、Trie树、并查集、哈希映射、图等 |
| `tools.date` | 日期时间：日历计算、日期时间处理、时间戳转换 |
| `tools.geography` | 地理相关：高程地图、地理坐标、投影坐标 |

### ✨ 主要功能

- **数学计算**：表达式求值、复数运算、行列式计算、排列组合、斐波那契数列
- **排序算法**：冒泡、选择、插入、希尔、快速排序（单轴/双轴）、归并、计数、基数排序
- **搜索算法**：线性搜索、二分搜索、插值搜索
- **数据结构**：动态数组、链表、栈、队列、各种树结构、堆、并查集
- **字符串处理**：回文判断、最长回文子串、正则匹配

## 快速开始

### 引入方式

直接复制 `src` 目录下的 `tools` 包到您的项目中：

```java
import tools.mathematics.*;
import tools.collection.*;
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // 使用 mathematics 包计算表达式
        System.out.println(maths.calculate("(5+4)*3/2-1"));  // 输出: 12.5
        
        // 使用 collection 包进行快速排序
        int[] num = {7, 1, 4, 2, 8, 5};
        sort.quick(num);
        System.out.println(Arrays.toString(num));  // 输出: [1, 2, 4, 5, 7, 8]
    }
}
```

### 环境要求

- JDK 8 或更高版本
- 无第三方依赖，纯标准库实现

## 项目结构

```
tools_code/
├── src/                    # 源代码目录
│   └── tools/
│       ├── mathematics/    # 数学工具包
│       ├── collection/     # 集合工具包
│       ├── data_structure/# 数据结构包
│       ├── date/          # 日期时间包
│       └── geography/      # 地理工具包
├── doc/                    # Javadoc 文档
├── bin/                    # 编译输出目录
└── README.md               # 项目说明文档
```

## 开发环境

推荐使用 Visual Studio Code 配合 Java 扩展插件打开项目。

## 开源协议

本项目仅供学习交流使用，欢迎提交 Issue 和Pull Request。

---

如有问题，欢迎在 Gitee 仓库中提交 Issue。