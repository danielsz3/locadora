package com.unialfa.service;

import com.unialfa.dao.FilmeDao;
import com.unialfa.model.Filme;

import java.util.Collections;
import java.util.List;

public class FilmeService {

    public void salvar(Filme filme) {
        try {
            var dao = new FilmeDao();
            if (filme.getId() == null) {
                dao.inserir(filme);
            }else {
                dao.atualizar(filme);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deletar (int id) {
        try {
            var dao = new FilmeDao();
            if (id > -1) {
                dao.deletar(id);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

     public List<Filme> listarFilmes() {
        try {
            var dao = new FilmeDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
