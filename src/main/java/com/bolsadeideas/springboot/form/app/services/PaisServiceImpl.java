package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImpl implements PaisService {

	private List<Pais> lista;
	
	public PaisServiceImpl() {
		this.lista = Arrays.asList(
			new Pais(1, "AR", "Argentina"),
			new Pais(2, "UR", "Uruguay"),
			new Pais(3, "CH", "Chile"),
			new Pais(4, "BR", "Brasil")
		);
	}

	@Override
	public List<Pais> listar() {
		return this.lista;
	}

	@Override
	public Pais obtenerPaisPorId(Integer id) {
		for(Pais pais : this.lista) {
			if(pais.getId() == id)
				return pais;
		}
		
		return null;
	}

}
