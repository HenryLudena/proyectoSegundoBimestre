package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TutorListActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    val tutorList = mutableListOf<Tutor>()
    lateinit var adapter: TutorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_list)

        adapter = TutorAdapter(tutorList)

        val recyclerTutors = findViewById<RecyclerView>(R.id.recyclerTutors)


        recyclerTutors.layoutManager = LinearLayoutManager(this)
        recyclerTutors.adapter = adapter

        cargarTutores()
    }

    fun cargarTutores() {
        db.collection("tutors")
            .get()
            .addOnSuccessListener { result ->
                tutorList.clear()
                for (doc in result) {
                    val tutor = doc.toObject(Tutor::class.java)
                    tutor.id = doc.id
                    tutorList.add(tutor)
                }
                adapter.notifyDataSetChanged()
            }
    }


}