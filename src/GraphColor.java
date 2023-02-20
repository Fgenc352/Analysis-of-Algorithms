
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.SortedSet;

// GraphColor class which applies coloring algorithm
public class GraphColor
{
    private final Graph graph;
    
    public GraphColor(Graph graph)
    {
        this.graph = graph;
    }
    
    // Return indexes of the nodes in ascending order
    private int[] getIndexesByNoOrder()
    {
        int numOfVertices = graph.getNumOfVertices();
        int[] indexes = new int[numOfVertices];
        
        for (int i = 0; i < numOfVertices; ++i) {
            indexes[i] = i+1;
        }
        
        return indexes;
    }
    
    // Return indexes of the nodes in random order
    private int[] getIndexesByRandomOrder()
    {
        int numOfVertices = graph.getNumOfVertices();
        int[] indexes = new int[numOfVertices];
        List<Integer> list = new ArrayList<>();
        
        for (int i = 0; i < numOfVertices; ++i) {
            list.add(i+1);
        }
        
        Random random = new Random();
        
        int i = 0;
        
        while (!list.isEmpty()) {
            indexes[i++] = list.remove(random.nextInt(list.size()));
        }
        
        return indexes;
    }
    
    // Sort indexes in descending order according to their degrees (connections)
    // and return sorted indexes
    private int[] getIndexesByNumberOfConnections()
    {
        int numOfVertices = graph.getNumOfVertices();
        SortedMap<Integer, SortedSet<Integer>> adjacencyMap = graph.getAdjacencyMap();
        
        int[] indexes = new int[numOfVertices];     // Holds indexes of nodes
        int[] connections = new int[numOfVertices]; // Holds the degrees of the nodes
        
        for (int i = 0; i < numOfVertices; ++i) {
            indexes[i] = i+1;
            connections[i] = adjacencyMap.get(i+1).size();
        }
        
        // Bubble sort used for simplicity O(V^2)
        // Quick sort or heap sort can be used for O(VlogV)
        for (int i = 0; i < numOfVertices - 1; ++i) {
            for (int j = 0; j < numOfVertices - i - 1; ++j) {
                if (connections[j] < connections[j+1]) {
                    int temp = connections[j];
                    connections[j] = connections[j+1];
                    connections[j+1] = temp;
                    
                    temp = indexes[j];
                    indexes[j] = indexes[j+1];
                    indexes[j+1] = temp;
                }
            }
        }
        
        return indexes;
    }
    
    // Color the graph
    public void run(String outfile)
    {
        int numOfVertices = graph.getNumOfVertices();
        SortedMap<Integer, SortedSet<Integer>> adjacencyMap = graph.getAdjacencyMap();
        
        int[] colors = new int[numOfVertices+1];
        Arrays.fill(colors, -1);
        
        int[] indexes = getIndexesByNumberOfConnections();
        
        colors[indexes[0]]  = 0;
 
        boolean available[] = new boolean[numOfVertices];
        Arrays.fill(available, true);
 
        for (int i = 1; i < numOfVertices; i++)
        {
            int u = indexes[i];
            
            for (int v : adjacencyMap.get(u)) {
                if (colors[v] != -1)
                    available[colors[v]] = false;
            }
            
            int color = 0;
            
            for (; color < numOfVertices; color++){
                if (available[color])
                    break;
            }
 
            colors[u] = color;
            
            Arrays.fill(available, true);
        }
        
        int maxVal = -1;
        
        for (int u = 1; u <= numOfVertices; u++) {
            if (colors[u] > maxVal) {
                maxVal = colors[u];
            }
        }
        
        int totalColors = maxVal + 1;
        
        // Create output
        StringBuilder sb = new StringBuilder();
        sb.append(totalColors + "\n");
        
        String delim = "";
        
        for (int u = 1; u <= numOfVertices; u++) {
            sb.append(delim + colors[u]);
            
            if (delim.isEmpty()) {
                delim = " ";
            }
        }
        
        sb.append("\n");
        
        String outputText = sb.toString();
        
        // Print output
        System.out.println(outputText);
        Util.writeToFile(outfile, outputText);
    }
    
    // Main method
    public static void main(String args[])
    {
        if (args.length < 1) {
            System.out.println("Usage: graphcolor <input file>");
            return;
        }
        
        String filename = args[0];
        String outfile = "output.txt";
        
        if (args.length >= 2)
            outfile = args[1];
        
        // Parse input file
        Graph graph = Util.parseFile(filename);
        
        // Run coloring algorithm on the graph
        GraphColor graphColor = new GraphColor(graph);
        graphColor.run(outfile);
    }
}
