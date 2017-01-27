package a00953315.comp3717.bcit.ca.nwschoolsafewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    private EditText messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = getIntent();
    }

    public void createUser(View view) {
        // Get the id/username from RegisterActivity.class
        messageView = (EditText)findViewById(R.id.register_id);
        final String message = messageView.getText().toString();
        final Intent intent = new Intent(this, Menu.class);

        intent.putExtra("message2", message);
        startActivity(intent);
        finish();
    }
}
