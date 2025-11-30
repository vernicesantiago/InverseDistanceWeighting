package inverseDistanceWeighting;

import java.util.Arrays;

public class Main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//this is the input matrix
		double[][] matrixInput = {
				{1.0, Double.NaN, 3.0},
				{Double.NaN, Double.NaN, 2.0},
				{4.0, 5.0, Double.NaN}
		};

		double[][] matrixIDW = idw(matrixInput);
		
		//prints out the final matrix after IDW
		System.out.println("Updated Matrix after Inverse Distance Weighting: \n");
		for (int d=0; d < matrixIDW.length; d++) {
			System.out.println(Arrays.toString(matrixIDW[d]));
		}
		
		
	} //static void main
	
	static double[][] idw(double[][] data) {
		
		//declaring a duplicate of the input matrix with same size
		double[][] matrixUpdated = new double[data.length][data[0].length];
		
		//duplicating the contents of the input matrix into the updated matrix
		//the updated matrix will be the final output matrix after IDW later
		for (int e=0; e < data.length; e++) {
			for (int f=0; f < data[0].length; f++) {
				matrixUpdated[e][f] = data[e][f];
			}
		}
		
		//for loops (g and h) to get each matrix value
		for (int g=0; g < data.length; g++) {
			for (int h=0; h < data[0].length; h++) {
				boolean z_NaN = Double.isNaN(data[g][h]); //checks if current matrix value is known or unknown/NaN	
				
				if (z_NaN==true) { //if current matrix value is NaN then perform IDW
					
					double numSum = 0; //declaring the sum of numerator as zero to be used in the formula later
					double denSum = 0; //declaring the sum of numerator as zero to be used in the formula later
					
					//for loops (i and j) to get matrix values for use in the IDW formula
					for (int i=0; i < data.length; i++) {
						for (int j=0; j < data[0].length; j++) {							
							if (g==i && h==j) { //the current matrix value is skipped and not used in the IDW formula
									continue; 
							} else {
								double z_pair = data[i][j]; //z_pair refers to the other matrix values
								boolean z_pair_NaN = Double.isNaN(z_pair); //checks if the z_pair is known or unknown/NaN
								if (z_pair_NaN==true) { //if z_pair is unknown, then skip
									continue;
								} else { //if z_pair is known, then perform IDW
									double p = 2; //specified parameter
									double dist = Math.sqrt(
											Math.pow((g-i),2)+
											Math.pow((h-j),2)
									); //computes for the distance between current matrix value and z_pair
									double num = z_pair/Math.pow(dist,p); //computes for the numerator or the product of weight and the z_pair
									double den = 1/Math.pow(dist,p); //computes for the denominator or the weight
									numSum += num; //adds computed numerator to numSum
									denSum += den; //adds computed denominator to denSum
								} //compute numerator and denominator
							} //if pair is not the same location then compute

						} //for loop j
					} //for loop i

					//once all known values of the matrix were looped and the numerators and denominator computed
					//then the summation of numerators and the summation of denominators are used to compute the updated matrix value
					matrixUpdated[g][h] = numSum/denSum; //IDW formula
					
				} //if NaN condition
				
			} //for loop h
		} //for loop g
		
		return matrixUpdated;
		
	} //idw method
	
} //class main
