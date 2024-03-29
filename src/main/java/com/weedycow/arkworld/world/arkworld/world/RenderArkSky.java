package com.weedycow.arkworld.world.arkworld.world;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.WorldGenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class RenderArkSky extends IRenderHandler
{

    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation(Arkworld.MODID + ":textures/gui/sun.png");
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation(Arkworld.MODID + ":textures/gui/moon_phases.png");

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
        boolean vboEnabled = OpenGlHelper.useVbo();
        final TextureManager renderEngine = mc.getTextureManager();
        
        if (mc.world.provider.getDimension()== WorldGenRegistry.ARKWORLD_DIM_ID)
        {
            GlStateManager.disableTexture2D();
            Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
            float f = (float)vec3d.x;
            float f1 = (float)vec3d.y;
            float f2 = (float)vec3d.z;

//            if (pass != 2)
//            {
//                float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
//                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
//                float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
//                f = f3;
//                f1 = f4;
//                f2 = f5;
//            }

            GlStateManager.color(f, f1, f2);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.depthMask(false);
            GlStateManager.enableFog();
            GlStateManager.color(f, f1, f2);

//            if (vboEnabled)
//            {
//                skyVBO.bindBuffer();
//                GlStateManager.glEnableClientState(32884);
//                GlStateManager.glVertexPointer(3, 5126, 12, 0);
//                skyVBO.drawArrays(7);
//                skyVBO.unbindBuffer();
//                GlStateManager.glDisableClientState(32884);
//            }
//            else
//            {
//                GlStateManager.callList(glSkyList);
//            }

            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.disableStandardItemLighting();
            float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

            if (afloat != null)
            {
                GlStateManager.disableTexture2D();
                GlStateManager.shadeModel(7425);
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                float f6 = afloat[0];
                float f7 = afloat[1];
                float f8 = afloat[2];

//                if (pass != 2)
//                {
//                    float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
//                    float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
//                    float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
//                    f6 = f9;
//                    f7 = f10;
//                    f8 = f11;
//                }

                bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
                int l1 = 16;

                for (int j2 = 0; j2 <= 16; ++j2)
                {
                    float f21 = (float)j2 * ((float)Math.PI * 2F) / 16.0F;
                    float f12 = MathHelper.sin(f21);
                    float f13 = MathHelper.cos(f21);
                    bufferbuilder.pos((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
                }

                tessellator.draw();
                GlStateManager.popMatrix();
                GlStateManager.shadeModel(7424);
            }

            GlStateManager.enableTexture2D();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            float f16 = 1.0F - world.getRainStrength(partialTicks);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
            float f17 = 30.0F;
            renderEngine.bindTexture(SUN_TEXTURES);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(-f17), 100.0D, (double)(-f17)).tex(0.0D, 0.0D).endVertex();
            bufferbuilder.pos((double)f17, 100.0D, (double)(-f17)).tex(1.0D, 0.0D).endVertex();
            bufferbuilder.pos((double)f17, 100.0D, (double)f17).tex(1.0D, 1.0D).endVertex();
            bufferbuilder.pos((double)(-f17), 100.0D, (double)f17).tex(0.0D, 1.0D).endVertex();
            tessellator.draw();
            f17 = 20.0F;
            renderEngine.bindTexture(MOON_PHASES_TEXTURES);
            int k1 = world.getMoonPhase();
            int i2 = k1 % 4;
            int k2 = k1 / 4 % 2;
            float f22 = (float)(i2 + 0) / 4.0F;
            float f23 = (float)(k2 + 0) / 2.0F;
            float f24 = (float)(i2 + 1) / 4.0F;
            float f14 = (float)(k2 + 1) / 2.0F;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(-f17), -100.0D, (double)f17).tex((double)f24, (double)f14).endVertex();
            bufferbuilder.pos((double)f17, -100.0D, (double)f17).tex((double)f22, (double)f14).endVertex();
            bufferbuilder.pos((double)f17, -100.0D, (double)(-f17)).tex((double)f22, (double)f23).endVertex();
            bufferbuilder.pos((double)(-f17), -100.0D, (double)(-f17)).tex((double)f24, (double)f23).endVertex();
            tessellator.draw();
            GlStateManager.disableTexture2D();
            float f15 = world.getStarBrightness(partialTicks) * f16;

//            if (f15 > 0.0F)
//            {
//                GlStateManager.color(f15, f15, f15, f15);
//
//                if (vboEnabled)
//                {
//                    starVBO.bindBuffer();
//                    GlStateManager.glEnableClientState(32884);
//                    GlStateManager.glVertexPointer(3, 5126, 12, 0);
//                    starVBO.drawArrays(7);
//                    starVBO.unbindBuffer();
//                    GlStateManager.glDisableClientState(32884);
//                }
//                else
//                {
//                    GlStateManager.callList(starGLCallList);
//                }
//            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableFog();
            GlStateManager.popMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.color(0.0F, 0.0F, 0.0F);
            double d3 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

            if (d3 < 0.0D)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 12.0F, 0.0F);

//                if (vboEnabled)
//                {
//                    sky2VBO.bindBuffer();
//                    GlStateManager.glEnableClientState(32884);
//                    GlStateManager.glVertexPointer(3, 5126, 12, 0);
//                    sky2VBO.drawArrays(7);
//                    sky2VBO.unbindBuffer();
//                    GlStateManager.glDisableClientState(32884);
//                }
//                else
//                {
//                    GlStateManager.callList(glSkyList2);
//                }

                GlStateManager.popMatrix();
                float f18 = 1.0F;
                float f19 = -((float)(d3 + 65.0D));
                float f20 = -1.0F;
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
            }

            if (world.provider.isSkyColored())
            {
                GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
            }
            else
            {
                GlStateManager.color(f, f1, f2);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -((float)(d3 - 16.0D)), 0.0F);
//            GlStateManager.callList(glSkyList2);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
        }
    }
}
