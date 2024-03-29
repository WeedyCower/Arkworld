package com.weedycow.arkworld.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SoundRegistry
{
    public static final SoundEvent DAMEDANE = new SoundEvent(new ResourceLocation("arkworld", "damedane"));
    public static final SoundEvent FROSTNOVA_FIRST_BGM = new SoundEvent(new ResourceLocation("arkworld", "frostnova_first_bgm"));
    public static final SoundEvent FROSTNOVA_SECOND_BGM = new SoundEvent(new ResourceLocation("arkworld", "frostnova_second_bgm"));
    public static final SoundEvent FROSTNOVA_SKILL_FIRST = new SoundEvent(new ResourceLocation("arkworld", "frostnova_skill_first"));
    public static final SoundEvent FROSTNOVA_SKILL_SECOND = new SoundEvent(new ResourceLocation("arkworld", "frostnova_skill_second"));
    public static final SoundEvent FROSTNOVA_TRANSFORMATION = new SoundEvent(new ResourceLocation("arkworld", "frostnova_transformation"));
    public static final SoundEvent FROSTNOVA_SECOND_SPAWN = new SoundEvent(new ResourceLocation("arkworld", "frostnova_second_spawn"));
    public static final SoundEvent FROSTNOVA_DEATH_BGM = new SoundEvent(new ResourceLocation("arkworld", "frostnova_death_bgm"));
    public static final SoundEvent MAYFEST_SINGER_FIRST_BGM = new SoundEvent(new ResourceLocation("arkworld", "mayfest_singer_first_bgm"));
    public static final SoundEvent MAYFEST_SINGER_SECOND_BGM = new SoundEvent(new ResourceLocation("arkworld", "mayfest_singer_second_bgm"));
    public static final SoundEvent BLACK_SNAKE_FIRST_BGM = new SoundEvent(new ResourceLocation("arkworld", "talulah_first_bgm"));
    public static final SoundEvent BLACK_SNAKE_SECOND_BGM =new SoundEvent(new ResourceLocation("arkworld", "talulah_second_bgm"));
    public static final SoundEvent BLACK_SNAKE_ATTACK =new SoundEvent(new ResourceLocation("arkworld", "talulah_attack"));
    public static final SoundEvent BLACK_SNAKE_SKILL =new SoundEvent(new ResourceLocation("arkworld", "talulah_skill"));
    public static final SoundEvent FAUST_BGM = new SoundEvent(new ResourceLocation("arkworld", "faust_bgm"));
    public static final SoundEvent FAUST_ATTACK = new SoundEvent(new ResourceLocation("arkworld", "faust_attack"));
    public static final SoundEvent FAUST_SKILL = new SoundEvent(new ResourceLocation("arkworld", "faust_skill"));
    public static final SoundEvent PATRIOT_RESURRECTION_SPAWN = new SoundEvent(new ResourceLocation("arkworld", "patriot_resurrection_spawn"));
    public static final SoundEvent PATRIOT_SECOND_SPAWN = new SoundEvent(new ResourceLocation("arkworld", "patriot_second_spawn"));
    public static final SoundEvent PATRIOT_FIRST_BGM = new SoundEvent(new ResourceLocation("arkworld", "patriot_first_bgm"));
    public static final SoundEvent PATRIOT_SECOND_BGM = new SoundEvent(new ResourceLocation("arkworld", "patriot_second_bgm"));
    public static final SoundEvent PATRIOT_DEATH_BGM = new SoundEvent(new ResourceLocation("arkworld", "patriot_death_bgm"));
    public static final SoundEvent W_COUNTDOWN = new SoundEvent(new ResourceLocation("arkworld", "w_countdown"));
    public static final SoundEvent W_EXPLODE =new SoundEvent(new ResourceLocation("arkworld", "w_explode"));
    public static final SoundEvent MAYFEST_SINGER_ATTACK =new SoundEvent(new ResourceLocation("arkworld", "mayfest_singer_attack"));
    public static final SoundEvent MAYFEST_SINGER_SKILL =new SoundEvent(new ResourceLocation("arkworld", "mayfest_singer_skill"));
    public static final SoundEvent MAYFEST_SKILL =new SoundEvent(new ResourceLocation("arkworld", "mayfest_skill"));
    public static final SoundEvent SKULLSHATTERER_BGM =new SoundEvent(new ResourceLocation("arkworld", "skullshatterer_bgm"));
    public static final SoundEvent AMIYA_INTERACT_I =new SoundEvent(new ResourceLocation("arkworld", "amiya_interact_i"));
    public static final SoundEvent AMIYA_INTERACT_II =new SoundEvent(new ResourceLocation("arkworld", "amiya_interact_ii"));
    public static final SoundEvent AMIYA_INTERACT_III =new SoundEvent(new ResourceLocation("arkworld", "amiya_interact_iii"));
    public static final SoundEvent AMIYA_INTERACT_IV =new SoundEvent(new ResourceLocation("arkworld", "amiya_interact_iv"));
    public static final SoundEvent AMIYA_FIGHT_I =new SoundEvent(new ResourceLocation("arkworld", "amiya_fight_i"));
    public static final SoundEvent AMIYA_FIGHT_II =new SoundEvent(new ResourceLocation("arkworld", "amiya_fight_ii"));
    public static final SoundEvent AMIYA_FIGHT_III =new SoundEvent(new ResourceLocation("arkworld", "amiya_fight_iii"));
    public static final SoundEvent AMIYA_FIGHT_IV =new SoundEvent(new ResourceLocation("arkworld", "amiya_fight_iv"));
    public static final SoundEvent AMIYA_IDLE_I =new SoundEvent(new ResourceLocation("arkworld", "amiya_idle_i"));
    public static final SoundEvent AMIYA_IDLE_II =new SoundEvent(new ResourceLocation("arkworld", "amiya_idle_ii"));
    public static final SoundEvent AMIYA_IDLE_III =new SoundEvent(new ResourceLocation("arkworld", "amiya_idle_iii"));
    public static final SoundEvent AMIYA_IDLE_IV =new SoundEvent(new ResourceLocation("arkworld", "amiya_idle_iv"));
    public static final SoundEvent QUEEN_DEATH =new SoundEvent(new ResourceLocation("arkworld", "queen_death"));
    public static final SoundEvent QUEEN_HURT =new SoundEvent(new ResourceLocation("arkworld", "queen_hurt"));
    public static final SoundEvent QUEEN_LIVING_I =new SoundEvent(new ResourceLocation("arkworld", "queen_living_i"));
    public static final SoundEvent QUEEN_LIVING_II =new SoundEvent(new ResourceLocation("arkworld", "queen_living_ii"));
    public static final SoundEvent HYDRALISK_DEATH =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_death"));
    public static final SoundEvent HYDRALISK_HURT =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_hurt"));
    public static final SoundEvent HYDRALISK_LIVING_I =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_living_i"));
    public static final SoundEvent HYDRALISK_LIVING_II =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_living_ii"));
    public static final SoundEvent HYDRALISK_LIVING_III =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_living_iii"));
    public static final SoundEvent HYDRALISK_LIVING_IV =new SoundEvent(new ResourceLocation("arkworld", "hydralisk_living_iv"));
    public static final SoundEvent ZERGLING_DEATH =new SoundEvent(new ResourceLocation("arkworld", "zergling_death"));
    public static final SoundEvent ZERGLING_HURT =new SoundEvent(new ResourceLocation("arkworld", "zergling_hurt"));
    public static final SoundEvent ZERGLING_LIVING_I =new SoundEvent(new ResourceLocation("arkworld", "zergling_living_i"));
    public static final SoundEvent ZERGLING_LIVING_II =new SoundEvent(new ResourceLocation("arkworld", "zergling_living_ii"));
    public static final SoundEvent ZERGLING_LIVING_III =new SoundEvent(new ResourceLocation("arkworld", "zergling_living_iii"));
    public static final SoundEvent ZERGLING_LIVING_IV =new SoundEvent(new ResourceLocation("arkworld", "zergling_living_iv"));
    public static final SoundEvent OPERATOR_PLACE =new SoundEvent(new ResourceLocation("arkworld", "operator_place"));
    public static final SoundEvent AMIYA_ATTACK =new SoundEvent(new ResourceLocation("arkworld", "amiya_attack"));
    public static final SoundEvent OPERATOR_SKILL_START =new SoundEvent(new ResourceLocation("arkworld", "operator_skill_start"));
    public static final SoundEvent AMIYA_SKILL_II =new SoundEvent(new ResourceLocation("arkworld", "amiya_skill_ii"));
    public static final SoundEvent OPERATOR_DEATH =new SoundEvent(new ResourceLocation("arkworld", "operator_death"));
    public static final SoundEvent SURTR_ATTACK =new SoundEvent(new ResourceLocation("arkworld", "surtr_attack"));
    public static final SoundEvent SURTR_SKILL_III_START =new SoundEvent(new ResourceLocation("arkworld", "surtr_skill_iii_start"));
    public static final SoundEvent SURTR_SKILL_III_ATTACK =new SoundEvent(new ResourceLocation("arkworld", "surtr_skill_iii_attack"));
    public static final SoundEvent SURTR_INTERACT_I =new SoundEvent(new ResourceLocation("arkworld", "surtr_interact_i"));
    public static final SoundEvent SURTR_INTERACT_II =new SoundEvent(new ResourceLocation("arkworld", "surtr_interact_ii"));
    public static final SoundEvent SURTR_INTERACT_III =new SoundEvent(new ResourceLocation("arkworld", "surtr_interact_iii"));
    public static final SoundEvent SURTR_INTERACT_IV =new SoundEvent(new ResourceLocation("arkworld", "surtr_interact_iv"));
    public static final SoundEvent SURTR_FIGHT_I =new SoundEvent(new ResourceLocation("arkworld", "surtr_fight_i"));
    public static final SoundEvent SURTR_FIGHT_II =new SoundEvent(new ResourceLocation("arkworld", "surtr_fight_ii"));
    public static final SoundEvent SURTR_FIGHT_III =new SoundEvent(new ResourceLocation("arkworld", "surtr_fight_iii"));
    public static final SoundEvent SURTR_FIGHT_IV =new SoundEvent(new ResourceLocation("arkworld", "surtr_fight_iv"));
    public static final SoundEvent SURTR_IDLE_I =new SoundEvent(new ResourceLocation("arkworld", "surtr_idle_i"));
    public static final SoundEvent SURTR_IDLE_II =new SoundEvent(new ResourceLocation("arkworld", "surtr_idle_ii"));
    public static final SoundEvent SURTR_IDLE_III =new SoundEvent(new ResourceLocation("arkworld", "surtr_idle_iii"));
    public static final SoundEvent SURTR_IDLE_IV =new SoundEvent(new ResourceLocation("arkworld", "surtr_idle_iv"));
    public static final SoundEvent PURESTREAM_FIGHT_I =new SoundEvent(new ResourceLocation("arkworld", "purestream_fight_i"));
    public static final SoundEvent PURESTREAM_FIGHT_II =new SoundEvent(new ResourceLocation("arkworld", "purestream_fight_i"));
    public static final SoundEvent PURESTREAM_FIGHT_III =new SoundEvent(new ResourceLocation("arkworld", "purestream_fight_i"));
    public static final SoundEvent PURESTREAM_FIGHT_IV =new SoundEvent(new ResourceLocation("arkworld", "purestream_fight_i"));
    public static final SoundEvent PURESTREAM_INTERACT_I =new SoundEvent(new ResourceLocation("arkworld", "purestream_interact_i"));
    public static final SoundEvent PURESTREAM_INTERACT_II =new SoundEvent(new ResourceLocation("arkworld", "purestream_interact_ii"));
    public static final SoundEvent PURESTREAM_INTERACT_III =new SoundEvent(new ResourceLocation("arkworld", "purestream_interact_iii"));
    public static final SoundEvent PURESTREAM_INTERACT_IV =new SoundEvent(new ResourceLocation("arkworld", "purestream_interact_iv"));
    public static final SoundEvent PURESTREAM_IDLE_I =new SoundEvent(new ResourceLocation("arkworld", "purestream_idle_i"));
    public static final SoundEvent PURESTREAM_IDLE_II =new SoundEvent(new ResourceLocation("arkworld", "purestream_idle_ii"));
    public static final SoundEvent PURESTREAM_IDLE_III =new SoundEvent(new ResourceLocation("arkworld", "purestream_idle_iii"));
    public static final SoundEvent PURESTREAM_IDLE_IV =new SoundEvent(new ResourceLocation("arkworld", "purestream_idle_iv"));
    public static final SoundEvent W_FIGHT_I =new SoundEvent(new ResourceLocation("arkworld", "w_fight_i"));
    public static final SoundEvent W_FIGHT_II =new SoundEvent(new ResourceLocation("arkworld", "w_fight_i"));
    public static final SoundEvent W_FIGHT_III =new SoundEvent(new ResourceLocation("arkworld", "w_fight_i"));
    public static final SoundEvent W_FIGHT_IV =new SoundEvent(new ResourceLocation("arkworld", "w_fight_i"));
    public static final SoundEvent W_INTERACT_I =new SoundEvent(new ResourceLocation("arkworld", "w_interact_i"));
    public static final SoundEvent W_INTERACT_II =new SoundEvent(new ResourceLocation("arkworld", "w_interact_ii"));
    public static final SoundEvent W_INTERACT_III =new SoundEvent(new ResourceLocation("arkworld", "w_interact_iii"));
    public static final SoundEvent W_INTERACT_IV =new SoundEvent(new ResourceLocation("arkworld", "w_interact_iv"));
    public static final SoundEvent W_IDLE_I =new SoundEvent(new ResourceLocation("arkworld", "w_idle_i"));
    public static final SoundEvent W_IDLE_II =new SoundEvent(new ResourceLocation("arkworld", "w_idle_ii"));
    public static final SoundEvent W_IDLE_III =new SoundEvent(new ResourceLocation("arkworld", "w_idle_iii"));
    public static final SoundEvent W_IDLE_IV =new SoundEvent(new ResourceLocation("arkworld", "w_idle_iv"));


    @SubscribeEvent
    public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().register(W_FIGHT_I.setRegistryName(new ResourceLocation("arkworld", "w_fight_i")));
        event.getRegistry().register(W_FIGHT_II.setRegistryName(new ResourceLocation("arkworld", "w_fight_ii")));
        event.getRegistry().register(W_FIGHT_III.setRegistryName(new ResourceLocation("arkworld", "w_fight_iii")));
        event.getRegistry().register(W_FIGHT_IV.setRegistryName(new ResourceLocation("arkworld", "w_fight_iv")));
        event.getRegistry().register(W_INTERACT_I.setRegistryName(new ResourceLocation("arkworld", "w_interact_i")));
        event.getRegistry().register(W_INTERACT_II.setRegistryName(new ResourceLocation("arkworld", "w_interact_ii")));
        event.getRegistry().register(W_INTERACT_III.setRegistryName(new ResourceLocation("arkworld", "w_interact_iii")));
        event.getRegistry().register(W_INTERACT_IV.setRegistryName(new ResourceLocation("arkworld", "w_interact_iv")));
        event.getRegistry().register(W_IDLE_I.setRegistryName(new ResourceLocation("arkworld", "w_idle_i")));
        event.getRegistry().register(W_IDLE_II.setRegistryName(new ResourceLocation("arkworld", "w_idle_ii")));
        event.getRegistry().register(W_IDLE_III.setRegistryName(new ResourceLocation("arkworld", "w_idle_iii")));
        event.getRegistry().register(W_IDLE_IV.setRegistryName(new ResourceLocation("arkworld", "w_idle_iv")));
        event.getRegistry().register(PURESTREAM_FIGHT_I.setRegistryName(new ResourceLocation("arkworld", "purestream_fight_i")));
        event.getRegistry().register(PURESTREAM_FIGHT_II.setRegistryName(new ResourceLocation("arkworld", "purestream_fight_ii")));
        event.getRegistry().register(PURESTREAM_FIGHT_III.setRegistryName(new ResourceLocation("arkworld", "purestream_fight_iii")));
        event.getRegistry().register(PURESTREAM_FIGHT_IV.setRegistryName(new ResourceLocation("arkworld", "purestream_fight_iv")));
        event.getRegistry().register(PURESTREAM_INTERACT_I.setRegistryName(new ResourceLocation("arkworld", "purestream_interact_i")));
        event.getRegistry().register(PURESTREAM_INTERACT_II.setRegistryName(new ResourceLocation("arkworld", "purestream_interact_ii")));
        event.getRegistry().register(PURESTREAM_INTERACT_III.setRegistryName(new ResourceLocation("arkworld", "purestream_interact_iii")));
        event.getRegistry().register(PURESTREAM_INTERACT_IV.setRegistryName(new ResourceLocation("arkworld", "purestream_interact_iv")));
        event.getRegistry().register(PURESTREAM_IDLE_I.setRegistryName(new ResourceLocation("arkworld", "purestream_idle_i")));
        event.getRegistry().register(PURESTREAM_IDLE_II.setRegistryName(new ResourceLocation("arkworld", "purestream_idle_ii")));
        event.getRegistry().register(PURESTREAM_IDLE_III.setRegistryName(new ResourceLocation("arkworld", "purestream_idle_iii")));
        event.getRegistry().register(PURESTREAM_IDLE_IV.setRegistryName(new ResourceLocation("arkworld", "purestream_idle_iv")));
        event.getRegistry().register(SURTR_IDLE_I.setRegistryName(new ResourceLocation("arkworld", "surtr_idle_i")));
        event.getRegistry().register(SURTR_IDLE_II.setRegistryName(new ResourceLocation("arkworld", "surtr_idle_ii")));
        event.getRegistry().register(SURTR_IDLE_III.setRegistryName(new ResourceLocation("arkworld", "surtr_idle_iii")));
        event.getRegistry().register(SURTR_IDLE_IV.setRegistryName(new ResourceLocation("arkworld", "surtr_idle_iv")));
        event.getRegistry().register(SURTR_FIGHT_I.setRegistryName(new ResourceLocation("arkworld", "surtr_fight_i")));
        event.getRegistry().register(SURTR_FIGHT_II.setRegistryName(new ResourceLocation("arkworld", "surtr_fight_ii")));
        event.getRegistry().register(SURTR_FIGHT_III.setRegistryName(new ResourceLocation("arkworld", "surtr_fight_iii")));
        event.getRegistry().register(SURTR_FIGHT_IV.setRegistryName(new ResourceLocation("arkworld", "surtr_fight_iv")));
        event.getRegistry().register(SURTR_INTERACT_I.setRegistryName(new ResourceLocation("arkworld", "surtr_interact_i")));
        event.getRegistry().register(SURTR_INTERACT_II.setRegistryName(new ResourceLocation("arkworld", "surtr_interact_ii")));
        event.getRegistry().register(SURTR_INTERACT_III.setRegistryName(new ResourceLocation("arkworld", "surtr_interact_iii")));
        event.getRegistry().register(SURTR_INTERACT_IV.setRegistryName(new ResourceLocation("arkworld", "surtr_interact_iv")));
        event.getRegistry().register(SURTR_ATTACK.setRegistryName(new ResourceLocation("arkworld", "surtr_attack")));
        event.getRegistry().register(SURTR_SKILL_III_START.setRegistryName(new ResourceLocation("arkworld", "surtr_skill_iii_start")));
        event.getRegistry().register(SURTR_SKILL_III_ATTACK.setRegistryName(new ResourceLocation("arkworld", "surtr_skill_iii_attack")));
        event.getRegistry().register(OPERATOR_DEATH.setRegistryName(new ResourceLocation("arkworld", "operator_death")));
        event.getRegistry().register(OPERATOR_PLACE.setRegistryName(new ResourceLocation("arkworld", "operator_place")));
        event.getRegistry().register(AMIYA_ATTACK.setRegistryName(new ResourceLocation("arkworld", "amiya_attack")));
        event.getRegistry().register(OPERATOR_SKILL_START.setRegistryName(new ResourceLocation("arkworld", "operator_skill_start")));
        event.getRegistry().register(AMIYA_SKILL_II.setRegistryName(new ResourceLocation("arkworld", "amiya_skill_ii")));
        event.getRegistry().register(ZERGLING_DEATH.setRegistryName(new ResourceLocation("arkworld", "zergling_death")));
        event.getRegistry().register(ZERGLING_HURT.setRegistryName(new ResourceLocation("arkworld", "zergling_hurt")));
        event.getRegistry().register(ZERGLING_LIVING_I.setRegistryName(new ResourceLocation("arkworld", "zergling_living_i")));
        event.getRegistry().register(ZERGLING_LIVING_II.setRegistryName(new ResourceLocation("arkworld", "zergling_living_ii")));
        event.getRegistry().register(ZERGLING_LIVING_III.setRegistryName(new ResourceLocation("arkworld", "zergling_living_iii")));
        event.getRegistry().register(ZERGLING_LIVING_IV.setRegistryName(new ResourceLocation("arkworld", "zergling_living_iv")));
        event.getRegistry().register(HYDRALISK_DEATH.setRegistryName(new ResourceLocation("arkworld", "hydralisk_death")));
        event.getRegistry().register(HYDRALISK_HURT.setRegistryName(new ResourceLocation("arkworld", "hydralisk_hurt")));
        event.getRegistry().register(HYDRALISK_LIVING_I.setRegistryName(new ResourceLocation("arkworld", "hydralisk_living_i")));
        event.getRegistry().register(HYDRALISK_LIVING_II.setRegistryName(new ResourceLocation("arkworld", "hydralisk_living_ii")));
        event.getRegistry().register(HYDRALISK_LIVING_III.setRegistryName(new ResourceLocation("arkworld", "hydralisk_living_iii")));
        event.getRegistry().register(HYDRALISK_LIVING_IV.setRegistryName(new ResourceLocation("arkworld", "hydralisk_living_iv")));
        event.getRegistry().register(QUEEN_DEATH.setRegistryName(new ResourceLocation("arkworld", "queen_death")));
        event.getRegistry().register(QUEEN_HURT.setRegistryName(new ResourceLocation("arkworld", "queen_hurt")));
        event.getRegistry().register(QUEEN_LIVING_I.setRegistryName(new ResourceLocation("arkworld", "queen_living_i")));
        event.getRegistry().register(QUEEN_LIVING_II.setRegistryName(new ResourceLocation("arkworld", "queen_living_ii")));
        event.getRegistry().register(AMIYA_IDLE_I.setRegistryName(new ResourceLocation("arkworld", "amiya_idle_i")));
        event.getRegistry().register(AMIYA_IDLE_II.setRegistryName(new ResourceLocation("arkworld", "amiya_idle_ii")));
        event.getRegistry().register(AMIYA_IDLE_III.setRegistryName(new ResourceLocation("arkworld", "amiya_idle_iii")));
        event.getRegistry().register(AMIYA_IDLE_IV.setRegistryName(new ResourceLocation("arkworld", "amiya_idle_iv")));
        event.getRegistry().register(AMIYA_FIGHT_I.setRegistryName(new ResourceLocation("arkworld", "amiya_fight_i")));
        event.getRegistry().register(AMIYA_FIGHT_II.setRegistryName(new ResourceLocation("arkworld", "amiya_fight_ii")));
        event.getRegistry().register(AMIYA_FIGHT_III.setRegistryName(new ResourceLocation("arkworld", "amiya_fight_iii")));
        event.getRegistry().register(AMIYA_FIGHT_IV.setRegistryName(new ResourceLocation("arkworld", "amiya_fight_iv")));
        event.getRegistry().register(AMIYA_INTERACT_I.setRegistryName(new ResourceLocation("arkworld", "amiya_interact_i")));
        event.getRegistry().register(AMIYA_INTERACT_II.setRegistryName(new ResourceLocation("arkworld", "amiya_interact_ii")));
        event.getRegistry().register(AMIYA_INTERACT_III.setRegistryName(new ResourceLocation("arkworld", "amiya_interact_iii")));
        event.getRegistry().register(AMIYA_INTERACT_IV.setRegistryName(new ResourceLocation("arkworld", "amiya_interact_iv")));
        event.getRegistry().register(FROSTNOVA_FIRST_BGM.setRegistryName(new ResourceLocation("arkworld", "frostnova_first_bgm")));
        event.getRegistry().register(FROSTNOVA_SECOND_BGM.setRegistryName(new ResourceLocation("arkworld", "frostnova_second_bgm")));
        event.getRegistry().register(FROSTNOVA_SKILL_FIRST.setRegistryName(new ResourceLocation("arkworld", "frostnova_skill_first")));
        event.getRegistry().register(FROSTNOVA_SKILL_SECOND.setRegistryName(new ResourceLocation("arkworld", "frostnova_skill_second")));
        event.getRegistry().register(FROSTNOVA_TRANSFORMATION.setRegistryName(new ResourceLocation("arkworld", "frostnova_transformation")));
        event.getRegistry().register(DAMEDANE.setRegistryName(new ResourceLocation("arkworld", "damedane")));
        event.getRegistry().register(FAUST_BGM.setRegistryName(new ResourceLocation("arkworld", "faust_bgm")));
        event.getRegistry().register(FROSTNOVA_DEATH_BGM.setRegistryName(new ResourceLocation("arkworld", "frostnova_death_bgm")));
        event.getRegistry().register(MAYFEST_SINGER_FIRST_BGM.setRegistryName(new ResourceLocation("arkworld", "myfest_singer_first_bgm")));
        event.getRegistry().register(MAYFEST_SINGER_SECOND_BGM.setRegistryName(new ResourceLocation("arkworld", "myfest_singer_second_bgm")));
        event.getRegistry().register(PATRIOT_DEATH_BGM.setRegistryName(new ResourceLocation("arkworld", "patriot_death_bgm")));
        event.getRegistry().register(BLACK_SNAKE_FIRST_BGM.setRegistryName(new ResourceLocation("arkworld", "talulah_first_bgm")));
        event.getRegistry().register(FAUST_ATTACK.setRegistryName(new ResourceLocation("arkworld", "faust_attack")));
        event.getRegistry().register(FAUST_SKILL.setRegistryName(new ResourceLocation("arkworld", "faust_skill")));
        event.getRegistry().register(FROSTNOVA_SECOND_SPAWN.setRegistryName(new ResourceLocation("arkworld", "frostnova_second_spawn")));
        event.getRegistry().register(PATRIOT_RESURRECTION_SPAWN.setRegistryName(new ResourceLocation("arkworld", "patriot_resurrection_spawn")));
        event.getRegistry().register(PATRIOT_SECOND_SPAWN.setRegistryName(new ResourceLocation("arkworld", "patriot_second_spawn")));
        event.getRegistry().register(W_COUNTDOWN.setRegistryName(new ResourceLocation("arkworld", "w_countdown")));
        event.getRegistry().register(W_EXPLODE.setRegistryName(new ResourceLocation("arkworld", "w_explode")));
        event.getRegistry().register(MAYFEST_SINGER_ATTACK.setRegistryName(new ResourceLocation("arkworld", "mayfest_singer_attack")));
        event.getRegistry().register(MAYFEST_SINGER_SKILL.setRegistryName(new ResourceLocation("arkworld", "mayfest_singer_skill")));
        event.getRegistry().register(BLACK_SNAKE_ATTACK.setRegistryName(new ResourceLocation("arkworld", "talulah_attack")));
        event.getRegistry().register(BLACK_SNAKE_SKILL.setRegistryName(new ResourceLocation("arkworld", "talulah_skill")));
        event.getRegistry().register(MAYFEST_SKILL.setRegistryName(new ResourceLocation("arkworld", "mayfest_skill")));
        event.getRegistry().register(SKULLSHATTERER_BGM.setRegistryName(new ResourceLocation("arkworld", "skullshatterer_bgm")));
        event.getRegistry().register(BLACK_SNAKE_SECOND_BGM.setRegistryName(new ResourceLocation("arkworld", "talulah_second_bgm")));
        event.getRegistry().register(PATRIOT_FIRST_BGM.setRegistryName(new ResourceLocation("arkworld", "patriot_first_bgm")));
        event.getRegistry().register(PATRIOT_SECOND_BGM.setRegistryName(new ResourceLocation("arkworld", "patriot_second_bgm")));
    }
}
