import android.content.Context
import android.content.res.Resources
import com.example.recipeapp.data.RecipeRepositoryImpl
import com.example.recipeapp.data.model.IngredientDto
import com.example.recipeapp.domain.Recipe
import com.example.recipeapp.domain.RecipeDetails
import com.example.recipeapp.domain.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class RecipeRepositoryImplTest {

    private lateinit var repository: RecipeRepositoryImpl

    private val mockContext = mockk<Context>(relaxed = true)
    private val mockResources = mockk<Resources>(relaxed = true)

    @Before
    fun setUp() {
        every { mockContext.resources } returns mockResources
        repository = RecipeRepositoryImpl(mockContext, "https://coles.com.au")
    }

    @Test
    fun test_getRecipes_valid_json_returns_success() = runTest {
        val fakeJson = """
        {
            "recipes": [
                {
                    "dynamicTitle": "Pork, fennel and sage ragu with polenta",
                    "dynamicDescription": "Put your slow cooker to work and make this mouth-watering pork ragu. Served with fluffy polenta, it’s a guaranteed crowd-pleaser.",
                    "dynamicThumbnail": "/content/dam/coles/inspire-create/thumbnails/Pork-ragu-480x288.jpg",
                    "dynamicThumbnailAlt": "Pork ragu served on top of polenta with fennel and fried sage on top with bread on the side",
                    "recipeDetails": {
                        "amountLabel": "Serves",
                        "amountNumber": 8,
                        "prepLabel": "Prep",
                        "prepTime": "15m",
                        "prepNote": "+ cooling time",
                        "cookingLabel": "Cooking",
                        "cookingTime": "15m",
                        "cookTimeAsMinutes": 15,
                        "prepTimeAsMinutes": 15
                    },
                   "ingredients": [
                            { "ingredient": "1.2kg Coles Australian Pork Slow Cook Scotch Roast, cut into 10cm pieces" },
                            { "ingredient": "1 tsp ground fennel or fennel seeds" },
                            { "ingredient": "6 pancetta slices, thinly sliced" },
                            { "ingredient": "200ml red wine or chicken stock" },
                            { "ingredient": "1 tbs olive oil" },
                            { "ingredient": "1 brown onion, thinly sliced" },
                            { "ingredient": "2 x 400g cans cherry tomatoes" },
                            { "ingredient": "1 carrot, peeled, finely chopped" },
                            { "ingredient": "1 small fennel, thinly sliced, fronds reserved" },
                            { "ingredient": "2 garlic cloves, crushed" },
                            { "ingredient": "1 tbs finely chopped sage" }
                   ]
                }
            ]
        }
        """.trimIndent()

        val expectedRecipes = listOf(
            Recipe(
                title = "Pork, fennel and sage ragu with polenta",
                description = "Put your slow cooker to work and make this mouth-watering pork ragu. Served with fluffy polenta, it’s a guaranteed crowd-pleaser.",
                imageUrl = "https://coles.com.au/content/dam/coles/inspire-create/thumbnails/Pork-ragu-480x288.jpg",
                imageDescription = "Pork ragu served on top of polenta with fennel and fried sage on top with bread on the side",
                details = RecipeDetails(
                    amountLabel = "Serves",
                    amountNumber = 8,
                    prepLabel = "Prep",
                    prepTime = "15m",
                    prepNote = "+ cooling time",
                    cookingLabel = "Cooking",
                    cookingTime = "15m",
                    cookTimeAsMinutes = 15,
                    prepTimeAsMinutes = 15
                ),
                ingredients = listOf(
                    "1.2kg Coles Australian Pork Slow Cook Scotch Roast, cut into 10cm pieces",
                    "1 tsp ground fennel or fennel seeds",
                    "6 pancetta slices, thinly sliced",
                    "200ml red wine or chicken stock",
                    "1 tbs olive oil",
                    "1 brown onion, thinly sliced",
                    "2 x 400g cans cherry tomatoes",
                    "1 carrot, peeled, finely chopped",
                    "1 small fennel, thinly sliced, fronds reserved",
                    "2 garlic cloves, crushed",
                    "1 tbs finely chopped sage",
                )
            )
        )


        val inputStream = ByteArrayInputStream(fakeJson.toByteArray())
        every { mockResources.openRawResource(any()) } returns inputStream

        val result = repository.getRecipes()
        println("actual resutl $result")
        assert(result is Result.Success)
        assertEquals(expectedRecipes, (result as Result.Success).data)
    }

    @Test
    fun test_getRecipes_malformed_json_returns_error() = runTest {

        val invalidJson = """{ "invalid": "data" }"""
        val inputStream = ByteArrayInputStream(invalidJson.toByteArray())

        every { mockResources.openRawResource(any()) } returns inputStream

        val result = repository.getRecipes()

        assert(result is Result.Error)
        assertEquals("Oops! Something went wrong. Please try again later.", (result as Result.Error).errMessage)
    }
}
