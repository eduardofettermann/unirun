package br.unisinos.unirun.runningConsulting;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssessoriaCorridaService {
    private final AssessoriaCorridaRepository assessoriaCorridaRepository;

    public AssessoriaCorridaService(AssessoriaCorridaRepository assessoriaCorridaRepository) {
        this.assessoriaCorridaRepository = assessoriaCorridaRepository;
    }

    public AssessoriaCorrida save(AssessoriaCorrida assessoriaCorrida) {
        return assessoriaCorridaRepository.save(assessoriaCorrida);
    }

    public Optional<AssessoriaCorrida> findByNome(String nome) {
        return assessoriaCorridaRepository.findByNome(nome);
    }

    public Optional<AssessoriaCorrida> findById(Long id) {
        return assessoriaCorridaRepository.findById(id);
    }
}
