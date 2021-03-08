package com.example.graph;

import java.util.ArrayList;
import java.util.HashMap;


public class EdmondBlossomMaxMatch {
	
    static boolean verbose = true;
	 //list of current blossoms stored in a map 
    HashMap<Node, Blossom> currBlossoms = new HashMap<Node, Blossom>();  
    // current list of Blossoms in a List 
    ArrayList<Blossom> currB = new ArrayList<Blossom>();
    // Blossom start label
    private int bLabel = -2;
    private Node currFreeNode ; 
   
	public int getbLabel() {
		return bLabel;
	}
	public void setbLabel(int bLabel) {
		this.bLabel = bLabel;
	}
	public Node getCurrFreeNode() {
		return currFreeNode;
	}
	public void setCurrFreeNode(Node currFreeNode) {
		this.currFreeNode = currFreeNode;
	}
	
	
	 
}
