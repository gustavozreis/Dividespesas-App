package com.gustavozreis.dividespesas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.databinding.ActivityMainBinding
import com.gustavozreis.dividespesas.domain.models.Spend
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btnTeste: Button = binding.button2
        var textTeste: TextView = binding.tvTest




    }
}