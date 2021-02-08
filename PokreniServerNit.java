/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import forme.FrmServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;

/**
 *
 * @author User
 */
public class PokreniServerNit extends Thread{
    FrmServer frmServer;

    public PokreniServerNit(FrmServer frmServer) {
        this.frmServer = frmServer;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            System.out.println("Server je pokrenut");
            frmServer.formaPoslePokretanja();
            frmServer.srediTabelu();
            while (true) {                
                Socket s = ss.accept();
                System.out.println("Klijent se povezao");
                Kontroler.getInstance().dodajKorisnika(s);
                ObradiKlijentaNit okn = new ObradiKlijentaNit(s, frmServer);
                okn.start();
            }
        } catch (IOException ex) {
            frmServer.puklo();
            Logger.getLogger(PokreniServerNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
