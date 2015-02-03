package wu.jay.simplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* TODO: Requires more error handling.
 * Also need to work on handling displaying the expression and input
 * numbers better.  Kind of sloppy for now.
 */

public class SimpleCalc extends Activity {
	// Keys for savedInstanceState
	private static final String KEY_DISP = "KEY_DISP";
	
	// Buttons!
	private static final int MAX_BUTTONS = 19; // total of 19 buttons according to the layout.
	private Button [] mButtons = new Button[MAX_BUTTONS]; // auto fill in buttons
	
	// Button enum with associated relative idx position in array.
	private static enum InputButton {
		NUM7(3, "7"),
		NUM8(4, "8"),
		NUM9(5, "9"),
		NUM4(7, "4"),
		NUM5(8, "5"),
		NUM6(9, "6"),
		NUM1(11, "1"),
		NUM2(12, "2"),
		NUM3(13, "3"),
		NUM0(15, "0"),
		BRACKETR(2, ")"),
		BRACKETL(1, "("),
		DIV(6, "/"),
		MULT(10, "*"),
		MINUS(14, "-"),
		DOT(16, "."),
		PLUS(17, "+");
		
		private int idx;
		private String symbol;
		private InputButton(int i, String s) { 
			idx = i; 
			symbol = s;
			}
		public int getIdx() { return idx; }
		public String getChar() { return symbol; }
	}
	
	private static enum ActionKey {
		CLEAR(0),
		EQUALS(18);
		
		private int idx;
		private ActionKey(int i) { idx = i; }
		public int getIdx() { return idx; }
	}

	// TextView
	private TextView mTvDisplay;
	private StringBuffer mDisplay;		// string to be displayed by textview
	
	// Calculator 
	private Calculator mCalculator;
	private boolean mFreshState = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Initialization.
		// get the textview
		mTvDisplay = (TextView) findViewById(R.id.main_display);
		mDisplay = new StringBuffer();
		mCalculator = new Calculator();
		setDisplayString("0");

		// Fill in buttons according to layout.
		setButtonIds();
		addInputButtonListeners();
		addActionKeyListeners();
		
		// Check if savedInstanceState exists.
		if (savedInstanceState != null) {
			String prevDisplay = savedInstanceState.getString(KEY_DISP);
			setDisplayString(prevDisplay);
		}

		updateMainDisplay();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString(KEY_DISP, mDisplay.toString());
	}
	
	// Automatically associate every button to an array of buttons by taking advantage
	// of the order of buttons added.  The resource ID increases by 1 based on on the
	// order of buttons added to the layout in the xml file.  So, we just start with
	// the first button added and increment by 1 to get all the button views.
	// Only thing is this will break if the layout changes.  
	// Need to decouple layout from the actual code.  Is this possible?
	private void setButtonIds() {
		for (int i = 0; i < MAX_BUTTONS; i++) {
			mButtons[i] = (Button) findViewById(R.id.buttonClear + i);
		}
	}
	
	// Set string to be displayed.
	private void setDisplayString(String s) {
		mDisplay.replace(0, mDisplay.length(), s);
	}
	
	// Update main display with display string.
	private void updateMainDisplay() {
		mTvDisplay.setText(mDisplay);
	}
	
	// Add button listeners to buttons.
	private void addInputButtonListeners() {		
		//Numbers.
		for (InputButton b : InputButton.values()) {
			final InputButton button = b;
			mButtons[b.getIdx()].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (mFreshState) {
						mDisplay.replace(0,  mDisplay.length(), button.getChar());
						mFreshState = false;
					}
					else {
						mDisplay.append(button.getChar());
					}
					updateMainDisplay();
				}
			});
		}
	}
	
	private void addActionKeyListeners() {
		for (ActionKey k : ActionKey.values()) {
			final ActionKey ak = k;
			mButtons[k.getIdx()].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					processActionKey(ak);
					updateMainDisplay();
				}
			});
		}	
	}
	
	// When an operator is pressed, push mEnteredNum to mTotal using a previous
	// operator if it exists.
	private void processActionKey(ActionKey k) {
	switch (k) {
		case CLEAR:
			setDisplayString("0");
			mCalculator.handleKeyPress(Calculator.Key.CLEAR);
			mFreshState = true;
			return;
		case EQUALS:
			mCalculator.setInput(mDisplay.toString());
			mCalculator.handleKeyPress(Calculator.Key.EQUALS);
			setDisplayString(mCalculator.getOutput());
			if (mCalculator.getFailState()) {
				mCalculator.handleKeyPress(Calculator.Key.CLEAR);
				mFreshState = true;
			}
			return;
		default:
			return;
		}
	}
}
