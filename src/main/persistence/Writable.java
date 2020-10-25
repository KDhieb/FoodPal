package persistence;

import org.json.JSONObject;

// Represents the interface for making class data writable as json
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
