package com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double numeroDownload;

    public Livro() {}

    public Livro(DadosLivro dadosLivro){
        this.titulo = dadosLivro.titulo();
        // Lógica para pegar apenas o primeiro autor, já que a API retorna uma lista
        if (dadosLivro.autor() != null && !dadosLivro.autor().isEmpty()) {
            this.autor = new Autor(dadosLivro.autor().get(0));
        }
        // Lógica para pegar o primeiro idioma
        if (dadosLivro.idioma() != null && !dadosLivro.idioma().isEmpty()) {
            this.idioma = dadosLivro.idioma().get(0);
        }
        this.numeroDownload = dadosLivro.numeroDownload();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLivro() {
        return titulo;
    }

    public void setLivro(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Double numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    @Override
    public String toString() {
        return "livro: '" + titulo + '\'' +
                ", autor: " + autor +
                ", idioma: '" + idioma + '\'' +
                ", numeroDownload: " + numeroDownload;
    }
}
