package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientCLI {
    private final Scanner stdin;
    private String nickname;
    //private Board boardState;
    public ClientCLI(){
        this.stdin = new Scanner(System.in);
    }

    public void setup(){
        System.out.println("Game is starting...");
    }

    public String getIP(){
        System.out.println("Scrivi ip address");
        return stdin.next();
    }

    public int getPort(){
        System.out.println("Scrivi porta");
        return stdin.nextInt();
    }

    public String getNickname(){
        System.out.println("Scrivi il tuo nickname");
        this.nickname = stdin.next();
        return nickname;
    }

    public int getGameSize(){
        System.out.println("Insert player amount:");
        //TODO: Aggiungere controllo ciclo infinito
        return stdin.nextInt();
    }

    public Map<ResourceType, Integer> getInitialResources(int numPlayer) {
        Map<ResourceType, Integer> res = new HashMap<>();
        switch (numPlayer){
            case 1:
            case 2:
                System.out.println("Scegli una risorsa a scelta");
                int numRes = viewInitialResources();
                res.put(parseToResourceType(numRes),1);
                break;
            case 3:
                System.out.println("Scegli due una risorse a scelta");
                int res1 = viewInitialResources();
                int res2 = viewInitialResources();
                res.put(parseToResourceType(res1),1);
                if(res1==res2) res.put(parseToResourceType(res2),2);
                else res.put(parseToResourceType(res2),1);
                break;
            default:
                System.out.println("ko");
                break;
        }
        return res;
    }

    public int viewInitialResources(){
        System.out.println("1) STONE");
        System.out.println("2) COIN");
        System.out.println("3) SHIELD");
        System.out.println("4) SERVANT");
        return stdin.nextInt();
    }

    public void viewInitialsLeadersCars(List<LeaderCard> leaderCards){
        System.out.println("Sono arrivati "+leaderCards.size()+" carte ma non so ancora come fartele vedere");
        System.out.println("Intanto scegline 2");
    }

    public int getInitialLeaderCards(){
        System.out.println("Scegli una carta");
        System.out.println("0) La prima");
        System.out.println("1) La seconda");
        System.out.println("2) La terza");
        System.out.println("3) La quarta");
        return stdin.nextInt();
    }

    public ResourceType parseToResourceType(int choice){
        switch (choice){
            case 1: return ResourceType.STONE;
            case 2: return ResourceType.COIN;
            case 3: return ResourceType.SHIELD;
            case 4: return ResourceType.SERVANT;
            default: break;
        }
        return null;
    }

    public String simulateGame() {
        System.out.println("Simulazione del gioco");
        System.out.println("Premi un carattere e invio per passare il turno");
        return stdin.next();
    }
}
