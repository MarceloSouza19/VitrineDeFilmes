package com.example.android.vitrinedefilmes.Preferencias;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.vitrinedefilmes.Principal.TelaInicial;
import com.example.android.vitrinedefilmes.R;

public class Preferencias extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new AtividadeConfiguracoesFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, TelaInicial.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class AtividadeConfiguracoesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.configuracoes);

            bindSummaryValue(findPreference(getString(R.string.codigo_api)));
            bindSummaryValue(findPreference(getString(R.string.tipo_busca_vitrine)));

        }
    }

    private static void bindSummaryValue(Preference preference) {
        preference.setOnPreferenceChangeListener(preferenceChangeListener);

        preferenceChangeListener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(
                preference.getContext()).getString(
                preference.getKey(), ""));

    }

    private static Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object valorAlterado) {

            String valorASetar = valorAlterado.toString();

            if (preference instanceof ListPreference) {

                ListPreference listaValores = (ListPreference) preference;

                int index = listaValores.findIndexOfValue(valorASetar);

                preference.setSummary(index >= 0 ? listaValores.getEntries()[index] : null);
            } else if (preference instanceof EditTextPreference) {
                preference.setSummary(valorASetar);
            }
            return true;
        }
    };
}
