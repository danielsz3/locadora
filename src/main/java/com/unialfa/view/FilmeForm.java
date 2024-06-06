package com.unialfa.view;

import com.unialfa.model.Filme;
import com.unialfa.service.FilmeService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class FilmeForm extends JFrame {

    private FilmeService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeFilme;
    private JTextField campoNomeFilme;
    private JLabel labelDiretor;
    private JTextField campoDiretor;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;


    public FilmeForm() {
        service = new FilmeService();

        setTitle("Filme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);

        //pack();
        setLocationRelativeTo(null);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDadosLocadoras());
        tabela.getSelectionModel().addListSelectionListener(this::selecionarFilme);

        JScrollPane scrollPane = new JScrollPane(tabela);

        painelSaida.add(scrollPane, BorderLayout.CENTER);

        return painelSaida;
    }

    private JPanel montarPainelEntrada() {
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelId = new JLabel("ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(20);
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        labelNomeFilme = new JLabel("Nome do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeFilme, constraints);

        campoNomeFilme = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeFilme, constraints);

        labelDiretor = new JLabel("Diretor do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDiretor, constraints);

        campoDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoDiretor, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> salvar());
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletar());
        constraints.gridx = 2;
        constraints.gridy = 3;
        painelEntrada.add(botaoDeletar, constraints);

        botaoVoltar = new JButton("Menu Principal");
        botaoVoltar.addActionListener(e -> menuPrincipal());
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        painelEntrada.add(botaoVoltar, constraints);

        return painelEntrada;
    }

    private DefaultTableModel carregarDadosLocadoras() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Diretor");

        service.listarFilmes().forEach(filme -> model.addRow(
                new Object[]{
                        filme.getId(),
                        filme.getNome(),
                        filme.getDiretor()}));

        return model;
    }

    private void salvar() {
        try {
            service.salvar(construirFilme());
            limparCampos();
            tabela.setModel(carregarDadosLocadoras());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar o filme: "
                            +
                            ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        service.deletar(Integer.parseInt(campoId.getText()));
        limparCampos();
        tabela.setModel(carregarDadosLocadoras());
    }

    private void limparCampos() {
        campoNomeFilme.setText("");
        campoDiretor.setText("");
        campoId.setText("");
    }

    private Filme construirFilme() {
        String nomeFilme = campoNomeFilme.getText();
        String diretor = campoDiretor.getText();

        if (!nomeFilme.matches("[A-Za-z\\s]+")) {
            throw new RuntimeException("O nome do filme não pode conter números ou símbolos.");
        }

        if (!diretor.matches("[A-Za-z\\s]+")) {
            throw new RuntimeException("O nome do diretor não pode conter números ou símbolos.");
        }

        try {
            return campoId.getText().isEmpty()
                    ? new Filme(nomeFilme, diretor)
                    : new Filme(
                    parseInt(campoId.getText()),
                    nomeFilme,
                    diretor);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Quantidade de prêmios deve ser um número inteiro.");
        }
    }

    private void selecionarFilme(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var nome = (String) tabela.getValueAt(selectedRow, 1);
                var diretor = (String) tabela.getValueAt(selectedRow, 2);

                campoId.setText(id.toString());
                campoNomeFilme.setText(nome);
                campoDiretor.setText(diretor);
            }
        }

    }

    private void menuPrincipal() {
        dispose();
        MenuForm menu = new MenuForm();
        menu.setVisible(true);
    }

}
