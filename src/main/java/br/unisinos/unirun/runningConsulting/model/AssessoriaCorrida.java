package br.unisinos.unirun.runningConsulting.model;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.nutritionist.model.Nutricionista;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class AssessoriaCorrida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "assessoriaCorrida")
    private List<Corredor> corredores;

    @OneToMany(mappedBy = "assessoriaCorrida")
    private List<Treinador> treinadores;

    @OneToMany(mappedBy = "assessoriaCorrida")
    private List<Nutricionista> nutricionistas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
