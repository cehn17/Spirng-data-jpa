package com.cehn17.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cehn17.springboot.app.models.dao.IClienteDao;
import com.cehn17.springboot.app.models.entity.Cliente;

@Service
public class IClienteServiceImpl implements IClienteService {

	@Autowired
	IClienteDao clienteDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Transactional
	@Override
	public void save(Cliente c) {
		clienteDao.save(c);
	}

	@Transactional(readOnly=true)
	@Override
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		clienteDao.deleteById(id);
	}

}
