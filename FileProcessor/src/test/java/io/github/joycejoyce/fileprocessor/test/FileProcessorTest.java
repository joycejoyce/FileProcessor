package io.github.joycejoyce.fileprocessor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.github.joycejoyce.fileprocessor.*;

public class FileProcessorTest {
	
	ListAppender<ILoggingEvent> logAppender;
	final String ROOT_FOLDER = "C:\\Users\\Joyce\\Documents\\GitRepo_Projects\\FileProcessor\\test_folder";
	final String TEMPLATE_FOLDER = "../template_folder";
	final String COPIED_FOLDER_NAME = "copied_folder";
	final String DUMMY_FOLDER_NAME = "dummy_folder";
	final String YEAR_MONTH_DAY_FOLDER_NAME = "2019/01/02";
	final String DUMMY_FILE_NAME = "dummy.txt";
	final String DATE = "20190102";
	
	@Before
	public void cleanRootFolder() {
		try {
			FileUtils.cleanDirectory(new File(ROOT_FOLDER));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void initLogAppender() {
		logAppender = LoggerTestUtil.getLogAppenderForClass(FileProcessor.class);
	}
	
	@Test
	public void testCreateOneLevelFolder() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, "Level1");
		assertTrue(Files.exists(folder.get()) && Files.isDirectory(folder.get()));
	}
	
	@Test
	public void testCreateNestedFolder() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, "Level1/Level2");
		assertTrue(Files.exists(folder.get()) && Files.isDirectory(folder.get()));
	}
	
	@Test
	public void testCreateFileInExistedFolder() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(Files.exists(folder.get()));
		
		Optional<Path> file = FileProcessor.createFile(folder.get().toString(), DUMMY_FILE_NAME);
		assertTrue(Files.exists(file.get()));
	}
	
	@Test
	public void testCreateFileInNotExistedFolder() {
		FileProcessor.deleteFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(!Files.exists(Paths.get(ROOT_FOLDER + "/" + DUMMY_FOLDER_NAME)));
		
		Optional<Path> file = FileProcessor.createFile(ROOT_FOLDER, DUMMY_FILE_NAME);
		assertTrue(Files.exists(file.get()));
	}
	
	@Test
	public void testDeleteFolder() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(Files.exists(folder.get()));
		
		FileProcessor.deleteFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(!Files.exists(Paths.get(ROOT_FOLDER + "/" + DUMMY_FOLDER_NAME)));
	}

	@Test
	public void givenFolderExistsWhenCreateFolderThenPrintMessage() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(Files.exists(folder.get()));
		
		FileProcessor.createFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(logAppender.list.get(0).getMessage().contains(ErrorMessage.FOLDER_ALREADY_EXISTS));
	}
	
	@Test
	public void givenFolderNotExistsWhenDeleteFolderThenPrintMessage() {
		FileProcessor.deleteFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(!Files.exists(Paths.get(ROOT_FOLDER + "/" + DUMMY_FOLDER_NAME)));
		
		FileProcessor.deleteFolder(ROOT_FOLDER, DUMMY_FOLDER_NAME);
		assertTrue(logAppender.list.get(0).getMessage().contains(ErrorMessage.DELETE_FOLDER_FAIL));
	}
	
	@Test
	public void testIsDate() {
		assertTrue(FileProcessor.isDate("20190131"));
		assertTrue(!FileProcessor.isDate("20190133"));
	}
	
	@Test
	public void testGetYearMonthDayByDate() {
		DateObj dateObj = new DateObj(DATE);
		assertEquals(dateObj.getYear(), DATE.substring(0, 4));
		assertEquals(dateObj.getMonth(), DATE.substring(4, 6));
		assertEquals(dateObj.getDay(), DATE.substring(6));
	}
	
	@Test
	public void testGetYearMonthDayFolderNameByDate() {
		String yearMonthDayFolderName = FileProcessor.getYearMonthDayFolderNameByDate(DATE);
		assertEquals(yearMonthDayFolderName, YEAR_MONTH_DAY_FOLDER_NAME);
	}
	
	@Test
	public void testSeparateDateFolderToYearMonthDayFolders() {
		Optional<Path> folder = FileProcessor.createFolder(ROOT_FOLDER, DATE);
		assertTrue(Files.exists(folder.get()));
		
		FileProcessor.separateDateFolderToYearMonthDayFolders(ROOT_FOLDER, DATE);
		
		DateObj dateObj = new DateObj(DATE);
		assertTrue(!Files.exists(Paths.get(ROOT_FOLDER + "/" + DATE)));
		assertTrue(Files.exists(Paths.get(ROOT_FOLDER + "/" + dateObj.getYear())));
		assertTrue(Files.exists(Paths.get(ROOT_FOLDER + "/" + dateObj.getYear() + "/" + dateObj.getMonth())));
		assertTrue(Files.exists(Paths.get(ROOT_FOLDER + "/" + dateObj.getYear() + "/" + dateObj.getMonth() + "/" + dateObj.getDay())));
	}
	
	@Test
	public void testSeperateAllDateFolders() {
		try {
			FileUtils.copyDirectory(new File(ROOT_FOLDER + "/" + TEMPLATE_FOLDER), new File(ROOT_FOLDER + "/" + COPIED_FOLDER_NAME));
		} catch (IOException e) {
			fail(ErrorMessage.COPY_FOLDER_FAIL);
			e.printStackTrace();
		}
		
		FileProcessor.seperateAllDateFolders(ROOT_FOLDER, COPIED_FOLDER_NAME);
		String copiedPathStr = ROOT_FOLDER + "/" + COPIED_FOLDER_NAME;
		assertTrue(!Files.exists(Paths.get(copiedPathStr + "/20190802")));
		assertTrue(Files.exists(Paths.get(copiedPathStr + "/2019/08/02")));
		assertTrue(!Files.exists(Paths.get(copiedPathStr + "/20190831")));
		assertTrue(Files.exists(Paths.get(copiedPathStr + "/2019/08/31")));
	}
}
