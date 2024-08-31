package com.example.matchmaker;

import com.example.matchmaker.DiscordConfiguration.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class DiscordEventListener extends ListenerAdapter {

  public static final int SHUFFLE_COUNT = 3;

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (!event.isFromGuild()) {
      return;
    }
    if (!Constants.COMMAND.equals(event.getName())) {
      event.reply("아직 지원하지 않는 명령어 입니다.").queue();
    }

    Member chatMember = event.getMember();
    Guild guild = event.getGuild();
    List<VoiceChannel> voiceChannels = guild.getVoiceChannels();

    if (voiceChannels.size() < 2) {
      event.reply("음성 채널을 최소 2개 이상 생성해 주세요.").queue();
      return;
    }
    GuildVoiceState voiceState = chatMember.getVoiceState();

    if (voiceState == null || voiceState.getChannel() == null) {
      event.reply("음성 채널에 접속해 주세요.").queue();
      return;
    }
    VoiceChannel authorVoiceChannel = guild.getVoiceChannelById(voiceState.getChannel().getId());
    List<Member> members = new ArrayList<>(authorVoiceChannel.getMembers());

    for (int i = 0; i < SHUFFLE_COUNT; i++) {
      Collections.shuffle(members);
    }

    for (int i = 0; i < members.size(); i++) {
      Member member = members.get(i);
      if (i % 2 == 0) {
        guild.moveVoiceMember(member, voiceChannels.get(0)).queue();
        continue;
      }
      guild.moveVoiceMember(member, voiceChannels.get(1)).queue();
    }

    event.reply("팀 나누기가 완료되었습니다.").queue();
  }
}
