package pro.fateeva.spacechucha.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.BottomDrawerFragmnetBinding

class BottomDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomDrawerFragmnetBinding? = null
    val binding: BottomDrawerFragmnetBinding
        get() {
            return _binding!!
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.one -> {
                    Toast.makeText(context,"1",Toast.LENGTH_SHORT).show()
                }
                R.id.two -> {
                    Toast.makeText(context,"2",Toast.LENGTH_SHORT).show()
                }
            }

            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomDrawerFragmnetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}