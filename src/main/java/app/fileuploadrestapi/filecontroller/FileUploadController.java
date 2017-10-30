package app.fileuploadrestapi.filecontroller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import app.fileuploadrestapi.filehandler.FileService;

@RestController
public class FileUploadController {

	@Autowired
	FileService fileHandler;

	List<String> uploadedFiles = new ArrayList<String>();
	
	/**
	 * POST /api/upload-file -> receive and locally save a file.
	 * 
	 * @param fileUploading The uploaded file as Multipart file parameter in the 
	 * HTTP request. The RequestParam name must be the same of the attribute 
	 * "name" in the input tag with type file.
	 * 
	 * @return An success message in case of success, an error message in case 
	 * of errors.
	 */
	@PostMapping("/api/upload-file")
	public String fileUpload(@RequestParam("fileUploading") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				fileHandler.storeToDir(file);
				uploadedFiles.add(file.getOriginalFilename());
				return "Successfully uploaded - " + file.getOriginalFilename() + " !";
			} catch (Exception e) {
				return "FAILED to upload " + file.getOriginalFilename() + " . Try Again";
			}
		}else {
			return "Please Upload Valid File";
		}
	}

	/**
	 * GET /get-all-files-uploaded -> Get all the files uploaded on to the server.
	 *  
	 * @return List of files that uploaded succesfully.
	 */
	@GetMapping("/get-all-files-uploaded")
	public List<String> getUploadedFilesList() {
		List<String> filesList = new ArrayList<String>();
		filesList = uploadedFiles
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(FileUploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
		return filesList;
	}

	@GetMapping("/uploadedFiles/{fileName:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws MalformedURLException {
		Resource file = fileHandler.displayFile(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
