package com.poo.orion.Service;

import com.poo.orion.DTO.CupomDTO;
import com.poo.orion.Model.Cupom;
import com.poo.orion.Model.Produto;
import com.poo.orion.Repository.CupomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CupomService {

    private final CupomRepository repository;

    public CupomService(CupomRepository repository) {
        this.repository = repository;
    }

    public List<CupomDTO> getAllCupons() {
        return repository.findAll()
                .stream()
                .map(CupomDTO::from)
                .collect(Collectors.toList());
    }

    public CupomDTO getCupomById(Long id) {
        Cupom cupom = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
        return CupomDTO.from(cupom);
    }

    public CupomDTO createCupom(CupomDTO dto) {
        Optional<Cupom> existente = repository.findByCodigoIgnoreCase(dto.codigo());
        if (existente.isPresent()) {
            throw new RuntimeException("Já existe um cupom com esse código.");
        }

        Cupom cupom = dto.toEntity();
        Cupom salvo = repository.save(cupom);
        return CupomDTO.from(salvo);
    }

    public CupomDTO putCupom(Long id, CupomDTO dto) {
        Cupom cupom = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        Optional<Cupom> cupomExistente = repository.findByCodigoIgnoreCase(dto.codigo());
        if (cupomExistente.isPresent() && !cupomExistente.get().getIdCupom().equals(id)) {
            throw new RuntimeException("Já existe um cupom com esse código.");
        }

        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setAtivo(dto.ativo());
        cupom.setValidadeInicio(dto.validadeInicio());
        cupom.setValidadeFinal(dto.validadeFinal());

        Cupom atualizado = repository.save(cupom);
        return CupomDTO.from(atualizado);
    }

    public void deleteCupom(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cupom não encontrado");
        }
        repository.deleteById(id);
    }

    public void deleteAllCupons() {
        repository.deleteAll();
    }

    public List<CupomDTO> getCuponsAtivosValidos() {
        return repository.findByAtivoTrue()
                .stream()
                .map(CupomDTO::from)
                .collect(Collectors.toList());
    }
}
