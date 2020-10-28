package entity;

import java.util.LinkedList;

public class Graph
{
	int vertex;
	LinkedList<Integer> list[];

	public Graph(int vertex)
	{
		init(vertex);
	}

	public void init(int vertex)
	{
		if (vertex <= 0)
			return;
		this.vertex = vertex;
		list = new LinkedList[vertex];
		for (int i = 0; i < vertex; i++)
		{
			list[i] = new LinkedList<>();
		}
	}

	public void addEdge(int source, int destination)
	{

		// add edge
		list[source].addFirst(destination);

		// add back edge ((for undirected)
		// list[destination].addFirst(source);
	}

	public void printGraph()
	{
		for (int i = 0; i < vertex; i++)
		{
			if (list[i].size() > 0)
			{
				System.out.print("Vertex " + i + " is connected to: ");
				for (int j = 0; j < list[i].size(); j++)
				{
					System.out.print(list[i].get(j) + " ");
				}
				System.out.println();
			}
		}
	}
}
