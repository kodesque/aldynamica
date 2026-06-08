package aldynamica.util;

import java.util.ArrayList;

import aldynamica.root.Main;
import aldynamica.util.T9n.EnumGroups;
import aldynamica.util.T9n.EnumGroups.Context;
import aldynamica.util.T9n.ILocGroupValues;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ExceptionManager {

    public static class ExceptionContext {

        private EnumSpecial source;
        private EntityPlayer player;
        private ItemStack stack;
        private IBlockState state;
        private TileEntity tile;
        private Entity entity;
        private World world;
        private BlockPos pos;

        public ExceptionContext() {}

        public String getAllCauses() {

            if (this.source != null)
                return ExceptionManager.getSpecial(this.source);

            String string = new StringBuilder()
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.PLAYER) +  (this.player != null ? this.player.getName() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.ITEM) + (this.stack != null ? this.stack.getDisplayName() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.BLOCK) + (this.state != null ? this.state.getBlock() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.TILE) + (this.tile != null ? this.tile.getDisplayName() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.ENTITY) + (this.entity != null ? this.entity.getName() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.WORLD) + (this.world != null ? this.world.getProviderName() : ""))
                    .append(T9n.getLoc(EnumGroups.CONTEXT, Context.POSITION) + (this.pos != null ? this.pos : ""))
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

        public ContextBuilder addWorld(World world) {
            this.current.world = world;
            return this;
        }

        public ContextBuilder addPos(BlockPos pos) {
            this.current.pos = pos;
            return this;
        }

        public ContextBuilder addSource(EnumSpecial source) {
            this.current.source = source;
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
            causedbys.add(t.toString());
            t = t.getCause();
        }

        return causedbys;
    }

    private static String compileReport(ExceptionContext ctx) {

        ILocGroupValues severity = EnumGroups.Exception.MIDDLE;

        if (ctx.source != null) {
            severity = EnumGroups.Exception.LIGHT;
        }

        String report = new StringBuilder()
                .append(Main.MODID + ":")
                .append(severity)
                .append(T9n.getLoc(EnumGroups.EXCEPTION, EnumGroups.Exception.CAUSE))
                .append(ctx.getAllCauses())
                .append(T9n.getLoc(EnumGroups.EXCEPTION, EnumGroups.Exception.REPORT))
                .append(Main.GITHUB)
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

    public enum EnumSpecial {
        REMAPPING("Found a missing ID without any possible replacement."),
        SORTING("Caught an exception while sorting items in the creative tab."),
        REFRESHING("Tried to refresh the localization file in a non-development environment.");

        private String message;

        EnumSpecial(String message) {
            this.message = message;
        };

        public String getMessage() {
            return this.message;
        }

    }

    public static String getSpecial(EnumSpecial type) {
        return new String(Main.MODID + ": " + type.getMessage() + " " + "This is not an expected behavior. Please report this to the mod author: " + Main.GITHUB);
    }

}
