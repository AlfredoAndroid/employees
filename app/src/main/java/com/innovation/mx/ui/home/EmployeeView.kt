package com.innovation.mx.ui.home

import androidx.core.content.ContextCompat
import com.innovation.mx.R
import com.innovation.mx.databinding.ViewEmployeeItemBinding
import com.innovation.mx.models.EmployeeModel
import com.xwray.groupie.databinding.BindableItem

class EmployeeView(
    private val employee: EmployeeModel,
    private val listener: (EmployeeModel) -> Unit
) : BindableItem<ViewEmployeeItemBinding>() {
    override fun bind(viewBinding: ViewEmployeeItemBinding, position: Int) {
        viewBinding.apply {
            textViewId.text = employee.id.toString()
            textViewName.text = employee.name
            textViewSalary.text = employee.salary.toString()
            textViewAge.text = employee.age.toString()

            val colorAge = if (employee.age > 25 && employee.age < 35)
                ContextCompat.getColor(root.context, R.color.green)
            else
                ContextCompat.getColor(root.context, R.color.red_error)

            textViewAge.setTextColor(colorAge)

            cardView.setOnClickListener {
                listener(employee)
            }

        }
    }

    override fun getLayout() = R.layout.view_employee_item

}