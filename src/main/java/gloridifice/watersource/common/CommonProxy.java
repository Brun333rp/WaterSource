package gloridifice.watersource.common;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class CommonProxy {
    public static void init() {
        registerFireInfo();
    }

    public static void registerFireInfo() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        /*
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_STAIRS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_PLANKS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE_GATE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_SLAB, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_STRIPPED_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LEAF, 30, 60);*/
    }
}
