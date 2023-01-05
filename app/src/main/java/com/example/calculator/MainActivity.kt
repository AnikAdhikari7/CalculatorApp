package com.example.calculator

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.MovementMethod
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.exp

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val github: TextView = githubLink
        github.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/AnikAdhikari7"))
            startActivity(intent)
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
            appendText("0", true)
        }
        one.setOnClickListener {
            appendText("1", true)
        }
        two.setOnClickListener {
            appendText("2", true)
        }
        three.setOnClickListener {
            appendText("3", true)
        }
        four.setOnClickListener {
            appendText("4", true)
        }
        five.setOnClickListener {
            appendText("5", true)
        }
        six.setOnClickListener {
            appendText("6", true)
        }
        seven.setOnClickListener {
            appendText("7", true)
        }
        eight.setOnClickListener {
            appendText("8", true)
        }
        nine.setOnClickListener {
            appendText("9", true)
        }

        modulo.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "%" && lastChar != "-" && lastChar != "*" && lastChar != "/" && lastChar != "+") {
                    appendText("%", false)
                }
            }
        }
        divide.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "/" && lastChar != "-" && lastChar != "*" && lastChar != "+" && lastChar != "%") {
                    appendText("/", false)
                }
            }
        }
        multiply.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "*" && lastChar != "+" && lastChar != "-" && lastChar != "/" && lastChar != "%") {
                    appendText("*", false)
                }
            }
        }
        minus.setOnClickListener {
            var value = expression.text
            if (value.isEmpty()) {
                appendText("-", false)
            }
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "-") {
                    appendText("-", false)
                }
            }
        }
        plus.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != "+" && lastChar != "-" && lastChar != "*" && lastChar != "/" && lastChar != "%") {
                    appendText("+", false)
                }
            }
        }

        ac.setOnClickListener {
            expression.text = ""
            result.text = ""
            result.hint = ""
        }
        backspace.setOnClickListener {
            result.text = ""
            result.hint = ""
            val value = expression.text
            if (value.isNotEmpty()) {
                expression.text = value.substring(0, value.length - 1)
            }
        }

        decimal.setOnClickListener {
            var value = expression.text
            if (value.isNotEmpty()) {
                var lastChar = value.substring(value.length - 1)
                if (lastChar != ".") {
                    appendText(".", true)
                }
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
            result.hint = ""
            try {
                var expr = ExpressionBuilder(expression.text.toString()).build()
                var ans = expr.evaluate()
                result.text = ans.toString()
            } catch (e: Exception) {
                result.text = e.message
            }
        }
    }

    private fun appendText(value: String, toBeCleared: Boolean) {
        if (result.text != "") {
            expression.text = ""
        }

        if (toBeCleared) {
            result.text = ""
            expression.append(value)
        } else {
            expression.append(result.text)
            expression.append(value)
            result.text = ""
        }

        result.hint = expression.text
    }
}