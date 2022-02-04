package ru.hzerr.config.profile.settings;

import ru.hzerr.config.profile.Structure;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HFile;

import java.io.IOException;
import java.io.Serializable;

public abstract class Background implements Serializable {

    private static final long serialVersionUID = 1L;
    protected BaseFile bg;

    public Background() {
    }

    public abstract void init(Structure profileStructure);

    public abstract void setBackgroundFile(BaseFile forCopy) throws IOException;
    public final BaseFile getBackgroundFile() {
        return bg;
    }

    public final boolean exists() { return bg.exists(); }
    public final boolean notExists() { return bg.notExists(); }

    public static class McSkillBackground extends Background {

        private static final String MC_SKILL_BG_NAME = "BG.png";
        private static final String MC_SKILL_EXTENSION = "png";

        @Override
        public void init(Structure profileStructure) {
            bg = new HFile(profileStructure.getAssetsDir(), MC_SKILL_BG_NAME);
        }

        @Override
        public void setBackgroundFile(BaseFile forCopy) throws IOException {
            if (forCopy.getExtension().equals(MC_SKILL_EXTENSION)) {
                bg.create();
                forCopy.copyToFile(bg);
            }
        }
    }

    public static class MythicalWorldBackground extends Background {

        private static final String MYTHICAL_WORLD_BG_NAME = "dialog.png";
        private static final String MYTHICAL_WORLD_EXTENSION = "png";

        @Override
        public void init(Structure profileStructure) {
            bg = new HFile(profileStructure.getAssetsDir(), MYTHICAL_WORLD_BG_NAME);
        }

        @Override
        public void setBackgroundFile(BaseFile forCopy) throws IOException {
            if (forCopy.getExtension().equals(MYTHICAL_WORLD_EXTENSION)) {
                bg.create();
                forCopy.copyToFile(bg);
            }
        }
    }
}
