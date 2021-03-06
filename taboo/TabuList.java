package taboo;

import java.util.LinkedList;

import solution.Solution;

public class TabuList 
{
	protected int length;
	protected LinkedList<Solution> tabuList;
	
	public TabuList(int l)
	{
		tabuList=new LinkedList<Solution>();
		length=l;
	}

	public void add(Solution e)
	{
		for(Solution i: tabuList){
			if(i.equals(e))return;
		}
		tabuList.addLast(e);
		if(tabuList.size()>length)
			while(tabuList.size()>length)
				tabuList.removeFirst();
	}

	public int length()
	{
		return tabuList.size();
	}

	public boolean contains(Solution e) 
	{
		for(Solution x:tabuList)
			if(e.equals(x)) return true;
		return false;
	}
}
