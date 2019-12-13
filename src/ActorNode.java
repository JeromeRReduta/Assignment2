import java.util.*;

// Node containing actor's data
// Data: Name, unique id, and coworkers
public class ActorNode extends Node {
    private String key;
    private int id;
    private ArrayList<Integer> coworkers = new ArrayList<>();

    public ActorNode(String key, int id) {
        this.key = key;
        this.id = id;
    }


    public String getKey() {
        return this.key;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Integer> getCoworkers() {
        return this.coworkers;
    }

    // Adds the hashmap id of this actor's coworker
    public void workedWith(int actorCode) {
        coworkers.add(actorCode);
    }

    public String toString() {
        return key + "\t\t\t" + id;
    }



}
