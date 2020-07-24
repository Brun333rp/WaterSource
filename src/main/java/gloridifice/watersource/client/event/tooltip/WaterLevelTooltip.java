package gloridifice.watersource.client.event.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelRecipe;
import gloridifice.watersource.common.recipe.WaterLevelRecipeManager;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static gloridifice.watersource.client.hud.WaterLevelGui.OVERLAY_BAR;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class WaterLevelTooltip {
    private static int x;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        x = 1;
        if (ConfigRegistry.IS_FOOD_WATER_LEVEL_OPEN.get()) {
            WaterLevelRecipe recipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getItemStack());
            if (recipe != null) {
                StringBuilder stringBuilder = new StringBuilder(" ");
                for (int i = 0;i < recipe.getWaterLevel()/2 ;i++) stringBuilder.append("  ");
                if (ModList.get().isLoaded("quark") && event.getItemStack().isFood()){
                    if (event.getToolTip().size() >= 3 && event.getToolTip().get(2) instanceof TranslationTextComponent && event.getToolTip().get(2).getString().startsWith("quark.misc.saturation")){
                        x = 3;
                    }else x = 2;
                }else x = 1;
                if (ModList.get().isLoaded("quark") && PotionUtils.getEffectsFromStack(event.getItemStack()).size() != 0){
                    x += getQuarkPotionCount(event.getItemStack());
                }
                event.getToolTip().add(x,new StringTextComponent(stringBuilder.toString()));
            }
        }
    }
    @SubscribeEvent
    public static void onRenderTooltipEvent(RenderTooltipEvent.PostText event) {
        if (ConfigRegistry.IS_FOOD_WATER_LEVEL_OPEN.get()) {
            WaterLevelRecipe recipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getStack());
            if (recipe != null) {
                RenderSystem.pushMatrix();
                ThirstItemRecipe recipe1 = ThirstItemRecipeManager.getRecipeFromItemStick(event.getStack());
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);

                FontRenderer fontRenderer = event.getFontRenderer();

                int OffsetY = event.getY() + x * 9 + 3;
                int OffsetX = event.getX() - 1;
                int texU = 0;
                int texU1 = texU, texU2 = texU;
                if (recipe1 != null) {
                    texU1 = texU + 18;
                    texU2 = texU + 9;
                }

                Minecraft.getInstance().getTextureManager().bindTexture(OVERLAY_BAR);
                if (recipe.getWaterLevel() % 2 == 0){
                    for(int i = 0; i < recipe.getWaterLevel()/2; i++){
                        AbstractGui.blit(OffsetX + i * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        AbstractGui.blit(OffsetX + i * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }else {
                    AbstractGui.blit(OffsetX, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                    AbstractGui.blit(OffsetX, OffsetY, 9 + texU1, 0, 9, 9, 256, 256);
                    for (int n = 1; n < (recipe.getWaterLevel() + 1)/2; n++){
                        AbstractGui.blit(OffsetX + n * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        AbstractGui.blit(OffsetX + n * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }
                //TODO:SaturationLevel
                /*
                double scale = 0.9;
                RenderSystem.scaled(scale, scale, scale);

                fontRenderer.drawString("x" + ((float) recipe.getWaterLevel()) / 2, (int) ((event.getX() + 10) / scale), (int) ((OffsetY + 2) / scale),0xFFFFFF);
                //SaturationLevel↓
                fontRenderer.drawString("x" + ((float) recipe.getWaterSaturationLevel()) / 2, (int) ((event.getX() + 41) / scale), (int) ((OffsetY + 2) / scale), 0xFFFFFF);
                RenderSystem.scaled(1, 1, 1);
*/
                RenderSystem.popMatrix();
            }
        }
    }
    public static int getQuarkPotionCount(ItemStack itemStack){
        int i = 0;
        List<EffectInstance> potions = PotionUtils.getEffectsFromStack(itemStack);
        for (EffectInstance instance : potions){
            if (instance.getPotion() == Effects.SLOWNESS){
                i++;
            }
            if (instance.getPotion() == Effects.SPEED){
                i++;
            }
            if (instance.getPotion() == Effects.LUCK){
                i++;
            }
            if (instance.getPotion() == Effects.WEAKNESS){
                i++;
            }
            if (instance.getPotion() == Effects.CONDUIT_POWER){
                i++;
            }
        }
        return i;
    }
}