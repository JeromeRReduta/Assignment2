import java.util.*;

public class Graph {

    private EdgeList<Edge>[] actors;

    private int size;

    private UniqueActors aList;

    public Graph (int size) {
        this.size = size;
        actors = new EdgeList[this.size];

        for (int i = 0; i < actors.length; i++) {
            actors[i] = new EdgeList<>();
            actors[i].link(i);
        }

    }
    public Graph(UniqueActors aList) throws Exception {
        this.size = aList.size();
        this.aList = aList;
        actors = new EdgeList[this.size];


        for (int i = 0; i < actors.length; i++) {
            actors[i] = new EdgeList<>();
            actors[i].link(i);
        }



        for (int i = 0; i < actors.length; i++) {
            ArrayList<Integer> coworkers = aList.getActorById(i).getCoworkers();

            for (int coworker : coworkers)
                addEdge(coworker, i);
        }

        //findShortestPath(0, 5269);
    }


    public void addEdge(int v1, int v2) {
        actors[v1].link(v2);
        actors[v2].link(v1);
    }

    // Lovingly taken from GeeksForGeeks: https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
    // Modified BFS that finds shortest path from src to dest vertex
    // ...since in a graph w/ equal weight (unweighted means weight = 0)
    // ...for all vertices, BFS always finds a shortest path

    public void findShortestPath(String actor1, String actor2) throws Exception {
        int src = this.aList.getIdByName(actor1);
        int dest = this.aList.getIdByName(actor2);

        /*System.out.println(actor1);
        System.out.println(actor2);

        System.out.println(actor1.equalsIgnoreCase("Sam Worthington"));
         */
        /*
            init dist[v-1] such that dist[i] stores dist of vertex i
            from source vertex

            init pred[v-1] such that pred[i] is predecessor of vertex i in BFS

            Once vertex dest is visited, you have a chain of vertices
            leading from dest to pred[dest] to pred[pred[dest]]...to src

            Thank you, GeeksForGeeks
         */

        // Case: inputting same actor

        if (actor1.equalsIgnoreCase(actor2)) {
            System.out.println("Same actor!");
            return;
        }
        // Found this # off the Google
        int[] pred = new int[this.size];
        boolean[] visited = new boolean[this.size];
        MyQueue<Integer> q = new MyQueue<>();
        /* Initializes pred such that:
            No predecessors found, so pred[i] = -1 for all vertices i

            visited is initialized to false by default
         */

        for (int i = 0; i < size; i++) {
            pred[i] = -1;
        }

        // Starts with src
        visited[src] = true;
        q.enqueue(src);

        while (!q.empty()) {
            int next = q.dequeue();


            Edge current = actors[next].get(0);

            while (current != null) {

                int neighbor = current.getId();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    pred[neighbor] = next;
                    q.enqueue(neighbor);

                    if (neighbor == dest) {
                        printShortestPath(dest, pred);
                        return;
                    }

                }

                current = (Edge) current.getNext();

            }


        }
        System.out.println("Couldn't find it!");
    }

    private void printShortestPath(int dest, int[] pred) {
        String pathById = "" + this.aList.getNameById(dest);

        int next = dest;

        while (pred[next] != -1) {
            pathById = this.aList.getNameById(pred[next]) + "-> " + pathById;
            next = pred[next];
        }

        System.out.println("Shortest path is:\t" + pathById);
    }

    public void print() throws Exception {



        for (EdgeList<Edge> row : actors) {
            Edge current = row.get(0);

            System.out.print("Actor with ID: " + current.getId() +
                    " connected to:\t");
            current = (Edge)current.getNext();

            while (current != null) {
                System.out.print("-> " + current.toString() + " ");
                current = (Edge)current.getNext();
            }
            System.out.println();
        }
    }
}
