package Gestionnaire.Model;


import Gestionnaire.View.VueGestionnaire;

import java.io.*;
import java.lang.String;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe modélisant la vidéothèque
 */
public class Videotheque {

    //###########################
    //###### ATTRIBUTS
    //###########################

    public ArrayList<Film> filmsNonNotes;
    public ArrayList<Film> filmsNotes;
    public ArrayList<Film> filmsTous;
    public String fichier;



    //###########################
    //###### CONSTRUCTEUR
    //###########################

    /**
     * Constructeur de la vidéothèque. Se lance avec le fichier passé en paramètre et contenant les listes de Films
     * @param fichier : String - Base de donnée contenant les films
     */
    public Videotheque(String fichier){
        this.filmsNonNotes = new ArrayList<>();
        this.filmsTous = new ArrayList<>();
        this.filmsNotes = new ArrayList<>();
        this.fichier = fichier;
    }



    //###########################
    //###### MODIFIEURS
    //###########################

    /**
     * Ajoute le film passé en paramètre à la liste des films non vus
     * @param f : Film
     * @param ordre : int - ordre de tri de la liste
     * @param affichage : int - liste selectionnée
     */
    public void ajouterFilm (Film f, int ordre, int affichage){
        this.filmsTous.add(f);
        if (f.estVisionne()) this.filmsNotes.add(this.filmsTous.get(this.filmsTous.indexOf(f)));
        else this.filmsNonNotes.add(this.filmsTous.get(this.filmsTous.indexOf(f)));
        this.trier(ordre, affichage);
    }

    /**
     * Ajoute les Films contenus dans l'ArrayList à la liste des films
     * @param listeFilm : ArrayList<Film> - Liste des films
     * @param ordre : int - ordre de tri de la liste
     * @param affichage : int - liste selectionnée
     */
    public void ajouterFilm (ArrayList<Film> listeFilm, int ordre, int affichage){
        for(Film f : listeFilm){
            if(!this.filmsTous.contains(f)) {
                this.filmsTous.add(f);
                if (f.estVisionne()) this.filmsNotes.add(this.filmsTous.get(this.filmsTous.indexOf(f)));
                else this.filmsNonNotes.add(this.filmsTous.get(this.filmsTous.indexOf(f)));
            }
        }
        this.trier(ordre, affichage);
    }

    /**
     * Ordonne la liste de Films celon l'ordre passé en paramètre.
     * @param ordre : int - ordre de tri de la liste
     * @param affichage : int - liste selectionnée
     */
    public void trier(int ordre, int affichage){
        if(ordre==VueGestionnaire.ORDRE_TITRE) {
            if(affichage==VueGestionnaire.AFF_TOUS) this.filmsTous.sort(Film.compareByNom());
            else if(affichage==VueGestionnaire.AFF_NOTES) this.filmsNotes.sort(Film.compareByNom());
            else this.filmsNonNotes.sort(Film.compareByNom());
        }
        else if(ordre==VueGestionnaire.ORDRE_ANNEE) {
            if(affichage==VueGestionnaire.AFF_TOUS) this.filmsTous.sort(Film.compareByDate());
            else if(affichage==VueGestionnaire.AFF_NOTES) this.filmsNotes.sort(Film.compareByDate());
            else this.filmsNonNotes.sort(Film.compareByDate());
        }
        else{
            if(affichage==VueGestionnaire.AFF_TOUS) this.filmsTous.sort(Film.compareByNote());
            else if(affichage==VueGestionnaire.AFF_NOTES) this.filmsNotes.sort(Film.compareByNote());
            else this.filmsNonNotes.sort(Film.compareByNote());
        }
    }

    /**
     * Attribut une note au Film situé à l'index passé en paramètre
     * @param index : int - index du film dans l'ArrayList
     * @param note : int - note à attribuer au film
     * @return : boolean - vrai si l'ajout à réussi, faux sinon
     * @param affichage : int - liste selectionnée
     */
    public boolean noterFilm (int index, int note, int affichage){

        if(affichage==VueGestionnaire.AFF_NOTES){
            Film f = this.filmsNotes.get(index);
            return f.setNote(note);
        }

        if(affichage==VueGestionnaire.AFF_TOUS){
            Film f = this.filmsTous.get(index);
            this.filmsNotes.add(f);
            this.filmsNonNotes.remove(f);
            return f.setNote(note);
        }

        Film f = this.filmsNonNotes.get(index);
        this.filmsNotes.add(f);
        this.filmsNonNotes.remove(f);
        return f.setNote(note);
    }

    /**
     * Défini le Film situé à l'index passé en paramètre comme visionné
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     */
    public void setVisionne(int index, int affichage){
        if(affichage==VueGestionnaire.AFF_NOTES) this.filmsNotes.get(index).setVisionne();
        else if(affichage==VueGestionnaire.AFF_TOUS) this.filmsTous.get(index).setVisionne();
        else this.filmsNonNotes.get(index).setVisionne();
    }

    /**
     * Défini le Film situé à l'index passé en paramètre comme non visionné
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     */
    public void setNonVisionne(int index, int affichage){
        if(affichage==VueGestionnaire.AFF_NOTES) this.filmsNotes.get(index).setNonVisionne();
        else if(affichage==VueGestionnaire.AFF_TOUS) this.filmsTous.get(index).setNonVisionne();
        else this.filmsNonNotes.get(index).setNonVisionne();
    }

    /**
     * Ajoute la liste des films contenu dans le fichier base à l'ArrayList
     * @param ordre : int - ordre de tri
     * @param affichage : int - liste selectionnée
     */
    public void chargerDonnees(int ordre, int affichage){
        this.ajouterFilm(this.lectureBase(), ordre, affichage);
    }

    /**
     * Supprime la note du film à la place index de l'arrayliste
     * @param index : index du film
     * @param affichage : type d'affichage
     */
    public void supprimerNote(int index, int affichage){

        if (affichage==VueGestionnaire.AFF_NON_NOTES) {
            Film f = this.filmsNonNotes.get(index);
            this.filmsNotes.remove(f);
            this.filmsNonNotes.add(f);
            f.supprimerNote();
        }
        else if (affichage==VueGestionnaire.AFF_NOTES){
            Film f = this.filmsNotes.get(index);
            this.filmsNotes.remove(f);
            this.filmsNonNotes.add(f);
            f.supprimerNote();
        }
        else{
            Film f = this.filmsTous.get(index);
            this.filmsNotes.remove(f);
            this.filmsNonNotes.add(f);
            f.supprimerNote();
        }
    }



    //###########################
    //###### ACCESSEURS
    //###########################

    /**
     * Informe si le film à l'index passé en paramètre à été visionné
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     * @return : boolean - Vrai si le film est visionné, faux sinon
     *
     */
    public boolean estVisionne(int index, int affichage){
        if(affichage==VueGestionnaire.AFF_NOTES) return this.filmsNotes.get(index).estVisionne();
        if(affichage==VueGestionnaire.AFF_TOUS) return this.filmsTous.get(index).estVisionne();
        return this.filmsNonNotes.get(index).estVisionne();
    }

    /**
     * Donne la note attribué au film situé à l'index passé en paramètre
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     * @return : int - note du film si définie, -1 sinon
     */
    public int getNote(int index, int affichage){
        if(affichage==VueGestionnaire.AFF_NOTES) return this.filmsNotes.get(index).getNote();
        if(affichage==VueGestionnaire.AFF_TOUS) return this.filmsTous.get(index).getNote();
        return this.filmsNonNotes.get(index).getNote();
    }

    /**
     * Retourne la liste de tous films
     * @return : DefaultListModel<String> - liste des films
     */
    public DefaultListModel<String> getListeFilms(){
        DefaultListModel<String> liste = new DefaultListModel<>();
        for (Film film : filmsTous) {
            liste.addElement(film.getTitre());
        }
        return liste;
    }

    /**
     * Retourne la liste des films vus
     * @return : DefaultListModel<String> - liste des films
     */
    public DefaultListModel<String> getFilmsNotes(){
        DefaultListModel<String> liste = new DefaultListModel<>();
        for (Film film : filmsNotes) {
            liste.addElement(film.getTitre());
        }
        return liste;
    }

    /**
     * Retourne la liste des films non vus
     * @return : DefaultListModel<String> - liste des films
     */
    public DefaultListModel<String> getListeFilmsNonNotes(){
        DefaultListModel<String> liste = new DefaultListModel<>();
        for (Film film : filmsNonNotes) {
            liste.addElement(film.getTitre());
        }
        return liste;
    }

    /**
     * Retourne les informations concernant le film dont l'index est passé en paramètre
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     * @return : String - informations au format HTML
     */
    public String getInfoHTML(int index, int affichage){
        if (affichage==VueGestionnaire.AFF_TOUS) return filmsTous.get(index).getInfoHTML();
        if (affichage==VueGestionnaire.AFF_NOTES) return filmsNotes.get(index).getInfoHTML();
        return filmsNonNotes.get(index).getInfoHTML();
    }

    /**
     * Retourne le nom du fichier de l'affiche du film dont l'index est passé en paramètre
     * @param index : int - index du film dans l'ArrayList
     * @param affichage : int - liste selectionnée
     * @return : String - nom du fichier de l'affiche
     */
    public String getAffiche(int index, int affichage){
        if (affichage==VueGestionnaire.AFF_TOUS) return filmsTous.get(index).getImage();
        if (affichage==VueGestionnaire.AFF_NOTES) return filmsNotes.get(index).getImage();
        return filmsNonNotes.get(index).getImage();
    }



    //###########################
    //###### TRAVAIL SUR LE FICHIER BASE
    //###########################

    /**
     * Lit les données du fichier base et renvoi les films dans une ArrayList
     * @return : ArrayList<Film> - Liste de films du fichier base
     */
    private ArrayList<Film> lectureBase(){

        ArrayList<Film> liste = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            String ligne;
            while ((ligne=reader.readLine()) != null) {
                try {

                    if (ligne.charAt(0) != '#') {
                        System.out.println("Ajout de la ligne : \n" + ligne + "\n\n");
                        Film f = creerFilm(ligne);
                        if (f == null)
                            System.out.println("[ATTENTION] Videotheque.lectureBase - une des ligne de la base est incorrecte.");
                        else liste.add(creerFilm(ligne));
                    }
                } catch (Exception e) {
                    if (e.getMessage().equals("film"))
                        System.out.println("[ERREUR] Videotheque.lectureBase - une des ligne de la base est incorrecte.");
                    else {
                        System.out.println("[ERREUR] Videotheque.lectureBase - erreur inconnue :\n");
                        e.printStackTrace();
                        System.out.println("\n\n");
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e){
            System.out.println("[ERREUR] Videotheque.lectureBase - fichier de base inexistant.");
        } catch (IOException e){
            System.out.println("[ERREUR] Videotheque.lectureBase - lecture du fichier impossible.");
        }

        return liste;
    }

    /**
     * Création d'un film à partir des données de la base
     * @param ligne : String - informations du film sous forme d'une ligne
     * @return : Film - Film créé grace aux informations fournies dans la ligne
     */
    private Film creerFilm(String ligne){

        String[] ligneDecoupee = ligne.split("\t");
        try {
            if (ligneDecoupee.length !=8) throw new Exception("taille");
            if (!verifExtFichier(ligneDecoupee[5])) throw new Exception("formatImage");
            String titre = ligneDecoupee[0];
            String synopsis = ligneDecoupee[1];
            Duration duree = Duration.ofMinutes(Integer.parseInt(ligneDecoupee[2]));
            int annee = Integer.parseInt(ligneDecoupee[3]);
            String realisateur = ligneDecoupee[4];
            String image = ligneDecoupee[5];
            Boolean visionne = ligneDecoupee[7].equals("true");
            if (ligneDecoupee[6].equals("null")) return new Film(titre,synopsis,duree,annee,realisateur,image,visionne);
            int note = Integer.parseInt(ligneDecoupee[6]);
            if (note < Note.NOTE_MIN || note > Note.NOTE_MAX){
                System.out.println("[ATTENTION] La note fournie est incorrecte. Suppression de celle-ci.");
                return new Film(titre,synopsis,duree,annee,realisateur,image,visionne);
            }
            return new Film(titre,synopsis,duree,annee,realisateur,image,note,visionne);
        }
        catch (NumberFormatException e){
            System.out.println("[ERREUR] Videotheque.creerFilm - un des champs numérique est incorrecte.\n\n");
        }
        catch (Exception e){
            System.out.println(ligne+"\n");
            if(e.getMessage().equals("taille")) System.out.println("[ERREUR] Videotheque.creerFilm - le nombre de champs est incorrecte.\n\n");
            if(e.getMessage().equals("formatImage")) System.out.println("[ERREUR] Videotheque.creerFilm - le format de l'image n'est pas jpeg, jpg, bmp ou png.\n\n");
            else{
                System.out.println("[ERREUR] Videotheque.creerFilm - erreur inconnue :\n");
                e.printStackTrace();
                System.out.println("\n\n");
            }

        }
        return null;
    }

    /**
     * Envoi la liste des Films de l'ArrayList dans la base
     */
    public void exportBase(){
        try {
            PrintWriter trace = new PrintWriter(new BufferedWriter(new FileWriter(this.fichier)));
            for (Film film : this.filmsTous) {
                trace.write(film.filmsToBase());
            }
            trace.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //###########################
    //###### STATICS UTILITAIRES
    //###########################

    /**
     * Vérifie si l'extention du fichier fourni est correcte (fichier image)
     * @param fichier : nom du fichier
     * @return : booleen - vrai si le fichier est une image
     */
    public static boolean verifExtFichier(String fichier){
        String[] extension = fichier.split("\\.");
        ArrayList<String> validExt= new ArrayList<>();
        validExt.add("jpeg");
        validExt.add("jpg");
        validExt.add("bmp");
        validExt.add("png");
        validExt.add("gif");
        return validExt.contains(extension[extension.length-1]);
    }



}
