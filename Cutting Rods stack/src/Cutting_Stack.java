import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author kranthi
 *
 */
public class Cutting_Stack {
	private int rod_size ;
	private int number_Of_Rods;
	private double[] cutting_Sizes;
	private int[] rods_needed;
	Map<String, Integer> pattern;
	public Cutting_Stack() {
		pattern = new HashMap<String,Integer>();
	}
	public void setRod_size(int rod_size) {
		this.rod_size = rod_size;
	}
	public void setCutting_Sizes(double[] cutting_Sizes) {
		this.cutting_Sizes = cutting_Sizes;
	}
	public void setRods_needed(int[] rods_needed) {
		this.rods_needed = rods_needed;
	}
	
	public void printPattern() {
		System.out.print("\n\n************** The pattern to cut ***************** \n\n");
		for (String pat : pattern.keySet()) {
			System.out.println(pat +"  ------->  "+pattern.get(pat));
		}
	}
	
	public void generatePatters() {
		while(number_Of_Rods>0) {
			List<Double> optionsToCut = new ArrayList<Double>();
			List<Integer> optionIndex = new ArrayList<Integer>();
			for(int i=0;i<cutting_Sizes.length;i++) {
				if(rods_needed[i]!=0) {
					double temp = cutting_Sizes[i];
					for(int j=0;j<rods_needed[i];j++) {
						if(temp<=rod_size) {
							optionsToCut.add(cutting_Sizes[i]);
							optionIndex.add(i);
						}else {
							break;
						}
						temp +=cutting_Sizes[i];
					}
				}
					
			}
			int min = Integer.MAX_VALUE;
			List<Integer> subArrIndex = findMaxSubArraySum(optionsToCut);
			Set<Integer> indices = new HashSet<Integer>();
			Map<Integer,Integer> indexCutperRod = new HashMap<>();
			String pat ="";
			for (Integer integer : subArrIndex) {
				pat+=optionsToCut.get(integer)+"\t";
				int index = optionIndex.get(integer);
				if(indexCutperRod.containsKey(index))
				{
					indexCutperRod.put(index, indexCutperRod.get(index)+1);
				}else {
					indexCutperRod.put(index, 1);
				}
				
				indices.add(index);
			}
			for (Integer integer : indices) {
				int temp = rods_needed[integer]/indexCutperRod.get(integer);
				if(min>temp) {
					min = temp;
				}
			}
			for (Integer integer : indices) {
				rods_needed[integer]-=(min*indexCutperRod.get(integer));
				if(rods_needed[integer]==0)
					number_Of_Rods--;
			}
			if(pattern.containsKey(pat)) {
				pattern.put(pat, pattern.get(pat)+min);
			}else {
				pattern.put(pat, min);
			}
			
			
			
			
		}
	}
	
	public void getInputs() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nEnter Rod size(m) :");
		rod_size=scanner.nextInt();
		System.out.print("\nEnter Number of sizes to be cut :");
		number_Of_Rods =scanner.nextInt();
		cutting_Sizes = new double[number_Of_Rods];
		rods_needed= new int[number_Of_Rods];
		for(int i=1;i<=number_Of_Rods;i++) {
			System.out.print("\nEnter size of the rod "+i+" : ");
			cutting_Sizes[i-1]=scanner.nextDouble();
			if(cutting_Sizes[i-1]>rod_size) {
				System.out.println("Sorry! insufficient rod size.  ");
			}
			System.out.print("\nEnter number of "+cutting_Sizes[i-1] +"(m) rods needed  : ");
			rods_needed[i-1]=scanner.nextInt();
		}
	}
		
	/*
	 * This will return the indices of the element of List ,
	 *  such that the Max sum of elements is less than rod size 
	 */
	public  List<Integer> findMaxSubArraySum(List<Double> optionsToCut) {
		List<Integer> finalSet = null;
		double sum ,high=0;
		for(int i=0; i<optionsToCut.size();i++) {
			List<Integer> tempSet = new ArrayList<Integer>();
			sum=0;
			double temp = optionsToCut.get(i);
			sum= temp;
			tempSet.add(i);
			for(int j=i+1;j<optionsToCut.size();j++) {
				temp = optionsToCut.get(j);
				sum+=temp;
				if(sum <= rod_size) {
					tempSet.add(j);
					if(sum == rod_size) {
						break;
					}
				}else {
					sum -=temp;
				}
			}
			if(sum==rod_size) {
				return tempSet;
			}
			if(high<sum) {
				high = sum;
				finalSet = tempSet;
			}
			
			
		}
		return finalSet;
	}

}
