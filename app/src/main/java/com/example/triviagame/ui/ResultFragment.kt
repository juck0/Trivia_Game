import android.view.LayoutInflater
import com.example.triviagame.R
import com.example.triviagame.data.Constants
import com.example.triviagame.data.Constants.LOSE_EMOJI
import com.example.triviagame.data.Constants.WIN_EMOJI
import com.example.triviagame.databinding.FragmentResultBinding
import com.example.triviagame.ui.BaseFragment
import com.example.triviagame.ui.HomeFragment


class ResultFragment: BaseFragment<FragmentResultBinding>() {
    override val LOG_TAG: String = "result fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentResultBinding = FragmentResultBinding::inflate
    var points:Int = 0

    override fun setup() {
        points = requireArguments().getInt(Constants.POINTS)
        showResult()

        binding?.buttonRetake?.setOnClickListener {
            retakeQuiz()
        }


    }

    private fun showResult() {
        if (points > 5){
            binding?.textCongratulations?.text = Constants.WIN_TEXT
            binding?.textScoreNum?.text = points.toString()
            binding?.emoji?.text = WIN_EMOJI.toString()
        } else{
            binding?.textCongratulations?.text =Constants.LOSE_TEXT
            binding?.textScoreNum?.text = points.toString()
            binding?.emoji?.text = LOSE_EMOJI.toString()

        }
    }
    private fun retakeQuiz() {
        val homeFragment = HomeFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,homeFragment)
            .addToBackStack(null)
            .commit()
    }
    override fun addCallBack() {
    }
}