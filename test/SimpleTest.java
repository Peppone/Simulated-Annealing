package test;

import java.util.ArrayList;
import java.util.Random;

import sa.SimulatedAnnealingSO;
import solution.IntegerSolution;
import solution.Solution;
class MyIntegerSolution extends IntegerSolution{
	int task=5;
	int server=2;
	int weight[]={1,2,3,4,5};
	
	MyIntegerSolution(int size){
		super(size);
		
	}
	
	
	MyIntegerSolution (MyIntegerSolution s) {
		super(s);
	}
	
	@Override
	public Comparable[] fitness() {
		int instructions[]=new int[server];
		for(int i=0;i<this.variables.length;++i){
			instructions[variables[i]]+=weight[i];
		}
		int max=-1;
		for(int i=0;i<instructions.length;++i){
			if(max<instructions[i])max=instructions[i];
		}
		Double result[]=new Double[1];;
		result[0]=(double) max;
		return result;
		
	}
	public MyIntegerSolution nextNeighbour() {
		Random r = new Random();
		int index = r.nextInt(5);
		MyIntegerSolution duplicate=new MyIntegerSolution(this);
		int perturbedValue=r.nextInt(getMaxValue(0)-getMinValue(0)+1);
		int j=0;
		while(duplicate.getVariables(index)==perturbedValue)perturbedValue=r.nextInt((getMaxValue(0)-getMinValue(0)+1));
		duplicate.setVariable(perturbedValue, index);
		return duplicate;

	}


	@Override
	public Double overallConstraintViolation() {
		return new Double(0);
	}
	
}
public class SimpleTest {
	int task=5;
	int server=2;
	int weight[]={1,2,3,4,5};
	public static void main(String[] args) {
		MyIntegerSolution x0 = new MyIntegerSolution(5);
		x0.setMinValue(1,0);
		x0.setMaxValue(2,0);
		
		SimulatedAnnealingSO so= new SimulatedAnnealingSO(x0);
		so.setTemperature(2000);
		ArrayList<Solution> sol=so.execute();
		System.out.println(sol.get(0).fitness()[0]);
		Integer var[]=(Integer[])sol.get(0).getVariables();
		for(int i=0; i<var.length; i++){
			System.out.print(var[i]+" ");
		}

	}

}
