package luke.paxels.compat.aether;

import luke.paxels.compat.aether.paxel.*;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;
import teamport.aether.item.item_tool.AetherToolMaterial;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.dependency.Key;

import static luke.paxels.PaxelItems.itemKey;
import static luke.paxels.PaxelMod.MOD_ID;

public class PaxelAetherMod{
    public static Item TOOL_PAXEL_SKYROOT;
    public static Item TOOL_PAXEL_HOLYSTONE;
    public static Item TOOL_PAXEL_ZANITE;
    public static Item TOOL_PAXEL_GRAVITITE;
    public static Item TOOL_PAXEL_VALKYRIE;
    private static boolean hasInit = false;

    private PaxelAetherMod(){}

    public static void onInitialized() {
        Key key = Key.of("compatAether");
        CommonEvents.RECIPES_READY.listen(key, PaxelAetherMod::onRecipesReady);
        CommonEvents.RECIPES_NAMESPACE_INIT.listen(key, PaxelAetherMod::initNamespaces);
        CommonEvents.AFTER_ITEM_INIT.listen(key, PaxelAetherMod::init);
    }

    public static @NotNull EnumDropCause resolveSilkTouch(@NotNull Player player) {
        return PlayerUtil.isSilkTouchPendant(player) ? EnumDropCause.SILK_TOUCH : EnumDropCause.PROPER_TOOL;
    }

    public static void init() {
        TOOL_PAXEL_SKYROOT = new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> AetherItems.TOOL_SWORD_SKYROOT))
            .build(new ItemToolPaxelAether("tool.paxel.skyroot", itemKey("tool_paxel_skyroot"), 22000, 3, AetherToolMaterial.SKYROOT, null));

        TOOL_PAXEL_HOLYSTONE = new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> AetherItems.TOOL_SWORD_HOLYSTONE))
            .build(new ItemToolPaxelHolystone("tool.paxel.holystone", itemKey("tool_paxel_holystone"), 22001, AetherToolMaterial.HOLYSTONE));

        TOOL_PAXEL_ZANITE = new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> AetherItems.TOOL_SWORD_ZANITE))
            .build(new ItemToolPaxelZanite("tool.paxel.zanite", itemKey("tool_paxel_zanite"), 22002, AetherToolMaterial.ZANITE));

        TOOL_PAXEL_GRAVITITE = new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> AetherItems.TOOL_SWORD_GRAVITITE))
            .build(new ItemToolPaxelGravitite("tool.paxel.gravitite", itemKey("tool_paxel_gravitite"), 22003, AetherToolMaterial.GRAVITITE));

        TOOL_PAXEL_VALKYRIE = new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> AetherItems.TOOL_SWORD_VALKYRIE))
            .build(new ItemToolPaxelValkyrie("tool.paxel.valkyrie", itemKey("tool_paxel_valkyrie"), 22004, AetherToolMaterial.VALKYRIE));
    }


    public static void onRecipesReady() {
        RecipeBuilderShaped paxelTemplate = new RecipeBuilderShaped(MOD_ID, "A5P", " S ", " S ");
        paxelTemplate
            .addInput('A', AetherItems.TOOL_AXE_SKYROOT)
            .addInput('5', AetherItems.TOOL_SHOVEL_SKYROOT)
            .addInput('P', AetherItems.TOOL_PICKAXE_SKYROOT)
            .addInput('S', AetherItems.STICK_SKYROOT)
            .create("skyroot_paxel", new ItemStack(TOOL_PAXEL_SKYROOT));

        paxelTemplate
            .addInput('A', AetherItems.TOOL_AXE_HOLYSTONE)
            .addInput('5', AetherItems.TOOL_SHOVEL_HOLYSTONE)
            .addInput('P', AetherItems.TOOL_PICKAXE_HOLYSTONE)
            .addInput('S', AetherItems.STICK_SKYROOT)
            .create("holystone_paxel", new ItemStack(TOOL_PAXEL_HOLYSTONE));

        paxelTemplate
            .addInput('A', AetherItems.TOOL_AXE_ZANITE)
            .addInput('5', AetherItems.TOOL_SHOVEL_ZANITE)
            .addInput('P', AetherItems.TOOL_PICKAXE_ZANITE)
            .addInput('S', AetherItems.STICK_SKYROOT)
            .create("zanite_paxel", new ItemStack(TOOL_PAXEL_ZANITE));

        paxelTemplate
            .addInput('A', AetherItems.TOOL_AXE_GRAVITITE)
            .addInput('5', AetherItems.TOOL_SHOVEL_GRAVITITE)
            .addInput('P', AetherItems.TOOL_PICKAXE_GRAVITITE)
            .addInput('S', AetherItems.STICK_SKYROOT)
            .create("gravitite_paxel", new ItemStack(TOOL_PAXEL_GRAVITITE));

        paxelTemplate
            .addInput('A', AetherItems.TOOL_AXE_VALKYRIE)
            .addInput('5', AetherItems.TOOL_SHOVEL_VALKYRIE)
            .addInput('P', AetherItems.TOOL_PICKAXE_VALKYRIE)
            .addInput('S', AetherItems.STICK_SKYROOT)
            .create("valkyrie_paxel", new ItemStack(TOOL_PAXEL_VALKYRIE));
    }

    public static void initNamespaces() {
        RecipeBuilder.initNameSpace(MOD_ID);
        LookupFuelFurnace.instance.addFuelEntry(TOOL_PAXEL_SKYROOT.id, 500);
        Registries.ITEM_GROUPS.getItem("paxels:tool_paxels").add(TOOL_PAXEL_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("paxels:tool_paxels").add(TOOL_PAXEL_HOLYSTONE.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("paxels:tool_paxels").add(TOOL_PAXEL_ZANITE.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("paxels:tool_paxels").add(TOOL_PAXEL_GRAVITITE.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("paxels:tool_paxels").add(TOOL_PAXEL_VALKYRIE.getDefaultStack());
    }

}
