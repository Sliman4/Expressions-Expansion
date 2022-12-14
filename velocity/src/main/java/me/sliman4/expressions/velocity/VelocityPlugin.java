package me.sliman4.expressions.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.Dependency;

import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.sliman4.expressions.Expressions;
import me.sliman4.expressions.Platform;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        name = "Expressions-Expansion",
        id = "expressionsexpansion",
        version = "1.0.0",
        authors = {"Sliman4"},
        dependencies = {
                @Dependency(id = "miniplaceholders")
        }
)
public final class VelocityPlugin {
    private final Logger logger;
    private final Path dataFolder;
    private final Platform platform;

    @Inject
    public VelocityPlugin(Logger logger, ProxyServer server, @DataDirectory final Path dataFolder) {
        this.logger = logger;
        this.dataFolder = dataFolder;
        this.platform = new VelocityPlatform(server);
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Starting Expressions Expansion for Velocity");

        Expressions.initialize(dataFolder.toFile(), getClass().getClassLoader().getResourceAsStream("config.yml"), platform);
    }
}
