package br.com.alura.DesafioTabelaFIPE.principal;

import br.com.alura.DesafioTabelaFIPE.model.DadosModelos;
import br.com.alura.DesafioTabelaFIPE.model.DadosVeiculo;
import br.com.alura.DesafioTabelaFIPE.model.Veiculo;
import br.com.alura.DesafioTabelaFIPE.service.ConsumoApi;
import br.com.alura.DesafioTabelaFIPE.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu () {
        var menu = """
                *** Selecione um tipo de veiculo ***
                
                Carro
                Moto
                Caminhão
                
                *** Digite para selecionar ***
                """;
        System.out.println(menu);
        var opcao = sc.next();
        String endereco;

        if (opcao.contains("carr")) {
            endereco = URL_BASE + "carros/marcas/";
        } else if (opcao.contains("mot")) {
            endereco = URL_BASE + "motos/marcas/";
        } else {
            endereco = URL_BASE + "caminhoes/marcas/";
        }

        var json = consumo.obterDados(endereco);

        var veiculos = conversor.obterLista(json, DadosVeiculo.class);
        veiculos.stream()
                .sorted(Comparator.comparing(DadosVeiculo::nome))
                .forEach(System.out::println);

        System.out.print("Digite o código da marca para a pesquisa: ");
        var codigo = sc.next();

        endereco += codigo + "/modelos/";
        json  = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, DadosModelos.class);
        System.out.println("\nModelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::nome))
                .forEach(System.out::println);

        System.out.print("\nDigite o nome do modelo para a pesquisa: ");
        var nomeVeiculo = sc.next();
        List<DadosVeiculo> modelosFiltrados = modelosLista.modelos().stream()
                .filter(n -> n.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);
        System.out.print("Digite o código do modelo do carro: ");
        var codigoModelo = sc.next();

        endereco += codigoModelo + "/anos/";
        json = consumo.obterDados(endereco);
        List<DadosVeiculo> anos = conversor.obterLista(json, DadosVeiculo.class);
        List<Veiculo> listaVeiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            listaVeiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano");
        listaVeiculos.forEach(System.out::println);
    }
}
