//Jefferson Andrey Dias Cardoso - Ra: 24017498

package com.fut.fut360.service;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Contract;
import com.fut.fut360.Repository.AtletaRepository;
import com.fut.fut360.Repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository repository;
    private final AtletaRepository atletaRepository;

    @Autowired
    public ContractService(ContractRepository repository, AtletaRepository atletaRepository) {
        this.repository = repository;
        this.atletaRepository = atletaRepository;
    }

    public List<Contract> findAll() {
        return repository.findAll();
    }

    public Contract save(Contract contract) {
        // 1. Validação de nome vazio
        if (contract.getName() == null || contract.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do atleta é obrigatório.");
        }

        // 2. BUSCA O ATLETA NO BANCO
        Optional<Atleta> atletaExistente = atletaRepository.findByName(contract.getName());

        if (atletaExistente.isPresent()) {
            Atleta atletaReal = atletaExistente.get();

            // --- NOVA VALIDAÇÃO DE IDADE (O PULO DO GATO) ---
            // Se o usuário digitou uma idade, verificamos se bate com a do banco.
            if (contract.getAge() != null) {
                int idadeNoBanco = atletaReal.getAge(); // Pega a idade calculada pelo Java
                int idadeDigitada = contract.getAge();

                if (idadeNoBanco != idadeDigitada) {
                    throw new IllegalArgumentException(
                            "Erro de Dados: O atleta '" + atletaReal.getName() +
                                    "' tem " + idadeNoBanco + " anos cadastrados, mas você informou " + idadeDigitada +
                                    ". Corrija o formulário."
                    );
                }
            }

            // --- VALIDAÇÃO DE POSIÇÃO (OPCIONAL, MAS RECOMENDADO) ---
            // Garante que não cadastrem um "Goleiro" como "Atacante" sem querer
            if (contract.getPosition() != null && !contract.getPosition().equalsIgnoreCase(atletaReal.getPosicao())) {
                throw new IllegalArgumentException(
                        "Divergência: O atleta é '" + atletaReal.getPosicao() +
                                "', mas o contrato informa '" + contract.getPosition() + "'."
                );
            }

            // Se passou, vincula o contrato ao atleta certo
            contract.setAtleta(atletaReal);

        } else {
            // Se não achou o atleta
            throw new IllegalArgumentException("Erro: O atleta '" + contract.getName() + "' não existe no banco. Cadastre-o na aba Atletas primeiro.");
        }

        return repository.save(contract);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}