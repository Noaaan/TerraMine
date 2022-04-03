package terracraft.common.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import terracraft.TerraCraft;
import terracraft.common.block.*;
import terracraft.common.block.chests.GoldChestBlock;

public class ModBlocks {
    public static final Block GOLD_CHEST = register("gold_chest", new GoldChestBlock(FabricBlockSettings.of(Material.METAL).strength(3f).sounds(SoundType.METAL), () -> BlockEntityType.CHEST));
    public static final Block TINKERER_TABLE = register("tinkerer_workshop", new Block(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).randomTicks()));
    public static final Block CORRUPTED_GRASS = register("corrupted_grass", new CorruptedGrass(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));
    public static final Block CORRUPTED_GRAVEL = register("corrupted_gravel", new CorruptedFallingBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).randomTicks()));
    public static final Block CORRUPTED_SAND = register("corrupted_sand", new CorruptedFallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).randomTicks()));
    public static final Block CORRUPTED_GLASS = register("corrupted_glass", new CorruptedTransparentBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
    public static final Block CORRUPTED_SANDSTONE = register("corrupted_sandstone", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE).randomTicks()));
    public static final Block CORRUPTED_ANDESITE = register("corrupted_andesite", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE).randomTicks()));
    public static final Block CORRUPTED_DIORITE = register("corrupted_diorite", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE).randomTicks()));
    public static final Block CORRUPTED_GRANITE = register("corrupted_granite", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE).randomTicks()));
    public static final Block CORRUPTED_STONE = register("corrupted_stone", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.STONE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE = register("corrupted_deepslate", new CorruptedRotatableBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).randomTicks()));
    public static final Block CORRUPTED_COBBLESTONE = register("corrupted_cobblestone", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).randomTicks()));
    public static final Block CORRUPTED_COBBLED_DEEPSLATE = register("corrupted_cobbled_deepslate", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE).randomTicks()));
    public static final Block CORRUPTED_COAL_ORE = register("corrupted_coal_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.COAL_ORE).randomTicks()));
    public static final Block CORRUPTED_IRON_ORE = register("corrupted_iron_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).randomTicks()));
    public static final Block CORRUPTED_DEMONITE_ORE = register("demonite_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).randomTicks()));
    public static final Block CORRUPTED_COPPER_ORE = register("corrupted_copper_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).randomTicks()));
    public static final Block CORRUPTED_GOLD_ORE = register("corrupted_gold_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).randomTicks()));
    public static final Block CORRUPTED_LAPIS_ORE = register("corrupted_lapis_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE).randomTicks()));
    public static final Block CORRUPTED_REDSTONE_ORE = register("corrupted_redstone_ore", new CorruptedRedstoneOreBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_ORE).randomTicks()));
    public static final Block CORRUPTED_DIAMOND_ORE = register("corrupted_diamond_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).randomTicks()));
    public static final Block CORRUPTED_EMERALD_ORE = register("corrupted_emerald_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_COAL_ORE = register("corrupted_deepslate_coal_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_IRON_ORE = register("corrupted_deepslate_iron_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_DEMONITE_ORE = register("deepslate_demonite_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_COPPER_ORE = register("corrupted_deepslate_copper_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_GOLD_ORE = register("corrupted_deepslate_gold_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_LAPIS_ORE = register("corrupted_deepslate_lapis_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_LAPIS_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_REDSTONE_ORE = register("corrupted_deepslate_redstone_ore", new CorruptedRedstoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_REDSTONE_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_DIAMOND_ORE = register("corrupted_deepslate_diamond_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).randomTicks()));
    public static final Block CORRUPTED_DEEPSLATE_EMERALD_ORE = register("corrupted_deepslate_emerald_ore", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_EMERALD_ORE).randomTicks()));
    public static final Block CORRUPTED_SNOW_LAYER = register("corrupted_snow_layer", new CorruptedSnowLayer(BlockBehaviour.Properties.copy(Blocks.SNOW).randomTicks()));
    public static final Block CORRUPTED_SNOW = register("corrupted_snow", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).randomTicks()));
    public static final Block CORRUPTED_ICE = register("corrupted_ice", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.ICE).randomTicks()));
    public static final Block CORRUPTED_PACKED_ICE = register("corrupted_packed_ice", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).randomTicks()));
    public static final Block CORRUPTED_BLUE_ICE = register("corrupted_blue_ice", new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE).randomTicks()));

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, TerraCraft.id(name), block);
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
