package io.aleosiss.physicalRecipes.fabric

import io.aleosiss.physicalRecipes.PhysicalRecipes
import io.aleosiss.physicalRecipes.client.PhysicalRecipesClient
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.mixin.item.client.HeldItemRendererMixin

class PhysicalRecipesFabric : ModInitializer {
  override fun onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.

    // Run our common setup.
    PhysicalRecipes.init()
    HeldItemRendererMixin
  }
}
