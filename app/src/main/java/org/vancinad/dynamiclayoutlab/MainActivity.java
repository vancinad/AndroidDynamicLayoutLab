package org.vancinad.dynamiclayoutlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintProperties;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Using the layout and barrier defined in the XML file. Fields and labels will be built dynamically */
        ConstraintLayout dynamicLayout = findViewById(R.id.fieldsLayout);
        Barrier barrier = findViewById(R.id.barrier);
        int textBaseId = 1000, editBaseId = 2000;

        ArrayList<Station> stations = getStations();
        for (int i = 0; i < stations.size(); i++)
            CreateField(dynamicLayout, stations.get(i), barrier, editBaseId, textBaseId, i);

    }  //end: onCreate

    private void CreateField(ConstraintLayout dynamicLayout, Station station, Barrier barrier, int editBaseId, int textBaseId, int fieldIndex) {
        int editId = editBaseId + fieldIndex;
        int textId = textBaseId + fieldIndex;
        int layoutId = dynamicLayout.getId(); // get ids from the object instead of R.id.xxx, to decouple from R
        int barrierId = barrier.getId();

        ConstraintLayout.LayoutParams lp;

        EditText editText_edit = new EditText(this);
        editText_edit.setId(editId);

        editText_edit.setEms(6); // width of the edit field
        editText_edit.setInputType(InputType.TYPE_CLASS_NUMBER);

        lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);// editText_edit.getLayoutParams();
        lp.startToEnd = barrierId;
        if (fieldIndex > 0) // first field follows top of the layout, others follow the field above
            lp.topToBottom = editId - 1;
        else
            lp.topToTop = layoutId;

        dynamicLayout.addView(editText_edit, lp);

        TextView textView_Label = new TextView(this);
        textView_Label.setId(textId);
        textView_Label.setText(String.format("%s (%.1f)", station.name, station.station));
        lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); // textView_Label.getLayoutParams();
        lp.baselineToBaseline = editId;
        lp.startToStart = layoutId;
        dynamicLayout.addView(textView_Label, lp);

        barrier.addView(textView_Label);

    } // end: CreateField

    ArrayList<Station> getStations() {
        ArrayList<Station> stations = new ArrayList<>();

        stations.add(new Station(-20.0, "Nose baggage*"));
        stations.add(new Station(46.0, "Fuel"));
        stations.add(new Station(37.0, "Front"));
        stations.add(new Station(48.5, "Right wing baggage"));
        stations.add(new Station(48.5, "Left wing baggage"));
        stations.add(new Station(73.0, "Rear"));
        stations.add(new Station(95.0, "Fuselage baggage 1"));
        stations.add(new Station(123.0, "Fuselage baggage 2"));

        return stations;
    }

    class Station {
        public Double station; //distance from datum
        public String name;

        Station(Double station, String name) {
            this.station = station;
            this.name = name;
        }
    }
}