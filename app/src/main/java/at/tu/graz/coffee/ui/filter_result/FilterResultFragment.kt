package at.tu.graz.coffee.ui.filter_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.tu.graz.coffee.CoffeeApplication
import at.tu.graz.coffee.R
import at.tu.graz.coffee.businessLogic.SortHelper


class FilterResultFragment : Fragment() {
    private val args: FilterResultFragmentArgs by navArgs()

    private val filterResultModel: FilterResultViewModel by viewModels {
        FilterResultViewModelFactory((requireActivity().application as CoffeeApplication).coffeeRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_filter_result, container, false)
        val listView: RecyclerView = root.findViewById(R.id.filter_result_listview)

        val adapter = FilterResultAdapter()
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())

        filterResultModel.filterCoffees(args.filteredCoffeeList!!.toList())
            .observe(requireActivity()) { coffees ->
                val empty = root.findViewById(R.id.empty) as TextView

                if (coffees.isEmpty()) {
                    empty.visibility = VISIBLE
                } else {
                    empty.visibility = GONE
                }
                coffees.forEach { it.calculateNewEvaluation() }
                val sortedCoffees = SortHelper.sortCoffee(coffees)
                sortedCoffees.let { adapter.submitList(it) }
            }

        return root
    }
}