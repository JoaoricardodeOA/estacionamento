package api.estacionamento.service;

import api.estacionamento.model.VagasModel;
import api.estacionamento.repository.VagasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VagasService {
    @Autowired
    VagasRepository vagasRepository;
    @Transactional
    public VagasModel save(VagasModel vagasModel) {
        return vagasRepository.save(vagasModel);
    }

    public boolean existsByPlaca(String placa) {
        return vagasRepository.existsByPlaca(placa);
    }

    public boolean existsByNumeroVaga(String numeroVaga) {
        return vagasRepository.existsByNumeroVaga(numeroVaga);
    }

    public boolean existsByApartamentoAndBloco(String apartamento, String bloco) {
        return vagasRepository.existsByApartamentoAndBloco(apartamento, bloco);
    }

    public Page<VagasModel> findAll(Pageable pageable) {
        return vagasRepository.findAll(pageable);
    }

    public Optional<VagasModel> findById(UUID id) {
        return vagasRepository.findById(id);
    }
    @Transactional
    public void delete(VagasModel vagasModel) {
        vagasRepository.delete(vagasModel);
    }
}
