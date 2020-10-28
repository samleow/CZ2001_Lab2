package control;
import java.io.File;
import java.io.FileNotFoundException;
// Java program to find shortest path in an undirected 
// graph 
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.LinkedList;
import java.util.Scanner; 
  
public class pathUnweighted { 
  
    // Driver Program 
    public static void main(String args[]) throws FileNotFoundException 
    { 
        // No of vertices 
        int v = 500; 
  
		String currLine = "";
        
		
		//TEST FILE NAME
		File file = new File("test.txt");
		Scanner fileSc = new Scanner(file);	
        
        
        // Adjacency list for storing which vertices are connected 
        ArrayList<ArrayList<Integer>> adj =  
                            new ArrayList<ArrayList<Integer>>(v); 
        for (int i = 0; i < v; i++) { 
            adj.add(new ArrayList<Integer>()); 
        } 
  

        while(fileSc.hasNextLine()) {
        	currLine = fileSc.nextLine();
			if(currLine.charAt(0) != '#')
			{
				// plot graph
				//System.out.println(currLine);
				String[] nodeStr = currLine.split("\\s+");
				System.out.println("From: " + nodeStr[0] + "\tTo: " + nodeStr[1]);
				
				// can store nodes and edges into graph data structure here after initialization
		        addEdge(adj, Integer.parseInt(nodeStr[0]),Integer.parseInt(nodeStr[1]));
			}
        	
        
        }
        
        //EDIT VALUE HERE
        int source = 14, dest = 20; 
        printShortestDistance(adj, source, dest, v);
        printShortestDistance(adj, 25, 20, v); 
    } 
  
    // function to form edge between two vertices 
    // source and dest 
    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) 
    { 
        adj.get(i).add(j); 
        adj.get(j).add(i); 
    } 
  

    private static void printShortestDistance( 
                     ArrayList<ArrayList<Integer>> adj, 
                             int s, int dest, int v) 
    { 

        int pred[] = new int[v]; 
        int dist[] = new int[v]; 
  
        if (BFS(adj, s, dest, v, pred, dist) == false) { 
            System.out.println("Given source and destination" +  
                                         "are not connected"); 
            return; 
        } 
  
        // LinkedList to store path 
        LinkedList<Integer> path = new LinkedList<Integer>(); 
        int crawl = dest; 
        path.add(crawl); 
        while (pred[crawl] != -1) { 
            path.add(pred[crawl]); 
            crawl = pred[crawl]; 
        } 
  
        // Print distance 
        System.out.println("Shortest path length is: " + dist[dest]); 
  
        // Print path 
        System.out.println("Path is ::"); 
        for (int i = path.size() - 1; i >= 0; i--) { 
            System.out.print(path.get(i) + " "); 
        } 
    } 
  
    // a modified version of BFS that stores predecessor 
    // of each vertex in array pred 
    // and its distance from source in array dist 
    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, 
                                  int dest, int v, int pred[], int dist[]) 
    { 
        // a queue to maintain queue of vertices whose 
        // adjacency list is to be scanned as per normal 
        // BFS algorithm using LinkedList of Integer type 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
  
        // boolean array visited[] which stores the 
        // information whether ith vertex is reached 
        // at least once in the Breadth first search 
        boolean visited[] = new boolean[v]; 
  
        // initially all vertices are unvisited 
        // so v[i] for all i is false 
        // and as no path is yet constructed 
        // dist[i] for all i set to infinity 
        for (int i = 0; i < v; i++) { 
            visited[i] = false; 
            dist[i] = Integer.MAX_VALUE; 
            pred[i] = -1; 
        } 
  
        // now source is first to be visited and 
        // distance from source to itself should be 0 
        visited[src] = true; 
        dist[src] = 0; 
        queue.add(src); 
  
        // bfs Algorithm 
        while (!queue.isEmpty()) { 
            int u = queue.remove(); 
            for (int i = 0; i < adj.get(u).size(); i++) { 
                if (visited[adj.get(u).get(i)] == false) { 
                    visited[adj.get(u).get(i)] = true; 
                    dist[adj.get(u).get(i)] = dist[u] + 1; 
                    pred[adj.get(u).get(i)] = u; 
                    queue.add(adj.get(u).get(i)); 
  
                    // stopping condition (when we find 
                    // our destination) 
                    if (adj.get(u).get(i) == dest) 
                        return true; 
                } 
            } 
        } 
        return false; 
    } 
} 