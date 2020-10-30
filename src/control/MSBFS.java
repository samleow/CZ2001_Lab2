package control;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import entity.Graph;

public class MSBFS 
{
	public static int V;
	public static boolean[] visited;
	public static int distance[];
	public static String[] shortestPath;
	public static long TIMETAKEN = 0;
	public static final String SPLITTER = "->";
	
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Input filename: ");
		String filename = sc.nextLine();
		
		Graph g = IO_Handler.extractFile(filename, FileType.GRAPH);
		
		System.out.print("Output filename: ");
		filename = sc.nextLine();

		System.out.print("Input K: ");
		int k = sc.nextInt();
		
		V = IO_Handler.maxNode + 1;
		visited = new boolean[V];
		distance = new int[V];
		shortestPath = new String[V];
		
		/*for(int i = 0; i < k; i++)
		{
			shortestPath[i] = new String[V];
			distance[i] = new int[V];
		}*/
		
		int hospitalNum = 100;
		int[] hospital = new int[hospitalNum+1];
		hospital[0] = 0;
		for(int i = 1; i <= hospitalNum; i ++)
		{
			hospital[i] = i*(V/hospitalNum);
			System.out.println(i*(V/hospitalNum));
		}

        long startTime = System.nanoTime();
		nearestTown(hospital, g, hospitalNum);
        long stopTime = System.nanoTime();
        TIMETAKEN += stopTime - startTime;
		
		for(int i = 0; i < V; i++)
		{
			System.out.println(i);
			System.out.printf(String.format("NODE %s\n", i));
			IO_Handler.saveFile(filename, String.format("NODE %s\n", i));
			for(int j = 0; j < k; j++)
			{
				System.out.printf(String.format("%-3s:%-150s\n", distance[i], shortestPath[i]));
				IO_Handler.saveFile(filename, String.format("%-3s:%-150s\n", distance[i], shortestPath[i]));
			}
			System.out.println("");
			IO_Handler.saveFile(filename,"\n");
		}
		System.out.println("Execution Time: " + TIMETAKEN + " ns");
		IO_Handler.saveFile(filename,"Execution Time: " + TIMETAKEN + " ns");
		sc.close();
	}
	
	public static void nearestTown(int[] hospitals, Graph g, int hospitalNum) 
	{
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
	    for(int i = 0;i < hospitalNum; i++) 
	    { 
	        queue.add(hospitals[i]); 
	        visited[hospitals[i]] = true; 
	    } 
	  
	    Multisource_BFS(g,queue); 
	}
	
	public static void Multisource_BFS(Graph g ,LinkedList<Integer> queue) 
	{ 
		int n = -1;
		Integer[] parent = new Integer[V];
		
	    while(queue.size() != 0) 
	    { 
	        int source = queue.poll();
            Iterator<Integer> i = g.list[source].listIterator();
	      
            while(i.hasNext())  
	        { 
            	n = i.next();
            	
	            if(!visited[n]) 
	            {
	                distance[n] = distance[source] + 1;
	                parent[n] = source;
	                visited[n] = true;
	                queue.add(n);
	            } 
	        } 
	    }
	    
	    for(int i = 0; i < V; i++)
	    {
	    	int j = i;
	    	String path = Integer.toString(j);
        	while(parent[j] != null)
	        {
	        	j = parent[j];
	        	path = path + SPLITTER + j;
	        }
        	shortestPath[i] = path;
	    }
	}
}
