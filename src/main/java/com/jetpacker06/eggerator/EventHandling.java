package com.jetpacker06.eggerator;

import com.jetpacker06.eggerator.eggerator.EggeratorBlockEntity;
import com.jetpacker06.eggerator.eggerator.EggeratorBlockItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=EggeratorMod.MOD_ID)
public class EventHandling {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getPlacedBlock().getBlock() == ModRegistry.EGGERATOR.get())) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        ItemStack usedStack = player.getMainHandItem().getItem() == ModRegistry.EGGERATOR_BLOCK_ITEM.get() ?
            player.getMainHandItem()
                : player.getOffhandItem();
        EggeratorBlockItem.ensureEggeratorTags(usedStack);
        assert usedStack.getTag() != null;
        EggeratorBlockEntity be = (EggeratorBlockEntity) event.getEntity().level().getBlockEntity(event.getPos());
        assert be != null;
        be.setChickens(usedStack.getTag().getInt("chickens"));
    }
}
