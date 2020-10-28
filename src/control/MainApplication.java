package control;

import java.util.Iterator;
import java.util.LinkedList;

import entity.Graph;

public class MainApplication
{
	public static int V = 1500000;
	public static int[] dist = new int[V];
	public static boolean[] visited = new boolean[V];

	public static void main(String[] args)
	{
		//IO_Handler.extractFile("smallTestGraph.txt", FileType.GRAPH);
		Graph g = IO_Handler.extractFile("smallTestGraph.txt", FileType.GRAPH);
		BFS(2, g);
	}

    static void BFS(int s, Graph g) 
    { 
        // Mark all the vertices as not visited(By default 
        // set as false) 
        boolean visited[] = new boolean[V]; 
  
        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
  
        // Mark the current node as visited and enqueue it 
        visited[s]=true; 
        queue.add(s); 
  
        while (queue.size() != 0) 
        { 
            // Dequeue a vertex from queue and print it 
            s = queue.poll(); 
            System.out.print(s+" "); 
  
            // Get all adjacent vertices of the dequeued vertex s 
            // If a adjacent has not been visited, then mark it 
            // visited and enqueue it 
            Iterator<Integer> i = g.list[s].listIterator(); 
            while (i.hasNext()) 
            { 
                int n = i.next(); 
                if (!visited[n]) 
                { 
                    visited[n] = true; 
                    queue.add(n); 
                } 
            } 
        } 
    } 
}
