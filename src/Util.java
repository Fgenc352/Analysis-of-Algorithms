
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util
{
    // Parse the file and return a graph object accordingly
    public static Graph parseFile(String filename)
    {
        Graph graph = null;
        
        try {
            Scanner scanner = new Scanner(new File(filename));
            
            boolean init = false;
            
            // Read file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] tokens = line.split("\\s");
                
                String cmd = tokens[0].trim();
                int first = Integer.parseInt(tokens[1]);
                int second = Integer.parseInt(tokens[2]);
                
                switch (cmd) {
                    case "p":
                        graph = new Graph(first);
                        init = true;
                        break;
                    case "e":
                        if (graph != null)
                            graph.addEdge(first, second);
                        break;
                    default:
                        if (!init) {
                            init = true;
                            graph = new Graph(first);
                        }
                        break;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return graph;
    }
    
    // Write data to the file
    public static void writeToFile(String filename, String content)
    {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(content);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
