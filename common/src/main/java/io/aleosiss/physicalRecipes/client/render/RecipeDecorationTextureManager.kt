package io.aleosiss.physicalRecipes.client.render

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.saveddata.maps.MapDecoration

@Environment(EnvType.CLIENT)
class RecipeDecorationTextureManager(textureManager: TextureManager): TextureAtlasHolder(
  textureManager,
  ResourceLocation.withDefaultNamespace("textures/atlas/map_decorations.png"),
  ResourceLocation.withDefaultNamespace("map_decorations")
) {
  fun get(mapDecoration: MapDecoration): TextureAtlasSprite {
    return this.getSprite(mapDecoration.spriteLocation)
  }
}