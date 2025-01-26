package io.aleosiss.physicalRecipes.neoforge

import io.aleosiss.physicalRecipes.PhysicalRecipes
import io.aleosiss.physicalRecipes.PhysicalRecipes.MOD_ID
import net.neoforged.fml.common.Mod

@Mod(MOD_ID)
class PhysicalRecipesNeoForge {
  init {
    // Run our common setup.
    PhysicalRecipes.init()
  }
}
