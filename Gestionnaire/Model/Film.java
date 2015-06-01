package Gestionnaire.Model;

import java.time.Duration;
import java.util.Comparator;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe modélisant un Film
 */
public class Film {

    //###########################
    //###### ATTRIBUTS
    //###########################

    private String titreFilm;
    private String synopsis;
    private Duration duree;
    private int anneeSortie;
    private String realisateur;
    private String affiche;
    private Note note;
    private boolean visionne;



    //###########################
    //###### CONSTRUCTEURS
    //###########################

    /**
     * Constructeur de la classe : construction d'un film avec sa note
     * @param titreFilm : String - titre du film
     * @param synopsis : String - synopsis du film
     * @param duree : Duration - durée en minutes d'un film
     * @param anneeSortie : int - année de sortie du film
     * @param realisateur : String - réalisateur du film
     * @param affiche : String - nom de l'affiche (contenue dans le dossier './affiches'
     * @param note : Note - note attribuée au film
     * @param visionne : booleen - défini si un film à été visionné ou non
     */
    public Film(String titreFilm, String synopsis, Duration duree, int anneeSortie, String realisateur, String affiche, int note ,boolean visionne){
        this.titreFilm = titreFilm;
        this.synopsis = synopsis;
        this.duree = duree;
        this.anneeSortie = anneeSortie;
        this.realisateur = realisateur;
        this.affiche = affiche;
        this.note=new Note(note);
        this.visionne=visionne;
    }

    /**
     * Constructeur de la classe : construction d'un film sans note
     * @param titreFilm : String - titre du film
     * @param synopsis : String - synopsis du film
     * @param duree : Duration - durée en minutes d'un film
     * @param anneeSortie : int - année de sortie du film
     * @param realisateur : String - réalisateur du film
     * @param affiche : String - nom de l'affiche (contenue dans le dossier './affiches'
     * @param visionne : booleen - défini si un film à été visionné ou non
     */
    public Film(String titreFilm, String synopsis, Duration duree, int anneeSortie, String realisateur, String affiche, boolean visionne){
        this.titreFilm = titreFilm;
        this.synopsis = synopsis;
        this.duree = duree;
        this.anneeSortie = anneeSortie;
        this.realisateur = realisateur;
        this.affiche =affiche;
        this.note=null;
        this.visionne=visionne;
    }



    //###########################
    //###### MODIFIEURS
    //###########################

    /**
     * Défini le film cible comme étant visionné
     */
    public void setVisionne(){
        this.visionne=true;
    }

    /**
     * Défini le film cible comme n'étant pas visionné
     */
    public void setNonVisionne(){
        this.visionne=false;
    }

    public boolean estVisionne(){
        return this.visionne;
    }

    /**
     * Attribu la note passée en paramètre au Film cible
     * @return : booléen - Retourne vrai si l'ajout de la note à réussi.
     */
    public boolean setNote(int note){
        if(this.possedeNote()) return this.note.setNote(note);
        this.note=new Note(note);
        return this.note.getNote()==note;
    }

    public void supprimerNote(){
        this.note=null;
    }



    //###########################
    //###### ACCESSEURS
    //###########################

    /**
     * Informe si le film est déja noté ou non
     * @return : booléen - Vrai si le film possède une note, faux sinon
     */
    public boolean possedeNote(){
        return this.note!=null;
    }

    /**
     * Renvoi la note attribué au Film s'il en possède une, -1 sinon.
     * @return : int - note du film
     */
    public  int getNote(){
        if(this.possedeNote())
            return this.note.getNote();
        return -1;
    }

    /**
     * Retourne le titre du Film cible
     * @return : String - titre du film
     */
    public String getTitre(){
        return titreFilm;
    }

    /**
     * Retourne le nom du fichier de l'affiche du Film cible
     * @return : String - nom du fichier d'affiche
     */
    public String getImage(){
        return this.affiche;
    }

    /**
     * Retourne les informations du Film cible au format HTML
     * @return : String - Informations sur le film au format HTML
     */
    public String getInfoHTML(){
        String s = "";
        s+="<html>";
        s+="Titre : <u>"+titreFilm+ "</u><br/>";
        if(note!=null){
            s+="<big>Note : "+note.getNote()+"</big><br/>";
        }
        s+="Année : "+anneeSortie+ "<br/>";
        if(duree.toMinutes()!=301) s+="Durée : "+duree.toMinutes()+ " minutes <br/>";
        else s+="Durée : > 300 minutes<br/>";
        s+="Réalisateur : "+realisateur+ "<br/><br/>";
        s+="Synopsis : <br/>"+synopsis.replaceAll("\n","<br/>")+ "<br/>";
        s+="</html>";

        return s;
    }

    /**
     * Retourne les informations concernant le film au format de la base
     * @return : String - informations formatées pour le stockage dans le fichier
     */
    public String filmsToBase() {
        if(note==null) return this.titreFilm+"\t"+this.synopsis+"\t"+this.duree.toMinutes()+"\t"+this.anneeSortie+"\t"+this.realisateur+"\t"+this.affiche+"\t"+"null"+"\t"+this.visionne+"\n";
        return this.titreFilm+"\t"+this.synopsis+"\t"+this.duree.toMinutes()+"\t"+this.anneeSortie+"\t"+this.realisateur+"\t"+this.affiche+"\t"+this.note.getNote()+"\t"+this.visionne+"\n";
    }



    //###########################
    //###### COMPARATEURS
    //###########################

    /**
     * Défini une comparaison entre Films sur la base de leur titre
     * @return : Comparator<Film>
     */
    static Comparator<Film> compareByNom(){
        return new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return o1.titreFilm.compareToIgnoreCase(o2.titreFilm);
            }
        };
    }

    /**
     * Défini une comparaison entre Films sur la base de leur date
     * @return : Comparator<Film>
     */
    static Comparator<Film> compareByDate(){
        return new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                if (o1.anneeSortie==o2.anneeSortie) return 0;
                else if (o1.anneeSortie<o2.anneeSortie) return -1;
                else return 1;
            }
        };
    }

    /**
     * Défini une comparaison entre Films sur la base de leur note
     * @return : Comparator<Film>
     */
    static Comparator<Film> compareByNote(){
        return new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                if ((!o1.possedeNote()&& !o2.possedeNote()) || (!o2.possedeNote() && o1.possedeNote())) return -1;
                if (o2.possedeNote() && !o1.possedeNote()) return 1;
                if (o1.note==o2.note) return 0;
                else if (o1.note.getNote()>o2.note.getNote()) return -1;
                else return 1;
            }
        };
    }



    //###########################
    //###### REDEFINITIONS
    //###########################

    /**
     * Redéfinition de equals : comparaison entre deux Films
     * @param o : Film - Film a comparer au Film cible
     * @return : boolean - vrai si les deux Films sont identiques, faux sinon
     */
    @Override
    public boolean equals(Object o){
        return (
                (o instanceof  Film) &&
                        ((Film) o).titreFilm.compareToIgnoreCase(this.titreFilm)==0 &&
                        ((Film) o).synopsis.compareToIgnoreCase(this.synopsis)==0&&
                        ((Film) o).duree==this.duree &&
                        ((Film) o).anneeSortie==this.anneeSortie&&
                        ((Film) o).realisateur.compareToIgnoreCase(this.realisateur)==0&&
                        ((Film) o).affiche.compareToIgnoreCase(this.affiche)==0
        );
    }
}
