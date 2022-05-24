package com.gustavozreis.dividespesas.features.splitspend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.SplitSpendFragmentBinding
import com.gustavozreis.dividespesas.features.utils.CurrencyFormater
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel

class SplitSpendFragment : Fragment() {

    companion object {
        fun newInstance() = SplitSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: SplitSpendFragmentBinding? = null
    private val binding get() = _binding!!

    private var textUser01Line01: TextView? = null
    private var textUser01Line02: TextView? = null
    private var textUser01Line03: TextView? = null
    private var textUser02Line01: TextView? = null
    private var textUser02Line02: TextView? = null
    private var textUser02Line03: TextView? = null
    private var textResultLine01: TextView? = null
    private var textResultLine02: TextView? = null
    private var textResultLine03: TextView? = null

    private var usersAndSpendDataObserver: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = SplitSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        (viewModel as SpendSharedViewModel).splitSpend()
        setUpBindings()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun setUpBindings() {
        textUser01Line01 = binding.textviewUser01Line01
        textUser01Line02 = binding.textviewUser01Line02
        textUser01Line03 = binding.textviewUser01Line03
        textUser02Line01 = binding.textviewUser02Line01
        textUser02Line02 = binding.textviewUser02Line02
        textUser02Line03 = binding.textviewUser02Line03
        textResultLine01 = binding.textviewResultLine01
        textResultLine02 = binding.textviewResultLine02
        textResultLine03 = binding.textviewResultLine03
    }

    private fun setUpObservers() {
       (viewModel as SpendSharedViewModel)
           .usersAndSpendsData.observe(this.viewLifecycleOwner) { returnList ->
               usersAndSpendDataObserver = returnList
               setUpSplitSpends(usersAndSpendDataObserver)
       }
    }

    private fun setUpListeners() {
    }

    private fun setUpSplitSpends(dataList: List<String>) {
        val user01Name = dataList[0]
        val user01Spends = dataList[1]
        val user02Name = dataList[2]
        val user02Spends = dataList[3]

        val spendDifferenceDouble = dataList[4].toDouble()
        var spendDifferenceString: String = ""

        val format = CurrencyFormater()
        val spendDifferenceFormated = format.doubleToString(spendDifferenceDouble)

        textUser01Line01?.text = getString(R.string.user01line01, user01Name)
        textUser01Line02?.text = getString(R.string.user01line02, user01Spends)

        textUser02Line01?.text = getString(R.string.user02line01, user02Name)
        textUser02Line02?.text = getString(R.string.user02line02, user02Spends)

        if (spendDifferenceDouble >= 0) {
            textResultLine01?.text = resources.getString(R.string.resultLine01, user02Name)
            textResultLine03?.text = resources.getString(R.string.resultLine03, user01Name)

            val payout = spendDifferenceDouble/2
            val payoutString = format.doubleToString(payout)
            textResultLine02?.text = resources.getString(R.string.resultLine02, payoutString)
        } else if (spendDifferenceDouble <= 0) {
            textResultLine01?.text = resources.getString(R.string.resultLine01, user01Name)
            textResultLine03?.text = resources.getString(R.string.resultLine03, user02Name)

            val payout = spendDifferenceDouble/2 * -1
            val payoutString = format.doubleToString(payout)
            textResultLine02?.text = resources.getString(R.string.resultLine02, payoutString)
        } else {
            textResultLine02?.text = "Dividas em dia"
            textResultLine01?.visibility = View.INVISIBLE
            textResultLine03?.visibility = View.INVISIBLE
        }
    }

}