package test;

import java.util.ArrayList;

import sa.SimulatedAnnealingMO;
import solution.Solution;
import acceptance.AcceptancePolicy;
import acceptance.DefaultMOAcceptance;
import cooling.CoolingProcedure;
import cooling.DecrementalCooling;

public class TestSAMO extends SimulatedAnnealingMO
{
	public TestSAMO(Path start, double initialTemperature,
			CoolingProcedure cp, AcceptancePolicy ap) {
		super(start, initialTemperature, cp, ap);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) 
	{
		ArrayList<Solution> solutions;
		TestSAMO test=new TestSAMO(new Path(), 100,new DecrementalCooling(),new DefaultMOAcceptance());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
