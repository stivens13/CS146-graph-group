package sjsu.VoloshenkoDadfar.cs146.project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class MST 
{

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning tree
     * If no MST exists, return null
     * 
     * @param g			The graph to be processed.  Will never be null
     * @param start 		The ID of the start node.  Will always exist in the graph
     * @return 			the MST of the graph, null if no valid MST exists
     */
     public static Collection<Edge> prims(Graph g, int start){

    	Vertex startVertex = new Vertex(start);
    	Vertex currVertex = startVertex;
    	Collection<Edge> finalEdges = new ArrayList<Edge>();
    	Collection<Edge> edgeList = g.getEdgeList();
    	ArrayList<Vertex> visited = new ArrayList<Vertex>();
    	visited.add(startVertex);
    	PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
    	Map<Vertex, Float> map = g.getAdjacencies(startVertex);
    	Set<Vertex> set = map.keySet();
    	for(Vertex v: set)
    	{
    		pq.add(new Edge(startVertex, v, map.get(v)));
    	}
    	while(visited.size()!= g.getVertices().size() && !pq.isEmpty())
    	{
    		Edge min = pq.poll();
    		if(!visited.contains(min.getU()) || !visited.contains(min.getV()))
    		{
    			finalEdges.add(min);
    			if(!visited.contains(min.getU()))
    				currVertex = min.getU();
    			else if(!visited.contains(min.getV()))
    				currVertex = min.getV();
    			map.putAll(g.getAdjacencies(currVertex));
    			visited.add(currVertex);
    			set = map.keySet();
    			for(Vertex v:set)
                {
    				if(edgeList.contains(new Edge(currVertex,v, map.get(v))))
    				{
    					pq.add(new Edge(currVertex,v, map.get(v)));
    				}
                }
    		}
    	}
    	if(pq.isEmpty())
    		return null;
    	return finalEdges;
    }

	/*
	*	method to print out the mininum spoanning tree from a graph
	*	param g		graph that featuresd the minimum spannign tree
	*/
    public static void printMST(Graph g) 
    {
        Collection<Edge> edges = g.getEdgeList();
        Iterator<Edge> iter = edges.iterator();
        while(iter.hasNext()) 
	{
            iter.next().printEdge();
        }
        
        System.out.println("");
        
        System.out.println("The weight of the MST is: " + g.getGraphWeight());
    }
    
	/*
		method to print out the mininum spoanning tree from a collection of edges
	*	param mst	/collection of edges left after the algorithms
	*/
    public static void printMST(Collection<Edge> mst) 
    {	
    	float weights = 0.0f;
    	int numOfEdges = 0;
    		
        Iterator<Edge> iter = mst.iterator();
        while(iter.hasNext()) 
	{
            iter.next().printEdge();
            numOfEdges++;
        }
        
        System.out.println("");
        
	    for(Edge e: mst) 
	    {
		weights += e.getWeight();
	    }
        
	System.out.println("The weight of the MST is: " + weights);		
	System.out.println("Number of edges: " + numOfEdges);
    }	

	/**
	*	driver method - creating graphs and running them with different algorithms
	*	returns the MST of the given graph
	**/
    public static void main(String [] args) 
    {
        Graph g = createGraph();
        System.out.println("Prim's");
        System.out.println("");
        printMST(prims(g,0));
        System.out.println("");
        
        Graph graph = new_algorithm(createGraph());
        
        graph = new_algorithm(graph);
        
        printMST(graph.getEdgeList());
    }
    
    
	/*
	*	Creates a new empty graph which is populated with the given text file
	*	return g	fillled out graph
	*/
    public static Graph createGraph() 
    {

        int numOfV = 0;
        int numOfE = 0;

        Vertex u = null;
        Vertex v = null;

        Edge e;

        Collection<Edge> edges = new HashSet<Edge>();
        Set<Vertex> vertices = new HashSet<Vertex>();
        Map<Vertex, Map<Vertex, Float>> adjacencies = new HashMap<Vertex, Map<Vertex, Float>>();
        
        Map<Float, Edge> unsortedEdges = new HashMap<Float, Edge>();

        Graph g = null;

        File file = new File("tinyEWG.txt");
        
        try {
            Scanner scan = new Scanner(file);

            float weight = 0;

            if(scan.hasNext()) {
                numOfV = Integer.parseInt(scan.nextLine());
            }

            if(scan.hasNext()) {
                numOfE = Integer.parseInt(scan.nextLine());
            }
            
            while (scan.hasNext()) {

                
                    
                String line = scan.nextLine();
                if(line != null && !line.equals("")) {
                    String[] splited = line.split("\\s+");
                    u = new Vertex(Integer.parseInt(splited[0]));
                    v = new Vertex(Integer.parseInt(splited[1]));
                    weight = Float.valueOf(splited[2]);
                }

                e = new Edge(u, v, weight);

                edges.add(e);
                unsortedEdges.put(weight, e);
                vertices.add(u);
                vertices.add(v);

                if (!adjacencies.containsKey(u)) {
                    adjacencies.put(u, new HashMap<Vertex, Float>());
                }
                
                adjacencies.get(u).put(v, (float) weight);
                
                if (!adjacencies.containsKey(v)) {
                    adjacencies.put(v, new HashMap<Vertex, Float>());
                }

                adjacencies.get(v).put(u, (float) weight);

            }

            g = new Graph(numOfV, numOfE, vertices, edges, adjacencies, unsortedEdges);

            scan.close();
            
            
        } 
	 catch (FileNotFoundException ex) 
	 {
            ex.printStackTrace();
       	 }

        return g;

    }
    
	/*
	*	reverse search delete algorithm - abandoned version - not used anymore
	*	return g	graph with the heaviest edges removed (given the graph stays acyclic)
	*/
    public static Graph new_algorithm(Graph graph) 
    {
	System.out.println("New algorithm");
    	
        Map<Float, Edge> sortedEdges = graph.getSortedEdges();

        Iterator<Map.Entry<Float, Edge>> it = sortedEdges.entrySet().iterator();
        
        while( it.hasNext() ) {
        	
        		Map.Entry<Float, Edge> entry = it.next();
        	
        		float weight = entry.getKey();
        		Vertex u = entry.getValue().getU();
        		Vertex v = entry.getValue().getV();
        		if( ( graph.isCyclic(v) ) && graph.isCyclic(u) ) {
        			graph.removeEdgeFromAdjecencyList(v, u);
        			graph.removeEdgeFromSortedEdges(weight);
        			it.remove();
        		}

        }
        
        return graph;
    }
    
	/*
	*	modified reverse search delete algorithm, works with a while loop 
	*	return g	graph with the heaviest edges removed (given the graph stays acyclic)
	*/
    public static Graph new_algorithm_modified(Graph graph) {
    	
		System.out.println("New algorithm");
	
    Map<Float, Edge> sortedEdges = graph.getSortedEdges();
     
    Map<Vertex, Map<Vertex, Float>> adjacencies = graph.getAdjacenciesList();
    Collection<Edge> edges = graph.getEdgeList();
    
    Iterator<Map.Entry<Float, Edge>> it = sortedEdges.entrySet().iterator();
    
    while( it.hasNext() ) {
    	
    		Map.Entry<Float, Edge> entry = it.next();
    	
    		float weight = entry.getKey();
    		Vertex u = entry.getValue().getU();
    		Vertex v = entry.getValue().getV();

	    	adjacencies.get(v).remove(u);
		adjacencies.get(u).remove(v);
		
		if(adjacencies.get(v).isEmpty() || adjacencies.get(u).isEmpty()) {
			adjacencies.get(v).put(u, (float) weight);
			adjacencies.get(u).put(v, (float) weight);
		}
		
		else {
			edges.remove(new Edge(u, v, weight));
			it.remove();
		}
   
    		
    		graph.setAdjacenciesList(adjacencies);
    		graph.setEdgesList(edges);

    }
    
    return graph;
}
    
}
    	
