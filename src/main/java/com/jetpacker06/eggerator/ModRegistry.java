package com.jetpacker06.eggerator;

import com.jetpacker06.eggerator.eggerator.EggeratorBlock;
import com.jetpacker06.eggerator.eggerator.EggeratorBlockEntity;
import com.jetpacker06.eggerator.eggerator.EggeratorBlockItem;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EggeratorMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EggeratorMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EggeratorMod.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, EggeratorMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        DATA_COMPONENT_TYPES.register(eventBus);
    }

    public static RegistryObject<Block> EGGERATOR;
    public static RegistryObject<Item> EGGERATOR_BLOCK_ITEM;
    public static RegistryObject<BlockEntityType<EggeratorBlockEntity>> EGGERATOR_BLOCK_ENTITY;
    public static RegistryObject<DataComponentType<Integer>> CHICKENS_COUNT;

    static {
        EGGERATOR = BLOCKS.register("eggerator", () -> new EggeratorBlock(BlockBehaviour.Properties.of()
                .sound(SoundType.METAL)
                .strength(6)
                .explosionResistance(9)
                .requiresCorrectToolForDrops()
        ));
        EGGERATOR_BLOCK_ITEM = ITEMS.register("eggerator",
                () -> new EggeratorBlockItem(EGGERATOR.get(), new Item.Properties())
        );
        EGGERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("eggerator", () ->
                BlockEntityType.Builder.of(EggeratorBlockEntity::new, ModRegistry.EGGERATOR.get()).build(null));

        CHICKENS_COUNT = DATA_COMPONENT_TYPES.register("chickens", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());
    }
}
