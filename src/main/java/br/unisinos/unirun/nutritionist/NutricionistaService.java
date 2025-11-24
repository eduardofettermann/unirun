package br.unisinos.unirun.nutritionist;

import br.unisinos.unirun.nutritionist.model.Nutricionista;
import br.unisinos.unirun.runner.CorredorRepository;
import br.unisinos.unirun.runner.model.Corredor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutricionistaService {
    private final NutricionistaRepository nutricionistaRepository;
    private final CorredorRepository corredorRepository;

    public NutricionistaService(NutricionistaRepository nutricionistaRepository, CorredorRepository corredorRepository) {
        this.nutricionistaRepository = nutricionistaRepository;
        this.corredorRepository = corredorRepository;
    }

    public Nutricionista save(Nutricionista nutricionista) {
        return nutricionistaRepository.save(nutricionista);
    }

    public Optional<Nutricionista> findByNome(String nome) {
        return nutricionistaRepository.findByNome(nome);
    }

    public Optional<Nutricionista> findById(Long id) {
        return nutricionistaRepository.findById(id);
    }

    public List<Nutricionista> findByAssessoriaCorrida(br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida assessoriaCorrida) {
        return nutricionistaRepository.findByAssessoriaCorrida(assessoriaCorrida);
    }

    public List<Corredor> findStudents(Nutricionista nutricionista) {
        if (nutricionista == null || nutricionista.getAssessoriaCorrida() == null) {
            return List.of();
        }
        return corredorRepository.findByAssessoriaCorrida(nutricionista.getAssessoriaCorrida());
    }
}
