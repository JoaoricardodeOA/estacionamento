package api.estacionamento.controller;

import api.estacionamento.dto.VagasDto;
import api.estacionamento.model.VagasModel;
import api.estacionamento.service.VagasService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/vagas")
public class VagasController {
    @Autowired
    VagasService vagasService;
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid VagasDto vagasDto){
        if(vagasService.existsByPlaca(vagasDto.getPlaca())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito de placa já em uso");
        }if(vagasService.existsByNumeroVaga(vagasDto.getNumeroVaga())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito de vaga já em uso");
        }if(vagasService.existsByApartamentoAndBloco(vagasDto.getApartamento(), vagasDto.getBloco())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito de apartamento e bloco já em uso");
        }
        VagasModel vagasModel = new VagasModel();
        BeanUtils.copyProperties(vagasDto,vagasModel);
        vagasModel.setEmissao(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagasService.save(vagasModel));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<VagasModel>> getTodasVagas(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vagasService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getVaga(@PathVariable(value = "id")UUID id){
        Optional<VagasModel> optionalVagas = vagasService.findById(id);
        if(!optionalVagas.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalVagas.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVaga(@PathVariable(value = "id")UUID id){
        Optional<VagasModel> optionalVagas = vagasService.findById(id);
        if(!optionalVagas.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }
        vagasService.delete(optionalVagas.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVaga(@PathVariable(value = "id")UUID id,
                                             @RequestBody @Valid VagasDto vagasDto){
        Optional<VagasModel> optionalVagas = vagasService.findById(id);
        if(!optionalVagas.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }
        VagasModel vagasModelInsert = new VagasModel();
        BeanUtils.copyProperties(vagasDto,vagasModelInsert);

        VagasModel vagasModel = optionalVagas.get();

        vagasModelInsert.setEmissao(vagasModel.getEmissao());
        vagasModelInsert.setId(vagasModel.getId());

        return ResponseEntity.status(HttpStatus.OK).body(vagasService.save(vagasModelInsert));
    }
}
