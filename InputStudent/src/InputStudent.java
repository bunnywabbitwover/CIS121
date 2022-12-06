import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputStudent {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		ArrayList<Student> list = new ArrayList<Student>();

		boolean isTrue = true;
		int choice = -1;
		String name = "";
		int grade = -1; 
		Student selectedStudent;
		int newGrade = -1;

		do{
			System.out.println("\nMain menu\r\n" + "Select one of the following options\r\n"
					+ "1. Add a Student\r\n"
					+ "2. Edit student grades\r\n"
					+ "3. Exit\r\n");
			choice = getInt(input);

			//Main menu choice 1 to add student to list.
			if (choice == 1){
				System.out.println("Please enter the student's name: ");
				name = getValidName(input);

				System.out.println("Please enter a grade for " + name + ":");
				grade = getInt(input); 
				list.add(new Student(name, grade));
			}

			//Main menu choice 2 to modify student grade
			else if(choice == 2 && list.isEmpty()) {
				System.out.println("There are no students at this time.\r\nEnter a valid choice.");
			}
			else if(choice == 2 && !list.isEmpty()) {

				System.out.println("What student would you like to enter a grade for?");
				for(int i = 0; i < list.size(); i++) {
					System.out.println(i + 1 + ". " + list.get(i));
				}

				selectedStudent = getValidStudent(input,list);
				System.out.println("Please enter a grade for " + selectedStudent.getName() + ":");
				newGrade = getInt(input); 
				selectedStudent.setGrade(newGrade);
			} 

			//Main menu choice 3 to exit the program
			else if(choice == 3) {
				isTrue = false;
				System.out.println("Thank you, Goodbye!");
			}	
			else if(choice < 1 || choice > 3) { 
				System.out.println("Enter a valid choice from menu: ");
			}
		}while(isTrue);
	}

	//Method to take in user input in a try/catch to check that name entered is not a symbol or digit.
	public static String getValidName(Scanner input){
		String name = "";
		boolean isTrue = true;

		while(isTrue) {
			try {
				name = input.nextLine();
				if (name.equals("") || name.charAt(0) == ' '|| name.charAt(0) == '-' || name.charAt(name.length()-1) == '-'){
					throw new InputMismatchException();
				}
				else {
				for(int i = 0; i < name.length(); i ++){
					char c = name.charAt(i);

					if(!Character.isLetter(c) && !Character.isWhitespace(c) && name.charAt(i) != '-') {
						throw new InputMismatchException();

					}
				}
					isTrue = false;
				}

			}catch (InputMismatchException e) {
				System.out.println("Please enter a name with no symbols or numbers:");
			}
		}
		return name;

	}
	//Method to take in user input in a try/catch to check the entered grade of the student
	public static int getInt(Scanner input) {
		int grade = 0;
		boolean isTrue = true;
		while(isTrue) {
			try {
				String s = input.nextLine();
				grade = Integer.parseInt(s);
				if(grade < 0 || grade > 100)
					throw new InputMismatchException();
				isTrue = false;
			} catch (NumberFormatException e){
				System.out.println("Please enter a valid number: ");
			} catch (InputMismatchException e){
				System.out.println("Please enter a valid number: ");
			}
		}
		return grade;
	}

	//Method that takes in user input and ArrayList in a try/catch to check a selected student to change their grade.
	public static Student getValidStudent(Scanner input, ArrayList<Student> al) {
		Student selectedStudent = new Student();
		int number = 0;
		boolean isTrue = true;
		while(isTrue) {
			try {
				String s = input.nextLine();
				number = Integer.parseInt(s) - 1;
				selectedStudent = al.get(number);
				isTrue = false;
			} catch (NumberFormatException e){ 
				System.out.println("Please enter a valid number: ");
			} catch (IndexOutOfBoundsException e){ 
				System.out.println("Please enter a valid number:");
			}
		}
		return selectedStudent;
	}
}

