package cooling;

import sa.SimulatedAnnealing;

public final class DecrementalCooling implements CoolingProcedure 
{
	private int iterations=1;

	public DecrementalCooling()
	{
		this.iterations=1;
	}

	public DecrementalCooling(int iter)
	{
		this.iterations=iter;
	}

	public void setIterations(int iter)
	{
		this.iterations=iter;
	}
	
	@Override
	public final double coolDown(SimulatedAnnealing sa) 
	{
		if(sa.getIterationNumber()%iterations==0)
			return sa.getTemperature()-1;
		else
			return sa.getTemperature();
	}
	
	@Override
	public boolean isEnd(SimulatedAnnealing sa) 
	{
		if(sa.getTemperature()<0)return true;
		if(sa.getIterationNumber()>10000)return true;
		return false;
	}
}
