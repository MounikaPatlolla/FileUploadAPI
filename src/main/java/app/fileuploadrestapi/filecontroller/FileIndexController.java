package app.fileuploadrestapi.filecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileIndexController {

	/**
	 * Show the index page containing the form for uploading a file.
	 */
	@GetMapping("/")
	public String uploadedFilesList(Model model) {
		return "fileupload";
	}

}
