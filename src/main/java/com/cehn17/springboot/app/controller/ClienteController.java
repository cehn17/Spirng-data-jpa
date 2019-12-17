package com.cehn17.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cehn17.springboot.app.models.entity.Cliente;
import com.cehn17.springboot.app.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value= {"/listar"})
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", this.clienteService.findAll());
		return "listar";
	}
	
	@GetMapping(value="/form")
	public String crear(Map<String,Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo","Formulario de Cliente");
		return "form";
	}
	
	@PostMapping(value="/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		
		String msjFlash = (cliente.getId() != null)? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", msjFlash);
		return "redirect:listar";
	}
	
	@GetMapping(value="/form/{id}")
	public String editar(@PathVariable("id") Long id, Map<String,Object> model,RedirectAttributes flash) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = this.clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del Cliente no existe en la BBDD!");
				return "redirect:/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del Cliente no puede ser 0!");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo","Editar Cliente");
		return "form";
	}
	
	@GetMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			clienteService.deleteById(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}
		return "redirect:/listar";
	}

}
