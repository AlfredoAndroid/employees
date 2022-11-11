package com.innovation.mx.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.innovation.mx.R
import com.innovation.mx.databinding.ActivityDetailBinding
import com.innovation.mx.models.EmployeeModel
import com.innovation.mx.utils.EXTRA_EMPLOYEE
import com.innovation.mx.utils.orZero

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.title = getString(R.string.detail_title)

        val employee = intent.getParcelableExtra<EmployeeModel>(EXTRA_EMPLOYEE)

        binding.apply {
            textViewDetailId.text = employee?.id.toString()
            textViewDetailName.text = employee?.name
            textViewDetailSalary.text = employee?.salary.toString()
            textViewDetailAge.text = employee?.age.toString()

            val colorSalary = if (employee?.salary.orZero() > 1000)
                ContextCompat.getColor(root.context, R.color.green)
            else
                ContextCompat.getColor(root.context, R.color.red_error)

            textViewDetailSalary.setTextColor(colorSalary)
        }
    }

}