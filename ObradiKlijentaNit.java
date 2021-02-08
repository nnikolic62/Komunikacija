/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import forme.FrmServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.KlijentskiZahtev;
import komunikacija.ServerskiOdgovor;
import kontroler.Kontroler;

/**
 *
 * @author User
 */
public class ObradiKlijentaNit extends Thread{
    Socket s;
    FrmServer frmServer;

    public ObradiKlijentaNit(Socket s, FrmServer frmServer) {
        this.s = s;
        this.frmServer = frmServer;
    }

    @Override
    public void run() {
        while (true) {            
            KlijentskiZahtev kz = primiZahtev();
            ServerskiOdgovor so = new ServerskiOdgovor();
            
            switch(kz.getOperacija()){
                case ULOGUJ_SE:
                    String korisnik = kz.getUsername();
                    if(Kontroler.getInstance().daLiJeOvlascen(korisnik)){
                        if(!Kontroler.getInstance().daLiJeUlogovan(korisnik)){
                            Kontroler.getInstance().dodajUlogovanog(korisnik);
                            so.setPoruka("Dobrodosli");
                            so.setUspesno(true);
                        } else{
                            so.setPoruka("Korisnik je vec ulogovan");
                            so.setUspesno(false);
                        }
                    } else{
                        so.setPoruka("Nemas ovlascenje");
                        so.setUspesno(false);
                    }
                    posaljiOdgovor(so, s);
                    break;
                case IZBACI:
                    int pozicija = kz.getPozicija();
                    String ime = kz.getUsername();
                    String poruka = frmServer.odradiPosao(pozicija, ime);
                    so.setPoruka(poruka);
                    List<Socket> list = Kontroler.getInstance().vratiKorisnike();
                    for (Socket socket : list) {
                        posaljiOdgovor(so, socket);
                    }
                    break;
            }
        }
    }
    
    private KlijentskiZahtev primiZahtev() {
        try {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so, Socket socket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}