package Controller;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class CavalosController extends Thread {
    
    private final int cavaleiro;    
    
    private static final Semaphore semaforoTocha = new Semaphore(1);
    private static final Semaphore semaforoPedra = new Semaphore(1);
    private static final Semaphore semaforoPorta = new Semaphore(1);
    
    private static final Random rnd = new Random();

    private int velocidade;
    private static int saida = rnd.nextInt(3);
    
    private static boolean temTocha = false;
    private static boolean temPedra = false;
    
    public CavalosController(int cavaleiro, int velocidade){
        
        this.cavaleiro = cavaleiro;
        this.velocidade = 2 + rnd.nextInt(3);
        
    }
    
    @Override
    public void run(){
        
        cavaleiroAndando();
        pegaTocha();
        pegaPedra();
        escolhePorta();
                
    }
    
    private void cavaleiroAndando(){
        
        int distPercorrida = 0;
        int distanciaTocha = 500;
        int distanciaPedra = 1500;
        int distanciaPorta = 2000;
        
        int deslocamento = velocidade;
        int tempo = 50;
        
        while(distPercorrida < distanciaPorta){
            
            distPercorrida += deslocamento;
            
            try {
                                
                sleep(tempo);
                
            } catch (InterruptedException e){                
                
                System.err.println(e.getMessage());
            }
            
            System.out.println("O cavaleiro " + cavaleiro + " andou " + distPercorrida + "m.");
            
            if(distPercorrida == distanciaTocha){
                
                pegaTocha();
                
            } else if (distPercorrida == distanciaPedra){
                
                pegaPedra();
            }
        }
        
        escolhePorta();
    }
    
    private void pegaTocha(){
        
        if(!temTocha && semaforoTocha.tryAcquire(1)){
               
            temTocha = true;
            velocidade += 2;
               
            System.out.println("O cavaleiro " + cavaleiro + " pegou a tocha e aumentou sua velocidade para " + velocidade);
                      
        }
        
        semaforoTocha.release();
    }
    
    private void pegaPedra(){
        
        if(!temPedra && semaforoPedra.tryAcquire()){
                            
            temPedra = true;
            velocidade += 2;
                
            System.out.println("O cavaleiro " + cavaleiro + " pegou a pedra e aumentou sua velocidade para " + velocidade);
          
        }
        
        semaforoPedra.release();
    }
    
    private void escolhePorta(){                        
        
        try{
            
            semaforoPorta.acquire(1);  
            int portaEscolhida = rnd.nextInt(3);
            
            if(portaEscolhida == saida){
                
                System.out.println("O cavaleiro " + cavaleiro + " escapou!");
                
            } else {
                
               System.out.println("O cavaleiro " + cavaleiro + " foi morto pelo monstro :("); 
            }
            
        } catch (Exception e){
                        
            System.err.println(e.getMessage());
            
        } finally {
            
            semaforoPorta.release();
        }                
    }    
}
