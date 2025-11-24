package br.unisinos.unirun.nutritionist.model;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.meal.model.Dieta;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Nutricionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "assessoria_corrida_id")
    private AssessoriaCorrida assessoriaCorrida;

    @OneToMany(mappedBy = "nutricionista")
    private List<Dieta> dietas;

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
