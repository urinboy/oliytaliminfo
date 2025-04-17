package uz.urinboydev.oliytaliminfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import uz.urinboydev.oliytaliminfo.R
import uz.urinboydev.oliytaliminfo.adapter.UniversityAdapter
import uz.urinboydev.oliytaliminfo.databinding.ActivityMainBinding
import uz.urinboydev.oliytaliminfo.viewmodel.UniversityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UniversityViewModel by viewModels()
    private val adapter = UniversityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupTabLayout()
        setupSearch()
        observeData()

        viewModel.loadUniversities()
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        binding.universitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.loadUniversities()
                    1 -> viewModel.filterByOwnershipType("Davlat")
                    2 -> viewModel.filterByOwnershipType("Nodavlat")
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            editable?.toString()?.let { query ->
                if (query.isNotEmpty()) {
                    viewModel.searchUniversities(query)
                } else {
                    viewModel.loadUniversities()
                }
            }
        }
    }

    private fun observeData() {
        viewModel.universities.observe(this) { universities ->
            adapter.setUniversities(universities)
        }
    }
}