import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simplex {
	double [] coefEco;
	double [][] coefCont;
	double [][] coefCont2;
	double [] cp;
	double [] qy;
	double [] zj;
	double [] cjZj;
	double ve;
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
		
		this.ve = ve;
	
		
	};
	
	 public void calDiffNbvNbcont() {
		 int diffNbvNbcont = this.coefEco.length - this.coefEco.length;
		 if(diffNbvNbcont >0) {
			// ?????????????????????????? 
		 }
	 };
	 
	 
	 public void calZj() { 
		 for (int col = 0; col < this.coefCont[col].length; col++) {
			 
				 this.xj[col] = this.coefCont[col].length * this.cp[col];
				 this.zj[col] = this.xj[col+1];
			
		 }		 

	 };


	public void calCjZj() {
		
	};
	
//	public void calVe() {
//		List b = Arrays.asList(Arrays.toObject(this.coefCont));
//		this.ve = (double) Collections.max(b);
//	}

}
