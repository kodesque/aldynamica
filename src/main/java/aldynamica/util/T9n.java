package aldynamica.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.io.Files;

import aldynamica.api.EnumLangSection;
import aldynamica.root.Main;
import aldynamica.util.ExceptionManager.ContextBuilder;
import aldynamica.util.ExceptionManager.EnumSpecial;
import aldynamica.util.ExceptionManager.ExceptionContext;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;

public class T9n {

    public static Path LANG = Paths.get("src/main/resources/assets/aldynamica/lang");

    public interface ILocGroupValues {

        String getKey();
    }

    //possible future addition: color/custom font handling (look into AbyssalCraft api)

    public enum EnumGroups {
        EXCEPTION("exception", EnumSuffixes.TEXT),
        CONTEXT("context", EnumSuffixes.TEXT);

        private String key;
        private EnumSuffixes suffix;

        EnumGroups(String key, EnumSuffixes suffix) {
            this.key = key;
            this.suffix = suffix;
        };

        public String getKey() {
            return this.key;
        }

        public String getSuffix() {
            return this.suffix.getKey();
        }

        public enum Exception implements ILocGroupValues {
            LIGHT("light"),
            MIDDLE("middle"),
            SEVERE("severe"),
            NON_ROLLBACK("non_rollback"),
            ROLLBACK("rollback"),
            REPORT("report"),
            CAUSE("cause");

            private String key;

            Exception(String key) {
                this.key = key;
            };

            @Override
            public String getKey() {
                return this.key;
            }
        }

        public enum Context implements ILocGroupValues {
            PLAYER("player"),
            ITEM("item"),
            BLOCK("block"),
            TILE("tile"),
            ENTITY("entity"),
            WORLD("world"),
            POSITION("position");

            private String key;

            Context(String key) {
                this.key = key;
            };

            @Override
            public String getKey() {
                return this.key;
            }
        }
    }

    public enum EnumSuffixes {
        NAME("name"),
        TEXT("text");

        private String key;

        EnumSuffixes(String key) {
            this.key = key;
        };

        public String getKey() {
            return this.key;
        }
    }

    public static String simpleKey(String name, EnumLangSection section) {

        TextComponentTranslation comp = new TextComponentTranslation(Main.MODID + "." + name);

        String fullkey = null;

        switch (section) {
            case CREATIVE_TAB: fullkey = "tab." + name;
            case BLOCKS: fullkey = "tile." + name;
            case ITEMS: fullkey = "item." + name;
        }

        fullkey = fullkey + ".name";

        if (!I18n.hasKey(fullkey)) {
            refreshFile(fullkey, section.getName());
        }

        return comp.getFormattedText();
    }

    public static TextComponentTranslation getComp(EnumGroups group, ILocGroupValues type) {

        TextComponentTranslation comp = new TextComponentTranslation(group.getKey() + "." + Main.MODID + "." + type.getKey() + "." + group.getSuffix());

        if (!I18n.hasKey(comp.getKey())) {
            refreshFile(comp.getKey(), group.getKey().toUpperCase());
        }

        return comp;
    }

    public static String getLoc(EnumGroups group, ILocGroupValues type) {

        TextComponentTranslation comp = new TextComponentTranslation(group.getKey() + "." + Main.MODID + "." + type.getKey() + "." + group.getSuffix());

        if (!I18n.hasKey(comp.getKey())) {
            refreshFile(comp.getKey(), group.getKey().toUpperCase());
        }

        return comp.getKey();
    }

    public static void refreshFile(String fullkey, String section) {

        File[] files = LANG.toFile().listFiles();

        if (files == null)
            return;

        for (File file : files) {

            try {

                List<String> lines = Files.readLines(file, StandardCharsets.UTF_8);

                String header = "#" + section;

                Integer start = null;

                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).trim().equalsIgnoreCase(header)) {
                        start = i;
                        break;
                    }
                }

                if (start == null)
                    return;

                int end = lines.size();

                for (int i = start + 1; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("#")) {
                        end = i;
                        break;
                    }
                }

                Integer insertIndex = null;

                for (int i = start + 1; i < end; i++) {
                    if (lines.get(i).trim().isEmpty()) {
                        insertIndex = i;
                        break;
                    }
                }

                if (insertIndex == null) {
                    insertIndex = end;
                }

                lines.add(insertIndex, fullkey + "=");

                String content = String.join(System.lineSeparator(), lines);

                Files.write(content, file, StandardCharsets.UTF_8);

            } catch (IOException e) {

                ExceptionContext ctx = new ContextBuilder()
                        .addSource(EnumSpecial.REFRESHING)
                        .build();

                ExceptionManager.handle(e, ctx);
            }
        }
    }
}
