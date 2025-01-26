package io.aleosiss.physicalRecipes.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.world.item.crafting.ShapelessRecipe")
public interface ShapelessRecipeAccessor {
    @Accessor("result")
    ItemStack getResult();
}
