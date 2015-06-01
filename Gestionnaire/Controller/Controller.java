package Gestionnaire.Controller;

import Gestionnaire.Model.Film;
import Gestionnaire.Model.Videotheque;
import Gestionnaire.View.AjoutFilm;
import Gestionnaire.View.VueGestionnaire;
import Gestionnaire.View.VueNoter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileView;
import java.awt.event.*;
import java.io.File;
import java.time.Duration;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe de contrôle des évennements. Implémente ListSelectionListener et ActionListener
 */
public class Controller implements ListSelectionListener, ActionListener, ItemListener {

    //###########################
    //###### ATTRIBUTS
    //###########################

    private VueGestionnaire vue;
    private AjoutFilm vueAjout;
    private Videotheque modele;
    private VueNoter vueNote;



    //###########################
    //###### CONSTRUCTEURS
    //###########################

    /**
     * Constructeur du contrôleur
     * @param vue : VueGestionnaire
     * @param modele : Videotheque
     */
    public Controller(VueGestionnaire vue, Videotheque modele){
        this.vue=vue;
        this.modele=modele;
        this.modele.chargerDonnees(this.vue.getChoixOrdre(), this.vue.getTypeAffiche());
        this.maj();
    }



    //###########################
    //###### MISE A JOUR LISTE
    //###########################

    /*
     * Met à jours la liste : affiche tous les films
     */
    private void majTous(){
        DefaultListModel<String> liste = modele.getListeFilms();
        vue.majListeFilms(liste);
    }

    /*
     * Met à jours la liste : affiche les films vus
     */
    private void majNotes(){
        DefaultListModel<String> liste = modele.getFilmsNotes();
        vue.majListeFilms(liste);
    }

    /*
     * Met à jours la liste : affiche les films non vus
     */
    private void majNonNotes(){
        DefaultListModel<String> liste = modele.getListeFilmsNonNotes();
        vue.majListeFilms(liste);
    }

    /**
     * Met à jours la liste en fonction de la case cochée.
     */
    public void maj(){
        int affichage=vue.getTypeAffiche();
        int ordre=vue.getChoixOrdre();
        this.modele.trier(ordre,affichage);
        if (affichage == VueGestionnaire.AFF_NOTES) this.majNotes();
        else if (affichage == VueGestionnaire.AFF_NON_NOTES) this.majNonNotes();
        else this.majTous();
    }



    //###########################
    //###### EVENEMENTS
    //###########################

    /**
     * Redéfinition de la méthode ValueChanged de ListSelectionListener
     * @param e : ListSelectionEvent
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (vue.getIndexListe()!=-1) {
            int affichage = vue.getTypeAffiche();
            vue.majInfoFilm(modele.getInfoHTML(vue.getIndexListe(),affichage));
            vue.clearImage();
            vue.afficherImage(modele.getAffiche(vue.getIndexListe(),affichage));
            if (modele.estVisionne(vue.getIndexListe(),affichage)) vue.activerBouton(false);
            else vue.activerBouton(true);
        }
    }

    /**
     * Redéfinition de la méthode actionPerformed de ActionListener
     * @param e : ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int affichage = vue.getTypeAffiche();
        int index = vue.getIndexListe();

        if(e.getActionCommand().equals("Notés")){
            vue.supprimerSelect();
            vue.cocherNotes();
        }
        else if(e.getActionCommand().equals("Tous")){
            vue.supprimerSelect();
            vue.cocherTous();
        }
        else if(e.getActionCommand().equals("Non notés")){
            vue.supprimerSelect();
            vue.cocherNonNotes();
        }
        else if(e.getActionCommand().equals("Vu")){
            modele.setVisionne(vue.getIndexListe(),affichage);
            vue.supprimerSelect();
        }
        else if(e.getActionCommand().equals("Non vu")){
            if(modele.getNote(index, affichage)!=-1) {
                int suppr = JOptionPane.showConfirmDialog(this.vue,
                        "Le film possède une note. Le marquer comme non vu supprimera celle-ci.\n Continuer ?",
                        "Marquer non vu",
                        JOptionPane.YES_NO_OPTION);
                if(suppr==JOptionPane.YES_OPTION){
                    this.modele.supprimerNote(index,affichage);
                    this.modele.setNonVisionne(index,affichage);
                }
            }
            modele.setNonVisionne(index,this.vue.getTypeAffiche());
            vue.supprimerSelect();
        }
        else if(e.getActionCommand().equals("Noter")){
            this.vue.supprimerSelect();
            vueNote = new VueNoter(index);
            vueNote.ajouterControleurNote(this);
            if(modele.getNote(index,affichage) !=-1){
                vueNote.setNote(this.modele.getNote(index,affichage));
            }
        }
        else if(e.getActionCommand().equals("Ajouter")){
            vueAjout = new AjoutFilm();
            vueAjout.ajouterControleur(this);
        }
        else if(e.getActionCommand().equals("Valider")){
            if(vueNote.getTextField()<=20 && vueNote.getTextField()>=0){
                this.modele.noterFilm(this.vueNote.getIndex(), this.vueNote.getTextField(),affichage);
                this.vueNote.dispose();
                this.modele.setVisionne(this.vueNote.getIndex(),affichage);
            }
            else this.vueNote.erreur(true);
        }
        else if(e.getActionCommand().equals("Parcourir")){
            final File dirLock = new File ("./affiches");
            JFileChooser chooser = new JFileChooser(dirLock);
            chooser.setApproveButtonText("Sélectionner");
            chooser.setFileView(new FileView() {
                @Override
                public Boolean isTraversable(File f) {
                    return dirLock.equals(f);
                }
            });
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                String path=chooser.getSelectedFile().getAbsolutePath();
                this.vueAjout.setChemin(path);
                if (!Videotheque.verifExtFichier(path)) this.vueAjout.setErreurAffiche("Extention incorrecte.");
                else this.vueAjout.setErreurAffiche(false);
            }
        }
        else if(e.getActionCommand().equals("Ajouter le film")){

            boolean titreOk, synopsisOk, dureeOk, dateOk, realOk, imageOk;
            String titre, synopsis, realisateur, affiche;
            Duration duree;
            int annee;

            // Vérification du titre
            if(this.vueAjout.getZoneTitreFilm().isEmpty()){
                this.vueAjout.setErreurTitreFilm(true);
                titreOk = false;
            }
            else {
                this.vueAjout.setErreurTitreFilm(false);
                titreOk = true;
            }

            // Vérification de la synopsis
            if(this.vueAjout.getZoneSynopsis().isEmpty()){
                this.vueAjout.setErreurSynopsis(true);
                synopsisOk= false;
            }
            else {
                this.vueAjout.setErreurSynopsis(false);
                synopsisOk= true;
            }

            //Vérification de la durée
            if(this.vueAjout.getDuree().isEmpty()){
                this.vueAjout.setErreurDuree(true);
                dureeOk= false;
            }
            else {
                this.vueAjout.setErreurDuree(false);
                dureeOk= true;
            }

            //Vérification de la date
            if(this.vueAjout.getAnneeSorite().isEmpty()){
                this.vueAjout.setErreurAnneeSortie(true);
                dateOk= false;
            }
            else {
                this.vueAjout.setErreurAnneeSortie(false);
                dateOk= true;
            }

            //Vérification du réalisateur
            if(this.vueAjout.getZoneRealisateur().isEmpty()){
                this.vueAjout.setErreurRealisateur(true);
                realOk= false;
            }
            else {
                this.vueAjout.setErreurRealisateur(false);
                realOk= true;
            }

            //Vérification de l'afficher
            if(this.vueAjout.getCheminAffiche().isEmpty()){
                this.vueAjout.setErreurAffiche(AjoutFilm.ERREUR);
                imageOk= false;
            }
            else if(!Videotheque.verifExtFichier(this.vueAjout.getCheminAffiche())){
                this.vueAjout.setErreurAffiche("Extention incorrecte.");
                imageOk=false;
            }
            else {
                this.vueAjout.setErreurAffiche(false);
                imageOk= true;
            }

            if(titreOk && synopsisOk && dureeOk && dateOk && realOk && imageOk) {

                titre = this.vueAjout.getZoneTitreFilm();
                synopsis = this.vueAjout.getZoneSynopsis();
                if (this.vueAjout.getDuree().equals("> 300")) {
                    duree = Duration.ofMinutes(301);
                } else duree = Duration.ofMinutes(Integer.parseInt(this.vueAjout.getDuree()));
                annee = Integer.parseInt(this.vueAjout.getAnneeSorite());
                realisateur = this.vueAjout.getZoneRealisateur();
                affiche = formaterChemin(this.vueAjout.getCheminAffiche());
                this.modele.ajouterFilm(new Film(titre, synopsis, duree, annee, realisateur, affiche, false), this.vue.getChoixOrdre(), affichage);
                this.vueAjout.dispose();

            }
        }
        this.maj();
        this.modele.exportBase();
    }

    /**
     * Redéfinition de la méthode itemStateChanged de ItemListener
     * @param e : ItemEvent
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        modele.trier(this.vue.getChoixOrdre(),this.vue.getTypeAffiche());
        if(this.vue.getTypeAffiche()==VueGestionnaire.AFF_TOUS) this.majTous();
        else if (this.vue.getTypeAffiche()==VueGestionnaire.AFF_NON_NOTES) this.majNonNotes();
        else this.majNotes();
    }



    //###########################
    //###### STATICS UTILITAIRES
    //###########################

    /**
     * Permet de formater le chemin pour ne garder que le nom du fichier et de son extension
     * @param affiche : String - Chemin d'accès au fichier
     * @return String : nom du fichier
     */
    private static String formaterChemin(String affiche){
        affiche = affiche.replace("\\","/");
        String[] afficheDecoupe = affiche.split("/");
        System.out.println(afficheDecoupe[afficheDecoupe.length - 1]);
        return afficheDecoupe[afficheDecoupe.length-1];
    }


}
