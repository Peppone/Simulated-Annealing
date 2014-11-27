package sa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import solution.Solution;
import acceptance.AcceptancePolicy;
import acceptance.DefaultMOAcceptance;
import cooling.CoolingProcedure;
import cooling.GeometricCooling;

public class SimulatedAnnealingMO extends SimulatedAnnealing {

	public SimulatedAnnealingMO(Solution start){
		this(start,100,new GeometricCooling(),new DefaultMOAcceptance());
		
	}
	@Override
	public Solution getActualSolution(){
		return actual;
	}
	@Override
	public Solution getNeighbour(){
		return neighbour;
	}
	public SimulatedAnnealingMO(Solution start, double initialTemperature,CoolingProcedure cp, AcceptancePolicy ap){
		paretoFront=new ArrayList<Solution>();
		actual=start;
		temperature=initialTemperature;
		if(initialTemperature<=0){
			System.err.println("Temperature must be greater than 0. Setting default value");
			temperature=100;
		}
		cooling=cp;
		policy=ap;
	}
	
	protected ArrayList<Solution> paretoFront;
	protected Solution neighbour;
	protected Solution actual;
	
	public boolean compareToParetoFront(Solution s){
		Iterator<Solution>iterator = paretoFront.iterator();
		boolean nonDominated=true;
		boolean added=false;
		ArrayList<Solution> toRemove=new ArrayList<Solution>();
		while(iterator.hasNext()){
			Solution p=iterator.next();
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
		paretoFront.add(s);
		paretoFront.removeAll(toRemove);
		return false;
	}
	
	public int domination (Solution a, Solution b){
		Comparable[] aObj=a.fitness();
		Comparable[] bObj=b.fitness();
		if(aObj.length != bObj.length){
			System.err.println("Different objective length, abort");
			return 0;
		}
		boolean aDominates=false;
		boolean bDominates=false;
		int comp=aObj[0].compareTo(bObj[0]);
		if(comp<=0)aDominates=true;
		if (comp>=0)bDominates=true;
		for(int i=1;i<aObj.length;++i){
			comp=aObj[i].compareTo(bObj[i]);
			if(comp<=0 && aDominates)continue;
			if(comp>=0 && bDominates)continue;
			else return 0;
			
		}
		if(aDominates && bDominates)return 0;
		else if(aDominates)return -1;
		else return 1;
	}
	@Override
	public ArrayList<Solution> execute() {
		
		iteration=0;
		while(!cooling.isEnd(this)){
			neighbour=actual.nextNeighbour();
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
