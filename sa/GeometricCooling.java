package sa;

import java.util.HashMap;

public final class GeometricCooling implements CoolingProcedure {
	protected double alpha;
	public GeometricCooling(){
		this(0.75);
	}
	public GeometricCooling(double alpha){
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
