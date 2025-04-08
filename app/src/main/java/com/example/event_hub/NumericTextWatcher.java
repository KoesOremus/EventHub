package com.example.event_hub;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NumericTextWatcher implements TextWatcher {
    private final EditText editText;

    public NumericTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        String filteredText = text.replaceAll("[^0-9]", "");

        if (!text.equals(filteredText)) {
            int cursorPosition = editText.getSelectionStart();
            cursorPosition -= (text.length() - filteredText.length());
            if (cursorPosition < 0) {
                cursorPosition = 0;
            }

            editText.setText(filteredText);
            editText.setSelection(Math.min(cursorPosition, filteredText.length()));
        }
    }
}