package javalearnstream;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javalearn.lambda.hello.people;

/**
 * 
 * @author huhu group 学习 2018-10-4
 */
public class StreamGroup {
	static List<String> s1 = Arrays.asList("zhangsan", "lishi", "wangwu");
	static List<String> s2 = Arrays.asList("hi", "hello", "您好！");
	private static Map<Boolean, List<people>> collect;

	public static void main(String[] args) {
		group();
		group2();
		group3();
		group4();
	}

	public static void group() {
		s1.stream().flatMap(x -> s2.stream().map(x2 -> x + "问候：" + x2)).collect(Collectors.toList())
				.forEach(System.out::println);
	}

	public static void group2() {
		s1.stream().flatMap(x -> s2.stream().map(x2 -> {
			System.out.println(x + x2);
			return x + x2;
		})).collect(Collectors.toList());
	}

	// 更具名字来分组
	public static void group3() {
		people p1 = new people("张三", 18, "m");
		people p2 = new people("张三", 20, "m");
		people p3 = new people("李四", 18, "m");
		people p4 = new people("张三", 18, "m");
		List<people> li = Arrays.asList(p1, p2, p3, p4);
		Map<String, List<people>> st = li.stream().collect(Collectors.groupingBy(people::getName));
		System.out.println(st);
	}

	// 分区
	public static void group4(){
		people p1 =new  people("张三",18,"m");
		people p2 =new  people("张三",20,"m");
		people p3 =new  people("李四",17,"m");
		people p4 =new  people("张三",16,"m");
		List<people> li = Arrays.asList(p1,p2,p3,p4);
		collect = li.stream().collect(Collectors.partitioningBy(people -> people.getAge()>18));
		System.out.println(collect);
		
	}
}