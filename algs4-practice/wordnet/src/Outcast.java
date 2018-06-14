import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = 0;
        String outlier = "";

        for (int i = 0; i < nouns.length; i++) {
            int tempSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                tempSum += wn.distance(nouns[i], nouns[j]);
            }

            if (tempSum > maxDistance) {
                maxDistance = tempSum;
                outlier = nouns[i];
            }
        }

        return outlier;

    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}