package io.aleosiss.physicalRecipes.network

import com.mojang.serialization.Codec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.stats.RecipeBook
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemStack.CODEC
import net.minecraft.world.item.component.BundleContents
import net.minecraft.world.item.crafting.Recipe

/**
 * public final class BundleContents implements TooltipComponent {
 *     public static final BundleContents EMPTY = new BundleContents(List.of());
 *     public static final Codec<BundleContents> CODEC;
 *     public static final StreamCodec<RegistryFriendlyByteBuf, BundleContents> STREAM_CODEC;
 *     private static final Fraction BUNDLE_IN_BUNDLE_WEIGHT;
 *     private static final int NO_STACK_INDEX = -1;
 *     public static final int NO_SELECTED_ITEM_INDEX = -1;
 *     final List<ItemStack> items;
 *     final Fraction weight;
 *     final int selectedItem;
 *
 *     BundleContents(List<ItemStack> list, Fraction fraction, int i) {
 *         this.items = list;
 *         this.weight = fraction;
 *         this.selectedItem = i;
 *     }
 *
 */
class RecipeContent(private val recipe: Recipe<*>?): TooltipComponent {

  companion object {
    val EMPTY: RecipeContent = RecipeContent(null)

    val STREAM_CODEC: StreamCodec<in RegistryFriendlyByteBuf, RecipeContent>
      get() {
        TODO()
      }
    val CODEC: Codec<RecipeContent> = ItemStack.CODEC
     		 //.listOf()
         //.flatXmap(BundleContents::checkAndCreate, bundleContents -> DataResult.success(bundleContents.items));
  }
}

/**
 * 	public static final Codec<BundleContents> CODEC = ItemStack.CODEC
 * 		.listOf()
 * 		.flatXmap(BundleContents::checkAndCreate, bundleContents -> DataResult.success(bundleContents.items));
 * 	public static final StreamCodec<RegistryFriendlyByteBuf, BundleContents> STREAM_CODEC = ItemStack.STREAM_CODEC
 * 		.apply(ByteBufCodecs.list())
 * 		.map(BundleContents::new, bundleContents -> bundleContents.items);
 */

class RecipeBookContent(private val recipes: List<Recipe<*>>) {

  companion object {
    val EMPTY: RecipeBookContent = RecipeBookContent(emptyList())

    val STREAM_CODEC: StreamCodec<in RegistryFriendlyByteBuf, RecipeBookContent>
      get() {
        TODO()
      }
    val CODEC: Codec<RecipeBookContent>
      get() {
        TODO()
      }
  }
}