package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    val metricUnitsView="Metric_Unit_View"
    val UsUnitsView="Us_Units_View"
    var currentvisibleview:String=metricUnitsView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        btnCalculateUnits.setOnClickListener {
            if(currentvisibleview.equals(metricUnitsView))
            {
                if(validateMetricUnits())
                {
                    val heightValue:Float  =etMetricUnitHeight.text.toString().toFloat()/100
                    val weightValue:Float=etMetricUnitWeight.text.toString().toFloat()
                    val bmi=weightValue/(heightValue*heightValue)
                    displayBMIResult(bmi)
                }else
                {
                    Toast.makeText(this, "Enter Valid Values Please", Toast.LENGTH_SHORT).show()
                }
            }else
            {
                if(validateUsUnits())
                {
                    val usunitheightvaluefeet:String=etUsUnitHeightFeet.text.toString()
                    val usunitheightvalueinch:String=etUsUnitHeightInch.text.toString()
                    val usunitweightvalue:Float=etUsUnitWeight.text.toString().toFloat()
                    val heightvalue=usunitheightvalueinch.toFloat()+usunitheightvaluefeet.toFloat()*12
                    val bmi=703*(usunitweightvalue/(heightvalue*heightvalue))
                    displayBMIResult(bmi)

                }else
                {
                    Toast.makeText(this, "Enter Valid Values Please", Toast.LENGTH_SHORT).show()
                }
            }
        }
        makevisiblemetricView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.rbMetricUnits)
            {
                makevisiblemetricView()
            }else
            {
                makevisiblUsView()
            }
        }

    }
    private fun validateUsUnits():Boolean{
        var isValid=true
        if(etUsUnitHeightFeet.text.toString().isEmpty())
        {
            isValid=false
        }else if(etUsUnitHeightInch.text.toString().isEmpty())
        {
            isValid=false
        }else if(etUsUnitWeight.text.toString().isEmpty())
        {
            isValid=false
        }


        return isValid
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
    private fun makevisiblemetricView()
    {
        currentvisibleview=metricUnitsView
        tilMetricUnitHeight.visibility=View.VISIBLE
        tilMetricUnitWeight.visibility=View.VISIBLE
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()
        tilUsUnitWeight.visibility=View.GONE
        llUsUnitsHeight.visibility=View.GONE
        llDiplayBMIResult.visibility=View.INVISIBLE

    }
    private fun makevisiblUsView()
    {
        currentvisibleview=UsUnitsView
        tilMetricUnitHeight.visibility=View.GONE
        tilMetricUnitWeight.visibility=View.GONE
        llUsUnitsView.visibility=View.VISIBLE
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()
        etUsUnitWeight.text!!.clear()
        tilUsUnitWeight.visibility=View.VISIBLE
        llUsUnitsHeight.visibility=View.VISIBLE
        llDiplayBMIResult.visibility=View.INVISIBLE

    }
    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        llDiplayBMIResult.visibility=View.VISIBLE
       /* tvYourBMI.visibility=View.VISIBLE
        tvBMIValue.visibility=View.VISIBLE
        tvBMIType.visibility=View.VISIBLE
        tvBMIDescription.visibility=View.VISIBLE*/
        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text=bmiDescription
    }

}