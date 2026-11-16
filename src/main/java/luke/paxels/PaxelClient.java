package luke.paxels;

import luke.paxels.compat.aether.PaxelAetherClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.event.defs.ClientEvents;

import static luke.paxels.PaxelMod.*;
import static net.minecraft.client.render.item.model.ItemModelDispatcher.*;

public class PaxelClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LOGGER.info("PaxelsClient initialized.");
        ClientEvents.ITEM_MODEL_RELOAD.listen(KEY, PaxelClient::initItemModels);
        if(FabricLoader.getInstance().isModLoaded("aether")){
            PaxelAetherClient.onInitializeClient();
        }
    }

    public static ItemModelStandard setUpModel(Item item, String namespace){
        ItemModelStandard model = new ItemModelStandard(item).setIcon(namespace);
        return model.setDisplayPos("firstperson_righthand", HANDHELD_FIRST_PERSON_RIGHT_HAND)
            .setDisplayPos("firstperson_lefthand", HANDHELD_FIRST_PERSON_LEFT_HAND)
            .setDisplayPos("thirdperson_righthand", HANDHELD_THIRD_PERSON_RIGHT_HAND)
            .setDisplayPos("thirdperson_lefthand", HANDHELD_THIRD_PERSON_LEFT_HAND);
    }

    public static void initItemModels(ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_WOOD, "paxels:item/tool_paxel_wood"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_STONE, "paxels:item/tool_paxel_stone"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_IRON, "paxels:item/tool_paxel_iron"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_GOLD, "paxels:item/tool_paxel_gold"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_DIAMOND, "paxels:item/tool_paxel_diamond"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelItems.TOOL_PAXEL_STEEL, "paxels:item/tool_paxel_steel"));

    }
}
