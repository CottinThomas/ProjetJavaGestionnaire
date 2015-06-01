package Gestionnaire.Model;

/**
 * PROJET DE JAVA - GESTIONNAIRE DE FILMS
 * Auteur : Thomas COTTIN
 *
 *      Classe modélisant une Note - utilisée pour vérifications
 */
public class Note {

    //###########################
    //###### CONSTANTES
    //###########################

    public static final int NOTE_MIN = 0;
    public static final int NOTE_MAX = 20;



    //###########################
    //###### ATTRIBUTS
    //###########################

    private int note;



    //###########################
    //###### CONSTRUCTEUR
    //###########################

    public Note(int note){
        if (verifNote(note)){
            this.note=note;
        }
    }



    //###########################
    //###### CONTROLE DES DONNEES
    //###########################

    public boolean verifNote(int note){
        return (note>=NOTE_MIN && note <= NOTE_MAX);
    }



    //###########################
    //###### MODIFIEUR
    //###########################

    public boolean setNote(int note){
        if (!verifNote(note)) return false;
        this.note=note;
        return true;
    }



    //###########################
    //###### ACCESSEUR
    //###########################

    public int getNote(){
        return this.note;
    }

}
