package sa;

import java.util.HashMap;

public final class ExponentialCooling implements CoolingProcedure {
	protected double alpha;
	public ExponentialCooling(){
		this(0.5);
	}
	public ExponentialCooling(double alpha){
		this.alpha=alpha;
	}
	@Override
	public final double coolDown(SimulatedAnnealing sa) {
		return sa.getTemperature()*alpha;
	}
	@Override
	public boolean isEnd(SimulatedAnnealing sa) {
		if(sa.temperature<1)return true;
		if(sa.getIterationNumber()>100)return true;
		return false;
	}


}
