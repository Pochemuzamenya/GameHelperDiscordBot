package org.filatov.appcommand;

import discord4j.discordjson.json.ApplicationCommandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface AppCommand {
    Logger LOG = LoggerFactory.getLogger(AppCommand.class);

    ApplicationCommandRequest command();

}
