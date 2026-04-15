package dev.jefy.connectpro.user.infrastructure.config

import dev.jefy.connectpro.shared.infrastructure.config.SecurityProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
/**
 * @author  Jôph Yamba
 */
@Service
class JwtService(private val securityProperties: SecurityProperties) {

    fun extractUsername(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    fun generateToken(claims: HashMap<String, Any>, userDetails: UserDetails): String {
        return buildToken(claims, userDetails, securityProperties.jwt.expirationTime)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractExpiration(token)
        return expiration.before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        val signInKey = getSignInKey()
        return Jwts.parser()
            .verifyWith(signInKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun buildToken(extraClaims: HashMap<String, Any>, userDetails: UserDetails, jwtExpiration: Long): String {
        val authorities = userDetails.authorities.map { it.authority }
        val signInKey = getSignInKey()

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtExpiration * 1000 * 60))
            .claim("authorities", authorities)
            .signWith(signInKey)
            .compact()
    }

   /* private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
    }*/

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(securityProperties.jwt.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}