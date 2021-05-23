package com.jaewoo.srs

import com.jaewoo.srs.app.user.dao.UserRepository
import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.common.message.dao.MessageRepository
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.core.config.properties.SecurityProperties
import com.jaewoo.srs.core.logging.Log
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableConfigurationProperties(SecurityProperties::class)
@SpringBootApplication
class Application {
    companion object : Log

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        messageRepository: MessageRepository,
        encodePassword: BCryptPasswordEncoder
    ) = CommandLineRunner {
        logger.trace("trace!!")
        logger.debug("debug!!")
        logger.info("info!!")
        logger.warn("warn!!")
        logger.error("error!!")

        val user = User(
            name = "Jaewoo Jung",
            mobileNo = "010-9910-9999",
            loginId = "jeawoo.jeong@gmail.com",
            password = encodePassword.encode("1234")
        )

        userRepository.save(user)

        val message1 = Message("MSG0001", "중복된 사용자가 존재합니다.", "Duplicated user exists!!")
        val message2 = Message("MSG0002", "{0}는 중복된 메시지입니다.", "{0} is duplicated message!!")
        val message3 = Message("MSG0003", "데이터가 존재하지 않습니다.", "Data does not exist!")
        val message4 = Message("MSG0004", "사용자 정보를 찾을 수 없습니다.", "User does not exists")
        val message5 = Message("MSG0005", "사용자 패스워드가 불일치 합니다.", "User password does not match!!")

        messageRepository.saveAll(listOf(message1, message2, message3, message4, message5))
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
