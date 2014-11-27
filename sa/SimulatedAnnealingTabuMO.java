package sa;

import java.util.ArrayList;
import java.util.Random;

import solution.Solution;

public class SimulatedAnnealingTabuMO extends SimulatedAnnealingMO {

	protected TabuList	tabuList;
	protected AspirationCriterion criterion;
	
	public SimulatedAnnealingTabuMO(Solution start) {
		this(start,100,new GeometricCooling(),new DefaultMOAcceptance(), 10, new DefaultMOAspiration());
		
	}
	
	public SimulatedAnnealingTabuMO(Solution start, double initialTemperature, CoolingProcedure cp, AcceptancePolicy ap, int TLlength, AspirationCriterion ac)
	{
		super(start, initialTemperature,cp,ap);	
		criterion=ac;
		tabuList=new TabuList(TLlength);
	}
	
	@Override
	public ArrayList<Solution> execute() {
		
		iteration=0;
		while(!cooling.isEnd(this)){
			neighbour=actual.nextNeighbour();
			if(tabuList.contains(neighbour))
			{
				Random r=new Random();
				double prob=r.nextDouble();
				if(!(prob<criterion.aspirationCriterion(this)))
					continue;
			}
			boolean comparison=compareToParetoFront(neighbour);
			if(!comparison){
				Random r=new Random();
				double prob=r.nextDouble();
				if(prob<policy.acceptanceProbabilty(this)){
					actual=neighbour;
				}
			}
			iteration++;
			temperature=cooling.coolDown(this);
			}
		return paretoFront;
	}

}
