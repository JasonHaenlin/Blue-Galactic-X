package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class AppLog {

    private final static Logger ROOT = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public final static AppLog LOGGER_INSTANCE = new AppLog();

    private static final int NORMAL = 0;
    private static final int RED = 31;
    private static final int MAGENTA = 35;
    private static final int CYAN = 36;
    private static final int YELLOW = 33;

    private static final String PREFIX = "\u001b[";
    private static final String SUFFIX = "m";
    private static final char SEPARATOR = ';';
    private static final String END_COLOUR = PREFIX + SUFFIX;

    private final static String NEWLINE = "\n";
    private final static String TAB = "    ";
    private final static String SECTION = "";

    private AppLog() {
        ROOT.setLevel(Level.INFO);
    }

    public AppLog info(String str) {
        ROOT.info(TAB + PREFIX + NORMAL + SEPARATOR + YELLOW + SUFFIX + "Info > " + str + END_COLOUR + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public AppLog request(String str) {
        ROOT.info(TAB + PREFIX + NORMAL + SEPARATOR + MAGENTA + SUFFIX + "Request > " + str + END_COLOUR + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public AppLog response(String str) {
        ROOT.info(TAB + PREFIX + NORMAL + SEPARATOR + CYAN + SUFFIX + "Response > " + str + END_COLOUR + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public AppLog trace(String str) {
        ROOT.info(TAB + str + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public AppLog error(String str) {
        ROOT.info(TAB + PREFIX + NORMAL + SEPARATOR + RED + SUFFIX + "Error > " + str + END_COLOUR + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public AppLog endSection() {
        ROOT.info(SECTION + NEWLINE);
        return LOGGER_INSTANCE;
    }

    public static AppLog getInstance() {
        return LOGGER_INSTANCE;
    }

}
