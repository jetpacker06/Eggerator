package com.jetpacker06.eggerator.eggerator;

import com.jetpacker06.eggerator.GameRulesHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        assert pStack.getTag() != null;
        int chickens = getChickens(pStack);
        if (chickens == GameRulesHelper.entityCramThreshold.get()) {
            return InteractionResult.PASS;
        }
        pStack.getTag().putInt("chickens", chickens + 1);
        pInteractionTarget.remove(Entity.RemovalReason.DISCARDED);

        pPlayer.setItemInHand(pUsedHand, pStack);
        return InteractionResult.SUCCESS;
    }

    public static void ensureEggeratorTags(ItemStack pStack) {
        if (!pStack.hasTag()) {
            pStack.setTag(new CompoundTag());
        }
        CompoundTag tag = pStack.getTag();
        assert tag != null;
        if (!tag.contains("chickens")) {
            tag.putInt("chickens", 0);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip,
                                @NotNull TooltipFlag pFlag) {
        if (!pStack.hasTag()) {
            pTooltip.add(new TranslatableComponent("tooltip.eggerator.empty"));
            return;
        }
        assert pStack.getTag() != null;
        pTooltip.add(new TranslatableComponent("tooltip.eggerator.chickens")
                .append(String.valueOf(pStack.getTag().getInt("chickens")))
        );
    }

    public static int getChickens(ItemStack pStack) {
        assert pStack.getTag() != null;
        return pStack.getTag().getInt("chickens");
    }
    public static int getEggs(ItemStack pStack) {
        assert pStack.getTag() != null;
        return pStack.getTag().getInt("eggs");
    }
}
