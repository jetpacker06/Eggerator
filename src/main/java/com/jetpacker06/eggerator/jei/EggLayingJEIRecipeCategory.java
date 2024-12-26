package com.jetpacker06.eggerator.jei;

import com.jetpacker06.eggerator.EggeratorMod;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"FieldCanBeLocal"})
public class EggLayingJEIRecipeCategory implements IRecipeCategory<EggSource> {

    public static final ResourceLocation UID = new ResourceLocation(EggeratorMod.MOD_ID, "egg_laying");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EggeratorMod.MOD_ID, "textures/gui/egg_laying_recipe.png");

    private final IDrawable background;
    private final IDrawable icon;

    public EggLayingJEIRecipeCategory(IGuiHelper guiHelper, int width, int height) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, width, height);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Items.EGG));
    }

    @Override
    public @NotNull ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Class<EggSource> getRecipeClass() {
        return EggSource.class;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("label.eggerator.egglayingrecipelabel");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(@NotNull EggSource recipe, @NotNull IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getCatalystItem());
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(Items.EGG));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull EggSource recipe, @NotNull IIngredients ingredients) {
        int yLevel = background.getHeight() / 2 - 10;
        recipeLayout.getItemStacks().init(0, true, 32, yLevel);
        recipeLayout.getItemStacks().init(1, false, 98, yLevel);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
