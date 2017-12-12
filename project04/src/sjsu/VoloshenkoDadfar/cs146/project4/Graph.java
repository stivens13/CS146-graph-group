package sjsu.VoloshenkoDadfar.cs146.project4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

	private int numOfVertices;
	private int numOfEdges;
	
	private Set<Vertex> vertices = new HashSet<Vertex>();
	private Collection<Edge> edges = new HashSet<Edge>();
	private Map<Vertex, Map<Vertex, Float>> adjacencies = new HashMap<Vertex, Map<Vertex, Float>>();

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
		
		numOfVertices = v;
		numOfEdges = e;
		
		vertices = vert;
		edges = edg;
		adjacencies = adj;
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
	
	public int getNumOfEdges() {
		
		return numOfEdges;
	}
	
	public int getNumOfVertices() {
		
		return numOfVertices;
	}


	public Collection<Edge> getEdgeList() {
		return edges;
	}

	public Set<Vertex> getVertices() {
		return vertices;
	}
	
	public Map<Vertex, Float> getAdjacencies(Vertex u) {
		return adjacencies.get(u);
	}
}