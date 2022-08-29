package com.weedycow.arkworld.block.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.nature.surface.BlockTalaStone;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.WorldGenRegistry;
import com.weedycow.arkworld.world.arkworld.world.ArkTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockPortalArkworld extends BlockBreakable
{
    private static final AxisAlignedBB PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockPortalArkworld()
    {
        super(Material.PORTAL,false);
        this.setTickRandomly(true);
        this.setUnlocalizedName(Arkworld.MODID + ".portalArkworld");
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setRegistryName("portal_arkworld");
    }

    public boolean trySpawnPortal(World world, BlockPos pos)
    {
        Size size = new Size(world, pos);

        if (size.isValid())
        {
            size.placePortalBlocks();
            return true;
        }
        else
        {
            Size size1 = new Size(world, pos);

            if (size1.isValid())
            {
                size1.placePortalBlocks();
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos)
    {

        Size size = new Size(world, pos);
        if (neighborBlock == this || size.isTelaStone(neighborBlock.getDefaultState()))
        {
            if (!size.isValid())
            {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public static void changeDimension(World world, EntityPlayerMP player, int dimension, ArkTeleporter teleporter)
    {
        if (!world.isRemote)
        {
            player.changeDimension(dimension, teleporter);

            player.timeUntilPortal = 300;
            if (player.dimension == WorldGenRegistry.ARKWORLD_DIM_ID)
            {
                BlockPos playerPos = new BlockPos(player);
                if (world.isAirBlock(playerPos) && world.getBlockState(playerPos).isSideSolid(world, playerPos, EnumFacing.UP))
                {
                    player.setSpawnChunk(playerPos, true, WorldGenRegistry.ARKWORLD_DIM_ID);
                }
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, Entity entity)
    {
        if (!entity.isRiding() && !entity.isBeingRidden() && entity instanceof EntityPlayerMP && entity.timeUntilPortal <= 0)
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            final int dimension = player.dimension == WorldGenRegistry.ARKWORLD_DIM_ID ? DimensionType.OVERWORLD.getId() : WorldGenRegistry.ARKWORLD_DIM_ID;
            changeDimension(world, (EntityPlayerMP) entity, dimension, new ArkTeleporter(player.mcServer.getWorld(dimension)));
        }
    }

    public static class Size
    {
        private static final int MIN_LENGTH = 2;
        private static final int MIN_WIDTH = 1;
        private final World world;
        private boolean valid = false;
        private BlockPos nw;
        private BlockPos se;

        public Size(World world, BlockPos pos)
        {
            this.world = world;
            int width;
            int length;
            int east = getDistanceUntilEdge(pos, EnumFacing.EAST);
            int west = getDistanceUntilEdge(pos, EnumFacing.WEST);
            int north = getDistanceUntilEdge(pos, EnumFacing.NORTH);
            int south = getDistanceUntilEdge(pos, EnumFacing.SOUTH);
            int ew = east+west-1;
            int ns = north+south-1;
            BlockPos nwCorner;
            BlockPos seCorner;
            int wallWidth;
            int wallLength;

            if(ew==ns) return;

            if(ew>ns && ew>=MIN_LENGTH && ns>=MIN_WIDTH)
            {
                length = ew;
                width = ns;
                nwCorner = pos.west(west).south(south);
                seCorner = pos.east(east).north(south);
                this.nw = nwCorner.add(1, 0, -1);
                this.se = seCorner.add(-1, 0, 1);
                wallWidth = length + 2;
                wallLength = -width - 2;
            }
            else if(ns>ew && ns>=MIN_LENGTH && ew>=MIN_WIDTH)
            {
                width = ew;
                length = ns;
                nwCorner = pos.west(west).north(north);
                seCorner = pos.east(east).south(south);
                this.nw = nwCorner.add(1, 0, 1);
                this.se = seCorner.add(-1, 0, -1);
                wallWidth = width + 2;
                wallLength = length + 2;
            }
            else
                return;

            for (int y = 0; y <= 1; y++)
            {
                for (int x = 0; x < wallWidth; x++)
                {
                    if(wallLength==length + 2)
                    {
                        for (int z = 0; z < wallLength; z++)
                        {
                            if (y == 0 || x == 0 || z == 0 || x == wallWidth - 1 || z == wallLength - 1)
                            {
                                if (!isTelaStone(world.getBlockState(nwCorner.down().add(x, y, z))))
                                {
                                    return;
                                }
                            }
                        }
                    }

                    if(wallLength==width + 2)
                    {
                        for (int z = 0; z > wallLength; z--)
                        {
                            if (y == 0 || x == 0 || z == 0 || x == wallWidth - 1 || z == wallLength - 1)
                            {
                                if (!isTelaStone(world.getBlockState(nwCorner.down().add(x, y, z))))
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            this.valid = true;
        }

        int getDistanceUntilEdge(BlockPos pos, EnumFacing facing)
        {
            int i;

            for (i = 0; i < 9; ++i)
            {
                BlockPos blockpos = pos.offset(facing, i);
                if (!this.isWaterBlock(this.world.getBlockState(blockpos)) || !isTelaStone(this.world.getBlockState(blockpos.down())))
                {
                    break;
                }
            }

            IBlockState state = this.world.getBlockState(pos.offset(facing, i));

            return isTelaStone(state) ? i : 0;
        }

        boolean isWaterBlock(IBlockState state)
        {
            return state.getMaterial() == Material.WATER;
        }

        boolean isTelaStone(IBlockState state)
        {
            return state.getBlock() instanceof BlockTalaStone;
        }

        boolean isValid()
        {
            return this.valid;
        }

        void placePortalBlocks()
        {
            for (BlockPos.MutableBlockPos portalPos : BlockPos.MutableBlockPos.getAllInBoxMutable(nw, se))
            {
                this.world.setBlockState(portalPos, BlockRegistry.PORTAL_ARKWORLD.getDefaultState(), 2);
            }
        }
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos)
    {
        return PORTAL_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess world, @Nonnull BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess world, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int quantityDropped(@Nonnull Random random) {
        return 0;
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player)
    {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, Random rand)
    {
        int random = rand.nextInt(100);
        if (random == 0)
        {
            worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
        for (int i = 0; i < 4; ++i)
        {
            double xPos = (float) pos.getX() + rand.nextFloat();
            double yPos = pos.getY() + 1D;
            double zPos = (float) pos.getZ() + rand.nextFloat();
            double xSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double ySpeed = rand.nextFloat();
            double zSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
        }
    }
}
