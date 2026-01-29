package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class RegisterActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    lateinit var btnGuardar: Button
    lateinit var editNombreTutor: EditText
    lateinit var editMaterias: EditText
    lateinit var editDescripcion: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_tutor)


        //Inicializar variables
        btnGuardar = findViewById(R.id.btnRegistro)
        editNombreTutor = findViewById(R.id.edtTutorName)
        editMaterias = findViewById(R.id.edtSubjects)
        editDescripcion = findViewById(R.id.edtProfessionalDescription)
        editEmail = findViewById(R.id.edtEmail)
        editPassword = findViewById(R.id.edtPassword)
        val txtGoLogin = findViewById<TextView>(R.id.txtGoLogin)

        auth = Firebase.auth


        //Eventos clic
        btnGuardar.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            val tutor = Tutor(
                nombre_tutor = editNombreTutor.text.toString(),
                materia = editMaterias.text.toString(),
                descripcion = editDescripcion.text.toString(),
                calificacion = 0.0
            )
            registrarTutor(email, password, tutor)
        }

        txtGoLogin.setOnClickListener {
            finish()
        }

    }

    fun registrarTutor(email: String, password: String, tutor: Tutor) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid

                //Guardar perfil en Firestore
                db.collection("tutors")
                    .add(tutor)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Tutor registrado", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)

                        // Cierra la pantalla de registro para que no vuelva atrás
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.w(EXTRA_LOGIN, "Error adding document", e)
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }


    }
}