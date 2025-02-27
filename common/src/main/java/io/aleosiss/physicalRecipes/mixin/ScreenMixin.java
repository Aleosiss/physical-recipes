package io.aleosiss.physicalRecipes.mixin;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "addRenderableWidget", at = @At("HEAD"), cancellable = true)
    public <T extends GuiEventListener & Renderable & NarratableEntry> void removeRecipeButton(T widget, CallbackInfoReturnable<T> cir) {
        if (!(widget instanceof ImageButton)) {
            return;
        }

        var imageAccessor = (ImageButtonAccessor) widget;
        if (imageAccessor.getSprites() != null && imageAccessor.getSprites().equals(RecipeBookComponent.RECIPE_BUTTON_SPRITES)) {
            cir.setReturnValue(null);
        }
    }
}