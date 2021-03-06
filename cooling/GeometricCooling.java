package cooling;

import sa.SimulatedAnnealing;

public final class GeometricCooling implements CoolingProcedure 
{
	protected double alpha;
	private int iterations=1;

	public GeometricCooling()
	{
		this(0.75,1);
	}
	
	public GeometricCooling(double alpha)
	{
		this.alpha=alpha;
	}
	
	public GeometricCooling(double alpha,int iter)
	{
		this.alpha=alpha;
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
			return sa.getTemperature()*alpha;
		else
			return sa.getTemperature();
	}
	
	@Override
	public boolean isEnd(SimulatedAnnealing sa) 
	{
		if(sa.getTemperature()<1)return true;
		if(sa.getIterationNumber()>1000)return true;
		return false;
	}
}
