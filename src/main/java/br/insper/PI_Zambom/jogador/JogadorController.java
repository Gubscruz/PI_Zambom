package br.insper.PI_Zambom.jogador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogador")
public class JogadorController {


    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public List<Jogador> listar() {
        return jogadorService.listar();
    }

    @PostMapping
    public Jogador salvar(@RequestBody Jogador aposta) {
        return jogadorService.salvar(aposta);
    }

    @PostMapping("/{idTime}")
    public Jogador salvarComTime(@RequestBody Jogador aposta, @PathVariable Integer idTime) {
        return jogadorService.addTime(aposta, idTime);
    }
}