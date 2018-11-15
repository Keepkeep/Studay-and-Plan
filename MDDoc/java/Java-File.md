# File 学习笔记

> 记录每一天的成长！

## 目录

#### File类的构造函数(方法)

- File(String pathname)：指定文件或者文件夹的路径来创建一个File文件
- File(File parent, String child)：根据parent抽象路径和child路径名字符串，创建一个新的File实例
- File(String parent, String child)

> 目录分割符：在Windows机器上的目录分隔符是\，在Linux机器上的目录分隔符是/,
> 在Windows上面\与/都可以作为目录分隔符。而且，如果写/的时候，只需要写1个正斜杠即可，而写\的时候，需要写2个反斜杠
