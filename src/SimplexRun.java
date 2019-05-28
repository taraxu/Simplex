
public class SimplexRun {

	public static void main(String[] args) {

		
		Simplex sp1 = new Simplex();
		sp1.calVe();
		sp1.calVs();
		sp1.calZj();
		sp1.calCjZj();
		sp1.calPivot();
		sp1.calPivotRow();
		sp1.iteration();
	}

}
