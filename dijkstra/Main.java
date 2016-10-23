import java.io.*;
import java.util.*;

/**
 * program to find word ladder with shortest path (i.e. minimum number edges)
 */
public class Main {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        String inputFileName = args[0]; // dictionary
        String startWord = args[1]; // first word
        String endWord = args[2]; // second word

        FileReader reader = new FileReader(inputFileName);
        Scanner in = new Scanner(reader);
        reader = new FileReader(inputFileName);

        // add input words to graph
        Graph graph = new Graph();
        int index = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            graph.addVertex(index, line);
            index++;
        }
        int inputSize = index;

        // connect vertices
        Vertex inner;
        Vertex outer;
        for (int outerIndex = 0; outerIndex < inputSize; outerIndex++) {
            outer = graph.getVertex(outerIndex);
            for (int innerIndex = outerIndex + 1; innerIndex < inputSize; innerIndex++) {
                inner = graph.getVertex(innerIndex);
                int difference = outer.letterDifference(inner);
                if (difference != -1) {
                    outer.addToAdjList(innerIndex, difference);
                    inner.addToAdjList(outerIndex, difference);
                }
            }
        }

        // find word ladder
        ArrayList<String> wordLadder = graph.findWordLadder(startWord, endWord);
        if (wordLadder == null) {
            System.out.format("Word ladder from %s to %s does not exist.\n", startWord, endWord);
        } else {
            for (String word : wordLadder) System.out.println(word);
        }

        reader.close();

        // end timer and print total time
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (endTime - startTime) + " milliseconds");
    }

}
