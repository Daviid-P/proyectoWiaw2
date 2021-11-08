package org.escoladeltreball.proyectowiaw2.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.mail.AuthenticationFailedException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    //commons-fileupload
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String errorSubida1(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {

    	List<String> errores = new ArrayList<String>();
		errores.add("El fichero supera el tamaño maximo permitido: 512KB");
        redirectAttributes.addFlashAttribute("errores", errores);
        return "redirect:/profile";

    }
    
 // Excepcion propia para AccessDeniedException al intentar descargar un expediente
    @ExceptionHandler(DownloadDeniedException.class)
    public String errorBajada1(Exception e, Model model) {

    	List<String> mensajes = new ArrayList<String>();

    	mensajes.add("Esta pagina es solo para empleados.");
		mensajes.add("Si eres un paciente y quieres descargar tu expediente puedes hacerlo desde tu dashboard al consultar tu expediente.");
		
		model.addAttribute("error","Acceso no Permitido.");
		model.addAttribute("mensajes", mensajes);
        return "mensaje";

    }
    
 // Excepcion propia para AccessDeniedException al intentar descargar un expediente
    @ExceptionHandler(PdfCreationException.class)
    public String errorBajada2(Exception e, Model model) {

    	List<String> mensajes = new ArrayList<String>();
		
		mensajes.add("No se ha podido generar el expediente del paciente.");
		mensajes.add("Comprueba que el DNI este bien escrito y que pertenezca a un paciente con expediente.");
		
		model.addAttribute("error","Error.");
		model.addAttribute("mensajes", mensajes);
		
		return "mensaje";

    }

    // Excepcion propia para AccessDeniedException al intentar descargar un expediente
    @ExceptionHandler(AuthenticationFailedException.class)
    public String errorLoginEmail(Exception e, Model model) {

    	List<String> mensajes = new ArrayList<String>();

    	mensajes.add("Esta pagina es solo para empleados.");
		mensajes.add("Si eres un paciente y quieres descargar tu expediente puedes hacerlo desde tu dashboard al consultar tu expediente.");
		
		model.addAttribute("error","Acceso no Permitido.");
		model.addAttribute("mensajes", mensajes);
        return "mensaje";

    }
}