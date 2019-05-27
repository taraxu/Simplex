
public class SimplexRun {

	public static void main(String[] args) {

		
		Simplex sp1 = new Simplex();
		sp1.initZj();
		sp1.initCjZj();
		sp1.getVe();
		sp1.getVs();
		sp1.getPivot();
		sp1.getPivotRow();
		sp1.iteration();
	}

}
