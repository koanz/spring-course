package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.UpperCaseEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private RoleService rolesService;
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RolesPropertyEditor rolesEditor;
	
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, "nombre", new UpperCaseEditor());
		binder.registerCustomEditor(String.class, "apellido", new UpperCaseEditor());
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Role.class, "roles", rolesEditor);
	}
	
	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("Argentina","Uruguay","Chile","Brasil");
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		return paisService.listar();
	}
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles() {
		return rolesService.listar();
	}
	
	@ModelAttribute("listaGeneros")
	public List<String> listaGeneros() {
		return Arrays.asList("Femenino", "Masculino");
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesString() {
		Map<String, String> roles = new HashMap<>();
		
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");

		return roles;
	}
	
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<>();
		
		paises.put("AR", "Argentina");
		paises.put("UR", "Uruguay");
		paises.put("CH", "Chile");
		paises.put("BR", "Brasil");

		return paises;
	}
	 
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setId("12.456.887-K");
		usuario.setHabilitar(true);
		
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
