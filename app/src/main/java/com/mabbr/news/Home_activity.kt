package com.mabbr.news

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.mabbr.news.ui.category
import com.mabbr.news.ui.news.newsFragment
import com.mabbr.news.ui.setting_fragment
import com.mabbr.news.ui.categories.categore_fragment as categoriesfragment

class Home_activity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerbottom: ImageView
    lateinit var categorie: View
    lateinit var setting: View

    val categoreFragment = categoriesfragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initview()
        pushfragment(categoreFragment)
        categoreFragment.onCategoryClickListener =
            object : categoriesfragment.OnCategoryClickListener {
                override fun oncategoryClick(category: category) {
                    pushfragment(newsFragment.getInstance(category), true)
                }

            }


    }

    fun initview() {
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerbottom = findViewById(R.id.menu_bottom)
        categorie = findViewById(R.id.categore)
        setting = findViewById(R.id.setting)
        drawerbottom.setOnClickListener {
            drawerLayout.open()
        }
        categorie.setOnClickListener {
            pushfragment(categoreFragment)

        }
        setting.setOnClickListener {
            pushfragment(setting_fragment())

        }
    }

    fun pushfragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_container, fragment)
        if (addToBackStack)
            fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
        drawerLayout.close()
    }

}
