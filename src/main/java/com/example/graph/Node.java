package com.example.graph;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Node {

	private int label = 0; 
	@JsonIgnore
    private LinkedHashSet<Node> nbrs = new LinkedHashSet<Node>(); 
	@JsonIgnore
    private Node matchedWith = null;
    @JsonIgnore
    private int forIteration;
    @JsonIgnore
    private NodeType type; 
    @JsonIgnore
    private Node parent = null;
    @JsonIgnore
    private boolean contracted = false;
	

	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	public LinkedHashSet<Node> getNbrs() {
		return nbrs;
	}
	public void setNbrs(LinkedHashSet<Node> nbrs) {
		this.nbrs = nbrs;
	}
	void addNbr(Node n) { 
        if (n == null) { 
            return; 
        } 
        if (n == this) { 
            return; 
        }
        
        if (nbrs.contains(n)) { 
            return; 
        } 
        nbrs.add(n); 
    } 

    void removeNbr(Node n) { 

        nbrs.remove(n); 

    } 
    @Override 
    public String toString() { 
        return "" + label; 
    }
	public Node getMatchedWith() {
		return matchedWith;
	}
	public void setMatchedWith(Node matchedWith) {
		this.matchedWith = matchedWith;
	} 
    
    
    public void updateNode(int iter, Node parent, NodeType type) {
        this.forIteration = iter; 
        this.parent = parent; 
        this.type = type; 
    } 
    
    
	public NodeType getType() {
		return type;
	}
	public void setType(NodeType type) {
		this.type = type;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public boolean isContracted() {
		return contracted;
	}
	public void setContracted(boolean contracted) {
		this.contracted = contracted;
	}

	
	public int getForIteration() {
		return forIteration;
	}
	public void setForIteration(int forIteration) {
		this.forIteration = forIteration;
	}
	
   
}
