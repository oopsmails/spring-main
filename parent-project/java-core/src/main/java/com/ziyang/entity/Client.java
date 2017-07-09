package com.ziyang.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @JsonView, useful feature, it lets you control what fields to display.
 * 
 * 
 * @author liu
 *
 */
public class Client {
	@JsonView(Views.Normal.class)
	private String name;

	@JsonView(Views.Normal.class)
	private int age;

	@JsonView(Views.Normal.class)
	private String position;

	@JsonView(Views.Manager.class)
	private BigDecimal salary;

	@JsonView(Views.Normal.class)
	private List<String> skills;
	
	@JsonView(Views.Normal.class)
	private List<Address> addresses;

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", age=" + age + ", position=" + position + ", salary=" + salary + ", skills="
				+ skills + ", addresses=" + addresses + "]";
	}
	
	
}
