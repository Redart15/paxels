package luke.paxels.compat.aether.paxel;

import luke.paxels.ItemPaxelCommon;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;

import java.util.Random;

import static net.minecraft.core.item.tool.ItemToolPickaxe.miningLevels;


public class ItemToolPaxelAether extends ItemToolPickaxeAether implements ItemPaxelCommon {

    public ItemToolPaxelAether(String name, String namespaceId, int id, int damageDealt, ToolMaterial toolMaterial, Tag<Block<?>> tagEffectiveAgainst) {
        super(name, namespaceId, id, toolMaterial);
        this.setMaxDamage(toolMaterial.getDurability() * 2);
    }

    @Override
    public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public boolean canHarvestBlock(@NotNull ItemStack selfStack, @NotNull Mob mob, @NotNull Block<?> block) {
        int miningLevel = miningLevels.getOrDefault(block, -1);
        if (miningLevel != -1) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
                || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
        }
    }

    @Override
    public boolean beforeBlockDestroyed(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @NotNull Player player,
        @NotNull Block<?> block,
        @NotNull TilePosc blockPos,
        @NotNull Side side
    ) {
        return this.beforeBlockDestroyed(
            selfStack, world, player, block, blockPos, side,
            AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE
        );
    }

    @Override
    public boolean onUseOnBlock(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @Nullable Player player,
        @NotNull TilePosc blockPos,
        @NotNull Side side,
        double xHit, double yHit
    ) {
        return ItemPaxelCommon.super.onUseOnBlock(selfStack, world, player, blockPos, side, xHit, yHit);
    }

    public boolean shovelBlock(
        @NotNull ItemStack itemstack,
        @NotNull World world,
        @Nullable Player entityplayer,
        @NotNull TilePosc blockPos,
        @NotNull Side side
    ) {
        TilePosc blockAbovePos = blockPos.add(Direction.UP, new TilePos(blockPos));
        Block<?> block = world.getBlockType(blockPos);
        Block<?> blockAbove = world.getBlockType(blockAbovePos);
        if (side != Side.BOTTOM
            && blockAbove == Blocks.AIR
            && (block == Blocks.GRASS || block == Blocks.DIRT
            || block == Blocks.GRASS_RETRO || block == Blocks.FARMLAND_DIRT)
        ){
            world.playBlockSoundEffect(entityplayer, blockPos.x() + 0.5F, blockPos.y() + 0.5F, blockPos.z() + 0.5F, block, EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockTypeNotify(blockPos, Blocks.PATH_DIRT);
                itemstack.damageItem(1, entityplayer);
            }
            return true;
        }
        if (side != Side.BOTTOM
            && blockAbove == Blocks.AIR
            && (block == AetherBlocks.GRASS_AETHER || block == AetherBlocks.DIRT_AETHER)
        ) {
            world.playBlockSoundEffect(entityplayer, blockPos.x() + 0.5F, blockPos.y() + 0.5F, blockPos.z() + 0.5F, block, EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockTypeNotify(blockPos, AetherBlocks.PATH_DIRT_AETHER);
                itemstack.damageItem(1, entityplayer);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUseByActivator(
        @NotNull ItemStack selfStack,
        @NotNull World world,
        @NotNull TileEntityActivator activator,
        @NotNull Random random,
        @NotNull TilePosc blockPos,
        @NotNull Direction direction,
        double offX, double offY, double offZ
    ) {
        ItemPaxelCommon.super.onUseByActivator(selfStack, world, activator, random, blockPos, direction, offX, offY, offZ);
    }

}
