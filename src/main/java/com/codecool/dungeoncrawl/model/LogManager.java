package com.codecool.dungeoncrawl.model;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Date;

public class LogManager {
    private static final String LOG_FILE_PATH = "debug/game.log";
    private final String logFileName;

    private FileWriter logFile;
    private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    private LogManager(String logFileName) {
        this.logFileName = logFileName;
    }
    private static final LogManager logger = new LogManager(LOG_FILE_PATH);

    private void logMessage(String message) throws IOException {
        FileOutputStream logFileWriter = null;
        try {
            logFileWriter = new FileOutputStream(logFileName, true);
        } catch (IOException ignored) {}  // realistically shouldn't happen
        logFileWriter.write(String.format( "[%s]: %s%n", new Date().toString().substring(4, 19), message ).getBytes());
        logFileWriter.close();
    }

    public static void enqueueMessage(String message) { logger.messageQueue.add(message); }
    public static void dumpQueue() {
        System.out.println(logger.messageQueue);
        while (!logger.messageQueue.isEmpty()) {
            try {
                logger.logMessage(logger.messageQueue.remove());
            } catch (IOException ignored) {}
        }
    }
}
