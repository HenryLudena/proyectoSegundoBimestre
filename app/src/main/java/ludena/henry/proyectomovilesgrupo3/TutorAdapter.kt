package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TutorAdapter(private val tutors: List<Tutor>) :
    RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

    inner class TutorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tutorName)
        val materia: TextView = view.findViewById(R.id.chipSubject)
        val rating: RatingBar = view.findViewById(R.id.ratingBar)
        val verPerfil: TextView = view.findViewById(R.id.btnViewProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_tutor, parent, false)
        return TutorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        val tutor = tutors[position]
        holder.nombre.text = tutor.nombre_tutor
        holder.materia.text = tutor.materia
        holder.rating.rating = tutor.calificacion.toFloat()
        holder.verPerfil.setOnClickListener {
            val intent = Intent(holder.itemView.context, PerfilTutorActividad::class.java)
            intent.putExtra("TUTOR_ID", tutor.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = tutors.size
}
