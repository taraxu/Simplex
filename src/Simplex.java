import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Simplex {
	double [] coefEco;
	double [][] coefCont;
	double [] qy;
	double[] cp;
	double z;
	
	Simplex () {
		this.coefEco = new double[] {6, 7, 8, 0, 0, 0};
		this.coefCont = new double[][] {
				{1, 2, 1, 1, 0, 0},
				{3, 4, 2, 0, 1, 0},
				{2, 6, 4, 0, 0, 1}
		};
		this.qy = new double[] {100, 120, 200};
		this.cp = calCp(); 
		this.z = 0;
		
	};
	
	public boolean max() {
		boolean option = true;
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous maximiser ou minimiser le resultat? ");
		String max = sc.nextLine();
		if(max == "maximiser") {			
			option = true;
		}
		return option;
	}
	
	 public void getDiffNbvNbcont() {
		 int diffNbvNbcont = this.coefEco.length - this.coefEco.length;
		 if(diffNbvNbcont >0) {
			// ?????????????????????????? 
		 }
	 };
	 
	 public double[] calCp() {
		this.cp = new double[this.coefCont.length];
		int deltaLength = this.coefEco.length - cp.length;  //number of variable in Eco table
		
		// loop the table of vdb = deltalength
		for (int indexCP = 0; indexCP < this.cp.length; indexCP++) {
			 this.cp[indexCP] = this.coefEco[indexCP + deltaLength];   // cp value correspond the rest value of Eco table after the number of variable
		}
		return this.cp;
	 }
	 
	 
	 public double[] calZj() { 
		 double[] eachRowRes = new double[this.coefCont.length];
		 double[] zj = new double[this.coefCont[0].length];
		 double sum;
		 
		 for (int col = 0; col < this.coefCont[0].length; col++) {
			 sum = 0;
			 for (int row = 0; row < this.coefCont.length; row++) {
				 eachRowRes[row] = this.cp[row] * this.coefCont[row][col];
				 sum += eachRowRes[row];
			 }
			 zj[col] = sum;
			 
		 }
		 return zj;
	 };


	public double[] calCjZj() {
		double[] cjZj = new double[this.coefCont[0].length];
		 for (int i = 0; i < coefEco.length; i++) {
			 cjZj[i] = this.coefEco[i] - this.calZj()[i];
		 }
		 return cjZj;
	};
	
	
	//get the value and the position of ve
	public double calVe() {
		double ve = coefEco[0];
		for(int coefEcoIndex = 0; coefEcoIndex < this.coefEco.length; coefEcoIndex++) {
			if(coefEco[coefEcoIndex] > ve) {
				ve = coefEco[coefEcoIndex];
			}
		}
		return ve;
	}
	
	public int calVePosition() {	
		
		// get the position of ve
		int vePosition = 0;
		for (int i=0; i < this.coefEco.length; i++) {
			if(this.calVe() ==  this.coefEco[i]) {
				vePosition = i;
			}
		}
		return vePosition;
	}
	
	
	// get the value and position of vs
	public double[] calRatio() {
		
		// get the ratio
		double [] ratio = new double[this.cp.length];		
		for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
			ratio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.calVePosition()]; //get ratio table
		}
		return ratio;
	}
	
	public double calVs() {
		double[] ratios = this.calRatio();
		double vs = ratios[0];
		
		//get the min of ratio = vs
		for(int ratioIndex = 0; ratioIndex < ratios.length; ratioIndex++) {
			if(vs > ratios[ratioIndex] ) {
				vs = ratios[ratioIndex];
			}
		}
		return vs;
	}
		
	
	public int calVsPosition() {
		//get position of vs
		int vsPosition = 0;
		for (int i = 0; i < this.qy.length; i++) {
			if(this.calVs() == this.calRatio()[i]) {
				vsPosition = i;
			}
		};
		return vsPosition;
	}
	

	public double calPivot() {
		
		// the pivot is the intersection of column ve and row vs
		double pivot;
		pivot = this.coefCont[this.calVsPosition()][this.calVePosition()];
		return pivot;
	}
	
	
	public double[] calPivotRow() {
		double pivot = this.calPivot();
		int vsPosition = this.calVsPosition();
		
		double[] pivotRow = new double[this.coefCont[0].length];
		for (int i = 0; i < this.coefCont[0].length; i++) {
			pivotRow[i] = this.coefCont[vsPosition][i] / pivot;
		}
		return pivotRow;		
	}
	
	
	public double[] calPivotQy() {
		int vsPosition = this.calVsPosition();
		this.qy[vsPosition] = this.qy[vsPosition] / this.calPivot();
		return this.qy;
	}
	
	
	public void iteration() {
		
		while (!this.stopIteration()) {
			double[] pivotRow = this.calPivotRow();
			int vePosition = this.calVePosition();
			int vsPosition = this.calVsPosition();
			double[] qy = this.calPivotQy();
			
			double pTest = pivotRow[vePosition]; // get reference point of constraint of ve of Table-1 to calculate the iterated table
			double coefIteration;
			
			// loop to create the new table of constraint with new quantity
			for (int row=0; row < this.coefCont.length; row++) {
				
				//let all the coef of constraint of ve of Table-1 = 0
				// this.coefcont[row][vePosition] - coefInteration * pTest = 0
				coefIteration = this.coefCont[row][vePosition] /pTest;
				
				//insert the new constraint rows except the pivot row
				if (!(this.coefCont[row] == this.coefCont[vsPosition])) {					
					for (int col = 0; col< this.coefCont[0].length; col++) {
						this.coefCont[row][col] = this.coefCont[row][col] - (coefIteration * pivotRow[col]);	
					}					
					qy[row] = qy[row] - (coefIteration * qy[vsPosition]);  // insert the new quanty except the pivot row
					
					
				// insert the new constraint row of the pivot row
				} else if (this.coefCont[row] == this.coefCont[vsPosition]){
						this.coefCont[row] = pivotRow  ; // replace the old vs coef by ve in table-1					
						qy[row] = qy[vsPosition];  //insert the new quantity of th pivot row
				}
			}			
			
			// get the new cp		
						
			this.cp[vsPosition] = this.coefEco[vePosition]; // replace old vs coef by ve in table-1 
			//this.calCp(); //call calculate cp method to update the values
			
			// ???????????????????????should recalculate the value of zj, cjZj based on the first iteration??????????????????????????????
			this.calZj();  // recalculate the zj;
			this.calCjZj();  // recalculate the cjzj;
			
			
		}
	}
		
	
	public boolean stopIteration () {
		boolean iteStop = false;
//		for(int i=0; i<this.coefEco.length; i++) {
//			if(this.coefEco[i] <=0) {
//				iteStop = true;
//			}				
//		}
		return iteStop;
//		
	}
	
	
}




