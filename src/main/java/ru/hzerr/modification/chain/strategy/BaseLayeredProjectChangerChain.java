package ru.hzerr.modification.chain.strategy;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.Project;
import ru.hzerr.stream.HStream;

import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class BaseLayeredProjectChangerChain extends AbstractLayeredProjectChangerChain {

    protected Consumer<? super Throwable> onError;
    protected Runnable onFinishedIsError;
    protected Runnable onFinishedIsComplete;

    public BaseLayeredProjectChangerChain(Project project) { super(project); }

    public <T>
    void addAction(Function<? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addFunction((Function<? super Project, Object>) onAction, (Consumer<Object>) onFinished, level);
    }

    public <T>
    void addAction(EmptyRunnable onAction, Runnable onFinished, Integer level) {
        super.addFunction(project -> onAction.emptyRun(), o -> onFinished.run(), level);
    }

    public void setOnError(Consumer<? super Throwable> onError) { this.onError = onError; }
    public void setOnFinishedIsComplete(Runnable onFinishedIsComplete) { this.onFinishedIsComplete = onFinishedIsComplete; }
    public void setOnFinishedIsError(Runnable onFinishedIsError) { this.onFinishedIsError = onFinishedIsError; }

    @Override
    public void apply() {
        LogManager.getLogger().debug("Debug multimap keys used");
        for (Integer key: actions.keySet()) {
            LogManager.getLogger().debug("\tKey: " + key);
        }
        Integer[] keysTmp = new Integer[actions.keys().size()];
        HStream<Integer> keys = HStream.of(actions.keys().toArray(keysTmp)); // don't use the spliterator!
        int minLevel = keys.reduce(Math::min).orElseThrow(IllegalStateException::new);
        int maxLevel = keys.reduce(Math::max).orElseThrow(IllegalStateException::new);
        for (int i = minLevel; i <= maxLevel; i++) {
            actions.get(i).forEach(action -> observable = observable.doOnNext(project -> {
                if (action.getValue() != null) {
                    Object o = action.getKey().apply(project);
                    Platform.runLater(() -> {
                        try {
                            action.getValue().accept(o);
                        } catch (Throwable throwable) { throwable.printStackTrace(); }
                    });
                } else action.getKey().apply(project);
            }));
        }
        if (Objects.nonNull(onFinishedIsComplete)) observable = observable.doOnComplete(() -> Platform.runLater(onFinishedIsComplete));

        observable.subscribeOn(Schedulers.io()).subscribe(new Observer<Project>() {
            @Override public void onSubscribe(@NotNull Disposable d) {}
            @Override public void onNext(@NotNull Project project) {}
            @Override public void onError(@NotNull Throwable e) {
                if (Objects.nonNull(onFinishedIsError)) {
                    onFinishedIsError.run();
                }

                onError.accept(e);
            }
            @Override public void onComplete() {}
        });
    }
}
