import com.example.faidalib.helpers.InlineInventoryHelper
import com.example.faidalib.main
import com.example.faidalib.models.NewItemData
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GenerateItemSignatureTest {
    @Test
    fun `generate_unique_item_signature should return properly formatted string`(){
        /* AP - First two letters of the item.
        * 12 - The date purchased.
        * 10 - Month of purchase.
        * 26 - Year of purchase.
        * 180 - Quantity.
        * F - Category.
        * 1234 - Randomly generated values to cover a situation where two versions of the same
        * varieties of items have been brought.
        * RETURN AP12-1026-180F-1234
        */
        val ftChars = "AP"
        val dop = 12
        val mop = 10
        val yrp = 26
        val qty = 180
        val catgry = "F"
        val randValues = 1234
        val expectedSig = "AP12-1026-180F-1234"
        val actualSig = "$ftChars$dop-$mop$yrp-${if (qty < 100) "0$qty" else qty}$catgry-$randValues"
        assertEquals(expectedSig, actualSig, "Signature engine works perfectly!")
    }
}