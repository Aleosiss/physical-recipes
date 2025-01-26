package io.aleosiss.physicalRecipes.item

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.aleosiss.physicalRecipes.PhysicalRecipes.MOD_ID
import io.aleosiss.physicalRecipes.network.PhysicalRecipeComponents
import io.aleosiss.physicalRecipes.network.RecipeBookContent
import io.aleosiss.physicalRecipes.network.RecipeContent
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BundleItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.BundleContents


object Items {
  @JvmStatic
  val ITEMS: DeferredRegister<Item> = DeferredRegister.create(MOD_ID, Registries.ITEM)


  @JvmStatic @Suppress("unused") val RECIPE_KEY: ResourceKey<Item> by lazy { BLANK_PHYSICAL_RECIPE.key }
  @JvmStatic @Suppress("unused") val RECIPE_BOOK_KEY: ResourceKey<Item> by lazy { RECIPE_BOOK.key }
  
  @JvmStatic
  val BLANK_PHYSICAL_RECIPE: RegistrySupplier<Item> = ITEMS.register("recipe") {
    RecipeItem(Item.Properties()
      .setId("recipe")
      .component(PhysicalRecipeComponents.RECIPE_CONTENT, RecipeContent.EMPTY))
  }

  @JvmStatic
  val RECIPE_BOOK: RegistrySupplier<Item> = ITEMS.register("recipe_book") {
    RecipeBookItem(Item.Properties()
      .setId("recipe_book")
      .component(PhysicalRecipeComponents.RECIPE_BOOK_CONTENT, RecipeBookContent.EMPTY))
  }

  fun register() {
    ITEMS.register()
  }
}

private fun Item.Properties.setId(itemId: String): Item.Properties = setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, itemId)))
