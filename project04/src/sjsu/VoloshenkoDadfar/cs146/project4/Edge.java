package sjsu.VoloshenkoDadfar.cs146.project4;

// public class Edge implements Comparable<Edge> { 
public class Edge implements Comparable<Edge> {

	private Vertex u, v;
	private float weight;

	/**
	 * Comparable edge class based on weight. Order of u and v does not matter.
	 * 
	 * @param u
	 * @param v
	 * @param weight
	 */
	public Edge(Vertex u, Vertex v, float weight) {
		setU(u);
		setV(v);
		setWeight(weight);
	}

	@Override
	public int hashCode() {
		
		return (u == null ? 0 : u.hashCode()) ^ (v == null ? 0 : v.hashCode())
				^ (int) weight * 10000;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge) {
			Edge e = (Edge) o;
			return weight == e.weight
					&& ((u.equals(e.u) && v.equals(e.v)) || (u.equals(e.v) && v
							.equals(e.u)));
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Edge e) {
		if(weight == e.getWeight() )
			return 0;
		else if(weight - e.getWeight() > 0) 
			return 1;
		else return -1;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Vertex getU() {
		return u;
	}

	public void setU(Vertex u) {
		this.u = u;
	}

	public Vertex getV() {
		return v;
	}

	public void setV(Vertex v) {
		this.v = v;
	}
}
