package persistence;

import org.json.JSONObject;

// source code: JsonSerializationDemo application from Project Phase 2 on edX
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
