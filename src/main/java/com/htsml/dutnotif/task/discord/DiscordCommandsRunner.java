package com.htsml.dutnotif.task.discord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htsml.dutnotif.api.discord.DiscordApiSender;
import com.htsml.dutnotif.api.discord.DiscordApiSenderImpl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DiscordCommandsRunner {
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    private static String appId;

    private static String token;

    private static DiscordApiSender discordApiSender;

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(DiscordCommandsRunner.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE));

        appId = properties.getProperty("discord.app.id");
        token = properties.getProperty("discord.app.token");
        discordApiSender = new DiscordApiSenderImpl(token);

        registerCommands();
    }

    private static void registerCommands() {
        try (InputStream commandsStream =
                     DiscordCommandsRunner.class.getClassLoader().getResourceAsStream("discord/command/commands.json")) {
            if (commandsStream == null) throw new RuntimeException("Commands file not found");

            BufferedInputStream bufferedCommandsStream = new BufferedInputStream(commandsStream);
            String commands = new String(bufferedCommandsStream.readAllBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode data = objectMapper.readTree(commands).get("data");

            System.out.println("Registering commands: "+ data.toString());

            discordApiSender.send(String.format("/applications/%s/commands", appId), data.toString(), "PUT");

            System.out.println("Commands registered: ");
        } catch (Exception e) {
            throw new RuntimeException("Error while registering commands", e);
        }
    }

}
