package javalearnstream;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLearn2 {
	public static void main(String[] args) {
		//转换为大写 用map映射
		//stream 提供一般为高阶函数  接受lambda表达式 和方法引用 
		// map 使用的function 接受一个参数 返回一个参数
		List<String> s1 = Arrays.asList("hello","world","huhu");
		s1.stream().map(s -> s.toUpperCase()).collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::println);
		
		//flatMap
		System.out.println("多个几个合并");
		Stream<List<Integer>> s7 = Stream.of(Arrays.asList(1),Arrays.asList(2,3),Arrays.asList(4,5,6));
		s7.flatMap(theList -> theList.stream()).map(ti -> ti*ti).forEach(System.out::println);
		//stream  iterate 生成无限 流 所以加以限制limit
		Stream.iterate(1,ietem -> ietem + 2).limit(6).forEach(System.out::println);
		
		//求和
		Stream<Integer> st=Stream.iterate(1, Item -> Item+1).limit(6);
		Integer integer=st.filter(it -> it >2).mapToInt(x -> x*2).skip(2).limit(2).sum();
		System.out.println(integer);
		
		//note  stream 需要注意 终止操作和中间操作  如果没有foreach 这个操作 中间的操作是不会执行的
		Stream<String> ss = Arrays.asList("hello","world","cat").stream();
		ss.map(x -> {
			String  s=x.substring(0, 1).toUpperCase()+x.substring(1);
			System.out.println("test");
			return s;
		}).forEach(x ->{System.out.println(x);});
		
		
	
	}
}
