package luke.paxels.compat.aether.paxel;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.item.AetherItems.AMBROSIUM;

public class ItemToolPaxelHolystone extends ItemToolPaxelAether {

    public ItemToolPaxelHolystone(String name, String namespaceId, int id, ToolMaterial toolMaterial) {
        super(name, namespaceId, id, 3, toolMaterial, null);
    }

    @Override
    public boolean onBlockDestroyed(@NotNull ItemStack selfStack, @NotNull World world, @NotNull Mob mob, @NotNull Block<?> removedBlock, @NotNull TilePosc blockPos, @NotNull Side side) {
        if (removedBlock.getHardness() > 0.0F || this.isSilkTouch()) {
            selfStack.damageItem(1, mob);
        }
        if (!EnvironmentHelper.isMultiplayerClient() && itemRand.nextInt(16) == 0 && removedBlock.getHardness() > 0.0F) {
            world.dropItem(blockPos, new ItemStack(AMBROSIUM, 1));
        }
        return true;
    }


}
