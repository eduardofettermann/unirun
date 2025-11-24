package br.unisinos.unirun.meal.model;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.nutritionist.model.Nutricionista;
import jakarta.persistence.*;

@Entity
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date data;

    private boolean concluido;

    @ManyToOne
    @JoinColumn(name = "corredor_id")
    private Corredor corredor;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public java.util.Date getData() {
        return data;
    }

    public void setData(java.util.Date data) {
        this.data = data;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }


    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }
}
