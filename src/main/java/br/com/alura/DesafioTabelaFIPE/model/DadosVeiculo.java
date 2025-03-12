package br.com.alura.DesafioTabelaFIPE.model;

public record DadosVeiculo(String codigo, String nome) {
    @Override
    public String toString() {
        return "Nome: " + nome + ", Código: " + codigo;
    }
}
