package com.unialfa.model;

import java.sql.Date;

public class Diretor {
    private Integer id;
    private String nome;
    private String nacionalidade;
    private Integer premiacao;
    private Date dt_inicio_carreira;

    public Diretor (String nome, String nacionalidade, Integer premiacao, Date dt_inicio_carreira) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.premiacao = premiacao;
        this.dt_inicio_carreira = dt_inicio_carreira;
    }

    public Diretor(Integer id, String nome, String nacionalidade, Integer premiacao, Date dt_inicio_carreira ) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.premiacao = premiacao;
        this.dt_inicio_carreira = dt_inicio_carreira;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(Integer premiacao) {
        this.premiacao = premiacao;
    }

    public Date getDt_inicio_carreira() {
        return dt_inicio_carreira;
    }

    public void setDt_inicio_carreira(Date dt_inicio_carreira) {
        this.dt_inicio_carreira = dt_inicio_carreira;
    }

    @Override
    public String toString() {
        return "Diretor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", premiacao=" + premiacao +
                ", dt_inicio_carreira=" + dt_inicio_carreira +
                '}';
    }
}
