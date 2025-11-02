package com.poo.orion.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poo.orion.Model.Cupom;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CupomDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idCupom;

    private String codigo;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private float percentualDesconto;

    private boolean ativo;

    @NotNull
    private Date validadeInicio;

    @NotNull
    private Date validadeFinal;

    public CupomDTO() {}

    public CupomDTO(Long idCupom, String codigo, float percentualDesconto,
                    boolean ativo, Date validadeInicio, Date validadeFinal) {
        this.idCupom = idCupom;
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.ativo = ativo;
        this.validadeInicio = validadeInicio;
        this.validadeFinal = validadeFinal;
    }

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