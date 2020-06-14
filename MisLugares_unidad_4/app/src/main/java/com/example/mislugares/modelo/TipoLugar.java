package com.example.mislugares.modelo;
import com.example.mislugares.R;
public enum TipoLugar
{
    OTROS ("Otros", R.drawable.otros),
    RESTAURANTE ("Restaurante",R.drawable.restaurante),
    BAR ("Bar", R.drawable.bar),
    COPAS ("Copas",R.drawable.copas),
    ESPECTACULO ("Espectáculo", R.drawable.espectaculos),
    HOTEL ("Hotel", R.drawable.hotel),
    COMPRAS ("Compras", R.drawable.compras),
    EDUCACION ("Educación", R.drawable.educacion),
    DEPORTE ("Deporte", R.drawable.deporte),
    NATURALEZA ("Naturaleza", R.drawable.naturaleza),
    GASOLINERA ("Gasolinera", R.drawable.gasolinera);

    private final String texto;
    private final int recurso;

    TipoLugar(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public String getTexto() { return texto; }
    public int getRecurso() { return recurso; }

    /** Retorna un array de strings con el texto de cada elemento
     * del enum
     * @return Array de Strings
     */
    public static String[] getNombres()
    {
        //Array de strings con tantos strings como elementos del enum
        String[] resultado = new String[TipoLugar.values().length];
        for (TipoLugar tipo : TipoLugar.values())
        {
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }
}