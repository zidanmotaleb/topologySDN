package top;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Point {
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {

		return "Point("+x+"," +y+")";
	}


}

class Node {
	public Node(int i, int x, int y)
	{
		id = i;
		cord = new Point(x, y);
	}

	public int getId() { return id; }
	public Point getCord() { return cord; }

	@Override
	public String toString() {

		return "<" + id + ":(" + cord.getX() + "," + cord.getY() + ")>";
	}

	private int id;
	private Point cord;
}

class Edge {
	public Edge(Node from, Node to)
	{
		src = from;
		dest = to;
		distance = Util.dist(from.getCord(), to.getCord());
	}

	public Node getSrc() { return src; }
	public Node getDest() { return dest; }
	public double getDistance() {return distance;}

	private Node src;
	private Node dest;
	private final double distance;
}


class GraphReader {
	static Node makeNode(int id, String ns)
	{
		int ypos = ns.indexOf(",");
		if (ypos < 0)
			return new Node(0, 0, 0);

		String xs = ns.substring(1, ypos);
		String ys = ns.substring(ypos+1, ns.length()-1);

		int x = Integer.parseInt(xs);
		int y = Integer.parseInt(ys);

		return new Node(id, x, y);
	}

	static ArrayList<Edge> makeEdge(int fromNodeId, String connectedTo, Map<Integer, Node> nodes){

		String toNodeIds[] = connectedTo.split(",");

		Node fromNode = nodes.get(fromNodeId);

		ArrayList<Edge> edges = new ArrayList<Edge>();

		for (String toNodeId: toNodeIds){
			Integer toId = Integer.parseInt(toNodeId);

			Node toNode = nodes.get(toId);
			edges.add(new Edge(fromNode, toNode));
		}

		return edges;
	}
}
class GraphSize {
	final private int xo;
	final private int yo;
	final private int xs;
	final private int ys;

	public GraphSize(int xo, int yo, int xs, int ys) {
		super();
		this.xo = xo;
		this.yo = yo;
		this.xs = xs;
		this.ys = ys;
	}

	public int getXo() {
		return xo;
	}
	public int getYo() {
		return yo;
	}	
	public int getXs() {
		return xs;
	}
	public int getYs() {
		return ys;
	}	
	public int getXl() {
		return  xs-xo;
	}
	public int getYl() {
		return ys-yo;
	}	
}
public class Graph {

	public Graph(File ifile, File edgesFile,final GraphSize graphSize) {
		init(ifile, edgesFile);
		this.graphSize=graphSize;	

		System.out.println();
		System.out.println("====================");
		System.out.println("The Graph Size = ("+ graphSize.getXs() + "," + graphSize.getYs() + ")");
		area = new Area();
	}     	

	public void breadthFirst() {
		System.out.println("Breadth first traversal");     	

		NumberFormat nf = new DecimalFormat("0.000"); 
		System.out.println();
		System.out.println("////////////// "+ VN.size() +" Nodes ///////////////");

		for(Integer nodeId: VE.keySet()){
			System.out.println();
			System.out.println();
			System.out.println("For Node Id: " + nodeId);
			System.out.println("    Egdes are:");
			for(Edge e: VE.get(nodeId)){
				Node s = VN.get(e.getSrc().getId());
				Node d = VN.get(e.getDest().getId());
				double ds = Util.dist(s.getCord(), d.getCord());
				//System.out.println("    " + e.getSrc().getId() + " to " + e.getDest().getId() + " : Distance=" + nf.format(e.getDistance()));
				System.out.println("The distance between Node " + s.getId() + " " + s.getCord() + " and Node " + d.getId() + " " + d.getCord() +  " = " + nf.format(ds));
			}
		}
		//System.out.println("The distance between Node " + s.getId() + " " + s.getCord() + " and Node " + d.getId() + " " + d.getCord() +  " = " + nf.format(ds));

	}

	public void print() {
		double count=0;
		for(Node n: VN.values())
		{
			System.out.print(n);
			count +=1;
		}
		System.out.println("");

		StringBuilder line;
		for(Integer i: VE.keySet()){
			line = new StringBuilder();
			line.append(i).append(" > ");
			for (Edge e:VE.get(i)){
				line.append(e.getDest().getId() + ", ");
			}
			System.out.println(line);
		}

		System.out.println("Print Avrage of Xs and Ys");
		double sums[] = Util.sumCords(VN.values());
		double[] midPoint = new double[2];
		midPoint[0]= sums[0]/count;
		midPoint[1]= sums[1]/count;
		System.out.println("Xs:" + midPoint[0] + ", Ys:" + midPoint[1]);

		// Print the sorting array of the distances from the controller position to all nodes 


		// Node will be the middle of the topology
		double mX = graphSize.getXs();
		double mY = graphSize.getYs();
		//Point cordMid= mX;//, mY);

		//double sortPos[] = sortNodes(VN.values(), cordMid );

		// pick first node on the array


		// Calculate the delay time between the controller and other nodes


		//System.err.println("Examples");		
		//Util.foo(VN, VE, graphSize);



	// Function divide the topology area depend on the N, input(graphSize, N), return(Cards[Begin(x, y) End(x,y)] depend on N)
		int numberContr = 3, contrID =1000, j =0, k =1;	
		int contrX =0, contrY =0;
		Point[] areaN = Util.divide( graphSize.getXs(), numberContr);
		Node[] contrsLocations = new Node[numberContr];
		int start = 0;
		int end = 0;
		for (int i=0; i < numberContr ; i++){
			System.out.println("j =" + j + " k ="+ k);
			//System.out.println("Starting point of Area = " + areaN[j] );
			//System.out.println("Ending point of the Area = " + areaN[k] );
			
			// Location of Controllers at the middle of a specific area
			start = areaN[j].getX();
			end = areaN[k].getX();
			contrX = (start + end)/2;
			contrY = graphSize.getYs()/2;
			Point tempLocation = new Point(contrX, contrY);
			Node tempNodeContr = new Node(contrID, tempLocation.getX(), tempLocation.getY());
			//Point contrLocation; 
			//Point contrLocation = new Point(contrX, contrY);
			
			System.out.println("Value of Start Point=" + areaN[j]);
			System.out.println("Value of End Point=" + areaN[k]);
			
			
	// Function to get a point in the middle of specific area: input( begArea, EndArea, Nodes) return(nodesInArea)
			ArrayList<Node> nodesInArea = Util.areaNodes(areaN[j], areaN[k], VN.values());
			System.out.println("Area #: " + i);
			System.out.println("The temprary Location of the Controller = " + tempLocation);
			for(Node node: nodesInArea)
				System.out.println(node);			
			j += 2;
			k += 2;			
			Node contrLocation = Util.controllerLocation(tempNodeContr, nodesInArea);
			contrsLocations[i] = contrLocation;
			
			area.addPartition(contrLocation, nodesInArea);
			
			System.out.println("");
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("In Area( "+ i +" ) The Location of the Controller = " + contrLocation + " and the node located in this area" + nodesInArea);
		}
	// Function to assign  to the closed node to this point. input(nodesArea, begArea, EndArea, midCard) return(newCard)
		
		System.out.println("---------------Printing all Areas------------");
		area.print();
			
		
	}

	private void init(File ifile, File edgesFile)
	{

		//build Vertexes
		Scanner sc = null;
		try
		{
			sc = new Scanner(ifile);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		VN = new HashMap<Integer, Node>();	
		String s;
		while (sc.hasNext())
		{
			s = sc.next();
			//System.out.println(s);
			String [] ns = s.split(";");
			for(int i=0; i < ns.length; ++i)
			{
				Node nn = GraphReader.makeNode(i+1, ns[i]);
				VN.put(i+1, nn);
			}
		}
		sc.close();
		//build Edges
		sc = null;
		try
		{
			sc = new Scanner(edgesFile);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		VE = new HashMap<Integer, ArrayList<Edge>>();	

		while (sc.hasNext())
		{
			s = sc.next();
			//System.out.println(s);
			String [] ns = s.split(":");
			if(ns.length == 2){
				Integer fromNodeId = Integer.parseInt(ns[0]);

				ArrayList<Edge> edgeList = GraphReader.makeEdge(fromNodeId, ns[1], VN);
				if(edgeList!=null && edgeList.size() > 0){
					VE.put(fromNodeId, edgeList);
				}
			}
		}
	}

	Area getArea() {
		return area;
	}
	
	// G = <V, E>
	private Map<Integer, Node> VN;
	private Map<Integer, ArrayList<Edge>> VE;
	final private GraphSize graphSize;
	private Area area;
}
