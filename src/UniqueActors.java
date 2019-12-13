import java.util.*;

// List of actors w/o duplicates
// I thought it'd be cool to make this more efficient by using
// ...a linked hash map instead, but when I looked it up it was
// ...already pretty similar to this...

// Lets program search for names, ids, and actors based on some reference value

public class UniqueActors {

	private ActorsMap references = new ActorsMap();
	private ArrayList<String> names = new ArrayList<>();

	public UniqueActors() {}

	public void add(String key) {
		if (references.isUnique(key))
			names.add(key);
	}

	// getXByY: given reference Y, get X
	public ActorNode getActorByName(String name) {
		return references.find(name);
	}

	public ActorNode getActorById(int id) {
		String name = getNameById(id);
		return getActorByName(name);
	}

	public String getNameById(int id) {
		return names.get(id);
	}

	public int getIdByName(String name) {
		return references.getValue(name);
	}

	public int size() {
		return names.size();
	}
}