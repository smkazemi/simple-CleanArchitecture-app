package com.basalam.intern.android.ui.listOfAnimalFlower

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.basalam.intern.android.R
import com.basalam.intern.android.databinding.FragmentListAnimalFlowerBinding
import com.basalam.intern.android.util.shortToast
import com.basalam.intern.android.util.toLog

class AnimalFlowerListFragment : Fragment() {

    private var _binding: FragmentListAnimalFlowerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListAnimalFlowerBinding.inflate(inflater, container, false)

        initView()

        return binding.root

    }

    private fun initView() {

        (requireActivity() as AppCompatActivity).apply {

            setSupportActionBar(binding.toolbar)

            val navController = findNavController()
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val item = menu.findItem(R.id.action_search)

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        val searchView = item.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.toString().toLog("search submited")
                findNavController().navigate(R.id.action_ListFragment_to_ContentFragment)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText.toString().toLog("search")
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {
                requireActivity().shortToast(getString(R.string.disable))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}