package top;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
//import java.util.Arrays;

public class Util {

	public static double dist(Point a, Point b){		
		return	Math.sqrt((b.getX() - a.getX())*(b.getX() - a.getX()) + (b.getY() - a.getY())*(b.getY() - a.getY()));
	}
	
	public static double[] sumCords(final Collection<Node> nodes){
		
		int sumX=0;
		int sumY=0;
		
		for(Node n: nodes){
			sumX+= n.getCord().getX();
			sumY+= n.getCord().getY();			
		}		
		
		double[] sums = new double[2]; 
		sums[0] = sumX;
		sums[1] = sumY;
		
		return sums;
	}
	
	public static void foo(final Map<Integer, Node> nodesMap, final Map<Integer, ArrayList<Edge>> edgesMap, GraphSize graphSize){
		
		//search by id, lets say Node Id 2;
		//Node n = nodesMap.get(2);
		//System.out.println(n);
		
		//System.out.println(n.getId() + ", " + n.getCord().x );
		
		for(Integer nodeId: edgesMap.keySet()){
			System.out.println("For Node Id: " + nodeId);
			
			System.out.println("    Egdes are:");
			for(Edge e: edgesMap.get(nodeId)){
				System.out.println("    " + e.getSrc().getId() + " to " + e.getDest().getId() + " : Distance=" + e.getDistance());
			}
		}
	}
	
//Alg1:A
	
	// Function divide the topology area depend on the N, input(graphSize, N), return(Cards[Begin(x, y) End(x,y)] depend on N)
	public static Point[] divide(int graphXSize, int n){
		int splits = graphXSize / n ;
		
		int totalPoints = n * 2;
		
		Point[] areaPoints = new Point[totalPoints];
		
		int c = 0;
		int k = 0;
		for(int i=0 ; i < n ; i++){
			//Start			
			Point start = new Point(k*splits, 0);
			//System.out.println("Value i =" + i + " C =" + c +", K =" + k + " Start Point=" + start);
			areaPoints[c++] = start;
			
			
			//End
			++k;
			Point end = new Point(k*splits, 0);
			//System.out.println("Value i =" + i + " C =" + c  + ", K =" + k + " End Point=" + end);
			areaPoints[c++] = end;
			
		}
		
		return areaPoints;
	}
	
	
	
	// Function to get a point in the middle of specific area: input( begArea, EndArea, Nodes) return(nodesInArea)
	
	public static ArrayList<Node> areaNodes(Point startPoint, Point endPoint, final Collection<Node> nodes ){
		
		ArrayList<Node> nodesInArea = new ArrayList<Node>();
		
		for(Node n: nodes){
			if(n.getCord().getX() >= startPoint.getX() && n.getCord().getX() < endPoint.getX()){
				nodesInArea.add(n);
			}
		}
		
		return nodesInArea;
	}
	
	
	
	// Function to assigned controller to the closed node to this point. input(ArrayList<Node> nodesInArea, Point tempLocation) return(newCard)
	
	public static Node controllerLocation(Node tempLocation, ArrayList<Node> nodesInArea){
		double distance;
		int n = nodesInArea.size();
		double distFromNode[][]=new double[n][2];  
		int rows = distFromNode[0].length, columns = 0; 
		Node contrLocation = tempLocation; 
		System.out.println("Two-dimensional (2d) array: ");
		
		for(Node node: nodesInArea){
			distance = dist(tempLocation.getCord(), node.getCord());
				//System.out.format("%d \t", Sum[rows][columns]);
				distFromNode[rows][columns] = node.getId();
				System.out.format("%d \t", distFromNode[rows][columns]);		
				System.out.println("");
				distFromNode[rows][++columns] = distance;
				System.out.format("%d \t", distFromNode[rows][columns]);		
				System.out.println("");				
				rows++; columns = 0;	
				
		}			
		
		// Sorting the array depend on the second colon
		//distFromNode[2]Util.sortAcender.
		
		//Node contrLocation = new Node(tempLocation.getId(), distFromNode[0].X, distFromNode[0].Y);
		
		return contrLocation;
	}
	
	// Function to calculate the shortest path. return the list of shortest path. input(Nodes, Distances betweenNodes, newCard ) outPut(newCard, listShortestPaths)
	
	
	
	//Function to calculate the minimum delay between the nodes and Controller = shortestPath/3*10^8;   input(newCard, listShortestPaths) 
	
	
	//Results:
	//Function to calculate the average minimum delay = Sum(the minimum delay between the nodes and Controller)/Nodes#
	
		
	
	//Function to calculate the max minimum delay between the nodes and Controller = the farest shortestPath/3*10^8;   input(newCard, listShortestPaths) 

	
	
	
	// Function to get the distance between a node and the rest of the nodes, return(ID, Node cord,  the smallest distance)
	/*public static double[] sortNodes(final Collection<Node> nodes, Point cord ){		
		private Map<Integer, Double> sortPos;
		double ds= 0;
		int numberN = nodes.size();
		int count = 0;
		 double[] sortPos = new double[numberN]; 
		for(Node n: nodes){	
			  ds = Util.dist(n.getCord(), n.getCord());	
			  sortPos[count]=ds;
			  count++;
		}	
		Arrays.sort(sortPos);		
		return sortPos;	
	}*/	

//Alg2: K-Means //wait some time
	
	//Function to calculate the position of Controller by divide sumX/N, sumY/N input(nodesArea) return(midCard)
	
	
	// Function to assigned to the closed node to this point. input(nodesArea, midCard) return(newCard)
	
	//Repeated: // Function to calculate the shortest path. return the list of shortest path. input(nodesArea, Distances betweenNodes, newCard ) outPut(newCard, listShortestPaths)
	
	//Repeated:	//Function to calculate the minimum delay= nodePath/3*10^8;   input(newCard, listShortestPaths)
	
	
//Algo3: CNPA 	
	// Randomly select one node from the topology as the location of the controller. 
	
	
	// In the 2nd Controller choose the node with the biggest distance from the first Controller. input(nodesArea, Card) return(NewContr)
	
	
	//Assign the nodes depend on there distance from the Controller, input(Nodes, ContLocations[]),return(areaWithCont[Contr, nodesArea])
	
	
	//Repeated:// Function to calculate the shortest path. return the list of shortest path. input(Nodes, Distances betweenNodes, newCard ) outPut(newCard, listShortestPaths)
	
	//Repeated://Function to calculate the minimum delay between the nodes and Controller = shortestPath/3*10^8;   input(newCard, listShortestPaths) 
	
	
		//Results:
	//Repeated://Function to calculate the average minimum delay = Sum(the minimum delay between the nodes and Controller)/Nodes#
		
			
		
	//Repeated://Function to calculate the max minimum delay between the nodes and Controller = the farest shortestPath/3*10^8;   input(newCard, listShortestPaths) 

		
	
	
}


/* 
 * Dijkstra's algorithm
 * http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 * 
 * 
 * 
 * http://cs.fit.edu/~ryan/java/programs/graph/Dijkstra-java.html
 * public class Dijkstra {
  2 
  3    // Dijkstra's algorithm to find shortest path from s to all other nodes
  4    public static int [] dijkstra (WeightedGraph G, int s) {
  5       final int [] dist = new int [G.size()];  // shortest known distance from "s"
  6       final int [] pred = new int [G.size()];  // preceeding node in path
  7       final boolean [] visited = new boolean [G.size()]; // all false initially
  8 
  9       for (int i=0; i<dist.length; i++) {
 10          dist[i] = Integer.MAX_VALUE;
 11       }
 12       dist[s] = 0;
 13 
 14       for (int i=0; i<dist.length; i++) {
 15          final int next = minVertex (dist, visited);
 16          visited[next] = true;
 17 
 18          // The shortest path to next is dist[next] and via pred[next].
 19 
 20          final int [] n = G.neighbors (next);
 21          for (int j=0; j<n.length; j++) {
 22             final int v = n[j];
 23             final int d = dist[next] + G.getWeight(next,v);
 24             if (dist[v] > d) {
 25                dist[v] = d;
 26                pred[v] = next;
 27             }
 28          }
 29       }
 30       return pred;  // (ignore pred[s]==0!)
 31    }
 32 
 33    private static int minVertex (int [] dist, boolean [] v) {
 34       int x = Integer.MAX_VALUE;
 35       int y = -1;   // graph not connected, or no unvisited vertices
 36       for (int i=0; i<dist.length; i++) {
 37          if (!v[i] && dist[i]<x) {y=i; x=dist[i];}
 38       }
 39       return y;
 40    }
 41 
 42    public static void printPath (WeightedGraph G, int [] pred, int s, int e) {
 43       final java.util.ArrayList path = new java.util.ArrayList();
 44       int x = e;
 45       while (x!=s) {
 46          path.add (0, G.getLabel(x));
 47          x = pred[x];
 48       }
 49       path.add (0, G.getLabel(s));
 50       System.out.println (path);
 51    }
 52 
 53 }
 * 
 *  
 *  */



