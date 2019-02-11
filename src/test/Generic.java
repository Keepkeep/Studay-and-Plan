package test;

import java.util.ArrayList;

public class Generic {
	public static void main(String[] args) {
		getData(5);
		
	}
	public static <E>E getData(E o){//在方法上，自定义泛型
		return o;    
	}

}
