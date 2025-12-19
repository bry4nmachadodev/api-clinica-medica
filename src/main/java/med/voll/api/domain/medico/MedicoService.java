package med.voll.api.domain.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    public Medico cadastrar(DadosCadastroMedico dados){
        var medico = new Medico(dados);
        repository.save(medico);
        return medico;
    }

    public Page<DadosListagemMedico> listarMedicosAtivos(Pageable pageable){
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }
}
