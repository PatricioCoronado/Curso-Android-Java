package com.example.mislugares.modelo;

import com.example.mislugares.modelo.GeoPunto;
import com.example.mislugares.modelo.TipoLugar;

public class Lugar //Datos de un lugar del mundd
{
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private String foto;
    private int telefono;
    private String url;
    private String comentario;
    private long fecha;
    private float valoracion;
    private TipoLugar tipo;

    /** Crea un Lugar vacío
     *
     */
    public Lugar()
    {
        fecha = System.currentTimeMillis();
        posicion =  GeoPunto.SIN_POSICION;
        tipo = TipoLugar.OTROS;
    }
    /** Conjunto de datos de un lugar del mundo
     *  El campo fecha se actualiza con la fecha de instanciadión del objeto
     * @param nombre
     * @param direccion
     * @param longitud Longitud y latitud. Objeto GeoPunto
     * @param latitud
     * @param telefono
     * @param url
     * @param comentario
     * @param valoracion
     */
    public Lugar(String nombre, String direccion, double longitud,
                 double latitud,TipoLugar tipo, int telefono,
                 String url, String comentario,int valoracion)
    {
        fecha = System.currentTimeMillis();
        posicion = new GeoPunto(longitud, latitud);
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo = tipo;
        this.telefono = telefono;
        this.url = url;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }
    // Geters, setters y toString
    public String getNombre() {
        return nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public GeoPunto getPosicion() {
        return posicion;
    }
    public String getFoto() {
        return foto;
    }
    public int getTelefono() {
        return telefono;
    }
    public String getUrl() {
        return url;
    }
    public String getComentario() {
        return comentario;
    }
    public long getFecha() {
        return fecha;
    }
    public float getValoracion() {
        return valoracion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    /** Recibe "valoracion" del listener del RatingBar
     *
     * @param valoracion float enviado por un RatingBar
     */
    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", foto='" + foto + '\'' +
                ", telefono=" + telefono +
                ", url='" + url + '\'' +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                ", valoracion=" + valoracion +
                ", tipo=" + tipo +
                '}';
    }

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }
}