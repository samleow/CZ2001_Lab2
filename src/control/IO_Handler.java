package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

enum FileType
{
	GRAPH,
	HOSPITALS
}

public class IO_Handler
{
	
	public static void extractFile(String filename, FileType type)
	{
		String currLine = "";
		
		try
		{
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
						}
					}
					break;
				case HOSPITALS:
					
					// for the list of hospitals
					
					break;
				default:
					break;
			}
			System.out.println("File successfully extracted!");
			fileSc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
	}
	
	public static void saveFile(String fileName)
	{
		// save graph/outputs into txt file here
	}
	
}
