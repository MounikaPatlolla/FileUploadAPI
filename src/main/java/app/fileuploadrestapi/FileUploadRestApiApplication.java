package app.fileuploadrestapi;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.fileuploadrestapi.filehandler.FileService;
import app.fileuploadrestapi.util.ConfigurationManager;

@SpringBootApplication
public class FileUploadRestApiApplication {

	public static void main(String[] args) {
		try {
			String outputDirPath = ConfigurationManager.getInstance().getProperty("file.upload.output.DirectoryPaths");
			FileService.outputDirPath = outputDirPath;
			SpringApplication.run(FileUploadRestApiApplication.class, args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
