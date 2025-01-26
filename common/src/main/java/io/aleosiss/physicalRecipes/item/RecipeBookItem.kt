package io.aleosiss.physicalRecipes.item

import io.aleosiss.physicalRecipes.network.PhysicalRecipeComponents
import io.aleosiss.physicalRecipes.network.RecipeBookContent
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class RecipeBookItem(properties: Properties) : Item(properties) {

  override fun appendHoverText(
    itemStack: ItemStack,
    tooltipContext: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    itemStack.components.get(PhysicalRecipeComponents.RECIPE_BOOK_CONTENT)?.let { bookContent: RecipeBookContent ->
        val numRecipes = bookContent.recipes.size
        val component = Component.translatable("item.physical_recipes.recipe_book.num_recipes", numRecipes)
        tooltipComponents.add(component)
      }
    }
}
