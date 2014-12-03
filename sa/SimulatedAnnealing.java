package sa;

import java.util.ArrayList;

import acceptance.AcceptancePolicy;

import cooling.CoolingProcedure;

import solution.Solution;

public abstract class SimulatedAnnealing {
	protected double temperature;
	protected double minTemperature=0;
	protected int iteration=0;
	protected int skippedIteration=0;
	protected CoolingProcedure cooling;
	protected AcceptancePolicy policy;
	public abstract Solution getNeighbour();
	public abstract Solution getActualSolution();
	public abstract ArrayList<Solution> execute();
	public SimulatedAnnealing setTemperature(double temperature){this.temperature=temperature; return this;};
	public SimulatedAnnealing incrementSkippedIterations(){skippedIteration++; return this;}
	public SimulatedAnnealing resetSkippedIterations(){skippedIteration=0; return this;}
	public double getTemperature(){return temperature;}
	public int getIterationNumber(){return iteration;}
	public int getSkippedIterationNumber(){return skippedIteration;};
}
