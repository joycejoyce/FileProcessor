package io.github.joycejoyce.fileprocessor;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class LoggerTestUtil {
	
	public static ListAppender<ILoggingEvent> getLogAppenderForClass(Class<?> clazz) {
	    ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
	    logAppender.start();
	    
	    Logger logger = (Logger)LoggerFactory.getLogger(clazz);
	    logger.addAppender(logAppender);

	    return logAppender;
	}
	
}
