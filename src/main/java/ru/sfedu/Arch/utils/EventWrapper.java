package ru.sfedu.Arch.utils;
import org.apache.logging.log4j.Logger;

public class EventWrapper {
    private final static String WRAPPER_STRING = "Event [%d]: %s";
    private static Logger log = null;

    public EventWrapper (Logger log) {
        EventWrapper.log = log;
    }

    public <T> void debug (int eventNumber, T message) {
        log.debug(String.format(WRAPPER_STRING, eventNumber, message));
    }

    public <T> void error (int eventNumber, T message) {
        log.error(String.format(WRAPPER_STRING, eventNumber, message));
    }

    public <T> void info (int eventNumber, T message) {
        log.info(String.format(WRAPPER_STRING, eventNumber, message));
    }
}
