package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.RoleService;

@Component
public class RolesPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			this.setValue(roleService.obtenerRolePorId(Integer.parseInt(id)));
		}catch(NumberFormatException e) {
			this.setValue(null);
		}		
	}
}
