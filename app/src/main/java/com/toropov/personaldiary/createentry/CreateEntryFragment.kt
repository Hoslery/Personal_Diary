package com.toropov.personaldiary.createentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.toropov.personaldiary.R
import com.toropov.personaldiary.database.EntryDatabase
import com.toropov.personaldiary.databinding.FragmentCreateEntryBinding
import com.toropov.personaldiary.validateEntryTags
import com.toropov.personaldiary.validateEntryText


class CreateEntryFragment : Fragment() {

    private lateinit var viewModel: CreateEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCreateEntryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_entry, container, false)

        val application = requireActivity().application
        val dao = EntryDatabase.getInstance(application).getEntryDatabaseDao()

        val viewModelFactory = CreateEntryViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CreateEntryViewModel::class.java)

        binding.doneButton.setOnClickListener {
            val text: String? = validateEntryText(binding.editEntryCreateText.text.toString())
            val tags: String? = validateEntryTags(binding.editEntryCreateTags.text.toString())
            if (text == null){
                binding.editEntryCreateText.error = resources.getString(R.string.error_text)
            } else if(tags == null) {
                binding.editEntryCreateTags.error = resources.getString(R.string.error_tags)
            } else
                viewModel.addEntry(text,tags)
        }

        viewModel.navigateToAllEntries.observe(viewLifecycleOwner, Observer { entry ->
            if (entry != null){
                findNavController().navigate(R.id.action_createEntryFragment_to_allEntriesFragment)
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = resources.getString(R.string.create_entry_title)
    }

}