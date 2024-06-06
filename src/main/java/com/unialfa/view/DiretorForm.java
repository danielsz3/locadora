package com.unialfa.view;

import com.unialfa.model.Diretor;
import com.unialfa.service.DiretorService;

import javax.swing.text.MaskFormatter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class DiretorForm extends JFrame {

    private DiretorService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeDiretor;
    private JTextField campoNomeDiretor;
    private JLabel labelNacDiretor;
    private JTextField campoNacDiretor;
    private JLabel labelPreDiretor;
    private JTextField campoPreDiretor;
    private JLabel labelCarDiretor;
    private JFormattedTextField campoCarDiretor;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JButton botaoVoltar;
    private JTable tabela;

    public DiretorForm() {
        service = new DiretorService();

        setTitle("Diretor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private JPanel montarPainelSaida() {
        JPanel painelSaida = new JPanel(new BorderLayout());

        tabela = new JTable();
        tabela.setDefaultEditor(Object.class, null);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setModel(carregarDadosDiretores());
        tabela.getSelectionModel().addListSelectionListener(this::selecionarDiretor);

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

        labelNomeDiretor = new JLabel("Nome do Diretor:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeDiretor, constraints);

        campoNomeDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeDiretor, constraints);

        labelNacDiretor = new JLabel("Nacionalidade do Diretor:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelNacDiretor, constraints);

        campoNacDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoNacDiretor, constraints);

        labelPreDiretor = new JLabel("Quantidade de prêmios:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(labelPreDiretor, constraints);

        campoPreDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(campoPreDiretor, constraints);

        labelCarDiretor = new JLabel("Data do início da carreira:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(labelCarDiretor, constraints);

        try {
            MaskFormatter dateMask = new MaskFormatter("##-##-####");
            dateMask.setPlaceholderCharacter('_');
            campoCarDiretor = new JFormattedTextField(dateMask);
            campoCarDiretor.setColumns(20);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(campoCarDiretor, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> salvar());
        constraints.gridx = 0;
        constraints.gridy = 5;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 5;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletar());
        constraints.gridx = 2;
        constraints.gridy = 5;
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


    private DefaultTableModel carregarDadosDiretores() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Nacionalidade");
        model.addColumn("Premiação");
        model.addColumn("Data do Início da Carreira");

        service.listarDiretor().forEach(diretor -> model.addRow(
                new Object[]{
                        diretor.getId(),
                        diretor.getNome(),
                        diretor.getNacionalidade(),
                        diretor.getPremiacao(),
                        diretor.getDt_inicio_carreira()
                }));

        return model;
    }

    private void salvar() {
        try {
            service.salvar(construirDiretor());
            limparCampos();
            tabela.setModel(carregarDadosDiretores());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o diretor: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        service.deletar(parseInt(campoId.getText()));
        try {
            int id = parseInt(campoId.getText());
            limparCampos();
            tabela.setModel(carregarDadosDiretores());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar o diretor: ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNomeDiretor.setText("");
        campoNacDiretor.setText("");
        campoPreDiretor.setText("");
        campoCarDiretor.setText("");
        campoId.setText("");
    }

    private Diretor construirDiretor() {
        String nomeDiretor = campoNomeDiretor.getText();
        String nacionalidade = campoNacDiretor.getText();
        String premiacao = campoPreDiretor.getText();
        String dataInicioCarreira = campoCarDiretor.getText();

        if (!nomeDiretor.matches("[A-Za-z\\s]+")) {
            throw new RuntimeException("O nome do diretor não pode conter números ou caracteres especiais.");
        }

        if (!nacionalidade.matches("[A-Za-z\\s]+")) {
            throw new RuntimeException("A nacionalidade não pode conter números ou caracteres especiais.");
        }

        try {
            Integer.parseInt(premiacao);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Informe um número, valor mínimo: 0");
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setLenient(false);
            Date parsedDate = new Date(format.parse(dataInicioCarreira).getTime());
            return campoId.getText().isEmpty()
                    ? new Diretor(nomeDiretor, nacionalidade, Integer.parseInt(premiacao), parsedDate)
                    : new Diretor(
                    parseInt(campoId.getText()),
                    nomeDiretor,
                    nacionalidade,
                    Integer.parseInt(premiacao),
                    parsedDate
            );
        } catch (ParseException ex) {
            throw new RuntimeException("Formato de data inválido. Use o formato dd-MM-yyyy.");
        }
    }

    private void selecionarDiretor(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Integer) tabela.getValueAt(selectedRow, 0);
                var nome = (String) tabela.getValueAt(selectedRow, 1);
                var nacionalidade = (String) tabela.getValueAt(selectedRow, 2);
                var premiacao = (Integer) tabela.getValueAt(selectedRow, 3);
                var dataInicioCarreira = (Date) tabela.getValueAt(selectedRow, 4);

                campoId.setText(id.toString());
                campoNomeDiretor.setText(nome);
                campoNacDiretor.setText(nacionalidade);
                campoPreDiretor.setText(premiacao.toString());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dataFormatada = dateFormat.format(dataInicioCarreira);
                campoCarDiretor.setText(dataFormatada);
            }
        }
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new Date(format.parse(date).getTime());
    }

    private void menuPrincipal() {
        dispose(); // Fecha a janela atual (DiretorForm)
        MenuForm menu = new MenuForm(); // Cria uma nova instância do MenuForm
        menu.setVisible(true); // Torna o MenuForm visível
    }

}
