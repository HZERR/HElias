package ru.hzerr.config.listener.content;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;

public class Content extends HashHMap<String, Object> implements HMap<String, Object> {

    public Content() { super(); }
    private Content(String key, Object value) {
        super();
        put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) super.get(key);
    }

    public static Content single(String key, Object value) {
        return new Content(key, value);
    }
}
