package control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import entity.Graph;
import java.io.IOException;

public class MainApplication
{
	public static int V;
	public static String[][] shortestPath;
	public static int[][] distance;
	public static boolean[] hospital;
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
		hospital = new boolean[V];
		distance = new int[k][];
		shortestPath = new String[k][];
		
		for(int i = 0; i < k; i++)
		{
			shortestPath[i] = new String[V];
			distance[i] = new int[V];
		}
		
		for(int i = 0; i < V; i += 10)
			hospital[i] = true;
		
		if(k > 1)
			for(int i = 0; i < V; i++)
			{
				System.out.println(i);
				BFSK(i, g, k);
			}
		
		else
			for(int i = 0; i < V; i++)
			{
				System.out.println(i);
				if(shortestPath[0][i] == null && !hospital[i])
					BFS1(i, g);
			}
		
		for(int i = 0; i < V; i++)
		{
			System.out.println(i);
			System.out.printf(String.format("NODE %s\n", i));
			IO_Handler.saveFile(filename, String.format("NODE %s\n", i));
			for(int j = 0; j < k; j++)
			{
				System.out.printf(String.format("%-3s:%-150s\n", distance[j][i], shortestPath[j][i]));
				IO_Handler.saveFile(filename, String.format("%-3s:%-150s\n", distance[j][i], shortestPath[j][i]));
			}
			System.out.println("");
			IO_Handler.saveFile(filename,"\n");
		}
		System.out.println("Execution Time: " + TIMETAKEN + " ns");
		IO_Handler.saveFile(filename,"Execution Time: " + TIMETAKEN + " ns");
		sc.close();
	}

    public static void BFSK(int start, Graph g, int k)
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        Integer[] hospitalTemp = new Integer[k];
        boolean visited[] = new boolean[V];
        int[] parent = new int[V];
    	int source = start;
        int n = -1;
        int j = 0;
        
        visited[source]=true;
        queue.add(source);

        long startTime = System.nanoTime();
        outerloop:
        while(queue.size() != 0)
        {
            source = queue.poll(); 
            Iterator<Integer> i = g.list[source].listIterator();
            while(i.hasNext()) 
            {
                n = i.next();
                if(!visited[n]) 
                {
                    visited[n] = true;
                    parent[n] = source;
                    queue.add(n);
                    
                    if(hospital[n])
                	{
                		hospitalTemp[j] = n;
                		if(++j == k)
                			break outerloop;
                	}
                }
            }
        }
        long stopTime = System.nanoTime();
        TIMETAKEN += stopTime - startTime;
        
        String[] paths = new String[k];
        for(int i = 0; i < k; i++)
        {
        	if(hospitalTemp[i] != null)
        	{
	        	n = hospitalTemp[i]; 
	        	paths[i] = Integer.toString(n);
	        	while(parent[n] != start)
		        {
		        	n = parent[n];
		        	paths[i] = n + SPLITTER + paths[i];
		        }
		        paths[i] = start + SPLITTER + paths[i];
        	}
        }
        
        for(int i = 0; i < k; i++)
        {
        	if(paths[i] != null)
        	{
    	        shortestPath[i][start] = paths[i];
    	        distance[i][start] = paths[i].split(SPLITTER).length - 1;
        	}
        }
    }
    
    public static void BFSN(int start, Graph g, int k)
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        Integer[] hospitalTemp = new Integer[hospitalNum];
        boolean visited[] = new boolean[V];
        int[] parent = new int[V];
    	int source = start;
        int n = -1;
        int j = 0;
        
        visited[source]=true;
        queue.add(source);

        long startTime = System.nanoTime();
        outerloop:
        while(queue.size() != 0)
        {
            source = queue.poll(); 
            Iterator<Integer> i = g.list[source].listIterator();
            while(i.hasNext()) 
            {
                n = i.next();
                if(!visited[n]) 
                {
                    visited[n] = true;
                    parent[n] = source;
                    queue.add(n);
                    
                    if(hospital[n])
                		hospitalTemp[j] = n;
                }
            }
        }
        long stopTime = System.nanoTime();
        TIMETAKEN += stopTime - startTime;
        
        String[] paths = new String[k];
        for(int i = 0; i < k; i++)
        {
        	if(hospitalTemp[i] != null)
        	{
	        	n = hospitalTemp[i]; 
	        	paths[i] = Integer.toString(n);
	        	while(parent[n] != start)
		        {
		        	n = parent[n];
		        	paths[i] = n + SPLITTER + paths[i];
		        }
		        paths[i] = start + SPLITTER + paths[i];
        	}
        }
        
        for(int i = 0; i < k; i++)
        {
        	if(paths[i] != null)
        	{
    	        shortestPath[i][start] = paths[i];
    	        distance[i][start] = paths[i].split(SPLITTER).length - 1;
        	}
        }
    }

    static void BFS1(int start, Graph g)
    {
    	ArrayList<Integer> temp = new ArrayList<Integer>();
    	LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[V];
        int[] parent = new int[V];
    	int source = start;
        int n = -1;
        
        visited[source]=true;
        queue.add(source);
        
        long startTime = System.nanoTime();
        outerloop:
        while(queue.size() != 0)
        {
            source = queue.poll(); 
            Iterator<Integer> i = g.list[source].listIterator();
            while(i.hasNext()) 
            {
                n = i.next();
                if(!visited[n]) 
                { 
                    visited[n] = true;
                    parent[n] = source;
                    
                    if(shortestPath[0][n] != null)
                    	temp.add(n);
                    else
                        queue.add(n);
                    
                    if(hospital[n])
                    	break outerloop;
                }
            	
            }
        }
        long stopTime = System.nanoTime();
        TIMETAKEN += stopTime - startTime;
        
        if(n < 0)
        	return;
        
        String[] paths = new String[temp.size()+1];
        int m = 0;
        
        if(hospital[n])
        {
	        paths[temp.size()] = Integer.toString(n);
	        while(parent[n] != start)
	        {
	        	n = parent[n];
	        	paths[temp.size()] = n + SPLITTER + paths[temp.size()];
	        }
	        paths[temp.size()] = start + SPLITTER + paths[temp.size()];
	        m++;
        }
        
        for(int i = 0; i < temp.size(); i++)
        {
        	n = temp.get(i);
        	paths[i] = shortestPath[0][n];
        	while(parent[n] != start)
        	{
            	n = parent[n];
        		paths[i] = n + SPLITTER + paths[i];
        	}
            paths[i] = start + SPLITTER + paths[i];
        }
        
        int shortestIndex = 0;
        for(int i = 0; i < temp.size()+m; i++)
        {
        	String[] compare = paths[i].split(SPLITTER);
        	String[] shortest = paths[shortestIndex].split(SPLITTER);
        	if(compare.length < shortest.length)
        		shortestIndex = i;
        }
        String currentShortestPath = paths[shortestIndex];
        
        AddPaths(currentShortestPath);
       
    } 
    
    public static void AddPaths(String currentShortestPath)
    {
    	if(currentShortestPath == null)
        	return;
        
        String[] stringLength = currentShortestPath.split(SPLITTER);
        
        for(int i = 0; i < stringLength.length-1; i++)
        {
        	String[] stringSplit = currentShortestPath.split(SPLITTER, 2);
        	
        	if(shortestPath[0][Integer.parseInt(stringSplit[0])] == null)
        	{
        		shortestPath[0][Integer.parseInt(stringSplit[0])] = currentShortestPath;
        		distance[0][Integer.parseInt(stringSplit[0])] = currentShortestPath.split(SPLITTER).length - 1;
        	}
        	currentShortestPath = stringSplit[1];
        }
    }
}
