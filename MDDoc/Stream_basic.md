# Stream Study

## Summary

#### Stream

- Conllection 提供了新的stream的方法

- 流不存储值，而是通过管道的方式获取值

- 本质是函数式的，对流的操作会生成一个结果，不过并不会修改低层的数据源 ，集合可以为流的底层数据源 

- 流的操作都是延迟查找  ，很多流的操作（过滤、映射、排序等）都是可以延迟实现的 （流的操作分为两类 ：1.延时求值； 2.及时求值）

####  compare

- 描述性语言

- select name from  suduent  where age > 20 and  sex ='M' order by age desc;

- stream 与 sql 类比

- student.stream.filter(student -> student.getAge > 20).filter(student ->    student.getSex.equals("M")).sorted(...).forEach(student -> system.out.print(studemt.getName))







