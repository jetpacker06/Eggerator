package com.jetpacker06.eggerator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(EggeratorMod.MOD_ID)
public class EggeratorMod {
    public static final String MOD_ID = "eggerator";

    public EggeratorMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
