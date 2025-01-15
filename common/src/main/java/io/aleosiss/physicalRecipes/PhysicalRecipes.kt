package io.aleosiss.physicalRecipes

import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.event.events.common.PlayerEvent
import io.aleosiss.physicalRecipes.item.Items
import net.minecraft.world.entity.player.Player

object PhysicalRecipes {
  const val MOD_ID: String = "physical_recipes"

  fun init() {
    // Write common init code here.
    LifecycleEvent.SETUP.register {
      Items.register()
    }


    PlayerEvent.PLAYER_JOIN.register { player: Player ->
      // Do something when a player joins the world.
      if(player.inventory.isEmpty) {
        player.addItem(Items.BLANK_PHYSICAL_RECIPE.get().defaultInstance)
        player.addItem(Items.RECIPE_BOOK.get().defaultInstance)
      }
    }
  }
}
