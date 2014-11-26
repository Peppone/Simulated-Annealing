package sa;

import java.util.ArrayList;
import java.util.Random;

import solution.Solution;

public class SimulatedAnnealingSO extends SimulatedAnnealing {

	protected Solution best;
	protected Solution actual;
	protected Solution neighbour;
	
	public SimulatedAnnealingSO(Solution start){
		this(start,100,new ExponentialCooling(),new DefaultSOAcceptance());
		
	}
	@Override
	public Solution getActualSolution(){
		return actual;
	}
	@Override
	public Solution getNeighbour(){
		return neighbour;
	}
	
	public SimulatedAnnealingSO(Solution start, double initialTemperature,CoolingProcedure cp, AcceptancePolicy ap){
		best=start;
		actual=best;
		temperature=initialTemperature;
		if(initialTemperature<=0){
			System.err.println("Temperature must be greater than 0. Setting default value");
			temperature=100;
		}
		cooling=cp;
		policy=ap;
	}
	@Override
	public ArrayList<Solution> execute() {
		
		iteration=0;
		while(!cooling.isEnd(this)){
			neighbour=actual.nextNeighbour();
			double res = (Double)neighbour.fitness()[0]-(Double)actual.fitness()[0];
			if(res<=0){
				actual=neighbour;
				if(res<0){
				double bestRes=	(Double)actual.fitness()[0]-(Double)best.fitness()[0];
				if(bestRes<0)
				best=actual;				
				}
			}
			else{
				Random r=new Random();
				double prob=r.nextDouble();
				if(prob<policy.acceptanceProbabilty(this)){
					actual=neighbour;
				}
			}
			iteration++;
			temperature=cooling.coolDown(this);
			}
		ArrayList<Solution> solution = new ArrayList<Solution>();
		solution.add(best);
		return solution;
	}


}
