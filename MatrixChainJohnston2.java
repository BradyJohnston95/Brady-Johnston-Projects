/*************************************************************************

Brady Johnston

This program takes a sequence of matrices and calculates the optimal
order to multiply them in to do the minimum amount of multiplications.
We use dynamic programming to fill and refer back to graphs and choose
the minimum at each step to eventually find the overall minimum. We then
use one of our graphs to recur and output a string based on that parenthesization.

You can contact me at bradyjohnston95@gmail.com if you have any questions.
Thank you for taking a look at my code!

**************************************************************************/

import java.util.*;

public class MatrixChainJohnston2 {
	public static void main(String[] args) {
		
		// This will hold the temporary values when we multiply at each
		// cell of the values array from which we choose the minimum.
		// TempIndex is how we will build this vector dynamically
		Vector<Integer> TempValues = new Vector<Integer>();
		int TempIndex = 0;
	    	    
	    // Create Scanner, gather the information for the matrices to be multiplied,
	 	// and create the variables for those matrices
	 	Scanner scan = new Scanner(System.in);
	 	System.out.print("Plese enter the number of matrices you would like to multiply: ");
	 	int matrixcount = scan.nextInt();
	 	int[] rows = new int[matrixcount];
	    int[] columns = new int[matrixcount]; 
	    
	 	System.out.print("Enter the number of rows for matrix 1: "); rows[0] = scan.nextInt();
	 	for(int i = 0; i < rows.length; i++) {
	 		System.out.print("Enter the number of columns for matrix " + (i+1) + ":");
	 		columns[i] = scan.nextInt();
	 		if(i < rows.length-1) {	rows[i+1] = columns[i]; }
	 	}
	 	scan.close();	// Close scanner when done
	 	
	 	// Create string array for input matrices dimensions to make
	 	// referencing them later easier
	 	String[] matrices = new String[rows.length];
	 	for(int i = 0; i < rows.length; i++) {
	 		matrices[i] = rows[i] + "x" + columns[i];
	 	}
	 	
	 	// Print out matrices array for debugging
	 	/*
	 	System.out.println("Matrices string array: ");
	 	for(int i = 0; i < matrices.length; i++) {
	 		System.out.print(matrices[i] + " ");
	 	}System.out.println();
	 	*/
	 	
	 	// These arrays will keep track of the minimum amount of
	    // multiplications for each combination and the places at which
	    // we will divide those matrices by parenthesis. Their size
	    // is initialized to the length of our rows array
	    int[][] MinValues = new int[rows.length][rows.length];
	    int[][] MinLocations = new int[rows.length][rows.length];
	 	
	    // Each entry of MinValues that represents one matrix will
	    // be zero since it is not multiplied by anything so we
	    // set [0][0], [1][1], etc each to zero
	    for(int i = 0; i< rows.length; i++) {
	    	MinValues[i][i] = 0;
	    }
	    
	    // Each entry that represents two matrices does not require
	    // any choosing, so we just multiply each out
	    for(int i = 1; i < rows.length; i++) {
	    	MinValues[i-1][i] = rows[i] * columns[i] * rows[i-1];
	    }
	    
	    // Initialize the diagonal for MinLocations as zero for recursive purposes
	    for(int i=0; i < rows.length; i++) {
	    	MinLocations[i][i] = 0;
	    }
	    
	    // For each subsequent cell we must choose between orderings. We will
	    // hold each potential value in TempValues and choose the smallest when
	    // we are done. The TempLocation will store that parenthesis location
	    // which matches the minimum value. I have written a helper function
	    // below to simplify finding that minimum.
	    for(int i = 0; i < rows.length-1; i++) {
	    	// This variable holds the original i value while we modify it
	    	// in order to traverse diagonally then we can revert back
	    	for(int j = i+2, h = 0; j < rows.length; j++, h++) {
	    		TempIndex = h;
	    		while(TempIndex < j){
	    			TempValues.add(MinValues[h][TempIndex] + MinValues[TempIndex+1][j] + (rows[h]*columns[TempIndex]*columns[j]));
	    			TempIndex++;
	    		}
	    		//find minimum with vector and make changes to graphs
	            MinLocations[h][j] = MinimumFinder(TempValues) + h + 1;
	            MinValues[h][j] = TempValues.elementAt(MinimumFinder(TempValues));
	            TempValues.clear();
	    	}
	    }

	    // Printing out MinValues and MinLocations for debugging purposes
	    /*
	    for(int i = 0; i < rows.length; i++) {
	    	for(int j = 0; j < rows.length; j++) {
	    		System.out.print(MinValues[i][j] + "\t");
	    	}
	    	System.out.println();
	    }System.out.println();
	    
	    for(int i = 0; i < rows.length; i++) {
	    	for(int j = 0; j < rows.length; j++) {
	    		System.out.print(MinLocations[i][j] + "\t");
	    	}
	    	System.out.println();
	    }System.out.println();
	    */
	    
	    // Finally print out the optimal parenthesization and multiplications needed from the graphs built above
	    System.out.println(PrintParenthesis(matrices, MinLocations, 0, rows.length-1) + " with " + MinValues[0][rows.length-1] + " multiplications");

	}
	
	// This helper function is a quick and easy way for me to send values and
	// determine the minimum since the number of matrices determines the number
	// of things compared and can vary
	public static int MinimumFinder(Vector<Integer> x) {
		int min = 0;
	      for(int i=1; i<x.size(); i++){
	         if(x.elementAt(min) > x.elementAt(i)){
	            min = i;
	         }
	      }
	    return min;
	}
	
	// This function will take our overall string of matrices, the parenthesization chart,
	// and z1/z2 which represent the beginning and end of our current range. Since we will
	// divide the overall string at each parenthesization step, this helps us remember
	// where this substring is located in relation to the entire sequence of matrices
	public static String PrintParenthesis(String[] x, int[][] y, int z1, int z2) {
		
		// result is what will be returned. Tempref refers to our parenthesization chart
		// and is pulled down each time to know where the parenthesis go for that range
		String result = new String(); int tempref = y[z1][z2];
		
		// If we only have 1 or 2 matrices in this substring, there is no recurring involved
		if(x.length <= 1) {			result = "(" + x[0] + ")"; 					}
		else if(x.length == 2) {	result = "(" + x[0] + " * " + x[1] + ")"; 	}
		
		// We will break the string into two substrings based on the location of our Tempref
		// where the parenthesis go. We make this point Tempref-z1 so we adapt the original
		// Tempref value to make sense for this substring where z1 is not always zero. We
		// then recur with those substrings
		else {		
			String[] Segment1 = Arrays.copyOfRange(x, 0, tempref-z1);
			String[] Segment2 = Arrays.copyOfRange(x, tempref-z1, x.length);
			result = "(" + PrintParenthesis(Segment1, y, z1, tempref-1) + " * " + PrintParenthesis(Segment2, y, tempref, z2) + ")";
		}	
		return result;
	}
}
