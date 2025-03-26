package View;

import Controller.CavalosController;

public class CavalosPrincipal {
    
    public static void main (String args[]){
        
        int velocidade = 0;
            
        for(int cavaleiro = 0; cavaleiro < 4; cavaleiro++){
            
            CavalosController cc = new CavalosController(cavaleiro, velocidade);
            
            cc.start();
        }
    }
}
