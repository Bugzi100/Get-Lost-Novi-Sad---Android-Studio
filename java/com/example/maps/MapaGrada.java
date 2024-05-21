package com.example.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapaGrada {

    HashMap<String, HashMap<String, ArrayList<String>>> kvartovi = new HashMap<>();
    public MapaGrada(HashMap<String, HashMap<String, ArrayList<String>>> kvartovi) {
        this.kvartovi = new HashMap<>();
        defaultPodesavanja();
    }

    private void defaultPodesavanja() {
        dodajKvartSaZnamenitostima("Liman", "Bulevar cara Lazara", new String[]{"Naučno-tehnološki park", ""});
        dodajKvartSaZnamenitostima("Liman", "Most slobode", new String[]{"Štrand"});
        dodajKvartSaZnamenitostima("Liman", "Narodnog fronta", new String[]{"Room Escape Novi Sad"});
        dodajKvartSaZnamenitostima("Liman", "Bulevar despota Stefana", new String[]{"Limanski park"});
        dodajKvartSaZnamenitostima("Liman", "Sunčani kej", new String[]{"Teniski teren", "Košarkaški teren", "Teretanu na otvorenom", "Odbojkaški teren"});

        dodajKvartSaZnamenitostima("Stari grad", "Bulevar oslobođenja", new String[]{"Tržni centar 'Promenada'"});
        dodajKvartSaZnamenitostima("Stari grad", "Dunavska", new String[]{"Dunavski park", "Gradsku biblioteku", "Muzej savremene umetnosti Vojvodine"});
        dodajKvartSaZnamenitostima("Stari grad", "Trg slobode", new String[]{"Gradsku kuću", "Spomenik Svetozaru Mileticu", "Crkvu Imena Marijinog (Katedrala)"});
        dodajKvartSaZnamenitostima("Stari grad", "Zmaj Jovina", new String[]{"Vladičanski dvor", "Spomenik Jovanu Jovanoviću Zmaju"});
        dodajKvartSaZnamenitostima("Stari grad", "Bulevar Mihajla Pupina", new String[]{"Zgradu Pokrajinske vlade AP Vojvodine"});
        dodajKvartSaZnamenitostima("Stari grad", "Matice srpske", new String[]{"Matica srpsku"});
        dodajKvartSaZnamenitostima("Stari grad", "Jevrejska", new String[]{"Sinagogu"});
        dodajKvartSaZnamenitostima("Stari grad", "Pozorišni trg", new String[]{"Srpsko narodno pozorište - Novi Sad"});

        dodajKvartSaZnamenitostima("Grbavica", "Futoška", new String[]{"Vojvođanku", "Futoški park"});
        dodajKvartSaZnamenitostima("Grbavica", "Braće Ribnikar", new String[]{"Park Grbavica"});
        dodajKvartSaZnamenitostima("Grbavica", "Vojvode Knićanina", new String[]{"Park kod Srednje medicinske škole"});

        dodajKvartSaZnamenitostima("Petrovaradin", "Preradovićeva", new String[]{"Vojvodinašume"});
        dodajKvartSaZnamenitostima("Petrovaradin", "Tvrđava BB", new String[]{"Muzej grada Novog Sada", "Sat na Petrovaradinskoj tvrđavi", "Petrovaradinske katakombe"});
        dodajKvartSaZnamenitostima("Petrovaradin", "Beogradska", new String[]{"Beogradsku kapiju"});

        dodajKvartSaZnamenitostima("Sajmište", "Novosadskog sajma", new String[]{"Mural Svetozara Miletića"});
        dodajKvartSaZnamenitostima("Sajmište", "Hajduk Veljkova", new String[]{"Novosadski sajam", ""});

        dodajKvartSaZnamenitostima("Detelinara", "Bulevar Evrope", new String[]{"Košarkaški teren"});
        dodajKvartSaZnamenitostima("Detelinara", "Kornelija Stankovića", new String[]{"Crkva rođenja Svetog Jovana Krstitelja"});

        dodajKvartSaZnamenitostima("Telep", "Feješ Klare", new String[]{"Srpska pravoslavna crkva Svetih Kirila i Metodija"});
        dodajKvartSaZnamenitostima("Telep", "Podunavska", new String[]{"Šodroš"});
        dodajKvartSaZnamenitostima("Telep", "Ćirila i Metodija", new String[]{"Adrenalin park 'Zemlja čuda' "});

        dodajKvartSaZnamenitostima("Podbara", "Temerinska", new String[]{"Temerinsku pijacu"});
        dodajKvartSaZnamenitostima("Podbara", "Venizelosova", new String[]{"Kvantašku pijacu"});
        dodajKvartSaZnamenitostima("Podbara", "Beogradski kej", new String[]{"Spomenik Uzeiru Hadžibejliju", ""});
        dodajKvartSaZnamenitostima("Podbara", "Nikole Pasica", new String[]{"Sabornu crkvu Svetog Đorđa"});

        dodajKvartSaZnamenitostima("Rotkvarija", "Jovana Subotića", new String[]{"Novosadsko Pozorište"});
        dodajKvartSaZnamenitostima("Rotkvarija", "Kraljevića Marka", new String[]{"Kraljev park"});
        dodajKvartSaZnamenitostima("Rotkvarija", "Kisačka", new String[]{"Kuću Mileve Marić - Ajnštajn"});

        dodajKvartSaZnamenitostima("Novo naselje", "Radomira Raše Radujkova", new String[]{"Srpski pravoslavni hram Prenosa moštiju Svetog Save", "Novi park"});
        dodajKvartSaZnamenitostima("Novo naselje", "Stevana Hladnog", new String[]{"Satelitska pijaca"});
    }

    public void dodajKvart(String nazivKvarta) {
        kvartovi.putIfAbsent(nazivKvarta, new HashMap<>());
    }

    public void dodajKvartSaZnamenitostima(String kvart, String ulica, String[] znamenitosti) {
        kvartovi.putIfAbsent(kvart, new HashMap<>());
        kvartovi.get(kvart).put(ulica, new ArrayList<>(List.of(znamenitosti)));
    }

    public void dodajZnamenitost(String kvart, String ulica, String znamenitost) {
        if (kvartovi.containsKey(kvart) && kvartovi.get(kvart).containsKey(ulica)) {
            kvartovi.get(kvart).get(ulica).add(kvart);
        } else {
            System.err.println("Ulica ili kvart ne postoje!");
        }
    }

    public List<String> getZnamenitosti(String kvart, String ulica) {
        if (kvartovi.containsKey(kvart) && kvartovi.get(kvart).containsKey(ulica)) {
            return kvartovi.get(kvart).get(ulica);
        } else {
            System.err.println("Ulica ili kvart ne postoje!");
            return new ArrayList<>();
        }
    }

    public Map<String, HashMap<String, ArrayList<String>>> getKvartovi() {
        return kvartovi;
    }

}
