package committee.nova.anamm.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
    @Shadow @Final private MinecraftClient client;
    @Shadow private int scaledWidth;
    private static final Identifier MINIMAP_TEXTURE = new Identifier("anamm","textures/gui/minimap.png");

    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!this.client.options.hudHidden) {
            renderMinimap(matrices);
        }
    }

    private void renderMinimap(MatrixStack matrices) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MINIMAP_TEXTURE);
        int width = this.scaledWidth;
        this.drawTexture(matrices, width - 166, 0, 0, 0, 166, 166);
        this.drawTexture(matrices, width - 88, 78, 166, 0, 176, 10);
        RenderSystem.disableBlend();
    }
}
