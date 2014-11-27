package sa;

public final class DecrementalCooling implements CoolingProcedure {

	@Override
	public final double coolDown(SimulatedAnnealing sa) {
		return sa.getTemperature()-1;
	}
	@Override
	public boolean isEnd(SimulatedAnnealing sa) {
		if(sa.temperature<0)return true;
		if(sa.getIterationNumber()>10000)return true;
		return false;
	}


}
