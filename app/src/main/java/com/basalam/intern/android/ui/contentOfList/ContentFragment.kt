package com.basalam.intern.android.ui.contentOfList

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.basalam.intern.android.R
import com.basalam.intern.android.databinding.FragmentContentBinding
import com.basalam.intern.android.ui.contentOfList.adapter.ImagePagerAdapter
import com.basalam.intern.android.util.toLog

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null

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

        _binding = FragmentContentBinding.inflate(inflater, container, false)

        initView()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun initView() {

        // toolbar
        (requireActivity() as AppCompatActivity).apply {

            setSupportActionBar(binding.toolbarContent)

            val navController = findNavController()
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)

        }

        // view pager
        binding.pagerContent.adapter = ImagePagerAdapter(requireContext(), listOf(""))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main2, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val item = menu.findItem(R.id.action_search)

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        val searchView = item.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.toString().toLog("search submited")
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
                findNavController().navigate(R.id.action_ContentFragment_to_ListFragment)
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