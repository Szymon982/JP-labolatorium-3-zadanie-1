package com.company;

import java.io.*;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    static String st;
    //static ArrayList<String> produkty = new ArrayList<String>();
    static ArrayList<String> koszyk = new ArrayList<String>();
    static String[] Dane;
    static ArrayList<String[]> Pelne_dane = new ArrayList<String[]>();
    static ArrayList<produkt> Lista_produktow = new ArrayList<produkt>();

    public static void main(String[] args) throws IOException {
        System.out.println("Autor: Szymon Borzdyński");
        File plik = new File("C:\\Users\\Dom\\Desktop\\JEDEN.txt");
        BufferedReader buf = new BufferedReader(new FileReader(plik));
        while ((st = buf.readLine()) != null){
            Dane = st.split("-");
            //produkty.add(Dane[1]);
            Lista_produktow.add(new produkt(Dane[0],Dane[1],Integer.parseInt(Dane[2])));
        }
        // testy funkcji
        System.out.println(czy_dostepny("maslo"));
        dodaj_do_koszyka("chleb");
        dodaj_do_koszyka("woda");
        dodaj_do_koszyka("gruszka");
        wyrzuc_z_koszyka("woda");
        wycen_koszyk();
        realizacja_zamowienia(plik);
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

    public static void wycen_koszyk() {
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
        System.out.print("Zaktualizowano baze produktów");
    }
}
