package aldynamica.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import aldynamica.root.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class ExceptionManager {

    public static TextComponentTranslation type1_loc = new TextComponentTranslation("aldynamica.exception.light");

    //"A predictable error has been caught. There may be a minor logic issue."

    //(if the issue is caught explicitly with local try-catch and handled through EnumSource)

    public static  TextComponentTranslation type2_loc = new TextComponentTranslation("aldynamica.exception.middle");

    //"A partially predictable error has been caught. There may be a moderate logic issue."

    //(if the issue is caught by EventWrapper and handled through this class)

    public static  TextComponentTranslation type3_loc = new TextComponentTranslation("aldynamica.exception.severe");

    //"A completely unpredictable error has been caught. There may be a major logic issue."

    //(if the issue cannot be caught and prevented by any means)

    public static TextComponentTranslation non_rollback_loc = new TextComponentTranslation("aldynamica.exception.non_rollback");

    //"Preventing a game crash without interfering with gameplay."

    public static TextComponentTranslation rollback_loc = new TextComponentTranslation("aldynamica.exception.rollback");

    //"Preventing a game crash required interfering with gameplay. If you have lost valuable resources, you can reclaim them using a console command. It is recommended to do this **after** the error has been fixed."

    public static TextComponentTranslation report_loc = new TextComponentTranslation("aldynamica.exception.report");

    //"Please report it to the mod author."

    public static TextComponentTranslation cause_loc = new TextComponentTranslation("aldynamica.exception.cause");

    //"Possible causes of the exception:"

    public enum EnumSource {

        CREATIVE_SORT("sorting items in the creative tab");

        private final String message;

        EnumSource(String message) {
            this.message = message;
        }
    }

    public static class ExceptionContext {

        private EntityPlayer player;
        private ItemStack stack;
        private IBlockState state;
        private TileEntity tile;
        private Entity entity;

        public ExceptionContext() {}

        public String getAllCauses() {

            String string = new StringBuilder()
                    .append("Player: " +  (this.player != null ? this.player.getName() : ""))
                    .append("Item: " + (this.stack != null ? this.stack.getDisplayName() : ""))
                    .append("Block: " + (this.state != null ? this.state.getBlock() : ""))
                    .append("Machine: " + (this.tile != null ? this.tile.getDisplayName() : ""))
                    .append("Entity: " + (this.entity!= null ? this.entity.getName() : ""))
                    .toString();

            return string;
        }

    }

    public static class ContextBuilder {

        private final ExceptionContext current = new ExceptionContext();

        public ContextBuilder addPlayer(EntityPlayer player) {
            this.current.player = player;
            return this;
        }

        public ContextBuilder addStack(ItemStack stack) {
            this.current.stack = stack;
            return this;
        }

        public ContextBuilder addState(IBlockState state) {
            this.current.state = state;
            return this;
        }

        public ContextBuilder addTile(TileEntity tile) {
            this.current.tile = tile;
            return this;
        }

        public ContextBuilder addEntity(Entity entity) {
            this.current.entity = entity;
            return this;
        }

        public ExceptionContext build() {
            return this.current;
        }

    }

    //handler start

    public static void handle(Exception e, ExceptionContext ctx) {

        ArrayList<String> documented = ExceptionManager.extractCauses(e);

        String report = ExceptionManager.compileReport(ctx);

        ExceptionManager.send(documented, report, ctx);

    }

    private static ArrayList<String> extractCauses(Exception e) {

        ArrayList<String> causedbys = new ArrayList<String>();

        Throwable t = e.getCause();

        while (t != null) {
            System.out.println("Caused by: " + t);
            t = t.getCause();
        }

        return causedbys;
    }

    private static String compileReport(ExceptionContext ctx) {

        String report = new StringBuilder()
                .append(Main.MODID + ":")
                .append(type2_loc.getFormattedText())
                .append(cause_loc.getFormattedText())
                .append(ctx.getAllCauses())
                .append(report_loc.getFormattedText())
                .append("https://github.com/kodesque/aldynamica/issues")
                .toString();

        return report;

    }

    private static void send(ArrayList<String> documented, String report, ExceptionContext ctx) {

        if (ctx.player != null) {

            for (String string : documented) {
                ctx.player.sendMessage(new TextComponentString(string));
            }

            ctx.player.sendMessage(new TextComponentString(report));
        } else {

            for (String string : documented) {
                System.out.println(string);
            }

            System.out.println(report);

        }

    }

}
