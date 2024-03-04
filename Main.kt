package chucknorris

fun checkIllegalChars(str: String): Boolean {
    return str.any { it != '0' && it != ' ' }
}

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        var operation = readln()
        
        if (operation == "encode") {
            println("Input string:")
            var inputString = readln()

            var binary = inputString.map { Integer.toBinaryString(it.toInt()).padStart(7, '0') }.joinToString("")
            var currentBit = binary[0]
            var count = 1
            var encoded = ""

            for (i in 1 until binary.length) {
                if (binary[i] == currentBit) {
                    count++
                } else {
                    encoded += if (currentBit == '1') "0 " else "00 "
                    encoded += "0".repeat(count) + " "
                    currentBit = binary[i]
                    count = 1
                }
            }
            encoded += if (currentBit == '1') "0 " else "00 "
            encoded += "0".repeat(count)
            
            println("Encoded string:")
            println(encoded)
            continue
            
        } else if (operation == "decode") {
            println("Input encoded string:")
            var input = readln().split(" ")
            var stringValues = ""
            var isValid = true

            for (i in 0 until input.size) {
                if (checkIllegalChars(input[i]) == true) {
                    isValid = false
                } 
            }
            
            if (input.size % 2 != 0) {
                isValid = false
            } else {
                for (i in 0 until input.size step 2) {
                    if (input[i] == "00") {
                        repeat(input[i + 1].length) {
                            stringValues += "0"
                        }
                    } else if (input[i] == "0"){
                        repeat(input[i + 1].length) {
                            stringValues += "1"
                        }
                    } else {
                        isValid = false
                    }
                }
            }

            if (stringValues.length % 7 != 0) {
                isValid = false
            }

            if(!isValid) {
                println("Encoded string is not valid.")
                continue
            }

            val chunks = mutableListOf<String>()
    
            for (i in 0 until stringValues.length step 7) {
                chunks.add(stringValues.substring(i, i + 7))
            }

            var decimalValues = chunks.map { Integer.parseInt(it, 2) }
            var result = decimalValues.map { it.toChar() }
            println("Decoded string:")
            println(result.joinToString(""))
            continue
            
        } else if (operation != "encode" && operation != "decode" && operation != "exit") {
            println("There is no \'" + operation + "\' operation")
            continue
            
        } else if (operation == "exit") {
            println("Bye!")
            break
        }   
    }
}
