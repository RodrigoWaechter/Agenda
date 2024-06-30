package com.projeto.agenda.database.tipo.services;
import java.util.ArrayList;
import java.util.List;

import com.projeto.agenda.database.tipo.entity.Tipo;

public class TipoDatabase {
    private List<Tipo> tipos;

    public TipoDatabase() {
        tipos = new ArrayList<>();

        tipos.add(new Tipo(1, "Tipo A", 100.0f));
        tipos.add(new Tipo(2, "Tipo B", 150.0f));
        tipos.add(new Tipo(3, "Tipo C", 200.0f));
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

	
}