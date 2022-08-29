package com.weedycow.arkworld.world.arkworld.world;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.registry.WorldGenRegistry;
import com.weedycow.arkworld.world.arkworld.biome.ArkBiomeProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArkWorldProvider extends WorldProviderSurface
{
    Entity cameraEntity;
    Vec3d gray = new Vec3d(0.5,0.5,0.5);

    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer()
    {
        return new RenderArkSky();
    }

    @Nonnull
    @Override
    public DimensionType getDimensionType()
    {
        return WorldGenRegistry.ARKWORLD_DIM_TYPE;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @Override
    public boolean canDoLightning(@Nonnull net.minecraft.world.chunk.Chunk chunk)
    {
        return true;
    }

    @Override
    public boolean canDoRainSnowIce(@Nonnull Chunk chunk)
    {
        return true;
    }

    @Override
    public boolean canSnowAt(@Nonnull BlockPos pos, boolean checkLight)
    {
        return true;
    }

    @Nonnull
    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ArkChunkGenerator(world, world.getSeed() - WorldGenRegistry.ARKWORLD_DIM_ID,world.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public void init()
    {
        this.biomeProvider = new ArkBiomeProvider((world.getSeed()));
        this.nether = false;
        this.hasSkyLight = true;
    }

    @Override
    public void calculateInitialWeather() {}

    @Override
    public void updateWeather() {}

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float par1, float par2)
    {
        if(cameraEntity!=null && ArkConfig.world.graySky)
        {
            return gray;
        }
        else
            return super.getFogColor(par1,par2);
    }

    @Override
    public boolean canRespawnHere()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int par1, int par2)
    {
        return ArkConfig.world.fog;
    }

    @Nonnull
    @Override
    public WorldSleepResult canSleepAt(@Nonnull EntityPlayer player, @Nonnull BlockPos pos) {return WorldSleepResult.ALLOW;}

    @Override
    public boolean doesWaterVaporize() {return false;}

    @Nonnull
    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(@Nonnull Entity cameraEntity, float partialTicks)
    {
        this.cameraEntity = cameraEntity;

        if(ArkConfig.world.graySky)
            return gray;
        else
            return world.getSkyColorBody(cameraEntity, partialTicks);
    }

    @Override
    public float getCloudHeight()
    {
        return -1;
    }
}
