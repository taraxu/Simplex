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
		
	}
	
	
	/**
	 * interface to select the optimizer way, maximum by default
	 * @return maximum or minimum the solution
	 */
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
	 }
	 
	 
	 
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
	 }
		 
	
	
		public double[] calCjZj() {
			double[] cjZj = new double[this.coefCont[0].length];
			 for (int i = 0; i < coefEco.length; i++) {
				 cjZj[i] = this.coefEco[i] - this.calZj()[i];
			 }
			 return cjZj;
		};
		
		
		public void calZ() {
			double[] eachRowRes = new double[this.qy.length];
			 z = 0;		
			 for (int i = 0; i < this.qy.length; i++) {
				 eachRowRes[i] = this.cp[i] * this.qy[i];
				 this.z += eachRowRes[i];
			 }
				System.out.println(this.z); 
			 
		}
		
		/**
		 * Initialize the ve position
		 * @return
		 */
		public int initVePosition () {
			double ve = coefEco[0];
			int initVePosition = 0;
			
			for(int coefEcoIndex = 0; coefEcoIndex < this.coefEco.length; coefEcoIndex++) {
				if(coefEco[coefEcoIndex] > ve) {
					ve = coefEco[coefEcoIndex];
				}
			}
			
			for (int i=0; i < this.coefEco.length; i++) {
				if(ve ==  this.coefEco[i]) {
					initVePosition = i;
				}
			}
			return initVePosition;
		}
		
		/**
		 * initialize the vs position
		 * @return
		 */
		public int initVsPosition() {

			double[] initRatio = new double[this.cp.length];
			double vs = initRatio[0];
			int vsPosition = 0;
	
			// get ratio table
			for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
				initRatio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.initVePosition()]; 
			}
			
			// get vs
			for(int ratioIndex = 0; ratioIndex < initRatio.length; ratioIndex++) {
				if(vs > initRatio[ratioIndex] ) {
					vs = initRatio[ratioIndex];
				}
			}
			
			// get vs position
			for (int i = 0; i < this.qy.length; i++) {
				if(vs == initRatio[i]) {
					vsPosition = i;
				}
			};
			return vsPosition;
		}
		
		
		
		
		/**
		 * get the ve position for iteration
		 * @return ve position of new constraint table
		 */
		public int calVePosition() {
			double[] zj = this.calZj();
			double ve = zj[0];
			int vePosition = 0;
			
			for(int i = 0; i < zj.length; i++) {
				if(zj[i] > ve) {
					ve = zj[i];
				}
			}			
			for (int i=0; i < zj.length; i++) {
				if(ve == zj[i]) {
					vePosition = i;
				}
			}
			return vePosition;
		}		
		
		
		/**
		 *  get the vs position for iteration
		 * @return vs position of new constraint table
		 */
		public int calVsPosition() {
			
			double[] ratio = new double[this.cp.length];
			double vs = ratio[0];
			int vsPosition = 0;
			
			// get ratio table
			for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
				ratio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.calVePosition()];
			}
			
			// get vs value
			for(int ratioIndex = 0; ratioIndex < ratio.length; ratioIndex++) {
				if(vs > ratio[ratioIndex] ) {
					vs = ratio[ratioIndex];
				}
			}
			
			// get vs position
			for (int i = 0; i < this.qy.length; i++) {
				if(vs == ratio[i]) {
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
		
		
		public void iteration(int nbIteration) {
			
			double[] pivotRow = this.calPivotRow();
			int vePosition = 0;
			int vsPosition = 0;
			if (nbIteration == 1) {
				vePosition = this.initVePosition();
				vsPosition = this.initVsPosition();
			} else if (nbIteration > 1) {
				vePosition = this.calVePosition();
				vsPosition = this.calVsPosition();
			}
			double[] qy = this.calPivotQy();			
			double pTest = pivotRow[vePosition]; // get reference point of constraint of ve of Table-1 to calculate the iterated table
			double coefIteration;
			

			
			// loop to create the new table of constraint with new value
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
			
			// get new zj and cj-zj
			this.calZj();  // recalculate the zj;
			this.calCjZj();  // recalculate the cjzj;
			this.calZ();
			this.calVePosition();
			this.calVsPosition();
		}
		
		public void initTab() {
			this.calCp();
			this.calZj();
			this.calCjZj();
			this.calZ();
			this.calVePosition();
			this.calVsPosition();
			this.calPivot();
			this.calPivotRow();
		}
			
		
		public void stopIteration () {
			
			double[] cjZj = this.calCjZj();
			
			int nbIteration = 1;
			
			for(int i =0; i < cjZj.length; i++) {
				if (cjZj[i] > 0) {
					this.iteration(nbIteration);
					nbIteration ++;
					return;
				}				
			}
		}
	
	
}




