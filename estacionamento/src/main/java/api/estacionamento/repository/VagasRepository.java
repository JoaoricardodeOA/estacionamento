package api.estacionamento.repository;

import api.estacionamento.model.VagasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VagasRepository extends JpaRepository <VagasModel, UUID>{
    boolean existsByPlaca(String placa);
    boolean existsByNumeroVaga(String numeroVaga);
    boolean existsByApartamentoAndBloco(String apartamento, String bloco);
}
