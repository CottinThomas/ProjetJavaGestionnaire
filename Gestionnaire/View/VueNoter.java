package Gestionnaire.View;

import Gestionnaire.Controller.Controller;
import Gestionnaire.Model.Note;

import javax.swing.*;
import java.awt.*;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe de cr�ation de l'interface d'ajout de note
 */
public class VueNoter extends JDialog{

    //###########################
    //###### ATTRIBUTS
    //###########################

    private JFormattedTextField note;
    private JButton validerNote;
    private JLabel erreur;
    private int index;



    //###########################
    //###### CONSTRUCTEURS
    //###########################

    /**
     * Cr�ation de l'interface d'ajout
     * @param index : int - index du film ajout�
     */
    public VueNoter(int index){

        this.index=index;

        this.setLayout(new FlowLayout());

        JLabel titre = new JLabel("Noter le film");
        titre.setPreferredSize(new Dimension(200,50));

        note = new JFormattedTextField();

        note.setPreferredSize(new Dimension(100, 25));

        JLabel surVingt = new JLabel("/"+ Note.NOTE_MAX);
        surVingt.setSize(new Dimension(100,25));

        validerNote = new JButton("Valider");
        validerNote.setPreferredSize(new Dimension(100, 75));

        erreur = new JLabel("Note incorrecte.");
        erreur.setForeground(Color.red);
        erreur.setVisible(false);


        Container cp = this.getContentPane();
        cp.add(titre);
        cp.add(note);
        cp.add(surVingt);
        cp.add(validerNote);
        cp.add(erreur);

        this.setSize(new Dimension(300, 200));
        this.setVisible(true);

    }



    //###########################
    //###### MODIFIEURS
    //###########################

    /**
     * Ajoute un controleur sur les �l�ments de la classe
     * @param c : Controller
     */
    public void ajouterControleurNote(Controller c){
        this.validerNote.addActionListener(c);
    }

    /**
     * Ajoute la note pass�e en param�tre dans le champ de texte note
     * @param note : int - note du film
     */
    public void setNote(int note){
        this.note.setText("" + note);
    }

    /**
     * Affiche/Masque le message d'erreur
     * @param b : boolean - Afficher / Masquer
     */
    public void erreur(boolean b){
        erreur.setVisible(b);
    }



    //###########################
    //###### ACCESSEURS
    //###########################

    /**
     * Retourne l'index du film
     * @return : int - index du film not�
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Retourne la note donn�e par l'utilisateur
     * @return : int - note attribu�e
     */
    public int getTextField(){
        return Integer.parseInt(this.note.getText());
    }
}
