package com.unialfa.dao;

import com.unialfa.model.Diretor;
import com.unialfa.model.Filme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiretorDao {
    private Connection connection;

    public DiretorDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void inserir(Diretor diretor) throws SQLException {
        String sql = "insert into diretor(nome, nacionalidade, premiacao, dt_inicio_carreira) values(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, diretor.getNome());
        ps.setString(2, diretor.getNacionalidade());
        ps.setInt(3, diretor.getPremiacao());
        ps.setDate(4, diretor.getDt_inicio_carreira());
        ps.execute();
    }

    public List<Diretor> listarTodosDiretores() throws SQLException {
        List<Diretor> diretores = new ArrayList<Diretor>();

        ResultSet rs = connection.prepareStatement("select * from diretor").executeQuery();
        while (rs.next()) {
            diretores.add(new Diretor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("nacionalidade"),
                    rs.getInt("premiacao"),
                    rs.getDate("dt_inicio_carreira")
            ));
        }
        rs.close();

        return diretores;
    }

    public void atualizar(Diretor diretor) throws SQLException {
        String sql = "update diretor set nome = ?, diretor = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, diretor.getNome());
        ps.setString(2, diretor.getNacionalidade());
        ps.setInt(3, diretor.getPremiacao());
        ps.setDate(4, diretor.getDt_inicio_carreira());
        ps.execute();
    }

    public void deletar(int id) throws SQLException {
        String sql = "delete from diretor where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

}
