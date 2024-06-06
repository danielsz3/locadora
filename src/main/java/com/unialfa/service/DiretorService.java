package com.unialfa.service;

import com.unialfa.dao.DiretorDao;
import com.unialfa.model.Diretor;

import java.util.Collections;
import java.util.List;

public class DiretorService {

    public void salvar(Diretor diretor) {
        try {
            var dao = new DiretorDao();
            if (diretor.getId() == null) {
                dao.inserir(diretor);
            }else {
                dao.atualizar(diretor);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            var dao = new DiretorDao();
            if (id > 0) {
                dao.deletar(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Diretor> listarDiretor() {
        try {
            var dao = new DiretorDao();
            return dao.listarTodosDiretores();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
