package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentAllInOne extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_allinone, container, false);
        MainActivity.setToolBarInfo("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());

        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);

        EditText etInput = rootView.findViewById(R.id.text_user_input);


        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate();
                    return true;
                }
                return false;
            }
        });

        etInput.addTextChangedListener(this);

        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentConversionsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        EditText etInput = rootView.findViewById(R.id.text_user_input);

        //answer textviews
        ExpandableTextView tvBinary = rootView.findViewById(R.id.answer_binary);
        TextView tvOctal = rootView.findViewById(R.id.answer_octal);
        TextView tvHexadecimal = rootView.findViewById(R.id.answer_hexadecimal);


        String binary, octal, hexadecimal;

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Double decLong = Double.parseDouble(decimal);

            if (decLong <= 0) {
                Toast.makeText(getContext(), "Number should be greater than 0", Toast.LENGTH_LONG).show();
                return;
            }

            binary = Numericals.DecimalToBinary(decLong);
            octal = Numericals.DecimalToOctal(decLong);
            hexadecimal = Numericals.DecimalToHexadecimal(decimal);

            tvBinary.setText(binary);
            tvOctal.setText(octal);
            tvHexadecimal.setText(hexadecimal);

            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

            //rootView.findViewById(R.id.show_all).setVisibility(isAnswerTruncated ? View.VISIBLE : View.GONE);


        } catch (NumberFormatException ex) {
            Log.i(Utilities.Log, "cannot parse " + decimal + " to an integer value");
        } finally {
            MainActivity.hideKeyboard(etInput);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}