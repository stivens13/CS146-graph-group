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

public class MST {

    /**
     * Using Disjoint set(s), run Kruskal's algorithm on the given graph and return the MST, return
     * null if no MST exists for the graph
     * 
     * @param g
     *            The graph, g will never be null
     * @return the MST of the graph, null if no valid MST exists
     */
    public static Collection<Edge> kruskals(Graph g) {
    	if (g==null)
			return null;
    	if(g.getEdgeList().size()<(g.getVertices().size()-1)) {
    		return null;
    	}
		ArrayList<Edge> edgeList =  new ArrayList<Edge>(g.getEdgeList());
		ArrayList<Edge> finalEdges = new ArrayList<Edge>();
		DisjointSets<Vertex> disjointSet = new DisjointSets<Vertex>(g.getVertices());
		Collections.sort(edgeList);
		for(Edge min: edgeList){
			Vertex a = min.getU();
			Vertex b = min.getV();
			if (!disjointSet.sameSet(a, b)) {
				disjointSet.merge(a, b);
				finalEdges.add(min);
			}
		}
		return finalEdges;

    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning tree
     * If no MST exists, return null
     * 
     * @param g 
     * 				The graph to be processed.  Will never be null
     * @param start 
     * 				The ID of the start node.  Will always exist in the graph
     * @return the MST of the graph, null if no valid MST exists
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
    
    public static void printMST(Collection<Edge> mst) {
        Iterator<Edge> iter = mst.iterator();
        while(iter.hasNext()) {
            iter.next().printEdge();;
        }
    }

    public static void main(String [] args) {

        
        Graph graph = createGraph();
        
        // MST.prims(g, 0);
        printMST(prims(graph,0));
        System.out.println("");
        System.out.println("");
        printMST(kruskals(graph));
        
    }

    public static Graph createGraph() {

        int numOfV = 0;
        int numOfE = 0;

        Vertex u = null;
        Vertex v = null;

        Edge e;

        Collection<Edge> edges = new HashSet<Edge>();
        Set<Vertex> vertices = new HashSet<Vertex>();
        Map<Vertex, Map<Vertex, Float>> adjacencies = new HashMap<Vertex, Map<Vertex, Float>>();

        Graph g = null;
        
//        MST mst = new MST();


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

            g = new Graph(numOfV, numOfE, vertices, edges, adjacencies);

            scan.close();
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return g;

    }
    
}
    	