package com.jetpacker06.eggerator.jei;

import com.jetpacker06.eggerator.EggeratorMod;
import com.jetpacker06.eggerator.ModRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class EggeratorJEIPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(EggeratorMod.MOD_ID, "jei_plugin");
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        reg.addRecipeCategories(new EggLayingJEIRecipeCategory(reg.getJeiHelpers().getGuiHelper(), 144, 44));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration reg) {
        reg.addRecipes(List.of(EggSource.values()), EggLayingJEIRecipeCategory.UID);

        reg.addIngredientInfo(new ItemStack(ModRegistry.EGGERATOR_BLOCK_ITEM.get()), VanillaTypes.ITEM, new TranslatableComponent("info.eggerator.eggerator"));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(new ItemStack(ModRegistry.EGGERATOR_BLOCK_ITEM.get()), EggLayingJEIRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(Items.CHICKEN_SPAWN_EGG), EggLayingJEIRecipeCategory.UID);
    }
}
