package api.estacionamento.repository;

import api.estacionamento.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsuarioModel,Long> {
    Optional<UsuarioModel> findByUsername(String username);
}
