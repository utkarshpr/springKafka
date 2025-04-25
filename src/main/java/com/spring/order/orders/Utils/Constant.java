package com.spring.order.orders.Utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.SignatureAlgorithm;

public class Constant {
   public  static final  String  EMPTY="";
   public static final String SECRET="vgwcdfiw6823fvg#hGQSGXJLKWC*^%$784FG3EKGXSA78GLJb08y__o8CBVGIKGXSA78GLJ6823fvghGQSGXJLKWC>:@784FG3EKGXSA78GLJb08yo8CBVGIKGXSA78GLJb08yo8uhjl56IYVb08yo8uhj_l56IYVKHl/G6823fvghGQSGXJLKWC784FG3EKGXSA78GLJb08yo8CBVGIKGXSA78GLJb08yo8uhjl56IYVQSGXJLKWC784FG3EKCBVGIKGXSA78GLJbhuo978GLK[=uvhk]";
   public static final Key SECRET_KEY = new SecretKeySpec(
        SECRET.getBytes(),
        SignatureAlgorithm.HS256.getJcaName()
);

}