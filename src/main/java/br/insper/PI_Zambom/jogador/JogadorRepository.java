package br.insper.PI_Zambom.jogador;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends MongoRepository<Jogador, String> {
}
