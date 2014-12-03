package test;

import java.util.ArrayList;

import acceptance.AcceptancePolicy;
import acceptance.DefaultSOAcceptance;

import cooling.CoolingProcedure;
import cooling.GeometricCooling;

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
		TestSASO test=new TestSASO(new Path(), 50,new GeometricCooling(.8),new DefaultSOAcceptance());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
