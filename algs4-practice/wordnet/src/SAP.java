//package wordNet;

import edu.princeton.cs.algs4.*;


public class SAP {

    private Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){

        if (G == null) throw new IllegalArgumentException("Null graph");

        dg = new Digraph(G);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){

        if (v < 0 || v >= dg.V() || w < 0 || w >= dg.V())
            throw new IllegalArgumentException("vertex is not between 0 and " + (dg.V()-1));

//        ancestor(v,w);

        // use bfs to find the path and the shortest distance from v to every vertices in the graph
        // use two arrays, one record the last vertex, edgeTo[]. Another record the distance, distTo[].
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(dg, w);

        int ancestor = ancestor(v,w);

        if (ancestor == -1){
            return -1;
        }else{
            return bfs1.distTo(ancestor) + bfs2.distTo(ancestor);
        }

    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){

        if (v < 0 || v >= dg.V() || w < 0 || w >= dg.V())
            throw new IllegalArgumentException("vertex is not between 0 and " + (dg.V()-1));

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(dg, w);

        Stack<Integer> candidates = new Stack<>();

        for (int p = 0; p < dg.V(); p++){
            if (bfs1.hasPathTo(p) && bfs2.hasPathTo(p)){
                candidates.push(p);
            }
        }

        int shortesAncestor = -1;
        int shortesPath = Integer.MAX_VALUE;

        while (!candidates.isEmpty()){
            int c = candidates.pop();
            if (shortesPath > bfs1.distTo(c)+bfs2.distTo(c)){
                shortesPath = bfs1.distTo(c)+bfs2.distTo(c);
                shortesAncestor = c;
            }
        }

        return shortesAncestor;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){

//        if (v < 0 || v >= dg.V() || w < 0 || w >= dg.V())
//            throw new IllegalArgumentException("vertex is not between 0 and " + (dg.V()-1));
//        ancestor(v,w);

        // use bfs to find the path and the shortest distance from v to every vertices in the graph
        // use two arrays, one record the last vertex, edgeTo[]. Another record the distance, distTo[].
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(dg, w);

        int ancestor = ancestor(v,w);

        if (ancestor == -1){
            return -1;
        }else{
            return bfs1.distTo(ancestor) + bfs2.distTo(ancestor);
        }

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(dg, w);

        Stack<Integer> candidates = new Stack<>();

        for (int p = 0; p < dg.V(); p++){
            if (bfs1.hasPathTo(p) && bfs2.hasPathTo(p)){
                candidates.push(p);
            }
        }

        int shortesAncestor = -1;
        int shortesPath = Integer.MAX_VALUE;

        while (!candidates.isEmpty()){
            int c = candidates.pop();
            if (shortesPath > bfs1.distTo(c)+bfs2.distTo(c)){
                shortesPath = bfs1.distTo(c)+bfs2.distTo(c);
                shortesAncestor = c;
            }
        }

        return shortesAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
