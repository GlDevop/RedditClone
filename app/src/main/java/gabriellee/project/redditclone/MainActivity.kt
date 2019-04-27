package gabriellee.project.redditclone

import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import gabriellee.project.redditclone.networking.RedditPost

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = RedditAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeList()
    }

    private fun initializeList() {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        val liveData = initializedPagedListBuilder(config).build()

        liveData.observe(this, Observer<PagedList<RedditPost>>{
            pagedList -> adapter.submitList(pagedList)
        })

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<String, RedditPost> {
        val dataSourceFactory = object : DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                return RedditDataSource()
            }
        }

        return LivePagedListBuilder<String, RedditPost>(dataSourceFactory, config)
    }
}
