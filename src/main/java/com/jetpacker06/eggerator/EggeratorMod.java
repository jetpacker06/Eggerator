package com.jetpacker06.eggerator;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(EggeratorMod.MOD_ID)
public class EggeratorMod {
    public static final String MOD_ID = "eggerator";

    public EggeratorMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.register(eventBus);
        eventBus.addListener(this::onCreativeModeTabBuildContentsEvent);
        MinecraftForge.EVENT_BUS.register(this);
    }
    public void onCreativeModeTabBuildContentsEvent(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(() -> ModRegistry.EGGERATOR_BLOCK_ITEM.get());
        }
    }
}
