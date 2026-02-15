package com.graeff.transacao_api.business.services;

import com.graeff.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.graeff.transacao_api.infrastructure.execeptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionarTransacoes(TransacaoRequestDTO dto){

        log.info("Iniciado o processamento de gravar transações " + dto);


        if (dto.dataHora().isAfter(OffsetDateTime.now())){
            log.error("A data e hora meiores que a data e hora atuais");
            throw new UnprocessableEntity("Data e hora meiores que a data e hora atuais");
        }


        if(dto.valor() < 0){
            log.error("O valor não pode ser menor que zero");
            throw new UnprocessableEntity("O valor não pode ser menor que zero");
        }

        listaTransacoes.add(dto);
        log.info("Transações foram adicionadas com sucesso");
    }

    public void limparTransacoes(){

        log.info("Iniciado o processo para limpar transações");

        listaTransacoes.clear();
        log.info("Transações foram limpas com sucesso");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca){
        log.info("Iniciando as buscas de transações pelo tempo " + intervaloBusca);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        log.info("Retorno de transações foi feita com sucesso");
        return listaTransacoes.stream()
                .filter(transacao -> transacao.dataHora()
                .isAfter(dataHoraIntervalo)).toList();
    }
}
