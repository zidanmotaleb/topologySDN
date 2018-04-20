package top;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
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
	public static Point[] divide(int graphSizeX, int n){
		int splits = graphSizeX / n ;

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
		if (nodesInArea.isEmpty())			
			return tempLocation;

		double  distance=0;
		NumberFormat nf = new DecimalFormat("0.000"); 
		int n = nodesInArea.size();

		double distFromNode[][]=new double[n][2]; 
		int rows = 0, columns = 0; 
		Node contrLocation = tempLocation; 
		System.out.println("rows = " + rows + " Nodes number = " + n);
		System.out.println("Two-dimensional (2d) array: ");
		for(Node node: nodesInArea){
			distance = dist(tempLocation.getCord(), node.getCord());				
			distance = Double.parseDouble(nf.format(distance));
			distFromNode[rows][columns] = node.getId();
			columns += 1; 
			System.out.println("Node ID = "+ node.getId());				
			distFromNode[rows][columns] = distance; 
			System.out.println("The distance = "+ distance);
			System.out.println("");				
			rows +=1; columns = 0;	
		}			

		// Sorting the array depend on the second colon
		for (double[] d : distFromNode)
			System.out.println(Arrays.toString(d));	
		System.out.println("");

		Arrays.sort(distFromNode, new Comparator<double[]>() {
			@Override
			public int compare(double[] o1, double[] o2) {
				return Double.compare(o2[1], o1[1]);
			}
		});

		for (double[] d : distFromNode)
			System.out.println(Arrays.toString(d));	
		System.out.println("");

		double ID = distFromNode[n-1][0];
		System.out.println("Node ID the Controller will located = " + ID);
		System.out.println("");
		for(Node node: nodesInArea){
			if(ID == node.getId())
				contrLocation = node;

		}

		//Node contrLocation = new Node(tempLocation.getId(), distFromNode[0].X, distFromNode[0].Y);
		System.out.println("The Controller location will be on node = " + contrLocation);
		System.out.println("");
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


