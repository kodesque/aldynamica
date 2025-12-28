package essentialcraft.util;

import java.util.ArrayList;

import javax.annotation.Nullable;

import essentialcraft.common.tiles.TileEntityWheelFiller;
import essentialcraft.init.BlockInit;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureUtil {

    public enum funcType {
        ASSEMBLE,
        DISASSEMBLE
    }

    public enum checkType {
        PREPARE,
        MAKE_SURE
    }

    public static boolean handleStructure(World worldIn, BlockPos corePos, EnumFacing side, @Nullable funcType funcType, @Nullable checkType checkType) {

        ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();

        BlockPos anchorPos = corePos;
        BlockPos targetPos = null;
        int sidesPass = 0;

        handleSides:
            for (EnumFacing facing : EnumFacing.VALUES) {

                if (side != facing && side != facing.getOpposite()) {
                    sides.add(facing);
                } else {
                    continue;
                }

                targetPos = corePos.offset(facing);

                if (checkType != null && proceedWithCheck(checkType, targetPos, corePos, worldIn)) {
                    sidesPass++;
                } else if (funcType != null && proceedWithFunc(funcType, targetPos, corePos, worldIn)) {
                    sidesPass++;
                } else {
                    break handleSides;
                }

                if (sidesPass == 4) {

                    for (int x = 0; x < 2; x++) {
                        for (int y = 2; y < 4; y++) {
                            anchorPos = corePos.offset(sides.get(x));
                            targetPos = anchorPos.offset(sides.get(y));

                            if (checkType != null && proceedWithCheck(checkType, targetPos, corePos, worldIn)) {
                                sidesPass++;
                            } else if (funcType != null && proceedWithFunc(funcType, targetPos, corePos, worldIn)) {
                                sidesPass++;
                            } else {
                                break handleSides;
                            }

                            if (sidesPass == 8)
                                return true;

                        }
                    }
                }
            }

        return false;

    }

    public static boolean proceedWithCheck(checkType type, BlockPos targetPos, @Nullable BlockPos corePos, World worldIn) {
        switch (type) {
            case PREPARE: {
                if (worldIn.getBlockState(targetPos).getBlock().isReplaceable(worldIn, targetPos))
                    return true;
                break;
            }
            case MAKE_SURE: {
                if (worldIn.getBlockState(targetPos).getBlock().equals(BlockInit.WHEEL_FILLER))
                    return true;
                break;
            }
        }
        return false;
    }

    public static boolean proceedWithFunc(funcType type, BlockPos targetPos, @Nullable BlockPos corePos, World worldIn) {
        switch (type) {
            case ASSEMBLE: {
                worldIn.setBlockState(targetPos, BlockInit.WHEEL_FILLER.getDefaultState());

                TileEntity tile = worldIn.getTileEntity(targetPos);
                if (tile != null) {
                    ((TileEntityWheelFiller)tile).setCorePos(corePos);
                }

                return true;
            }
            case DISASSEMBLE: {

                if (worldIn.getBlockState(targetPos).getBlock().equals(BlockInit.WHEEL_FILLER)) {
                    worldIn.destroyBlock(targetPos, false);
                }
                return true;

            }
        }
        return false;
    }

}
