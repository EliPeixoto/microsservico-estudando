package br.com.fiap.mscidade.controller;

import br.com.fiap.mscidade.model.Cidade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController()
public class CidadeController {

    private List<Cidade> cidade = new ArrayList<>(Arrays.asList(
            new Cidade(1, "Campinas", "Brasil", "Campinas é perto de SP"),
            new Cidade(2,"Florianopolis", "Brasil", "Floripa tem lindas praias" ),
            new Cidade(3, "Porto Alegre", "Brasil", "Porto Alegre é muito fria no inverno")
    ));


    @GetMapping("/cidades")
    public List<Cidade>listarCidades(){
        return cidade;
    }

    @GetMapping("/cidade/{id}")
    public Cidade getCidade(@PathVariable int id){
        return cidade.stream().filter(cidade-> cidade.getId()==id)
                .findFirst()
                .orElse(null);
    }
    @DeleteMapping("/cidade/{id}")
    public ResponseEntity<String> deletarCidade(@PathVariable int id) {
        Optional<Cidade> cidadeEncontrada = cidade.stream()
                .filter(cidade -> cidade.getId() == id)
                .findFirst();

        if (cidadeEncontrada.isPresent()) {
            cidade.remove(cidadeEncontrada.get());
            return ResponseEntity.ok("Cidade deletada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cidade não encontrada.");
        }
    }
}
