package solution;


public abstract class DoubleSolution extends Solution<Double> {

	private double [] minValue;
	private double [] maxValue;

	public DoubleSolution() {
		this(1, null, null);
	}
	public DoubleSolution(int size,double minValue, double maxValue){
		this(size,null,null);
		if(minValue>=maxValue){
			System.err.println("Min >= Max, set default values");
			
		}else
		for(int i=0;i<size;++i){
			this.minValue[i]=minValue;
			this.maxValue[i]=maxValue;
		}
	}
	public DoubleSolution(int size) {
		this(size, null, null);
	}
	
	public DoubleSolution (DoubleSolution is){
		size=is.size;
		minValue=new double[size];
		maxValue=new double[size];
		for(int i=0;i<size;++i){
		minValue[i]=is.minValue[i];
		maxValue[i]=is.maxValue[i];
		}
		variables=is.copyVariables();
		
	}

	public DoubleSolution(int size, double[] minValue, double[] maxValue) {
		this.size = size;
		if (size < 0) {
			System.out.println("Invalid size, default set to 1");
			size = 1;
		}
		variables = new Double[size];
		for(int i=0;i<variables.length;++i){
			variables[i]=new Double(0);
		}
		if(minValue==null){
			minValue=new double[size];
			for(int i=0;i<size;++i)
				minValue[i]=Integer.MIN_VALUE;
		}
		if(maxValue==null){
			maxValue=new double[size];
			for(int i=0;i<size;++i)
				maxValue[i]=Integer.MAX_VALUE;
		}
		this.minValue = minValue;
		this.maxValue = maxValue;
		for(int i=0;i<size;++i)
		if (minValue[i] >= maxValue[i]) {
			System.err.println("Minimum value must be less than the maximum. Set to default values");
			minValue[i] = Double.MIN_VALUE;
			maxValue[i] = Double.MAX_VALUE;
		}
	}

	protected Double[] copyVariables() {
		Double[] var = new Double[size];
		for (int i = 0; i < size; ++i) {
			var[i] = new Double(variables[i]);
		}
		return var;
	}

	public double getMinValue(int index) {
		if(index<0 || index>=size){
			System.err.println("Trying to access an invalid location");
			return minValue[0];
		}
		return minValue[index];
	}

	public double getMaxValue(int index) {
		if(index<0 || index>=size){
			System.err.println("Trying to access an invalid location");
			return maxValue[0];
		}
		return maxValue[index];
	}

	public void setVariable(Double in, int index) {
		if (size <= index || index < 0) {
			System.err
					.println("Trying to access an invalid location, nothing is done");
			return;
		}
		variables[index] = in;
		return;
	}

	protected DoubleSolution setVariables(Double[] var) {
		variables = var;
		size = var.length;
		return this;
	}

	public DoubleSolution setMaxValue(double max, int index){
		if(minValue[index]<max)
			maxValue[index]=max;
		return this;
	}
	
	public DoubleSolution setMaxValue(double max){
		for(int i=0;i<size;i++)
			setMaxValue(max,i);
		return this;
	}
	
	public DoubleSolution setMinValue(double min,int index){
		if(maxValue[index]>min)
			minValue[index]=min;
		return this;
	}
	
	public DoubleSolution setMinValue(double min){
		for(int i=0;i<size;i++)
			setMinValue(min,i);
		return this;
	}
	
	public boolean equals(Solution s){
		DoubleSolution s2=(DoubleSolution) s;
		if(this.variables.length!=s2.variables.length)return false;
		for(int i=0;i<this.variables.length;++i){
			if(this.variables[i].doubleValue()!=s2.variables[i].doubleValue())
				return false;
		}
		return true;
	}

}
