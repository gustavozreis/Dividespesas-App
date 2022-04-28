package com.gustavozreis.dividespesas.base.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModel
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModelFactory
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserServiceImpl
import com.gustavozreis.dividespesas.databinding.LoginFragmentBinding
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel

class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var viewModel: ViewModel

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private var emailInput: EditText? = null
    private var passwordInput: EditText? = null
    private var btnEnter: Button? = null
    private var btnRegister: Button? = null

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBinding()
        setUpFirebaseAuth()
        setUpListeners()

    }

    private fun setUpBinding() {
        emailInput = binding.edittextEmailInput
        passwordInput = binding.edittextPaswordInput
        btnEnter = binding.buttonLogin
        btnRegister = binding.buttonRegister
    }

    private fun setUpFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setUpListeners() {

        btnEnter?.setOnClickListener {
            if (emailInput!!.text.isEmpty() || passwordInput!!.text.isEmpty()) {
                Toast.makeText(requireContext(), "Insira todos os dados para entrar.", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(emailInput!!.text.toString(),
                    passwordInput!!.text.toString())
                    .addOnCompleteListener { auth ->
                        if (auth.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                    }
            }
        }

    }

    private fun insertUserDataToSharedPreferences() {
        val userEmail = auth.currentUser?.email

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            BaseViewModelFactory(
                FirebaseUserHelper(
                    FirebaseUserServiceImpl())))[BaseViewModel::class.java]
    }


}