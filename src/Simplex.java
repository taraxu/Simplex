import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Simplex {
	double [] coefEco;
	double [][] coefCont;
	double [] qy;
	double z;
	
	Simplex () {
		this.coefEco = new double[] {6, 7, 8, 0, 0, 0};
		this.coefCont = new double[][] {
				{1, 2, 1, 1, 0, 0},
				{3, 4, 2, 0, 1, 0},
				{2, 6, 4, 0, 0, 1}
		};
		this.qy = new double[] {100, 120, 200};
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
	 
	 public double[] getCp() {
		double[] cp = new double[this.coefCont.length];
		int deltaLength = this.coefEco.length - cp.length;
		for (int indexCP = 0; indexCP<cp.length; indexCP++) {
			 cp[indexCP] = this.coefEco[indexCP+deltaLength];
		}		
		 return cp;
	 }
	 
	 
	 public double[] initZj() { 
		 double[] zj = new double[this.coefCont[0].length];
		 for (int row = 0; row < this.coefCont.length; row++) {
			 for (int col = 0; col < this.coefCont[row].length; col++) {
				 zj[row] += this.getCp()[row] * this.coefCont[row][col];
			 }			 
		 }
		 return zj;
	 };


	public double[] initCjZj() {
		double[] cjZj = new double[this.coefCont[0].length];
		 for (int i = 0; i < coefEco.length; i++) {
			 cjZj[i] = this.coefEco[i] - this.initZj()[i];
		 }
		 return cjZj;
	};
	
	
	//get the value and the position of ve
	public double getVe() {
		double ve = 0;
		for(int coefEcoIndex = 0; coefEcoIndex < this.coefEco.length; coefEcoIndex++) {
			if(coefEco[coefEcoIndex] > ve) {
				ve = coefEco[coefEcoIndex];
			}
		}
		return ve;
	}
	
	public int getVePosition() {	
		
		// get the position of ve
		int vePosition = 0;
		for (int i=0; i < this.coefEco.length; i++) {
			if(this.getVe() ==  this.coefEco[i]) {
				vePosition = i;
			}
		}
		return vePosition;
	}
	
	
	// get the value and position of vs
	public double[] getRatio() {
		
		// get the ratio
		double [] ratio = new double[this.getCp().length];		
		for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
			ratio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.getVePosition()]; //get ratio table
		}
		return ratio;
	}
	
	public double getVs() {
		double vs = this.getRatio()[0];
		
		//get the min of ratio = vs
		for(int ratioIndex = 0; ratioIndex < this.getRatio().length; ratioIndex++) {
			if(vs > this.getRatio()[ratioIndex] ) {
				vs = this.getRatio()[ratioIndex];
			}
		}
		return vs;
	}
		
	
	public int getVsPosition() {
		//get position of vs
		int vsPosition = 0;
		for (int i = 0; i < this.qy.length; i++) {
			if(this.getVs() == this.getRatio()[i]) {
				vsPosition = i;
			}
		};
		return vsPosition;
	}
	

	public double getPivot() {
		
		// the pivot is the intersection of column ve and row vs
		double pivot;
		pivot = this.coefCont[this.getVsPosition()][this.getVsPosition()];
		return pivot;
	}
	
	
	public double[] getPivotRow() {
		this.qy[this.getVsPosition()] = this.qy[this.getVsPosition()] / this.getPivot();
		double[] pivotRow = new double[this.coefEco.length];
		for (int i = 0; i < this.coefEco.length; i++) {
			pivotRow[i] = this.coefCont[this.getVsPosition()][i] / this.getPivot();
		}
		//System.out.println(pivotRow[5]);
		return pivotRow;
		
	}
	
	
	public void iteration() {
		
		if(!this.stopIteration()) {
			
			double pTest = this.getPivotRow()[this.getVePosition()]; // get reference point of constraint of ve of Table-1
			double coefIteration;
			for (int row=0; row < this.coefCont.length; row++) {
				
				//let all the coef of constraint of ve of Table-1 = 0
				coefIteration = this.coefCont[row][this.getVePosition()] /pTest;
				
				//creat the limit of looping of constraint lines for insert the new values
				if (!(this.coefCont[row] == this.coefCont[this.getVsPosition()])) {
					
					for (int col = 0; col< this.coefCont[0].length; col++) {
						
						// affect the new value for all the constraint coef except the vsPosition line
						this.coefCont[row][col] = this.coefCont[row][col] - (coefIteration * this.getPivotRow()[col]); 					
					}	
				}						
			}
			
			for (int col = 0; col< this.coefCont[0].length; col++) {
				this.coefCont[this.getVePosition()][col] = this.getPivotRow()[col]  ; // replace the old vs coef by ve in table-1
				//System.out.println(this.coefCont[this.getVePosition()][col]);
			}
			
			
			this.getCp()[this.getVsPosition()] = this.coefEco[this.getVePosition()]; // replace old vs row by ve in table-1
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




