import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;

public class WordNet {

    private final Map<Integer, String> idToWord;      // mapping between id and the noun
    private final Map<String, LinkedList<Integer>> nounSets;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("name can't be empty");

        In nouns = new In(synsets);
        idToWord = new HashMap<>();
        nounSets = new HashMap<>();
        int numOfVertices = 0;
        // find all the words
        while (nouns.hasNextLine()) {
            String line = nouns.readLine();
            int id = Integer.parseInt(line.split(",")[0]);
            String n = line.split(",")[1];
            idToWord.put(id, n);

            for (String w : n.split("\\s+")) {
//                System.out.println(w);
                LinkedList<Integer> temp = nounSets.getOrDefault(w, new LinkedList<>());
                temp.add(id);
                nounSets.put(w, temp);
            }

            numOfVertices++;
        }

        Digraph dg = new Digraph(numOfVertices);

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

        int root = -1;

        for (int v = 0; v < dg.V(); v++) {
            if (dg.outdegree(v) == 0 && dg.indegree(v) > 0) {
                if (root == -1) {
                    root = v;
                } else {
                    throw new IllegalArgumentException("Multiple Roots");
                }
            }
        }

        if (root == -1) {
            throw new IllegalArgumentException("No Roots");
        }

        sap = new SAP(dg);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounSets.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (!nounSets.containsKey(word)) {
            throw new IllegalArgumentException("Non existed noun!");
        }
        return true;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Non existed noun!");
        }

        LinkedList<Integer> synsetA = nounSets.get(nounA);
        LinkedList<Integer> synsetB = nounSets.get(nounB);

        return sap.length(synsetA, synsetB);

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Non existed noun!");
        }

        LinkedList<Integer> synsetA = nounSets.get(nounA);
        LinkedList<Integer> synsetB = nounSets.get(nounB);

        return idToWord.get(sap.ancestor(synsetA, synsetB));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("data/synsets15.txt", "data/hypernyms15Path.txt");
        System.out.println(wn.isNoun("a"));

    }
}