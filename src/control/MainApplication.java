package control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import entity.Graph;

public class MainApplication
{
	public static int V = 50;
	public static String[] shortestPath = new String[V];
	public static boolean[] hospital = new boolean[V];
	
	public static void main(String[] args)
	{
		hospital[20] = true;
		//hospital[50] = true;
		Graph g = IO_Handler.extractFile("test.txt", FileType.GRAPH);
		for(int i = 0; i < V; i++)
		{
			Iterator<Integer> j = g.list[i].listIterator();
			if(shortestPath[i] == null && j.hasNext() && !hospital[i])
			{
				BFS(i, g);
			}
		}
		
		for(int i = 0; i < V; i++)
			System.out.println(shortestPath[i]);
	}

    static void BFS(int start, Graph g)
    {
    	int root = start;
        int n = -1;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int[] parent = new int[V];
        boolean visited[] = new boolean[V];
        
        
        visited[root]=true;
        queue.add(root);

        outerloop:
        while(queue.size() != 0)
        {
            root = queue.poll(); 
            Iterator<Integer> i = g.list[root].listIterator();
            while(i.hasNext()) 
            {
                n = i.next();
                if(!visited[n]) 
                { 
                    visited[n] = true;
                    parent[n] = root;
                    
                    if(shortestPath[n] != null)
                    	temp.add(n);
                    else
                        queue.add(n);
                }
                	
            	if(hospital[n])
                	break outerloop;
            }
        }
        
        String[] paths = new String[temp.size()+1];
        int m = 0;
        
        if(hospital[n])
        {
	        paths[temp.size()] = Integer.toString(n);
	        while(parent[n] != start)
	        {
	        	n = parent[n];
	        	paths[temp.size()] = n + "->" + paths[temp.size()];
	        }
	        paths[temp.size()] = start + "->" + paths[temp.size()];
	        m++;
        }
        
        for(int i = 0; i < temp.size(); i++)
        {
        	n = temp.get(i);
        	paths[i] = shortestPath[n];
        	while(parent[n] != start)
        	{
            	n = parent[n];
        		paths[i] = n + "->" + paths[i];
        	}
            paths[i] = start + "->" + paths[i];
        }
        
        int shortestIndex = 0;
        for(int i = 0; i < temp.size()+m; i++)
        {
        	String[] compare = paths[i].split("->");
        	String[] shortest = paths[shortestIndex].split("->");
        	if(compare.length < shortest.length)
        		shortestIndex = i;
        }
        
        String currentShortestPath = paths[shortestIndex];
        String[] stringLength = currentShortestPath.split("->");
        
        for(int i = 0; i < stringLength.length-1; i++)
        {
        	String[] stringSplit = currentShortestPath.split("->", 2);
        	if(shortestPath[Integer.parseInt(stringSplit[0])] == null)
        		shortestPath[Integer.parseInt(stringSplit[0])] = currentShortestPath;
        	currentShortestPath = stringSplit[1];
        }
    } 
}
