package ludena.henry.proyectomovilesgrupo3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SessionFormActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var inputLugar: EditText
    private lateinit var notaInput: EditText
    private var selectedHour = ""
    private var selectedDate = ""

    private lateinit var btn10: Button
    private lateinit var btn12: Button
    private lateinit var btn13: Button
    private lateinit var btn14: Button
    private lateinit var btn15: Button
    private lateinit var btn16: Button
    private lateinit var btn17: Button
    private lateinit var btn18: Button
    private lateinit var btnNext: Button
    private lateinit var hourButtons: List<Button>







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule_session)

        // 🔗 Enlazar vistas
        calendarView = findViewById(R.id.calendarView)
        inputLugar = findViewById(R.id.inputLugar)
        notaInput = findViewById(R.id.notaInput)

        btn10 = findViewById(R.id.btn10)
        btn12 = findViewById(R.id.btn12)
        btn13 = findViewById(R.id.btn13)
        btn14 = findViewById(R.id.btn14)
        btn15 = findViewById(R.id.btn15)
        btn16 = findViewById(R.id.btn16)
        btn17 = findViewById(R.id.btn17)
        btn18 = findViewById(R.id.btn18)
        btnNext = findViewById(R.id.btnNext)

        val tutorId = intent.getStringExtra("TUTOR_ID") ?: ""

        calendarView.setOnDateChangeListener { _, year, month, day ->
            selectedDate = "$day/${month + 1}/$year"
        }

        // Botones de hora
        btn10.setOnClickListener { selectHour("10:00") }
        btn12.setOnClickListener { selectHour("12:00") }
        btn13.setOnClickListener { selectHour("13:00") }
        btn14.setOnClickListener { selectHour("14:00") }
        btn15.setOnClickListener { selectHour("15:00") }
        btn16.setOnClickListener { selectHour("16:00") }
        btn17.setOnClickListener { selectHour("17:00") }
        btn18.setOnClickListener { selectHour("18:00") }

        hourButtons = listOf(btn10, btn12, btn13, btn14, btn15, btn16, btn17, btn18)


        btnNext.setOnClickListener {

            if (selectedDate.isEmpty() || selectedHour.isEmpty()) {
                Toast.makeText(this, "Selecciona fecha y hora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val lugar = inputLugar.text.toString()
            val descripcion = notaInput.text.toString()

            val session = Session(
                tutorId = tutorId,
                tutorNombre = "Nombre Tutor",
                fecha = selectedDate,
                hora = selectedHour,
                lugar = lugar,
                descripcion = descripcion
            )

            FirebaseFirestore.getInstance()
                .collection("sessions")
                .add(session)
                .addOnSuccessListener {
                    Toast.makeText(this, "Tutoría agendada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
        }


    }

    fun selectHour(hour: String) {
        selectedHour = hour

        hourButtons.forEach { button ->
            button.setBackgroundTintList(
                getColorStateList(R.color.light_cyan)
            )
            button.setTextColor(getColor(R.color.dark_cyan))
        }

        val selectedButton = hourButtons.first { it.text == hour }
        selectedButton.setBackgroundTintList(
            getColorStateList(R.color.primary_cyan)
        )
        selectedButton.setTextColor(getColor(android.R.color.white))
    }

}