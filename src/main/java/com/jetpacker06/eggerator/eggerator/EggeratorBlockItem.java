package com.jetpacker06.eggerator.eggerator;

import com.jetpacker06.eggerator.GameRulesHelper;
import com.jetpacker06.eggerator.ModRegistry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class EggeratorBlockItem extends BlockItem {
    public EggeratorBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer,
                         @NotNull LivingEntity pInteractionTarget, @NotNull InteractionHand pUsedHand) {
        if (!(pInteractionTarget instanceof Chicken chicken)) {
            return InteractionResult.PASS;
        }
        if (chicken.isBaby()) {
            return InteractionResult.PASS;
        }
        ensureEggeratorTags(pStack);

        int chickens = getChickens(pStack);
        if (chickens >= GameRulesHelper.entityCramThreshold.get()) {
            return InteractionResult.PASS;
        }

        DataComponentPatch patch = DataComponentPatch.builder().set(ModRegistry.CHICKENS_COUNT.get(), chickens + 1).build();
        pStack.applyComponents(patch);

        pInteractionTarget.remove(Entity.RemovalReason.DISCARDED);

        pPlayer.setItemInHand(pUsedHand, pStack);
        return InteractionResult.SUCCESS;
    }

    public static void ensureEggeratorTags(ItemStack pStack) {
        Integer chickens = pStack.getComponents().get(ModRegistry.CHICKENS_COUNT.get());
        if (chickens == null) {
            DataComponentPatch patch = DataComponentPatch.builder().set(ModRegistry.CHICKENS_COUNT.get(), 0).build();
            pStack.applyComponents(patch);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        Integer chickens = pStack.getComponents().get(ModRegistry.CHICKENS_COUNT.get());
        chickens = chickens == null ? 0 : chickens;
        pTooltipComponents.add(Component.translatable("tooltip.eggerator.chickens")
                .append(": " + chickens)
        );
    }

    public static int getChickens(ItemStack pStack) {
        Integer chickens = pStack.getComponents().get(ModRegistry.CHICKENS_COUNT.get());
        return chickens == null ? 0 : chickens;
    }
}
