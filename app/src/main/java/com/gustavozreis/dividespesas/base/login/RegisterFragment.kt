package com.gustavozreis.dividespesas.base.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModel
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModelFactory
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserServiceImpl
import com.gustavozreis.dividespesas.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment(R.layout.register_fragment) {

    var _binding: RegisterFragmentBinding? = null
    val binding get() = _binding!!

    var viewModel: ViewModel? = null

    var firstNameInput: EditText? = null
    var lastNameInput: EditText? = null
    var emailInput: EditText? = null
    var passwordInput: EditText? = null
    var btnCreate: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBindings()
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
            (viewModel as BaseViewModel).createUser(
                firstNameInput?.text.toString(),
                lastNameInput?.text.toString(),
                emailInput?.text.toString(),
                passwordInput?.text.toString()
            )
        }
    }

}