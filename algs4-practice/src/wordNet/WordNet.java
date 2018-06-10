package wordNet;

import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private Map<Integer, String> idToWord;      // mapping between id and the noun
    private Digraph dg;
    private int root = -1;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("name can't be empty");

        In nouns = new In(synsets);
        idToWord = new HashMap<>();
        int numOfVertices = 0;
        // find all the words
        while (nouns.hasNextLine()) {
            String l = nouns.readLine();
            int id = Integer.parseInt(l.split(",")[0]);
            String n = l.split(",")[1];
            idToWord.put(id, n);
            numOfVertices++;
        }

        dg = new Digraph(numOfVertices);

        // construct the edges
        In links = new In(hypernyms);
        while (links.hasNextLine()) {

            String[] parts = links.readLine().split(",");
            int start = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                dg.addEdge(start, Integer.parseInt(parts[i]));
            }
        }

        // check if the graph is a rooted DAG
        DirectedCycle dc = new DirectedCycle(dg);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException("Not a valid DAG");
        }

        for (int v = 0; v < dg.V(); v++) {
            if (dg.indegree(v) == 0) {
                if (root == -1) {
                    root = v;
                } else {
                    throw new IllegalArgumentException("Multiple Roots");
                }
            }
        }


    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
    }

    ;

    // is the word a WordNet noun?
    public boolean isNoun(String word)

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)

    // do unit testing of this class
    public static void main(String[] args)
}