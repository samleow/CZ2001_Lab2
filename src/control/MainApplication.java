package control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import entity.Graph;
import java.io.IOException;

public class MainApplication
{
	public static int maxNode;
	public static int numHospital;
	public static String[][] shortestPath;
	public static int[][] distance;
	public static boolean[] hospital;
	public static int[] hospitalList;
	public static int k = 1;
	public static long TIMETAKEN = 0;
	public static final String SPLITTER = "->";
	
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input filename: ");
		String filenameIn = sc.nextLine();
		System.out.print("Output filename: ");
		String filenameOut = sc.nextLine();
		Graph g = IO_Handler.extractFile(filenameIn, FileType.GRAPH);
		
		maxNode = IO_Handler.maxNode + 1;
		hospital = new boolean[maxNode];
		
		numHospital = 5;
		hospitalList = new int[numHospital + 1];
		hospitalList[0] = 0;
		for(int i = 1; i <= numHospital; i ++)
		{
			hospitalList[i] = i*(maxNode/numHospital);
			System.out.println(i*(maxNode/numHospital));
		}
		for(int i = 0; i < numHospital + 1; i++)
			hospital[hospitalList[i]] = true;

		System.out.println("1.BFS Modified(k = 1)");
		System.out.println("2.BFS Multi-Source(k = 1)");
		System.out.println("3.BFS Standard(k > 1)");
		System.out.print("Option: ");
		int option = sc.nextInt();
		
		switch(option)
		{
		case 1:
			distance = new int[maxNode][k];
			shortestPath = new String[maxNode][k];
			for(int i = 0; i < maxNode; i++)
			{
				System.out.println(i);
				if(shortestPath[i][0] == null && !hospital[i])
				{

			        long startTime = System.nanoTime();
					BFSModified(i, g);
			        long stopTime = System.nanoTime();
			        TIMETAKEN += stopTime - startTime;
				}
			}
			break;
			
		case 2:
			distance = new int[maxNode][k];
			shortestPath = new String[maxNode][k];
			
			long startTime = System.nanoTime();
			BFSMulti(g);
			long stopTime = System.nanoTime();
	        TIMETAKEN += stopTime - startTime;
			break;
			
		case 3:
			System.out.print("Input K: ");
			k = sc.nextInt();

			distance = new int[maxNode][k];
			shortestPath = new String[maxNode][k];
			
			for(int i = 0; i < maxNode; i++)
			{
				System.out.println(i);

				startTime = System.nanoTime();
				BFSStandard(i, g, k);
				stopTime = System.nanoTime();
		        TIMETAKEN += stopTime - startTime;
			}
			break;
			
		default:
			System.out.println("No such option");
		}
		
		for(int i = 0; i < maxNode; i++)
		{
			System.out.printf(String.format("NODE %s\n", i));
			IO_Handler.saveFile(filenameOut, String.format("NODE %s\n", i));
			for(int j = 0; j < k; j++)
			{
				System.out.printf(String.format("%-3s:%-150s\n", distance[i][j], shortestPath[i][j]));
				IO_Handler.saveFile(filenameOut, String.format("%-3s:%-150s\n", distance[i][j], shortestPath[i][j]));
			}
			System.out.println("");
			IO_Handler.saveFile(filenameOut,"\n");
		}
		System.out.println("Execution Time: " + TIMETAKEN + " ns");
		IO_Handler.saveFile(filenameOut,"Execution Time: " + TIMETAKEN + " ns");
		sc.close();
	}
	
	static void BFSModified(int start, Graph g)
    {
    	ArrayList<Integer> temp = new ArrayList<Integer>();
    	LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[maxNode];
        int[] parent = new int[maxNode];
    	int source = start;
        int n = -1;
        
        visited[source]=true;
        queue.add(source);
        
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
                    
                    if(shortestPath[n][0] != null)
                    	temp.add(n);
                    else
                        queue.add(n);
                    
                    if(hospital[n])
                    	break outerloop;
                }
            	
            }
        }
        
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
        	paths[i] = shortestPath[n][0];
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
        
        if(currentShortestPath == null)
        	return;
        
        String[] stringLength = currentShortestPath.split(SPLITTER);
        
        for(int i = 0; i < stringLength.length-1; i++)
        {
        	String[] stringSplit = currentShortestPath.split(SPLITTER, 2);
        	
        	if(shortestPath[Integer.parseInt(stringSplit[0])][0] == null)
        	{
        		shortestPath[Integer.parseInt(stringSplit[0])][0] = currentShortestPath;
        		distance[Integer.parseInt(stringSplit[0])][0] = currentShortestPath.split(SPLITTER).length - 1;
        	}
        	currentShortestPath = stringSplit[1];
        }
    }
	
	public static void BFSMulti(Graph g) 
	{
		LinkedList<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[maxNode];
		Integer[] parent = new Integer[maxNode];
		int n = -1;
		
		for(int i = 0;i < numHospital; i++) 
	    { 
	        queue.add(hospitalList[i]); 
	        visited[hospitalList[i]] = true; 
	    } 
		
	    while(queue.size() != 0) 
	    { 
	        int source = queue.poll();
            Iterator<Integer> i = g.list[source].listIterator();
	      
            while(i.hasNext())  
	        { 
            	n = i.next();
            	
	            if(!visited[n]) 
	            {
	                distance[n][0] = distance[source][0] + 1;
	                parent[n] = source;
	                visited[n] = true;
	                queue.add(n);
	            } 
	        } 
	    }
	    
	    for(int i = 0; i < maxNode; i++)
	    {
	    	int j = i;
	    	String path = Integer.toString(j);
        	while(parent[j] != null)
	        {
	        	j = parent[j];
	        	path = path + SPLITTER + j;
	        }
        	shortestPath[i][0] = path;
	    }
	}
	
	public static void BFSStandard(int start, Graph g, int k)
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        Integer[] hospitalTemp = new Integer[k];
        boolean visited[] = new boolean[maxNode];
        int[] parent = new int[maxNode];
    	int source = start;
        int n = -1;
        int j = 0;
        
        visited[source]=true;
        queue.add(source);

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
    	        shortestPath[start][i] = paths[i];
    	        distance[start][i] = paths[i].split(SPLITTER).length - 1;
        	}
        }
    }
}
