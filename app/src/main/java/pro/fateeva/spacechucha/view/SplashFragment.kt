package pro.fateeva.spacechucha.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.PictureOfTheDayFragmentBinding
import pro.fateeva.spacechucha.databinding.SplashFragmentBinding


class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null
    val binding: SplashFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animator = ObjectAnimator.ofFloat(binding.imageView, View.SCALE_X,-1f);
        animator.duration = 500
        animator.repeatCount = 5
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}