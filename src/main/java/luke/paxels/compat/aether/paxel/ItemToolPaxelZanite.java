package luke.paxels.compat.aether.paxel;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.AetherGlobals;
import teamport.aether.block.AetherBlockTags;

public class ItemToolPaxelZanite extends ItemToolPaxelAether {

    public ItemToolPaxelZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 3, enumtoolmaterial, null);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        if (itemstack == null) return 0f;
        if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) &&
            !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL) &&
            !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)) {
            return 1.0F;
        }
        float durabilityProgress = ((float) itemstack.getMetadata() / this.getMaxDamage());
        float baseEfficiency = this.material.getEfficiency(false);
        return MathHelper.lerp(baseEfficiency, baseEfficiency * AetherGlobals.ZANITE_MULTIPLIER, durabilityProgress);
    }
}
