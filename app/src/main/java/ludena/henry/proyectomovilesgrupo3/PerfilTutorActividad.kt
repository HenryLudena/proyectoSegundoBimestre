package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PerfilTutorActividad : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_tutor_actividad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Obtener ID del tutor
        val tutorId = intent.getStringExtra("TUTOR_ID") ?: return
        val txtNombre = findViewById<TextView>(R.id.nombreTutor)
        val txtMaterias = findViewById<TextView>(R.id.materiasTutor)
        val txtDescripcion = findViewById<TextView>(R.id.descripcionTutor)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBarTutor)
        val btnAgendar = findViewById<Button>(R.id.btnSiguiente)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        btnAgendar.setOnClickListener {
            val intent = Intent(this, SessionFormActivity::class.java)
            intent.putExtra("TUTOR_ID", tutorId)
            startActivity(intent)
        }


        db.collection("tutors")
            .document(tutorId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val tutor = doc.toObject(Tutor::class.java)

                    txtNombre.text = tutor?.nombre_tutor
                    txtMaterias.text = tutor?.materia
                    txtDescripcion.text = tutor?.descripcion
                    ratingBar.rating = tutor?.calificacion?.toFloat() ?: 0f
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}