package io.aleosiss.physicalRecipes.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.function.Predicate;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin {
    @Inject(method = "toNbt", at = @At("HEAD"), cancellable = true)
    public void onSave(CallbackInfoReturnable<CompoundTag> cir) {
        cir.setReturnValue(new CompoundTag());
    }

    @Inject(method = "fromNbt", at = @At("HEAD"), cancellable = true)
    public void onLoad(CompoundTag compoundTag, Predicate<ResourceKey<Recipe<?>>> predicate, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "addRecipes", at = @At("HEAD"), cancellable = true)
    public void send(Collection<RecipeHolder<?>> collection, ServerPlayer serverPlayer, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
    }

    @Inject(method = "copyOverData", at = @At("HEAD"), cancellable = true)
    public void onCopy(ServerRecipeBook serverRecipeBook, CallbackInfo ci) {
        ci.cancel();
    }
}
