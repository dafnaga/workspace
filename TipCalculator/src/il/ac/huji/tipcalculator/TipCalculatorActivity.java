package il.ac.huji.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tip_calculator, menu);
		return true;
	}

	public void calcClick(View view){
		try{
			double amount = Double.parseDouble(((EditText)findViewById(R.id.edtBillAmount)).getText().toString());
			String formattedString;
			
			amount *= 0.12;
			if (((CheckBox)findViewById(R.id.chkRound)).isChecked()){
				formattedString = String.format("%.0f", amount); 
			}
			else{
				formattedString = String.format("%.2f", amount);
			}
			((TextView)findViewById(R.id.txtTipResult)).setText("Tip: $" + formattedString);
		}
		catch(Exception e){
			// do nothing
		}
	}
}
