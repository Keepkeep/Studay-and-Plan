package javalearn;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.security.auth.x500.X500Principal;

import javalearn.lambda.hello.people;

public class Hello {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> li =Arrays.asList("hello","world","huhu");
		li.forEach(System.out::println);
		System.out.println("----------------------");
		li.forEach(s -> System.out.println(s));
		li.forEach(s -> {
			System.out.println(s);
		});
		System.out.println("----------------------");
		//方法引用
		li.stream().map(X -> X.toUpperCase()).forEach(System.out::println);
		System.out.println("----------------------");
		
		// supplier
		Supplier<Hello> s = (Hello :: new );
		System.out.println(s.get());
	
	}
}
