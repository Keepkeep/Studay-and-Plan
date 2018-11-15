# File 学习笔记

> 记录每一天的成长！

## 目录

#### File类的构造函数(方法)

- File(String pathname)：指定文件或者文件夹的路径来创建一个File文件
- File(File parent, String child)：根据parent抽象路径和child路径名字符串，创建一个新的File实例
- File(String parent, String child)

> 目录分割符：在Windows机器上的目录分隔符是\，在Linux机器上的目录分隔符是/,
> 在Windows上面\与/都可以作为目录分隔符。而且，如果写/的时候，只需要写1个正斜杠即可，而写\的时候，需要写2个反斜杠


```java
 public class Demo1 {
    public static void main(String[] args) throws IOException {
         File file = new File("F:/a.txt");//指定路径    [构造函数]
         file.createNewFile();//创建文件（空文件）    [方法]
         
         File files = new File("F:\\","b.txt");//第一个参数是路径，第二个参数是要创建文件的文件名    [构造函数]
         files.createNewFile();//创建文件（空文件）    [方法]
     }
 }
```
#### File类的一些常用方法
> 创建：

  - createNewFile()：在指定位置创建空文件，成功返回true，如果已存在就不再创建了返回false
  - mkdir()：在指定位置创建目录，这之后创建最后一级目录，如果上级目录不存在就抛出异常.
  - mkdirs()：在指定位置创建目录，这也会创建路径中所有不存在的父目录
  - renameTo(File dest)：重命名文件或文件夹。文件不同时，相当于文件的剪切，剪切的时候不能操作非空文件夹。移动/重命名成功返回true，失败返回false

> 删除：
	
   - delete() ：删除一个文件或者空文件夹,如果文件夹不为空,则不能删除,成返回true,失败返回false
   - deleteOnExit()： 在虚拟机终止时,请求删除此抽象路径名的文件或者目录,保证文件异常时也可以删除文件
   
> 判断：
   - exists()：判断指定的文件或者文件夹是否存在
   - isFile()：判断是否是一个文件；如果不存在，则为false
   - isDirectory()：判断是否是一个文件夹
   - isHidden()：判断指定的文件是否是隐藏文件
   - isAbsolute()：判断指定的文件或文件夹是否是在绝对路径下
   
> 获取：
  - getName()：获取文件或文件夹名称
  - getPath()：返回的是绝对路径,可以是相对路径,但是目录要指定
  - getAbsolutePath()：获取绝对路径
  - length()：获取文件的大小(字节为单位)
  - getParent()：获取文件的父路径
  - lastModified()：获取文件最后一次修改的时间
>  注意：
  \.  ：一个点表示当前路径
  \.\. ：两个点表示上一级路径
  
```java
 public class Demo3 {
     public static void main(String[] args) {
         /*File file=new File("..\\homeWork.java");
         System.out.println(file.exists());*/
         
         File f=new File("..\\..\\a.txt");//上上级路径下的a.txt文件
         System.out.println("获取文件名："+f.getName());//返回值：a.txt
         System.out.println("获取相对路径："+f.getPath());//返回值：..\..\a.txt   说明：a.txt文件在C:\MyEclipse10路径下
         /** 当上面两条输出语句的绝对路径下没有a.txt这个文件时，也是输出这个结果，因为他获取的是当前对象f的值*/
         System.out.println(f.exists());//返回值：true     说明：C:\MyEclipse10路径下有a.txt文件
         System.out.println("获取绝对路径："+f.getAbsolutePath());//返回值：C:\MyEclipse10\MyCode\MyJavaCode\..\..\a.txt
         System.out.println("获取文件的大小(字节为单位)："+f.length());//返回值：10
         System.out.println("获取文件的父路径："+f.getParent());//返回值：..\..   说明：其父路径为C:\MyEclipse10
         long lm=f.lastModified();
         Date date=new Date(lm);
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");    
         System.out.println("获取文件最后一次修改的时间："+sdf.format(date));//返回值：2018年06月25日 11:29:49
     }
 }
```
#### 简单案例(递归列出所有文件)
```java
public class FileDemo{
    public static void main(String[] args){
        File f =new File("e:\\test");
        showDir(f);
    }
    public static void showDir(File dir){
        System.out.println(dir);
        File[] files =dir.listFiles();
        for(File file:files){
            if(file.isDirectory())
                showDir(file);
            else
                System.out.println(file);
        }
    }
}
```




  





