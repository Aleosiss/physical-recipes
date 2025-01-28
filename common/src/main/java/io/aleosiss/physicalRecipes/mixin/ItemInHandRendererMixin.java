package io.aleosiss.physicalRecipes.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import io.aleosiss.physicalRecipes.client.render.RecipeRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.aleosiss.physicalRecipes.item.Items.isModItem;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @ModifyExpressionValue(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;has(Lnet/minecraft/core/component/DataComponentType;)Z")
    private boolean inject(boolean original, AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        if(isModItem(stack)) { return true; }

        return original;
    }

    @Inject(method = "renderMap", at = @At("HEAD"), cancellable = true)
    private void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ItemStack stack, CallbackInfo ci) {
        if (!isModItem(stack)) return;

        ci.cancel();


        RecipeRenderer.render(poseStack, buffer, packedLight, stack);
    }


}
