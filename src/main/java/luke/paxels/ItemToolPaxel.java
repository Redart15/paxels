package luke.paxels;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ItemToolPaxel extends ItemToolPickaxe implements ItemPaxelCommon{

    public ItemToolPaxel(String name, String namespaceId, int id, ToolMaterial toolMaterial) {
        super(name, namespaceId, id, toolMaterial);
        this.setMaxDamage(toolMaterial.getDurability() * 2);
    }

    @Override
    public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
        return block.hasTag(BlockTags.MINEABLE_BY_PICKAXE)
            || block.hasTag(BlockTags.MINEABLE_BY_AXE)
            || block.hasTag(BlockTags.MINEABLE_BY_SHOVEL)
            || block.hasTag(BlockTags.MINEABLE_BY_HOE) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public boolean canHarvestBlock(@NotNull ItemStack selfStack, @NotNull Mob mob, @NotNull Block<?> block) {
        int miningLevel = miningLevels.getOrDefault(block, -1);
        if (miningLevel != -1) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(BlockTags.MINEABLE_BY_PICKAXE)
                || block.hasTag(BlockTags.MINEABLE_BY_AXE)
                || block.hasTag(BlockTags.MINEABLE_BY_SHOVEL)
                || block.hasTag(BlockTags.MINEABLE_BY_HOE);
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
           BlockTags.MINEABLE_BY_PICKAXE
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
