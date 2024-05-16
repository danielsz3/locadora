package com.unialfa.service;

import com.unialfa.dao.FilmeDao;
import com.unialfa.model.Filme;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FilmeService {

/*    private static final String NOME_DIRETORIO = System.getProperty("user.dir");
    private static final String NOME_ARQUIVO = "\\Locadoras.txt";

    private final File locadoraFile = new File(NOME_DIRETORIO, NOME_ARQUIVO); */

    public void salvar(Filme filme) {
        try {
            var dao = new FilmeDao();

            if (filme.getId() == null) {
                dao.inserir(filme);

            } else {
                dao.atualizar(filme);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Filme> listarFilmes() {
        try {
            var dao = new FilmeDao();
            return dao.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
