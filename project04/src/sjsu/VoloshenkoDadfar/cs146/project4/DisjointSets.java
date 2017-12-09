package sjsu.VoloshenkoDadfar.cs146.project4;

import java.util.HashMap;
import java.util.Set;

public class DisjointSets<T> {

	/**
	 * @param setItems
	 *            the initial items (sameSet and merge will never be called with
	 *            items not in this set, this set will never contain null
	 *            elements)
	 */
	
	private final HashMap<T, Node> disjointedSubtree = new HashMap<T, Node>();
	
	public DisjointSets(Set<T> setItems) {
		for (T item : setItems) 
		{
            disjointedSubtree.put(item, new Node(null, 0));
		}
	
	}
	
	private class Node {
         T parent;
         int l;
         Node(T parent, int rank) {
             this.parent = parent;
             this.l = rank;
         }
     }
         
	

	/**
	 * @param u
	 * @param v
	 * @return true if the items given are in the same set, false otherwise
	 */
	public boolean sameSet(T u, T v) {
		return findSet(u).equals(findSet(v));
	}
	
	public T findSet(T item) {
        Node n = disjointedSubtree.get(item);
        if (n.parent == null) {
        	return item;
        } else {
        	n.parent = findSet(n.parent);
            return n.parent;
                
        }
	 }

	/**
	 * merges the sets u and v are in, do nothing if they are in the same set
	 * You are required to implement the following in this method:
	 * 	Path compression: every node points to its root
	 *  Merge by rank: Let the rank (estimate tree depth) of each set initially be 0. 
	 *  When merging to set with different ranks, make the smaller ranked root point to the larger root.
	 *  If the two ranks are the same, choose one to point to the other, and increment the rank of the new set
	 *
	 * @param u
	 * @param v
	 */
	public void merge(T u, T v) {
		T u1 = findSet(u); 
		T v1 = findSet(v);
        if (u1.equals(v1)) 
        	return ;
        Node subTreeU1 = disjointedSubtree.get(u1); 
        Node subTreeV1 = disjointedSubtree.get(v1);
        if (subTreeV1.l >= subTreeU1.l){
        	subTreeU1.parent = v1;
            if (subTreeU1.l == subTreeV1.l) {
                	subTreeV1.l++;
                }
        } else {
        	subTreeV1.parent = u1;
        }
	}	
}