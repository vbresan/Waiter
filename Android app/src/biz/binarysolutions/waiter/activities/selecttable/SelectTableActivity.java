package biz.binarysolutions.waiter.activities.selecttable;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.data.Table;
import biz.binarysolutions.waiter.db.DatabaseAdapter;

/**
 * 
 *
 */
public class SelectTableActivity extends Activity {
	
	/**
	 * @param tables 
	 * 
	 */
	private void setTableButtons(ArrayList<Table> tables) {
		
		GridView grid = (GridView) findViewById(R.id.GridViewTables);
		grid.setAdapter(new ButtonAdapter(this, tables));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tables);
		
		ArrayList<Table> tables = DatabaseAdapter.getTables(this);
		setTableButtons(tables);
	}

	/**
	 * 
	 * @param tableId
	 */
	public void onTableSelected(int tableId) {
		
		String key = getPackageName() + getString(R.string.app_extras_table);

		Intent intent = new Intent();
		intent.putExtra(key, tableId);
		
		setResult(RESULT_OK, intent);
		
		finish();
	}
}
