package br.unisinos.unirun.workout.model;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.trainer.model.Treinador;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    private boolean concluido;

    @ManyToOne
    @JoinColumn(name = "corredor_id")
    private Corredor corredor;

    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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


    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }
}
