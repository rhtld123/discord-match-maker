package com.example.matchmaker;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DiscordConfiguration {

  private final DiscordProperties discordProperties;

  @PostConstruct
  private void init() {
    log.info("initialize JDA...");
    JDA jda = JDABuilder.createDefault(discordProperties.getToken())
        .setActivity(Activity.playing("팀 짜려고 기다리는 중.."))
        .enableIntents(GUILD_MESSAGES, GUILD_VOICE_STATES)
        .addEventListeners(new DiscordEventListener())
        .build();
  }
}
