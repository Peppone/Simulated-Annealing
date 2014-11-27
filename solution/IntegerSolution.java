package solution;

import java.util.Random;

public abstract class IntegerSolution extends Solution<Integer> {

	private int[] minValue;
	private int[] maxValue;

	public IntegerSolution() {
		this(1, null, null);
	}
	public IntegerSolution(int size,int minValue, int maxValue){
		this(size,null,null);
		if(minValue>=maxValue){
			System.err.println("Min >= Max, set default values");
			
		}else
		for(int i=0;i<size;++i){
			this.minValue[i]=minValue;
			this.maxValue[i]=maxValue;
		}
	}
	public IntegerSolution(int size) {
		this(size, null, null);
	}
	
	public IntegerSolution (IntegerSolution is){
		size=is.size;
		minValue=new int[size];
		maxValue=new int[size];
		for(int i=0;i<size;++i){
		minValue[i]=is.minValue[i];
		maxValue[i]=is.maxValue[i];
		}
		variables=is.copyVariables();
		
	}

	public IntegerSolution(int size, int[] minValue, int[] maxValue) {
		this.size = size;
		if (size < 0) {
			System.out.println("Invalid size, default set to 1");
			size = 1;
		}
		variables = new Integer[size];
		for(int i=0;i<variables.length;++i){
			variables[i]=new Integer(0);
		}
		if(minValue==null){
			minValue=new int[size];
			for(int i=0;i<size;++i)
				minValue[i]=Integer.MIN_VALUE;
		}
		if(maxValue==null){
			maxValue=new int[size];
			for(int i=0;i<size;++i)
				maxValue[i]=Integer.MAX_VALUE;
		}
		this.minValue = minValue;
		this.maxValue = maxValue;
		for(int i=0;i<size;++i)
		if (minValue[i] >= maxValue[i]) {
			System.err.println("Minimum value must be less than the maximum. Set to default values");
			minValue[i] = Integer.MIN_VALUE;
			maxValue[i] = Integer.MAX_VALUE;
		}
	}

	protected Integer[] copyVariables() {
		Integer[] var = new Integer[size];
		for (int i = 0; i < size; ++i) {
			var[i] = new Integer(variables[i]);
		}
		return var;
	}

	public int getMinValue(int index) {
		if(index<0 || index>=size){
			System.err.println("Trying to access an invalid location");
			return minValue[0];
		}
		return minValue[index];
	}

	public int getMaxValue(int index) {
		if(index<0 || index>=size){
			System.err.println("Trying to access an invalid location");
			return maxValue[0];
		}
		return maxValue[index];
	}

	public IntegerSolution setMaxValue(int max, int index){
		if(minValue[index]<max)
			maxValue[index]=max;
		return this;
	}
	
	public IntegerSolution setMaxValue(int max){
		for(int i=0;i<size;i++)
			setMaxValue(max,i);
		return this;
	}
	
	public IntegerSolution setMinValue(int min,int index){
		if(maxValue[index]>min)
			minValue[index]=min;
		return this;
	}
	
	public IntegerSolution setMinValue(int min){
		for(int i=0;i<size;i++)
			setMinValue(min,i);
		return this;
	}
	
	public void setVariable(Integer in, int index) {
		if (size <= index || index < 0) {
			System.err
					.println("Trying to access an invalid location, nothing is done");
			return;
		}
		variables[index] = in;
		return;
	}

	protected IntegerSolution setVariables(Integer[] var) {
		variables = var;
		size = var.length;
		return this;
	}
	
	protected Integer getVariables(int index) {
		return variables[index];
	}
	@Override
	public boolean equals(Solution s){
		IntegerSolution s2=(IntegerSolution) s;
		if(this.variables.length!=s2.variables.length)return false;
		for(int i=0;i<this.variables.length;++i){
			if(this.variables[i].intValue()!=s2.variables[i].intValue())
				return false;			
		}
		return true;
	}


}
