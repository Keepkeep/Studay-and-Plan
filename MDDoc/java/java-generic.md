# Java generic 学习笔记
> 记录每一天的成长！

-[dog]{../../IMG/moji/java-generic-dog.jpg}

## 目录

#### 1.泛型好处
泛型是jdk1.5使用的新特性
泛型的好处：
	1.将运行是异常提前至编译时。
    2.消除强制类型转换，所有的强制转换都是自动和隐式的，提高代码的重用率。
    3.提高了Java程序的类型安全性。

#### 2.泛型不能使用基本数据类型
> 如果需要使用基本数据类型，那么就使用基本数据类型对应的封装类型

	基本数据类型    封装类型
	byte--------------->Byte
	short-------------->Short
	int----------------->Integer
	long--------------->Long
      double----------->Double
      float--------------->Float
      boolean--------->Boolean
      char-------------->Character

```java
public class Generic {
	public static void main(String[] args) {
		ArrayList<int> s =new ArrayList<int>(); //基本类型报错
		ArrayList<Integer> s =new ArrayList<Integer>(); //封装类型
	}

}
```

#### 3. 在方法上自定义泛型
> 自定义泛型就是一个数据类型占位符或者一个数据类型变量
>	格式：
>	修饰符 <声明自定的泛型> 返回值类型 函数名(自定义泛型的类型 参数){
>	方法体
>   }

> 泛型方法需要注意的事项
	-1.泛型方法中自定义泛型具体数据类型是在调用该函数的时候传入实时参数确定的。
    -2.自定义泛型所用的标识符只要符号标识符的命名规范即可；但是我们习惯用一个大小的字母表示
    
```java
public class Generic {
	public static void main(String[] args) {
		getData(5);	
	}
	public static <E>E getData(E o){//在方法上，自定义泛型
		return o;    
	}
}
```

#### 4.类上自定义泛型
> 格式：class 类名<声明自定义泛型>{}

- 泛型类需要注意的事项
	- 1.泛型类上自定义泛型是使用该类创建对象的时候自定的具体数据类型的.
      - 2.如果一个类已经自定义了泛型，使用该类创建对象时，如果没有在指定泛型的具体类型，那么默认为Object类型
      - 3.静态的函数不能使用类上自定义泛型，如果静态函数需要使用，必须要在函数上自定泛型

例子1：
```java
//元素的反转
public class MyArrays{
    public <T> void reverse(T[] arr) {//在方法上，自定义泛型
         for (int starIndex = 0,endIndex = arr.length-1; starIndex < endIndex; starIndex++,endIndex--) {
             T temp = arr[starIndex];
             arr[starIndex] = arr[endIndex];
             arr[endIndex] = temp;
         }
         for (int i = 0; i < arr.length; i++) {
             System.out.print(arr[i]); //返回结果：87654321
         }
     }
  }
public class Demo {
    public static void main(String[] args) {
         MyArrays ma = new MyArrays();
         Integer[] integer = {1,2,3,4,5,6,7,8};
         ma.reverse(integer);
} 
```

例子2
```java
	//元素的反转
  class MyArrays<T>{//在类上，自定义泛型
      public  void reverse(T[] arr) {
         for (int starIndex = 0,endIndex = arr.length-1; starIndex < endIndex; starIndex++,endIndex--) {
             T temp = arr[starIndex];
             arr[starIndex] = arr[endIndex];
             arr[endIndex] = temp;
         }
         /*for (int i = 0; i < arr.length; i++) {
             System.out.print(arr[i]);//返回结果：87654321
         }*/
     }
     public String toString(T[] arr) {//自定义 输出格式
         StringBuilder sb =new StringBuilder();
         for (int i = 0; i < arr.length; i++) {//第一个元素
             if(i == 0){
                 sb.append("["+arr[i]+",");
             }else if(i == arr.length-1){//最后一个元素
                 sb.append(arr[i]+"]");
             }else{//中间的元素
                 sb.append(arr[i]+",");
             }
         }
         return sb.toString();
     }
 }
 public class Demo18 {
     public static void main(String[] args) {
         MyArrays<Integer> ma = new MyArrays<Integer>();
         Integer[] in = {1,2,3,4,5,6,7,8};
         ma.reverse(in);
         System.out.println(ma.toString(in));//返回结果：[8,7,6,5,4,3,2,1]
     }
 }
```

#### 5.接口定义泛型
>格式:interface 接口名<声明自定义泛型>{ }

泛型接口需要注意的事项
    1.在接口上自定义的泛型，具体数据类型是在实现接口的时候指定的。
    2.在接口上自定义的泛型，具体数据类型在实现接口时没有指定，那么默认问哦Object类型。
    3.如果需要在创建接口实现类对象的时候才指定自定义泛型，那么需要一下格式：class<T> 类名 implements 接口<T>{    }

例子：
```java
 public interface Dao<T>{
      public void add(T t);
      public void update(T t);
  }
 public class Demo4<T> implements Dao<T>{
    public static void main(String[] args) {
          Demo4<String> d=new Demo4<String>();
      }
     @Override
     public void add(T t) {
         // TODO Auto-generated method stub
     }
     @Override
     public void update(T t) {
         // TODO Auto-generated method stub
     }
 }
```

#### 6.泛型上下界
>  ? super Integer   允许是Integer类型或者是Integer父类的类型  泛型的下限
>  ? extends Number   允许是Number类型或者Number子类的数据类型  泛型的上限 

```java
/* 泛型的上下限
  * 
  * 需求1：定义一个函数可以接收任意类型的集合对象，要求接收的集合对象只能存储Integer或者是Integer的父类类型的数据
  * 需求2：定义一个函数可以接收任意类型的集合对象，要求接收的集合对象只能存储Number或者是Number的子类类型的数据
  * 
  * 泛型中的通配符：?
  *     <? super Integer>：只能存储Interger或者是Interger的父类元素.   泛型的下限
  *     <? extends Number>：只能存储Number或者Number以下的子类元素.   泛型的上限
  * */
public class Demo19 {
     public static void main(String[] args) {
         //泛型的上线
         ArrayList<Double> list = new ArrayList<Double>();//Number最大，所以<>里的泛型是子类的什么类型都行（上线，看关键字extends）
         getDate(list);
         //泛型的下线
         ArrayList<Integer> list1 = new ArrayList<Integer>();//要看super后面的类型确定当前泛型的类型（下线，看关键字super）
         print(list1);
     }
     //泛型的上线
     public static void getDate(Collection<? extends Number> c){//Number或者以下的都可以 
     }
     //泛型的下线
     public static void print(Collection<? super Integer> c){//Interger或者以上的都可以         
     }
 }
```












