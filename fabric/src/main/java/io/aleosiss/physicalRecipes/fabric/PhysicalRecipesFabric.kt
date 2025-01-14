package io.aleosiss.physicalRecipes.fabric

import io.aleosiss.physicalRecipes.PhysicalRecipes
import net.fabricmc.api.ModInitializer

class PhysicalRecipesFabric : ModInitializer {
  override fun onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.

    // Run our common setup.
    PhysicalRecipes.init()
  }
}
