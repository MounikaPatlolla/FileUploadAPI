package app.fileuploadrestapi.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class FileUploadAPIException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	
	public FileUploadAPIException() {
		
	}
	
	public FileUploadAPIException(Exception exception) {
		super(exception);
		this.message = exception.getMessage();
	}
	
	public FileUploadAPIException(String message) {
		this.message = message;
	}
	
	public FileUploadAPIException(String message, Exception exception) {
		super(exception);
		if (exception != null){
			this.message = message+" "+exception.getMessage();
		}
		else
			this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getTrace(Exception exception) {
		StringWriter wr = new StringWriter();
		PrintWriter pr = new PrintWriter(wr);
		exception.printStackTrace(pr);
		try {
		    pr.close();
		    wr.close();
		} catch (IOException e) {
			System.out.println("Exception while closing writers for converting  the stacktrace to a String. "+ new Date());
		}
		return wr.toString();
	} 
	
	// The will return the first n char of the trace
	public static String getTrace(Exception exception, int numChars) {
		String trace = getTrace(exception);
		if (trace != null) {
			if (trace.length() >= numChars) {
				trace = trace.substring(0, numChars);
			}
		}
		return trace;
	}
}