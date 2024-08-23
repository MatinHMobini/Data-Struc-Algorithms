import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.StringTokenizer;

public class ParisMetro extends AdjacencyMapGraph<Integer, Integer>{

    private static AdjacencyMapGraph<Integer,Integer> subwayGraph;  
    private static final int WALKING_TIME = 90; // in seconds, used when weight is -1
    public static ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> vistedStations = new ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>>();

    public ParisMetro(String fileName)throws Exception, IOException{ //constructor calls the readMetro method to begin reading the metro.txt file
        subwayGraph = new AdjacencyMapGraph<Integer, Integer>();
        readMetro(fileName);
    }

    //Code for function inspired by lab code, Got help with small errors from Jayson Seip
    static void readMetro(String filename) throws IOException {
        BufferedReader Graph = new BufferedReader(new FileReader(filename));
        Boolean switched = false;
        String str;
        HashMap<String, AdjacencyMapGraph<Integer,Integer>.InnerVertex<Integer>> vertices = new HashMap<>(); // Hashmap to track if a vertex is already in the list

        while((str = Graph.readLine()) != null){
            //Reading the file

            if(switched) { // reader has passed flag which mean now reading edges list
                String[] eInfo = str.split(" ");
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> sourceV = vertices.get(eInfo[0]);

                if (sourceV == null) {
                    sourceV = subwayGraph.insertVertex(Integer.parseInt(eInfo[0]));
                    vertices.put(eInfo[0], sourceV);
                }

                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> endV = vertices.get(eInfo[1]);
                if(endV == null){
                    endV = subwayGraph.insertVertex(Integer.parseInt(eInfo[1]));
                    vertices.put(eInfo[1],endV);
                }

                
                if(subwayGraph.getEdge(sourceV,endV) ==null){ // Adds edge to the subwayGraph
                    int eWeight = Integer.parseInt(eInfo[2]);
                    AdjacencyMapGraph<Integer, Integer>.InnerEdge<Integer> edge = subwayGraph.insertEdge(sourceV, endV, eWeight);
                }

            }
            // when reader reaches $ which symbolzes gets end of vertex list
            else if(str.equals("$")){
                switched = true;
            }
        }
    }
   

    public static void main(String[] args){

        if (args.length < 1) {
            System.out.println("invalid input");
            System.exit(1);
        }
    
        if(args.length == 1){  // task 2i) initializing varibles and reading file, then calling DFS() in order to find result
            try{
                ParisMetro AdjacencyMapGraph = new ParisMetro("metro.txt");
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> vert = subwayGraph.getVertex(Integer.parseInt(args[0]));
                ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> vistedStations = new ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>>();
                vistedStations.add(vert);
                DFS(subwayGraph, vert, vistedStations);

                System.out.println("Line: ");
                for(AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v : vistedStations){
                    System.out.println(v.getElement() + " ");
                }
                
            }catch(Exception e){ //Catching 
                e.printStackTrace();        
            }
            
        }
        else if(args.length == 2){   // task 2ii) initializing varibles and reading file, then calling shortestPathLengths() which uses djiskrta algorthim to find the shortest path from vertex to vertex 2

            try{
                ParisMetro AdjacencyMapGraph = new ParisMetro("metro.txt");
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> vertex = subwayGraph.getVertex(Integer.parseInt(args[0]));
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> vertex2 = subwayGraph.getVertex(Integer.parseInt(args[1]));
                shortestPathLengths(subwayGraph, vertex, vertex2);
                
            }catch(Exception e){ //Catching 
                e.printStackTrace();        
            }
        }
        //remove vertexes from same line as notWorkingLine varible by using DFS and using the result from DFS to iterate through the those 
        //stations and remove the vertex which also removes its edges, then call djistrka algorthim which will calculate what is the shortest path from sourceStation to endStation.
        else if(args.length == 3){ 
            try{
                int sourceStation = Integer.parseInt(args[0]);
                int endStation = Integer.parseInt(args[1]);
                int notWorkingLine = Integer.parseInt(args[2]);
                ParisMetro AdjacencyMapGraph = new ParisMetro("metro.txt");
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> vertex = subwayGraph.getVertex(notWorkingLine);
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> vertexS = subwayGraph.getVertex(sourceStation);
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> verteE = subwayGraph.getVertex(endStation);
                vistedStations.add(vertex);
                DFS(subwayGraph, vertex, vistedStations); // Call dfs to find broken line

                for (AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v : vistedStations) {
                    subwayGraph.removeVertex(v);
                }
            
                shortestPathLengths(subwayGraph, vertexS, verteE); // Call the method which uses djistrka to find shortest path with updated subway system
            }
            
            catch(Exception e){ //Catching 
                e.printStackTrace();        
            }
        }
            
        
        else{  // The input was invalid so print a warning
            System.out.println("Invalid input");
        }
    }

    /*
     * Code for function inspired by lab code
     * Performs depth-first search of the unknown portion of Graph g starting at Vertex u.
     *
     * @param g Graph instance
     * @param u Vertex of graph g that will be the source of the search
     * @param known is a set of previously discovered vertices
     * @param forest is a map from nonroot vertex to its discovery edge in DFS forest
     *
     * As an outcome, this method adds newly discovered vertices (including u) to the known set,
     * and adds discovery graph edges to the forest.
    */
    public static void DFS(AdjacencyMapGraph<Integer,Integer> g, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> u, ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> vistedStations){
        if(!vistedStations.contains(u)){
            vistedStations.add(u);
        }          
        for (AdjacencyMapGraph<Integer, Integer>.InnerEdge<Integer> e : g.outgoingEdges(u)) {   
            if(e.getElement() != -1){
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v = g.opposite(u,e);
                if(!vistedStations.contains(v)){
                    vistedStations.add(v);
                    DFS(g,v,vistedStations);
                }    
            }  
        }
    }


    /**
     * Code for function inspired by lab code
     * Computes shortest-path distances from src vertex to all reachable vertices of g.
     *
     * This implementation uses Dijkstra's algorithm.
     *
     * The edge's element is assumed to be its integral weight.
     */
    public static void shortestPathLengths(AdjacencyMapGraph<Integer, Integer> g, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> src, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> end ) {
        HashMap<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, Integer> d = new HashMap<>();
        HashMap<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, Integer> cloud = new HashMap<>();
        HashMap<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> previousVs = new HashMap<>();
        AdaptablePriorityQueue<Integer, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> pq;
        pq = new HeapAdaptablePriorityQueue<>();
        Map<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, Entry<Integer, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>>> pqTokens;
        pqTokens = new HashMap<>();

        for (AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v : g.vertices()) {
            if (v == src)
                d.put(v, 0);
            else
                d.put(v, Integer.MAX_VALUE);
            pqTokens.put(v, pq.insert(d.get(v), v));
        }

        while (!pq.isEmpty()) {
            Entry<Integer, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> entry = pq.removeMin();
            int key = entry.getKey();
            AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> u = entry.getValue();
            cloud.put(u, key);
            pqTokens.remove(u);

            for (AdjacencyMapGraph<Integer, Integer>.InnerEdge<Integer> e : g.outgoingEdges(u)) {
                AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v = g.opposite(u, e);
                if (cloud.get(v) == null) {
                    int wgt = e.getElement();
                    if(wgt == -1){
                        wgt = 90;
                    }
                    if(d.get(u) + wgt < d.get(v)) {
                        d.put(v, d.get(u) + wgt);
                        previousVs.put(v, u);
                        pq.replaceKey(pqTokens.get(v), d.get(v));
                    }
                }
            }
        }
        // send src, end, previousVs, cloud to shortestPathPrint
        shortestPathPrint(src, end, previousVs, cloud);
    }
 
    // traverse the previously visited vertexes and add them to a route varible which when reversed will be the answer to task 2ii)/2iii)
    public static void shortestPathPrint(AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> src, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> end, HashMap<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> prevVs, HashMap<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>, Integer> cloud){
        ArrayList<AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer>> route = new ArrayList<>();
        AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> v = end;

        while(v != null){
            route.add(v);
            v = prevVs.get(v);
        }

        Collections.reverse(route);
        System.out.println("Time = " + cloud.get(end));  //access time from cloud.
        System.out.println("Path : ");

        for(AdjacencyMapGraph<Integer, Integer>.InnerVertex<Integer> ver : route){
            System.out.println(ver.getElement() + " ");
        }
    }
}
    