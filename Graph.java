package top;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Point {
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	@Override
	public String toString() {

		return "Point("+x+"," +y+")";
	}
	private double x;
	private double y;
}

class Node {
	public Node(int i, double x, double y)
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

class GmlHeader {
    public String toString() {
	String str = new String();
	str += "  DateObtained " + '"' + DateObtained + "\"\n";
	str += "  GeoLocation \"" + GeoLocation + "\"\n";
	str += "  GeoExtent \"" + GeoExtent + "\"\n";
	str += "  Network \"" + Network + "\"\n";
	str += "  Provenance \"" + Provenance + "\"\n";
	str += "  Access " + Access + "\n";
	str += "  Source \"" + Source + "\"\n";
	str += "  Version \"" + Version + "\"\n";
	str += "  Type \"" + Type + "\"\n";
	str += "  DateType \"" + DateType + "\"\n";
	str += "  Backbone " + Backbone +  "\n";
	str += "  Commercial " + Commercial + "\n";
	str += "  label \"" + label + "\"\n";
	str += "  ToolsetVersion \"" + ToolsetVersion + "\"\n";
	str += "  Customer " + Customer + "\n";
	str += "  IX " + IX + "\n";
	str += "  SourceGitVersion \"" + SourceGitVersion + "\"\n";
	str += "  DateModifier \"" + DateModifier + "\"\n";
	str += "  DateMonth \"" + DateMonth +  "\"\n";
	str += "  LastAccess \"" + LastAccess + "\"\n";
	str += "  Layer \"" + Layer + "\"\n";
	str += "  Creator \"" + Creator + "\"\n";
	str += "  Developed " + Developed + "\n";
	str += "  Transit " + Transit + "\n";
	str += "  NetworkDate \"" + NetworkDate + "\"\n";
	str += "  DateYear \"" + DateYear + "\"\n";
	str += "  LastProcessed \"" + LastProcessed + "\"\n";
	str += "  Testbed " + Testbed;
	return str;
    }
    
    public String DateObtained="3/02/11";
    public String GeoLocation="US";
    public String GeoExtent="Country";
    public String  Network="Abilene";
    public String  Provenance="Primary";
    public int  Access = 0;
    public String  Source="http://www.internet2.edu/pubs/200502-IS-AN.pdf";
    public String  Version="1.0";
    public String  Type="REN";
    public String  DateType="Historic";
    public int  Backbone = 1;
    public int  Commercial = 0;
    public String  label ="Abilene";
    public String  ToolsetVersion ="0.3.34dev-20120328";
    public int  Customer = 0;
    public int  IX = 0;
    public String  SourceGitVersion="e278b1b";
    public String  DateModifier="=";
    public String  DateMonth="02";
    public String  LastAccess="3/02/11";
    public String  Layer="IP";
    public int  Developed = 0;
    public int  Transit = 0;
    public String  NetworkDate="2005_02";
    public String  DateYear="2005";
    public String  LastProcessed="2011_09_01";
    public int  Testbed = 0;
	public String  Creator="Topology Zoo Toolset";
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

	public Graph(File ifile, File edgesFile,int nContr, GraphSize graphSize) {
		init(ifile, edgesFile);
		this.graphSize=graphSize;
		this.numberContr = nContr;

		System.out.println();
		System.out.println("====================");
		System.out.println("The Graph Size = ("+ graphSize.getXo() + "," + graphSize.getYo() + ")" + 
				" and  = ("+ graphSize.getXs() + "," + graphSize.getYs() + ")");
		area = new Area();
	}     	

    public void makeGml(Node cntrl)
    {
	// header.IX = 4;
	
	    try {
	    	File gmlFile = new File(Constants.DataPath + "/Area_" + cntrl.getId() + ".gml");
		GmlHeader header = new GmlHeader();
	    	PrintWriter writer = new PrintWriter(gmlFile);
	    	writer.println("graph [");
	    	writer.println(header);
		
	    	ArrayList<Node> nodes = area.getNodes(cntrl);
	    	
	    	// write nodes ...
	    	for(Node node : nodes)
	    	{
	    		writer.println("\tnode [");
	    		writer.println("\t  id " + node.getId());
	    		writer.println("\t  label " + '"' + node.getId() + '"');
	    		writer.println("\t  Country " + '"' + cntrl.getId() + '"');
	    		writer.println("\t  Longitude " + node.getCord().getX());
	    		writer.println("\t  Internal 1");
	    		writer.println("\t  Latitude " + node.getCord().getY());
	    		writer.println("\t]");
	    	}
	    	
	    	// write edges ...
	    	for(Node node : nodes)
	    	{
	    		ArrayList<Edge> edges = VE.get(node.getId());
	    		for(Edge e : edges)
	    		{
		    		writer.println("\tedge [");
		    		writer.println("\t  source " + e.getSrc().getId());
		    		writer.println("\t  target " + e.getDest().getId());
		    		writer.println("\t  LinkType \"OC-192\"");
		    		writer.println("\t  LinkLabel \"OC-192c\"");
		    		writer.println("\t  LinkNote \"c\"");
		    		writer.println("\t]");
	    		}
	    	}
	    	writer.println("]");
	    	
		writer.close();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
    	
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
		int contrID =1000, j =0, k =1;	
		double contrX =0, contrY =0;
		int graphSizeX = graphSize.getXs() - graphSize.getXo();
		Point[] areaN = Util.divide( graphSizeX, numberContr);
		Node[] contrsLocations = new Node[numberContr];
		 double start = 0;
		double end = 0;
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
			
			makeGml(contrLocation);
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
	private int numberContr;
}
