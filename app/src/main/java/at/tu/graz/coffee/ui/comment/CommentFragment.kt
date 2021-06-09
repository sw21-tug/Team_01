package at.tu.graz.coffee.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import at.tu.graz.coffee.CoffeeApplication
import at.tu.graz.coffee.R
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import at.tu.graz.coffee.ui.coffee_detail.CoffeeDetailFragmentArgs
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CommentFragment : Fragment() {
    private val viewModel: CommentViewModel by viewModels {
        CommentViewModelFactory((requireActivity().application as CoffeeApplication).coffeeRepository)
    }
    private val args: CoffeeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.comments)

        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoffee(args.coffeeId).observe(requireActivity()) { coffee ->
            setData(coffee, view)
        }
    }

    private fun setData(coffeeWithReviews: CoffeeWithReviews, view: View) {
        val coffeeName = view.findViewById<TextView>(R.id.comment_coffee_name)
        coffeeName.text = coffeeWithReviews.coffee.name

        val listView: ListView = view.findViewById(R.id.comment_listview)

        val adapter = CommentAdapter(view.context, coffeeWithReviews.reviews.toMutableList())
        listView.adapter = adapter

        setListViewHeightBasedOnItems(listView)

        val submitButton = view.findViewById<Button>(R.id.btn_comment_submit)
        submitButton.setOnClickListener {
            val text = view.findViewById(R.id.comment_text_field) as EditText

            val costSlider = view.findViewById(R.id.comment_cost) as RangeSlider
            val valueCost: MutableList<Float> = costSlider.values

            val tasteSlider = view.findViewById(R.id.comment_taste) as RangeSlider
            val valueTaste: MutableList<Float> = tasteSlider.values

            val availabilitySlider = view.findViewById(R.id.comment_availability) as RangeSlider
            val valueAvailability: MutableList<Float> = availabilitySlider.values

            val newReview = Review(
                valueTaste[0].toInt(),
                valueCost[0].toInt(),
                valueAvailability[0].toInt(),
                text.text.toString(),
                coffeeWithReviews.coffee.coffeeId
            )

            GlobalScope.launch {
                viewModel.insertReviewForCoffee(listOf(newReview))
            }

            listView.adapter = CommentAdapter(
                requireContext(),
                coffeeWithReviews.reviews as MutableList<Review>
            )

            text.text.clear()
            costSlider.setValues(0.0F)
            tasteSlider.setValues(0.0F)
            availabilitySlider.setValues(0.0F)

            Toast.makeText(activity, getString(R.string.comment_added), Toast.LENGTH_SHORT).show()
        }
    }

    // https://stackoverflow.com/questions/35115788/how-to-set-listview-height-depending-on-the-items-inside-scrollview/48027821
    private fun setListViewHeightBasedOnItems(listView: ListView): Boolean {
        val listAdapter = listView.adapter
        return if (listAdapter != null) {
            val numberOfItems = listAdapter.count

            var totalItemsHeight = 0
            for (itemPos in 0 until numberOfItems) {
                val item = listAdapter.getView(itemPos, null, listView)
                val px = 500 * listView.resources.displayMetrics.density
                item.measure(
                    View.MeasureSpec.makeMeasureSpec(px.toInt(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                totalItemsHeight += item.measuredHeight
            }

            val totalDividersHeight = listView.dividerHeight *
                    (numberOfItems - 1)
            val totalPadding = listView.paddingTop + listView.paddingBottom

            val params = listView.layoutParams
            params.height = totalItemsHeight + totalDividersHeight + totalPadding
            listView.layoutParams = params
            listView.requestLayout()
            true
        } else {
            false
        }
    }
}