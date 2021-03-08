package com.example.graph;

public interface GraphService {
	
	
	Graph constructGraph(String filename);
	void printGraph(Graph g);
	void edmondExec(Graph g);
	void greedyMatching(Graph g);
	Graph constractRandomGraph(int nbnodes, int nbedges);
	Graph visaulMatchedGraph(Graph g);
	
}
