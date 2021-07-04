package com.jaewoo.srs

import com.fasterxml.jackson.databind.ObjectMapper
import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.core.context.SrsContext
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SpringTestSupport {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    lateinit var objectMapper: ObjectMapper

    protected val query: JPAQueryFactory by lazy { JPAQueryFactory(entityManager) }

    protected fun <T> save(entity: T): T {
        entityManager.persist(entity)
        flushAndClearPersistentContext()
        return entity
    }

    protected fun <T> saveAll(entities: Iterable<T>): Iterable<T> {
        for (entity in entities) {
            entityManager.persist(entity)
        }
        flushAndClearPersistentContext()
        return entities
    }

    private fun flushAndClearPersistentContext() {
        entityManager.flush()
        entityManager.clear()
    }

    @BeforeEach
    fun initSession() {
        val user = User(
            name = "Jaewoo Jung",
            mobileNo = "010-9910-9999",
            languageCode = "ko",
            timezoneName = "America/Los_Angeles",
            loginId = "jeawoo.jeong@gmail.com",
            password = "XXXXX"
        )

        val saveUser = save(user)
        SrsContext.setCurrentUser(saveUser.toSessionUser())
    }
}