package io.aleosiss.physicalRecipes.client.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import io.aleosiss.physicalRecipes.network.PhysicalRecipeComponents
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.client.renderer.MapRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.MapItem
import net.minecraft.world.level.saveddata.maps.MapId
import org.joml.Matrix4f

class RecipeRenderer {
  fun render(poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int, stack: ItemStack) {
    if(stack.has(PhysicalRecipeComponents.RECIPE_CONTENT)) {
      renderRecipe(poseStack, buffer, packedLight, stack)
    } else if (stack.has(PhysicalRecipeComponents.RECIPE_BOOK_CONTENT)) {
      //renderRecipeBook(poseStack, buffer, packedLight, stack)
    } else {
      throw IllegalStateException("ItemStack is not a recipe or recipe book")
    }
  }

  private fun renderRecipe(
    poseStack: PoseStack,
    buffer: MultiBufferSource,
    packedLight: Int,
    stack: ItemStack
  ) {
    manipulatePoseStack(poseStack)
    val vertexConsumer = buffer.getBuffer(EMPTY_RECIPE)
    val matrix4f = poseStack.last().pose()
    manipulateVertexConsumer(vertexConsumer, matrix4f, packedLight)
    if (mapItemSavedData != null) {
      val mapRenderer: MapRenderer = this.minecraft.getMapRenderer()
      mapRenderer.extractRenderState(mapId, mapItemSavedData, this.mapRenderState)
      mapRenderer.render(this.mapRenderState, poseStack, buffer, false, packedLight)
    }
  }

  private fun renderRecipeBook(
    poseStack: PoseStack,
    buffer: MultiBufferSource,
    packedLight: Int,
    stack: ItemStack
  ) {
    TODO()
  }

  private fun manipulateVertexConsumer(
    vertexConsumer: VertexConsumer,
    matrix4f: Matrix4f,
    packedLight: Int
  ) {
    vertexConsumer.addVertex(matrix4f, -7.0f, 135.0f, 0.0f).setColor(-1).setUv(0.0f, 1.0f).setLight(packedLight)
    vertexConsumer.addVertex(matrix4f, 135.0f, 135.0f, 0.0f).setColor(-1).setUv(1.0f, 1.0f).setLight(packedLight)
    vertexConsumer.addVertex(matrix4f, 135.0f, -7.0f, 0.0f).setColor(-1).setUv(1.0f, 0.0f).setLight(packedLight)
    vertexConsumer.addVertex(matrix4f, -7.0f, -7.0f, 0.0f).setColor(-1).setUv(0.0f, 0.0f).setLight(packedLight)
  }

  private fun manipulatePoseStack(poseStack: PoseStack) {
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0f))
    poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f))
    poseStack.scale(0.38f, 0.38f, 0.38f)
    poseStack.translate(-0.5f, -0.5f, 0.0f)
    poseStack.scale(0.0078125f, 0.0078125f, 0.0078125f)
  }
}