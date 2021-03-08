package com.example.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GraphSystemGraphService implements GraphService {
	
@Override
public Graph visaulMatchedGraph(Graph g) {
	
	Graph visual = new Graph();
	ArrayList<Edge>edges= new ArrayList<Edge>();
	ArrayList<Node>nodes= new ArrayList<Node>();

	for(int i=0;i<g.getNbnodes();i++) {
		
		if(g.getNodes().get(i).getMatchedWith() !=null ) {
			 nodes.add(g.getNodes().get(i));
			 Edge e = new Edge();
             e.setSource(g.getNodes().get(i).getLabel());
             e.setTarget(g.getNodes().get(i).getMatchedWith().getLabel());
             edges.add(e);
            
        
		}
		
	}
	visual.setEdges(edges);
	visual.setNodes(nodes);
	System.out.println("visual edges size"+ visual.getEdges().size());
	System.out.println("visual nodes size"+ visual.getNodes().size());

	return visual;
}

	@Override
	public Graph constructGraph(String filename) {
		
		Graph g = new Graph(); 
		ArrayList<Edge>edges= new ArrayList<Edge>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
	        int nodesCount = Integer.parseInt(br.readLine());
	        int edgesCount = Integer.parseInt(br.readLine());
	        g.setNbnodes(nodesCount);
	        g.setNbedges(edgesCount);
	        g.initGraph(); 
	        
	            for (int k = 0; k < edgesCount; k++) {
	                String[] strArr = br.readLine().split(" "); 
	                int u = Integer.parseInt(strArr[0]);
	                int v = Integer.parseInt(strArr[1]);
	                System.out.println("( "+u+", "+v+" )");
	                Edge e = new Edge();
	               
	                e.setSource(u);
	                e.setTarget(v);
	                edges.add(e);
	                g.createNode(u, v);
	                
	            }
	            g.setEdges(edges);
			br.close();
		  } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	   
		return g;
	}

	@Override
	public void printGraph(Graph g) {
		int i=0;
		int j=0;
		System.out.println("Nb nodes :"+ g.getNbnodes());
		System.out.println("Nb edges :"+ g.getNbedges());
		while (i < g.getNodes().size()) {
			System.out.println("Noeud : "+ g.getNodes().get(i).getLabel());
			 LinkedHashSet<Node> lhs = g.getNodes().get(i).getNbrs();
			 Iterator<Node> itr = lhs.iterator();
		        while(itr.hasNext()){
		            System.out.println("Voisins "+itr.next().toString());
		        }
		
			i++;
		}
	}

	@Override
	public void edmondExec(Graph g) {
	    
		creatMAlternatingTrees(g.getNodes());
        printMatchings(g); 		
	}
	
	 /** 
     * Expand the blossom, update the neighbors and the parent pointers 
     *  
     * @param blossom 
     * @param stem 
     * @param antennae 
     * @param iter 
     * @return 
     */ 
    private Node expandBlossom(Blossom blossom, Node stem, Node antennae, int iter) {
        List<Node> cycle = blossom.getCycle(); 
        Node newRoot = null; 
        Node newOut = null; 

        for (Node node : cycle) { 
            for (Node nbr : node.getNbrs()) { 
                if (nbr.isContracted()) 
                    continue; 
                nbr.removeNbr(blossom.getBlossomAlias()); 
                if ((newRoot == null) && (nbr == stem)
                        && (node == blossom.getRoot())) { 
                    newRoot = node; 
                } else if ((newOut == null) && (nbr == antennae)) {
                    newOut = node; 
                } 
            } 
            node.setForIteration(iter);
        } 
        for (Node node : cycle) { 
            node.setContracted(false);
        } 

        if (newRoot == null)
            newRoot = blossom.getRoot(); 

        if ((blossom.getRoot() != null)
                && (blossom.getRoot().getMatchedWith() != null) 
                && (blossom.getRoot().getMatchedWith().getMatchedWith()== blossom.getBlossomAlias())) { 
            blossom.getRoot().setMatchedWith(blossom.getRoot());
        } 

        antennae.setParent(newOut); 

        if (newRoot == blossom.getRoot()) { 
            if (newOut.getMatchedWith().getParent() == newOut) { 
                Collections.reverse(cycle); 
                Node n = null; 
                for (int i = 0; i < cycle.size() - 1; i++) {
                    n = cycle.get(i); 
                    n.setParent(cycle.get(i + 1)); 
                } 
            } 
        } else { 

            if (newRoot.getParent() == newRoot.getMatchedWith()) { 
                Collections.reverse(cycle); 
                Node n = null; 
                for (int i = 0; i < cycle.size() - 1; i++) {
                    n = cycle.get(i); 
                    n.setParent( cycle.get(i + 1)); 
                } 
            } 
        } 
        if (newRoot != null)
            newRoot.setParent(stem); 
        return newOut; 
    } 

    
	
	 /** 
     * Update the alternating path , with new matchings, expanding blossoms 
     * along the path 
     *  
     * @param pathNode 
     * @param iter 
     */ 
    void updateAugmentingPath(Node pathNode, int iter,HashMap<Node, Blossom> currBlossoms) {

    	  Node n = pathNode; 
          Node np = null; 
          Node npp = null; 
          Blossom blossom; 

          while (n != null) { 
              np = new Node();
              np= n.getParent();
              npp= new Node();	  
              npp = np.getParent(); 

              if (np.getLabel() < -1) { 
                  blossom = currBlossoms.get(np); 
                  n.setParent(expandBlossom(blossom, npp, n, iter));
                  while (n.getParent().getLabel() < -1) {

                      np = n.getParent(); 
                      blossom = currBlossoms.get(np); 
                      npp = np.getParent(); 
                  } 
                  np = n.getParent(); 
                  npp = np.getParent(); 
              } 
              if ((npp != null) && (npp.getLabel() < -1)) {
                  blossom = currBlossoms.get(npp); 
                  np.setParent(expandBlossom(blossom, npp.getParent(), np, iter));
                  while (np.getParent().getLabel() < -1) {
                      npp = np.getParent(); 
                      blossom = currBlossoms.get(np); 
                  } 
                  npp = np.getParent(); 
              } 
              n.setMatchedWith(np); 
              np.setMatchedWith(n); 
              n = npp; 
          } 
    } 
	/** 
     * Contract the blossom into a Blossom structure, updating the nbrs of the 
     * nodes comprising the odd cycle 
     */ 
    Blossom contractBlossom(List<Node> cycle, Node root, int iter,int bLabel) {

        Node bALias = new Node(); 
        Node node = null; 
        bALias.setForIteration(iter); 

        bALias.setType(NodeType.EVEN); 
        bALias.setLabel(bLabel--);

        Blossom blossom = new Blossom(); 
        blossom.setCycle(cycle); 
        blossom.setRoot(root); 
        blossom.setBlossomAlias(bALias); 
        for (int i = 0; i < cycle.size(); i++) {
            node = cycle.get(i); 
            node.setContracted(true);
        } 

        for (int i = 0; i < cycle.size(); i++) {
            node = cycle.get(i); 
            for (Node nbr : node.getNbrs()) { 
                if (nbr.isContracted()) 
                    continue; 

                nbr.addNbr(bALias); 
                bALias.addNbr(nbr); 
            } 
            node.setForIteration(iter); 
        } 

        if (root.getMatchedWith() != null) {
            bALias.setMatchedWith(root.getMatchedWith()); 
            root.getMatchedWith().setMatchedWith(bALias);
        } 

        return blossom; 
    } 

    
	
	/** 
     * Find the Odd cycle that can be contracted into a blossom 
   
     */ 
    private ArrayList<Node> findBlossom(Node node, Node nbr, int iter, Node currFreeNode) {

        ArrayList<Node> cycle = new ArrayList<Node>(); 
        Node root = node; 
        Node origNbr = nbr; 

        if ((root == currFreeNode) || (nbr == currFreeNode)) {
            return null; 
        } 
        cycle.add(root); 

        while ((nbr != null) && (nbr != root)) { 
            cycle.add(nbr); 
            nbr = nbr.getParent(); 
        } 
        root.setParent(origNbr); 
        cycle.add(root); 
        return cycle; 
    } 

	 /** 
     * Find alternating paths recursively. Node parameter will only be Even nodes. 
	 * @param bLabel 
	 * @param currB 
	 * @param currBlossoms 

     */ 
    private Node createMAlternatingTree(Node node, int iter, Node parent,Node currFreeNode, HashMap<Node,Blossom> currBlossoms, ArrayList<Blossom> currB, int bLabel) {

        ArrayList<Node> cycle; 
        Node nodeMatched = null; 
        Blossom blossom = null; 
        node.updateNode(iter, parent, NodeType.EVEN); 
        LinkedList<Node> nbrsQ = new LinkedList<Node>(node.getNbrs()); 
        Node nbr = null; 
        while ((nbr = nbrsQ.poll()) != null) { 
            if (nbr.isContracted()) 
                continue; 
            if ((nbr == node)) 
                continue; 
            if (nbr.getForIteration() != iter) { 
                if (nbr.getType() == NodeType.FREE) { 
                	nbr.updateNode(iter, node, NodeType.ODD); 
                    return nbr; 
                } else { 
                    nbr.updateNode(iter, node, NodeType.ODD); 
                    nodeMatched = nbr.getMatchedWith(); 
                    Node nodeInPath = null; 
                    nodeInPath = createMAlternatingTree(nodeMatched, iter, nbr,currFreeNode, currBlossoms, currB, bLabel); 

                    if (nodeInPath != null) {
                        return nodeInPath; 
                    } else { 
                        node.updateNode(iter, parent, NodeType.EVEN); 
                        continue; 
                    } 
                } 
            } 
            if (nbr.getType() == NodeType.ODD) { 
                continue; 
            } 

            if (nbr.getType() == NodeType.EVEN) { 
                cycle = findBlossom(nbr, node, iter,currFreeNode); 
                if (cycle == null) { // root.parent == null, we can ignore this
                    continue; 
                } 
                blossom = contractBlossom(cycle.subList(0, cycle.size()),
                        cycle.get(cycle.size() - 1), iter, bLabel); 
                currBlossoms.put(blossom.getBlossomAlias(), blossom); 
                currB.add(blossom); 
                return blossom.getBlossomAlias(); 
            } 
        } 
        return null; 
    } 
    
    /** 
     * Expand all the blossoms. Will be called when no alternating path through 
     * the existing blossoms can be found 
 
     */ 
    private void expandAllBlossoms(int iter, ArrayList<Blossom>currB , HashMap<Node, Blossom> currBlossoms ) {
        Blossom blossom = null; 

        for (int i = currB.size() - 1; i >= 0; i--) { 
            blossom = currB.get(i); 
            List<Node> cycle = blossom.getCycle(); 
            for (Node node : cycle) { 
                node.setContracted(false); 
                for (Node nbr : node.getNbrs()) { 
                    if (nbr.isContracted()) 
                        continue; 
                    nbr.removeNbr(blossom.getBlossomAlias()); 
                } 
                node.setForIteration(iter);  
            } 
            if ((blossom.getRoot() != null)
                    && (blossom.getRoot().getMatchedWith() != null)
                    && (blossom.getRoot().getMatchedWith().getMatchedWith() == blossom.getBlossomAlias())) { 
                blossom.getRoot().getMatchedWith().setMatchedWith(blossom.getRoot());
            } 
        } 
        currB.clear(); 
        currBlossoms.clear(); 
    } 
	
	/** 
     * Take all the freenodes and find an alternating path with it If found then update the augmenting path with new matchings 
 
     */ 
	
    private void creatMAlternatingTrees(ArrayList<Node> freeNodes) {
    	
    	//list of current blossoms stored in a map 
        HashMap<Node, Blossom> currBlossoms = new HashMap<Node, Blossom>();  
        // current list of Blossoms in a List 
        ArrayList<Blossom> currB = new ArrayList<Blossom>();  
        int bLabel = -2; // Blossom start label
        Node currFreeNode = null; 
        int iter = 1; 
        Node augNode = null; 
        
        for (Node freeNode : freeNodes) { 
            if (freeNode.getType() != NodeType.FREE) 
                continue; 
            		currFreeNode = freeNode; 
            if ((augNode = createMAlternatingTree(freeNode, iter, null,currFreeNode, currBlossoms, currB, bLabel)) != null) {
                
            	while ((augNode != null) && (augNode.getLabel() < -1))// blossom 
                { 
                    augNode = createMAlternatingTree(freeNode, ++iter, null,currFreeNode,currBlossoms,currB,bLabel);
                    
                } 
                if (augNode != null) {
                    updateAugmentingPath(augNode, iter, currBlossoms); 
                } 
            } 
            if (augNode == null) {
            	
            	freeNode.updateNode(iter, null, NodeType.FREE);
            } 
            expandAllBlossoms(iter, currB, currBlossoms); 
          
            iter++; 
        } 
    } 

	
	private void printMatchings(Graph g) {
		
	        StringBuilder strBuild = new StringBuilder(); 
	        int matches = 0; 
	        for (Node node : g.getNodes()) { 
	            if (node.getMatchedWith() == null)
	                continue; 
	            strBuild.append("Matched " + node.getLabel() + " : " 
	                    + node.getMatchedWith().getLabel() + "\t"); 
	            matches++; 
	        } 
	        strBuild.append("\n\nTotal Nodes Matched " + (matches) + " \t"); 
	        System.out.println(strBuild); 
	  
		
	}
	@Override
	public void greedyMatching(Graph g){
		ArrayList<Node> matched_nodes = new ArrayList<Node>();
		
		for (int i=0 ; i< g.getNbnodes();i++) {
			LinkedHashSet<Node> neighbors = g.getNodes().get(i).getNbrs();
			Iterator<Node> itr = neighbors.iterator();
			
		        while(itr.hasNext() && g.getNodes().get(i).getMatchedWith()==null){
		        	
		        	Node n = itr.next();

		        	if (! matched_nodes.contains(n)) {
		        		matched_nodes.add(g.getNodes().get(i));
		        		matched_nodes.add(n);
		        		g.getNodes().get(i).setMatchedWith(n);
		        		g.getNodes().get(n.getLabel()).setMatchedWith(g.getNodes().get(i));
		        	}
		        	
		        	
		        }
		        
	
		}
		printMatchings(g);
	}
	
	

@Override
public Graph constractRandomGraph(int nbnodes, int nbedges) {
	boolean test=false;
	Graph g= new Graph();
	g.setNbnodes(nbnodes);
	g.setNbedges(nbedges);
    g.initGraph();
    int k=0;
   	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	ArrayList<Edge>edges= new ArrayList<Edge>();
	int u = -1 ;
	int v= -1;
	while(!test) {
		u = (int) (Math.random() * (nbnodes ));
	    v = (int) (Math.random() * (nbnodes ));
	    if (u!=v) test=true; 
	}
   
    Edge e = new Edge();
    
    e.setSource(u);
    e.setTarget(v);
    edges.add(e);
    
    g.createNode(u, v); 
	 map.put(u, v);
	 map.put(v, u);
	 
   while(k<nbedges-1) {
         u = (int) (Math.random() * (nbnodes ));
         v = (int) (Math.random() * (nbnodes ));
    
        System.out.println("( "+u+","+v+")");
       
        if(! (map.containsKey(u) && map.get(u)== v ))
        {	
        	if (! (map.containsKey(v) && map.get(v)== u ))
            {
        		Edge e2 = new Edge();
        	    
        	    e2.setSource(u);
        	    e2.setTarget(v);
        	    edges.add(e2);
        	    
        	 g.createNode(u, v); 
        	 map.put(u, v);
        	 map.put(v, u);
             k++;
            }
        }
       
    
   }
   	g.setEdges(edges);
    printGraph(g);
	return g;
}
}