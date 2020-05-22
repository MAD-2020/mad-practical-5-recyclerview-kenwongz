package sg.edu.np.mad.mad_recyclerview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener {
    final String TAG = "To-Do List";
    RecyclerView mrecyclerview;
    Adapter madapter;
    EditText minput;
    Button mbtnadd;
    final ArrayList<String> todolist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Creating GUI");

        mrecyclerview = findViewById(R.id.recyclerView);
        minput = findViewById(R.id.textinput);
        mbtnadd = findViewById(R.id.btnAdd);
        todolist.add("Trash");

        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        madapter = new Adapter(this, todolist);
        madapter.setClickListener(this);
        mrecyclerview.setAdapter(madapter);

        mbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String list = minput.getText().toString().trim();
                todolist.add(list);
                madapter.notifyItemInserted(todolist.size());
                showNewEntry(mrecyclerview, todolist);
            }
        });
    }

    /**
     * Upon calling this method, the keyboard will retract
     * and the recyclerview will scroll to the last item
     *
     * @param rv RecyclerView for scrolling to
     * @param data ArrayList that was passed into RecyclerView
     */
    private void showNewEntry(RecyclerView rv, ArrayList data){
        //scroll to the last item of the recyclerview
        rv.smoothScrollToPosition(data.size());

        //auto hide keyboard after entry
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rv.getWindowToken(), 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int index = position;

        LayoutInflater inflater= getLayoutInflater();
        View layout = inflater.inflate(R.layout.alert_dialog, null);
        TextView msg = layout.findViewById(R.id.txtDiaMsg);

        builder.setTitle("Delete");
        msg.setText(Html.fromHtml("Are you sure you want to delete<br/>" + "<b>" + madapter.getItem(position) + "<b>"));

        ImageView img = layout.findViewById(R.id.imgbin);
        img.setImageResource(R.drawable.trash);

        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todolist.remove(index);
                madapter.notifyItemRemoved(index);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(layout);
        builder.show();
    }
}
