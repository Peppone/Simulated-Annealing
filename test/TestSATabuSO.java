package test;

import java.util.ArrayList;

import sa.AcceptancePolicy;
import sa.AspirationCriterion;
import sa.CoolingProcedure;
import sa.DefaultSOAcceptance;
import sa.DefaultSOAspiration;
import sa.ExponentialCooling;
import sa.SimulatedAnnealingTabuSO;
import solution.Solution;

public class TestSATabuSO extends SimulatedAnnealingTabuSO
{
	public TestSATabuSO(Path start, double initialTemperature,
			CoolingProcedure cp, AcceptancePolicy ap, int length, AspirationCriterion ac) {
		super(start, initialTemperature, cp, ap, length, ac);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) 
	{
		ArrayList<Solution> solutions;
		TestSATabuSO test=new TestSATabuSO(new Path(), 50,new ExponentialCooling(.8),new DefaultSOAcceptance(), 5, new DefaultSOAspiration());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
