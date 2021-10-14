package com.basalam.intern.android.features.contentOfList.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import com.basalam.intern.android.R
import com.basalam.intern.android.databinding.FragmentContentBinding
import com.basalam.intern.android.features.contentOfList.presentation.adapter.ImagePagerAdapter
import com.basalam.intern.android.features.listOfAnimalFlower.domain.model.AnimalFlowerItemModel
import com.basalam.intern.android.common.util.toLog

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ContentFragment : Fragment() {

    private lateinit var dataModel: AnimalFlowerItemModel
    val imagei = 0
    private var _binding: FragmentContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val imageIndex = MutableLiveData<Int>().also {
        it.value = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataModel = ContentFragmentArgs.fromBundle(requireArguments()).model

        _binding = FragmentContentBinding.inflate(inflater, container, false)

        _binding!!.fragment = this
        _binding!!.model = dataModel
        _binding!!.lifecycleOwner = this

        initView()

        return binding.root

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
        binding.pagerContent.adapter =
            ImagePagerAdapter(requireContext(), listOf(dataModel.animalUrlImage, dataModel.flowerUrlImage))
        binding.pagerContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                imageIndex.value = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

    }

    fun getSimilarLetters(letters: Array<String>) = letters.joinToString(separator = "،")


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