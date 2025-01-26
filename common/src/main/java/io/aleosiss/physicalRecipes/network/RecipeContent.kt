package io.aleosiss.physicalRecipes.network

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.aleosiss.physicalRecipes.PhysicalRecipes.LOG
import io.aleosiss.physicalRecipes.item.Recipes.Companion.getResultKey
import io.aleosiss.physicalRecipes.mixin.ShapedRecipeAccessor
import io.aleosiss.physicalRecipes.mixin.ShapelessRecipeAccessor
import io.aleosiss.physicalRecipes.mixin.SingleItemRecipeAccessor
import io.aleosiss.physicalRecipes.mixin.SmithingTransformRecipeAccessor
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.item.crafting.ShapelessRecipe
import net.minecraft.world.item.crafting.SingleItemRecipe
import net.minecraft.world.item.crafting.SmithingTransformRecipe


class RecipeContent(val recipe: Recipe<*>?) {


  companion object {
    val EMPTY: RecipeContent = RecipeContent(null)

    val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RecipeContent> = StreamCodec.of(
        /* streamEncoder = */ { buf, recipeContent -> recipeContent.recipe?.let { Recipe.STREAM_CODEC.encode(buf, it) } },
        /* streamDecoder = */ { buf -> Recipe.STREAM_CODEC.decode(buf).let { RecipeContent(it) } }
      )

    val CODEC: Codec<RecipeContent> = RecordCodecBuilder.create { instance ->
      instance.group(Recipe.CODEC.fieldOf("recipe").forGetter(RecipeContent::recipe)).apply(instance, ::RecipeContent)
    }
  }
}

// extension function on Recipe that returns the recipe result key, parsing most subtypes
fun Recipe<*>.output(): ItemStack? {
  return when (this) {
    is ShapedRecipe -> (this as ShapedRecipeAccessor).result
    is ShapelessRecipe -> (this as ShapelessRecipeAccessor).result
    is SingleItemRecipe -> (this as SingleItemRecipeAccessor).result
    is SmithingTransformRecipe -> (this as SmithingTransformRecipeAccessor).result
    else -> { LOG.warn("Recipe was not any of the known categories; was ${this::class.java}"); null }
  }
}

class RecipeBookContent(val recipes: List<Recipe<*>>) {

  companion object {
    val EMPTY = RecipeBookContent(emptyList())

    val CODEC: Codec<RecipeBookContent> = RecordCodecBuilder.create { instance ->
      instance.group(Recipe.CODEC.listOf().fieldOf("recipes").forGetter(RecipeBookContent::recipes)).apply(instance, ::RecipeBookContent)
    }

    val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RecipeBookContent> = StreamCodec.of(
        { buf, component ->
          buf.writeVarInt(component.recipes.size)
          component.recipes.forEach { recipe -> Recipe.STREAM_CODEC.encode(buf, recipe) }
        },
        { buf ->
          val size = buf.readVarInt()
          val recipes = mutableListOf<Recipe<*>>()
          repeat(size) { Recipe.STREAM_CODEC.decode(buf).let { recipe -> recipes.add(recipe) } }
          RecipeBookContent(recipes)
        }
      )
  }
}