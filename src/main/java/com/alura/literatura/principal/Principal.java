package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LivroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConverteDados;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private String json;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n======== LIVRARIA =======
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                    
                    0 - Sair
                    =========================
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();

        // 1. Chamada encapsulada retornando um Optional (igual ao segundo código)
        Optional<DadosLivro> dadosLivroOptional = buscarDadosNaApi(nomeLivro);

        if (dadosLivroOptional.isPresent()) {
            DadosLivro dadosLivro = dadosLivroOptional.get();
            Livro livro = new Livro(dadosLivro);

            // 2. Tratamento do Autor antes de salvar o livro
            // Assumindo que 'DadosLivro' traz uma lista de autores (padrão Gutendex)
            if (dadosLivro.autor() != null && !dadosLivro.autor().isEmpty()) {
                DadosAutor dadosAutor = dadosLivro.autor().get(0);

                // Usamos a instância injetada 'autorRepository', não a classe estática
                Optional<Autor> autorExistente = autorRepository.findByNome(dadosAutor.nome());

                if (autorExistente.isPresent()) {
                    livro.setAutor(autorExistente.get());
                } else {
                    // Se não existe, cria e salva o autor primeiro
                    Autor novoAutor = new Autor(dadosAutor);
                    autorRepository.save(novoAutor);
                    livro.setAutor(novoAutor);
                }
            }

            try {
                livroRepository.save(livro);
                System.out.println("Livro salvo com sucesso: " + livro);
            } catch (Exception e) {
                System.out.println("Erro ao salvar o livro (provavelmente já cadastrado): " + e.getMessage());
            }

        } else {
            System.out.println("Livro não encontrado.");
        }
    }
    private Optional<DadosLivro> buscarDadosNaApi(String nomeLivro) {
        var json = consumoAPI.obterDados(URL_BASE + nomeLivro.replace(" ", "+"));
        Resultados dados = conversor.obterDados(json, Resultados.class);

        if (dados.listaDeDadosLivro() != null && !dados.listaDeDadosLivro().isEmpty()) {
            return Optional.of(dados.listaDeDadosLivro().get(0));
        }

        return Optional.empty();
    }

    private void listarLivrosRegistrados(){
        var livros = livroRepository.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getLivro))
                .forEach(System.out::println);
    }
    private void listarAutores(){
        var autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNome());
                    System.out.println("Ano de nascimento: " + a.getDataNascimento());
                    System.out.println("Ano de falecimento: " + a.getDataFalecimento());

                    var listarLivros = a.getLivros().stream()
                            .map(Livro::getLivro)
                            .collect(Collectors.toList());
                            System.out.println("Livros: " + listarLivros + "\n");
                });
    }
    private void listarAutoresVivos(){
        System.out.println("Digite o ano que deseja: ");
        if (leitura.hasNextInt()) {
            var ano = leitura.nextInt();
            leitura.nextLine();

            var autores = autorRepository.autoresVivosNoAno(ano);
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo neste ano.");
            } else {
                autores.forEach(System.out::println);
            }
        } else {
            System.out.println("Ano invalido.");
            leitura.next();
        }
    }
    private void listarLivrosPorIdioma(){
        var menuIdiomas = """
                Insira o idioma para realizar a busca:
                es - espanhol
                en - ingles
                fr - frances
                pt - portugues
                """;
        System.out.println(menuIdiomas);
        var idioma = leitura.nextLine();

        var livro = livroRepository.findByIdioma(idioma);
        if (livro.isEmpty()){
            System.out.println("Não existe livros nesse idioma!");
        }else{
            livro.forEach(System.out::println);
        }
    }
}