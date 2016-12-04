package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Scanner;

class ShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ShellCommand.class);

    private final String command;

    /**
     * Create {@link ShellCommand}.
     *
     * @param command - command to execute
     */
    ShellCommand(String command) {
        Assert.notNull(command, "command cannot be null");
        this.command = command;
    }

    void executeInRelativePath(String directory) {
        this.execute(System.getProperty("user.dir") + directory);
    }

    void execute(String directory) {
        try {
            Process process = new ProcessBuilder().command(command.split(" "))
                                                  .directory(new File(directory))
                                                  .redirectErrorStream(true)
                                                  .start();

            Scanner s = new Scanner(process.getInputStream());
            while (s.hasNextLine()) {
                LOG.info(s.nextLine());
            }
        } catch (Exception e) {
            LOG.error("Error executing command: [" + this.command + "] in directory [" + directory + "]", e);
            throw new RuntimeException(e);
        }
    }
}
