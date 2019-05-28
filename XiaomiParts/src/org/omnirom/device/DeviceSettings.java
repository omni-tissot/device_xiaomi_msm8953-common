/*
* Copyright (C) 2016 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.omnirom.device;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.TwoStatePreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.util.Log;

import org.omnirom.device.utils.FileUtils;

public class DeviceSettings extends PreferenceFragment {

    private static final String KEY_CATEGORY_DISPLAY = "display";
    private static final String KEY_HARDWARE_KEYS_DISABLE = "hardware_keys_disable";
    public static final String KEY_VIBSTRENGTH = "vib_strength";

    private TwoStatePreference mTapToWakeSwitch;
    private SwitchPreference mKeysDisablesSwitch;

    private Preference mKcalPref;
    private Preference mAmbientPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main, rootKey);

        mKcalPref = findPreference("kcal");
        mKcalPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), DisplayCalibration.class);
                startActivity(intent);
                return true;
            }
        });

	mAmbientPref = findPreference("ambient_display_gestures");
        mAmbientPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), TouchscreenGesturePreferenceActivity.class);
                startActivity(intent);
                return true;
            }
        });

        SwitchPreference mKeysDisablesSwitch = (SwitchPreference) findPreference(KEY_HARDWARE_KEYS_DISABLE);
        mKeysDisablesSwitch.setChecked(Settings.System.getInt(getActivity().getContentResolver(),
                    Settings.System.OMNI_HARDWARE_KEYS_DISABLE, 0) != 0);

        mKeysDisablesSwitch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
             @Override
             public boolean onPreferenceClick(Preference preference) {
                 if (preference == mKeysDisablesSwitch) {
                     Settings.System.putInt(getActivity().getContentResolver(),
                             Settings.System.OMNI_HARDWARE_KEYS_DISABLE, mKeysDisablesSwitch.isChecked() ? 1 : 0);
                     return true;
                 }
                 return false;
             }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }
}
