package com.gustavozreis.dividespesas.base.login

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModel
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModelFactory
import com.gustavozreis.dividespesas.data.users.UserInstance
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserServiceImpl
import com.gustavozreis.dividespesas.databinding.RegisterFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.register_fragment) {

    var _binding: RegisterFragmentBinding? = null
    val binding get() = _binding!!

    var viewModel: ViewModel? = null

    var firstNameInput: EditText? = null
    var lastNameInput: EditText? = null
    var emailInput: EditText? = null
    var passwordInput: EditText? = null
    var btnCreate: Button? = null

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBindings()
        setUpFirebaseAuth()
        setUpListeners()

    }

    private fun setUpBindings() {
        firstNameInput = binding.edittextFirstnameInput
        lastNameInput = binding.edittextLastnameInput
        emailInput = binding.edittextEmailInput
        passwordInput = binding.edittextPaswordInput
        btnCreate = binding.buttonCreate
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            BaseViewModelFactory(
                FirebaseUserHelper(
                    FirebaseUserServiceImpl()
                )
            )
        )[BaseViewModel::class.java]
    }

    private fun setUpListeners() {
        btnCreate?.setOnClickListener {

            var isUserCreated: Boolean = false

            auth.createUserWithEmailAndPassword(emailInput?.text.toString(),
                passwordInput?.text.toString()).addOnSuccessListener {
                (viewModel as BaseViewModel).createUser(
                    firstNameInput?.text.toString(),
                    lastNameInput?.text.toString(),
                    emailInput?.text.toString(),
                    passwordInput?.text.toString()
                )

                auth.signInWithEmailAndPassword(emailInput?.text.toString(),
                    passwordInput?.text.toString())
                    .addOnCompleteListener { auth ->
                        if (auth.isSuccessful) {
                            lifecycleScope.launch {
                                (viewModel as BaseViewModel).getUserFromEmail()
                                while (UserInstance.currentUser == null) {
                                    delay(1_000)
                                }
                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                            }

                        }
                    }
            }

            /*if (isUserCreated == true) {

                (viewModel as BaseViewModel).createUser(
                    firstNameInput?.text.toString(),
                    lastNameInput?.text.toString(),
                    emailInput?.text.toString(),
                    passwordInput?.text.toString()
                )

                auth.signInWithEmailAndPassword(emailInput?.text.toString(),
                    passwordInput?.text.toString())
                    .addOnCompleteListener { auth ->
                        if (auth.isSuccessful) {
                            lifecycleScope.launch {
                                (viewModel as BaseViewModel).getUserFromEmail()
                                while (UserInstance.currentUser == null) {
                                    delay(1_000)
                                }
                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                            }

                        }
                    }
            }*/
        }
    }


    private fun setUpFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

}