package br.unisinos.unirun.trainer.model;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.workout.model.Treino;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Treinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "assessoria_corrida_id")
    private AssessoriaCorrida assessoriaCorrida;

    @OneToMany(mappedBy = "treinador")
    private List<Treino> treinos;

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

    public void setAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida) {
        this.assessoriaCorrida = assessoriaCorrida;
    }

    public AssessoriaCorrida getAssessoriaCorrida() {
        return assessoriaCorrida;
    }
}
