package ru.hzerr.modification.state.strategy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;

import java.io.Serializable;

public abstract class State implements Count, Serializable {

    protected HMap<String, Object> values;
    protected String name;

    public State() {
        this.values = new HashHMap<>();
    }
    public State(HMap<String, Object> values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(String key, Class<T> clazz) { return (T) this.values.get(key); }
    public final Boolean getBoolean(String key) { return (Boolean) this.values.get(key); }

    public final void setName(String name) { this.name = name; }
    public final String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof State)) return false;

        //noinspection ConstantConditions
        ru.hzerr.modification.state.strategy.State state = (ru.hzerr.modification.state.strategy.State) o;

        return new EqualsBuilder().append(values, state.values).append(name, state.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(values).append(name).toHashCode();
    }
}
