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

        log.info("Iniciado o processamento de gravar transações");


        if (dto.dataHora().isAfter(OffsetDateTime.now())){
            log.error("A data e hora meiores que a data e hora atuais");
            throw new UnprocessableEntity("Data e hora meiores que a data e hora atuais");
        }


        if(dto.valor() < 0){
            log.error("O valor não pode ser menor que zero");
            throw new UnprocessableEntity("O valor não pode ser menor que zero");
        }

        listaTransacoes.add(dto);

    }

    public void limparTransacoes(){

        log.info("Iniciado o processo para alimpar dados");

        listaTransacoes.clear();
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca){

        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        return listaTransacoes.stream()
                .filter(transacao -> transacao.dataHora()
                .isAfter(dataHoraIntervalo)).toList();
    }



}
