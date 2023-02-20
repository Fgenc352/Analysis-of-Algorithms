
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

// Graph implementation (undirected)
public class Graph
{
    // Total number of vertices in the graph
    private final int numOfVertices;
    // Adjacency list (hold in the map)
    private final SortedMap<Integer, SortedSet<Integer>> adjacencyMap;
    
    // Contructor initialize graph
    public Graph(int numOfVertices)
    {
        this.numOfVertices = numOfVertices;
        this.adjacencyMap = new TreeMap<>();
        
        for (int i = 1; i <= this.numOfVertices; ++i) {
            adjacencyMap.put(i, new TreeSet<>());
        }
    }
    
    // Add an edge between nodes u and v (undirected)
    public boolean addEdge(int u, int v)
    {
        if (u <= 0 || v <= 0 || u > numOfVertices || v > numOfVertices)
            return false;
        
        adjacencyMap.get(u).add(v);
        adjacencyMap.get(v).add(u);
        
        return true;
    }
    
    // Getter for number of vertices
    public int getNumOfVertices()
    {
        return this.numOfVertices;
    }
    
    // Getter for adjacency map
    public SortedMap<Integer, SortedSet<Integer>> getAdjacencyMap()
    {
        return this.adjacencyMap;
    }
}
