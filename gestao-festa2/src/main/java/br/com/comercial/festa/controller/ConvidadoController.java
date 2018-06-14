package br.com.comercial.festa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.validation.Valid;

import br.com.comercial.festa.model.Convidado;
import br.com.comercial.festa.repository.ConvidadoRepository;

@Controller
@RequestMapping("/convidados")
public class ConvidadoController {
	@Autowired
	public ConvidadoRepository convidadoRepository;
	
	@GetMapping
	public ModelAndView listarConvidados(Convidado convidado) {
		ModelAndView model = new ModelAndView("/convidados-lista");
		List<Convidado> lista = convidadoRepository.findAll();
		model.addObject("convidados", lista);
		return model;
	}

	@GetMapping(value = "/adicionar")
	public ModelAndView adicionar(Convidado convidado) {
		ModelAndView mv = new ModelAndView("/cadastro");
		mv.addObject(convidado);
		return mv;
	}

	//@GetMapping(value = "/salvar")

	@RequestMapping(value = { "/salvar", "{\\d+}" }, method = RequestMethod.POST) 
	public ModelAndView salvar(@Valid Convidado convidado, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return adicionar(convidado);
		}
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Registro salvo com sucesso!");
		return new ModelAndView("redirect:/convidados");
	}
	
	@GetMapping(value="/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Convidado convidado = convidadoRepository.getOne(id);
		ModelAndView mv = adicionar(convidado);
		mv.addObject(convidado);
		return mv;
	}
	
	@GetMapping(value = "/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
		convidadoRepository.delete(convidadoRepository.getOne(id));
		attributes.addFlashAttribute("mensagem", "Registro excluído com sucesso!");
		return new ModelAndView("redirect:/convidados");
	}

}
