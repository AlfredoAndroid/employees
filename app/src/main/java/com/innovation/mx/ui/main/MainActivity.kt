package com.innovation.mx.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.innovation.mx.R
import com.innovation.mx.databinding.ActivityMainBinding
import com.innovation.mx.models.EmployeeModel
import com.innovation.mx.ui.detail.DetailActivity
import com.innovation.mx.ui.home.HomeFragment
import com.innovation.mx.ui.settings.SettingsActivity
import com.innovation.mx.utils.CurrentEmployee
import com.innovation.mx.utils.EXTRA_EMPLOYEE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HomeFragment.HomeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_settings
            ), drawerLayout
        )

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        showEmployeeData()

        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_settings) {
                showSettings()
            }
            true
        }

        settingsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    showEmployeeData()
                }
            }
    }

    private fun showEmployeeData() {
        val header = binding.navView.getHeaderView(0)
        val textViewName = header.findViewById<TextView>(R.id.textView_employee_name)
        val imageView = header.findViewById<ImageView>(R.id.imageView_employee)
        textViewName.text = CurrentEmployee.employee?.name
        CurrentEmployee.image?.let {
            imageView.setImageBitmap(it)
        }

    }

    private fun showSettings() {
        binding.drawerLayout.close()
        settingsLauncher.launch(
            Intent(
                this,
                SettingsActivity::class.java
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun showDetail(employee: EmployeeModel) {
        startActivity(
            Intent(
                this,
                DetailActivity::class.java
            ).apply {
                putExtra(EXTRA_EMPLOYEE, employee)
            }
        )
    }

}