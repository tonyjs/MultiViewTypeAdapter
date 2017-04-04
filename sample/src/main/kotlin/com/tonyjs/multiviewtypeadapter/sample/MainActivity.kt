package com.tonyjs.multiviewtypeadapter.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

/**
 * Created by junseon on 2017. 4. 3..
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with((findViewById(R.id.recyclerView) as RecyclerView)) {
            adapter = SampleAdapter().apply {
                addItem(Pair("Hello World", SampleAdapter.ViewType.HEADER.value))
                "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",").forEach {
                    addItem(1.0f, SampleAdapter.ViewType.DIVIDER.value)
                    addItem(it, SampleAdapter.ViewType.TEXT.value)
                }
            }
        }
    }

}