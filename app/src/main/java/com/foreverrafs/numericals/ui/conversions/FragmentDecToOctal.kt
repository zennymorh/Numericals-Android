package com.foreverrafs.numericals.ui.conversions

import android.util.Log
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R

//import com.ms.square.android.expandabletextview.ExpandableTextView;
/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentDecToOctal : ConversionsBase() {
    override fun initControls() {
        super.initControls()
        setHeader(getString(R.string.dec_to_octal))
        setDescription(getString(R.string.decimal_to_octal_desc))
        setInputHint(getString(R.string.decimal_int_input_hint))
        setMethodName("dectooctal")
    }

    override fun onCalculate() {
        val decimal = etInput.text.toString()
        if (decimal.isEmpty()) {
            showErrorMessage(getString(R.string.errormsg_empty_input), false)
            return
        }
        try {
            val decLong = decimal.toDouble()
            if (decLong <= 0) {
                showErrorMessage(getString(R.string.errormsg_greater_than_zero), false)
                return
            }
            val octalDecimal = Numericals.decimalToOctal(decimal)
            tvAnswer.text = octalDecimal
            displayAnswer()
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "cannot parse $decimal to an integer value")
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    companion object {
        private const val TAG = "FragmentDecToOctal"
    }
}