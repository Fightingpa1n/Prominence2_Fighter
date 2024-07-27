package net.fightingpainter.help;

import java.util.*;
import org.apache.logging.log4j.util.BiConsumer;

public class Dict { //maybe at somepoint I need to make this a general lib for java so I can use it both for my mods as well as other projects
    private Map<String, Object> data = new HashMap<>(); //Dictionary data

    //================================MISC FUNCTIONS================================
    private void _set(String key, Object value) { //Add value
        _checkType(value);
        this.data.put(key, value);
    }

    private void _checkType(Object value) { //Check if value is allowed
        if (value == null || value instanceof String || value instanceof Integer || value instanceof Float || value instanceof Boolean || value instanceof Dict) {
            return;
        }
        if (value instanceof List<?>) { //Check list items
            for (Object item : (List<?>) value) {
                _checkType(item);
            }
            return;
        }
        throw new RuntimeException("Unknown type in Dict: " + (value != null ? value.getClass().getSimpleName() : "null"));
    }

    //================================CONSTRUCTORS================================
    public Dict() { //Empty constructor
    }
    @SuppressWarnings("unchecked")
    public Dict(Pair<String, Object>... pairs) { //Constructor with pairs
        for (Pair<String, Object> pair : pairs) {
            _set(pair.getFirst(), pair.getSecond());
        }
    }

    public Dict(Map<String, Object> map) { //Constructor with map
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            _set(entry.getKey(), entry.getValue());
        }
    }

    public Dict(String jsonString) { //Constructor with JSON string
        if (jsonString.isEmpty()) {
            throw new RuntimeException("Empty JSON string");
        } //If empty, throw exception
        if (jsonString.equals("{}")) {
            return;
        } //If empty, return empty dictionary
        List<String> pairs = _getItems(jsonString);
        for (String pair : pairs) {
            Pair<String, String> splitPair = _splitPair(pair);
            _set(splitPair.getFirst(), _getValue(splitPair.getSecond()));
        }
    }

    //================================OPERATORS================================
    public Object get(String key) { //Get value
        return this.data.get(key);
    }

    public void set(String key, Object value) { //Set value
        _set(key, value);
    }

    public void forEach(BiConsumer<String, Object> action) { //Iterate over dictionary
        for (Map.Entry<String, Object> entry : this.data.entrySet()) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    //================================MODIFY================================
    public void add(String key, Object value) {
        _set(key, value);
    } //Add key and value

    public void add(Pair<String, Object> pair) {
        _set(pair.getFirst(), pair.getSecond());
    } //Add pair

    @SuppressWarnings("unchecked")
    public void add(Pair<String, Object>... pairs) { //Add multiple pairs
        for (Pair<String, Object> pair : pairs) {
            _set(pair.getFirst(), pair.getSecond());
        }
    }

    public Object remove(String key) { //Remove key and value
        return this.data.remove(key);
    }

    public Dict copy() {
        return new Dict(this.data);
    } //Copy dictionary

    //================================CHECKING================================
    public List<String> keys() {
        return new ArrayList<>(this.data.keySet());
    } //Get all keys

    public List<Object> values() {
        return new ArrayList<>(this.data.values());
    } //Get all values

    public int length() {
        return this.data.size();
    } //Get length

    public boolean isEmpty() {
        return this.data.isEmpty();
    } //Check if empty

    public boolean contains(String key) {
        return this.data.containsKey(key);
    } //Check if contains key

    //================================CONVERSION================================
    @Override
    public String toString() { //Convert dictionary to string
        return this.data.toString();
    }

    public String toJsonString() {
        if (this.data.isEmpty()) {
            return "{}";
        } //If empty, return empty dictionary
        StringBuilder result = new StringBuilder("{");

        data.forEach((key, value) -> result.append("\"").append(key).append("\": ").append(_valueToString(value)).append(", "));

        return result.substring(0, result.length() - 2) + "}";
    }

    public Map<String, Object> toMap() { //Convert dictionary to map
        return this.data;
    }

    //================================JSON MISC================================
    private String _valueToString(Object value) { //Convert value to string
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Integer || value instanceof Float || value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof Dict) {
            return ((Dict) value).toJsonString();
        } else if (value instanceof List<?>) {
            return _listToString((List<?>) value);
        } else {
            throw new RuntimeException("Unknown type in Dict: " + value.getClass().getSimpleName());
        }
    }

    private String _listToString(List<?> list) { //Convert list to string
        if (list.isEmpty()) {
            return "[]";
        } //If empty, return empty list
        StringBuilder result = new StringBuilder("[");
        for (Object item : list) {
            result.append(_valueToString(item)).append(", ");
        }
        return result.substring(0, result.length() - 2) + "]";
    }

    private Pair<String, String> _splitPair(String str) { //Split string into pair
        String[] split = str.split(": ", 2);
        return new Pair<>((String) _getValue(split[0]), split[1]);
    }

    private List<String> _getItems(String str) { //Get items from string
        String content = str.substring(1, str.length() - 1); //Remove brackets
        return _splitIntoPairs(content);
    }

    private List<String> _splitIntoPairs(String str) { //Split string into pairs
        List<String> result = new ArrayList<>();
        int start = 0; //Start of current item
        int level = 0; //Current nesting level
        boolean isString = false; //If inside a string

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '"') { //If string
                if (i == 0 || str.charAt(i - 1) != '\\') {
                    isString = !isString; //If not escaped, toggle isString
                }
            }
            if (isString) continue; //If inside string, ignore
            if (c == ',') { //If possible split
                if (level == 0) { //If not inside nested object
                    result.add(str.substring(start, i).trim()); //Add item
                    start = i + 1; //Set start to next item
                }
            }
            if (c == '{' || c == '[') level++; //Increase level
            if (c == '}' || c == ']') level--; //Decrease level
        }
        result.add(str.substring(start).trim()); //Add last item
        return result;
    }

    private Object _getValue(String str) { //Convert string to value
        if (str.equals("null")) {
            return null;
        } else if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);
        } else if (str.matches("-?\\d+")) {
            return Integer.parseInt(str);
        } else if (str.matches("-?\\d*\\.\\d+")) {
            return Float.parseFloat(str);
        } else if (str.equals("true")) {
            return true;
        } else if (str.equals("false")) {
            return false;
        } else if (str.equals("{}")) {
            return new Dict();
        } else if (str.equals("[]")) {
            return new ArrayList<>();
        } else if (str.startsWith("{") && str.endsWith("}")) {
            return new Dict(str);
        } else if (str.startsWith("[") && str.endsWith("]")) {
            return _getList(str);
        } else {
            throw new RuntimeException("Unknown type in Dict: " + str);
        }
    }

    private List<Object> _getList(String str) { //Convert string to list
        List<Object> result = new ArrayList<>();
        String[] items = str.substring(1, str.length() - 1).split(", ");
        for (String item : items) {
            result.add(_getValue(item));
        }
        return result;
    }
}

