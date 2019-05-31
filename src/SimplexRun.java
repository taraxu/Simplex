
public class SimplexRun {

	public static void main(String[] args) {
		
		Simplex sp = new Simplex();
//		sp.operation();
		sp.initTable(sp.coefEco, sp.coefCont, sp.qy);
		sp.stopIteration();

	}

}
