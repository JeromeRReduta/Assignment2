import java.util.*;

// Thanks to Professor David for the idea of using a hashmap to store
// ...actors before making a graph

// Hashmap containing all actors
public class ActorsMap {
    // Set actors length = sqrt(# of unique cast values),
    // or sqrt(4761) - nice
    final int ACTORS_LENGTH = 69;

    private ActorNode[] actors = new ActorNode[ACTORS_LENGTH];
    private int size = 0;
    //private int numBuckets = actors.size();

    public ActorsMap() {}

    /*
    Hash by adding the ascii values of the first seven
    characters, or the whole word if it is less than seven
    characters
     */
    public int hash(String key) {

        if (key == null)
            System.out.println("NULL STRING FOUND");

        /* hash based on uppercase version of key so that
        if you have two keys with same spelling but different
        capitalization, the keys will hash to the same index
         */
        String hashKey = key.toUpperCase();
        
        int total = 0;

        for (int i = 0; i < hashKey.length() && i < 7; i++) 
            total += (int)hashKey.charAt(i);

        return total;
    }

    // Return the ActorNode with a given name
    public ActorNode find(String key) {
        int index = hash(key) % actors.length;

        ActorNode current = actors[index];

        // Case: an ActorNode exists
        while (current != null) {

            // Case: ActorNode has our given name
            if (current.getKey().equalsIgnoreCase(key)) {
                return current;
            }

            // Case: It doesn't
            current = (ActorNode)current.getNext();
        }

        // Case: ActorNode not in the map
        System.out.println("Can't find actor:\t" + key);;
        return null;
    }


    // Checks if a value is unique
    // If it is, add it to the hashmap and return true; else return false
    public boolean isUnique(String key) {

        int index = hash(key) % actors.length;

        // Case: head null
        if (actors[index] == null) {
            actors[index] = new ActorNode(key, size++);
            return true;
        }

        ActorNode current = actors[index];
        ActorNode prev = current;

        // Case: actors[index] not empty
        while (current != null) {

            // Case: duplicate found
            if (current.getKey().equalsIgnoreCase(key))
                return false;

            // Case: duplicate not found, but more nodes in list
            prev = current;
            current = (ActorNode)current.getNext();
        }

        // Case: duplicate not found
        prev.setNext(new ActorNode(key, size++));
        return true;

    }

    public int getValue(String key) {
        ActorNode actor = find(key);

        if (actor != null)
            return actor.getId();

        return -1;
    }

    public int getSize() {
        return this.size;

    }
    public String toString() {

        String result = "";
        result += "ACTOR\t\t\tID\n\n";

        for (ActorNode head : actors) {
            ActorNode current = head;

            while (current != null) {
                result += current.toString() + "\n";

                current = (ActorNode)current.getNext();
            }
        }

        return result;
    }

}