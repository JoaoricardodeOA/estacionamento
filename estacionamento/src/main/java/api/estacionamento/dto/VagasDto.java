package api.estacionamento.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasDto {
    @NotBlank
    private String numeroVaga;
    @NotBlank
    @Size(max = 7)
    private String placa;
    @NotBlank
    private String marcaCarro;
    @NotBlank
    private String modeloCarro;
    @NotBlank
    private String cor;
    @NotBlank
    private String responsavel;
    @NotBlank
    private String apartamento;
    @NotBlank
    private String bloco;
}
