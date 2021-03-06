package com.cehn17.springboot.app.service;

import java.util.List;

import com.cehn17.springboot.app.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public void save(Cliente c);
	
	public Cliente findOne(Long id);
	
	public void deleteById(Long id);
}
