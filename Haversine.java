package top;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class Haversine {
	private static final double FAKE_INF = 1E11;
	public static class Node {
		// 
		public int id;
		// name of the city
		public String name;
		// latitude, longitude
		public double lat, lon;
		// score from start to this node
		public double gScore;
		// heuristic score
		public double fScore;
		
		ArrayList<Node> neighboursList = new ArrayList<Node>();
		
		public Node(int _id, String _name, double _lat, double _lon) {
			id = _id;
			name = _name;
			lat = _lat;
			lon = _lon;
			gScore = FAKE_INF;
			fScore = FAKE_INF;
		}
		public Node(int _id, String _name, double _lat, double _lon, double _gScore, double _fScore) {
			id = _id;
			name = _name;
			lat = _lat;
			lon = _lon;
			gScore = _gScore;
			fScore = _fScore;
		}
		
		public String toString() {
			return "{" + id + ";" + name + ";" + lat + ";" + lon + ";" + gScore + ";" + fScore + "}";
		}
		public String neighboursListToString() {
			
			String s = "{" + id + ";" + name + ": ";
			
			if(neighboursList.size() > 0) {
				for(int i = 0; i < neighboursList.size() - 1; ++i)
					s += neighboursList.get(i).name + "; ";
				s += neighboursList.get(neighboursList.size()-1).name + "}";
			}
			
			return s;
		}
		
	}
	public static class NodeComparator implements Comparator<Node> {
		@Override 
		public int compare(Node a, Node b) {
			return (int)(a.fScore - b.fScore);
		}
	}
	public static double degToRad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	public static double distanceInMiles(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371; // Radius of the earth in km
		double dLat = degToRad(lat2-lat1);  // deg2rad below
		double dLon = degToRad(lon2-lon1);
		double sin_dLat = Math.sin(dLat/2.0);
		double sin_dLon = Math.sin(dLon/2.0);
		double a = sin_dLat * sin_dLat + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) * sin_dLon * sin_dLon; 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c; // Distance in km
		return d * 0.621371;
	}
	public static double distanceInMilesNodes(Node n1, Node n2) {
		return distanceInMiles(n1.lat, n1.lon, n2.lat, n2.lon);
	}
	
	public static ArrayList<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node startNode, Node endNode) {
		ArrayList<Node> path = new ArrayList<Node>();
		
		path.add(endNode);
		
		Node currentNode = endNode;
		while(!currentNode.equals(startNode)) {
			currentNode = cameFrom.get(currentNode);
			path.add(currentNode);
		}
		//System.out.println(path.size());
		return path;
	}
	
	public static ArrayList<Node> aStar(Node startNode, Node endNode) {
		HashSet<Node> visitedList = new HashSet<Node>();
		HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();
		NodeComparator nodeComparator = new NodeComparator();
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>(128, nodeComparator);
		
		startNode.gScore = 0;
		startNode.fScore = distanceInMilesNodes(startNode, endNode);
		Node currentNode = null;
		pQueue.add(startNode);
		
		while(!pQueue.isEmpty()) {
			currentNode = pQueue.remove();
			
			if(!visitedList.contains(currentNode)) {
				visitedList.add(currentNode);
				
				if(currentNode.equals(endNode))
					return reconstructPath(cameFrom, startNode, endNode);
				
				
				for(Node n : currentNode.neighboursList) {
					if(!visitedList.contains(n)) {
						
						// if not in the queue
						if(n.gScore == FAKE_INF)
							pQueue.add(n);
						
						double distToNeighbor = distanceInMilesNodes(n, currentNode);
						double nGScore = currentNode.gScore + distToNeighbor;
						
						if(nGScore > n.gScore)
							continue;
						
						double heuristicCostEst = distanceInMilesNodes(n, endNode);
						
						cameFrom.put(n, currentNode);
						n.gScore = nGScore;
						n.fScore = nGScore + heuristicCostEst;
					}
				}
			}
		}
		
		return null;
	}
	public static void haversine(String fileName) {
		
		HashMap<Integer, Node> nodeIdRefMap = new HashMap<Integer, Node>(128);
		HashMap<Node, Integer> nodeRefIdMap = new HashMap<Node, Integer>(128);
		
		ArrayList<Node> nodes = new ArrayList<Node>(128);
		Node currentNode = null;
		boolean startNodeInfo = false;
		boolean startEdgeInfo = false;
		// parse input here;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			Node n = null;
			int edgeSrc = -1, edgeTarget = -1;
			
			while ((line = br.readLine()) != null) {
			   String[] tokens = line.trim().split("\\s+");
			   if(startNodeInfo) {
				   if(tokens[0].equals("id")) { 
					   n.id = Integer.parseInt(tokens[1]);
					   nodeIdRefMap.put(n.id, n);
				   }
				   else if(tokens[0].equals("label")) {
					   String name = "";
					   for(int i = 1; i < tokens.length; ++i)
						   name += tokens[i] + " ";
					   
					   n.name = name.substring(1, name.length()-2);
				   }
				   else if(tokens[0].equals("Longitude"))
					   n.lon = Double.parseDouble(tokens[1]);
				   else if(tokens[0].equals("Latitude"))
					   n.lat = Double.parseDouble(tokens[1]);
				   else if(tokens[0].equals("]")) {
					   nodes.add(n);
					   startNodeInfo = false;
				   }
			   }
			   
			   if(startEdgeInfo) {
				   if(tokens[0].equals("source"))
					   edgeSrc = Integer.parseInt(tokens[1]);
				   else if(tokens[0].equals("target"))
					   edgeTarget = Integer.parseInt(tokens[1]);
				   else if(tokens[0].equals("]")) {
					   Node aNode = nodeIdRefMap.get(edgeSrc);
					   Node bNode = nodeIdRefMap.get(edgeTarget);
					   aNode.neighboursList.add(bNode);
					   bNode.neighboursList.add(aNode);
					   startEdgeInfo = false;
				   }
			   }
			   
			   if(tokens[0].equals("node")) {
				   startNodeInfo = true;
				   n = new Node(-1, "", 0, 0);
			   }
			   if(tokens[0].equals("edge"))
				   startEdgeInfo = true;
			} 
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		/*for(Node n : nodes) {
			System.out.println(n.toString());
			//System.out.println(n.neighboursListToString());
		}*/
		
		
		Node startNode;
		Node endNode;
		ArrayList<Node> path = null;
		
		int numNodes = nodes.size();
		double[][] distances = new double[numNodes][numNodes];
		for(int i = 0; i < numNodes; ++i) {
			
			startNode = nodes.get(i);
			for(int j = i+1; j < numNodes; ++j) {
				//reset info
				for(Node n : nodes) {
					n.gScore = FAKE_INF;
					n.fScore = FAKE_INF;
				}
				
				distances[i][j] = 0;
				endNode = nodes.get(j);
				
				path = aStar(startNode, endNode);
				
				if(path != null) {
					int pathLen = path.size();
					
					for(int k = 0; k < pathLen-1; ++k)
						distances[i][j] += distanceInMilesNodes(path.get(k), path.get(k+1));
					distances[j][i] = distances[i][j];
				} else {
					System.out.println("No path from " + startNode.name + " to " + endNode.name);
				}
				
			}
		}
		
		try {
			String line = "";
			PrintWriter writer = new PrintWriter(fileName + "_output.csv", "UTF-8");
			
			line += "City name;";
			for(Node n : nodes)
				line += n.name + ";";
			writer.println(line);
			
			for(int i = 0; i < numNodes; ++i){
				line = "";
				line += nodes.get(i).name + ";";
				
				for(int j = 0; j < numNodes; ++j)
					line += (int)distances[i][j] + ";";
				
				writer.println(line);
			}
			
			writer.close();
			System.out.println("Done!");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
