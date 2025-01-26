package io.aleosiss.physicalRecipes.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.mojang.brigadier.context.CommandContext
import dev.architectury.event.events.common.CommandRegistrationEvent
import io.aleosiss.physicalRecipes.item.Items
import io.aleosiss.physicalRecipes.item.Recipes
import io.aleosiss.physicalRecipes.network.PhysicalRecipeComponents
import io.aleosiss.physicalRecipes.network.RecipeContent
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput

class Commands {
  companion object {
    fun register() {
      CommandRegistrationEvent.EVENT.register { dispatcher, context, selection ->
        dispatcher.register(
          literal<CommandSourceStack>("physical_recipes")
            .then(literal<CommandSourceStack>("give")
              .then(literal<CommandSourceStack>("recipe")
                .then(argument<CommandSourceStack, ItemInput>("item", ItemArgument.item(context))
                  .executes { ctx -> createRecipeItem(ctx) }
      ))))
      }
    }

    private fun createRecipeItem(ctx: CommandContext<CommandSourceStack>): Int {
      val key = ItemArgument.getItem(ctx, "item").item.builtInRegistryHolder().key()
      val recipe = Recipes.MAP[key]?.random()

      return if (recipe != null) {
        val recipeItem = Items.BLANK_PHYSICAL_RECIPE
          .get()
          .defaultInstance.copy()
          .apply { set(PhysicalRecipeComponents.RECIPE_CONTENT, RecipeContent(recipe)) }
        ctx.source.player!!.addItem(recipeItem)
        1
      } else {
        0
      }
    }
  }
}