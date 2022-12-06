import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class InsertionSort {

	public static void main(String[] args) {
		Random randomNumber = new Random(); // creating Random object
		int numOfRandNum = 2000; 
		ArrayList<Long> times = new ArrayList<Long>();
		long time = 0;

		//Loop until number of random number is up to 150000
		while(numOfRandNum <= 150000){
			double[] arr = new double[numOfRandNum];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = randomNumber.nextDouble(1000); 
				//System.out.print(arr[j] + " "); //uncomment to print unsorted array
			}
			//System.out.println();

			long start = System.currentTimeMillis();
			insertionSort(arr);
			numOfRandNum += 2000;
			//			for(double n: arr) { //uncomment to print sorted array
			//				System.out.print(n + " ");
			//			}
			//			System.out.println();
			long end = System.currentTimeMillis();
			time = (end-start);
			System.out.println("InsertionSort took: " + (time) + " milliseconds");
			times.add(time);
			//System.out.println(times.toString()); //uncomment to print array of time recorded
		}
		//write times to a file
		try {
			FileWriter fw = new FileWriter("res/output.txt");
			for(Long t: times) {
				fw.write(t + System.lineSeparator());
			}
			fw.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}   

	public static void insertionSort(double array[]) {
		//Traverse the array of random numbers starting a the second element 
		for (int j = 1; j < array.length; j++) {
			double current = array[j]; //second element of the unsorted array
			int i = j-1;  //first element of the unsorted array 

			//Move elements of array that are greater than current, to one position ahead of their current position.
			while ((i > -1) && (array[i] > current)) {
				array[i+1] = array[i];
				i--;
			}
			//Put current after the element that is smaller than it.
			array[i+1] = current;
		}
	}
}
