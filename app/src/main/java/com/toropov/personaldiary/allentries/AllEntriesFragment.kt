package com.toropov.personaldiary.allentries
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.toropov.personaldiary.R
import com.toropov.personaldiary.database.Entry
import com.toropov.personaldiary.database.EntryDatabase
import com.toropov.personaldiary.databinding.FragmentAllEntriesBinding
import com.toropov.personaldiary.getAllUniqTags

class AllEntriesFragment : Fragment(), EntriesAdapter.OnItemClickListener, FilterAdapter.OnFilterItemClickListener {

    private lateinit var viewModel: AllEntriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAllEntriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_entries, container, false)

        val application = requireNotNull(this.activity).application
        val dao = EntryDatabase.getInstance(application).getEntryDatabaseDao()
        val viewModelFactory = AllEntriesViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AllEntriesViewModel::class.java)

        val adapterEntries = EntriesAdapter(this)
        binding.entryList.adapter = adapterEntries

        val adapterFilter = FilterAdapter(this)
        binding.filterList.adapter = adapterFilter

        viewModel.entries.observe(viewLifecycleOwner, Observer { entries ->
            if (entries != null){
                adapterEntries.data = entries
            }
        })

        viewModel.dbUpdate.observe(viewLifecycleOwner, Observer { entries ->
            if (entries != null){
                val tags = getAllUniqTags(entries)
                adapterFilter.data = tags
            }
        })

        binding.buttonCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_allEntriesFragment_to_createEntryFragment)
        }

        return binding.root
    }

    override fun onDeleteClicked(entry: Entry) {
        viewModel.onDelete(entry)
    }

    override fun onUpdateClicked(entry: Entry) {
        this.findNavController().navigate(AllEntriesFragmentDirections
            .actionAllEntriesFragmentToUpdateEntryFragment(entry.entryId, entry.entryText, entry.entryTags))
    }

    override fun onFilterClicked(tag: String, selected: Boolean) {
        if (selected){
            viewModel.setFilter(tag)
        } else {
            viewModel.setFilter("%")
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}