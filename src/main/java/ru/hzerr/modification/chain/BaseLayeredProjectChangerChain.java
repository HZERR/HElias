package ru.hzerr.modification.chain;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.log.LogManager;
import ru.hzerr.stream.HStream;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseLayeredProjectChangerChain extends AbstractLayeredProjectChangerChain implements Initializable {

    public BaseLayeredProjectChangerChain(Profile profile) { super(profile); }

    @Override
    public void apply() {
        LogManager.getLogger().debug("Initializing modification...");
        for (int i = 0; i < stages.size(); i++) {
            LogManager.getLogger().debug("\tStage #" + i + " has a level " + stages.get(i).level());
            LogManager.getLogger().debug("\tStage #" + i + ". Description: " + stages.get(i).description());
        }
        Map<Integer, BaseStage<?>> stagesMap = stages.stream().collect(Collectors.toMap(BaseStage::level, stage -> stage));
        LogManager.getLogger().debug("Checking toArray()...");
        long startTime = System.currentTimeMillis();
        Integer[] keysTmp = new Integer[stages.size()];
        HStream<Integer> keyst = HStream.of(stagesMap.keySet().toArray(keysTmp));
        LogManager.getLogger().debug("Time toArray(): " + (System.currentTimeMillis() - startTime));
        LogManager.getLogger().debug("Checking map()...");
        startTime = System.currentTimeMillis();
        HStream<Integer> keys = stages.toHStream().map(BaseStage::level);
        LogManager.getLogger().debug("Time map(): " + (System.currentTimeMillis() - startTime));
        int minLevel = keys.reduce(Math::min).orElseThrow(IllegalStateException::new);
        int maxLevel = keys.reduce(Math::max).orElseThrow(IllegalStateException::new);
        for (int i = minLevel; i <= maxLevel; i++) {
            int tmp = i;
            stages.findAll(stage -> stage.level().equals(tmp)).forEach(stage -> observable = observable.doOnNext(profile -> {
                try {
                    stage.run(profile);
                    Object o = stage.run(profile);
                    Platform.runLater(() -> stage.onExit(o));
                } catch (Throwable th) {
                    stage.onError(th);
                    throw th;
                }
            }));
        }

        observable.subscribeOn(Schedulers.io()).subscribe(new Observer<Profile>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull Profile profile) {
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
