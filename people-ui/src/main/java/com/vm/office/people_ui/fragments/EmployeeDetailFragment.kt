package com.vm.office.people_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.vm.office.common_ui.fragments.BaseBottomSheetDialogFragment
import com.vm.office.common_ui.utils.registerViewForEmailSender
import com.vm.office.people_ui.R
import com.vm.office.people_ui.databinding.FragmentEmployeeDetailBinding

/**
 * Bottom Sheet - Presents full information of the employee.
 *
 * `Note:` This is applicable only for the phones. (device >= w800dp wouldn't use this sheet)
 */
class EmployeeDetailFragment : BaseBottomSheetDialogFragment<FragmentEmployeeDetailBinding>() {

    private val employeeDetailArgs: EmployeeDetailFragmentArgs by navArgs()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEmployeeDetailBinding = FragmentEmployeeDetailBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.employee = employeeDetailArgs.employeeDetail
        context?.registerViewForEmailSender(
            viewBinding.sendEmailButton,
            employeeDetailArgs.employeeDetail.emailId,
            R.string.emailAppChooser
        )
    }
}