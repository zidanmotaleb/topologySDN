package top;

import java.io.File;

public class Topology {
	public static void main(String args[]) {
		System.out.println("Welcome to Topology analysis Program");
		
		String filename = "/home/zidan/workspace/topo/src/top/data.txt";
		String fileEgdes = "/home/zidan/workspace/topo/src/top/edges.txt";

		if (args.length > 0) 
			filename = args[0];


		System.out.println("Input filename is: " + filename);
		File ifile = new File(filename);
		File iEdgesFile = new File(fileEgdes);
		if (ifile.canRead()) {
			Graph G = new Graph(ifile, iEdgesFile, new GraphSize(0,0,132,50));
			G.print();
			G.breadthFirst();
		} else {
			System.out.println("Unable to read "+filename);
		}
		
	}
}