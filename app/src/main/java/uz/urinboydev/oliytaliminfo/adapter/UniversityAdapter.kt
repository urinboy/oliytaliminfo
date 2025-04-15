package uz.urinboydev.oliytaliminfo.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.urinboydev.oliytaliminfo.databinding.ItemUniversityBinding
import uz.urinboydev.oliytaliminfo.model.University
import uz.urinboydev.oliytaliminfo.ui.WebViewActivity

class UniversityAdapter : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {
    private var universities = listOf<University>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUniversities(newUniversities: List<University>) {
        universities = newUniversities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val binding = ItemUniversityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UniversityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(universities[position])
    }

    override fun getItemCount() = universities.size

    class UniversityViewHolder(private val binding: ItemUniversityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(university: University) {
            binding.apply {
                universityNameTextView.text = university.name
                regionTextView.text = university.region
                ownershipTypeTextView.text = university.ownershipType

                websiteButton.setOnClickListener {
                    val intent = Intent(itemView.context, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_URL, university.websiteUrl)
                        putExtra(WebViewActivity.EXTRA_TITLE, university.name)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}