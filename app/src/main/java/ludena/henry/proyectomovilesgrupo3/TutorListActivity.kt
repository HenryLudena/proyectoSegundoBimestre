package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TutorListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule_session)
        val showListPage = findViewById<ImageView>(R.id.btnList)
        showListPage.setOnClickListener {
            val intent = Intent(this, SessionFormActivity::class.java)
            startActivity(intent)
        }
    }
}