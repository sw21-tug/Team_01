package at.tu.graz.coffee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.tu.graz.coffee.CoffeeApplication
import at.tu.graz.coffee.R
import at.tu.graz.coffee.businessLogic.SortHelper


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((requireActivity().application as CoffeeApplication).coffeeRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val listView: RecyclerView = root.findViewById(R.id.home_listview)

        val adapter = HomeAdapter()
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.allCoffees.observe(requireActivity()) { coffees ->
            coffees.forEach { it.calculateNewEvaluation() }
            val sortedCoffees = SortHelper.sortCoffee(coffees)
            sortedCoffees.let { adapter.submitList(it) }
        }
        return root
    }
}
