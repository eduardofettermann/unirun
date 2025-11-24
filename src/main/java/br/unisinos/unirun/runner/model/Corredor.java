package br.unisinos.unirun.runner.model;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.workout.model.Treino;
import br.unisinos.unirun.meal.model.Dieta;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Entity
public class Corredor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "assessoria_corrida_id")
    private AssessoriaCorrida assessoriaCorrida;

    @OneToMany(mappedBy = "corredor", cascade = CascadeType.ALL)
    private List<Treino> treinos = new ArrayList<>();

    @OneToMany(mappedBy = "corredor", cascade = CascadeType.ALL)
    private List<Dieta> dietas = new ArrayList<>();

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

    public AssessoriaCorrida getAssessoriaCorrida() {
        return assessoriaCorrida;
    }

    public void setAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida) {
        this.assessoriaCorrida = assessoriaCorrida;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<Treino> treinos) {
        this.treinos = treinos;
    }
}
