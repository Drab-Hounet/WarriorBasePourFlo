package warriors.client.console;

import java.util.Scanner;

import warriors.contracts.*;
import warriors.contracts.IGameState;
import warriors.engine.Warriors;

// NE PAS MODIFIER CETTE CLASSE !!
public class ClientConsole {

    private static String MENU_COMMENCER_PARTIE = "1";
    private static String MENU_QUITTER = "2";

    public static void main(String[] args) {
        IWarriorsAPI warriors = new Warriors();
        Scanner sc = new Scanner(System.in);
        String menuChoice = "";
        do {
            menuChoice = displayMenu(sc);
            if (menuChoice.equals(MENU_COMMENCER_PARTIE)) {
                startGame(warriors, sc);
            }
        } while (!menuChoice.equals(MENU_QUITTER));
        sc.close();
        System.out.println("à bientôt");
    }

    private static void startGame(IWarriorsAPI warriors, Scanner sc) {
        System.out.println();
        System.out.println("Entrez votre nom:");
        String playerName = sc.nextLine();

        System.out.println("Choisissez votre héro:");
        for (int i = 0; i < warriors.getHeroes().size(); i++) {
            IHero heroe = warriors.getHeroes().get(i);
            System.out.println(i + 1 + " - " + heroe.getName());
            System.out.println("    Force d'attaque : " + heroe.getAttackLevel());
            System.out.println("    Niveau de vie : " + heroe.getLife());
        }
        IHero chosenHeroe = warriors.getHeroes().get(Integer.parseInt(sc.nextLine()) - 1);

        System.out.println("Choisissez votre map:");
        for (int i = 0; i < warriors.getMaps().size(); i++) {
            IMap map = warriors.getMaps().get(i);
            System.out.println(i + 1 + " - " + map.getName());
        }
        IMap choosenMap = warriors.getMaps().get(Integer.parseInt(sc.nextLine()) - 1);

        IGameState gameState = warriors.createGame(playerName, chosenHeroe, choosenMap);
        String gameId = gameState.getGameId();
        while (gameState.getGameStatus() == GameStatus.IN_PROGRESS) {
            System.out.println(gameState.getLastLog());
            System.out.println("\nAppuyer sur une touche numérique pour lancer le dé");
            if (sc.hasNext()) {
                sc.nextLine();
                gameState = warriors.nextTurn(gameId);
            }
        }

        System.out.println(gameState.getLastLog());
    }

    private static String displayMenu(Scanner sc) {
        System.out.println();
        System.out.println("================== Java Warriors ==================");
        System.out.println("1 - Commencer une partie");
        System.out.println("2 - Quitter");
        if (sc.hasNext()) {
            String choice = sc.nextLine();
            return choice;
        }

        return "";
    }
}

