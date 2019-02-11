package javalearnstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamLearn {
	public static void main(String[] args) {
		List<String> s = Arrays.asList("hello","world","apple");
		s.stream().forEachOrdered(X -> System.out.println(X));
		System.out.println("-------------------------------");
		java.util.stream.Stream<String> slStream =s.stream();
		String[] strings =slStream.toArray(String[]::new);
		for (String string : strings) {
			System.out.println(string);
		}
		//stream 返回 数组集合
		System.out.println("--------------------------------");
		Stream<String> ss= Stream.of("hehe","world","apple");
		ss.collect(Collectors.toList()).forEach(System.out::println);
		
		System.out.println("--------------------------------");
		//使用方法引用
		Stream<String> sss= Stream.of("hehe","world","apple");
		sss.collect(ArrayList::new,ArrayList::add,ArrayList::addAll).forEach(System.out::println);
		
		//使用方法引用  通用型返回集合返回linkedlist
		System.out.println("自定义输出集合");
		Stream<String> s4= Stream.of("hehe","world","apple");
		s4.collect(Collectors.toCollection(LinkedList::new)).forEach(System.out::println); //其实内置就是supplier的行为
	
		//使用方法引用  通用型返回集合返回linkedlist
		System.out.println("自定义输出set集合");
		Stream<String> s5= Stream.of("hehe","world","apple");
		s5.collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println); //其实内置就是supplier的行为
		
		//拼接出一个字符串
		System.out.println("拼接处一个字符串");
		Stream<String> s6= Stream.of("hehe","world","apple");
		String str=s6.collect(Collectors.joining()).toString();
		System.out.println(str);
		
	}

}
