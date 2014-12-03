package sa;

import java.util.ArrayList;
import java.util.Iterator;

import acceptance.AcceptancePolicy;
import cooling.CoolingProcedure;

import solution.Solution;

public class ConstrainedAnnealingMO extends SimulatedAnnealingMO {
	
	
	
	public ConstrainedAnnealingMO(Solution start) {
		super(start);
	}

	public ConstrainedAnnealingMO(Solution start, double initialTemperature,
			CoolingProcedure cp, AcceptancePolicy ap) {
		super(start, initialTemperature, cp, ap);
	}

	@Override
	public boolean compareToParetoFront(Solution s){
		Iterator<Solution>iterator = paretoFront.iterator();
		boolean nonDominated=true;
		boolean added=false;
		boolean sViolates=s.getViolatedConstraintsNumber()>0?true:false;	
		ArrayList<Solution> toRemove=new ArrayList<Solution>();
		while(iterator.hasNext()){
			Solution p=iterator.next();
			boolean pViolates=p.getViolatedConstraintsNumber()>0?true:false;
			if((sViolates && pViolates) || (!sViolates && !pViolates)){
				
			if(s.equals(p))return true;
			int dominate=domination(s,p);
			switch(dominate){
			case -1: toRemove.add(p);
					 break;
			case 0:	break;
			case 1:	return false;
			default: System.err.println("Wrong value");break;
				}
			}
			else if(sViolates && !pViolates){
				int dominate=domination(s,p);
				switch(dominate){
				case -1: break;
				case 0:	break;
				case 1:	return false;
				default: System.err.println("Wrong value");break;
					}
			}
			else if(!sViolates && pViolates){
				continue;
			}
		}
		paretoFront.add(s);
		paretoFront.removeAll(toRemove);
		return false;
	}

}
