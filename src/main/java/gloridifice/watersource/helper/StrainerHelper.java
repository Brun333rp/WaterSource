package gloridifice.watersource.helper;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.StrainerBlock;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class StrainerHelper {
    public static ItemStack getResultStack(ItemStack stack, StrainerBlock block) {
        ItemStack itemStack = stack.copy();
        if (!stack.isEmpty() && BlockTags.getCollection().get(new ResourceLocation(WaterSource.MODID, "strainer")).contains(block) && block != BlockRegistry.BLOCK_DIRTY_STRAINER && FluidUtil.getFluidHandler(stack) != null) {
            FluidUtil.getFluidHandler(itemStack).ifPresent(data -> {
                WaterFilterRecipe recipe = WaterFilterRecipe.getRecipeFromInput(Minecraft.getInstance().world, new ItemStack(Item.getItemFromBlock(block)), data.getFluidInTank(0).getFluid());
                if (recipe != null) {
                    int i = data.getFluidInTank(0).getAmount();
                    data.drain(data.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
                    data.fill(new FluidStack(recipe.getOutputFluid(), i), IFluidHandler.FluidAction.EXECUTE);
                }
            });
        }
        return itemStack;
    }

    public static int getDamageAmount(ItemStack stack, StrainerBlock block) {
        ItemStack itemStack = stack.copy();
        if (!stack.isEmpty() && BlockTags.getCollection().get(new ResourceLocation(WaterSource.MODID, "strainer")).contains(block) && block != BlockRegistry.BLOCK_DIRTY_STRAINER && FluidUtil.getFluidHandler(stack) != null) {
            return FluidUtil.getFluidHandler(itemStack).map(data -> {
                WaterFilterRecipe recipe = WaterFilterRecipe.getRecipeFromInput(Minecraft.getInstance().world, new ItemStack(Item.getItemFromBlock(block)), data.getFluidInTank(0).getFluid());
                if (recipe != null) {
                    return data.getFluidInTank(0).getAmount() / 250;
                }
                else return 0;
            }).orElse(0);
        }
        return 0;
    }
}
