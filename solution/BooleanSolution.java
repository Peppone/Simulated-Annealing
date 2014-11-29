package solution;

public abstract class BooleanSolution extends Solution<Boolean> 
{
	public BooleanSolution() 
	{
		this(1);
	}
	
	public BooleanSolution (BooleanSolution is)
	{
		size=is.size;
		variables=is.copyVariables();
	}

	public BooleanSolution(int size) 
	{
		this.size = size;
		if (size < 0) {
			System.out.println("Invalid size, default set to 1");
			size = 1;
		}
		variables = new Boolean[size];
		for(int i=0;i<variables.length;++i)
			variables[i]=new Boolean(false);
	}

	protected Boolean[] copyVariables() 
	{
		Boolean[] var = new Boolean[size];
		for (int i = 0; i < size; ++i)
			var[i] = new Boolean(variables[i]);
		
		return var;
	}

	public void setVariable(Boolean in, int index) 
	{
		if (size <= index || index < 0) 
		{
			System.err.println("Trying to access an invalid location, nothing is done");
			return;
		}
		variables[index] = in;
		return;
	}

	protected BooleanSolution setVariables(Boolean[] var) 
	{
		variables = var;
		size = var.length;
		return this;
	}
	
	protected Boolean getVariables(int index) 
	{
		return variables[index];
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Solution s)
	{
		BooleanSolution s2=(BooleanSolution) s;
		if(this.variables.length!=s2.variables.length) return false;
		
		for(int i=0;i<this.variables.length;++i)
			if(this.variables[i].booleanValue()!=s2.variables[i].booleanValue())
				return false;			
		
		return true;
	}
}
