import java.util.LinkedList;
import java.util.ListIterator;

public class LowerBound implements Solver {
    private Graph g;
    private int solution;

    public LowerBound(Graph g) {
        this.g = g;
        solution = -1;
    }

    public Graph getGraph() {
        return g;
    }
    //return the solution
    public int solve() {
        if (solution == -1){
            if(g.isTrivial())
                solution = g.trivialLowerBound();
            else {
                LinkedList<Node> R = new LinkedList<Node>();	//current clique
                LinkedList<Node> P = new LinkedList<Node>();	//possible nodes for the clique
                LinkedList<Node> X = new LinkedList<Node>();	//used nodes
                LinkedList<LinkedList<Node>> res = new LinkedList<LinkedList<Node>>();	//list of cliques
                Node[] nodes = g.getNodes();
                for(int i = 0; i < nodes.length; i++) {                    
                    P.add(nodes[i]);
                }
                findClique(R,P,X, res);
                for(int i = 0; i < res.size(); i++) {
                    if(res.get(i).size() > solution)
                        solution = res.get(i).size();
                }
            }
        }
        return solution;
    }
	
    private void findClique(LinkedList<Node> R, LinkedList<Node> P, LinkedList<Node> X, LinkedList<LinkedList<Node>> res) {
        //stop
        if(P.isEmpty() && X.isEmpty()) {
            //return;
            res.add(R);
            return;
        }
        //next recursion
        
        //for(Node n: (LinkedList<Node>)P.clone()) {
        Node n;
        for(int i = 0; i < P.size(); i++){
            try {
                n = P.get(i);
                if(n.getDegree() != 0){
                    LinkedList<Node> Rclone = (LinkedList<Node>)R.clone();
                    Rclone.add(n);
                    /*System.out.println("Pin:" + intersect(P, n.getChildren()));
                    System.out.println("P: " + P);
                    System.out.println("maxLink: " + maxLinked);
                    System.out.println("N(v): " + n.getChildren());
                    System.out.println("Xin:" + intersect(X, n.getChildren()));
                    in.nextLine();*/
                    findClique(Rclone, intersect(P, n.getChildren()), intersect(X, n.getChildren()), res);
                    P.remove(n);
                    if(!X.contains(n))
                        X.add(n);
                }
            }catch(StackOverflowError e){
                System.out.println(e.getMessage());
                System.out.println("StackOverflowError in findClique: " + P.size());
            }
        }

    }

    private LinkedList<Node> diff(LinkedList<Node> l1, LinkedList<Node> l2) {
        LinkedList<Node> result = (LinkedList<Node>)l1.clone();
        result.removeAll(l2);
        return l2;
    }

    private LinkedList<Node> union(LinkedList<Node> l1, LinkedList<Node> l2) {
        LinkedList<Node> result = (LinkedList<Node>)l1.clone();
        for(int i = 0; i < l2.size(); i++) {
            if(!l1.contains(l2.get(i)))
                l1.add(l2.get(i));
        }
        return result;
    }

    private LinkedList<Node> intersect(LinkedList<Node> l1, LinkedList<Node> l2) {
        LinkedList<Node> l3, l4;
        if(l1.size() < l2.size()) {
            l3 = (LinkedList<Node>)l1.clone();
            l4 = (LinkedList<Node>)l2.clone();
        }else{
            l4 = (LinkedList<Node>)l1.clone();
            l3 = (LinkedList<Node>)l2.clone();
        }
        ListIterator<Node> it = l3.listIterator(0);
        while(it.hasNext()) {
            if(!l4.contains(it.next()))
                it.remove();
        }
        return l3;
    }
}

// 
// public LowerBound(Graph g) {
		// 
		// // All edges are connected to each other
		// if(complete) {
			// minDegree = verts;
			// }
			// 
			// // There are no edges and so minDegree = chromaticNumber 
			// else if(nullGraph) {
			// minDegree = 1; 
			// }
			// 
			// // There are no vertices = non existent graph 
			// else if (verts == 0) {
			// minDegree = 0;
			// }
			// 
			// /* 	If there are no cliques, there can be no edges and so the chromatic number is = 1
			// Even if a graph has only one edge between two nodes, there is already a clique of degree 1
			// If there are any edges, there must be at least one clique with degree 1
			// Therefore, if noClique = true, minDegree = 0; */
		// 
			// else if (noClique){
			// minDegree = 0; 
			// }
			// 
		// /* Now that all the possible exceptions have been covered in the if statements, a very rough lower bound
		// can be found by finding the highest degree and then adding 1 
		// */
		// else {
		// /* Find node with minimum degree, which uses binary search 
		// Binary search = an algorithm to find an item from a sorted list
		// This algorithm works by dividing the list of items in two repeatedly and so finding the vertex with min degree
		// */ 
		// 
		// // Nodes must be sorted in terms of valency 
		// 
		// public int binarySearch(/*List of nodes*/){
			// int first = 0;
			// int last = /*listofnodes*/.length - 1;
			// int middle = (first + last)/2;
			// 
			// // Recursive method to find the right node
			// if (first < last) {
				// int middle = last + (last - first)/2;
				// return binarySearch(/*list of nodes*/, first, middle, last);
			// }
		// 
			// else if { }
			// 
			// // This needs to be finished 
			// 
			// }
		// }
	// }