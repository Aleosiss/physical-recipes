package io.aleosiss.physicalRecipes.item

import io.aleosiss.physicalRecipes.network.PhysicalRecipeComponents
import io.aleosiss.physicalRecipes.network.RecipeContent
import io.aleosiss.physicalRecipes.network.output
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class RecipeItem(properties: Properties) : Item(properties) {
  override fun appendHoverText(
    itemStack: ItemStack,
    tooltipContext: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    itemStack.components.get(PhysicalRecipeComponents.RECIPE_CONTENT)?.let { recipeContent: RecipeContent ->
      recipeContent.recipe?.let { recipe ->
        val component = recipe.output()?.styledHoverName ?: Component.translatable("item.physical_recipes.recipe.unknown")
        tooltipComponents.add(component)
      }
    }
  }
}