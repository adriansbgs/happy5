package com.ftw.happy5test.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ftw.happy5test.R
import com.ftw.happy5test.databinding.ItemMoviesBinding
import com.ftw.happy5test.model.Movies
import com.ftw.happy5test.utils.BASE_URL_IMG
import java.math.RoundingMode
import java.text.DecimalFormat

class MoviesAdapter(
    private val dataMovies: List<Movies> = listOf(),
    private var listener: (Movies) -> Unit = fun(_) {}
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    fun setListener(listener: (Movies) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movies,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.itemMoviesBinding.movie = dataMovies[position]
        holder.itemMoviesBinding.ratingBar.rating = ratingStar(dataMovies[position].vote_average)
        holder.itemMoviesBinding.itemMovies.setOnClickListener {
            listener.invoke(dataMovies[position])
        }
    }

    override fun getItemCount(): Int = dataMovies.size

    inner class MoviesViewHolder(val itemMoviesBinding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(itemMoviesBinding.root) {
    }

    private fun ratingStar(ratingData: Double): Float {
        var rating = ratingData / 2
        val getLastDecimal = rating.toString().takeLast(2)

        return if (getLastDecimal.toDouble() != 0.5) {
            val df = DecimalFormat("#")
            df.roundingMode = RoundingMode.HALF_EVEN
            rating = (df.format(rating).toDouble())
            rating.toFloat()
        } else rating.toFloat()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(posterImg: ImageView, url: String) {
            if (!url.isNullOrEmpty()) {
                Glide.with(posterImg)
                    .load(BASE_URL_IMG + url)
                    .transform(RoundedCorners(20))
                    .into(posterImg)
            }
        }
    }
}
