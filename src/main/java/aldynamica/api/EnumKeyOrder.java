package aldynamica.api;

public enum EnumKeyOrder {
    CREATIVE_TAB("CREATIVE TAB", "creative"),
    BLOCKS("BLOCKS", "tile"),
    ITEMS("ITEMS", "item"),
    EXCEPTIONS("EXCEPTIONS", "exception");

    private String category;
    private String entry;

    private EnumKeyOrder(String category, String entry) {
        this.category = category;
        this.entry = entry;
    }

    public String getName() {
        return this.name;
    }

    public int index() {
        return this.ordinal();
    }

}
