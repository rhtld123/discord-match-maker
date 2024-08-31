package com.example.matchmaker;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;
import static net.dv8tion.jda.api.requests.GatewayIntent.MESSAGE_CONTENT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
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
        .setActivity(Activity.playing("내전 대기"))
        .enableIntents(GUILD_MESSAGES, GUILD_VOICE_STATES, MESSAGE_CONTENT)
        .addEventListeners(new DiscordEventListener())
        .build();

    jda.updateCommands().addCommands(Commands.slash(Constants.COMMAND, "팀을 나눕니다.")).complete();
  }

  public static class Constants {
    public static final String COMMAND = "팀나누기";
  }
}
