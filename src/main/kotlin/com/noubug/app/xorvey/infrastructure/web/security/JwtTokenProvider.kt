package com.noubug.app.xorvey.infrastructure.web.security

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.JWEObject
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.DirectEncrypter
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.JWEDecryptionKeySelector
import com.nimbusds.jose.proc.SimpleSecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.noubug.app.xorvey.infrastructure.exception.TokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.annotation.PostConstruct
import javax.inject.Named
import javax.servlet.http.HttpServletRequest

@Named
class JwtTokenProvider(private val appUserDetail: ApiUserDetail) {
    @Value("\${security.jwt.token.secret-key:Ty3Vcroy9tAwz9IpZ87q4b+Y2xpkyrv1irm1j3rV8lM=}")
    private var secretKey: String? = null
    @Value("\${security.jwt.token.expire-length:4}")
    private val validityPeriod: Long = 4

    @PostConstruct
    private fun init() {
        //secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(username: String, authKey: String): String {

        val claims = JWTClaimsSet.Builder()
                .subject(username)
                .claim("authKey", authKey)
                .issueTime(Date())
                .issuer("xorvey")
                .expirationTime(Date.from(LocalDateTime.now().plusHours(validityPeriod).atZone(ZoneId.systemDefault()).toInstant()))
                .build()

        val secretKeyBytes = secretKey!!.toByteArray(Charset.forName("utf8"))
        val payload = Payload(claims.toJSONObject())
        val header = JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)

        val jweObject = JWEObject(header, payload)
        val decoded =  Base64.getDecoder().decode(secretKeyBytes)
        System.out.println(secretKey!!)
        System.out.println(decoded.size)
        jweObject.encrypt(DirectEncrypter(decoded))

        return jweObject.serialize()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = appUserDetail.loadUserByUsername(getUserName(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getUserName(token: String): String {
        val jwtProcessor = DefaultJWTProcessor<SimpleSecurityContext>()

        val jweKeySource = ImmutableSecret<SimpleSecurityContext>(Base64.getDecoder().decode(secretKey!!.toByteArray()))

        val jweKeySelector = JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource)

        jwtProcessor.jweKeySelector = jweKeySelector

        val claims = jwtProcessor.process(token, null)

        return claims.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String): Boolean {
        try {
            getUserName(token)
            return true
        } catch (e: IllegalArgumentException) {
            throw TokenException("Expired or invalid JWT token")
        } catch (e: Exception) {
            throw TokenException("Expired or invalid JWT token")
        }

    }
}