package io.aleosiss.physicalRecipes.neoforge

import dev.architectury.platform.hooks.EventBusesHooks
import io.aleosiss.physicalRecipes.PhysicalRecipes
import io.aleosiss.physicalRecipes.PhysicalRecipes.MOD_ID
import net.neoforged.bus.EventBus
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod

@Mod(PhysicalRecipes.MOD_ID)
class PhysicalRecipesNeoForge {
  init {
    // Run our common setup.
    PhysicalRecipes.init()
  }
}
