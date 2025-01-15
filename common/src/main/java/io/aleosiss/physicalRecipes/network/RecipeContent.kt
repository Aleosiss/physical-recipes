package io.aleosiss.physicalRecipes.network

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.crafting.Recipe


class RecipeContent(private val recipe: Recipe<*>?): TooltipComponent {
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
class RecipeBookContent(private val recipes: List<Recipe<*>>) {

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