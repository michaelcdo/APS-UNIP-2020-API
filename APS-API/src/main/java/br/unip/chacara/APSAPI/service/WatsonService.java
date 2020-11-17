package br.unip.chacara.APSAPI.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DialogNodeOutputOptionsElement;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.unip.chacara.APSAPI.controller.dto.MensagemWatsonDTO;
import br.unip.chacara.APSAPI.controller.util.APSAPIConstants;

@Service
public class WatsonService {
	
	public MensagemWatsonDTO callWatson(MensagemWatsonDTO message) {
		String watsonSession = generateSession();
		
		IamAuthenticator authenticator = new IamAuthenticator(APSAPIConstants.APIKey);
		Assistant assistant = new Assistant("2020-04-01", authenticator);
		assistant.setServiceUrl(APSAPIConstants.APIUrl);
		
		MessageInput input = new MessageInput.Builder()
		  .messageType("text")
		  .text(message.getMensagemEntrada())
		  .build();

		MessageOptions options = new MessageOptions.Builder(APSAPIConstants.idAssistant, watsonSession)
		  .input(input)
		  .build();

		MessageResponse response = assistant.message(options).execute().getResult();	
		
		message.setMensagemRetorno(trataMensagens(response));
		return message;
	}
	
	private String generateSession() {
		IamAuthenticator authenticator = new IamAuthenticator(APSAPIConstants.APIKey);
		Assistant assistant = new Assistant("2020-04-01", authenticator);
		assistant.setServiceUrl(APSAPIConstants.APIUrl);

		CreateSessionOptions sessionOptions = new CreateSessionOptions.Builder(APSAPIConstants.idAssistant).build();

		SessionResponse sessionResponse = assistant.createSession(sessionOptions).execute().getResult();
		
		return sessionResponse.getSessionId();
	}
	private ArrayList<String> trataMensagens(MessageResponse response) {
		ArrayList<String> respostas = new ArrayList<>();
		for (RuntimeResponseGeneric resp : response.getOutput().getGeneric()) {
			if(resp.text()!=null) {
				if(!resp.text().equals("")) {
					respostas.add(resp.text());
				}
			}
			if(resp.title()!=null && !resp.title().trim().equals("")) {
				respostas.add(resp.title());
				for (DialogNodeOutputOptionsElement dlg : resp.options()) {
					if(dlg.getLabel()!=null && !dlg.getLabel().trim().equals("")) {
						respostas.add(dlg.getLabel());
					}
				}
			}
			if(resp.topic()!=null && !resp.topic().trim().equals("")) {
				respostas.add(resp.topic());
			}
		
		}
		
		return respostas;
	}
}
