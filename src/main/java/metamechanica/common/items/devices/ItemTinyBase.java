package metamechanica.common.items.devices;

import metamechanica.common.templates.ItemBase;

public class ItemTinyBase extends ItemBase {

    public ItemTinyBase(String name) {
        super("device" + "tiny" + "_" + name);

        this.setMaxStackSize(1);
    }

}
