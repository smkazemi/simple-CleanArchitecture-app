package com.basalam.intern.android.ui.listOfAnimalFlower

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.basalam.intern.android.R
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.data.remote.response.NetworkStatus.*
import com.basalam.intern.android.databinding.FragmentListAnimalFlowerBinding
import com.basalam.intern.android.ui.listOfAnimalFlower.adapter.AnimalFlowerAdapter
import com.basalam.intern.android.ui.listOfAnimalFlower.adapter.AnimalFlowerItemModel
import com.basalam.intern.android.ui.main.MainViewModel
import com.basalam.intern.android.util.Constant
import com.basalam.intern.android.util.shortToast
import com.basalam.intern.android.util.toLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalFlowerListFragment : Fragment() {

    private var _binding: FragmentListAnimalFlowerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AnimalFlowerViewModel by viewModels()
    private lateinit var mainViewModel: MainViewModel

    private lateinit var animalList: List<AnimalFlowerModel>
    private lateinit var flowerList: List<AnimalFlowerModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        _binding = FragmentListAnimalFlowerBinding.inflate(inflater, container, false)

        initView()

        // everything start from here
        // fetch data of animals and flowers from server OR get from shared viewModel
            viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                if (mainViewModel.animalList != null) {
                    showList()
                } else {
                    getData()
                }
            }
        }

        return binding.root

    }

    private suspend fun getData() {

        viewModel.getData().collect {

            when (it.status) {

                SUCCESS -> {

                    animalList = it.data!![Constant.animal]!!
                    flowerList = it.data[Constant.flower]!!

                    mainViewModel.animalList = animalList
                    mainViewModel.flowerList = flowerList

                    showList()

                }

                LOADING -> {

                    requireActivity().shortToast(getString(R.string.loading))
                }

                ERROR -> {

                    requireActivity().shortToast(getString(R.string.apiError))

                }

                NETWORK_ERROR -> {

                    requireActivity().shortToast(getString(R.string.checkNetwork))

                }
            }

        }

    }

    private fun showList() {

        binding.recFragmentList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AnimalFlowerAdapter(
                mainViewModel.animalList!!,
                mainViewModel.flowerList!!,
                this@AnimalFlowerListFragment
            )
        }

    }

    private fun initView() {

        (requireActivity() as AppCompatActivity).apply {

            setSupportActionBar(binding.toolbar)

            val navController = findNavController()
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)

        }

    }

    fun showDetail(model: AnimalFlowerItemModel) {

        val dirc = AnimalFlowerListFragmentDirections.actionListFragmentToContentFragment(model)

        findNavController().navigate(dirc)

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
                query.toString().toLog("search submitted")
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