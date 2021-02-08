/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class NitSat extends Thread{
    JLabel jLabel;

    public NitSat(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh.mm.ss");
        while (true) {            
            jLabel.setText(sdf.format(new Date()));
        }
    }
    
    
}
