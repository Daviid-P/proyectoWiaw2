package org.escoladeltreball.proyectowiaw2.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
	
	@Autowired
	ServletContext context; 

	@Autowired
	Environment env;
	
	// POST Avatar usuario
	@PostMapping(value = "/avatar")
	public String avatar(Locale locale, Model model, Principal principal,
						@RequestPart("avatar") MultipartFile avatarFile, RedirectAttributes redirectAttributes) throws MaxUploadSizeExceededException {
		List<String> errores = new ArrayList<String>();
		
		if (avatarFile.isEmpty()) {
			errores.add("El fichero esta vacio.");
	        redirectAttributes.addFlashAttribute("errores", errores);
	        return "redirect:/profile";
		}
		
		Path avatarDir = Paths.get(env.getProperty("app.avatarDir"));
		
		if (!avatarDir.toFile().exists()) {
			avatarDir = Paths.get(env.getProperty("app.avatarDir2"));
		}
		
		if (!avatarDir.toFile().exists()) {
			avatarDir = Paths.get(env.getProperty("app.avatarDir3"));
		}

		
		File destino = new File(avatarDir+"/"+principal.getName());
		
		try {
			avatarFile.transferTo(destino);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errores.add(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e.getMessage().contains("No such file or directory")) {
				errores.add("No such file or directory");
			} else {
				errores.add(e.getMessage());
			}
		}
		
		redirectAttributes.addFlashAttribute("errores", errores);	
		
		return "redirect:/profile";
	}

}
