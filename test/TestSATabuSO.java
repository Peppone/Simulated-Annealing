package test;

import java.util.ArrayList;

import solution.Solution;
import taboo.SimulatedAnnealingTabuSO;
import acceptance.AcceptancePolicy;
import acceptance.DefaultSOAcceptance;
import aspiration.AspirationCriterion;
import aspiration.DefaultSOAspiration;
import cooling.CoolingProcedure;
import cooling.GeometricCooling;

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
		TestSATabuSO test=new TestSATabuSO(new Path(), 50,new GeometricCooling(.8),new DefaultSOAcceptance(), 5, new DefaultSOAspiration());
		solutions=test.execute();
		System.out.println("Solutions ("+solutions.size()+"): ");
		for(Solution p:solutions)
			System.out.println((Path)p);
	}
}
