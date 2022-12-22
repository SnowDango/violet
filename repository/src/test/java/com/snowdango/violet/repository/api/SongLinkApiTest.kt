package com.snowdango.violet.repository.api

import com.snowdango.violet.domain.response.SongApiResponse
import com.snowdango.violet.repository.api.provide.ApiProvider
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SongLinkApiTest : Spek({

    Feature("Call SongLink Api By AppleMusic") {

        Scenario("should return 200") {

            lateinit var repository: ApiRepository

            Given("SongLink Api") {
                repository = ApiRepository(ApiProvider())
            }

            var response: SongApiResponse? = null

            When("call SongLink Api") {
                runBlocking {
                    response = repository.getSongLink("appleMusic", "JP", "1497506561", "song")
                }
            }

            Then("return 200") {
                assertNotNull(response)
            }
        }

        Scenario(" should return other 200") {

            lateinit var repository: ApiRepository

            Given("SongLink Api") {
                repository = ApiRepository(ApiProvider())
            }

            var response: SongApiResponse? = null

            When("call SongLink Api") {
                runBlocking {
                    response = repository.getSongLink("appleMusi", "JP", "1497506561", "song")
                }
            }

            Then("return other 200") {
                assertNull(response)
            }
        }
    }

})