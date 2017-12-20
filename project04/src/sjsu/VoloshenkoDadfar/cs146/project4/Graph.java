package sjsu.VoloshenkoDadfar.cs146.project4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Comparator;

public class Graph {

	private int V;
	private int E;
	
	private Set<Vertex> vertices = new HashSet<Vertex>();
	private Collection<Edge> edges = new HashSet<Edge>();
	private Map<Vertex, Map<Vertex, Float>> adjacencies = new HashMap<Vertex, Map<Vertex, Float>>();
	private Collection<Edge> backedges = new LinkedList<Edge>();
	
	
	Map<Float, Edge> sortedEdges = new HashMap<Float, Edge>();

	/**
	 * A class representing a graph, can be built from an edge list
	 * 
	 * The first int is the number of edges, following this are triples of
	 * integers for each edge representing vertex u, vertex v, and the weight
	 * 
	 * @param v		vertice count
	 * @param e		edge count
	 * @param vert		set of vertices	
	 * @param edg		collection of edges
	 * @param adj		adjacency list of neighbors
	 */

	// New implementation, possibly to be improved
	public Graph(int v, int e, Set<Vertex> vert, Collection<Edge> edg, Map<Vertex, Map<Vertex, Float>> adj ) {
		
		V = v;
		E = e;
		
		vertices = vert;
		edges = edg;
		adjacencies = adj;
	}
	
	/**
	 * checks the graph for cycles starting/including this point
	 * 
	 * @param v		vertrex to consider at the point
	 * @param visited	list to 
	 * @param parent	parent of the considered vertex
	 */
	public Boolean isCyclicUtil(Vertex v, Map<Vertex, Boolean> visited, Vertex parent)
    {
        // Mark the current node as visited
        visited.put(v, true);
        
        Vertex i;
 
        // Recur for all the vertices adjacent to this vertex
        Map<Vertex, Float> adjs = getAdjacencies(v);
        Iterator it =  adjs.entrySet().iterator();
        
        while (it.hasNext())
        {
        		Map.Entry<Vertex, Float> pair = (Map.Entry<Vertex, Float>) it.next();
        		i = (Vertex) pair.getKey();
 
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited.get(i))
            {
                if (isCyclicUtil(i, visited, v))
                    return true;
            }
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent) 
	    {
                return true;
            }
        }
         return false;
    }
	/**
	 * checks the graph for disjointness
	 * 
	 * @param v		vertrex to consider
	 */
	public Boolean isDisjoint(Vertex v) {
		
		if(adjacencies.get(v).isEmpty())
			return true;
		else return false;
	}
    
	/**
	 * checks the graph for cycles starting/including this point
	 * 
	 * @param v		vertrex to consider at the point
	 */
    public Boolean isCyclic(Vertex v)
    {
        // Mark all the vertices as not visited and not part of
        // recursion stack
    	
    		Map<Vertex, Boolean> visited = new HashMap<>();
    		for(Vertex u: vertices) {
    			visited.put(u, false);
    		}
    		
		if( isCyclicUtil(v, visited, null) )
			return true;
    	
    		return false;
    }

 tStackTrace();

    /**
	 * returns the backedges of the graoh
	 
	 * return	collection of back edges
	 */
    public Collection<Edge> getBackEdges() {
    		return backedges;
    }
	
	 /**
	 * returns the number of the edges in the graoh
	 * return	number of edges
	 */
	public int getNumOfEdges() {
		
		return E;
	}
	
	/**
	 * returns the number of the vertices in the graoh
	 * return	number of verticesd
	 */
	public int getNumOfVertices() {
		
		return V;
	}
	
	/**
	 * helped method to remove a selected edge (two vertices)
	 * from adjacency lists of eachother
	 */
	public void removeEdgeFromAdjecencyList(Vertex v, Vertex u) {
		adjacencies.get(v).remove(u);
		adjacencies.get(u).remove(v);
	}

	/**
	 * returns the list of the edges in the graoh
	 * return	list of edges
	 */
	public Collection<Edge> getEdgeList() {
		return edges;
	}

	/**
	 * returns the lidt of the vertices in the graoh
	 * return	list of vertices
	 */
	public Set<Vertex> getVertices() {
		return vertices;
	}
	
	/**
	 * returns the adjacency list for the current vertex - works with integer ids
	 * param u 	the id of the vertex
	 * return	adjacency list of the vertex
	 */
	public Map<Vertex, Float> getAdjacencies(int u) {
		Vertex v = new Vertex(u);
		return adjacencies.get(v);
	}
	
	/**
	 * returns the adjacency list for the current vertex - works with vertices
	 * param u 	the vertex
	 * return	adjacency list of the vertex
	 */
	public Map<Vertex, Float> getAdjacencies(Vertex u) {
		return adjacencies.get(u);
	}
	
	/**
	 * returns the adjacency list for the graoh
	 * return	adjacency list of the graph
	 */
	public Map<Vertex, Map<Vertex, Float>> getAdjacenciesList() {
		return adjacencies;
	}
	
	/**
	 * returns the list of the sdorted edges of the graoh
	 * return	sorted edges list of the graph
	 */
	public Map<Float, Edge> getSortedEdges() {
		return sortedEdges;
	}
	
	/**
	 * method to sort the edges by weight 
	 * param unst	unsorted edge list
	 * return	sortef list of the edges of the graph
	 */
	public Map<Float, Edge> sortEdges(Map<Float, Edge> unst) {
		
		Map<Float, Edge> treeMap = new TreeMap<Float, Edge>(
                new Comparator<Float>() {

                    @Override
                    public int compare(Float o1, Float o2) 
		    {
                        return o2.compareTo(o1);
                    }

                });
		
		treeMap.putAll(unst);
		
		return treeMap;
	}
	
	/**
	 * removes the edge with the given weight from the graph 
	 * param weight 	weight of the edge to be removed
	 */
	public void removeEdgeFromSortedEdges(float weight) {
		Edge e = null;
		if(sortedEdges.containsKey(weight)) {
			e = sortedEdges.get(weight);
			if(edges.contains(e))
				edges.remove(e);
		}
		
	}
	
	/**
	 * sets the adjacency list of the graph from a given h the given weight from the graph 
	 * param adj 	adjacency list for the graph
	 */
	public void setAdjacenciesList(Map<Vertex, Map<Vertex, Float>> adj) {
		adjacencies = adj;
	}
	
	/**
	 * sets the edgelist of the graph from the given list
	 * param edg 	collection of edges
	 */
	public void setEdgesList(Collection<Edge> edg) {
		edges = edg;
	}
	
	/**
	 * removes the edge with the given weight from the graph 
	 * return	the weight of the entire graph
	 */
	public float getGraphWeight() {
		
		float sum = 0.0f;
		
		for(Edge e: edges) {
			sum += e.getWeight();
		}
		
		return sum;
	}
}
