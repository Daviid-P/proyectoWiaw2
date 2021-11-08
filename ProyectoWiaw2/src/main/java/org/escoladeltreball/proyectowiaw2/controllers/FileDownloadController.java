package org.escoladeltreball.proyectowiaw2.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.exceptions.DownloadDeniedException;
import org.escoladeltreball.proyectowiaw2.exceptions.PdfCreationException;
import org.escoladeltreball.proyectowiaw2.repositories.RecepcionistaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileDownloadController {

	@Autowired
	AccessCheck accessCheck;
	
	@Autowired
	RecepcionistaDAO recepcionistaDao;
	
	@Autowired
	Environment env;

	@RequestMapping(value="/descargar/expediente", method = RequestMethod.GET)
	public void descargarExpUser(Locale locale, HttpServletResponse response, Principal principal) throws PdfCreationException{
		
		///////////////////////////////////////////////////
		accessCheck.metodoPaciente(principal.getName());
		///////////////////////////////////////////////////
		
		if (!recepcionistaDao.createPDF(principal.getName())) {
			// Algo ha fallado al crear el pdf
			throw new PdfCreationException();
		}
		
		Path expedientesDir = Paths.get(env.getProperty("app.expedientesDir"));
		
		if (!expedientesDir.toFile().exists()) {
			expedientesDir = Paths.get(env.getProperty("app.expedientesDir2"));
		}
		
		File expedientePdf = new File(expedientesDir+"/"+principal.getName()+".pdf");
		System.out.println(expedientePdf.toString());
		//System.out.println("Descargando "+expedientePdf.toString());
		if (expedientePdf.exists()) {
		
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename="+principal.getName()+".pdf");
			
			try {
				Files.copy(expedientePdf.toPath(), response.getOutputStream());
				response.getOutputStream().flush();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
		
	}
	
	@RequestMapping(value="/descargar/expediente/{dni}", method = RequestMethod.GET)
	public void descargarExpEmp(Locale locale, HttpServletResponse response, Principal principal,
								@PathVariable("dni") String dni) throws DownloadDeniedException, PdfCreationException {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (Exception e) {
			throw new DownloadDeniedException();
		}
		
		if (!recepcionistaDao.createPDF(dni)) {
			// Algo ha fallado al crear el pdf
			throw new PdfCreationException();
		}
		
		Path expedientesDir = Paths.get(env.getProperty("app.expedienteDir2"));
		
		if (!expedientesDir.toFile().exists()) {
			expedientesDir = Paths.get(env.getProperty("app.expedienteDir1"));
		}
		
        File expedientePdf = new File(expedientesDir+"/"+dni+".pdf");
        System.out.println(expedientePdf.toString());
        //System.out.println("Descargando "+expedientePdf.toString());
        if (expedientePdf.exists()) {
        	
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+dni+".pdf");
            try {
                Files.copy(expedientePdf.toPath(), response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
}
