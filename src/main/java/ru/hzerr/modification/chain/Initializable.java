package ru.hzerr.modification.chain;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.modification.state.strategy.State;

public interface Initializable {

    <T extends State> void init(@NotNull T state);
}
