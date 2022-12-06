
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class QuickSort {

	public static void main(String[] args) {

		Random randomNum = new Random(); // creating Random object
		int numOfRandNum = 2000; 
		ArrayList<Long> times = new ArrayList<Long>();
		long time = 0;

		//Loop until number of random number is up to 150000
		while(numOfRandNum <= 150000){
			double[] arr = new double[numOfRandNum];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = randomNum.nextDouble(1000); 
				//System.out.print(arr[i] + " "); //uncomment to print unsorted array
			}
			//System.out.println();
			long start = System.currentTimeMillis();
			quickSort(arr, 0 , arr.length - 1);
			numOfRandNum += 2000;
//					for(double n: arr) { //uncomment to print sorted array
//						System.out.print(n + " ");
//					}
//					System.out.println();
			long end = System.currentTimeMillis();
			time = (end-start);
			System.out.println("QuickSort took: " + (time) + " milliseconds");
			times.add(time);
			//System.out.println(times.toString()); //uncomment to print array of time recorded
		}
		//write times to a file
		try {
			FileWriter fw = new FileWriter("res/output2.txt");
			for(Long t: times) {
				fw.write(t + System.lineSeparator());
			}
			fw.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}      
	public static void quickSort(double[] arr, int low, int high) {
		int middle = 0;
		double pivot  = 0;
		int l = 0;
		int h = 0;
		if (arr == null || arr.length == 0){
			return;
		}
		if (low >= high){
			return;
		}
		middle = low + (high - low) / 2;
		pivot = arr[middle]; //element that the array will be partitioned on   
		l = low; //first element of the array
		h = high; //last element of the array

		while (l <= h) {
			//check elements to the left that are lower then pivot
			while (arr[l] < pivot) {
				l++;
			}
			//check elements to the left that are higher then pivot
			while (arr[h] > pivot) {
				h--;
			}
			//compare element from low and high and swap accordingly
			if (l <= h) {
				double temp = arr[l];
				arr[l] = arr[h];
				arr[h] = temp;
				//move the iterator for high and low
				l++; 
				h--;
			}
		}
		//Recursively call quicksort to sort high and low
		if (low < h){
			quickSort(arr, low, h);
		}
		if (high > l){
			quickSort(arr, l, high);
		}
	}
}