package com.nutrikares.nutrideskapp

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val clearCachePref: Preference? = findPreference("cache")

        clearCachePref?.setOnPreferenceClickListener {
            context?.cacheDir?.let {
                var size = 0
                it.listFiles()?.forEach { file ->
                    size += file.length().toInt()
                    file.delete()
                }
                size = size.div(1024).div(1024)
                Toast.makeText(context, "$size MB eliminados", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

}
