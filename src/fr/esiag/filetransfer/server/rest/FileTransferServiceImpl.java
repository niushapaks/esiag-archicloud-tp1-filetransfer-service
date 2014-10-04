package fr.esiag.filetransfer.server.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystemException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Implementation of the FileTransfer REST Service
 * @author Maxime Pakseresht
 */
@Path("/filetransfer")
public class FileTransferServiceImpl implements FileTransferService {

	/**
	 * @TODO define the UPLOAD_LOCATION_FOLDER in a properties file
	 */
	public static final String UPLOAD_LOCATION_FOLDER = "D:/LocalData/a036411/Documents_Locaux/upload/";
	
	/**
	 * upload medhod exposed on the REST Service
	 * 
	 * accessible via HTTP POST Request
	 * accept Multipart Form Data to take a file input stream and the file name 
	 * 
	 */
	@Override
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream, 
			@FormDataParam("filename") String fileName) {

		String filePath = UPLOAD_LOCATION_FOLDER + fileName;
		
		/**
		 * Saving the file. In function of the success of the save, returning a Response
		 * to the Client : OK or INTERNAL_SERVER_ERROR
		 */
		try {
			saveFile(fileInputStream, filePath);
			
			String responseStr = "File saved at the server : " + filePath;
			return Response.status(Response.Status.OK).entity(responseStr).build();
			
		} catch (IOException e) {
			String responseStr = "Error while saving the file at : " + filePath + " : " + e.getMessage();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseStr).build();
		}

		
	}

	/**
	 * Method saving the file at a specified location
	 */
	@Override
	public void saveFile(InputStream uploadedInputStream, String serverLocation) throws IOException {
		
		try {
			OutputStream outputStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			outputStream = new FileOutputStream(new File(serverLocation));
			/**
			 * While the input stream is not empty :
			 *  - save 1024 bytes in the buffer
			 *  - write these 1024 bytes in the File that is being saved
			 *  	via an output stream.
			 *  
			 *  When the input stream is empty, flushing and closing the output stream.
			 */
			while((read = uploadedInputStream.read(bytes)) != -1){
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			
		} catch (IOException e) {
			throw e;
		}	
		
	}

}
