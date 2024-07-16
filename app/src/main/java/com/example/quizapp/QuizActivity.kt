package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.model.Question
import com.example.quizapp.model.User
import com.google.android.material.color.utilities.Score
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var list: ArrayList<Question>
    private var count: Int = 0
    private var score = 0
    var currentQuestionIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        Toast.makeText(this@QuizActivity, "Your Time Start Now", Toast.LENGTH_SHORT).show()
        setContentView(binding.root)
        var image = intent.getIntExtra("categoryImg", 0)
        var textCat = intent.getStringExtra("categoryText")
        binding.categoryImg.setImageResource(image)

        list = ArrayList<Question>()
        if (textCat == "Science") {
            list.add(
                Question(
                    "The bases change litmus paper from?",
                    "Blue to red",
                    "Red to blue",
                    "Red to orange",
                    "Orange to blue",
                    "Red to blue"
                )
            )
            list.add(
                Question(
                    "Which of the following is not lustrous?",
                    "Gallium",
                    "Cesium",
                    "Mercury",
                    "Diamond",
                    "Diamond"
                )
            )
            list.add(
                Question(
                    "Chemical formula of water?",
                    "C6H12O6",
                    "H2O",
                    "H2SO4",
                    "HO2",
                    "H2O"
                )
            )
            list.add(
                Question(
                    "Where is the dirty blood in our body filtered?",
                    "Heart",
                    "Lungs",
                    "Bone",
                    "Kidneys",
                    "Kidneys"
                )
            )
            list.add(
                Question(
                    "The unit of current is?",
                    "Ampere",
                    "watt",
                    "Volt",
                    "Coulomb",
                    "Ampere"
                )
            )
            list.add(
                Question(
                    "The speed of light is?",
                    "3*10^6 m/s",
                    "1.86*10^6 m/s",
                    "3*10^8 m/s",
                    "3*10^10 m/s",
                    "3*10^8 m/s"
                )
            )
            list.add(
                Question(
                    "Ribosomes are sites for?",
                    "Protein synthesis",
                    "Photosynthesis",
                    "Fat synthesis",
                    "Respiration",
                    "Protein synthesis"
                )
            )
            list.add(
                Question(
                    "What are the waves are light wave?",
                    "Longitudinal wave",
                    "Transverse wave",
                    "Both A & B",
                    "None",
                    "Transverse wave"
                )
            )
            list.add(
                Question(
                    "A passenger in a moving bus is thrown forward when the bus suddenly stops. This is explained",
                    "by Newton's second law",
                    "by Newton's first law",
                    "by Newton's third law",
                    "by Conservation of momentum",
                    "by Newton's first law"
                )
            )
            list.add(
                Question(
                    "Which of the following organism breathes from skin?",
                    "Snake",
                    "Monkey",
                    "Human",
                    "Earthworm",
                    "Earthworm"
                )
            )
        } else if (textCat == "Mathematics") {
            list.add(
                Question(
                    "450/15*(28-12)+32",
                    "480",
                    "492",
                    "512",
                    "504",
                    "512"
                )
            )
            list.add(
                Question(
                    "1+2*3+4-5",
                    "5",
                    "9",
                    "6",
                    "8",
                    "6"
                )
            )
            list.add(
                Question(
                    "Pick the odd one out",
                    "8/4*2",
                    "5*1-1",
                    "3*3-5",
                    "3*2+1",
                    "3*2+1"
                )
            )
            list.add(
                Question(
                    "Pick the even one out",
                    "6*4+2",
                    "3*10-3",
                    "5*5+2",
                    "9*4-9",
                    "6*4+2"
                )
            )
            list.add(
                Question(
                    "Evaluate (3+6)*2",
                    "9",
                    "15",
                    "18",
                    "12",
                    "18"
                )
            )
            list.add(
                Question(
                    "9*(12-8)",
                    "35",
                    "36",
                    "37",
                    "32",
                    "36"
                )
            )
            list.add(
                Question(
                    "225 is square root of?",
                    "15",
                    "17",
                    "25",
                    "13",
                    "15"
                )
            )
            list.add(
                Question(
                    "23+28/_____=30",
                    "4",
                    "5",
                    "7",
                    "6",
                    "4"
                )
            )
            list.add(
                Question(
                    "Evaluate 10/2-3",
                    "-10",
                    "2",
                    "9",
                    "-9",
                    "2"
                )
            )
            list.add(
                Question(
                    "7^2",
                    "36",
                    "45",
                    "49",
                    "37",
                    "49"
                )
            )
        } else if (textCat == "General knowledge") {
            list.add(
                Question(
                    "Who is the Prime Minister of India?",
                    "Rahul Gandhi",
                    "Yogi Adityanath",
                    "Narendra Modi",
                    "Amit Shah",
                    "Narendra Modi"
                )
            )
            list.add(
                Question(
                    "Green colour in Indian National Flag sigifies?",
                    "Valour",
                    "Sacrifice",
                    "Relation to soil and prosperity",
                    "Truth",
                    "Relation to soil and prosperity"
                )
            )
            list.add(
                Question(
                    "Which day was the National Emblem of India adopted?",
                    "15th August 1947",
                    "26th January 1959",
                    "15th August 1950",
                    "26th January 1950",
                    "26th January 1950"
                )
            )
            list.add(
                Question(
                    "Which is the national aquatic animal of India?",
                    "Crocodile",
                    "Gangetic Dophins",
                    "Alligator",
                    "Soft shelled tortoise",
                    "Gangetic Dophins"
                )
            )
            list.add(
                Question(
                    "Who among the following composed India's national anthem?",
                    "Mahatma Gandhi",
                    "Ravindranath Tagore",
                    "Dadabhai Naoroji",
                    "Motilal Nehru",
                    "Ravindranath Tagore"
                )
            )
            list.add(
                Question(
                    "When was the first cricket club established in India?",
                    "1890",
                    "1780",
                    "1792",
                    "1905",
                    "1792"
                )
            )
            list.add(
                Question(
                    "Motto of olympic council of India?",
                    "Ever Onward",
                    "Ever Freedom",
                    "Ever unity",
                    "All together",
                    "Ever Onward"
                )
            )
            list.add(
                Question(
                    "What is the nickname of india Women's National Hockey team?",
                    "Shakti",
                    "Durga",
                    "Nabhvarna",
                    "Nari",
                    "Nabhvarna"
                )
            )
            list.add(
                Question(
                    "Which of the following is not a School of Hindu philosophy?",
                    "Yoga",
                    "Lokayata",
                    "Samkhaya",
                    "Vedanta",
                    "Lokayata"
                )
            )
            list.add(
                Question(
                    "Recently,Which country launched the Multi-Mission communication satellite(PAKSAT MM1)?",
                    "Pakistan",
                    "China",
                    "Japan",
                    "India",
                    "Pakistan"
                )
            )
        } else if (textCat == "Computer") {
            list.add(
                Question(
                    "Who is the father of Computer?",
                    "James Gosling",
                    "Charles Babbage",
                    "Dennis Ritchie",
                    "Bjarne Stroustrup",
                    "Charles Babbage"
                )
            )
            list.add(
                Question(
                    "What is the full form of CPU?",
                    "Computer Processing Unit",
                    "Computer Principle Unit",
                    "Central Processing Unit",
                    "Control Processing Unit",
                    "Central Processing Unit"
                )
            )
            list.add(
                Question(
                    "Brain of Computer?",
                    "Central Processing Unit",
                    "Memory",
                    "Arithmatic Logic Unit",
                    "Control Unit",
                    "Central Processing Unit"
                )
            )
            list.add(
                Question(
                    "Which of the following is the smallest unit of data in a computer?",
                    "Bit",
                    "KB",
                    "Nibble",
                    "Byte",
                    "Bit"
                )
            )
            list.add(
                Question(
                    "Which of the following are physical device of a computer?",
                    "Software",
                    "Hardware",
                    "System Software",
                    "Package",
                    "Hardware"
                )
            )
            list.add(
                Question(
                    "Which of the following can access the server?",
                    "Web Client",
                    "User",
                    "Web Browser",
                    "Web server",
                    "Web Client"
                )
            )
            list.add(
                Question(
                    "What is the full form of PROM?",
                    "Program read only Memory",
                    "Primary read only Memory",
                    "Programmable read only Memory",
                    "Program read output Memory",
                    "Programmable read only Memory"
                )
            )
            list.add(
                Question(
                    "Full form of URL?",
                    "Undistributed Resource Locator",
                    "Unified Resource Locator",
                    "Uniform Resource Locator",
                    "Uniform Region Locator",
                    "Uniform Resource Locator"
                )
            )
            list.add(
                Question(
                    "MS Word is an example of?",
                    "Application software",
                    "A operating system ",
                    "A processing device",
                    "An Input device",
                    "Application software"
                )
            )
            list.add(
                Question(
                    "Shortcut key of cut?",
                    "Ctrl+c",
                    "Ctrl+x",
                    "Ctrl+v",
                    "Ctrl+z",
                    "Ctrl+x"
                )
            )
        } else if (textCat == "English") {
            list.add(
                Question(
                    "Which of the following is a conjunction?",
                    "With",
                    "Because",
                    "Therefore",
                    "None of the above",
                    "Because"
                )
            )
            list.add(
                Question(
                    "Select the grammatically correct sentence",
                    "He ate the sweets greedy",
                    "I haven't got none",
                    "He is much weak",
                    "I could not find it anywhere",
                    "I could not find it anywhere"
                )
            )
            list.add(
                Question(
                    "Which of the following word is plural?",
                    "Criteria",
                    "Index",
                    "Analysis",
                    "crisis",
                    "Criteria"
                )
            )
            list.add(
                Question(
                    "Our country is spiritual country,their......religious.",
                    "are",
                    "is",
                    "also",
                    "have",
                    "is"
                )
            )
            list.add(
                Question(
                    "Our sir teaches Mathematics......English.",
                    "across",
                    "besides",
                    "beside",
                    "both",
                    "across"
                )
            )
            list.add(
                Question(
                    "Find the correctly spelt word",
                    "Adeldurate",
                    "Adulterat",
                    "Adulterate",
                    "Adultarate",
                    "Adulterate"
                )
            )
            list.add(
                Question(
                    "Find the correct spelt word",
                    "Deference",
                    "Deffrance",
                    "Defference",
                    "Deffarence",
                    "Deference"
                )
            )
            list.add(
                Question(
                    "How Many letters are there in english alphabet",
                    "23",
                    "25",
                    "26",
                    "24",
                    "26"
                )
            )
            list.add(
                Question(
                    "People.......walk on grass",
                    "couldn't",
                    "needn't",
                    "mustn't",
                    "may not",
                    "mustn't"
                )
            )
            list.add(
                Question(
                    "........you speak any foreign language?",
                    "couldn't",
                    "can",
                    "should",
                    "can't",
                    "can't"
                )
            )
        } else {
            list.add(
                Question(
                    "Which of the following is a conjunction?",
                    "With",
                    "Because",
                    "Therefore",
                    "None of the above",
                    "Because"
                )
            )
            list.add(
                Question(
                    "Select the grammatically correct sentence",
                    "He ate the sweets greedy",
                    "I haven't got none",
                    "He is much weak",
                    "I could not find it anywhere",
                    "I could not find it anywhere"
                )
            )
            list.add(
                Question(
                    "Which of the following word is plural?",
                    "Criteria",
                    "Index",
                    "Analysis",
                    "crisis",
                    "Criteria"
                )
            )
            list.add(
                Question(
                    "Our country is spiritual country,their......religious.",
                    "are",
                    "is",
                    "also",
                    "have",
                    "is"
                )
            )
        }

        binding.question.setText(list.get(0).question)
        binding.option1.setText(list.get(0).option1)
        binding.option2.setText(list.get(0).option2)
        binding.option3.setText(list.get(0).option3)
        binding.option4.setText(list.get(0).option4)

        binding.option1.setOnClickListener {
            nextData(binding.option1.text.toString())
            currentQuestionIndex++
            loadQuestions()
        }
        binding.option2.setOnClickListener {
            nextData(binding.option2.text.toString())
            currentQuestionIndex++
            loadQuestions()
        }
        binding.option3.setOnClickListener {
            nextData(binding.option3.text.toString())
            currentQuestionIndex++
            loadQuestions()
        }
        binding.option4.setOnClickListener {
            nextData(binding.option4.text.toString())
            currentQuestionIndex++
            loadQuestions()
        }
        startTimer()
        loadQuestions()
    }

    private fun startTimer() {
        val totalTimeInMillis = 10 * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timer.text = String.format("%02d:%02d", minutes, remainingSeconds)

            }

            override fun onFinish() {
                //Finish the quiz
                val intent = Intent(this@QuizActivity, Timeoutscreen::class.java)
                startActivity(intent)
                finish()
            }

        }.start()

    }


    private fun loadQuestions() {
        binding.apply {
            questionindicatortextview.text = "Question ${currentQuestionIndex + 1}/10"
        }
    }

    private fun nextData(i: String) {
        if (count < list.size) {
            if (list.get(count).ans.equals(i)) {
                score++
            }
        }
        count++
        if (count >= list.size) {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        } else {
            binding.question.setText(list.get(count).question)
            binding.option1.setText(list.get(count).option1)
            binding.option2.setText(list.get(count).option2)
            binding.option3.setText(list.get(count).option3)
            binding.option4.setText(list.get(count).option4)
        }

    }

    companion object {

    }


}