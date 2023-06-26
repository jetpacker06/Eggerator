package com.jetpacker06.eggerator;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid=EggeratorMod.MOD_ID)
public class GameRulesHelper {
    private static GameRules gameRules;

    public static final Supplier<Integer> entityCramThreshold = () -> gameRules.getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        gameRules = event.getLevel().getLevelData().getGameRules();
    }
}
