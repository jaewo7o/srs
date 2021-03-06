package com.jaewoo.srs

import com.jaewoo.srs.app.user.dao.UserRepository
import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.common.code.dao.CodeRepository
import com.jaewoo.srs.common.code.dao.GroupCodeRepository
import com.jaewoo.srs.common.code.domain.entity.Code
import com.jaewoo.srs.common.code.domain.entity.GroupCode
import com.jaewoo.srs.common.message.dao.MessageRepository
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.domain.enum.MessageType.SERVER_MESSAGE
import com.jaewoo.srs.core.logging.Log
import com.jaewoo.srs.core.security.properties.SecurityProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableCaching
@EnableConfigurationProperties(SecurityProperties::class)
@SpringBootApplication
class Application {
    companion object : Log

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        messageRepository: MessageRepository,
        groupCodeRepository: GroupCodeRepository,
        codeRepository: CodeRepository,
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
            languageCode = "ko",
            timezoneName = "America/Los_Angeles",
            loginId = "jeawoo.jeong@gmail.com",
            password = encodePassword.encode("1234")
        )

        userRepository.save(user)

        val message1 = Message("MSG0001", SERVER_MESSAGE, "????????? ???????????? ???????????????.", "Duplicated user exists!!")
        val message2 = Message("MSG0002", SERVER_MESSAGE, "{0}??? ????????? ??????????????????.", "{0} is duplicated message!!")
        val message3 = Message("MSG0003", SERVER_MESSAGE, "???????????? ???????????? ????????????.", "Data does not exist!")
        val message4 = Message("MSG0004", SERVER_MESSAGE, "????????? ????????? ?????? ??? ????????????.", "User does not exists")
        val message5 =
            Message("MSG0005", SERVER_MESSAGE, "????????? ??????????????? ????????? ?????????.", "User password does not match!!")
        val message6 = Message("MSG0006", SERVER_MESSAGE, "????????? ???????????? ???????????????.", "Duplicated data exists!!")

        messageRepository.saveAll(listOf(message1, message2, message3, message4, message5, message6))

        val groupCode = GroupCode(
            groupCode = "CM001",
            groupCodeNameKo = "Message Type",
            groupCodeNameEn = "Message Type"
        )
        groupCodeRepository.save(groupCode)


        val code1 =
            Code(groupCode = groupCode.groupCode, code = "UT", codeNameKo = "??????", codeNameEn = "Term", sortRank = 1)
        val code2 = Code(
            groupCode = groupCode.groupCode,
            code = "UM",
            codeNameKo = "???????????????",
            codeNameEn = "UI Message",
            sortRank = 2
        )
        val code3 = Code(
            groupCode = groupCode.groupCode,
            code = "SM",
            codeNameKo = "???????????????",
            codeNameEn = "Server Message",
            sortRank = 3
        )

        codeRepository.saveAll(listOf(code1, code2, code3))
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
