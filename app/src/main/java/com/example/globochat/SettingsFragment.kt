package com.example.globochat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*
import com.example.globochat.R.string.key_auto_reply


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       setPreferencesFromResource(R.xml.settings,rootKey)

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))
        accSettingsPref?.setOnPreferenceClickListener {

            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            return@setOnPreferenceClickListener true
        }

        val sharedPreference =PreferenceManager.getDefaultSharedPreferences(context)
        val autoReplyTime = sharedPreference.getString(getString(R.string.key_auto_reply_time),"")
        val autoDownload = sharedPreference.getBoolean(getString(R.string.key_auto_download),false)

        // we can use sharedPreferenceChangeListener but in activity which is in active state only
        // like main activity or setting activity in this case
        // implement it first

        val statusPref = findPreference<EditTextPreference>(getString(R.string.key_status))

        // this method is executed before changing in sharedPreference
        // the value true or false will determine if sharedPreference file will change or not
        statusPref?.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                val newStatus = newValue as String
                return !newStatus.contains("bad")
            }

        })

        val notificationPref = findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notif))
        notificationPref?.summaryProvider = object : Preference.SummaryProvider<SwitchPreferenceCompat>{
            override fun provideSummary(preference: SwitchPreferenceCompat?): CharSequence {
                return if (preference?.isChecked!!)
                    "status on"
                else
                    "status off"
            }
        }
    }

}