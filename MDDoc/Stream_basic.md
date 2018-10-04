# Stream Study

## Summary

#### Stream

- Conllection 提供了新的stream的方法

- 流不存储值，而是通过管道的方式获取值

- 本质是函数式的，对流的操作会生成一个结果，不过并不会修改低层的数据源 ，集合可以为流的底层数据源 

- 流的操作都是延迟查找  ，很多流的操作（过滤、映射、排序等）都是可以延迟实现的 （流的操作分为两类 ：1.延时求值； 2.及时求值）

####  2.compare

- 描述性语言

- select name from  suduent  where age > 20 and  sex ='M' order by age desc;

- stream 与 sql 类比

- student.stream.filter(student -> student.getAge > 20).filter(student ->     student.getSex.equals("M")).sorted(...).forEach(student -> system.out.print(studemt.getName))

#### 3.外部迭代（java8之前）
- 基于面向对象的命令模式编程，操作的对象是集合

- for循环 if判断   addList 然后conlections.sort() 

- 集合 ----》业务代码

- 集合关注的是数据与数据存储本身，流关注的是对数据的处理；

- 流与迭代器相似：流一旦被使用 关闭不能再使用；

#### 4.内部迭代
- 集合所生成stream 操作的目标是stream 

- 自己写代码会和流融合到一起，当执行终止操作时 ，会一并处理

- 处理提供的函数，中间操作返回 stream，终止操作返回其他类型单个值或者不返回

#### 5.并发流
- fork join 


####  函数接口

- Consumer 接受一个参数不返回值

- BiConsumer 接受两个参数不返回值

- supplier  不接受参数 返回一个结果

- Funciton  就收一个参数 返回一个结果

- BiFunction  接受两个参数 返回一个结果

- BinaryOperator 接受两个参数 返回一个结果和参数类型一样的

#### collector 	(重要概念)
```java
 A <a href="package-summary.html#Reduction">mutable reduction operation</a> that
 accumulates input elements into a mutable result container, optionally transforming
 the accumulated result into a final representation after all input elements
 have been processed.  Reduction operations can be performed either sequentially
 or in parallel.
```
> 它是一个可变的汇集操作，将输入元素累积到一个可变的结果容器中；他会在所有元素处理完毕，将累积的结果转化为最终的表示(这是一个可选的操作)；它支持串行与并行
并行不一定比串行快，并行有线程上下文切换。

```java
The class {@link Collectors} provides implementations of many common mutable reductions.
//cllectors 提供了关于colletor一些常见的汇聚实现，colletors 本身实际上是一个工厂
```

```java
<p>A {@code Collector} is specified by four functions that work together to
accumulate entries into a mutable result container, and optionally perform
a final transform on the result.  They are: 
1.creation of a new result container ({@link #supplier()})
2.incorporating a new data element into a result container ({@link #accumulator()})
3.combining two result containers into one ({@link #combiner()})</li>
4.performing an optional final transform on the container ({@link #finisher()})
```
> 理解 collector是由四个函数组成的，其中supplier用于提供可变容器的，ccumulator 不短往流中累积元素，combiner 用于线程并发将多个部分结果合并成一个，finisher 完成器可有可无的操作 ，不提供一会提供一个默认，将累积的中间结果转换为最终的一种表示

#### combiner

```java 
A function that accepts two partial results and merges them.  The
combiner function may fold state from one argument into the other and
return that, or may return a new result container.
@return a function which combines two partial results into a combined result
```
> 各个部分结果 合并成一个结果返回一个新的结果或者将两个 部分折叠到一个结果中
      如四个线程分别有四个结果 1，2,3,4  合并可能出现
   1, 2 ->1  这种折叠到之前的一个结果中
   1, 4 ->5  这种返回一个新的结果中
  5, 3 ->6  同上返回一个新的结果中


- collector 作为collect 的参数 (重要) 是一个泛型接口 有三个参数<T,A,R>
	-- 

- collect  是一个接口











