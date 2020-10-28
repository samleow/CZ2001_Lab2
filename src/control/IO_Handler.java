package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import entity.Graph;

enum FileType
{
	GRAPH,
	HOSPITALS
}

public class IO_Handler
{
	
	public static Graph extractFile(String filename, FileType type)
	{
		String currLine = "";
		
		try
		{
			Graph g = new Graph(-1);
			File file = new File(filename);
			Scanner fileSc = new Scanner(file);
			System.out.println("File being extracted ...");
			switch(type)
			{
				case GRAPH:
					
					while(fileSc.hasNextLine())
					{
						currLine = fileSc.nextLine();
						if(currLine.charAt(0) != '#')
						{
							// plot graph
							//System.out.println(currLine);
							String[] nodeStr = currLine.split("\\s+");
							System.out.println("From: " + nodeStr[0] + "\tTo: " + nodeStr[1]);
							
							// can store nodes and edges into graph data structure here after initialization
					        g.addEdge(Integer.parseInt(nodeStr[0]),Integer.parseInt(nodeStr[1]));
						}
						else
						{
							if(currLine.contains("Nodes:"))
							{
								currLine = currLine.replaceAll("[^\\d]", " ").trim();
								String s[] = currLine.split("\\s+");
								int numNodes = Integer.parseInt(s[0]);
								int numEdges = Integer.parseInt(s[1]);
								System.out.println("No. of nodes: " + numNodes);
								System.out.println("No. of edges: " + numEdges);
								// can initialize the graph data structure using numNodes here
								g.init(1500000);
							}
						}
					}
					g.printGraph();
					break;
				case HOSPITALS:
					int numHospitals=0;
					int[] hospitals = new int[1500000];
					
					// for the list of hospitals
					while(fileSc.hasNextLine())
					{
						currLine = fileSc.nextLine();
						if(currLine.charAt(0) != '#')
							hospitals[Integer.parseInt(currLine)] = 1;
						else
						{
							currLine = currLine.replaceAll("[^\\d]", " ").trim();
							numHospitals = Integer.parseInt(currLine);
							System.out.println("No. of hospitals: " + numHospitals);
						}
					}
					
					break;
				default:
					break;
			}
			System.out.println("File successfully extracted!");
			fileSc.close();
			return g;
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		return null;
	}
	
	public static void saveFile(String fileName)
	{
		// save graph/outputs into txt file here
	}
	
}
