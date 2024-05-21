package com.example.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zadatak {

    private List<String> obicniZadaci;
    private List<String> zanimljiviZadaci;
    public Zadatak(List<Zadatak> obicniZadaci, List<Zadatak> zanimljiviZadaci) {
        this.obicniZadaci = new ArrayList<>(Arrays.asList(
                "Poseti restoran i probaj tradicionalnu srpsku hranu",
                "Poseti kafić i probaj neko od srpskih pića",
                "Poseti pijacu",
                "Poseti market i kupi srpski slatkiš/grickalice",
                "Provozaj se taksijem",
                "Poseti pekaru i kupi burek",
                "Poseti butik",
                "Provozaj se autobusom"
                ));
        this.zanimljiviZadaci = new ArrayList<>(Arrays.asList(
                "Prisustvuj uličnoj svirci",
                "Pronađi nekoga ko liči na tebe i zamoli ih da se slikate",
                "Pronađi nekoga u odelu ko vozi trotinet",
                "Pronađi osobu sa bradom od minimalno 30cm i zamoli ih da se slikate",
                "Pronađi emitovanje uživo (TV, Youtube...)",
                "Učestvuj u uličnoj igri (šah, karte, boćanje...)",
                "Okupi tri nasumična prolaznika i napravi grupni selfi sa njima (ne smeju se poznavati međusobno)",
                "Pronađi osobu koja nosi tradicionalnu nošnju i zamoli ih da se slikate"));
    }

    public List<String> getObicniZadaci() {
        return obicniZadaci;
    }

    public List<String> getZanimljiviZadaci() {
        return zanimljiviZadaci;
    }
}
