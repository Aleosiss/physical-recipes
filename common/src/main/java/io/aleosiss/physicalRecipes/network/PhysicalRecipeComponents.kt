package io.aleosiss.physicalRecipes.network

import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import java.util.function.UnaryOperator

object PhysicalRecipeComponents {

  val RECIPE_CONTENT: DataComponentType<RecipeContent> = register("recipe_contents") {
      it.persistent(RecipeContent.CODEC).networkSynchronized(RecipeContent.STREAM_CODEC).cacheEncoding()
  }

  val RECIPE_BOOK_CONTENT: DataComponentType<RecipeBookContent> = register("recipe_book_contents") {
      it.persistent(RecipeBookContent.CODEC).networkSynchronized(RecipeBookContent.STREAM_CODEC).cacheEncoding()
  }

  private fun <T> register(
    string: String,
    unaryOperator: UnaryOperator<DataComponentType.Builder<T>>
  ): DataComponentType<T> {
    return Registry.register(
      BuiltInRegistries.DATA_COMPONENT_TYPE, string, unaryOperator.apply(DataComponentType.builder()).build()
    )
  }
}


//e: file:///C:/Github/physical-recipes/common/src/main/java/io/aleosiss/physicalRecipes/network/PhysicalRecipeComponents.kt:12:62 Argument type mismatch:
// actual type is
// 'net.minecraft.network.codec.StreamCodec<CapturedType(in net.minecraft.network.RegistryFriendlyByteBuf),
// net.minecraft.world.item.component.BundleContents>',
//
// but
// 'net.minecraft.network.codec.StreamCodec<in net.minecraft.network.RegistryFriendlyByteBuf!,
//
// io.aleosiss.physicalRecipes.network.RecipeContent!>!' was expected.
