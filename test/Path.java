package test;

import solution.IntegerSolution;
import solution.Solution;

public class Path extends IntegerSolution
{
	protected Comparable<Double>[] objective;
	private boolean set=false;
	protected double[][] distances;
	protected double[][] costs;
	
	public Path()
	{
		this(new Integer[]{0,2,4,3,1,0});
	}
	public Path(Integer[] c)
	{
		variables=c;
		distances= new double[][]{{0, 1, 7, 8, 1},
				{1, 0, 10, 1, 9},
				{7, 10, 0, 1, 1},
				{8, 1, 1, 0, 6},
				{1, 9, 1, 6, 0}};

		costs= new double[][]{{0, 8, 1, 1, 4},
				{8, 0, 3, 12, 1},
				{1, 3, 0, 7, 9},
				{1, 12, 7, 0, 8},
				{4, 1, 9, 8, 0}};
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Comparable[] fitness() 
	{
		if(set) return objective;
		
		double d=0,c=0;
		for(int i=0;i<distances.length;++i)
		{
			d+=distances[variables[i]][variables[i+1]];
			c+=costs[variables[i]][variables[i+1]];
		}
		objective=new Double[2];
		
		set=true;
		objective[0]=d;
		objective[1]=c;
		
		return (Comparable[]) objective;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Solution nextNeighbour() 
	{
		int i=0,j=0,k;
		Integer[] neigh=variables.clone();
		
		while(i==0 || i==variables.length-1)
			i=(int)Math.floor(Math.random()*neigh.length);
		while(j==0 || j==variables.length-1 || j==i)
			j=(int)Math.floor(Math.random()*neigh.length);
		
		k=neigh[i];
		neigh[i]=neigh[j];
		neigh[j]=k;
		
		return new Path(neigh);
	}
	
//	@SuppressWarnings("rawtypes")
//	public boolean equals(Solution x)
//	{
//		for(int i=0;i<variables.length;++i)
//			if(	variables[i] != ((Path) x).variables[i])
//				return false;
//		return true;
//	}
	
	public String toString()
	{
		String res="D:"+objective[0]+" C:"+objective[1]+"/P:";
		for(int x:variables)
			res+=x+"-";
		return res.substring(0, res.length()-1);
	}
}
