package ru.hzerr.loaders.font;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.log.LogManager;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

// May be deprecated?
public class FontLoader {

    private static final HMap<FontData, Font> CACHE = new HashHMap<>();
    private static final String PREFIX = "runtime/style/fonts/";

    public static Font load(Fonts font, double size) throws IOException, FontFormatException {
        final FontData key = FontData.newFontData(PREFIX + font.getPath(), size);
        if (CACHE.noContainsKey(key)) {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream fontStream = FontLoader.class.getClassLoader().getResourceAsStream(PREFIX + font.getPath());
            if (fontStream == null) throw new IllegalArgumentException("Font " + key + " can't be found");
            if (ge.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, fontStream))) {
                LogManager.getLogger().debug("Font " + font.getPath() + " was registered");
                InputStream fontStream2 = FontLoader.class.getClassLoader().getResourceAsStream(PREFIX + font.getPath());
                Font f = Font.loadFont(fontStream2, size);
                System.out.println("Font family: " + f.getFamily());
                return CACHE.putAndGet(key, f);
            } else throw new InternalError("Font can't be registered");
        } else
            return CACHE.get(key);
    }

    @SafeVarargs
    public static <T extends Labeled> void install(Fonts font, double size, T... children) throws IOException, FontFormatException {
        for (T labeled: children) {
            labeled.setFont(load(font, size));
        }
    }

    private static class FontData {

        private final String fullPath;
        private final double size;

        public FontData(String fullPath, double size) {
            this.fullPath = fullPath;
            this.size = size;
        }

        public static FontData newFontData(String path, double size) {
            return new FontData(path, size);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            FontData fontData = (FontData) o;

            return new EqualsBuilder().append(size, fontData.size).append(fullPath, fontData.fullPath).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(fullPath).append(size).toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("fullPath", fullPath)
                    .append("size", size)
                    .toString();
        }
    }
}
