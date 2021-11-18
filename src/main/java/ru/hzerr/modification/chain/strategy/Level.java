package ru.hzerr.modification.chain.strategy;

public enum Level {
    CLEANUP(1),
    DECOMPRESS(2),
    TRANSFORM(3),
    BUILD(4),
    CHANGE_RESOURCES(5),
    ADD_DELETE_LIBRARIES(6),
    UPDATE(7),
    START(8);

    private final int level;

    Level(int level) { this.level = level; }

    public int getLevel() { return this.level; }
}
