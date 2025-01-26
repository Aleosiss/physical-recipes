package io.aleosiss.physicalRecipes

import com.mojang.logging.LogUtils
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.event.events.common.PlayerEvent
import io.aleosiss.physicalRecipes.command.Commands
import io.aleosiss.physicalRecipes.item.Items
import io.aleosiss.physicalRecipes.item.Recipes
import net.minecraft.world.entity.player.Player
import org.slf4j.Logger

object PhysicalRecipes {
  const val MOD_ID: String = "physical_recipes"
  val LOG: Logger = LogUtils.getLogger()

  fun init() {
    LifecycleEvent.SETUP.register {
      Items.register()
      Recipes.register()
      Commands.register()
    }

    LifecycleEvent.SERVER_BEFORE_START.register { server ->
      Recipes.build(server)
    }

    PlayerEvent.PLAYER_JOIN.register { player: Player ->
      player.server!!.let { server -> server.execute { debugAddItems(player) } }
    }
  }

  private fun debugAddItems(player: Player) {
    if (player.inventory.isEmpty) {
      val recipeItemStack = Items.BLANK_PHYSICAL_RECIPE.get().defaultInstance
      val recipeBookItemStack = Items.RECIPE_BOOK.get().defaultInstance
      player.addItem(recipeItemStack)
      player.addItem(recipeBookItemStack)
    }
  }
}
