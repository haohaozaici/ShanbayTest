# ShanbayTestß
> 因为之前没有接触过Markdown解析器相关的内容，所以大部分代码参考使用了[zzhoujay/Markdown](https://github.com/zzhoujay/Markdown)项目

[demo apk点击下载](https://raw.githubusercontent.com/haohaozaici/ShanbayTest/master/app/release/app-release.apk)

## 要求
1. 根据要求，实现Markdown语法子集的渲染控件
2. 代码结构合理、风格良好、可拓展
3. 图片加载使用Glide
4. 使用Recycler View实现渲染
5. 使用编译原理相关内容实现Markdown解析

## 我的完成情况

### 实现Markdown语法子集的渲染控件
根据子集要求和`zzhoujay/Markdown`库的实现，抽取、简化了相关的`接口`和`接口实现`。
修改保留了：
1. 标题h1、h2、h3
2. 有序、无序列表
3. 常规文本、粗体、斜体、链接
4. 图片
5. 嵌套

将原库中最终返回的`Spanned`接口，改为返回`List<Line>`提供给`Recycler View`渲染。

### 代码结构，图片加载，Recycler View实现渲染
原库采用了自己实现的双向链表满足了完整markdown所需的数据结构，使用接口将正则匹配、渲染的方法和他们的实现分离。

我在原有基础上删减了接口，增加了图片相关类，修改了图片匹配的处理方法。使用`Recycler View`渲染，并使用[MultiType](https://github.com/drakeet/MultiType)将普通文本渲染和图片选渲染分离，用Glide完成图片的加载。

### 编译原理与Markdown解析
原库中使用递归语法和`inline(Line line)`方法实现了各种类型的嵌套，由于效果图中无嵌套、引用、缩进相关特性，故删除了大部分实现代码。

## 收获
不使用`LinkedList`手动实现双向链表，熟悉了双向链表的实现、适用情况。

正则匹配的使用

`SpannableStringBuilder`类的使用及相关接口

接口、实现分离

库方法的使用域限制