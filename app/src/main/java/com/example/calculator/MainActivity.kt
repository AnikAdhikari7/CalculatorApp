package com.example.calculator

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    lateinit var zero: TextView
    lateinit var one: TextView
    lateinit var two: TextView
    lateinit var three: TextView
    lateinit var four: TextView
    lateinit var five: TextView
    lateinit var six: TextView
    lateinit var seven: TextView
    lateinit var eight: TextView
    lateinit var nine: TextView

    lateinit var ac: TextView
    lateinit var backspace: ImageView

    lateinit var modulo: TextView
    lateinit var divide: TextView
    lateinit var multiply: TextView
    lateinit var minus: TextView
    lateinit var plus: TextView

    lateinit var decimal: TextView
    lateinit var changeSign: TextView
    lateinit var equals: TextView

    lateinit var expression: TextView
    lateinit var result: TextView

    var operatorCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val github: TextView = githubLink
        github.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/AnikAdhikari7"))
            startActivity(intent)
        }
        val github2: TextView = githubLink2
        github2.setOnClickListener {
            val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ShashiKPD"))
            startActivity(intent2)
        }

        zero = zeroTV
        one = oneTV
        two = twoTV
        three = threeTV
        four = fourTV
        five = fiveTV
        six = sixTV
        seven = sevenTV
        eight = eightTV
        nine = nineTV

        ac = acTV
        backspace = backspaceIV

        modulo = moduloTV
        divide = divideTV
        multiply = multiplyTV
        minus = minusTV
        plus = plusTV

        decimal = decimalTV
        changeSign = changeSignTV
        equals = equalsTV

        expression = expressionTV
        result = resultTV

        zero.setOnClickListener {
            appendExpression("0", true)
            calculateResult(false)
        }
        one.setOnClickListener {
            appendExpression("1", true)
            calculateResult(false)
        }
        two.setOnClickListener {
            appendExpression("2", true)
            calculateResult(false)
        }
        three.setOnClickListener {
            appendExpression("3", true)
            calculateResult(false)
        }
        four.setOnClickListener {
            appendExpression("4", true)
            calculateResult(false)
        }
        five.setOnClickListener {
            appendExpression("5", true)
            calculateResult(false)
        }
        six.setOnClickListener {
            appendExpression("6", true)
            calculateResult(false)
        }
        seven.setOnClickListener {
            appendExpression("7", true)
            calculateResult(false)
        }
        eight.setOnClickListener {
            appendExpression("8", true)
            calculateResult(false)
        }
        nine.setOnClickListener {
            appendExpression("9", true)
            calculateResult(false)
        }

        modulo.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "%" && lastChar != "-" && lastChar != "*" && lastChar != "/" && lastChar != "+" && lastChar != ".") {
                    appendExpression("%", false)
                }
            }
        }
        divide.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "/" && lastChar != "-" && lastChar != "*" && lastChar != "+" && lastChar != "%" && lastChar != ".") {
                    appendExpression("/", false)
                }
            }
        }
        multiply.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "*" && lastChar != "+" && lastChar != "-" && lastChar != "/" && lastChar != "%" && lastChar != ".") {
                    appendExpression("*", false)
                }
            }
        }
        minus.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "-"  && lastChar != "." && lastChar != "+") {
                    appendExpression("-", false)
                }else{
                    expression.text = value.substring(0, value.length - 1) + "-"
                }
            }
        }
        plus.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "+" && lastChar != "-" && lastChar != "*" && lastChar != "/" && lastChar != "%" && lastChar != ".") {
                    appendExpression("+", false)
                }else{
                    expression.text = value.substring(0, value.length - 1) + "+"
                }
            }
        }

        ac.setOnClickListener {
            expression.text = ""
            result.text = ""
            result.hint = ""
            operatorCount = 0
        }
        backspace.setOnClickListener {
            result.text = ""
            result.hint = ""
            var value = expression.text
            if (value.isNotEmpty()) {
                var deletedChar = value.substring(value.length - 1)
                if(deletedChar >= "0" && deletedChar <= "9" && deletedChar != "."){}
                else{
                    operatorCount--
                }
                expression.text = value.substring(0, value.length - 1)

                //recalculating result after deletion of last char
                value = expression.text
                if (value.isNotEmpty()) {
                    deletedChar = value.substring(value.length - 1)
                    if (deletedChar >= "0" && deletedChar <= "9") {
                        calculateResult(false)
                    }
                }
            }
        }

        decimal.setOnClickListener {
            var value = expression.text
            if (value.isEmpty()) {
                appendExpression("0.", false)
            }
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar == ".") {

                }
                else if (lastChar == "+" || lastChar == "-" || lastChar == "*" || lastChar == "/" || lastChar == "%") {
                    appendExpression("0.", false)
                }
                else {
                    appendExpression(".", false)
                }
            }
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)

            }
        }
        changeSign.setOnClickListener {
            result.text = ""
            result.hint = ""

            if (expression.text.isNotEmpty() && expression.text[0] == '-') {
                expression.text = expression.text.substring(1)
            } else {
                expression.text = "-${expression.text}"
            }
        }

        equals.setOnClickListener {
            calculateResult(true)
        }
    }

    private fun appendExpression(value: String, toBeCleared: Boolean) {

        if (toBeCleared) {
            if (result.text != ""){
                expression.text=""
                result.text = ""
            }
            expression.append(value)
        } else {
            if (result.text != ""){
                expression.text = result.text
                result.text = ""
            }
            expression.append(value)
            operatorCount++
        }
        if(operatorCount > 0 && toBeCleared) result.hint = expression.text
        else result.hint = ""
    }

    private fun calculateResult(equalKeyPressed : Boolean){
        if(operatorCount > 0){
            result.hint = ""
            try {
                var expr = ExpressionBuilder(expression.text.toString()).build()
                var ans = expr.evaluate()
                if(equalKeyPressed)
                    result.text = ans.toString()
                else
                    result.hint = ans.toString()
            } catch (e: Exception) {
                if(equalKeyPressed)
                    result.text = e.message
                else
                    result.hint = e.message
            }
        }
    }
}