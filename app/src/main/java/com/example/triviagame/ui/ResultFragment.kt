import android.view.LayoutInflater
import com.example.triviagametask.R
import com.example.triviagametask.data.Constants
import com.example.triviagametask.databinding.FragmentResultBinding
import com.example.triviagametask.ui.BaseFragment
import com.example.triviagametask.ui.HomeFragment

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
        } else{
            binding?.textCongratulations?.text =Constants.LOSE_TEXT
            binding?.textScoreNum?.text = points.toString()
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