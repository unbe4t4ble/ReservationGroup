package kidzania.reservationgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;

public class GroupMenu extends AppCompatActivity {

    Button btnAvail, btnDraft, btnReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
        CheckConnection(this);
    }

    private void initialization(){
        btnAvail = (Button) findViewById(R.id.btnAvail);
        btnAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGroupData();
            }
        });
        btnDraft = (Button) findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGroupDraft();
            }
        });
        btnReject = (Button) findViewById(R.id.btnReject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGroupRejected();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void OpenGroupData(){
        startActivity(new Intent(GroupMenu.this, GroupData.class));
    }

    private void OpenGroupDraft(){
        startActivity(new Intent(GroupMenu.this, GroupDraft.class));
    }

    private void OpenGroupRejected(){
        startActivity(new Intent(GroupMenu.this, GroupAvailGrp.class));
    }
}
