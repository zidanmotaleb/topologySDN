package top;

import java.io.File;

public class Topology {
	public static void main(String args[]) {
		System.out.println("Welcome to Controller Placement for Reliable Connections in SDN");
		System.out.println("");
		
		String fileNodes = "/home/zidan/workspace/topo/src/top/data.txt";
		String fileEgdes = "/home/zidan/workspace/topo/src/top/edges.txt";

		if (args.length > 0) 
			fileNodes = args[0];

		final int numberContr = 3;
		final int minX = 0;
		final int minY = 0;
		final int maxX = 132;
		final int maxY = 50;

		//System.out.println("Input fileNodes is: " + fileNodes);
		File iNodesFile = new File(fileNodes);
		File iEdgesFile = new File(fileEgdes);
		if (iNodesFile.canRead()) {
			Graph G = new Graph(iNodesFile, iEdgesFile, numberContr, new GraphSize(minX,minY,maxX,maxY));
			System.out.println("///////////////////////////////////////////////");
			G.print();
			G.breadthFirst();
		} else {
			System.out.println("Unable to read "+fileNodes);
		}
		
		
		
	}
}