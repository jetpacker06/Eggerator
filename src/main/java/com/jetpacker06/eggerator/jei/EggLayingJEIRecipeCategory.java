package com.jetpacker06.eggerator.jei;

import com.jetpacker06.eggerator.EggeratorMod;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"FieldCanBeLocal"})
public class EggLayingJEIRecipeCategory implements IRecipeCategory<EggSource> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(EggeratorMod.MOD_ID, "textures/gui/egg_laying_recipe.png");
    public static final RecipeType<EggSource> recipeType = RecipeType.create(EggeratorMod.MOD_ID, "egg_laying", EggSource.class);

    private final IDrawable background;
    private final IDrawable icon;

    public EggLayingJEIRecipeCategory(IGuiHelper guiHelper, int width, int height) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, width, height);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.EGG));
    }

    @Override
    public @NotNull RecipeType<EggSource> getRecipeType() {
        return recipeType;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("label.eggerator.egg_laying_recipe_label");
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull EggSource recipe, @NotNull IFocusGroup focuses) {
        int yLevel = background.getHeight() / 2 - 9;
        builder.addSlot(RecipeIngredientRole.INPUT, 33, yLevel)
                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getCatalystItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, yLevel)
                .addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.EGG));
    }
}
