package io.gitlab.arturbosch.detekt.rules.style

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class EqualsNullCallSpec : Spek({
    val subject by memoized { EqualsNullCall(Config.empty) }

    describe("EqualsNullCall rule") {

        it("with null as parameter") {
            val code = """
				fun x(a: String) {
					a.equals(null)
				}"""
            Assertions.assertThat(subject.lint(code).size).isEqualTo(1)
        }

        it("with nested equals(null) call as parameter") {
            val code = """
				fun x(a: String, b: String) {
					a.equals(b.equals(null))
				}"""
            Assertions.assertThat(subject.lint(code).size).isEqualTo(1)
        }

        it("with non-nullable parameter") {
            val code = """
				fun x(a: String, b: String) {
					a.equals(b)
				}"""
            Assertions.assertThat(subject.lint(code).size).isEqualTo(0)
        }
    }
})
