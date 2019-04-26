package opheliasoasis;

import java.io.Serializable;

public class Pair<Key, Value> implements Serializable {
    public final Key key;
    public final Value value;

    public Pair(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }
}
