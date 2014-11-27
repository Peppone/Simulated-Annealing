package test;

import java.util.ArrayList;
import java.util.Random;

import sa.AcceptancePolicy;
import sa.CoolingProcedure;
import sa.SimulatedAnnealingMO;
import solution.IntegerSolution;
import solution.Solution;
class MyIntegerSolutionMO extends IntegerSolution{
	int task=5;
	int server=2;
	int weight[]={1,2,3,4,5};
	
	MyIntegerSolutionMO(){
		super(5,0,1);
		for(int i=0;i<5;++i){
			variables[i]=0;
		}
	}
	MyIntegerSolutionMO (MyIntegerSolutionMO s) {
		super(s);
	}
	@Override
	public Comparable[] fitness() {
		Double [] fitnessF=new Double[2];
		fitnessF[0]=maxCompletionTime();
		fitnessF[1]=minIdleServers();
		return fitnessF;
	}
	public Double minIdleServers(){
		Integer[] allocation=(Integer[]) this.getVariables();
		int [] allocation_resources=new int[server];
		double max=0;
			for(int i=0;i<allocation.length;++i){
				allocation_resources[allocation[i]]+=1;				
			}
			for(int i=0;i<server;++i){
				if(allocation_resources[i]==0)
					max++;
			}
			return new Double(-max);
	}
	public Double maxCompletionTime(){
		int instructions[]=new int[server];
		for(int i=0;i<this.variables.length;++i){
			instructions[variables[i]]+=weight[i];
		}
		int max=-1;
		for(int i=0;i<instructions.length;++i){
			if(max<instructions[i])max=instructions[i];
		}
		return new Double(max);
	}
	@Override
	public Solution nextNeighbour() {
		Random r = new Random();
		int index = r.nextInt(5);
		MyIntegerSolutionMO duplicate=new MyIntegerSolutionMO(this);
		int perturbedValue=r.nextInt(getMaxValue(0)-getMinValue(0)+1);
		int j=0;
		while(duplicate.getVariables(index)==perturbedValue)perturbedValue=r.nextInt((getMaxValue(0)-getMinValue(0)+1));
		duplicate.setVariable(perturbedValue, index);
		return duplicate;

	}
	public String print(){
		String res="";
		for(int i=0;i<5;++i){
		res+=" "+variables[i];
		}
		return res;
	}
}

public class MultiObjTest {
	public static void main (String[]args){
		
		SimulatedAnnealingMO mo=new SimulatedAnnealingMO(new MyIntegerSolutionMO());
		ArrayList<Solution> sol=mo.execute();
		for(Solution i:sol)
		System.out.println(("Fun "+i.fitness()[0]+" "+i.fitness()[1]+"  "+((MyIntegerSolutionMO)i).print())+" "+((MyIntegerSolutionMO)i).getVariables().length);
		}
}
