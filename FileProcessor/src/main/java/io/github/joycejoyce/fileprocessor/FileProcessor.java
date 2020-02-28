package io.github.joycejoyce.fileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessor {
	
	final static Logger logger = LoggerFactory.getLogger(FileProcessor.class);

	public static Optional<Path> createFile(String path, String fileName) {
		File f = new File(path + "/" + fileName);
		try {
			FileUtils.touch(f);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Optional.ofNullable(f.toPath());
	}
	
	public static Optional<Path> createFolder(String path, String folderName) {
		final String folderPath = path + "/" + folderName;
		Path folder = Paths.get(folderPath);
		try {			
			if(!Files.exists(folder) ) {
				Files.createDirectories(folder);
			}
			else {
				logger.error(ErrorMessage.FOLDER_ALREADY_EXISTS + ", " + folderPath);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Optional.ofNullable(folder);
	}

	public static void deleteFolder(String folderPath, String folderName) {
		final String fullPath = folderPath + "/" + folderName;
		Path folder = Paths.get(fullPath);
		if(canDoFolderDelete(folder)) {
			try {
				FileUtils.deleteDirectory(folder.toFile());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		else {
			logger.error(ErrorMessage.DELETE_FOLDER_FAIL);
		}
	}

	private static boolean canDoFolderDelete(Path folder) {
		return Files.exists(folder) && Files.isDirectory(folder);
	}

	public static boolean isDate(String s) {
		try {
			LocalDate.parse(s, DateTimeFormatter.BASIC_ISO_DATE);
		} catch(DateTimeParseException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public static String getYearMonthDayFolderNameByDate(String date) {
		DateObj dateObj = new DateObj(date);
		return dateObj.getYear() + "/" + dateObj.getMonth() + "/" + dateObj.getDay();
	}

	public static void separateDateFolderToYearMonthDayFolders(String path, String dateFolderName) {
		String yearMonthDayFolderName = getYearMonthDayFolderNameByDate(dateFolderName);
		
		Path oldPath = Paths.get(path + "/" + dateFolderName);
		Optional<Path> newPath = createFolder(path, yearMonthDayFolderName);
		
		try {
			Files.move(oldPath, newPath.get(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void seperateAllDateFolders(String path, String folderName) {
		String fullPath = path + "/" + folderName;
		List<File> subDirs = Arrays.asList(new File(fullPath).listFiles());
		subDirs.forEach(file -> {
			if(isDate(file.getName())) {
				separateDateFolderToYearMonthDayFolders(fullPath, file.getName());
			}
		});
	}
}
