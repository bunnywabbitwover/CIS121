package test;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		ArrayList <Student> studentQueue = new ArrayList <Student>();

		while(stdin.hasNext()) {

			String name =stdin.next();
			
			//if 'next' or 'end' is not entered continue getting age and grade
			if(!name.equalsIgnoreCase("next") && (!name.equalsIgnoreCase("end"))){
				int age = stdin.nextInt();
				int grade =stdin.nextInt();
				
				//add the student to the correct spot in line based on grade or age
				if(studentQueue.size() > 0) {
					Student last = studentQueue.get(studentQueue.size() -1);
					if(age < last.getAge() || grade < last.getGrade()) {
						studentQueue.add(studentQueue.size() -1, new Student(name, age, grade));
					}else {
						studentQueue.add(new Student(name, age, grade));
					}
				} else {
					studentQueue.add(new Student(name, age, grade));
				}
			}
			
			//if 'next' entered remove the first student
			else if(name.equalsIgnoreCase("next")){
				if(studentQueue.size() > 0) {
					studentQueue.remove(0);
				}else {
					System.out.println("empty");
				}
			}
			
			//if the arrayList is empty displays 'empty'
			else if(studentQueue.size()==0) {
				System.out.println("empty");
				
			//if 'end' entered exit and display ArrayList
			}else if(name.equalsIgnoreCase("end")){
				for(Student x : studentQueue){
					System.out.println(x);
				}
			}
		}
	}
}

class Student {
	private String name;
	private int age;
	private int grade;

	public Student(String name, int age, int grade) {
		this.name = name;
		this.age = age;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getGrade() {
		return grade;
	}

	public String toString() {
		return name + " " + age + " " + grade;
	}
}