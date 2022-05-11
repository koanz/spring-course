package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.PaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private PaisService paisService;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException { 
		try {
			this.setValue(paisService.obtenerPaisPorId(Integer.parseInt(id)));
		}catch(NumberFormatException e) {
			this.setValue(null);
		}
	}

}
