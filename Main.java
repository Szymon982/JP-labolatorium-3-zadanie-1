package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static String st;
    static ArrayList<String> koszyk = new ArrayList<>();
    static String[] Dane;
    static ArrayList<produkt> Lista_produktow = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Autor: Szymon Borzdyński");
        File plik = new File("C:\\Users\\Dom\\Desktop\\pliki do JP\\JEDEN.txt");
        BufferedReader buf = new BufferedReader(new FileReader(plik));
        while ((st = buf.readLine()) != null){
            Dane = st.split("-");
            Lista_produktow.add(new produkt(Dane[0],Dane[1],Integer.parseInt(Dane[2])));
        }
        // testy funkcji
        czy_dostepny("maslo");
        dodaj_do_koszyka("chleb");
        dodaj_do_koszyka("woda");
        dodaj_do_koszyka("gruszka");
        wyrzuc_z_koszyka("woda");
        wycenaZamowienie(koszyk);
        realizacja_zamowienia(plik);
        dodajArtykul("012","pomarancza",69,plik);
        pobierzArtykul("banan",plik);
        System.exit(69);
    }

    public static Boolean czy_dostepny(String produkt) {
        for (int i = 0; i < Lista_produktow.size(); i++) {
            if (Objects.equals(Lista_produktow.get(i).nazwa, produkt)) {
                System.out.println("Produkt '"+produkt+"' dostępny w sklepie");
                return true;
            }
        }
        System.out.println("Produkt niedostępny w sklepie");
        return false;
    }

    public static void dodaj_do_koszyka(String produkt) {
        if(czy_dostepny(produkt)){
            System.out.println("Nie można dodać do koszyka ponieważ produkt jest niedostępny w sklepie");
        }
        System.out.println("Dodano '"+produkt+"' do koszyka");
        koszyk.add(produkt);

    }

    public static void wyrzuc_z_koszyka(String produkt) {
        for (int i = 0; i < koszyk.size(); i++){
            if(Objects.equals(koszyk.get(i), produkt)){
                koszyk.remove(produkt);
                System.out.println("Wyrzucono '"+produkt+"' z koszyka");
                return;
            }
        }
        System.out.println("Nie można wyrzucić produktu z koszyka ponieważ produkt go tam nie ma");
    }

    public static void wycenaZamowienie(ArrayList<String> koszyk) {
        int cena = 0;
        for (int i = 0; i < Lista_produktow.size(); i++){
            for (int j = 0; j < koszyk.size(); j++) {
                if (Objects.equals(Lista_produktow.get(i).nazwa, koszyk.get(j))) {
                    cena = cena + Lista_produktow.get(i).cena;
                }
            }
        }
        System.out.println("Całkowity koszt koszyka: "+cena);
    }

    public static void zapisz_dane_do_pliku(File plik) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(plik));
        for (int i = 0; i < Lista_produktow.size(); i++) {
            writer.write(Lista_produktow.get(i).kod + "-" + Lista_produktow.get(i).nazwa + "-" + Lista_produktow.get(i).cena+"\n");
        }
        writer.close();
    }

    public static void realizacja_zamowienia(File plik) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(plik); //usuwa content pliku
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Lista_produktow.size(); i++){
            for (int j = 0; j < koszyk.size(); j++){
                if(Objects.equals(koszyk.get(j), Lista_produktow.get(i).nazwa)){
                    Lista_produktow.remove(i);
                }
            }
        }
        zapisz_dane_do_pliku(plik);
        System.out.println("Zamowienie zrealizowane");
        System.out.println("Zaktualizowano baze produktów");
    }

    public static void dodajArtykul(String kod,String nazwa,int cena,File plik) throws IOException {
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(plik,true));
        writer2.write(kod+"-"+nazwa+"-"+cena+"\n");
        writer2.close();
        System.out.println("Dodano produkt do sklepu: "+nazwa);
    }

    public static void pobierzArtykul(String nazwa,File plik) throws IOException {
        Scanner in;
        try {
            in = new Scanner(plik);
            while(in.hasNext())
            {
                String line=in.nextLine();
                if(line.contains(nazwa)){
                    System.out.println("Dane produktu: ");
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
            e.printStackTrace();
        }

    }
}
