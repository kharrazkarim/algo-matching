package com.example.graph;

import java.util.List;

public class Blossom {
	
	private Node root; 
    private List<Node> cycle; 
    private Node blossomAlias;
    
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	public Node getBlossomAlias() {
		return blossomAlias;
	}
	public void setBlossomAlias(Node blossomAlias) {
		this.blossomAlias = blossomAlias;
	}
	public List<Node> getCycle() {
		return cycle;
	}
	public void setCycle(List<Node> cycle) {
		this.cycle = cycle;
	}
}
