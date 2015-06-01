package Gestionnaire;

import Gestionnaire.Controller.Controller;
import Gestionnaire.Model.Videotheque;
import Gestionnaire.View.VueGestionnaire;

import javax.swing.*;
import java.awt.*;


/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe de lancement du projet
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Démarrage du gestionnaire de films.\n\n");

        String fichier = "films.txt";

        System.out.println("Fichier source = "+fichier+"\n\n");


        Videotheque modele = new Videotheque(fichier);


        VueGestionnaire vue = new VueGestionnaire();
        Controller controller = new Controller(vue, modele);

        vue.ajouterListener(controller);

        JFrame fenetre = new JFrame("Gestionnaire de films");
        fenetre.setPreferredSize(new Dimension(1000,800));
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container contentPane = fenetre.getContentPane();

        contentPane.add(vue);
        fenetre.setVisible(true);
    }

}
