package br.unip.chacara.APSAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unip.chacara.APSAPI.controller.dto.MensagemWatsonDTO;
import br.unip.chacara.APSAPI.service.WatsonService;


@RestController
public class APSController {
	@Autowired
	private WatsonService watsonService;
		
	@RequestMapping("/watson")
	public MensagemWatsonDTO watson(@RequestBody MensagemWatsonDTO mensagem) {
		
		try {
			mensagem = watsonService.callWatson(mensagem);
			mensagem.setCodRetorno(0);
		}catch (Exception e) {
			mensagem.setCodRetorno(99);
		}
		return mensagem;
	}
	
}
