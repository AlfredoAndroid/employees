package com.innovation.mx.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.innovation.mx.R
import com.innovation.mx.databinding.FragmentHomeBinding
import com.innovation.mx.models.EmployeeModel
import com.innovation.mx.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private var listener: HomeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? HomeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.getEmployees()
            }
        }

        bindViewModel()
        initRecyclerView()
        viewModel.getEmployees()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun bindViewModel() {
        viewModel.employees.observe(viewLifecycleOwner, Observer(this::populateEmployees))
        viewModel.error.observe(viewLifecycleOwner, Observer(this::showError))
    }

    private fun initRecyclerView() {
        binding.recyclerEmployees.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = groupAdapter
        }
    }

    private fun populateEmployees(employees: List<EmployeeModel>) {
        binding.swipeRefresh.isRefreshing = false
        groupAdapter.clear()
        groupAdapter.addAll(
            employees.map {
                EmployeeView(it) { employee ->
                    listener?.showDetail(employee)
                }
            }
        )
    }

    private fun showError(msg: String) {
        binding.swipeRefresh.isRefreshing = false
        requireActivity().showMessage(msg)
    }

    interface HomeListener {
        fun showDetail(employee: EmployeeModel)
    }
}