/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Kontroler {
    private static Kontroler instance;
    List<String> moguciKorisnici = new ArrayList<>();
    String[] moguci = {"user1", "user2", "admin1", "admin2"};
    List<String> ulogovaniKorisnici = new ArrayList<>();
    List<Socket> korisnici = new ArrayList<>();

    public Kontroler() {
        for (String s : moguci) {
            moguciKorisnici.add(s);
        }
    }
    
    public static Kontroler getInstance(){
        if(instance == null)
            instance = new Kontroler();
        return instance;
    }
    
    public boolean daLiJeOvlascen(String korisnik){
        return moguciKorisnici.contains(korisnik);
    }
    
    public boolean daLiJeUlogovan(String korisnik){
        return ulogovaniKorisnici.contains(korisnik);
    }
    
    public void dodajUlogovanog(String korisnik){
        ulogovaniKorisnici.add(korisnik);
    }
    
    public void dodajKorisnika(Socket s){
        korisnici.add(s);
    }
    
    public List<Socket> vratiKorisnike(){
        return korisnici;
    }
}
