package br.com.alura.DesafioTabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosModelos(List<DadosVeiculo> modelos) {
}
