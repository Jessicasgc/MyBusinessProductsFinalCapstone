package com.jessicaamadearahma.mybusinessproducts.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.jessicaamadearahma.mybusinessproducts.R
import com.jessicaamadearahma.mybusinessproducts.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val modePref = findPreference<ListPreference>("mode")
            modePref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener {_, modeValue ->
                val modeSettings =
                    when(modeValue){
                        getString(R.string.pref_auto) -> ModeSettings.AUTO
                        getString(R.string.pref_light_mode) -> ModeSettings.OFF
                        getString(R.string.pref_dark_mode) -> ModeSettings.ON
                        else -> ModeSettings.AUTO
                    }
                    mode(modeSettings.value)
                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    sharedPreferences.edit().putString("selected_mode", modeValue as String?).apply()
                    true
            }
        }
        private fun mode(nightMode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(nightMode)
            requireActivity().recreate()
            return true
        }
    }
}