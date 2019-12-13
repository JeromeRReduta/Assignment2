import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

public class JSonSimpleDemo {

    public static void testConcept() {
        JsonObject jo = new JsonObject();

        jo.put("firstName", "John");
        jo.put("lastName", "Smith");
        jo.put("age", 25);

        Map m = new LinkedHashMap(4);
        m.put("a", "1");
        m.put("b", "2");
        m.put("c", "3");
        m.put("d", 45);

        jo.put("address", m);

        JsonArray ja = new JsonArray();

        System.out.println(jo.toString());
        System.out.println("It WOOOOOOORKS");
    }

    public static void testMulti() {
        for (int i = 0; i < 250; i++) {
            JsonArray ja = new JsonArray();
            ja.add(i);
            System.out.println(ja);
        }
    }

   /* public static void addName(JsonArray ja, String line) {
        if (line.startsWith(" \"\"name\"\": \"\""))
            ja.add(line.substring(13, line.length() - 2));
        if (line.endsWith("\"\"}]\""))
            ja.add(line.substring(13, line.length() - 5));

        else if (line.endsWith("}"))
            ja.add(line.substring(13, line.length() - 3));

        else
            ja.add(line.substring(12, line.length()));
            //ja.add(line.substring(13, line.length() - 2));

        String buffer = " \"\"name\"\": \"\"";
        int bufferLength = buffer.length();

        //ja.add(line.substring(13, line.length()));
    } */

   public static void processData(JsonArray actors, String[] data) {
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

    public static void addToList(UniqueActors aList, JsonArray actors) {
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

        /*if (a != null) {
            System.out.println(aMap.find(a));
            System.out.println(aMap.find(a).getCoworkers());
            System.out.println(aList.get( aMap.find(a).getValue() ) );
            System.out.println(aList.get(aMap.getValue(a)).getCoworkers());
        } */
   }
    public static void testReadfile(File f) throws Exception {
        Scanner scan = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader(f));

        String line = br.readLine();
        UniqueActors aList = new UniqueActors();

        while ((line = br.readLine()) != null) {

            JsonArray actors = new JsonArray();
            String[] data = line.split(",");

            processData(actors, data);
            addToList(aList, actors);
        }

        Graph g = new Graph(aList);
        String actor1, actor2;

        do {
            System.out.print("Actor Name 1:\t");
            actor1 = scan.nextLine();
            System.out.println();
        }

        while(aList.getIdByName(actor1) < 0);

        do {
            System.out.print("Actor Name 2:\t");
            actor2 = scan.nextLine();
            System.out.println();
        }

        while(aList.getIdByName(actor2) < 0);

        g.findShortestPath(actor1, actor2);


    }

    public static void main(String[] args) throws Exception {
        File f = new File("src/tmdb_5000_credits.csv");
        testReadfile(f);
        //testMulti();


    }
}