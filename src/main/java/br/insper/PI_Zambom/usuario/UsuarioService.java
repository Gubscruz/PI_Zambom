package br.insper.PI_Zambom.usuario;

import br.insper.PI_Zambom.curso.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

	public ResponseEntity<RetornarUsuarioDTO> getUsuario(String cpf) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForEntity(
					 "http://184.72.80.215:8080/usuario/" + cpf,
					 RetornarUsuarioDTO.class);
	}
}