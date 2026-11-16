package luke.paxels.compat.aether.paxel;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.item.AetherHasCustomDamageType;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemToolPaxelGravitite extends ItemToolPaxelAether implements AetherHasCustomDamageType {

    public ItemToolPaxelGravitite(String name, String namespaceId, int id, ToolMaterial toolMaterial) {
        super(name, namespaceId, id, 3, toolMaterial, null);
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
        Block<?> block = world.getBlockType(blockPos);
        Block<?> nextBlock = world.getBlockType(new TilePos(blockPos).up());
        if (player != null
            && !player.isSneaking()
            || block.getHardness() < 0
            || (nextBlock != Blocks.COBWEB && !nextBlock.hasTag(BlockTags.PLACE_OVERWRITES))
            || !(block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
                || block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL)
            )
        ) {
            return false;
        }

        if (EnvironmentHelper.isMultiplayerClient()) {
            return true;
        }

        @Nullable TileEntity tileEntity = world.getTileEntity(blockPos);
        int metadata = world.getBlockData(blockPos);
        world.removeTileEntity(blockPos);
        world.setBlockTypeNotify(blockPos, Blocks.AIR);
        EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(
            world,
            (double) blockPos.x() + 0.5F, (double) blockPos.y() + 0.5F, (double) blockPos.z() + 0.5F,
            block.id(), metadata, tileEntity
        );
        entityFloatingBlock.setHasRemovedBlock(true);
        world.entityJoinedWorld(entityFloatingBlock);
        selfStack.damageItem(1, player);
        return true;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.FALL;
    }
}
