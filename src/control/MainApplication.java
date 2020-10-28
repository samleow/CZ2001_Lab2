package control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import entity.Graph;

public class MainApplication
{
	public static int V = 60;
	public static String[] path = new String[V];
	public static boolean[] hospital = new boolean[V];
	public static int J = 3;
	
	public static void main(String[] args) throws InterruptedException
	{
		//IO_Handler.extractFile("smallTestGraph.txt", FileType.GRAPH);
		hospital[25] = true;
		hospital[50] = true;
		Graph g = IO_Handler.extractFile("test.txt", FileType.GRAPH);
		for(int i = 0; i < 50; i++)
		{
			Iterator<Integer> j = g.list[i].listIterator();
			if(path[i] == null && j.hasNext())
			{
				System.out.println(i);
				BFS(i, g);
			}
		}
		
		for(int i = 0; i < 50; i++)
		{
			System.out.println(path[i]);
		}
	}

    static void BFS(int start, Graph g) throws InterruptedException 
    { 
        // Mark all the vertices as not visited(By default 
        // set as false) 
    	int s = start;
        int n = -1;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int[] parent = new int[V];
        boolean visited[] = new boolean[V]; 
 
        // Mark the current node as visited and enqueue it 
        visited[s]=true;
        queue.add(s);

        outerloop:
        while(queue.size() != 0) 
        {
            s = queue.poll(); 
            Iterator<Integer> i = g.list[s].listIterator();
            while(i.hasNext()) 
            {
                n = i.next();
                
                if(!visited[n]) 
                { 
                    visited[n] = true;
                    parent[n] = s;
                    if(path[n] != null)
                    	temp.add(n);
                    else
                        queue.add(n);
                }
                	
            	if(hospital[n])
                	break outerloop;
            }
        }
        
        String[] string = new String[temp.size()+1];
        string[temp.size()] = "";
        
        while(parent[n] != start)
        {
        	string[temp.size()] = n + "-" + string[temp.size()];
        	n = parent[n];
        }
        string[temp.size()] = n + "-" + string[temp.size()];
        string[temp.size()] = start + "-" + string[temp.size()];
        //System.out.println(temp.size() + "\t" + string[temp.size()]);
        
        for(int i = 0; i < temp.size(); i++)
        {
        	string[i] = path[temp.get(i)];
        	n = temp.get(i);
        	//n = parent[n];
        	while(parent[n] != start)
        	{
        		string[i] = n + "-" + string[i];
            	n = parent[n];
        	}
        	string[i] = n + "-" + string[i];
            string[i] = start + "-" + string[i];
            //System.out.println(i + "\t" + string[i]);
        }
        
        int shortestIndex = 0;
        for(int i = 1; i<temp.size()+1; i++)
        {
        	StringTokenizer tokens = new StringTokenizer(string[i]);
        	StringTokenizer tokenShortest = new StringTokenizer(string[shortestIndex]);
        	if(tokens.countTokens() < tokenShortest.countTokens())
        	{
        		shortestIndex = i;
        	}
        }
        
        String[] stringLength = string[shortestIndex].split("-");
        
        for(int i = 0; i < stringLength.length; i++)
        {
        	String[] stringSplit = string[shortestIndex].split("-", 2);
        	path[Integer.parseInt(stringSplit[0])] = string[shortestIndex];
        	System.out.println(start + "\t" + path[Integer.parseInt(stringSplit[0])]);
        	string[shortestIndex] = stringSplit[1];
        }
        //System.out.println(start + "\t" + string[shortestIndex]);
        //path[start] = string[shortestIndex];
    } 
}
