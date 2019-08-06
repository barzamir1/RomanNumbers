
//map each Roman numeral to its decimal value
val decimalMap = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100)


fun printErrorMsg(msg:String, romanNumber: String){
    println("illegal number $romanNumber: $msg")
}
fun isRomanNumber(romanNumber: String):Boolean{
    //check that all characters are valid
    for(ch in romanNumber)
        if(decimalMap[ch]==null) {
            printErrorMsg("illegal character $ch", romanNumber)
            return false
        }

    //split romanNumber to hundreds, tens and ones numerals
    val numerals = splitToSubNumbers(romanNumber)
    numerals ?: return false //error occurred

    //get the decimal value of each numeral
    val valuesList = mutableListOf<Int>() //list of decimal value of each numeral
    for(num in numerals)
        valuesList.add(toDecimalValue(num))

    var i=0
    val digitErrorMsg ={numeral1:String, numeral2:String, digit:String->
            "can't have two $digit digit. $numeral1 is followed by $numeral2"}

    //check that each numeral is followed by a smaller one
    while(i<valuesList.size){
        when(numerals[i]){
            "IV", "IX" -> {
                if (i < valuesList.size - 1) { //this is not the last digit
                    printErrorMsg(digitErrorMsg(numerals[i],numerals[i+1], "ones"), romanNumber)
                    return false
                }
            }
            "V"->{
                if(i<valuesList.size-1 && valuesList[i+1]>3){
                    //V is followed by ones numeral bigger than 3
                    printErrorMsg(digitErrorMsg(numerals[i],numerals[i+1], "ones"), romanNumber)
                    return false
                }
            }

            "XL", "XC"->{
                if(i<valuesList.size-1 && valuesList[i+1]>9){
                    //XL or XC is followed by numeral bigger than 9
                    printErrorMsg(digitErrorMsg(numerals[i],numerals[i+1], "tens"), romanNumber)
                    return false
                }
            }
            else->{
                if(i<valuesList.size-1 && valuesList[i+1] > valuesList[i]){
                    printErrorMsg("${numerals[i]} can't be followed by a bigger numeral ${numerals[i+1]}", romanNumber)
                    return false
                }
            }
        }
        i++
    }
    //println("Valid number: $romanNumber, ${valuesList.sum()}")
    return true
}

/*
@romanNumber - the Roman representation string, assuming all characters are valid
split @romanNumber to sub-numbers,
s.t the decimal value of @romanNumber equals to the sum of the decimal values of
all the elements in the returned list
example input:LXXXIV, output:["L","XXX", "IV"]
 */
fun splitToSubNumbers(romanNumber:String):MutableList<String>?{
    var i = 0
    var j:Int?

    val subNumbers:MutableList<String> = mutableListOf()

    //group equal characters in a row, e.g, "LXXXIV"->["L", "XXX", "I", "V"]
    while(i<romanNumber.length){
        j=i
        while(j<romanNumber.length-1 && romanNumber[j]==romanNumber[j+1])
            j++
        val currSubNumber:String = romanNumber.substring(i,j+1) //substring of equal characters
        if(currSubNumber.length>1) {
            if (currSubNumber[0] == 'V' || currSubNumber[0] == 'L') {
                printErrorMsg("$currSubNumber, you can’t have more than one",romanNumber)
                return null
            }
            if (currSubNumber.length > 3) {
                printErrorMsg("$currSubNumber, you can’t have more than three in a row", romanNumber)
                return null
            }
        }
        subNumbers.add(currSubNumber)
        i=j+1
    }

    //group subtractions, e.g, ["L", "XXX", "I", "V"]->["L", "XXX", "IV"]
    i = 0
    while(i<subNumbers.size-1) {
        //if list contains [I,V] or [I,X] or [X,L] or [X,C]
        if ((subNumbers[i]=="I" && (subNumbers[i + 1]=="V" || subNumbers[i + 1]=="X"))
                || (subNumbers[i]=="X" && (subNumbers[i + 1]=="L"|| subNumbers[i+1]=="C"))) {
            subNumbers[i] = subNumbers[i] + subNumbers[i + 1]
            subNumbers.removeAt(i + 1)
        }
        i++
    }
    return subNumbers
}

/*
@romanNumber - Roman representation number, at most 3 characters
returns the decimal value of the number, or -1 if @romanNumber is not a valid number
 */
fun toDecimalValue(romanNumber: String): Int {
    val chars = romanNumber.toCharArray()
    var decimalValue = decimalMap[chars[0]]
    val subtraction: Int?
    decimalValue ?: return -1

    if (chars.size == 1) {
        return decimalValue
    }
    if (chars.size == 2) {
        //identical characters: sum values
        if (chars[0] == chars[1]) {
            return 2 * decimalValue
        } else {
            //different characters: subtract the first numeral from the second
            decimalValue = decimalMap[chars[1]]
            subtraction = decimalMap[chars[0]]
            if (decimalValue != null && subtraction != null)
                return decimalValue - subtraction
        }
    } else {
        //chars.size=3. all characters must be identical
        return 3 * decimalValue
    }
    return -1
}
