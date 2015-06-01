package Gestionnaire.View;

import Gestionnaire.Controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe de création de l'interface graphique
 */
public class VueGestionnaire extends JPanel {

    public static int ORDRE_TITRE = 0;
    public static int ORDRE_NOTE = 1;
    public static int ORDRE_ANNEE = 2;
    public static int AFF_TOUS = 9;
    public static int AFF_NOTES = 8;
    public static int AFF_NON_NOTES = 7;


    //###########################
    //###### ATTRIBUTS
    //###########################

    private JList<String> listeFilms;
    private JLabel affiche;
    private JLabel infoFilm;
    private JCheckBox selectNotes, selectNonNotes, selectTous;
    private JComboBox<String> choixOrdre;
    private JButton marquerVu, marquerNonVu, ajouter, noter;



    //###########################
    //###### CONSTRUCTEUR
    //###########################

    /**
     * Constructeur : génération de l'affichage.
     */
    public VueGestionnaire(){

        // définition d'un BorderLayout ainsi que d'une bordure exterieure de 10px
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Titre de la page
        JLabel title = new JLabel("Outil de gestion des films");
        title.setPreferredSize(new Dimension(1000, 50));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER); // titre centré

        // JList affichant la liste des films
        listeFilms = new JList<>();
        JScrollPane liste = new JScrollPane(listeFilms);
        liste.setPreferredSize(new Dimension(1000, 300));
        liste.setBorder(new EmptyBorder(10, 10, 10, 0)); // bordure intérieure gauche de 10px*/

        liste.setSize(new Dimension(listeFilms.getWidth(),listeFilms.getHeight()));

        // Zone d'affichage de l'affiche du film
        affiche = new JLabel();
        affiche.setPreferredSize(new Dimension(400,300));
        affiche.setBackground(Color.lightGray); //fond gris
        affiche.setVerticalAlignment(SwingConstants.CENTER);
        affiche.setHorizontalAlignment(SwingConstants.CENTER);
        affiche.setOpaque(true);

        // Panneau de selection (affichage, vu / non vu, ajout)
        JPanel optionPane = new JPanel();
            optionPane.setPreferredSize(new Dimension(150, 300));
            //Titre
            JLabel titleAffiche = new JLabel("Affichage :");
            titleAffiche.setHorizontalAlignment(SwingConstants.CENTER);
            titleAffiche.setVerticalAlignment(SwingConstants.CENTER);
            titleAffiche.setPreferredSize(new Dimension(150, 50));
            //Choix de l'ordre de tri
            JPanel ordre = new JPanel(new FlowLayout());
                //Titre
                JLabel labelOrdre = new JLabel("Trier par :");
                //Liste des choix
                choixOrdre = new JComboBox<>();
                choixOrdre.addItem("Titre");
                choixOrdre.addItem("Note");
                choixOrdre.addItem("Année");
                //Ajout au pannel superieur
                ordre.add(labelOrdre);
                ordre.add(choixOrdre);
            //CheckBox
            selectTous = new JCheckBox("Tous");
            selectTous.setSelected(true);
            selectNotes = new JCheckBox("Notés");
            selectNonNotes = new JCheckBox("Non notés");
            //Bouton 'Vu'
            marquerVu = new JButton("Vu");
            marquerVu.setEnabled(false);
            marquerVu.setPreferredSize(new Dimension(100, 30));
            //Bouton 'Non Vu'
            marquerNonVu=new JButton("Non vu");
            marquerNonVu.setEnabled(false);
            marquerNonVu.setPreferredSize(new Dimension(100, 30));
            //Bouton 'Noter'
            noter = new JButton("Noter");
            noter.setEnabled(false);
            noter.setPreferredSize(new Dimension(100, 30));
            //Bouton 'Ajouter'
            ajouter = new JButton("Ajouter");
            ajouter.setEnabled(true);
            ajouter.setPreferredSize(new Dimension(100, 30));

            optionPane.add(titleAffiche); // définitions de CheckBox pour gérer le type d'affichage.
            optionPane.add(ordre);
            optionPane.add(selectTous);
            optionPane.add(selectNotes);
            optionPane.add(selectNonNotes);
            optionPane.add(marquerVu);
            optionPane.add(marquerNonVu);
            optionPane.add(noter);
            optionPane.add(ajouter);



        // Informations sur le film
        infoFilm = new JLabel();
        infoFilm.setBackground(Color.white);
        infoFilm.setBorder(BorderFactory.createLineBorder(Color.gray));
        infoFilm.setPreferredSize(new Dimension(800, 200));
        infoFilm.setVerticalAlignment(SwingConstants.TOP);

        JScrollPane infos = new JScrollPane(infoFilm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Si la description est grande, un scrollbar apparait
        infos.setPreferredSize(new Dimension(800,200));





        this.add(title, BorderLayout.NORTH); // ajout du titre au panneau principal, à son sommet
        this.add(liste, BorderLayout.WEST); // ajout de la liste au panneau principale, sur la gauche.
        this.add(affiche, BorderLayout.CENTER); // ajout de la zone au centre du Layout : à droite de la JListe.
        this.add(optionPane, BorderLayout.EAST); // ajout du panneau de selection au panneau principal, sur la droite
        this.add(infos,BorderLayout.SOUTH);
        this.setVisible(true);
    }



    //###########################
    //###### MODIFIEURS
    //###########################

    /**
     * Ajout des listenner : écoute des changements sur l'interface
     * @param l : contrôleur à l'écoute de l'interface
     */
    public void ajouterListener(Controller l){
        selectNonNotes.addActionListener(l);
        selectNotes.addActionListener(l);
        selectTous.addActionListener(l);
        noter.addActionListener(l);
        ajouter.addActionListener(l);
        marquerNonVu.addActionListener(l);
        marquerVu.addActionListener(l);
        listeFilms.addListSelectionListener(l);
        choixOrdre.addItemListener(l);
    }

    /**
     * Mise à jour de la liste des films
     * @param films : liste des films à afficher
     */
    public void majListeFilms(DefaultListModel<String> films){
        this.desactiverBouton();
        this.listeFilms.clearSelection();
        this.listeFilms.setModel(films);
    }

    /**
     * Met à jours le contenu de la zone d'information sur le film
     * @param info : infos sur le film
     */
    public void majInfoFilm(String info){
        this.infoFilm.setText(info);
    }

    /**
     * Gestion du cochage de la case 'afficher vus' : déselection des autres checkbox
     */
    public void cocherNotes(){
        this.selectNotes.setSelected(true);
        this.selectNonNotes.setSelected(false);
        this.selectTous.setSelected(false);
        this.desactiverBouton();
    }

    /**
     * Gestion du cochage de la case 'afficher non vus' : déselection des autres checkbox
     */
    public void cocherNonNotes(){
        this.selectNotes.setSelected(false);
        this.selectNonNotes.setSelected(true);
        this.selectTous.setSelected(false);
        this.desactiverBouton();
    }

    /**
     * Gestion du cochage de la case 'afficher tous' : déselection des autres checkbox
     */
    public void cocherTous(){
        this.selectNotes.setSelected(false);
        this.selectNonNotes.setSelected(false);
        this.selectTous.setSelected(true);
        this.desactiverBouton();
    }

    /**
     * Réinitialise la sélection dans la liste de films et efface le contenu de la zone d'information
     */
    public void supprimerSelect(){
        this.listeFilms.setSelectedIndex(-1);
        this.infoFilm.setText("");
        this.clearImage();
    }

    /**
     * Lorsque b est vrai, le bouton 'non vu' se désactive et le bouton 'vu' s'active. Inversement si b est faux
     * @param b : booleen
     */
    public void activerBouton(boolean b){
        this.noter.setEnabled(true);
        this.marquerNonVu.setEnabled(!b);
        this.marquerVu.setEnabled(b);
    }

    /**
     * Desactivation de tous les boutons sauf 'ajout'
     */
    public void desactiverBouton(){
        this.marquerVu.setEnabled(false);
        this.marquerNonVu.setEnabled(false);
        this.noter.setEnabled(false);
    }

    /**
     * Retourne un entier correspondant au type d'affichage (tous, notés, non notés).
     * @return : int - type d'affichage
     */
    public int getTypeAffiche(){
        if(this.selectTous.isSelected()) return AFF_TOUS;
        if(this.selectNonNotes.isSelected()) return AFF_NON_NOTES;
        return AFF_NOTES;
    }

    /**
     * Permet l'affichage de l'image
     * @param imagePath : String
     */
    public void afficherImage(String imagePath){
        try {
            File imageFile = new File("affiches/"+imagePath);
            if (!imageFile.exists()) throw new Exception("inexistant");
            BufferedImage image = ImageIO.read(imageFile);
            this.affiche.setIcon(new ImageIcon(redimentionner(image)));
            //this.affiche.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            this.affiche.setText("Impossible d'ouvrir le fichier");
            e.printStackTrace();
        } catch (Exception e){
            if(e.getMessage().equals("inexistant")) this.affiche.setText("Le fichier n'existe pas.");
        }
    }

    /**
     * Supprime l'affiche
     */
    public void clearImage(){
        this.affiche.setIcon(null);
        this.affiche.setText("");
    }



    //###########################
    //###### ACCESSEURS
    //###########################

    /**
     * Retourne l'index selectionné dans la JListe de films
     * @return int : index selectionné
     */
    public int getIndexListe(){
        return listeFilms.getSelectedIndex();
    }

    /**
     * Retourne l'ordre dans lequel les films sont triés
     * @return : int - Code ordre
     */
    public int getChoixOrdre(){
        if (choixOrdre.getSelectedItem().toString().equals("Note")) return ORDRE_NOTE;
        if(choixOrdre.getSelectedItem().toString().equals("Titre")) return ORDRE_TITRE;
        return ORDRE_ANNEE;
    }



    //###########################
    //###### STATICS UTILITAIRES
    //###########################

    /**
     * Redimentionne l'image
     * @param imageOrigine : BufferedImage
     * @return Image : image redimentionnée
     */
    public static Image redimentionner(BufferedImage imageOrigine){

        int max_height = 400;
        int max_width = 300;

        int width = imageOrigine.getWidth();
        int height = imageOrigine.getHeight();

        float ratio = (float)width/(float)height;

        if(height>max_height){
            height=max_height;
            width=(int)(ratio*height);

        }

        if(width > max_width){
            width=max_width;
            height=(int)(width/ratio);
        }

        return imageOrigine.getScaledInstance(width, height, Image.SCALE_DEFAULT);


    }




}
