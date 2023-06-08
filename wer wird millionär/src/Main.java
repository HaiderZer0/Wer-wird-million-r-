import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static Random rand = new Random();
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> fragen = new ArrayList<>();
        ArrayList<String> antworten = new ArrayList<>();
        ArrayList<String> optionen = new ArrayList<>();
        int modus;
        char wiederholen = 'y';

        /*Stufe 1: 0 - 2
          Stufe 2: 3 - 5
          Stufe 3: 6 - 8
          Stufe 4: 9 - 11
          Stufe 5: 12 - 14
          random.nextInt((max-min)+1) + min;
         */

        //Datei lesen. Die Datei Pfad in Code ändern
        File file = new File("C:\\Users\\walee\\Downloads\\fragen_projekt_Optimiert\\Fragen-mit Antworten.txt");
        Scanner sc = new Scanner(file);

        //Alle Fragen und Antworten in einem Array speichern
        for(int i = 0; i < 30; i++){
            if(fragen.size() == 15){antworten.add(sc.nextLine());}
            else{fragen.add(sc.nextLine());}
        }

        //Modus auswählen
        do {
            System.out.print("Modus Auswählen: \n1) Zocker-Variante\n2) Sicherheit-Variante\n>>> ");
            modus = Tastatur.intInput();
            if (modus == 1){
                System.out.println("\n-------------------------Zocker-Variante aktiviert!-------------------------\n");
                wiederholen = Varianten(antworten, fragen, optionen, true, modus);
            }
            if (modus == 2){
                System.out.println("\n-------------------------Sicherheit-Variante aktiviert!-------------------------\n");
                wiederholen = Varianten(antworten, fragen, optionen, false, modus);
            }
            if(wiederholen == 'n'){
                System.out.println("\n                                                          ---------------------------------------------------------------");
                System.out.println("                                                          |  "+"\u001B[34m"+"Ich danke Ihnen, dass Sie dieses Spiel ausprobiert haben!"+"\u001B[0m"+"  |");
                System.out.println("                                                          ---------------------------------------------------------------");
                break;
            }
            if((wiederholen == 'y')&&(modus != 1)&&(modus != 2)){
                System.out.println("\nBitte 1 oder 2 eingeben!\n");
            }
        }while((modus != 1)||(modus != 2));


    }

    //Die Varianten Methode ist für die Ausführung jeweiliger Variante zuständig
    public static char Varianten(ArrayList<String> antworten, ArrayList<String> fragen, ArrayList<String> optionen, boolean joker, int modus){
        int eingabe, antwort, stufe = 1, ehemaligeStufe;
        int[] geld = {0 , 100, 200, 300, 500, 1000};
        char wiederholen;
        while(true){
            System.out.println("|         Stufe: "+ stufe + "        |          Kontostand: "+ "\u001B[35m" + geld[stufe - 1] + "\u001B[0m" + "         |                [Taste = 5] Joker: " + joker + "           |");
            System.out.println("Frage für " + geld[stufe] + " € :");

            antwort = Question_N_Option_Selector(antworten, fragen, optionen, FrageNumGenerator(stufe));

            //System.out.println("Antwort: " + antwort);
            System.out.print("Bitte Ziffer eingeben: ");
            eingabe = Tastatur.intInput();
            ehemaligeStufe = stufe;

            //Bei der Eingabe von JOKER
            if ((eingabe == 5)&&(joker)){
                joker = false;
                Joker(antwort,optionen);
                System.out.print("Joker-Eingabe: ");
                eingabe = Tastatur.intInput();
            }
            stufe = Checker(eingabe, antwort, stufe, modus);

            //Bei einer Wiederholung des ganzen des Spiels
            if(ehemaligeStufe == stufe){
                System.out.print("Wollen Sie das Spiel wiederholen?(y/n) ");
                wiederholen = Tastatur.charInput();
                return wiederholen;
            }
        }
    }

    //Für das Generieren einer zufälligen Fragenummer nach Stufe
    public static int FrageNumGenerator(int stufe){
        return switch (stufe) {
            case 1 -> rand.nextInt(3);
            case 2 -> rand.nextInt(3) + 3;
            case 3 -> rand.nextInt(3) + 6;
            case 4 -> rand.nextInt(3) + 9;
            case 5 -> rand.nextInt(3) + 12;
            default -> 0;
        };
    }

    //Für die Selection und Ausgabe aller Optionen und möglichen Fragen
    public static int Question_N_Option_Selector(ArrayList<String> antworten, ArrayList<String> fragen, ArrayList<String> optionen, int frageNum){
        int antwort, temp;
        optionen.clear();

        //Andere falsche Antwortmöglichkeiten in den Optionen speichern
        for(int i = 0; i < 4; i++){
            temp = rand.nextInt(15);

            //verhindert eine Wiederholung der Optionen
            while(optionen.contains(antworten.get(temp))){
                temp = rand.nextInt(15);
            }
            optionen.add(antworten.get(temp));
        }

        //Richtige Antwort in den Optionen zufällig speichern
        if(!(optionen.contains(antworten.get(frageNum)))){
            temp = rand.nextInt(4);
            optionen.set(temp, antworten.get(frageNum));
            antwort = temp + 1;
        }
        else{
            antwort = optionen.indexOf(antworten.get(frageNum)) + 1;
        }

        //Die entsprechende zufällige Frage ausgeben
        System.out.println(fragen.get(frageNum) + "\n");

        //Alle entsprechende Optionen ausgeben
        for(int i = 0; i < 4; i++){
            System.out.println((i + 1) + ") " + optionen.get(i));
        }
        System.out.println();
        return antwort;
    }

    //Fifty-fifty Joker löscht zwei falsche Optionen
    public static void Joker(int antwort, ArrayList<String> optionen){
        int temp, count = 0;
        while(count != 2){
            temp = rand.nextInt(4);
            if((temp != antwort - 1)&&!(Objects.equals(optionen.get(temp), ""))){
                count++;
                optionen.set(temp, "");
            }
        }
        //Ausgabe aller JokerOptionen
        for(int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ") " + optionen.get(i));
        }
    }

    //überprüft die Richtigkeit der Eingabe und gibt die entsprechende Aussage aus
    public static int Checker(int eingabe, int antwort, int stufe, int modus) {
        int ehemaligeStufe = stufe;
        if (eingabe == antwort) {
            System.out.println("\u001B[32m" + "Ihre Antwort ist richtig!" + "\u001B[0m");
            stufe++;
            if(stufe > 5){
                System.out.println("\u001B[32m" + "Gratulation! Sie haben 1000 € gewonnen!" + "\u001B[0m");
                stufe = ehemaligeStufe;
            }
        }
        else{
            System.out.println("\u001B[31m" + "Ihre Antwort ist leider falsch!" + "\u001B[0m");

            //für die Sicherheit-Variante
            if((modus == 2)&&(stufe > 3)){
                System.out.println("\u001B[34m" + "Sie haben 300 € gewonnen!" + "\u001B[0m");
            }
            else{
                System.out.println("Sie haben " + "\u001B[31m" + "0 € "+ "\u001B[0m" +"gewonnen!");
            }
        }
        return stufe;
    }
}

//Ende