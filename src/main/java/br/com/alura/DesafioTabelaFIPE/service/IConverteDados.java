package br.com.alura.DesafioTabelaFIPE.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
    <T> List<T> obterLista(String json, Class<T> classe);
}
