package sa;

import java.util.ArrayList;

import solution.Solution;
import acceptance.AcceptancePolicy;
import cooling.CoolingProcedure;

public abstract class SimulatedAnnealing {
	protected double temperature;
	protected double minTemperature=0;
	protected int iteration=0;
	protected CoolingProcedure cooling;
	protected AcceptancePolicy policy;
	public abstract Solution getNeighbour();
	public abstract Solution getActualSolution();
	public abstract ArrayList<Solution> execute();
	public void setTemperature(double temperature){this.temperature=temperature;};
	public double getTemperature(){return temperature;}
	public int getIterationNumber(){return iteration;}
}
