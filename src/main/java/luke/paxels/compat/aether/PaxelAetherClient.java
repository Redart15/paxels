package luke.paxels.compat.aether;

import luke.paxels.PaxelClient;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.event.defs.ClientEvents;

import static luke.paxels.PaxelMod.KEY;

public class PaxelAetherClient {
    private PaxelAetherClient(){}

    public static void onInitializeClient() {
        ClientEvents.ITEM_MODEL_RELOAD.listen(KEY, PaxelAetherClient::initItemModels);
    }

    public static void initItemModels(ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelAetherMod.TOOL_PAXEL_SKYROOT, "paxels:item/tool_paxel_skyroot"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelAetherMod.TOOL_PAXEL_HOLYSTONE, "paxels:item/tool_paxel_holystone"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelAetherMod.TOOL_PAXEL_ZANITE, "paxels:item/tool_paxel_zanite"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelAetherMod.TOOL_PAXEL_GRAVITITE, "paxels:item/tool_paxel_gravitite"));
        dispatcher.addDispatch(PaxelClient.setUpModel(PaxelAetherMod.TOOL_PAXEL_VALKYRIE, "paxels:item/tool_paxel_valkyrie"));
    }

}
