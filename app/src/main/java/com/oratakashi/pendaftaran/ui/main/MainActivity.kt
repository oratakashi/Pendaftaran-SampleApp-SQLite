package com.oratakashi.pendaftaran.ui.main

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oratakashi.pendaftaran.R
import com.oratakashi.pendaftaran.data.db.DatabaseBuilder
import com.oratakashi.pendaftaran.data.db.DatabaseBuilder.Callback
import com.oratakashi.pendaftaran.data.db.DatabaseCallback
import com.oratakashi.pendaftaran.data.model.Students
import com.oratakashi.pendaftaran.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), Callback, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val data : MutableList<Students> = ArrayList()

    lateinit var backup : Students

    private val adapter : MainAdapter by lazy {
        MainAdapter(data)
    }

    private val builder : DatabaseBuilder by lazy {
        DatabaseBuilder(applicationContext)
    }

    private val p = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listener()

        fab.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        rvMain.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        srMain.setOnRefreshListener {
            srMain.isRefreshing = false
            launch {
                data.clear()
                data.addAll(builder.all())
                withContext(Dispatchers.Main){
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        launch {
            data.clear()
            data.addAll(builder.all())
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun listener(){
        val callback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                backup = data[viewHolder.adapterPosition]
                builder.delete(data[viewHolder.adapterPosition].id!!, this@MainActivity)
                data.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                val itemView: View = viewHolder.itemView
                if (dX > 0) {
                    p.color = Color.RED
                    val background = RectF(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        dX,
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, p)
                } else {
                    p.color = Color.RED
                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, p)
                }
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(rvMain)
    }

    override fun callback(callback: DatabaseCallback) {
        if(callback == DatabaseCallback.Delete){
            Snackbar.make(clBase, "Data berhasil terhapus", Snackbar.LENGTH_LONG)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setAction("Batalkan"){
                    launch {
                        builder.insert(backup, this@MainActivity)
                        data.clear()
                        data.addAll(builder.all())
                        withContext(Dispatchers.Main){
                            adapter.notifyDataSetChanged()
                        }
                    }
                }.show()
        }else{
            Toast.makeText(applicationContext, "Perubahan berhasil dibatalkan!", Toast.LENGTH_SHORT).show()
        }
    }
}