package io.aleosiss.physicalRecipes.network

import com.mojang.serialization.Codec
import io.aleosiss.physicalRecipes.PhysicalRecipes.MOD_ID
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries


object PhysicalRecipeComponents {

  val RECIPE_CONTENT: DataComponentType<RecipeContent> = register("recipe_contents") {
    persistent(RecipeContent.CODEC)
    networkSynchronized(RecipeContent.STREAM_CODEC)
    cacheEncoding()
  }

  val RECIPE_BOOK_CONTENT: DataComponentType<RecipeBookContent> = register("recipe_book_contents") {
    persistent(RecipeBookContent.CODEC)
    networkSynchronized(RecipeBookContent.STREAM_CODEC)
    cacheEncoding()
  }

  private inline fun <reified T> register(
    id: String,
    noinline configure: DataComponentType.Builder<T>.() -> Unit
  ): DataComponentType<T> {
    return Registry.register(
      BuiltInRegistries.DATA_COMPONENT_TYPE,
      "$MOD_ID:$id",
      DataComponentType.builder<T>().apply(configure).build()
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
