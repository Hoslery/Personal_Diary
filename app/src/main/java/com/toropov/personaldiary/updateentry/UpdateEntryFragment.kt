package com.toropov.personaldiary.updateentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.toropov.personaldiary.R
import com.toropov.personaldiary.database.EntryDatabase
import com.toropov.personaldiary.databinding.FragmentUpdateEntryBinding
import com.toropov.personaldiary.validateEntryTags
import com.toropov.personaldiary.validateEntryText

class UpdateEntryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUpdateEntryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_entry, container, false)

        val args = UpdateEntryFragmentArgs.fromBundle(requireArguments())
        binding.apply {
            editEntryUpdateText.setText(args.entryText)
            editEntryUpdateTags.setText(args.entryTags)
        }

        val application = requireNotNull(this.activity).application

        val dao = EntryDatabase.getInstance(application).getEntryDatabaseDao()
        val viewModelFactory = UpdateEntryViewModelFactory(args.entryId, dao, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UpdateEntryViewModel::class.java)

        binding.doneButton.setOnClickListener {
            val text: String? = validateEntryText(binding.editEntryUpdateText.text.toString())
            val tags: String? = validateEntryTags(binding.editEntryUpdateTags.text.toString())
            if (text == null){
                binding.editEntryUpdateText.error = resources.getString(R.string.error_text)
            } else if(tags == null) {
                binding.editEntryUpdateTags.error = resources.getString(R.string.error_tags)
            } else
                viewModel.updateEntry(text,tags)
        }

        viewModel.navigateToAllEntries.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate!!){
                findNavController().navigate(R.id.action_updateEntryFragment_to_allEntriesFragment)
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = resources.getString(R.string.update_entry_title)
    }
}