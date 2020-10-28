package control;

import entity.Graph;

public class MainApplication
{

	public static void main(String[] args)
	{
		IO_Handler.extractFile("smallTestGraph.txt", FileType.GRAPH);
		//IO_Handler.extractFile("roadNet-PA.txt", FileType.GRAPH);
		
	}

}
