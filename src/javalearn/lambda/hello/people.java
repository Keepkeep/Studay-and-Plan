package javalearn.lambda.hello;

import java.io.Serializable;

public class people implements Serializable{

	/**
	 * 测试对象
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer age;
	
	private String sex;

	
	public people() {
		super();
		// TODO Auto-generated constructor stub
	}

	public people(String name, Integer age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "people [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	
	

}
