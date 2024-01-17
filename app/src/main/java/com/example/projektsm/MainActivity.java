package com.example.projektsm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projektsm.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private JedzenieViewModel jedzenieViewModel;
    private TextView dolnyTekst;
    public static final int NEW_JEDZENIE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_JEDZENIE_ACTIVITY_REQUEST_CODE = 2;
    public static final int  NEW_JEDZENIE_GOTOWE_ACTIVITY_REQUEST_CODE = 3;
    private Jedzenie editedJedzenie = null;
    SwipeListener swipeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final JedzenieAdapter adapter = new JedzenieAdapter();
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dolnyTekst = findViewById(R.id.bottomText);

        jedzenieViewModel = ViewModelProviders.of(this).get(JedzenieViewModel.class);
        jedzenieViewModel.findAll().observe(this, new Observer<List<Jedzenie>>() {
            @Override
            public void onChanged(@Nullable final List<Jedzenie> jedzenia) {
                if (jedzenia != null && !jedzenia.isEmpty()) {
                    // Filtruj jedzenie z dzisiejszego dnia
                    List<Jedzenie> filteredJedzenia = filterTodayJedzenia(jedzenia);

                    adapter.setJedzenia(filteredJedzenia);
                    ustawDolnyTekst(filteredJedzenia);
                } else {
                    adapter.setJedzenia(jedzenia);
                    ustawDolnyTekst(jedzenia);
                }
            }
        });

//        FloatingActionButton addBookButton = findViewById(R.id.add_button);
//        addBookButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, EditJedzenieActivity.class);
//                startActivityForResult(intent, NEW_JEDZENIE_ACTIVITY_REQUEST_CODE);
//            }
//        });

        swipeListener = new SwipeListener(recyclerView);
    }
    private boolean isToday(Date date, long currentDateMillis) {
        // Konwertuj datę na milisekundy
        long dateMillis = date.getTime();

        // Implementacja tego sprawdzenia, np. z pomocą Calendar
        // Poniżej znajdziesz przykład z użyciem Calendar
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(dateMillis);
        cal2.setTimeInMillis(currentDateMillis);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private List<Jedzenie> filterTodayJedzenia(List<Jedzenie> allJedzenia) {
        List<Jedzenie> todayJedzenia = new ArrayList<>();
        long currentDateMillis = System.currentTimeMillis();

        for (Jedzenie jedzenie : allJedzenia) {
            if (isToday(jedzenie.getDataDodania(), currentDateMillis)) {
                todayJedzenia.add(jedzenie);
            }
        }

        return todayJedzenia;
    }



    private class SwipeListener implements View.OnTouchListener {
        GestureDetector gestureDetector;

        SwipeListener(View view) {
            int threshold = 100;
            int velocity_threshold = 50;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(@NonNull MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                            try {
                                float xDiff;
                                if(e1 == null) {
                                    xDiff = e2.getX();
                                } else {
                                    xDiff = e2.getX() - e1.getX();
                                }
                                if(xDiff > threshold && Math.abs(velocityX) > velocity_threshold) {
                                    showSnackbar(getString(R.string.jedzenie_archived));
                                    return true;
                                }
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                            return false;
                        }
                    };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jedzenie_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.new_jedzenie:
                Intent intent = new Intent(MainActivity.this, EditJedzenieActivity.class);
                startActivityForResult(intent, NEW_JEDZENIE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.new_gotowe:
                Intent intent2 = new Intent(MainActivity.this, DodajGotoweActivity.class);
                startActivityForResult(intent2, NEW_JEDZENIE_GOTOWE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.cytat_menu:
                Intent intent3 = new Intent(MainActivity.this, CytatActivity.class);
                startActivity(intent3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_JEDZENIE_ACTIVITY_REQUEST_CODE) {
                Jedzenie jedzenie = new Jedzenie(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_NAZWA),
                        Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_TLUSZCZE)),
                        Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_BIALKA)),
                        Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_WEGLOWODANY)),
                        Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_KALORIE)));
                jedzenieViewModel.insert(jedzenie);
                Snackbar.make(findViewById(R.id.coordinator_layout),
                                getString(R.string.jedzenie_added),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
            else if (requestCode == EDIT_JEDZENIE_ACTIVITY_REQUEST_CODE) {
                editedJedzenie.setNazwa(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_NAZWA));
                editedJedzenie.setKalorie(Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_KALORIE)));
                editedJedzenie.setTluszcze(Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_TLUSZCZE)));
                editedJedzenie.setBialka(Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_BIALKA)));
                editedJedzenie.setWeglowodany(Integer.parseInt(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_WEGLOWODANY)));
                editedJedzenie.setSciezkaZdjecia(data.getStringExtra(EditJedzenieActivity.EXTRA_EDIT_ZDJECIE_SCIEZKA));
                Log.d("sciezka v3",editedJedzenie.getSciezkaZdjecia());
                jedzenieViewModel.update(editedJedzenie);
                editedJedzenie = null;
                Snackbar.make(findViewById(R.id.coordinator_layout),
                                getString(R.string.jedzenie_edited),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
            else if(requestCode == NEW_JEDZENIE_GOTOWE_ACTIVITY_REQUEST_CODE)
            {
                Jedzenie jedzenie = new Jedzenie(data.getStringExtra(DodajGotoweActivity.EXTRA_EDIT_JEDZENIE_NAZWA),
                        Integer.parseInt(data.getStringExtra(DodajGotoweActivity.EXTRA_EDIT_JEDZENIE_TLUSZCZE)),
                        Integer.parseInt(data.getStringExtra(DodajGotoweActivity.EXTRA_EDIT_JEDZENIE_BIALKA)),
                        Integer.parseInt(data.getStringExtra(DodajGotoweActivity.EXTRA_EDIT_JEDZENIE_WEGLOWODANY)),
                        Integer.parseInt(data.getStringExtra(DodajGotoweActivity.EXTRA_EDIT_JEDZENIE_KALORIE)));
                jedzenieViewModel.insert(jedzenie);
                Snackbar.make(findViewById(R.id.coordinator_layout),
                                getString(R.string.jedzenie_added),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
        }
        else
            Snackbar.make(findViewById(R.id.coordinator_layout),
                            getString(R.string.empty_not_saved),
                            Snackbar.LENGTH_LONG)
                    .show();


    }

    private void ustawDolnyTekst(List<Jedzenie> jedzenia)
    {
        int kalorie=0,bialka=0, tluszcze=0, weglowodany=0;
        String tekst="";
        if (jedzenia != null && !jedzenia.isEmpty()) {
            for (Jedzenie jedzenie : jedzenia) {
                kalorie+=jedzenie.getKalorie();
                bialka+=jedzenie.getBialka();
                tluszcze+=jedzenie.getTluszcze();
                weglowodany+=jedzenie.getWeglowodany();
            }
        }
        tekst = "kalorie: "+kalorie+"kcal białka: "+bialka+"g tłuszcze: "+tluszcze+"g \n węglowodany: "+weglowodany+"g";
        dolnyTekst.setText(tekst);
    }

    private class JedzenieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView jedzenieNazwaTextView;
        private TextView jedzenieKalorieTextView;

        private Jedzenie jedzenie;

        public JedzenieHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.jedzenie_list_item, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            jedzenieNazwaTextView = itemView.findViewById(R.id.jedzenie_nazwa);
            jedzenieKalorieTextView = itemView.findViewById(R.id.jedzenie_kalorie);
        }

        public void bind(Jedzenie jedzenie) {
            this.jedzenie = jedzenie;
            jedzenieNazwaTextView.setText(jedzenie.getNazwa());
            jedzenieKalorieTextView.setText(String.valueOf(jedzenie.getKalorie())+" kcal");
        }

        @Override
        public void onClick(View v) {
            MainActivity.this.editedJedzenie = this.jedzenie;
            Intent intent = new Intent(MainActivity.this, EditJedzenieActivity.class);
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_NAZWA, jedzenie.getNazwa());
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_KALORIE,String.valueOf(jedzenie.getKalorie()) );
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_TLUSZCZE,String.valueOf(jedzenie.getTluszcze()) );
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_BIALKA,String.valueOf(jedzenie.getBialka()) );
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_JEDZENIE_WEGLOWODANY,String.valueOf(jedzenie.getWeglowodany()) );
            intent.putExtra(EditJedzenieActivity.EXTRA_EDIT_ZDJECIE_SCIEZKA,jedzenie.getSciezkaZdjecia());
            startActivityForResult(intent, EDIT_JEDZENIE_ACTIVITY_REQUEST_CODE);
        }

        @Override
        public boolean onLongClick(View v) {
            MainActivity.this.jedzenieViewModel.delete(this.jedzenie);
            return true;
        }
    }

    private class JedzenieAdapter extends RecyclerView.Adapter<JedzenieHolder> {
        private List<Jedzenie> jedzenia;

        @NonNull
        @Override
        public JedzenieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JedzenieHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JedzenieHolder holder, int position) {
            if (jedzenia != null) {
                Jedzenie jedzenie = jedzenia.get(position);
                holder.bind(jedzenie);
            }
            else
                Log.d("MainActivity", "Brak jedzenia");
        }

        @Override
        public int getItemCount() {
            if (jedzenia != null)
                return jedzenia.size();
            return 0;
        }

        void setJedzenia(List<Jedzenie> jedzenia) {
            this.jedzenia = jedzenia;
            notifyDataSetChanged();
        }
        public List<Jedzenie> getJedzenia()
        {
            return this.jedzenia;
        }

    }
}