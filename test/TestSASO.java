package test;

import java.util.ArrayList;

import sa.AcceptancePolicy;
import sa.CoolingProcedure;
import sa.DefaultSOAcceptance;
import sa.ExponentialCooling;
import sa.SimulatedAnnealingSO;
import solution.Solution;

public class TestSASO extends SimulatedAnnealingSO
{
	public TestSASO(Path start, double initialTemperature,
			CoolingProcedure cp, AcceptancePolicy ap) {
		super(start, initialTemperature, cp, ap);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) 
	{
		ArrayList<Solution> solutions;
		TestSASO test=new TestSASO(new Path(), 50,new ExponentialCooling(.8),new DefaultSOAcceptance());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
