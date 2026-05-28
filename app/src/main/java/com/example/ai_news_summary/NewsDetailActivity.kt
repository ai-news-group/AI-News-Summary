package com.example.ai_news_summary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ai_news_summary.core.model.News

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvSource: TextView
    private lateinit var tvContent: TextView
    private lateinit var btnFavorite: Button
    private lateinit var btnShare: Button

    private var news: News? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        initViews()
        loadNewsData()
        setupListeners()
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tv_detail_title)
        tvTime = findViewById(R.id.tv_detail_time)
        tvSource = findViewById(R.id.tv_detail_source)
        tvContent = findViewById(R.id.tv_detail_content)
        btnFavorite = findViewById(R.id.btn_favorite)
        btnShare = findViewById(R.id.btn_share)
    }

    private fun loadNewsData() {
        news = intent.getSerializableExtra("news") as? News

        news?.let {
            tvTitle.text = it.title
            tvTime.text = it.time
            tvSource.text = it.source ?: "未知来源"
            tvContent.text = generateMockContent(it)
            isFavorite = it.isFavorite
            updateFavoriteButton()
        }
    }

    private fun generateMockContent(news: News): String {
        return """
            ${news.title}
            
            【新闻摘要】
            ${news.desc}
            
            【详细内容】
            这是一篇关于${news.title}的详细报道。随着技术的不断发展，${news.title}正在改变我们的生活方式。
            
            据了解，相关领域的专家表示，这一进展具有重要意义。未来，我们可以期待更多的创新和突破。
            
            更多相关内容，请持续关注我们的更新。
            
            （${news.source ?: "本站"} 记者报道）
        """.trimIndent()
    }

    private fun setupListeners() {
        btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            news?.isFavorite = isFavorite
            updateFavoriteButton()
            val msg = if (isFavorite) "已添加到收藏" else "已取消收藏"
            Toast.makeText(this@NewsDetailActivity, msg, Toast.LENGTH_SHORT).show()
        }

        btnShare.setOnClickListener {
            news?.let {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${it.title}\n\n${it.desc}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "分享新闻"))
            }
        }
    }

    private fun updateFavoriteButton() {
        btnFavorite.text = if (isFavorite) "❤️ 已收藏" else "♡ 收藏"
    }
}