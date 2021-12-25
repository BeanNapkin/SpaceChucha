package pro.fateeva.spacechucha.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.themes_dialog_fragment.view.*
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.ThemesDialogFragmentBinding


class ThemesDialogFragment : DialogFragment() {

    lateinit var theme: String

    private var _binding: ThemesDialogFragmentBinding? = null
    val binding: ThemesDialogFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ThemesDialogFragment"
        const val THEME = "Theme"
        const val THEME_NAME = "Theme name"
        const val MOON = "moon"
        const val MARS = "mars"
        fun newInstance(): DialogFragment = ThemesDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ThemesDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs: SharedPreferences = requireActivity().getSharedPreferences(THEME, MODE_PRIVATE)
        val themeFromPref: String? = prefs.getString(THEME_NAME, null)

        when(themeFromPref){
            MOON -> binding.moon.isChecked = true
            MARS, null -> binding.mars.isChecked = false
        }

        binding.choseThemeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.mars -> theme = MOON
                R.id.moon -> theme = MARS
            }
        }

        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        view.btnPositive.setOnClickListener {
            val editor = requireContext().getSharedPreferences(THEME, MODE_PRIVATE).edit()
            editor.putString(THEME_NAME, theme)
            editor.apply()
            requireActivity().recreate()
            dismiss()
        }
        view.btnNegative.setOnClickListener {
            dismiss()
        }
    }
}
