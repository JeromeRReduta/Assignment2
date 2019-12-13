import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.io.*;
import java.util.*;

public class A3 {

    // For each line in the file, processes data and adds data to UniqueActors list
    public static UniqueActors initActorList(File f, Scanner scan, BufferedReader br) throws IOException {
        String line = br.readLine();
        UniqueActors aList = new UniqueActors();

        while ((line = br.readLine()) != null) {

            JsonArray actors = new JsonArray();
            String[] data = line.split(",");

            processData(actors, data);
            addToList(aList, actors);
        }

        return aList;
    }

    // For each line in file, adds actor names to JsonArray
    private static void processData(JsonArray actors, String[] data) {
        boolean isActor = false, isName = false;

        for (String passage : data) {
            ArrayList<Integer> coworkers = new ArrayList<>();

            isActor = (passage.startsWith(" {\"\"cast_id\"\":") ||
                    passage.startsWith("\"[{\"\"cast_id\"\":")) || isActor;

            isName = passage.startsWith(" \"\"name\"\": \"\"");

            if (isActor && isName) {
                String fragment = passage.substring(13, passage.length() - 2);
                actors.add(fragment);
                isActor = false;
            }

        }
    }

    // For each name in each line, adds names to UniqueActors if...
    // ...not already in the list

    // Also adds the ids of every actor in a given movie to every other actor
    // ...in that movie, to identify who is who's coworker
    private static void addToList(UniqueActors aList, JsonArray actors) {
        ArrayList<String> coworkerKeys = new ArrayList<>();
        ArrayList<Integer> coworkerIDs = new ArrayList<>();


        for (Object word : actors) {

            String name = word.toString();

            aList.add(name);

            coworkerKeys.add(name);

            coworkerIDs.add(aList.getIdByName(name));

        }

        String a = null;

        for (String name : coworkerKeys) {

            for (int id : coworkerIDs) {
                if (a == null)
                    a = name;
                aList.getActorByName(name).workedWith(id);
            }
        }
    }

    // Gets the shortest path between two actors
    public static void getShortestPath(Graph g, UniqueActors aList, Scanner scan) throws Exception {
        String actor1 = checkActorInput(aList, scan, "Actor Name 1:\t");
        String actor2 = checkActorInput(aList, scan, "Actor Name 2:\t");

        g.findShortestPath(actor1, actor2);
    }

    // Asks for an actor name until you input a valid one
    public static String checkActorInput(UniqueActors aList, Scanner scan, String message) {
        String input;
        do {
            System.out.print(message);
            input = scan.nextLine();
            System.out.println();
        }

        while (aList.getIdByName(input) < 0);

        return input;
    }

    public static void main(String[] args) throws Exception {
        File f = new File(args[0]);
        Scanner scan = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader(f));

        // Create UniqueActors list based on given file
        UniqueActors aList = initActorList(f, scan, br);

        // Create graph based on given UniqueActors list
        Graph g = new Graph(aList);

        // Gets the shortest path between two actors
        getShortestPath(g, aList, scan);
    }
}