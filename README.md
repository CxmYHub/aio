# tools_code

- 一个文件夹，实现了一些常见算法和数据结构。也编写了一些常用小工具。

**以下为VS Code自动生成的Readme，已翻译为中文。**

## 开始使用

欢迎来到 VS Code Java 世界。这里是一个指南，帮助您在 Visual Studio Code 中开始编写 Java 代码。

## 文件夹结构

工作区默认包含两个文件夹：

- `bin`：此处为编译后的.class文件，编译后的输出文件将默认生成在此文件夹中。
- `doc`：此处为使用javadoc自动创建的文档

- `src`：此处为源代码
- `lib`：此处为项目依赖（本项目属于工具包，不应包含第三方依赖）

> 如果您想要自定义文件夹结构，请打开 `.vscode/settings.json` 并更新相关设置。

## 依赖管理

`JAVA PROJECTS` 视图允许您管理依赖项。更多详细信息可以在[这里](https://github.com/microsoft/vscode-java-dependency#manage-dependencies)找到。

## 快速开始

本文件中的任何代码（或包）可直接引入代码或项目中使用，可直接将源代码、源代码文件或整包复制到项目目录中，import后可直接调用（类似java.util.*）