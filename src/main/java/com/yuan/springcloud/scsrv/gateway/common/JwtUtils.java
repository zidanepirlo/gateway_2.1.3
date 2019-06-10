package com.yuan.springcloud.scsrv.gateway.common;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yuan.springcloud.scsrv.gateway.entity.JwtEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yuan.springcloud.scsrv.gateway.enums.JwtTokenVerify;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

/**
 * JwtUtils
 *
 * @author yuanqing
 * @create 2019-04-09 13:33
 **/
public class JwtUtils {


    public static final ThreadLocal<JwtEntity> JWT_TOKEN_DATA = new ThreadLocal<>();

    private static String TOKEN_GENERATE_COLUMN = "USER_ID";

    private  JwtUtils(){
    }

    /**
     * APP登录Token的生成和解析
     *
     */

    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    private static final String ASSCESS_TOKEN_SECRET = "abcd";
    private static final String REFRESH_TOKEN_SECRET = "1234";

//    private static final int CALENDARFIELD_ACCESS_TOKEN = Calendar.HOUR;
//    private static final int CALENDARINTERVAL_ACCESS_TOKEN = 1;
//    private static final int CALENDARFIELD_REFRESH_TOKEN = Calendar.DATE;
//    private static final int CALENDARINTERVAL_REFRESH_TOKEN = 5;

    private static final int CALENDARFIELD_ACCESS_TOKEN = Calendar.MINUTE;
    private static final int CALENDARINTERVAL_ACCESS_TOKEN = 5;
    private static final int CALENDARFIELD_REFRESH_TOKEN = Calendar.MINUTE;
    private static final int CALENDARINTERVAL_REFRESH_TOKEN = 15;

    public static String createAccessToken(String user_id) throws Exception{
        return createToken(user_id,CALENDARFIELD_ACCESS_TOKEN,CALENDARINTERVAL_ACCESS_TOKEN,ASSCESS_TOKEN_SECRET);
    }

    public static String createRefreshToken(String user_id) throws Exception{
        return createToken(user_id,CALENDARFIELD_REFRESH_TOKEN,CALENDARINTERVAL_REFRESH_TOKEN,REFRESH_TOKEN_SECRET);
    }

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    private static String createToken(String user_id,int calendarField,int calendarInterval,String SECRET) throws Exception {

        if (StringUtils.isEmpty(user_id))
            throw new Exception("createToken exception,TOKEN_GENERATE_COLUMN is null!");
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim(TOKEN_GENERATE_COLUMN, user_id)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    private static String createToken(String userName,String passWord,int calendarField,int calendarInterval,String SECRET) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("userName", StringUtils.isEmpty(userName) ? null : userName)
                .withClaim("passWord", StringUtils.isEmpty(passWord) ? null : passWord)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }


    private static String getJwtToken(String userName,String passWord,String SECRET){

        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        //有10天有效期
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();
        Claims claims = Jwts.claims();
        claims.put("userName",userName);
        claims.put("passWord",passWord);
        claims.setAudience("cy");
        claims.setIssuer("cy");
        String token = Jwts.builder().setClaims(claims).setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }


    public static Map<String, Claim> verifyAccessTokenTokenV1(String token){
        return verifyTokenV1(token,ASSCESS_TOKEN_SECRET);
    }

    public static Map<String, Claim> verifyRefreshTokenTokenV1(String token){
        return verifyTokenV1(token,REFRESH_TOKEN_SECRET);
    }

    public static JwtTokenVerify verifyAccessTokenV2(String token){
        return verifyTokenV2(token,ASSCESS_TOKEN_SECRET);
    }
    public static JwtTokenVerify verifyRefreshTokenV2(String token){
        return verifyTokenV2(token,REFRESH_TOKEN_SECRET);
    }

    private static Map<String, Claim> verifyTokenV1(String token,String SECRET) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    private static JwtTokenVerify verifyTokenV2(String token,String SECRET) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
        } catch (TokenExpiredException ex) {
            return JwtTokenVerify.TOKEN_EXPIRED;
        } catch (Exception ex) {
            return JwtTokenVerify.TOKEN_IILEGAL;
        }
        return JwtTokenVerify.TOKEN_SUCCESS;
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    private static Long getAppUID(String token,String SECRET) throws Exception {
        Map<String, Claim> claims = verifyTokenV1(token,SECRET);
        Claim user_id_claim = claims.get(TOKEN_GENERATE_COLUMN);
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }

//    public static Claims parseJwtToken(String token,String SECRET) {
//        try{
//
//            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
//            String signature = jws.getSignature();
//            Map<String, String> header = jws.getHeader();
//            Claims Claims = jws.getBody();
//            return Claims;
//        }catch (Exception ex){
//            throw ex;
//        }
//    }

}
