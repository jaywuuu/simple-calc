package wu.jay.simplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SimpleCalc extends Activity {
	// Keys for savedInstanceState
	private static final String KEY_PREV_OP = "KEY_PREV_OP";
	private static final String KEY_ENT_NUM = "KEY_ENT_NUM";
	private static final String KEY_TOTAL = "KEY_TOTAL";
	
	// Buttons!
	private static final int MAX_BUTTONS = 19; // total of 18 buttons according to the layout.
	private Button [] mButtons = new Button[MAX_BUTTONS]; // auto fill in buttons
	
	// Button indices for Button array.
	private static final int BUTTON_CLEAR = 0;
	private static final int BUTTON_BRACKETR = 1;
	private static final int BUTTON_BRACKETL = 2;
	private static final int BUTTON_NUM7 = 3;
	private static final int BUTTON_NUM8 = 4;
	private static final int BUTTON_NUM9 = 5;
	private static final int BUTTON_DIV = 6;
	private static final int BUTTON_NUM4 = 7;
	private static final int BUTTON_NUM5 = 8;
	private static final int BUTTON_NUM6 = 9;
	private static final int BUTTON_MULT = 10;
	private static final int BUTTON_NUM1 = 11;
	private static final int BUTTON_NUM2 = 12;
	private static final int BUTTON_NUM3 = 13;
	private static final int BUTTON_MINUS = 14;
	private static final int BUTTON_NUM0 = 15;
	private static final int BUTTON_DOT = 16;
	private static final int BUTTON_PLUS = 17;
	private static final int BUTTON_EQUALS = 18;
	
	// TextView
	private TextView mTvDisplay;
	private String mDisplay;		// string to be displayed by textview
	private double mTotal;			// holds calculated total
	private double mEnteredNum;		// holds the currently entered number
	
	// Some states of the calculator
	private int mPrevOp;			// stores the previous operator.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Initialization.
		// get the textview
		mTvDisplay = (TextView) findViewById(R.id.main_display);
		mTotal = 0;
		mEnteredNum = 0;
		setDisplayString("0");
		mPrevOp = BUTTON_CLEAR;

		// Fill in buttons according to layout.
		getButtonIds();
		addButtonListeners();
		
		// Check if savedInstanceState exists.
		if (savedInstanceState != null) {
			mPrevOp = savedInstanceState.getInt(KEY_PREV_OP);
			mEnteredNum = savedInstanceState.getDouble(KEY_ENT_NUM);
			mTotal = savedInstanceState.getDouble(KEY_TOTAL);
			setDisplayString(Double.toString(mEnteredNum));
		}

		updateMainDisplay();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(KEY_PREV_OP, mPrevOp);
		savedInstanceState.putDouble(KEY_ENT_NUM, mEnteredNum);
		savedInstanceState.putDouble(KEY_TOTAL, mTotal);
	}
	
	// Automatically associate every button to an array of buttons by taking advantage
	// of the order of buttons added.  The resource ID increases by 1 based on on the
	// order of buttons added to the layout in the xml file.  So, we just start with
	// the first button added and increment by 1 to get all the button views.
	private void getButtonIds() {
		for (int i = 0; i < MAX_BUTTONS; i++) {
			mButtons[i] = (Button) findViewById(R.id.buttonClear + i);
		}
	}
	
	// Set string to be displayed.
	private void setDisplayString(String s) {
		mDisplay = s;
	}
	
	// Update main display with display string.
	private void updateMainDisplay() {
		mTvDisplay.setText(mDisplay);
	}
	
	// Add button listeners to buttons.
	private void addButtonListeners() {
		// Clear button
		mButtons[BUTTON_CLEAR].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDisplay = "0";
				mTotal = 0;
				mEnteredNum = 0;
				mPrevOp = BUTTON_CLEAR;
				updateMainDisplay();
			}
		});
		
		//Numbers.
		mButtons[BUTTON_NUM0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM0);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM1);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM2);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM3);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM4);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM5].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM5);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM6].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM6);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM7].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM7);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM8].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM8);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_NUM9].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processNumber(BUTTON_NUM9);
				updateMainDisplay();
			}

		});
		
		// operators.
		mButtons[BUTTON_DIV].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processOperator(BUTTON_DIV);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_MULT].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processOperator(BUTTON_MULT);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_MINUS].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processOperator(BUTTON_MINUS);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_PLUS].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processOperator(BUTTON_PLUS);
				updateMainDisplay();
			}
		});
		
		mButtons[BUTTON_EQUALS].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				processOperator(BUTTON_EQUALS);
				updateMainDisplay();
			}
		});
		
		// TODO: implement bracket evaluation and decimals.
	}
	
	private void processNumber(int num) {
		switch (num) {
		case BUTTON_NUM0:
			// We only change the number if it's not 0.
			// Push the previous entered number to the next digit and add whatever
			// is entered.  In the case of 0, it's just a multiplication by 10.
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "0";
				mEnteredNum = mEnteredNum * 10;
			}
			return;
		case BUTTON_NUM1:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "1";
			}
			else {
				mDisplay = "1";
			}
			mEnteredNum = mEnteredNum * 10 + 1;
			return;
		case BUTTON_NUM2:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "2";
			}
			else {
				mDisplay = "2";
			}
			mEnteredNum = mEnteredNum * 10 + 2;
			return;
		case BUTTON_NUM3:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "3";
			}
			else {
				mDisplay = "3";
			}
			mEnteredNum = mEnteredNum * 10 + 3;
			return;
		case BUTTON_NUM4:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "4";
			}
			else {
				mDisplay = "4";
			}
			mEnteredNum = mEnteredNum * 10 + 4;
			return;
		case BUTTON_NUM5:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "5";
			}
			else {
				mDisplay = "5";
			}
			mEnteredNum = mEnteredNum * 10 + 5;
			return;
		case BUTTON_NUM6:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "6";
			}
			else {
				mDisplay = "3";
			}
			mEnteredNum = mEnteredNum * 10 + 6;
			return;
		case BUTTON_NUM7:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "7";
			}
			else {
				mDisplay = "7";
			}
			mEnteredNum = mEnteredNum * 10 + 7;
			return;
		case BUTTON_NUM8:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "8";
			}
			else {
				mDisplay = "8";
			}
			mEnteredNum = mEnteredNum * 10 + 8;
			return;
		case BUTTON_NUM9:
			if (mEnteredNum != 0) {
				mDisplay = mDisplay + "9";
			}
			else {
				mDisplay = "9";
			}
			mEnteredNum = mEnteredNum * 10 + 9;
			return;
		default:
			return;
		}
	}
	
	// When an operator is pressed, push mEnteredNum to mTotal using a previous
	// operator if it exists.
	private void processOperator(int op) {
		if (mPrevOp == BUTTON_CLEAR) {
			mTotal = mTotal + mEnteredNum;
			mEnteredNum = 0;
			mPrevOp = op;
		}
		else {
			switch (op) {
			case BUTTON_DIV:
				if (mEnteredNum != 0) mTotal = mTotal / mEnteredNum;
				else mTotal = 0;
				mEnteredNum = 0;
				mPrevOp = op;
				setDisplayString(Double.toString(mTotal));
				return;
			case BUTTON_MULT:
				mTotal = mTotal * mEnteredNum;
				mEnteredNum = 0;
				mPrevOp = op;
				setDisplayString(Double.toString(mTotal));
				return;
			case BUTTON_MINUS:
				mTotal = mTotal - mEnteredNum;
				mEnteredNum = 0;
				mPrevOp = op;
				setDisplayString(Double.toString(mTotal));
				return;
			case BUTTON_PLUS:
				mTotal = mTotal + mEnteredNum;
				mEnteredNum = 0;
				mPrevOp = op;
				setDisplayString(Double.toString(mTotal));
				return;
			case BUTTON_EQUALS:
				processOperator(mPrevOp);
				mPrevOp = BUTTON_CLEAR;
				setDisplayString(Double.toString(mTotal));
				return;
			default:
				return;
			}
		}
	}
}
