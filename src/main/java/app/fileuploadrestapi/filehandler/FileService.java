package app.fileuploadrestapi.filehandler;

import static java.nio.file.StandardOpenOption.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	public static String outputDirPath;

	private static OpenOption[] options_createNew = new OpenOption[] { WRITE, CREATE_NEW };
	private static OpenOption[] options_append = new OpenOption[] { WRITE, APPEND };

	public void storeToDir(MultipartFile file) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(file.getBytes());
			out.flush();
			out.close();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			Date currentDate = new Date();
			String outFile = outputDirPath + File.separator + dateFormatter.format(currentDate) + File.separator
					+ currentDate.getHours() + File.separator + file.getOriginalFilename();
			File parentDir = new File(outFile).getParentFile();
			if (!parentDir.exists()) {
				System.out.println("Parent Directory doesn't exist. Creating parent directory and ancestors: "
						+ parentDir.toString());
				parentDir.mkdirs();
			}
			Path path = Paths.get(outFile);
			if (!new File(outFile).exists()) {
				Files.write(path, out.toByteArray(), options_createNew);
			} else {
				Files.write(path, out.toByteArray(), options_append);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Resource displayFile(String file) throws MalformedURLException {
		Path filePath = Paths.get(outputDirPath).resolve(file);
		Resource fileLoc = new UrlResource(filePath.toUri());
		if (!fileLoc.exists() || !fileLoc.isReadable()) {
			System.out.println("File doesn't exists or it's not human readble.");
		}
		return fileLoc;
	}
}