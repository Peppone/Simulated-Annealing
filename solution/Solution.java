package solution;

public abstract class Solution<T> {
	
	protected int size;
	protected int violatedConstraints=0;
	protected T[] variables;
	public abstract Comparable[] fitness();
	public abstract Comparable overallConstraintViolation();
	public abstract Solution nextNeighbour();
	public int getViolatedConstraintsNumber(){return violatedConstraints;}
	public int getSize(){
		return size;
	}
	public Object[] getVariables(){
		return variables;
	}
	public abstract boolean equals(Solution s);
}
