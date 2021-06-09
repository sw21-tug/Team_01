package at.tu.graz.coffee.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import at.tu.graz.coffee.CoffeeApplication
import at.tu.graz.coffee.R
import at.tu.graz.coffee.businessLogic.FilterHelper.Companion.filterCoffee
import com.google.android.material.slider.RangeSlider


class FilterFragment : Fragment() {

    private val filterViewModel: FilterViewModel by viewModels {
        FilterViewModelFactory((requireActivity().application as CoffeeApplication).coffeeRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sliderTotal: RangeSlider = view.findViewById(R.id.slider_total)
        val sliderTaste: RangeSlider = view.findViewById(R.id.slider_taste)
        val sliderCost: RangeSlider = view.findViewById(R.id.slider_cost)
        val sliderAvailability: RangeSlider = view.findViewById(R.id.slider_availability)

        sliderTotal.setValues(0f, 10f)
        sliderTaste.setValues(0f, 10f)
        sliderCost.setValues(0f, 10f)
        sliderAvailability.setValues(0f, 10f)

        val dropdownAvailableAt: Spinner = view.findViewById(R.id.spinner_available_at)

        filterViewModel.getStoresOfAllCoffees().observe(requireActivity()) {coffeeStoreNames ->
            val mutableCoffeeStoreNames = coffeeStoreNames as MutableList<String>
            mutableCoffeeStoreNames.add(0, getString(R.string.all_shops))

            dropdownAvailableAt.adapter = activity?.applicationContext?.let {
                ArrayAdapter(it, android.R.layout.simple_list_item_1, mutableCoffeeStoreNames)
            }
        }

        val filterButton: Button = view.findViewById(R.id.btn_filter)
        filterButton.setOnClickListener {
            val rangeTotal = sliderTotal.values
            val rangeTaste = sliderTaste.values
            val rangeCost = sliderCost.values
            val rangeAvailability = sliderAvailability.values
            val searchText = view.findViewById<EditText>(R.id.input_field).text.toString()

            var selectedStore = dropdownAvailableAt.selectedItem as String

            if (selectedStore == getString(R.string.all_shops)) {
                selectedStore = ""
            }

            filterViewModel.allCoffees.observe(requireActivity()) { allCoffees ->
                val ids = filterCoffee(
                    allCoffees, rangeTotal, rangeTaste, rangeCost,
                    rangeAvailability, selectedStore, searchText
                )

                val action =
                    FilterFragmentDirections.actionOpenFilterResult(ids.toIntArray())
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}