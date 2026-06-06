package aldynamica.api;

public enum EnumSortGroup {

    WORLDGEN,
    ITEMS;

    public int priority() {
        return this.ordinal();
    }

}
