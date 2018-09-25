# Oracle 学习笔记

## 目录
#### 

####  序列
```sql
ORACLE序列的语法格式为：
CREATE SEQUENCE 序列名
[INCREMENT BY n]
[START WITH n]
[{MAXVALUE/ MINVALUE n|NOMAXVALUE}]
[{CYCLE|NOCYCLE}]
[{CACHE n|NOCACHE}];
```
> 1)INCREMENT BY用于定义序列的步长，如果省略，则默认为1，如果出现负值，则代表Oracle序列的值是按照此步长递减的。

> 2)START WITH 定义序列的初始值(即产生的第一个值)，默认为1。

> 3)MAXVALUE 定义序列生成器能产生的最大值。选项NOMAXVALUE是默认选项，代表没有最大值定义，这时对于递增Oracle序列，系统能够产生的最大值是10的27次方;对于递减序列，最大值是-1。

> 4)MINVALUE定义序列生成器能产生的最小值。选项NOMAXVALUE是默认选项，代表没有最小值定义，这时对于递减序列，系统能够产生的最小值是?10的26次方;对于递增序列，最小值是1。

> 5)CYCLE和NOCYCLE 表示当序列生成器的值达到限制值后是否循环。CYCLE代表循环，NOCYCLE代表不循环。如果循环，则当递增序列达到最大值时，循环到最小值;最小值为1。对于递减序列达到最小值时，循环到最大值。如果不循环，达到限制值后，继续产生新值就会发生错误。

#### 序列使用

CURRVAL:	返回序列的当前值。
NEXTVAL：	序列递增，返回下一值。

你不能使用序列的CURRVAL和NEXTVAL，在下面情况下（具体参见官方文档）：
1：在DELETE、SELECT、UPDATE的子查询中
2：在视图或物化事物的查询中。
3：SELECT查询中使用了DISTINCT操作符。
4：SELECT查询中有GROUP BY或ORDER BY

#### 查看当前序列
4：序列查看
```sql
SELECT * FROM USER_SEQUENCES;

SELECT * FROM ALL_SEQUENCES;

SELECT * FROM DBA_SEQUENCES;
```

#### 序列修改

不能修改序列的初始值,否则会报ORA-02283:

SQL> ALTER SEQUENCE SEQ_TEST START WITH 2;

ALTER SEQUENCE SEQ_TEST START WITH 2

ORA-02283: 无法更改启动序列号

SQL> ALTER SEQUENCE SEQ_TEST INCREMENT BY 2;

Sequence altered
