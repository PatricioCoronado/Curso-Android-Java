package com.example.mislugares;

public interface RepositorioLugares {
    /**
     *
     * @param id
     * @return Objeto de la clase Lugar
     */
    Lugar elemento(int id); //Devuelve el elemento dado su id

    /** Añade un nuevo objeto de la clase Lugar al repositorio
     *
     * @param lugar tipo Lugar
     */
    void anyade(Lugar lugar); //Añade el elemento indicado

    /** Añade un objeto en blanco de la clase Lugar al repositirio
     *
     * @return
     */
    int nuevo(); //Añade un elemento en blanco y devuelve su id

    /** Borra el objeto Lugar indizado por id
     *
     * @param id tipo int
     */
    void borrar(int id); //Elimina el elemento con el id indicado

    /**Devuelve el tamaño del repositorio
     *
     * @return número de elementos en el repositirio tipo int
     */
    int tamanyo(); //Devuelve el número de elementos

    /**Reemplaza un lugar por otro
     *
     * @param id
     * @param lugar
     */
    void actualiza(int id, Lugar lugar); //Reemplaza un elemento
}