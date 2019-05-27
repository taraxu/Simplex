import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Simplex {
	double [] coefEco;
	double [][] coefCont;
	double [] cp;
	double [] qy;
	double [] zj;
	double [] cjZj;
	double ve;
	double vs;
	double [] xj;
	int vePosition;
	int vsPosition;
	double pivot;
	double z;
	
	Simplex (int nbVariable, int nbContraint) {
		this.coefEco = new double[] {6, 7, 8, 0, 0, 0};
		this.coefCont = new double[][] {
				{1, 2, 1, 1, 0, 0},
				{3, 4, 2, 0, 1, 0},
				{2, 6, 4, 0, 0, 1}
		};
		this.cp = new double[this.coefCont.length];
		this.qy = new double[] {100, 120, 200};
		this.xj = new double[nbContraint];
		this.zj = new double[nbVariable];
		this.cjZj = new double[nbVariable];
		this.ve = this.coefEco[0];
		this.vs = 0;
		this.vePosition = 0;
		this.vsPosition = 0;
		this.pivot = 0;
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
	 
	 public double[] ititCp() {
		int deltaLength = this.coefEco.length - this.cp.length;
		for (int indexCP = 0; indexCP<this.cp.length; indexCP++) {
			 this.cp[indexCP] = this.coefEco[indexCP+deltaLength];
		}		
		 return this.cp;
	 }
	 
	 
	 public void initZj() { 
		 for (int row = 0; row < this.coefCont.length; row++) {
			 for (int col = 0; col < this.coefCont[row].length; col++) {
				 this.zj[row] += this.cp[row] * this.coefCont[row][col];
			 }			 
		 }
	 };


	public void initCjZj() {
		 for (int i = 0; i < coefEco.length; i++) {
			 this.cjZj[i] = this.coefEco[i] - this.zj[i];
		 }
	};
	
	
	//get the value and the position of ve
	public void getVe() {
		for(int coefEcoIndex = 0; coefEcoIndex < this.coefEco.length; coefEcoIndex++) {
			if(coefEco[coefEcoIndex] > this.ve) {
				this.ve = coefEco[coefEcoIndex];
			}
		}
		System.out.println(this.ve);
		
		// get the position of ve	
		for (int i=0; i < this.coefEco.length; i++) {
			if(this.ve ==  this.coefEco[i]) {
				this.vePosition = i;
			}
		};
	}
	
	
	// get the value and position of vs
	public void getVs() {
		
		// get the ratio
		double [] ratio = new double[3];		
		for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
			ratio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.vePosition]; //get ratio table
		}
		this.vs = ratio[0];
		
		//get the min of ratio = vs
		for(int ratioIndex = 0; ratioIndex < ratio.length; ratioIndex++) {
			if(this.vs > ratio[ratioIndex] ) {
				this.vs = ratio[ratioIndex];
			}
		}
		
		//get position of vs
		for (int i = 0; i < this.qy.length; i++) {
			if(this.vs == ratio[i]) {
				this.vsPosition = i;
			}
		};
		
	}

	public void getPivot() {
		
		// the pivot is the intersection of column ve and row vs
		this.pivot = this.coefCont[this.vsPosition][this.vePosition];
	}
	
	
	public double[] getPivotRow() {
		this.qy[this.vsPosition] = this.qy[this.vsPosition] / this.pivot;
		double[] pivotRow = new double[this.coefEco.length];
		for (int i = 0; i < this.coefEco.length; i++) {
			pivotRow[i] = this.coefCont[this.vsPosition][i] / this.pivot;
		}
		return pivotRow;
	}
	
	
	public void iteration() {
		
		if(!this.stopIteration()) {
			
			double pTest = this.getPivotRow()[this.vePosition]; // get reference point of constraint of ve of Table-1
			double coefIteration;
			for (int row=0; row < this.coefCont.length; row++) {
				
				//let all the coef of constraint of ve of Table-1 = 0
				coefIteration = this.coefCont[row][this.vePosition] /pTest;			
					
				for (int col = 0; col< this.coefCont[0].length; col++) {
					
					// affect the new value for all the constraint coef
					this.coefCont[row][col] = this.coefCont[row][col] - (coefIteration * this.getPivotRow()[col]); 					
				}				
			}
			for (int col = 0; col< this.coefCont[0].length; col++) {
				this.getPivotRow()[col] = this.coefCont[this.vsPosition][col] ; // replace the old vs coef by ve in table-1
			}
			
			
			this.cp[this.vsPosition] = this.coefEco[this.vePosition]; // replace old vs row by ve in table-1
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




