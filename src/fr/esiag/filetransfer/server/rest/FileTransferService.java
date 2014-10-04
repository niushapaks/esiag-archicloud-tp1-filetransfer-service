package fr.esiag.filetransfer.server.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;

import javax.ws.rs.core.Response;



public interface FileTransferService {
	
	public Response uploadFile(InputStream fileInputStream, String fileName);
	
	public void saveFile(InputStream uploadedFile, String serverLocation) throws IOException;
	
}
