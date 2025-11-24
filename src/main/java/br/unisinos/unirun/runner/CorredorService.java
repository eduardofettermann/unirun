package br.unisinos.unirun.runner;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.runner.model.Corredor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;

    public CorredorService(CorredorRepository corredorRepository) {
        this.corredorRepository = corredorRepository;
    }

    public Corredor save(Corredor corredor) {
        return corredorRepository.save(corredor);
    }

    public Optional<Corredor> findByNome(String nome) {
        return corredorRepository.findByNome(nome);
    }

    public Optional<Corredor> findById(Long id) {
        return corredorRepository.findById(id);
    }

    public List<Corredor> findByAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida) {
        return corredorRepository.findByAssessoriaCorrida(assessoriaCorrida);
    }
}
