package com.example.mce_pro


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_management.*


class Management_Activity:AppCompatActivity() {
    var toolimage:ImageView?=null
var recyc_management:RecyclerView?=null
    var images= arrayOf(R.drawable.charman,R.drawable.director,R.drawable.principal)
    var messages= arrayOf("Chairman’s Message","Managing Director’s Message","Principal’s Message")
    var quotes= arrayOf("“The reward of every good action is triumph”","“Knowledge brings forth indefatigable novelty”","“Education scales you up to a new pinnacle of strength and success”")
var description= arrayOf("Our institution, uncompromisingly, creates professionally well-equipped and service-minded young graduates who can face any kind of situation and can offer best solutions for a better world. The academic career of Meenakshi College of Engineering enables students to solve the scientific and practical problems which have an impact in all aspects of life, eventually nurturing self-ignited students for the benefit of the entire community. Today, under the umbrella of this institution, young talented engineers emerge out with flying colors to beautify the world of tomorrow. I congratulate the management and faculty members for their dedicated service rendered to raise the college to the level of best engineering institutes.\n" +
        "\n" +
        "In coming years, we are planning to extend courses in creating professionals who are socially responsible and work for the benefit of fellow beings. The management wishes to extend its strong support to the young and aspiring generation.\n" +
        "\n" +
        "We welcome you to our regime in sharing our journey.","We strive hard to excel in academics using innovative teaching methods and instil sustainable leadership in the minds of young budding professionals. We follow the path of our Chairman who has been instrumental in making a niche in the field of education. Our students come from diverse backgrounds and we are determined to give a holistic view of learning through an interactive learning process. We are fortunate to have a team of highly committed teaching faculty to ensure the learning ambience of our students is accomplished.\n" +
        "\n" +
        "Our UG and PG students have secured university ranks in the Anna University Examinations, which really makes us feel proud of our students and their mettle. Majority of our students are placed in reputed globally acclaimed companies as well as in government sectors. I am proud to say that we toil to attain pinnacle of success as envisioned by our Chairman.\n" +
        "\n" +
        "We look forward to build long lasting relationship with you.","New academic year, filled with hope and dreams of scaling new heights, it is my privilege and pleasure to welcome all students of Under-graduate and Post-graduate courses. We are glad to be involved in moulding our students and to foster in them the values of personal discipline, leadership, entrepreneurship and integrity.\n" +
        "\n" +
        "We always wish to instil a passion for learning in the minds of our students which will enable them to gain knowledge and understand concepts clearly which will help them in future to achieve success globally. We reinforce our students to plan and prioritize their studies in order to stay focused on successful yield.\n" +
        "\n" +
        "Dedication and perseverance are the most important traits which will help us to taste the fruit of success. Our resourceful teachers and excellent infrastructure of our college help our students to reach their goals in life. We encourage students to take part in co-curricular and extra -curricular activities that are offered in our college. The overall development of a person is a sign of healthy being.\n" +
        "\n" +
        "Wishing to create an ambience of victory and everlasting progress in the years to come.")

    var sign= arrayOf("– Thiru A.N. Radhakrishnan","– Mrs. Jayanthi Prabhakaran","– Dr. R. RAJA")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)
toolimage=findViewById(R.id.tool_eimage)
        recyc_management=findViewById(R.id.recyc_management)

        /////recyclerview//
        var lm=LinearLayoutManager(this)
        lm.orientation=LinearLayoutManager.VERTICAL
        recyc_management!!.layoutManager=lm
        recyc_management!!.adapter=Adapter_Management(this,images,messages,quotes,description,sign)
        /////////////////////


        Glide.with(this).load(Shared_pref(this).getphoto()).into(toolimage!!)
        var toolbar=findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)

        val mAppBarLayout = findViewById<AppBarLayout>(R.id.appbarlayout)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        ///////the objective////




        ///////////////////////////


        //////////////scrolling state////

        mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if(scrollRange==-1){
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    card!!.visibility=View.GONE

                } else if (isShow) {
                    isShow = false

                    card!!.visibility=View.VISIBLE
                }
            }

        })
////////////////////////////////////////////////////////

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}