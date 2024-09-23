package br.insper.PI_Zambom.time;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimeService {

	public ResponseEntity<RetornarTimeDTO> getTime(Integer idTime) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForEntity(
					 "http://3.81.8.254:8080/time/" + idTime,
					 RetornarTimeDTO.class);
	}
}