package io.aleosiss.physicalRecipes.item

import com.mojang.logging.LogUtils
import io.aleosiss.physicalRecipes.network.output
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeHolder
import org.slf4j.Logger
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

class Recipes {
  private data class RecipeWithResultKey(val recipe: Recipe<*>, val result: ResourceKey<Item>)

  companion object {
    lateinit var MAP: Map<ResourceKey<Item>, List<Recipe<*>>>

    private val LOG: Logger = LogUtils.getLogger()

    @JvmStatic
    fun register() {}

    @JvmStatic
    fun build(server: MinecraftServer) = buildUsingAccessors(server)


    @Suppress("unused")
    private fun buildUsingReflection(server: MinecraftServer) {
      val invalidRecipes: MutableMap<String, MutableList<Recipe<*>>> = mutableMapOf()

      val recipes: MutableCollection<RecipeHolder<*>> = server.recipeManager.recipes
      val recipeList: List<RecipeWithResultKey> = recipes.mapNotNull {
        val recipe: Recipe<*> = it.value
        val field: KProperty1<Recipe<*>, ItemStack> = findResultField(recipe)
          ?: run { handleInvalidRecipe(recipe, invalidRecipes); return@mapNotNull null
        }
        field.isAccessible = true
        RecipeWithResultKey(recipe, field.get(recipe).item.builtInRegistryHolder().key())
      }
      MAP = recipeList.groupBy({ it.result }, { it.recipe })
    }

    private fun buildUsingAccessors(server: MinecraftServer) {
      val invalidRecipes: MutableMap<String, MutableList<Recipe<*>>> = mutableMapOf()

      val recipes: MutableCollection<RecipeHolder<*>> = server.recipeManager.recipes
      val recipeList: List<RecipeWithResultKey> = recipes.mapNotNull {
        val recipe: Recipe<*> = it.value
        val resultKey = recipe.output()?.item?.builtInRegistryHolder()?.key()
          ?: run { handleInvalidRecipe(recipe, invalidRecipes); return@mapNotNull null
        }
        RecipeWithResultKey(recipe, resultKey)
      }
      MAP = recipeList.groupBy({ it.result }, { it.recipe })
    }

    private fun handleInvalidRecipe(
      recipe: Recipe<*>,
      invalidRecipes: MutableMap<String, MutableList<Recipe<*>>>
    ) {
      val type = recipe::class.simpleName ?: "no simplename"
      invalidRecipes.getOrPut(type) { mutableListOf() }.add(recipe)
      LOG.warn("Could not find result field in recipe: $type")
    }

    // reflect up the class hierarchy to find the result field
    private fun findResultField(recipe: Recipe<*>): KProperty1<Recipe<*>, ItemStack>? {
      var currentClass: KClass<*>? = recipe::class
      while (currentClass != null) {
        currentClass.memberProperties
          .filter { it.returnType.classifier == ItemStack::class }
          .find { it.name == "result" }
          ?.let { return it as KProperty1<Recipe<*>, ItemStack> }
        currentClass = currentClass.superclasses.firstOrNull()
      }
      return null
    }

    @JvmStatic
    fun getResultKey(recipe: Recipe<*>): ResourceKey<Item>? {
      val field: KProperty1<Recipe<*>, ItemStack> = findResultField(recipe)?: return null
      field.isAccessible = true
      return field.get(recipe).item.builtInRegistryHolder().key()
    }
  }


}