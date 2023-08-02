package com.jessicaamadearahma.mybusinessproducts.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.jessicaamadearahma.mybusinessproducts.R
import com.jessicaamadearahma.mybusinessproducts.databinding.ActivityMainBinding
import com.jessicaamadearahma.mybusinessproducts.ui.listproducts.ListProductsFragment
import com.jessicaamadearahma.mybusinessproducts.ui.listproducts.ListProductsViewModel
import com.jessicaamadearahma.mybusinessproducts.ui.settings.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val listProductsViewModel: ListProductsViewModel by viewModel()
    private lateinit var binding : ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_list_products, ListProductsFragment.newInstance())
                .commitNow()
        }

        binding.fabFavoritesDynamic.setOnClickListener { view ->
            val uri = Uri.parse("mybusinessproducts://favoriteproducts")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
    private fun registerBroadCastReceiver(){
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        Snackbar.make(binding.root, getString(R.string.connected), Snackbar.LENGTH_SHORT).show()
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        Snackbar.make(binding.root, getString(R.string.disconnected), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }
}