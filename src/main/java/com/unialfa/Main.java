package com.unialfa;

import com.unialfa.view.DiretorForm;
import com.unialfa.view.FilmeForm;
import com.unialfa.view.MenuForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuForm menuForm = new MenuForm();
                menuForm.setVisible(true);
            }
        });
    }
}
