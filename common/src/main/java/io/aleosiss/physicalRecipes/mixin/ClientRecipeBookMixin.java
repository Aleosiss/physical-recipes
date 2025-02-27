package io.aleosiss.physicalRecipes.mixin;

import net.minecraft.client.ClientRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Inject(method = "rebuildCollections", at = @At("HEAD"), cancellable = true)
    public void onSetup(CallbackInfo ci) {
        ci.cancel();
    }
}
