# tools_code
这是一个**Java工具包**，包含了一些常用算法和数据结构的实现。也编写了一些常用工具类。

本包中的源代码、源代码文件或整包可以直接引入代码或项目中使用，可将其复制到项目源代码目录中，import后可直接调用（类似java.util.*）。

这是一个简单Java项目，不使用如Maven的项目构建器。

## 本项目使用Visual Studio Code开发
- 建议您在Visual Studio Code中打开项目文件夹。
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
## 依赖管理
本项目属于工具包，基于Java标准库，不应包含第三方依赖。