package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.UpperCaseEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {
	@Autowired
	private UsuarioValidador validador;
	
	@InitBinder public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, "nombre", new UpperCaseEditor());
		binder.registerCustomEditor(String.class, "apellido", new UpperCaseEditor());
	}
	
	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("Argentina","Uruguay","Chile","Brasil");
	}
	 
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setId("12.456.887-K");
		
		model.addAttribute("titulo", "Formulario de Usuario");
		model.addAttribute("usuario", usuario);
		
		return "form";
	}
	
	@PostMapping("/form")
	public String process(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		model.addAttribute("titulo", "Formulario de Usuario");
		
		if(result.hasErrors())
			return "form";
		
		model.addAttribute("titulo", "Resultado de Usuario");
		model.addAttribute("usuario", usuario);
		status.setComplete();
		
		return "result";
	}
}
