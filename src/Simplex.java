import java.util.Scanner;

public class Simplex {
	double [] coefEco;
	double[][] coefCont;
	double [] qy;
	double[] cp;
	double z;
	
	Simplex () {
		
		this.coefEco = new double[] {5, 4, 6, 0, 0, 0, 0};
		this.coefCont = new double[][] {
				{1, 1, 1, 0, 0, 0, 0},
				{1, 0, 0, -1, 0, 0, 0},
				{0, 1, 0, 0, 1, 0, 0},
				{0, 0, 1, 0, 0, -1, 0},
				{0, 0, 1, 0, 0, 0, 1}
		};
		this.qy = new double[] {225, 45, 55, 70, 100};
		
//		this.coefEco = new double[] {5, 6, 0, 0};
//		this.coefCont = new double[][] {
//				{1, 2, -1, 0, 1},
//				{1, 1, 0, -1, 0}
//		};
//		this.qy = new double[] {12, 8};
		
		
//		this.coefEco = new double[] {3, 2, 0, 0, 0};
//		this.coefCont = new double[][] {
//				{2, 1, 1, 0, 0},
//				{2, 3, 0, 1, 0},
//				{3, 1, 0, 0, 1}
//		};
//		this.qy = new double[] {18, 42, 24};
		
		
//		this.coefEco = new double[] {100, 120};
//		this.coefCont = new double[][] {
//				{3, 4},
//				{1, 3},
//				{2, 2}
//		};
//		this.qy = new double[] {4200, 2400, 2600};		
		
		
//		this.coefEco = new double[] {6, 7, 8, 0, 0, 0};
//		this.coefCont = new double[][] {
//				{1, 2, 1, 1, 0, 0},
//				{3, 4, 2, 0, 1, 0},
//				{2, 6, 4, 0, 0, 1}
//		};
//		this.qy = new double[] {100, 120, 200};
		
		this.z = 0;	
	}
	
	
	
	/**
	 * interface to select the optimizer way, maximum by default
	 * @return maximum or minimum the solution
	 */
	 
	public String operation() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir 'max' pour maximier le résultat ou 'min' pour minimiser le résultat ");
		String choice = sc.nextLine();
		if(choice.equals("max")) {			
			return "max";
		} else {
			return "min";
		}
	}	
	
	
	public String[] typeCont() {
		
		String[] typeCont = {};
		Scanner input = new Scanner(System.in);

		System.out.println("Saisir '>=' ou '<=' pour le type de contraint ");
		for (int i = 0; i < this.coefCont.length; i++) {
			typeCont[i] = input.nextLine();
			System.out.println("Saisir '>=' ou '<=' pour le type de contraint ");
			
		}		
		return typeCont;
	}
	
	
	
	/**
	 * Add slack and surplus variables
	 * @return
	 */
	public double[] addV() {
		String typeCont[] = this.typeCont();
		int contCol = this.coefCont[0].length;
		double[] vSlackOrSurplus = {};
		
		for (int i=0; i<= contCol; i++) {
			if(typeCont.equals("<=")) {
				vSlackOrSurplus[i] = this.coefCont[contCol][i+1] = 1;
			} else if(typeCont.equals(">=")) {
				vSlackOrSurplus[i]= this.coefCont[contCol][i+1] = -1;
			}
		}
		return vSlackOrSurplus;
	}
	
	
	
	 /**
	  * Calculate the quantity
	  * @return
	  */
	public double[] calQy() {
		
		int diffNbvNbcont = this.coefEco.length - this.coefCont.length; //nb of non-basic variables
		double[] vSlackOrSurplus = this.addV();
		 int coefEcoActLength = 1;
		 if(diffNbvNbcont > 0) {
			 // Set nb of variables - nb of constrains equal to zero (NBV = 0) and set all non-basic variables to 0
				for (int i=0; i < this.coefCont.length; i++) {
					this.qy[i] = this.qy[i] * vSlackOrSurplus[i];
				}
		 } else {
			 throw new Exception();
		 }
		return this.qy;
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
				System.out.println("Le résultat est " + this.z); 
			 
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
	 
	 
	 
	
	/**
	 * initialize the table
	 * 
	 */
	public void initTable () {
		this.calQy();
		this.calCp();
		this.calZj();
		this.calCjZj();
	}	
	
	
	/**
	 * check if it's the optimal solution
	 */
	public void stopIteration () {
		
		double[] cjZj = this.calCjZj();
		String operation = this.operation();
		boolean isOver = false; 
		
		if(operation.equals("max")) {
	
			for(int i =0; i < cjZj.length; i++) {
				if (cjZj[i] > 0) {
					this.iteration();
				} else if (!isOver) {
					isOver = true;
					this.calZ();
					break;
				}
			}
		} else if(operation.equals("min")) {
			for(int i =0; i < cjZj.length; i++) {
				if (cjZj[i] < 0) {
					this.iteration();
				} else if (!isOver){
					isOver = true;
					this.calZ();
					break;
				}
			}
		}
				
	}
	
	
	
	/**
	 * choice of entering variable ve
	 * @return ve index
	 */
	public int calVePosition() {
		double[] cjZj = this.calCjZj();
		double ve = cjZj[0];
		int vePosition = 0;
		
		for(int i = 0; i < cjZj.length; i++) {
			if(cjZj[i] > ve) {
				ve = cjZj[i];
				if(ve == coefEco[i]) {
					vePosition = i;
				}
			}
		}
		return vePosition;
	}
	
	
	
	/**
	 * choice of leaving variable vs
	 * @return vs index
	 */
	public int calVsPosition() {
		
		double[] ratio = new double[this.cp.length];
		
		
		// get ratio table
		for (int qyIndex = 0; qyIndex < this.qy.length; qyIndex++) {		
			ratio[qyIndex] = this.qy[qyIndex] / this.coefCont[qyIndex][this.calVePosition()];
		}
		
		// get vs value
		double vs = ratio[0];
		for(int i = 0; i < ratio.length; i++) {
			if(vs > ratio[i] ) {
				vs = ratio[i];
			}
		}
		
		// get vs index
		int vsPosition = 0;
		for (int i = 0; i < this.qy.length; i++) {
			if(vs == ratio[i]) {
				vsPosition = i;
			}
		};
		return vsPosition;
	}
	
	
	
	/**
	 * find out pivot
	 */
	
	public double calPivot() {
		
		// the pivot is the intersection of column ve and row vs
		double pivot;
		pivot = this.coefCont[this.calVsPosition()][this.calVePosition()];
		return pivot;
	}
	
	
	/**
	 * find out pivot row
	 * @return table of pivot row
	 */
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
	
	
	
	
	/**
	 * create the new table
	 */
	public void iteration() {
		
		double[] pivotRow = this.calPivotRow();
		int vePosition = this.calVePosition();
		int vsPosition = this.calVsPosition();
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
					
					// get the new cp					
					this.cp[row] = this.coefEco[vePosition]; // replace old vs coef by ve in table-1 
					//this.calCp(); //call calculate cp method to update the values
			}
		}			
		
		
		
		// get new zj and cj-zj
		this.calZj();  // recalculate the zj;
		this.calCjZj();  // recalculate the cjzj;
		this.calVePosition();
		this.calVsPosition();
	}
	
	
	
		
			
		

	
	
}




