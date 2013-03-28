package de.vogella.jersey.first;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ch.hsr.data.OutputFile;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import javax.persistence.*;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;

@Path("/file")
public class UploadFileService {
	@Context ServletContext context;
	
	
	
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OutputFile uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String fileId = UUID.randomUUID().toString();
		String uploadedFileLocation = context.getInitParameter("ch.hsr.challp.UploadPath")+fileId;

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		UploadedFile uploadedFile =  new UploadedFile();
		uploadedFile.setFileId(fileId);

		
		OutputFile output = new OutputFile();
		output.setId(fileId);
		output.setUrl(uploadedFileLocation);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OutputFile");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(output);
		em.getTransaction().commit();
		
		return output;
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[4096];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}