package metamechanica.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import metamechanica.common.blocks.scene.BlockOreTransform;
import metamechanica.common.entities.EntityBillet;
import metamechanica.init.BlockInit;
import metamechanica.init.SoundInit;
import metamechanica.network.Network;
import metamechanica.network.packets.PacketSpawnParticles;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class RecordEventUtil {

    static int wait = 160;

    private static final HashMap<UUID, TimePos> buildTasks = new HashMap<UUID, TimePos>();

    public static void startEvent(EntityPlayer player, BlockPos bracePos, World world) {
        if (!buildTasks.containsKey(player.getUniqueID())) {
            buildTasks.put(player.getUniqueID(), new TimePos(bracePos, world, wait));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {

            if (!buildTasks.isEmpty()) {

                for (UUID id : buildTasks.keySet()) {
                    TimePos timepos = buildTasks.get(id);
                    ValidBlocks bundle = findValidBlocks(timepos.world, timepos.pos);

                    if (timepos != null && bundle != null) {

                        timepos.ticksLeft--;

                        //TODO: add a record playing sound, adjust overall event speed respectively. Ignore for now
                        //for now 160 overall with change every 2 seconds

                        if (timepos.ticksLeft / 40 == 0) {

                            int last = timepos.world.getBlockState(bundle.orePos.get(0)).getValue(BlockOreTransform.STAGE);
                            int next = last + 1 < 4 ? last + 1 : last;
                            //TODO: add different particles for different stages. Ignore for now
                            //TODO: add different sound effects for different stages. Ignore for now

                            timepos.world.setBlockState(bundle.orePos.get(0), BlockInit.ORE_TRANSFORM.getDefaultState().withProperty(BlockOreTransform.STAGE, next));

                            if (timepos.world.getMinecraftServer().getPlayerList().getPlayerByUUID(id) != null) {
                                Network.sendToPlayerMP(new PacketSpawnParticles(bundle.orePos.get(0), EnumParticleTypes.FLAME), timepos.world.getMinecraftServer().getPlayerList().getPlayerByUUID(id));
                            }

                            bundle.orePos.remove(0);
                        } else if (timepos.ticksLeft == 0) {
                            timepos.world.destroyBlock(bundle.clayPos, false);

                            EntityBillet billet = new EntityBillet(timepos.world);
                            timepos.world.spawnEntity(billet);
                        }

                        buildTasks.remove(id);
                    }
                }
            }

        }

    }


    private static class TimePos {
        BlockPos pos;
        World world;
        int ticksLeft;

        TimePos(BlockPos pos, World world, int ticksLeft) {
            this.pos = pos;
            this.world = world;
            this.ticksLeft = ticksLeft;
        }
    }

    private static class ValidBlocks {
        ArrayList<BlockPos> orePos;
        BlockPos clayPos;

        ValidBlocks(ArrayList<BlockPos> orePos, BlockPos clayPos) {
            this.orePos = orePos;
            this.clayPos = clayPos;
        }

    }

    public static ValidBlocks findValidBlocks(World world, BlockPos playerPos) {

        ArrayList<BlockPos> orePos = new ArrayList<BlockPos>();
        BlockPos clayPos = null;

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {

                    BlockPos pos = playerPos.add(x, y, z);

                    if (world.getBlockState(pos).getBlock().equals(Blocks.CLAY) && clayPos == null) {
                        clayPos = pos;
                    }  else if (world.getBlockState(pos).getBlock().equals(Blocks.IRON_ORE) && orePos.size() < 4) {
                        orePos.add(pos);
                    }

                    if (orePos.size() > 0 && clayPos != null)
                        return new ValidBlocks(orePos, clayPos);

                }
            }
        }

        return null;
    }

}
