package test;

import java.util.ArrayList;

import solution.Solution;
import taboo.SimulatedAnnealingTabuMO;
import acceptance.AcceptancePolicy;
import acceptance.DefaultMOAcceptance;
import aspiration.AspirationCriterion;
import aspiration.DefaultMOAspiration;
import cooling.CoolingProcedure;
import cooling.DecrementalCooling;

public class TestSATabuMO extends SimulatedAnnealingTabuMO
{
	public TestSATabuMO(Path start, double initialTemperature,
			CoolingProcedure cp, AcceptancePolicy ap, int TLLength, AspirationCriterion ac) {
		super(start, initialTemperature, cp, ap, TLLength, ac);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) 
	{
		ArrayList<Solution> solutions;
		TestSATabuMO test=new TestSATabuMO(new Path(), 100,new DecrementalCooling(),new DefaultMOAcceptance(), 10, new DefaultMOAspiration());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
