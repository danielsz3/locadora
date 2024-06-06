package com.unialfa.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame {
    private JButton btnFilme;
    private JButton btnDiretor;

    public MenuForm() {
        setTitle("Menu Principal");
        setSize(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout do Menu
        setLayout(new FlowLayout());

        btnFilme = new JButton("Filme");
        btnDiretor = new JButton("Diretor");

        btnFilme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmeForm filmeForm = new FilmeForm();
                filmeForm.setVisible(true);
                fechar();
            }
        });

        btnDiretor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DiretorForm diretorForm = new DiretorForm();
                diretorForm.setVisible(true);
                fechar();
            }
        });

        add(btnFilme);
        add(btnDiretor);
    }

    private void fechar() {
        this.dispose();
    }
}
