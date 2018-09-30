package javalearn.lambda.hello;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javalearn.lambda.hello.*; 
/**
 * 
 * @author huhu
 * 替代内部匿名函数 和迭代集合
 */

public class hello {

	public static void main(String[] args) {
		HelloApi HelloApi = message ->System.out.println("Hello " + message);
	    HelloApi.sayhello("huhu"); 
	    SumNum   sumNum =(int a,int b) ->a+b;
	    int i=sumNum.sum(1, 3);
	    System.out.println(i);
	    
	    // 匿名函数
	    new Thread(() -> System.out.println("It's a lambda function!")).start();
	    
	    //迭代
	    List<String> languages = Arrays.asList("java","scala","python");
        //before java8
	    for(String each:languages) {
            System.out.println(each);
        }
        //after java8
        languages.forEach(y -> System.out.println(y));
        languages.forEach(System.out::println);
        
        
	    // Map
        //map函数可以说是函数式编程里最重要的一个方法了。map的作用是将一个对象变换为另外一个。在我们的例子中，
        //就是通过map方法将cost增加了0,05倍的大小然后输出。
        List<Integer> test=Arrays.asList(20,30,40,50);
        test.stream().map(x -> x + x*0.05).forEach(x -> System.out.println(x));
        
        //修改对象
        people  P1 = new people("张三",18,"男");
        people  P2 = new people("王五",28,"男");
        people  P3 = new people("马六",38,"男");
        List<people> p = Arrays.asList(P1,P2,P3);
        p.stream().map(x -> x.getAge()+2).forEach(System.out::println);
        
        //函数式编程  reduce
        List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
        double a = cost.stream().map(x -> x +x*0.05).reduce((sum,x) -> sum + x).get();
        System.out.println(a);
      
        //相信用map+reduce+lambda表达式的写法高出不止一个level
        //filter也是我们经常使用的一个操作。在操作集合的时候，经常需要从原始的集合中过滤掉一部分元素。
        List<Double> cost1 = Arrays.asList(10.0,20.0,30.0);
        List<Double> cs= cost1.stream().filter(x -> x>20).collect(Collectors.toList());
        cs.stream().forEach(System.out::println);
        
        // Java 8也添加了一个包，叫做 java.util.function。它包含了很多类，用来支持Java的函数式编程。
        //  其中一个便是Predicate，使用 java.util.function.Predicate 函数式接口以及lambda表达式，可以向API方法添加逻辑，用更少的代码支持更多的动态行为
        
        List<String> language2 = Arrays.asList("Java","Python","scala","Shell","R");
        System.out.println("Language starts with J: ");
        filterTest(language2,x -> x.startsWith("J"));
        System.out.println("\nLanguage ends with a: ");
        filterTest(language2,x -> x.endsWith("a"));
        System.out.println("\nAll languages: ");
        filterTest(language2,x -> true);
        System.out.println("\nNo languages: ");
        filterTest(language2,x -> false);
        System.out.println("\nLanguage length bigger three: ");
        filterTest(language2,x -> x.length() > 4); 
        
        
        //在Lambda表达式外部的局部变量会被JVM隐式的编译成final类型，因此只能访问外而不能修改。
        int n = 3;
        Calculate calculate = param -> {
            //n=10; 编译错误
            return n + param;
        };
       System.out.println( calculate.calculate(10));
    } 
	
	public static void filterTest(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }
	
}
