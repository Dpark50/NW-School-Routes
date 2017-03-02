package a00953315.comp3717.bcit.ca.nwschoolsafewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Route extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        spinner = (Spinner) findViewById(R.id.school_input);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.route_input_schools, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        itemPos = (int) parent.getItemAtPosition(pos);
        itemPos = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void back(View view) {
        final Intent intent = new Intent();
        //intent.putExtra("destination", itemPos);
        //setResult(RESULT_OK, intent);
        finish();
    }
}
