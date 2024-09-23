package br.insper.PI_Zambom.jogador;

import br.insper.PI_Zambom.time.RetornarTimeDTO;
import br.insper.PI_Zambom.time.TimeNaoEncontrandoException;
import br.insper.PI_Zambom.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

	@Autowired
	private JogadorRepository jogadorRepository;

	@Autowired
	private TimeService timeService;


	public Jogador salvar(Jogador jogador){
		//TODO: Checagens
		return jogadorRepository.save(jogador);
	}

	public List<Jogador> listar(){
		return jogadorRepository.findAll();
	}


	public Jogador addTime(Jogador jogador, Integer id){
		//TODO: Checagens2
		Optional<Jogador> op = jogadorRepository.findById(jogador.getId());
		if (op.isEmpty()) {
			throw new RuntimeException("Jogador não encontrado");
		}
		Jogador jogadorA = op.get();
		ResponseEntity<RetornarTimeDTO> time = timeService.getTime(id);
		if(time.getStatusCode().is2xxSuccessful()){
			RetornarTimeDTO timeDTO = time.getBody();
			ArrayList<String> lista = jogadorA.getTimes();
			lista.add(timeDTO.getIdentificador());
			jogadorA.setTimes(lista);
			return jogadorRepository.save(jogadorA);

		}
		else {
			throw new TimeNaoEncontrandoException("Time não encontrado");
		}


	}

	public void deletar(String id) {
		Optional<Jogador> jogador = jogadorRepository.findById(id);
		if (jogador.isPresent()) {
			jogadorRepository.deleteById(id);
		} else {
			throw new RuntimeException("Jogador não encontrado");
		}
	}


}