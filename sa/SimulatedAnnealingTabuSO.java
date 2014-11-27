package sa;

import java.util.ArrayList;
import java.util.Random;

import solution.Solution;

public class SimulatedAnnealingTabuSO extends SimulatedAnnealingSO 
{
	protected TabuList	tabuList;
	protected AspirationCriterion criterion;
	
	public SimulatedAnnealingTabuSO(Solution start)
	{
		this(start,100,new GeometricCooling(),new DefaultSOAcceptance(), 10, new DefaultSOAspiration());
	}
	
	@Override
	public Solution getActualSolution()
	{
		return actual;
	}

	@Override
	public Solution getNeighbour()
	{
		return neighbour;
	}
	
	public SimulatedAnnealingTabuSO(Solution start, double initialTemperature, CoolingProcedure cp, AcceptancePolicy ap, int TLlength, AspirationCriterion ac)
	{
		super(start, initialTemperature,cp,ap);	
		criterion=ac;
		tabuList=new TabuList(TLlength);
	}
	
	@Override
	public ArrayList<Solution> execute() 
	{
		iteration=0;
		while(!cooling.isEnd(this))
		{
			neighbour=actual.nextNeighbour();
			
			if(tabuList.contains(neighbour))
			{
				Random r=new Random();
				double prob=r.nextDouble();
				if(!(prob<criterion.aspirationCriterion(this)))
					continue;
			}
			
			double res = (Double)neighbour.fitness()[0]-(Double)actual.fitness()[0];

			System.out.println("t "+temperature+" it "+iteration+" n "+neighbour);
			
			if(res<=0)
			{
				actual=neighbour;
				tabuList.add(neighbour);
				if(res<0)
				{
					double bestRes=(Double)actual.fitness()[0]-(Double)best.fitness()[0];
					if(bestRes<0)
						best=actual;
				}
			}
			else
			{
				Random r=new Random();
				double prob=r.nextDouble();
				if(prob<policy.acceptanceProbabilty(this))
				{
					actual=neighbour;
					tabuList.add(neighbour);
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
