
public class Simplex {
	double [] coefEco;
	double [][] coefCont;
	double [][] coefCont2;
	double [] cp;
	double [] qy;
	double [] zj;
	double [] cjZj;
	double [] xj;
	
	Simplex () {
		this.coefEco = new double[] {6, 7, 8, 0, 0, 0};
		this.coefCont = new double[][] {
				{1, 2, 1, 1, 0, 0},
				{3, 4, 2, 0, 1, 0},
				{2, 6, 4, 0, 0, 1}
		};
		this.coefCont2 = new double [][] {};
		this.cp = new double[] {0, 0, 0};
		this.qy = new double[] {100, 120, 200};
		this.xj = new double[3];
		this.zj = new double[6];
		
	};
	
	 public void calDiffNbvNbcont() {
		 int diffNbvNbcont = this.coefEco.length - this.coefEco.length;
		 if(diffNbvNbcont >0) {
			// ?????????????????????????? 
		 }
	 };
	 
	 
	 public void calZj() {
		//System.out.println(this.coefCont.length);		
			 
//			 for (int indexXj = 0; indexXj < this.xj.length; indexXj++) {
//				 for (int row = 0; row < this.coefCont[0].length; row++) {
//					 for (int col = 0; col < this.coefCont.length; col++) {
//						 double cofRow = this.coefCont[col][row];					
//							 this.xj[indexXj] = cofRow;
//							 for (int indexCp = 0; indexCp < this.cp.length; indexCp++) {
//								 for (int indexZj = 0; indexZj < this.zj.length; indexZj++) {
//									 this.zj[indexZj] = this.xj[indexXj] * this.cp[indexCp];
//									 System.out.print(this.zj[indexZj]);
//								 }
//							 }
//							
//					 }
//					 
//				 }
//				 
//			 }	
		 
		 for (int row = 0; row < this.coefCont[0].length; row++) {
			 for (int col = 0; col < this.coefCont.length; col++) {
				 this.coefCont2[col][row] = this.coefCont[row][col];
			 }
			 for (int indexCp = 0; indexCp < this.cp.length; indexCp++) {
				 for (int indexZj = 0; indexZj < this.zj.length; indexZj++) {
//					 this.zj[indexZj] = this.coefCont2 * this.cp[indexCp];
//					 System.out.print(this.zj[indexZj]);
				 }
			 }
		 }
		 

	 };


public void calCjZj() {
	
};

}
