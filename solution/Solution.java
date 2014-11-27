package solution;

public abstract class Solution<T> {
	
	protected int size;
	protected T[] variables;
	public abstract Comparable[] fitness();
	public abstract Solution nextNeighbour();
	public int getSize(){
		return size;
	}
	public Object[] getVariables(){
		return variables;
	}
	public abstract boolean equals(Solution s);
}
