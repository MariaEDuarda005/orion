package com.poo.orion.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poo.orion.Model.Cupom;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

public record CupomDTO(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long idCupom,

        String codigo,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        float percentualDesconto,

        boolean ativo,

        @NotNull
        Date validadeInicio,

        @NotNull
        Date validadeFinal
) {
    // Converte uma entidade Cupom para DTO
    public static CupomDTO from(Cupom c) {
        return new CupomDTO(
                c.getIdCupom(),
                c.getCodigo(),
                c.getPercentualDesconto(),
                c.isAtivo(),
                c.getValidadeInicio(),
                c.getValidadeFinal()
        );
    }

    // Converte DTO para entidade Cupom
    public Cupom toEntity() {
        Cupom c = new Cupom();
        c.setIdCupom(this.idCupom);
        c.setCodigo(this.codigo);
        c.setPercentualDesconto(this.percentualDesconto);
        c.setAtivo(this.ativo);
        c.setValidadeInicio(this.validadeInicio);
        c.setValidadeFinal(this.validadeFinal);
        return c;
    }
}