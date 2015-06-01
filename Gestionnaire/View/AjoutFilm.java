package Gestionnaire.View;

import Gestionnaire.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Calendar;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe de création de l'interface d'ajout du film
 */
public class AjoutFilm extends JDialog {

    //###########################
    //###### CONSTANTES
    //###########################

    private static final Dimension DIM_LABEL = new Dimension(150,25);
    private static final Dimension DIM_TEXT_FIELD = new Dimension(250,25);
    private static final EmptyBorder BORDURE = new EmptyBorder(5,5,5,0);
    public static final String ERREUR = "Champ obligatoire.";



    //###########################
    //###### ATTRIBUTS
    //###########################

    private JTextField zoneTitreFilm, zoneRealisateur, cheminAffiche;
    private JTextArea zoneSynopsis;
    private JLabel erreurTitreFilm, erreurSynopsis, erreurDuree, erreurAnneeSortie, erreurRealisateur, erreurAffiche;
    private JButton parcourir, boutonAjouter;
    private JComboBox<String> setDuree, setAnnee;



    //###########################
    //###### CONSTRUCTEURS
    //###########################

    /**
     * Constructeur de la classe : création de l'interface
     */
    public AjoutFilm(){

        /* Fenêtre principale : définition des options */
        this.setSize(new Dimension(800, 800));
        this.setResizable(false);
        this.setLayout(new GridLayout(0, 1));
        Container contentPane = this.getContentPane();

        /* Ajout d'un JLabel faisant office de titre de page*/
        JLabel title = new JLabel("Ajouter un film");
        title.setPreferredSize(new Dimension(600, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("TimesRoman", Font.BOLD, 30));
        contentPane.add(title);

        /* Chaque attribut du film va être organisé de la façon suivante :
        *
        *   JPanel option
        *       JLabel nom_de_l_option
        *       JTextField champ_a_remplir
        *       JLabel champ_erreur
        *
        * */
        // Titre du film
        JPanel titreFilm = new JPanel();
            JLabel labelTitreFilm = new JLabel("Titre du film :");
            labelTitreFilm.setPreferredSize(DIM_LABEL);
            labelTitreFilm.setHorizontalAlignment(SwingConstants.CENTER);
            titreFilm.add(labelTitreFilm);

            zoneTitreFilm = new JTextField();
            zoneTitreFilm.setBackground(Color.WHITE);
            zoneTitreFilm.setOpaque(true);
            zoneTitreFilm.setBorder(BORDURE);
            zoneTitreFilm.setPreferredSize(DIM_TEXT_FIELD);
            titreFilm.add(zoneTitreFilm);

            erreurTitreFilm = new JLabel(ERREUR);
            erreurTitreFilm.setPreferredSize(DIM_LABEL);
            erreurTitreFilm.setForeground(Color.red);
            erreurTitreFilm.setVisible(false);
            titreFilm.add(erreurTitreFilm);
        contentPane.add(titreFilm);

        // Synopsis
        JPanel synopsisFilm = new JPanel();
            JLabel labelSynopsisFilm = new JLabel("Synopsis :");
            labelSynopsisFilm.setPreferredSize(DIM_LABEL);
            labelSynopsisFilm.setVerticalAlignment(SwingConstants.CENTER);
            labelSynopsisFilm.setHorizontalAlignment(SwingConstants.CENTER);
            synopsisFilm.add(labelSynopsisFilm);

            zoneSynopsis = new JTextArea(5,20);
            zoneSynopsis.setLayout(new BorderLayout());
            zoneSynopsis.setLineWrap(true);
            JScrollPane scrollPaneSynopsis = new JScrollPane(zoneSynopsis,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //scrollPaneSynopsis.setBounds(new Rectangle(-4, 1, 250, 100));
            synopsisFilm.add(scrollPaneSynopsis);

            erreurSynopsis = new JLabel(ERREUR);
            erreurSynopsis.setPreferredSize(DIM_LABEL);
            erreurSynopsis.setForeground(Color.red);
            erreurSynopsis.setVisible(false);
            synopsisFilm.add(erreurSynopsis);
        contentPane.add(synopsisFilm);

        //Durée
        JPanel dureeFilm = new JPanel();
            JLabel labelTitre = new JLabel("Durée :");
            labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
            labelTitre.setPreferredSize(DIM_LABEL);
            dureeFilm.add(labelTitre);

            JPanel selectionDuree = new JPanel();
            setDuree = new JComboBox<>();
            setDuree.setPreferredSize(DIM_TEXT_FIELD);
            setDuree.addItem("");
            for (int i=1;i<=300;i++){
                setDuree.addItem(i+"");
            }
            setDuree.addItem("> 300");
            selectionDuree.add(setDuree);
            JLabel min = new JLabel("min");
            selectionDuree.add(min);
            dureeFilm.add(selectionDuree);



            erreurDuree = new JLabel(ERREUR);
            erreurDuree.setPreferredSize(DIM_LABEL);
            erreurDuree.setForeground(Color.red);
            erreurDuree.setVisible(false);
            dureeFilm.add(erreurDuree);
        contentPane.add(dureeFilm);

        // Annee Sortie
        JPanel anneeSortie = new JPanel();
            JLabel labelAnnee = new JLabel("Année de sortie ");
            labelAnnee.setHorizontalAlignment(SwingConstants.CENTER);
            labelAnnee.setPreferredSize(DIM_LABEL);
            anneeSortie.add(labelAnnee);

            setAnnee = new JComboBox<>();
            setAnnee.setPreferredSize(DIM_TEXT_FIELD);
            setAnnee.addItem("");
            for (int i=Calendar.getInstance().get(Calendar.YEAR); i>=1891; i--){
                setAnnee.addItem(i+"");
            }
            anneeSortie.add(setAnnee);

            erreurAnneeSortie =  new JLabel(ERREUR);
            erreurAnneeSortie.setPreferredSize(DIM_LABEL);
            erreurAnneeSortie.setForeground(Color.RED);
            erreurAnneeSortie.setVisible(false);
            anneeSortie.add(erreurAnneeSortie);
        contentPane.add(anneeSortie);

        JPanel realisateur = new JPanel();
            JLabel labelRealisateur = new JLabel("Réalisateur :");
            labelRealisateur.setPreferredSize(DIM_LABEL);
            labelRealisateur.setHorizontalAlignment(SwingConstants.CENTER);
            realisateur.add(labelRealisateur);

            zoneRealisateur = new JTextField();
            zoneRealisateur.setBackground(Color.WHITE);
            zoneRealisateur.setOpaque(true);
            zoneRealisateur.setBorder(BORDURE);
            zoneRealisateur.setPreferredSize(DIM_TEXT_FIELD);
            realisateur.add(zoneRealisateur);

            erreurRealisateur = new JLabel(ERREUR);
            erreurRealisateur.setPreferredSize(DIM_LABEL);
            erreurRealisateur.setForeground(Color.red);
            erreurRealisateur.setVisible(false);
            realisateur.add(erreurRealisateur);
        contentPane.add(realisateur);

        JPanel image = new JPanel();
            JLabel labelImage = new JLabel("Affiche :");
            labelImage.setPreferredSize(DIM_LABEL);
            labelImage.setHorizontalAlignment(SwingConstants.CENTER);
            image.add(labelImage);

            cheminAffiche = new JTextField();
            cheminAffiche.setPreferredSize(DIM_TEXT_FIELD);
            image.add(cheminAffiche);

            parcourir = new JButton("Parcourir");
            parcourir.setPreferredSize(new Dimension(150, 23));
            image.add(parcourir);

            erreurAffiche = new JLabel("");
            erreurAffiche.setPreferredSize(DIM_LABEL);
            erreurAffiche.setForeground(Color.red);
            erreurAffiche.setVisible(false);
            image.add(erreurAffiche);
        contentPane.add(image);

        boutonAjouter = new JButton("Ajouter le film");
        contentPane.add(boutonAjouter);

        this.setVisible(true);
    }



    //###########################
    //###### MODIFIEURS
    //###########################

    /**
     * Ajout d'un controleur
     * @param c : Controller
     */
    public void ajouterControleur(Controller c){
        this.parcourir.addActionListener(c);
        this.boutonAjouter.addActionListener(c);
    }

    /**
     * Ajoute le chemin passé en paramètre dans la zone de text correspondante
     * @param chemin : String - Chemin du fichier
     */
    public void setChemin(String chemin){
        this.cheminAffiche.setText(chemin);
    }

    /**
     * Affiche ou supprime le message d'erreur titre selon la valeur du paramètre
      * @param b : booleen - Afficher ou masquer
     */
    public void setErreurTitreFilm(boolean b){
        this.erreurTitreFilm.setVisible(b);
    }

    /**
     * Affiche ou supprime le message d'erreur synopsis selon la valeur du paramètre
     * @param b : booleen - Afficher ou masquer
     */
    public void setErreurSynopsis(boolean b){
        this.erreurSynopsis.setVisible(b);
    }

    /**
     * Affiche ou supprime le message d'erreur durée selon la valeur du paramètre
     * @param b : booleen - Afficher ou masquer
     */
    public void setErreurDuree(boolean b){
        this.erreurDuree.setVisible(b);
    }

    /**
     * Affiche ou supprime le message d'erreur année selon la valeur du paramètre
     * @param b : booleen - Afficher ou masquer
     */
    public void setErreurAnneeSortie(boolean b){
        this.erreurAnneeSortie.setVisible(b);
    }

    /**
     * Affiche ou supprime le message d'erreur realisateur selon la valeur du paramètre
     * @param b : booleen - Afficher ou masquer
     */
    public void setErreurRealisateur(boolean b){
        this.erreurRealisateur.setVisible(b);
    }

    /**
     * Affiche ou supprime le message d'erreur affiche selon la valeur du paramètre
     * @param message : String - Message d'erreur
     */
    public void setErreurAffiche(String message){
        this.erreurAffiche.setText(message);
        this.erreurAffiche.setVisible(true);
    }

    /**
     * Affiche ou supprime le message d'erreur affiche selon la valeur du paramètre
     * @param b : booleen - Afficher ou masquer
     */
    public void setErreurAffiche(boolean b){
        this.erreurAffiche.setVisible(b);
    }



    //###########################
    //###### ACCESSEURS
    //###########################

    /**
     * Retourne le contenu de la zone titre
     * @return : String - Titre
     */
    public String getZoneTitreFilm() {
        return zoneTitreFilm.getText();
    }

    /**
     * Retourne le contenu de la zone réalisateur
     * @return : String - Réalisateur
     */
    public String getZoneRealisateur() {
        return zoneRealisateur.getText();
    }

    /**
     * Retourne le contenu de la zone fichier
     * @return : String - Chemin d'acces à l'affiche
     */
    public String getCheminAffiche() {
        return cheminAffiche.getText();
    }

    /**
     * Retourne le contenu de la zone synopsis
     * @return : String - Synopsis
     */
    public String getZoneSynopsis() {
        return zoneSynopsis.getText();
    }

    /**
     * Retourne le contenu de la zone Annee
     * @return : String - Annee de sortie
     */
    public String getAnneeSorite(){
        return String.valueOf(this.setAnnee.getSelectedItem());
    }

    /**
     * Retourne le contenu de la zone durée
     * @return : String - Durée
     */
    public String getDuree(){
        return String.valueOf(this.setDuree.getSelectedItem());
    }
}
