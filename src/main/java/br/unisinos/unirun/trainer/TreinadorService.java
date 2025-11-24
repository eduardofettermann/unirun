package br.unisinos.unirun.trainer;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runner.CorredorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreinadorService {
    private final TreinadorRepository treinadorRepository;
    private final CorredorRepository corredorRepository;

    public TreinadorService(TreinadorRepository treinadorRepository, CorredorRepository corredorRepository) {
        this.treinadorRepository = treinadorRepository;
        this.corredorRepository = corredorRepository;
    }

    public Treinador save(Treinador treinador) {
        return treinadorRepository.save(treinador);
    }

    public Optional<Treinador> findByNome(String nome) {
        return treinadorRepository.findByNome(nome);
    }

    public Optional<Treinador> findById(Long id) {
        return treinadorRepository.findById(id);
    }

    public List<Treinador> findByAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida) {
        return treinadorRepository.findByAssessoriaCorrida(assessoriaCorrida);
    }

    public List<Corredor> findStudents(Treinador treinador) {
        if (treinador == null || treinador.getAssessoriaCorrida() == null) {
            return List.of();
        }
        return corredorRepository.findByAssessoriaCorrida(treinador.getAssessoriaCorrida());
    }
}
