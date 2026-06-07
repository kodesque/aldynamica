package aldynamica.util;

import aldynamica.root.Main;
import net.minecraft.util.text.TextComponentTranslation;

public class TranslationManager {

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

    public static TextComponentTranslation getComp(EnumGroups group, ILocGroupValues type) {
        return new TextComponentTranslation(Main.MODID + "." + group.getKey() + "." + type.getKey() + "." + group.getSuffix());
    }

    public static String getLoc(EnumGroups group, ILocGroupValues type) {
        return new TextComponentTranslation(Main.MODID + "." + group.getKey() + "." + type.getKey() + "." + group.getSuffix()).getFormattedText();
    }

}
