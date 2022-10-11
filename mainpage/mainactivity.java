import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText num = (EditText) findViewById(R.id.sNum);
        Button ch = (Button) findViewById(R.id.rButton);
        TelephonyManager operator = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String opname = operator.getNetworkOperatorName();
        TextView status = (TextView) findViewById(R.id.setStatus);
        final EditText ID = (EditText) findViewById(R.id.IQID);
        Button save = (Button) findViewById(R.id.sButton);

        final String myID = ""; //When Reading The File Back, I Need To Store It In This String For Later Use

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Get Text From EditText "ID" And Save It To Internal Memory
            }
        });
        if (opname.contentEquals("zain SA")) {
            status.setText("Your Network Is: " + opname);
        } else {
            status.setText("No Network");
        }
        ch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Read From The Saved File Here And Append It To String "myID"


                String hash = Uri.encode("#");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:*141*" + /*Use The String With Data Retrieved Here*/ num.getText()
                        + hash));
                startActivity(intent);
            }
        });
    }
