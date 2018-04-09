package top;

import java.util.ArrayList; 
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;


public class Area {
	public Area() {
		partitions = new Hashtable<Node, ArrayList<Node>>();
	}
	
	public void addPartition(Node controller, ArrayList<Node> nodes) {
		partitions.put(controller, nodes);
	}
	
	public Hashtable<Node, ArrayList<Node>> getAreas() {
		return partitions;
	}
	
	public void print() {
		System.out.println(partitions);
	}
	
    public  Set<Node> getControllers() {
    	return partitions.keySet();
    }
    
    public Entry<Node,ArrayList<Node>> getNodes(Node cntrl, int dummy) {
    	//Entry<Node, ArrayList<Node>> e = new Entry<Node, ArrayList<Node>>();
    	return null;
    }
    
    public ArrayList<Node> getNodes(Node cntrl) {
    	return partitions.get(cntrl);
    }
	
	public Hashtable<Node, ArrayList<Node>> partitions;
}
