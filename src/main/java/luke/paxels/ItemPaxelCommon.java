package luke.paxels;

import luke.paxels.compat.aether.PaxelAetherMod;
import luke.paxels.compat.commandly.PaxelCommandlyRules;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redart15.commandly.veincapitator.VeinMining;

import java.util.Random;

public interface ItemPaxelCommon {

    default boolean beforeBlockDestroyed(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @NotNull Player player,
        @NotNull Block<?> block,
        @NotNull TilePosc blockPos,
        @NotNull Side side,
        @NotNull Tag<Block<?>>... tags
    ) {
        if (!world.isClientSide && world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
            Block<?> b = world.getBlockType(blockPos);
            if (Block.hasLogicClass(b, BlockLogicLog.class)) {
                return !(new TreecapitatorHelper(world, blockPos.x(), blockPos.y(), blockPos.z(), player)).chopTree();
            }
        }
        if (!world.isClientSide && PaxelCommandlyRules.canVeinMine(world) && !player.isSneaking()) {
            return !VeinMining
                .veinMining(world, selfStack, blockPos, player)
                .setDropCause(this.resolveSilkTouch(player))
                .setMiningTags(tags)
                .mine(block, side);
        }
        return true;
    }

    default EnumDropCause resolveSilkTouch(@NotNull Player player){
        if(FabricLoader.getInstance().isModLoaded("aether")){
            return PaxelAetherMod.resolveSilkTouch(player);
        }
        return EnumDropCause.PROPER_TOOL;
    }

    default boolean onUseOnBlock(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @Nullable Player player,
        @NotNull TilePosc blockPos,
        @NotNull Side side,
        double xHit, double yHit
    ) {
        return this.shovelBlock(selfStack, world, player, blockPos, side);
    }

    default void onUseByActivator(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @NotNull TileEntityActivator activator,
        @NotNull Random random,
        @NotNull TilePosc blockPos,
        @NotNull Direction direction,
        double offX, double offY, double offZ
    ) {
        TilePosc tilePosc = blockPos.add(direction, new TilePos(blockPos));
        this.shovelBlock(selfStack, world, null, tilePosc, direction.side());
    }

    default boolean shovelBlock(
        @NotNull ItemStack itemstack,
        @NotNull World world,
        @Nullable Player entityplayer,
        @NotNull TilePosc blockPos,
        @NotNull Side side
    ){
        TilePosc blockAbovePos = blockPos.add(Direction.UP, new TilePos(blockPos));
        Block<?> block = world.getBlockType(blockPos);
        Block<?> blockAbove = world.getBlockType(blockAbovePos);
        if (side != Side.BOTTOM
            && blockAbove == Blocks.AIR
            && (block == Blocks.GRASS || block == Blocks.DIRT
            || block == Blocks.GRASS_RETRO || block == Blocks.FARMLAND_DIRT)
        ) {
            world.playBlockSoundEffect(entityplayer, blockPos.x() + 0.5F, blockPos.y() + 0.5F, blockPos.z() + 0.5F, block, EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockTypeNotify(blockPos, Blocks.PATH_DIRT);
                itemstack.damageItem(1, entityplayer);
            }
            return true;
        }
        return false;
    }
}
