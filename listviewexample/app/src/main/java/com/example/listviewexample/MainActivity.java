package com.example.listviewexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CharacterAdapter adapter;
    private ArrayList<HashMap<String, String>> characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        characterList = new ArrayList<>();
        adapter = new CharacterAdapter(this, characterList);
        listView.setAdapter(adapter);

        new FetchCharactersTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedCharacter = characterList.get(position);

                if (findViewById(R.id.detailContainer) != null) { // Tablet mode
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", selectedCharacter.get("name"));
                    bundle.putString("height", selectedCharacter.get("height"));
                    bundle.putString("mass", selectedCharacter.get("mass"));
                    detailsFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, detailsFragment)
                            .commit();
                } else { // Phone mode
                    Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                    intent.putExtra("name", selectedCharacter.get("name"));
                    intent.putExtra("height", selectedCharacter.get("height"));
                    intent.putExtra("mass", selectedCharacter.get("mass"));
                    startActivity(intent);
                }
            }
        });
    }

    private class FetchCharactersTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONArray characters = new JSONObject(response).getJSONArray("results");
                for (int i = 0; i < characters.length(); i++) {
                    JSONObject character = characters.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", character.getString("name"));
                    map.put("height", character.getString("height"));
                    map.put("mass", character.getString("mass"));
                    characterList.add(map);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
