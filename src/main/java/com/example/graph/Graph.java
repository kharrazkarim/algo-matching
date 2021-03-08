package com.example.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Graph {
	
	
	@JsonIgnore
	private int nbnodes; 
	@JsonIgnore
	private int nbedges;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge>edges= new ArrayList<Edge>();

    public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}


	public int getNbnodes() {
		return nbnodes;
	}

	public void setNbnodes(int nbnodes) {
		this.nbnodes = nbnodes;
	}

	public int getNbedges() {
		return nbedges;
	}

	public void setNbedges(int nbedges) {
		this.nbedges = nbedges;
	}

	public void initGraph() {
		 for (int i = 0; i < this.nbnodes; i++) {
	            this.nodes.add(createNode(i)); 
	        } 
	        //currBlossoms.clear(); 
		
	} 
   public void createNode(int u, int v) {

    	 // ArrayList<Integer> nodes = new ArrayList<Integer>(); 

          this.nodes.get(u).addNbr(this.nodes.get(v)); 
          this.nodes.get(v).addNbr(this.nodes.get(u));
      
    } 
    
    Node createNode(int u) {
    	
        Node node = null; 
        node = new Node(); 
        node.setLabel(u); 
        node.setType(NodeType.FREE); 

        return node; 
    }

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	
	

}
