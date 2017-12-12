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
	 * @param input
	 */
	
	
	// New implementation, possibly to be improved
	public Graph(int v, int e, Set<Vertex> vert, Collection<Edge> edg, Map<Vertex, Map<Vertex, Float>> adj ) {
		
		V = v;
		E = e;
		
		vertices = vert;
		edges = edg;
		adjacencies = adj;
		isCyclic();
	}
	
public Graph(int v, int e, Set<Vertex> vert, Collection<Edge> edg, Map<Vertex, Map<Vertex, Float>> adj, Map<Float, Edge> unst ) {
		
		V = v;
		E = e;
		
		vertices = vert;
		edges = edg;
		adjacencies = adj;
//		unsortedEdges = unst;
		
		sortedEdges = sortEdges(unst);
//		isCyclic();
	}
	 
	
	// Old implementation, can't be used, but let it be for now
	public Graph(String input) {
		Scanner scan = new Scanner(input);

		int count = scan.nextInt();
		for (int i = 0; i < count; i++) {
			Vertex u = new Vertex(scan.nextInt());
			Vertex v = new Vertex(scan.nextInt());
			int weight = scan.nextInt();
			vertices.add(u);
			vertices.add(v);
			edges.add(new Edge(u, v, weight));
			
			if (!adjacencies.containsKey(u)) {
				adjacencies.put(u, new HashMap<Vertex, Float>());
			}
			adjacencies.get(u).put(v, (float) weight);
			
			if (!adjacencies.containsKey(v)) {
				adjacencies.put(v, new HashMap<Vertex, Float>());
			}
			
			adjacencies.get(v).put(u, (float) weight);
		}

		scan.close();
	}
	
	public Boolean isCyclicUtil(int v, Boolean visited[], int parent)
    {
        // Mark the current node as visited
        visited[v] = true;
        int i;
 
        // Recur for all the vertices adjacent to this vertex
        Map<Vertex, Float> adjs = getAdjacencies(v);
        Iterator it =  adjs.entrySet().iterator();
        while (it.hasNext())
        {
        		Map.Entry<Vertex, Float> pair = (Map.Entry<Vertex, Float>) it.next();
        		i = ( (Vertex) pair.getKey() ).getId();
//        		float weight = pair.getValue();
//            i = it.next();
 
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[i])
            {
                if (isCyclicUtil(i, visited, v))
                    return true;
            }
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent) {
//            		backedges.add( new Edge (new Vertex(i), new Vertex(parent), weight));
                return true;
            }
        }
         return false;
    }
 
    // Returns true if the graph contains a cycle, else false.
    public Boolean isCyclic()
    {
        // Mark all the vertices as not visited and not part of
        // recursion stack
        int x = 0;
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
       
 
        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int u = 0; u < V; u++)
            if (!visited[u]) // Don't recur for u if already visited
                if (isCyclicUtil(u, visited, -1))
                    return true;
 
         return false;
    }
    
    public Boolean isCyclic(int v)
    {
        // Mark all the vertices as not visited and not part of
        // recursion stack
        int x = 0;
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
        
        return isCyclicUtil(v, visited, -1);
 
        // Call the recursive helper function to detect cycle in
        // different DFS trees
//        for (int u = 0; u < V; u++)
//            if (!visited[u]) // Don't recur for u if already visited
//                if (isCyclicUtil(u, visited, -1))
//                    return true;
// 
//         return false;
    }

//	public void outputGDF(String fileName)
//    {
//        HashMap<Vertex, String> idToName = new HashMap<Vertex, String>();
//        try {
//            FileWriter out = new FileWriter(fileName);
//            int count = 0;
//            out.write("nodedef> name,label,style,distance INTEGER\n");
//            // write vertices
//            for (Vertex v: vertices.values())
//            {
//                String id = "v"+ count++;
//                idToName.put(v, id);
//                out.write(id + "," + escapedVersion(v.name));
//                out.write(",6,"+v.distance+"\n");
//            }
//            out.write("edgedef> node1,node2,color\n");
//            // write edges
//            for (Vertex v : myVertices.values())
//                for (Vertex w : myAdjList.get(v))
//                    if (v.compareTo(w) < 0)
//                    {
//                        out.write(idToName.get(v)+","+
//                                idToName.get(w) + ",");
//                        if (v.predecessor == w ||
//                                w.predecessor == v)
//                            out.write("blue");
//                        else
//                            out.write("gray");
//                        out.write("\n");
//                    }
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    
    public Collection<Edge> getBackEdges() {
    		return backedges;
    }
	
	public int getNumOfEdges() {
		
		return E;
	}
	
	public int getNumOfVertices() {
		
		return V;
	}
	
	public void removeEdgeFromAdjecencyList(Vertex v, Vertex u) {
		adjacencies.get(v).remove(u);
		adjacencies.get(u).remove(v);
	}


	public Collection<Edge> getEdgeList() {
		return edges;
	}

	public Set<Vertex> getVertices() {
		return vertices;
	}
	
	public Map<Vertex, Float> getAdjacencies(int u) {
		Vertex v = new Vertex(u);
		return adjacencies.get(v);
	}
	
	public Map<Vertex, Float> getAdjacencies(Vertex u) {
		return adjacencies.get(u);
	}
	
	public Map<Vertex, Map<Vertex, Float>> getAdjacenciesList() {
		return adjacencies;
	}
	
	public Map<Float, Edge> getSortedEdges() {
		return sortedEdges;
	}
	
	public Map<Float, Edge> sortEdges(Map<Float, Edge> unst) {
		
		Map<Float, Edge> treeMap = new TreeMap<Float, Edge>(
                new Comparator<Float>() {

                    @Override
                    public int compare(Float o1, Float o2) {
//                    		return o1.getChange() < o2.getChange() ? -1 
//                    			     : o1.getChange() > o2.getChange() ? 1 
//                    			    	     : 0;
                        return o2.compareTo(o1);
                    }

                });
		
		treeMap.putAll(unst);
		
		return treeMap;
	}
	
	public void removeEdgeFromSortedEdges(float weight) {
		Edge e = null;
		if(sortedEdges.containsKey(weight)) {
			e = sortedEdges.get(weight);
//			sortedEdges.remove(weight);
			if(edges.contains(e))
				edges.remove(e);
		}
		
	}
}