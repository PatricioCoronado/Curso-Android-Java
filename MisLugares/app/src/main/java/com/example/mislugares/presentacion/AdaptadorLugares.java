package com.example.mislugares.presentacion;
import com.example.mislugares.R;
import com.example.mislugares.modelo.Lugar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mislugares.datos.RepositorioLugares;
//Definición de la clase
public class AdaptadorLugares extends RecyclerView.Adapter<AdaptadorLugares.ViewHolder>
{
    protected View.OnClickListener onClickListener;
    protected RepositorioLugares lugares;// Lista de lugares a mostrar
    //El adaptador se instanciará con una List<> de elementos
    public AdaptadorLugares(RepositorioLugares lugares) //"lugares" es una lista de objetos lugar
    {
        this.lugares = lugares;//Lista de objetos de la clase Lugar. Origen de datos
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //Estos son los elementos a mostrar cuyo origen de datos es la lista de objetos Lugar "lugares"
        public TextView nombre, direccion;
        public ImageView foto;
        public RatingBar valoracion;
        //Constructor de ViewHolder
        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            direccion = itemView.findViewById(R.id.direccion);
            foto = itemView.findViewById(R.id.foto);
            valoracion= itemView.findViewById(R.id.valoracion);
        }
        // Personalizamos un ViewHolder a partir de un lugar
        public void personaliza(Lugar lugar)
        {
            //Personalizamos las 4 vistas
            nombre.setText(lugar.getNombre());
            direccion.setText(lugar.getDireccion());
            int id = R.drawable.otros;
            switch(lugar.getTipo())//Selecciona el icono según el enum al qu corrsponda
            {
                case RESTAURANTE:id = R.drawable.restaurante; break;
                case BAR:    id = R.drawable.bar;     break;
                case COPAS:   id = R.drawable.copas;    break;
                case ESPECTACULO:id = R.drawable.espectaculos; break;
                case HOTEL:   id = R.drawable.hotel;    break;
                case COMPRAS:  id = R.drawable.compras;   break;
                case EDUCACION: id = R.drawable.educacion;  break;
                case DEPORTE:  id = R.drawable.deporte;   break;
                case NATURALEZA: id = R.drawable.naturaleza; break;
                case GASOLINERA: id = R.drawable.gasolinera; break;
            }
            foto.setImageResource(id);
            foto.setScaleType(ImageView.ScaleType.FIT_END);
            valoracion.setRating(lugar.getValoracion());
        }
    }
    // El ViewHolder se crea con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflamos la vista desde el xml
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.elemento_lista, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }
    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion)
    {
        Lugar lugar = lugares.elemento(posicion);
        holder.personaliza(lugar);
    }
    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount()
    {
        return lugares.tamanyo();
    }
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}


