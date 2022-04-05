package terracraft;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;
import terrablender.worldgen.TBSurfaceRuleData;
import terracraft.common.compat.CompatHandler;
import terracraft.common.config.ModConfig;
import terracraft.common.init.*;
import terracraft.common.network.packet.BoneMealPacket;
import terracraft.common.world.biome.BiomeAdder;
import terracraft.common.world.biome.BiomeSurfaceRules;

import java.util.Optional;
import java.util.UUID;

public class TerraCraft implements ModInitializer, TerraBlenderApi {

	public static final String MOD_ID = "terracraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(TerraCraft.class);
	public static final SimpleParticleType BLUE_POOF = FabricParticleTypes.simple();
	public static final SimpleParticleType GREEN_SPARK = FabricParticleTypes.simple();
	public static final AttributeModifier KNOCKBACK_RESISTANCE_COBALT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a243-468f-b415-14bff6da3666"),
			"terracraft:cobalt_shield_knockback_resistance", 1, AttributeModifier.Operation.ADDITION);
	public static final AttributeModifier KNOCKBACK_RESISTANCE_OBSIDIAN_MODIFIER = new AttributeModifier(UUID.fromString("5e97cada-a242-465f-b425-13bfd6db3676"),
			"terracraft:obsidian_shield_knockback_resistance", 1, AttributeModifier.Operation.ADDITION);
	public static final AttributeModifier ARMOR_ADD_ONE = new AttributeModifier(UUID.fromString("2e97c2de-a2d3-4a8f-b415-14bff6da3666"),
			"terracraft:cobalt_shield_armor_two", 1, AttributeModifier.Operation.ADDITION);
	public static final AttributeModifier ARMOR_ADD_TWO = new AttributeModifier(UUID.fromString("1a93aedf-a243-4d8f-b415-14bff6da3666"),
			"terracraft:cobalt_shield_armor_one", 2, AttributeModifier.Operation.ADDITION);
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(
			id("item_group"),
			() -> new ItemStack(ModItems.TERRASPARK_BOOTS)
	);
	public static final ResourceLocation WALL_JUMP_PACKET_ID = new ResourceLocation(MOD_ID, "walljump");
	public static final ResourceLocation FALL_DISTANCE_PACKET_ID = new ResourceLocation(MOD_ID, "falldistance");
	public static ModConfig CONFIG;
	//private static final Map<String, Runnable> COMPAT_HANDLERS = Map.of(
	//		"origins", new OriginsCompat(),
	//		"haema", new HaemaCompat());

	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void onInitialize() {
		// Config
		CONFIG = getConfigAndInvalidateOldVersions();

		// Packets
		ServerPlayNetworking.registerGlobalReceiver(BoneMealPacket.ID, BoneMealPacket::receive);

		ServerPlayNetworking.registerGlobalReceiver(WALL_JUMP_PACKET_ID, (server, player, handler, buf, responseSender) -> {
			boolean didWallJump = buf.readBoolean();

			server.execute(() -> {
				if(didWallJump)
					player.causeFoodExhaustion(0.8F);
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(FALL_DISTANCE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
			float fallDistance = buf.readFloat();
			server.execute(() -> {
				player.fallDistance = fallDistance;
			});
		});

		// Loot table setup
		ModLootConditions.register();
		LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> ModLootTables.onLootTableLoad(id, supplier));

		// Force loading init classes
		// Entities is loaded by items, loot tables can load lazily (no registration)
		ModItems.ANTIDOTE_VESSEL.toString();
		ModSoundEvents.MIMIC_CLOSE.toString();
		ModPotions.LESSER_MANA_POTION.toString();
		Stats.CUSTOM.get(ModStatistics.MANA_USED, StatFormatter.DEFAULT);
		ModFeatures.register();
		ModBiomes.registerAll();
		ModProfessions.fillTradeData();
		ModCommands.registerAll();

		Registry.register(Registry.PARTICLE_TYPE, TerraCraft.id("blue_poof"), BLUE_POOF);
		Registry.register(Registry.PARTICLE_TYPE, TerraCraft.id("green_spark"), GREEN_SPARK);

		// Compat Handlers
		for (CompatHandler handler : FabricLoader.getInstance().getEntrypoints("terracraft:compat_handlers", CompatHandler.class)) {
			if (FabricLoader.getInstance().isModLoaded(handler.modId())) {
				Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(handler.modId());
				String modName = modContainer.map(c -> c.getMetadata().getName()).orElse(handler.modId());

				LOGGER.info("Running compat handler for " + modName);
				handler.run();
			}
		}

		LOGGER.info("Finished initialization");
	}

	@Override
	public void onTerraBlenderInitialized()
	{
		// Given we only add two biomes, we should keep our weight relatively low.
		Regions.register(new BiomeAdder(id("terraria_biomes"), RegionType.OVERWORLD, 1));

		SurfaceRuleManager.addToDefaultSurfaceRulesAtStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK,
				1, BiomeSurfaceRules.makeRules());
		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
				TBSurfaceRuleData.overworld());
	}

	/**
	 * Gets the config and if the config version is incompatible, reset to the default config.
	 * Note: this does not reset files for removed categories.
	 */
	private ModConfig getConfigAndInvalidateOldVersions() {
		ConfigHolder<ModConfig> configHolder = AutoConfig.register(ModConfig.class,
				PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
		int currentVersion = configHolder.getConfig().general.configVersion;
		int requiredVersion = ModConfig.General.CONFIG_VERSION;
		if (currentVersion != requiredVersion) {
			LOGGER.warn("Resetting incompatible config with version {} to version {}", currentVersion, requiredVersion);
			configHolder.resetToDefault();
			configHolder.save();
		}
		return configHolder.getConfig();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
