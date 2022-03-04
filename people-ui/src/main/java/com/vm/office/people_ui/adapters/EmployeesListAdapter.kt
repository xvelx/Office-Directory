package com.vm.office.people_ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.common_ui.utils.registerViewForEmailSender
import com.vm.office.people_ui.R
import com.vm.office.people_ui.databinding.ItemEmployeeBinding
import com.vm.office.people_ui.fragments.EmployeeListFragmentDirections
import com.vm.office.people_ui.states.data.EmployeeUiData

/**
 * Employee List Adapter that takes list of EmployeeUiData and configures
 * the list view with the help of its associated ViewHolder (EmployeeViewHolder)
 */
class EmployeesListAdapter(private val employees: List<EmployeeUiData>) :
    RecyclerView.Adapter<EmployeesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesListViewHolder {
        return EmployeesListViewHolder(
            ItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: EmployeesListViewHolder, position: Int) {
        viewHolder.bindEmployeeData(employees[position])
    }

    override fun getItemCount(): Int = employees.size
}

/**
 * View Holder that configures and manages the views that presents employee data.
 */
class EmployeesListViewHolder(private val itemEmployeeBinding: ItemEmployeeBinding) :
    RecyclerView.ViewHolder(itemEmployeeBinding.root) {

    private val context: Context = itemEmployeeBinding.root.context

    /**
     * Binds the EmployeeUiData with the view.
     */
    fun bindEmployeeData(employee: EmployeeUiData) {
        itemEmployeeBinding.employee = employee
        if (!context.resources.getBoolean(R.bool.isTablet)) {
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(EmployeeListFragmentDirections.toEmployeeDetail(employee))
            }
        }

        context.registerViewForEmailSender(
            itemEmployeeBinding.sendEmail,
            employee.emailId,
            R.string.emailAppChooser
        )
    }
}