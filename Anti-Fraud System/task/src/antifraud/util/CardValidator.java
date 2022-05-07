package antifraud.util;

import org.springframework.stereotype.Component;

@Component
public class CardValidator {

    /***
     * This uses Luhn algorithm to check if the card number is valid.
     */
    public boolean validate(String cardNumber){
        long convCardNumber = Long.parseLong(cardNumber);

        long numNoLastDigit = convCardNumber/10;
        long lastDigit = convCardNumber%10;
        int sum = 0;
        boolean isSecond = true;

        while(numNoLastDigit > 0){
            long lastNum = numNoLastDigit%10;
            if(isSecond){
                long doubleNum = lastNum * 2;
                if(doubleNum > 9){
                    long digitSum = (doubleNum%10) + (doubleNum/10%10);
                    sum += digitSum;
                }else{
                    sum += doubleNum;
                }
            }else{
                sum += lastNum;
            }
            isSecond = !isSecond;
            numNoLastDigit /= 10;
        }
        return (sum + lastDigit) % 10 == 0;
    }
}
